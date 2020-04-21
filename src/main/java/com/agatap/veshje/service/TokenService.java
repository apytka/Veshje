package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateTokenDTO;
import com.agatap.veshje.controller.DTO.TokenDTO;
import com.agatap.veshje.controller.mapper.TokenDTOMapper;
import com.agatap.veshje.model.Token;
import com.agatap.veshje.model.User;
import com.agatap.veshje.repository.TokenRepository;
import com.agatap.veshje.service.exception.TokenNotFoundException;
import com.agatap.veshje.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TokenService {

    private TokenRepository tokenRepository;
    private TokenDTOMapper mapper;
    private UserService userService;

    public List<TokenDTO> getAllTokens() {
        return tokenRepository.findAll().stream()
                .map(token -> mapper.mappingToDTO(token))
                .collect(Collectors.toList());
    }

    public TokenDTO findTokenDTOById(Integer id) throws TokenNotFoundException {
        return tokenRepository.findById(id)
                .map(token -> mapper.mappingToDTO(token))
                .orElseThrow(() -> new TokenNotFoundException());
    }

    public Token findTokenById(Integer id) throws TokenNotFoundException {
        return tokenRepository.findById(id)
                .orElseThrow(() -> new TokenNotFoundException());
    }

    public Token findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public TokenDTO createTokenDTO(CreateUpdateTokenDTO createUpdateTokenDTO) throws TokenNotFoundException, UserNotFoundException {
        if(tokenRepository.existsByToken(createUpdateTokenDTO.getToken())) {
            throw new TokenNotFoundException();
        }
        Token token = mapper.mappingToModel(createUpdateTokenDTO);
        token.setToken(String.valueOf(UUID.randomUUID()));
        token.setCreateDate(OffsetDateTime.now());

        if(token.getUser() != null) {
            User user = userService.findUserById(createUpdateTokenDTO.getUserId());
            token.setUser(user);
        }

        Token newToken = tokenRepository.save(token);
        return mapper.mappingToDTO(newToken);
    }

    public TokenDTO updateTokenDTO(CreateUpdateTokenDTO tokenDTO, Integer id) throws TokenNotFoundException {
        if(tokenRepository.existsByToken(tokenDTO.getToken())) {
            throw new TokenNotFoundException();
        }
        Token token = findTokenById(id);
        token.setToken(tokenDTO.getToken());
        token.setExpiryDate(tokenDTO.getExpiryDate());
        return mapper.mappingToDTO(token);
    }

    public TokenDTO deleteTokenDTO(Integer id) throws TokenNotFoundException {
        Token token = findTokenById(id);
        tokenRepository.delete(token);
        return mapper.mappingToDTO(token);
    }

    public Token deleteToken(String token) {
        Token tokenValue = findByToken(token);
        tokenRepository.delete(tokenValue);
        return tokenValue;
    }
}
