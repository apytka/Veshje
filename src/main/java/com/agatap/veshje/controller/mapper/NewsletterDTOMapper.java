package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.controller.DTO.NewsletterDTO;
import com.agatap.veshje.model.Newsletter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class NewsletterDTOMapper {
    public NewsletterDTO mappingToDTO(Newsletter newsletter) {
        Integer userId = Optional.ofNullable(newsletter.getUsers())
                .map(u -> u.getId()).orElse(null);
        return NewsletterDTO.builder()
                .id(newsletter.getId())
                .email(newsletter.getEmail())
                .userId(userId)
                .createDate(newsletter.getCreateDate())
                .updateDate(newsletter.getUpdateDate())
                .build();
    }

    public Newsletter mappingToModel(CreateUpdateNewsletterDTO createNewsletterDTO) {
        return Newsletter.builder()
                .email(createNewsletterDTO.getEmail())
                .build();
    }
}
