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
public class CreateUpdateProductDTO {
    private String name;
    private Double price;
    private String color;
    private String description;
    private String complementDescription;

    private List<Integer> orderIds;
    private List<Integer> compositionIds;
    private List<Integer> categoriesIds;
    private List<Integer> reviewIds;
    private List<Integer> careIds;
    private List<Integer> imageIds;
    private List<Integer> sizeIds;
    private List<Integer> favouritesIds;
}
