package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateTokenDTO;
import com.agatap.veshje.controller.DTO.TokenDTO;
import com.agatap.veshje.model.Token;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TokenDTOMapper {
    public TokenDTO mappingToDTO(Token token) {
        Integer userId = Optional.ofNullable(token.getUser())
                .map(tokenValue -> tokenValue.getId()).orElse(null);
        return TokenDTO.builder()
                .id(token.getId())
                .token(token.getToken())
                .expiryDate(token.getExpiryDate())
                .userId(userId)
                .createDate(token.getCreateDate())
                .updateDate(token.getUpdateDate())
                .build();
    }

    public Token mappingToModel(CreateUpdateTokenDTO createUpdateTokenDTO) {
        return Token.builder()
                .token(createUpdateTokenDTO.getToken())
                .expiryDate(createUpdateTokenDTO.getExpiryDate())
                .build();
    }

    public TokenDTO mapperUserToken(Token token) {
        return mappingToDTO(token);
    }
}
