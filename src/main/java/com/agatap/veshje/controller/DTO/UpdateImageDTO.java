package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateImageDTO {
    private String name;

    private Integer productId;
    private List<Integer> careIds;
}
