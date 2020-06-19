package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.ContactTopicDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateContactTopicDTO;
import com.agatap.veshje.model.ContactTopic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactTopicDTOMapper {
    public ContactTopicDTO mappingToDTO(ContactTopic contactTopic) {
        List<Integer> contactsId = contactTopic.getContact().stream()
                .map(contact -> contact.getId())
                .collect(Collectors.toList());
        return ContactTopicDTO.builder()
                .id(contactTopic.getId())
                .topic(contactTopic.getTopic())
                .contactsId(contactsId)
                .createDate(contactTopic.getCreateDate())
                .updateDate(contactTopic.getUpdateDate())
                .build();
    }

    public ContactTopic mappingToModel(CreateUpdateContactTopicDTO createUpdateContactTopicDTO) {
        return ContactTopic.builder()
                .topic(createUpdateContactTopicDTO.getTopic())
                .build();
    }
}
