package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.service.NewsletterService;
import com.agatap.veshje.service.exception.NewsletterAlreadyExistsException;
import com.agatap.veshje.service.exception.NewsletterDataInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewsletterViewController {

    @Autowired
    private NewsletterService newsletterService;

    @PostMapping("/index")
    public ModelAndView addNewsletter(@ModelAttribute(name = "addNewsletter") CreateUpdateNewsletterDTO createUpdateNewsletterDTO) throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        newsletterService.createNewsletterDTO(createUpdateNewsletterDTO);
        return new ModelAndView("redirect:index");
    }
}
