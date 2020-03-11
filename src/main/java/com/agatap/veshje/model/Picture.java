package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Picture {
    private Integer id;
    private String name;
    private String fileType;
    private long size;
    private byte[] data;
//    private List<Product> productList;
//    private List<CareProduct> careProductList;
}
