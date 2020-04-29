package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CategoryDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCategoryDTO;
import com.agatap.veshje.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryDTOMapper {
    public CategoryDTO mappingToDTO(Category category) {
        List<Integer> productsId = category.getProducts().stream()
                .map(product -> product.getId())
                .collect(Collectors.toList());
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .productsIds(productsId)
                .createDate(category.getCreateDate())
                .updateDate(category.getUpdateDate())
                .build();
    }

    public Category mappingToModel(CreateUpdateCategoryDTO createCategoryDTO) {
        return Category.builder()
                .name(createCategoryDTO.getName())
                .build();
    }
}
