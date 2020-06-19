package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactTopicDTO {
    private Integer id;
    private String topic;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
    private List<Integer> contactsId;
}
