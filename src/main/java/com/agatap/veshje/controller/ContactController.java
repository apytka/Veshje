package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.ContactDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateContactDTO;
import com.agatap.veshje.service.ContactService;
import com.agatap.veshje.service.exception.ContactDataInvalidException;
import com.agatap.veshje.service.exception.ContactNotFoundException;
import com.agatap.veshje.service.exception.ContactTopicNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping
    public List<ContactDTO> getAllContact() {
        return contactService.getAllContact();
    }

    @GetMapping("/{id}")
    public ContactDTO findContactDTOById(@PathVariable Integer id) throws ContactNotFoundException {
        return contactService.findContactDTOById(id);
    }

    @PostMapping
    public ContactDTO createContactDTO(@RequestBody CreateUpdateContactDTO createContactDTO)
            throws ContactDataInvalidException, ContactTopicNotFoundException {
        return contactService.createContactDTO(createContactDTO);
    }

    @PutMapping("/{id}")
    public ContactDTO updateContactDTO(@RequestBody CreateUpdateContactDTO updateContactDTO, @PathVariable Integer id)
            throws ContactNotFoundException, ContactDataInvalidException, ContactTopicNotFoundException {
        return contactService.updateContactDTO(updateContactDTO, id);
    }

    @DeleteMapping("/{id}")
    public ContactDTO deleteContactDTO(@PathVariable Integer id) throws ContactNotFoundException {
        return contactService.deleteContactDTO(id);
    }

}
