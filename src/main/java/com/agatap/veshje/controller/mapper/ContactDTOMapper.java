package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.ContactDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateContactDTO;
import com.agatap.veshje.model.Contact;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContactDTOMapper {

    public ContactDTO mappingToDTO(Contact contact) {
        Integer contactTopicId = Optional.ofNullable(contact.getContactTopic())
                .map(contactTopic -> contactTopic.getId()).orElse(null);
        return ContactDTO.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .phoneNumber(contact.getPhoneNumber())
                .email(contact.getEmail())
                .orderNumber(contact.getOrderNumber())
                .message(contact.getMessage())
                .status(contact.getStatus())
                .contactTopicId(contactTopicId)
                .createDate(contact.getCreateDate())
                .updateDate(contact.getUpdateDate())
                .build();
    }

    public Contact mappingToModel(CreateUpdateContactDTO updateContactDTO) {
        return Contact.builder()
                .firstName(updateContactDTO.getFirstName())
                .lastName(updateContactDTO.getLastName())
                .phoneNumber(updateContactDTO.getPhoneNumber())
                .email(updateContactDTO.getEmail())
                .orderNumber(updateContactDTO.getOrderNumber())
                .message(updateContactDTO.getMessage())
                .status(updateContactDTO.getStatus())
                .build();
    }
}
