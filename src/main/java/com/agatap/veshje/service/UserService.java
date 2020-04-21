package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.controller.mapper.NewsletterDTOMapper;
import com.agatap.veshje.controller.mapper.TokenDTOMapper;
import com.agatap.veshje.controller.mapper.UserDTOMapper;
import com.agatap.veshje.model.Newsletter;
import com.agatap.veshje.model.User;
import com.agatap.veshje.model.UserRole;
import com.agatap.veshje.model.Token;
import com.agatap.veshje.repository.NewsletterRepository;
import com.agatap.veshje.repository.UserRepository;
import com.agatap.veshje.repository.TokenRepository;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private TokenRepository verificationTokenRepository;
    private MailSenderService mailSenderService;
    private UserDTOMapper mapper;
    private NewsletterDTOMapper newsletterDTOMapper;
    private NewsletterRepository newsletterRepository;
    private NewsletterService newsletterService;
    private TokenDTOMapper tokenDTOMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> mapper.mappingToDTO(user))
                .collect(Collectors.toList());
    }

    public UserDTO findUserDTOById(Integer id) throws UserNotFoundException {
        return userRepository.findById(id)
                .map(user -> mapper.mappingToDTO(user))
                .orElseThrow(() -> new UserNotFoundException());
    }

    public User findUserById(Integer id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());
    }

    public UserDTO createUser(CreateUserDTO createUserDTO)
            throws UserAlreadyExistException, UserDataInvalidException, AddressDataInvalidException, NewsletterNotFoundException {

        validatePattern(createUserDTO);

        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new UserAlreadyExistException();
        }
        if (createUserDTO.getEmail() == null) {
            throw new UserDataInvalidException();
        }

        if (!createUserDTO.getPassword().equals(createUserDTO.getConfirmPassword())) {
            throw new UserDataInvalidException();
        }

        User user = mapper.mappingToModel(createUserDTO);
        user.setUserRole(UserRole.USER);
        user.setSubscribedNewsletter(createUserDTO.getSubscribedNewsletter());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateDate(OffsetDateTime.now());

        if (createUserDTO.getSubscribedNewsletter()) {
            if (newsletterService.isNewsletterEmailExists(createUserDTO.getEmail())) {
                newsletterService.deleteNewsletterByEmail(user.getEmail());
            }
            Newsletter newsletter = addNewsletterForUser(user);
            user.setNewsletter(newsletter);
            newsletter.setUsers(user);
        }

        User newUser = userRepository.save(user);
        sendToken(newUser, "register?token=", "Veshje shop - confirmation link",
                "confirm-registration", 60 * 24);
        return mapper.mappingToDTO(newUser);
    }

    public UserDTO updateUser(UpdateUserDTO updateUserDTO, Integer id)
            throws UserNotFoundException {
        User user = findUserById(id);
        User updateUser;
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setEmail(updateUserDTO.getEmail());
        user.setUserRole(updateUserDTO.getUserRole());
        user.setSubscribedNewsletter(updateUserDTO.getSubscribedNewsletter());
        user.setEnabled(updateUserDTO.isEnabled());
        user.setUpdateDate(OffsetDateTime.now());

        updateUser = userRepository.save(user);
        return mapper.mappingToDTO(updateUser);
    }

    @Transactional
    public UserDTO deleteUser(Integer id) throws UserNotFoundException {
        User user = findUserById(id);
        userRepository.delete(user);
        return mapper.mappingToDTO(user);
    }

    public User findUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());
    }

    public boolean isUserEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void sendToken(User user, String path, String subjectEmail, String htmlPage, int expiryTime) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setToken(tokenValue);
        token.setUser(user);
        token.setExpiryDate(expiryTimeInMinutes(expiryTime));
        verificationTokenRepository.save(token);

        String url = "http://localhost:8080/" + path + tokenValue;
        try {
            mailSenderService.sendMail(user.getEmail(), subjectEmail, url, true, htmlPage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Date expiryTimeInMinutes(int expiryTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTime);
        return new Date(calendar.getTime().getTime());
    }

    public UserDTO updatePassword(User user, ChangePasswordDTO changePasswordDTO) throws UserNotFoundException, UserDataInvalidException {
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())
                || !passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
            throw new UserDataInvalidException();
        }
        Integer id = user.getId();
        User userById = findUserById(id);
        userById.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userById.setUpdateDate(OffsetDateTime.now());
        User updateUser = userRepository.save(userById);
        return mapper.mappingToDTO(updateUser);
    }

    public UserDTO changeForgotPassword(Integer userId, ChangeForgotPasswordDTO changeForgotPasswordDTO) throws UserDataInvalidException, UserNotFoundException {
        if (!changeForgotPasswordDTO.getNewPassword().equals(changeForgotPasswordDTO.getConfirmPassword())) {
            throw new UserDataInvalidException();
        }
        User user = findUserById(userId);
        user.setPassword(passwordEncoder.encode(changeForgotPasswordDTO.getNewPassword()));
        user.setUpdateDate(OffsetDateTime.now());
        User updateUser = userRepository.save(user);
        return mapper.mappingToDTO(updateUser);
    }

    public UserDTO updateSubscriptionNewsletter(UserUpdateNewsletterDTO userUpdateNewsletter, Integer id) throws UserNotFoundException, NewsletterNotFoundException {
        User user = findUserById(id);
        User updateUser;
        user.setSubscribedNewsletter(userUpdateNewsletter.getSubscribedNewsletter());
        user.setUpdateDate(OffsetDateTime.now());

        if (!user.getSubscribedNewsletter()) {
            user.setNewsletter(null);
            updateUser = userRepository.save(user);
            newsletterService.deleteNewsletterByEmail(user.getEmail());
        } else {
            Newsletter newsletter = addNewsletterForUser(user);
            user.setNewsletter(newsletter);
            updateUser = userRepository.save(user);
        }
        return mapper.mappingToDTO(updateUser);
    }

    private void validatePattern(CreateUserDTO createUserDTO) throws AddressDataInvalidException {
        String pattern = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,}";
        Pattern password = Pattern.compile(pattern);
        Matcher matcherPassword = password.matcher(createUserDTO.getPassword());
        boolean passwordCheck = matcherPassword.matches();
        Pattern passwordConfirm = Pattern.compile(pattern);
        Matcher matcherPasswordConfirm = passwordConfirm.matcher(createUserDTO.getPassword());
        boolean passwordConfirmCheck = matcherPasswordConfirm.matches();

        if (!passwordCheck || !passwordConfirmCheck) {
            throw new AddressDataInvalidException();
        }
    }

    private Newsletter addNewsletterForUser(User user) {
        CreateUpdateNewsletterDTO createUpdateNewsletterDTO = new CreateUpdateNewsletterDTO();
        Newsletter newsletter = newsletterDTOMapper.mappingToModel(createUpdateNewsletterDTO);
        newsletter.setEmail(user.getEmail());
        newsletter.setCreateDate(OffsetDateTime.now());
        newsletterRepository.save(newsletter);
        return newsletter;
    }

    public TokenDTO getTokenByUserId(Integer id) throws UserNotFoundException {
        User user = findUserById(id);
        return tokenDTOMapper.mapperUserToken(user.getToken());
    }


}
