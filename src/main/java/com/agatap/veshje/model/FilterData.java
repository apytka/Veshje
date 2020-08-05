package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterData {
    private String name;
    private List<String> color;
    private List<SizeType> sizeType;
    private String search;
    private Double minPrice;
    private Double maxPrice;
    private String sort;

}
