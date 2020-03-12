package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdatePictureDTO {
    private String name;
    private String fileType;

    private Product productPictureIds;
    private List<Integer> careIds;
}
