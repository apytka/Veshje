package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.ContactDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateContactDTO;
import com.agatap.veshje.controller.mapper.ContactDTOMapper;
import com.agatap.veshje.model.Contact;
import com.agatap.veshje.model.ContactStatus;
import com.agatap.veshje.model.ContactTopic;
import com.agatap.veshje.repository.ContactRepository;
import com.agatap.veshje.service.exception.ContactDataInvalidException;
import com.agatap.veshje.service.exception.ContactNotFoundException;
import com.agatap.veshje.service.exception.ContactTopicNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContactService {

    private ContactRepository contactRepository;
    private ContactDTOMapper mapper;
    private ContactTopicService contactTopicService;

    public List<ContactDTO> getAllContact() {
        return contactRepository.findAll().stream()
                .map(contact -> mapper.mappingToDTO(contact))
                .collect(Collectors.toList());
    }

    public ContactDTO findContactDTOById(Integer id) throws ContactNotFoundException {
        return contactRepository.findById(id)
                .map(contact -> mapper.mappingToDTO(contact))
                .orElseThrow(() -> new ContactNotFoundException());
    }

    public Contact findContactById(Integer id) throws ContactNotFoundException {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException());
    }

    public ContactDTO createContactDTO(CreateUpdateContactDTO createContactDTO) throws ContactDataInvalidException, ContactTopicNotFoundException {
        invalidData(createContactDTO);

        Contact contact = mapper.mappingToModel(createContactDTO);
        contact.setStatus(ContactStatus.NEW);
        contact.setCreateDate(OffsetDateTime.now());

        if(createContactDTO.getContactTopicId() != null) {
            ContactTopic contactTopic = contactTopicService.findContactTopicById(createContactDTO.getContactTopicId());
            contact.setContactTopic(contactTopic);
            contactTopic.getContact().add(contact);
        }

        Contact newContact = contactRepository.save(contact);
        return mapper.mappingToDTO(newContact);
    }

    public ContactDTO updateContactDTO(CreateUpdateContactDTO updateContactDTO, Integer id) throws ContactNotFoundException, ContactDataInvalidException, ContactTopicNotFoundException {
        invalidData(updateContactDTO);

        Contact contact = findContactById(id);
        contact.setFirstName(updateContactDTO.getFirstName());
        contact.setLastName(updateContactDTO.getLastName());
        contact.setPhoneNumber(updateContactDTO.getPhoneNumber());
        contact.setEmail(updateContactDTO.getEmail());
        contact.setOrderNumber(updateContactDTO.getOrderNumber());
        contact.setMessage(updateContactDTO.getMessage());
        contact.setStatus(updateContactDTO.getStatus());
        contact.setUpdateDate(OffsetDateTime.now());

        if(updateContactDTO.getContactTopicId() != null) {
            ContactTopic contactTopic = contactTopicService.findContactTopicById(updateContactDTO.getContactTopicId());
            contact.setContactTopic(contactTopic);
            contactTopic.getContact().add(contact);
        }

        Contact updateContact = contactRepository.save(contact);
        return mapper.mappingToDTO(updateContact);
    }

    public ContactDTO deleteContactDTO(Integer id) throws ContactNotFoundException {
        Contact contactById = findContactById(id);
        contactRepository.delete(contactById);
        return mapper.mappingToDTO(contactById);
    }

    private void invalidData(CreateUpdateContactDTO createContactDTO) throws ContactDataInvalidException {
        if(createContactDTO.getMessage().length() < 10 || createContactDTO.getMessage().length() > 1500) {
            throw new ContactDataInvalidException();
        }
        String phoneNumberPattern = "^\\+\\d{11}$";
        Pattern patternPhoneNumber = Pattern.compile(phoneNumberPattern);
        Matcher matcherPhoneNumber = patternPhoneNumber.matcher(createContactDTO.getPhoneNumber());
        boolean phoneNumberCheck = matcherPhoneNumber.matches();

        if (!phoneNumberCheck) {
            throw new ContactDataInvalidException();
        }
    }
}
