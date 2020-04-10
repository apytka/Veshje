package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.controller.DTO.NewsletterDTO;
import com.agatap.veshje.controller.mapper.NewsletterDTOMapper;
import com.agatap.veshje.model.Newsletter;
import com.agatap.veshje.repository.NewsletterRepository;
import com.agatap.veshje.service.exception.NewsletterAlreadyExistsException;
import com.agatap.veshje.service.exception.NewsletterDataInvalidException;
import com.agatap.veshje.service.exception.NewsletterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsletterService {
    @Autowired
    private NewsletterRepository newsletterRepository;
    @Autowired
    private NewsletterDTOMapper mapper;

    public List<NewsletterDTO> getAllNewsletter() {
        return newsletterRepository.findAll().stream()
                .map(newsletter -> mapper.mappingToDTO(newsletter))
                .collect(Collectors.toList());
    }

    public NewsletterDTO findNewsletterDTOById(Integer id) throws NewsletterNotFoundException {
        return newsletterRepository.findById(id)
                .map(newsletter -> mapper.mappingToDTO(newsletter))
                .orElseThrow(() -> new NewsletterNotFoundException());
    }

    public Newsletter findNewsletterById(Integer id) throws NewsletterNotFoundException {
        return newsletterRepository.findById(id)
                .orElseThrow(() -> new NewsletterNotFoundException());
    }

    public Newsletter findNewsletterByEmail(String email) throws NewsletterNotFoundException {
        return newsletterRepository.findByEmail(email)
                .orElseThrow(() -> new NewsletterNotFoundException());
    }

    public boolean isNewsletterEmailExists(String email) {
        return newsletterRepository.existsByEmail(email);
    }

    public NewsletterDTO createNewsletterDTO(CreateUpdateNewsletterDTO createNewsletterDTO) throws NewsletterDataInvalidException, NewsletterAlreadyExistsException {
        if(newsletterRepository.existsByEmail(createNewsletterDTO.getEmail())) {
            throw new NewsletterAlreadyExistsException();
        }
        if(createNewsletterDTO.getEmail() == null) {
            throw new NewsletterDataInvalidException();
        }
        Newsletter newsletter = mapper.mappingToModel(createNewsletterDTO);
        newsletter.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Newsletter newNewsletter = newsletterRepository.save(newsletter);
        return mapper.mappingToDTO(newNewsletter);
    }

    public NewsletterDTO updateNewsletterDTO(CreateUpdateNewsletterDTO updateNewsletterDTO, Integer id) throws NewsletterNotFoundException {
        Newsletter newsletter = findNewsletterById(id);
        newsletter.setEmail(updateNewsletterDTO.getEmail());
        newsletter.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Newsletter updateNewsletter = newsletterRepository.save(newsletter);
        return mapper.mappingToDTO(updateNewsletter);
    }

    public NewsletterDTO deleteNewsletterDTO(Integer id) throws NewsletterNotFoundException {
        Newsletter newsletter = findNewsletterById(id);
        newsletterRepository.delete(newsletter);
        return mapper.mappingToDTO(newsletter);
    }

    public NewsletterDTO deleteNewsletterByEmail(String email) throws NewsletterNotFoundException {
        Newsletter newsletter = findNewsletterByEmail(email);
        newsletterRepository.delete(newsletter);
        return mapper.mappingToDTO(newsletter);
    }
}
