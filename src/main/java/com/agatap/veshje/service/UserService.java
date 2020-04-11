package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.controller.mapper.NewsletterDTOMapper;
import com.agatap.veshje.controller.mapper.UserDTOMapper;
import com.agatap.veshje.model.Newsletter;
import com.agatap.veshje.model.User;
import com.agatap.veshje.model.UserRole;
import com.agatap.veshje.model.VerificationToken;
import com.agatap.veshje.repository.NewsletterRepository;
import com.agatap.veshje.repository.UserRepository;
import com.agatap.veshje.repository.VerificationTokenRepository;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import javax.mail.MessagingException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private MailSenderService mailSenderService;
    private UserDTOMapper mapper;
    private NewsletterDTOMapper newsletterDTOMapper;
    private NewsletterRepository newsletterRepository;
    private NewsletterService newsletterService;

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

        if(createUserDTO.getSubscribedNewsletter() != null) {
            if(newsletterService.isNewsletterEmailExists(createUserDTO.getEmail())) {
                newsletterService.deleteNewsletterByEmail(user.getEmail());
            }
            Newsletter newsletter = addNewsletterForUser(user);
            user.setNewsletter(newsletter);
            newsletter.setUsers(user);
        }

        User newUser = userRepository.save(user);
        sendToken(newUser);
        return mapper.mappingToDTO(newUser);
    }

    public UserDTO updateUser(UpdateUserDTO updateUserDTO, Integer id)
            throws UserNotFoundException, NewsletterNotFoundException {
        User user = findUserById(id);
        User updateUser;
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setEmail(updateUserDTO.getEmail());
        user.setUserRole(updateUserDTO.getUserRole());
        user.setSubscribedNewsletter(updateUserDTO.getSubscribedNewsletter());
        user.setEnabled(updateUserDTO.isEnabled());
        user.setUpdateDate(OffsetDateTime.now());

        if (!user.getSubscribedNewsletter()) {
            user.setNewsletter(null);
            updateUser = userRepository.save(user);
            newsletterService.deleteNewsletterByEmail(user.getEmail());
        } else {
            updateUser = userRepository.save(user);
        }

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

    private void sendToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);

        String url = "http://localhost:8080/register?token=" + token;
        try {
            mailSenderService.sendMail(user.getEmail(), "Veshje shop - confirmation link",
                    url, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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

    public UserDTO updatePassword(User user, ChangePasswordDTO changePasswordDTO) throws UserNotFoundException, UserDataInvalidException {
//        if(!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())
//                || !user.getPassword().equals(changePasswordDTO.getCurrentPassword())) {
//            throw new UserDataInvalidException();
//        }
        Integer id = user.getId();
        User userById = findUserById(id);
        userById.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userById.setUpdateDate(OffsetDateTime.now());
        User updateUser = userRepository.save(userById);
        return mapper.mappingToDTO(updateUser);
    }
}
