package com.developia.ecommerce.service;

import com.developia.ecommerce.entity.CategoryEntity;
import com.developia.ecommerce.repository.CategoryRepository;
import com.developia.ecommerce.dto.CategoryResponse;
import com.developia.ecommerce.exception.CustomExceptions;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CategoryResponse mapToResponse(CategoryEntity entity) {
        return new CategoryResponse(entity.getId(), entity.getName());
    }

  
    public CategoryEntity findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Kateqoriya", "id", categoryId));
    }

    public CategoryResponse createCategory(String categoryName) {
        if (categoryRepository.findByName(categoryName).isPresent()) {
            throw new CustomExceptions.DuplicateResourceException("Kateqoriya", "ad", categoryName);
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setName(categoryName);
        return mapToResponse(categoryRepository.save(entity));
    }
}