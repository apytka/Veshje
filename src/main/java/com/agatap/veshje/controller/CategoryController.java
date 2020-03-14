package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CategoryDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCategoryDTO;
import com.agatap.veshje.service.CategoryService;
import com.agatap.veshje.service.exception.CategoryAlreadyExistsException;
import com.agatap.veshje.service.exception.CategoryDataInvalidException;
import com.agatap.veshje.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/{id}")
    public CategoryDTO findCategoryDTOById(@PathVariable Integer id) throws CategoryNotFoundException {
        return categoryService.findCategoryDTOById(id);
    }

    @PostMapping
    public CategoryDTO createCategoryDTO(@RequestBody CreateUpdateCategoryDTO createCategoryDTO)
            throws CategoryDataInvalidException, CategoryAlreadyExistsException {
        return categoryService.createCategoryDTO(createCategoryDTO);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategoryDTO(@RequestBody CreateUpdateCategoryDTO updateCategoryDTO, @PathVariable Integer id)
            throws CategoryNotFoundException {
        return categoryService.updateCategoryDTO(updateCategoryDTO, id);
    }

    @DeleteMapping("/{id}")
    public CategoryDTO deleteCategoryDTO(@PathVariable Integer id) throws CategoryNotFoundException {
        return categoryService.deleteCategoryDTO(id);
    }
}
