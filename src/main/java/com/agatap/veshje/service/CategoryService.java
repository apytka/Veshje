package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CategoryDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCategoryDTO;
import com.agatap.veshje.controller.mapper.CategoryDTOMapper;
import com.agatap.veshje.model.Category;
import com.agatap.veshje.repository.CategoryRepository;
import com.agatap.veshje.service.exception.CategoryAlreadyExistsException;
import com.agatap.veshje.service.exception.CategoryDataInvalidException;
import com.agatap.veshje.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryDTOMapper mapper;

    public List<CategoryDTO> getAllCategory() {
        return categoryRepository.findAll().stream()
                .map(category -> mapper.mappingToDTO(category))
                .collect(Collectors.toList());
    }

    public CategoryDTO findCategoryDTOById(Integer id) throws CategoryNotFoundException {
        return categoryRepository.findById(id)
                .map(category -> mapper.mappingToDTO(category))
                .orElseThrow(() -> new CategoryNotFoundException());
    }

    public Category findCategoryOById(Integer id) throws CategoryNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException());
    }

    public CategoryDTO createCategoryDTO(CreateUpdateCategoryDTO createCategoryDTO)
            throws CategoryDataInvalidException, CategoryAlreadyExistsException {
        if (categoryRepository.existsByName(createCategoryDTO.getName())) {
            throw new CategoryAlreadyExistsException();
        }
        if (createCategoryDTO.getName() == null || createCategoryDTO.getName().length() < 2) {
            throw new CategoryDataInvalidException();
        }
        Category category = mapper.mappingToModel(createCategoryDTO);
        category.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Category newCategory = categoryRepository.save(category);
        return mapper.mappingToDTO(newCategory);
    }

    public CategoryDTO updateCategoryDTO(CreateUpdateCategoryDTO updateCategoryDTO, Integer id) throws CategoryNotFoundException {
        Category category = findCategoryOById(id);
        category.setName(updateCategoryDTO.getName());
        category.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Category updateCategory = categoryRepository.save(category);
        return mapper.mappingToDTO(updateCategory);
    }

    public CategoryDTO deleteCategoryDTO(Integer id) throws CategoryNotFoundException {
        Category category = findCategoryOById(id);
        categoryRepository.delete(category);
        return mapper.mappingToDTO(category);
    }

    public CategoryDTO findCategoryByName(String name) throws CategoryNotFoundException {
        return categoryRepository.findByName(name)
                .map(product -> mapper.mappingToDTO(product))
                .orElseThrow(() -> new CategoryNotFoundException());
    }
}