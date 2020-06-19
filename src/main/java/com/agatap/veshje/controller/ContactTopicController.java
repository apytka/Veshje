package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.ContactTopicDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateContactTopicDTO;
import com.agatap.veshje.service.ContactTopicService;
import com.agatap.veshje.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contact-topic")
public class ContactTopicController {
    @Autowired
    private ContactTopicService contactTopicService;

    @GetMapping
    public List<ContactTopicDTO> getAllContactTopic() {
        return contactTopicService.getAllContactTopic();
    }

    @GetMapping("/{id}")
    public ContactTopicDTO findContactTopicDTOById(@PathVariable Integer id) throws ContactTopicNotFoundException {
        return contactTopicService.findContactTopicDTOById(id);
    }

    @PostMapping
    public ContactTopicDTO createContactTopicDTO(@RequestBody CreateUpdateContactTopicDTO createUpdateContactTopicDTO) {
        return contactTopicService.createContactTopicDTO(createUpdateContactTopicDTO);
    }

    @PutMapping("/{id}")
    public ContactTopicDTO updateContactTopicDTO(@RequestBody CreateUpdateContactTopicDTO createUpdateContactTopicDTO, @PathVariable Integer id)
            throws ContactTopicNotFoundException {
        return contactTopicService.updateContactTopicDTO(createUpdateContactTopicDTO, id);
    }

    @DeleteMapping("/{id}")
    public ContactTopicDTO deleteContactTopicDTO(@PathVariable Integer id) throws ContactTopicNotFoundException {
        return contactTopicService.deleteContactTopicDTO(id);
    }
}
