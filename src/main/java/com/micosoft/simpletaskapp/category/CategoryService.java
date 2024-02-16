package com.micosoft.simpletaskapp.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        List<Category> categoryDb = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category category : categoryDb) {
            CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getCategoryName(), category.getColor());
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }

    public CategoryDTO getCategory(UUID id) {
        Category categoryDb = categoryRepository.findById(id).orElse(null);
        if (categoryDb == null) {
            return null;
        }
        return new CategoryDTO(categoryDb.getId(), categoryDb.getCategoryName(), categoryDb.getColor());
    }

    public void createCategory(CategoryDTO categoryDTO) {
        Category categoryDb = categoryRepository.findByCategoryNameIgnoreCase(categoryDTO.getCategoryName()).orElse(null);
        Category category = new Category();
        if (categoryDb == null) {
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setColor(categoryDTO.getColor());
            categoryRepository.save(category);
        } else {
            throw new IllegalStateException("Category already exists");
        }
    }


    public void updateCategories(UUID id, CategoryDTO categoryDTO) {
        Category categoryDb = categoryRepository.findById(id).orElse(null);
        if (categoryDb != null) {
            Category categoryByName = categoryRepository.findByCategoryNameIgnoreCase(categoryDTO.getCategoryName()).orElse(null);
            if (categoryByName == null && !categoryDb.getCategoryName().equals(categoryDTO.getCategoryName()) && !categoryDTO.getCategoryName().isEmpty()) {
                categoryDb.setCategoryName(categoryDTO.getCategoryName());
            }
            if (categoryByName == null && !categoryDb.getColor().equals(categoryDTO.getColor()) && !categoryDTO.getColor().isEmpty()) {
                categoryDb.setColor(categoryDTO.getColor());
            }
            categoryRepository.save(categoryDb);
        }
    }


    public void deleteCategory(UUID id) {
        Category categoryDb = categoryRepository.findById(id).orElse(null);
        if (categoryDb != null) {
            categoryRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Category not available");
        }
    }
}
