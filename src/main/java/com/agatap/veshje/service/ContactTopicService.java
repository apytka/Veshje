package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.ContactTopicDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateContactTopicDTO;
import com.agatap.veshje.controller.mapper.ContactTopicDTOMapper;
import com.agatap.veshje.model.ContactTopic;
import com.agatap.veshje.repository.ContactTopicRepository;
import com.agatap.veshje.service.exception.ContactNotFoundException;
import com.agatap.veshje.service.exception.ContactTopicNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContactTopicService {

    private ContactTopicRepository contactTopicRepository;
    private ContactTopicDTOMapper mapper;

    public List<ContactTopicDTO> getAllContactTopic() {
        return contactTopicRepository.findAll().stream()
                .map(contact -> mapper.mappingToDTO(contact))
                .collect(Collectors.toList());
    }

    public ContactTopicDTO findContactTopicDTOById(Integer id) throws ContactTopicNotFoundException {
        return contactTopicRepository.findById(id)
                .map(contact -> mapper.mappingToDTO(contact))
                .orElseThrow(() -> new ContactTopicNotFoundException());
    }

    public ContactTopic findContactTopicById(Integer id) throws ContactTopicNotFoundException {
        return contactTopicRepository.findById(id)
                .orElseThrow(() -> new ContactTopicNotFoundException());
    }

    public ContactTopicDTO createContactTopicDTO(CreateUpdateContactTopicDTO createUpdateContactTopicDTO) {
        ContactTopic contactTopic = mapper.mappingToModel(createUpdateContactTopicDTO);
        contactTopic.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        ContactTopic newContactTopic = contactTopicRepository.save(contactTopic);
        return mapper.mappingToDTO(newContactTopic);
    }

    public ContactTopicDTO updateContactTopicDTO(CreateUpdateContactTopicDTO createUpdateContactTopicDTO, Integer id)
            throws ContactTopicNotFoundException {
        ContactTopic contactTopic = findContactTopicById(id);
        contactTopic.setTopic(createUpdateContactTopicDTO.getTopic());
        contactTopic.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        ContactTopic updateContact = contactTopicRepository.save(contactTopic);
        return mapper.mappingToDTO(updateContact);
    }

    public ContactTopicDTO deleteContactTopicDTO(Integer id) throws ContactTopicNotFoundException {
        ContactTopic contactById = findContactTopicById(id);
        contactTopicRepository.delete(contactById);
        return mapper.mappingToDTO(contactById);
    }
}
