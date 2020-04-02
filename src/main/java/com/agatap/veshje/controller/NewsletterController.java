package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.controller.DTO.NewsletterDTO;
import com.agatap.veshje.service.NewsletterService;
import com.agatap.veshje.service.exception.NewsletterAlreadyExistsException;
import com.agatap.veshje.service.exception.NewsletterDataInvalidException;
import com.agatap.veshje.service.exception.NewsletterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/newsletters")
public class NewsletterController {
    @Autowired
    private NewsletterService newsletterService;

    @GetMapping
    public List<NewsletterDTO> getAllNewsletter() {
        return newsletterService.getAllNewsletter();
    }

    @GetMapping("/{id}")
    public NewsletterDTO findNewsletterDTOById(@PathVariable Integer id) throws NewsletterNotFoundException {
        return newsletterService.findNewsletterDTOById(id);
    }

    @PostMapping
    public NewsletterDTO createNewsletterDTO(@Valid @RequestBody CreateUpdateNewsletterDTO createNewsletterDTO) throws NewsletterDataInvalidException, NewsletterAlreadyExistsException {
        return newsletterService.createNewsletterDTO(createNewsletterDTO);
    }

    @PutMapping("/{id}")
    public NewsletterDTO updateNewsletterDTO(@RequestBody CreateUpdateNewsletterDTO updateNewsletterDTO, @PathVariable Integer id) throws NewsletterNotFoundException {
        return newsletterService.updateNewsletterDTO(updateNewsletterDTO, id);
    }

    @DeleteMapping("/{id}")
    public NewsletterDTO deleteNewsletterDTO(@PathVariable Integer id) throws NewsletterNotFoundException {
        return newsletterService.deleteNewsletterDTO(id);
    }
}
