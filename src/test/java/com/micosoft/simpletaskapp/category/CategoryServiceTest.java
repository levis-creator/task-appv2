package com.micosoft.simpletaskapp.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryService categoryService;

    List<Category> categories = new ArrayList<>(
            List.of(
                    new Category(UUID.randomUUID(), "Home", "#fff"),
                    new Category(UUID.randomUUID(), "Grocery", "#fff"),
                    new Category(UUID.randomUUID(), "School", "#fff")
            )
    );

    Category singleCategory = new Category(UUID.randomUUID(), "School", "#fff");
    CategoryDTO categoryDTO = new CategoryDTO(UUID.randomUUID(), "School Exam", "#fff88");

    @BeforeEach
    void setUp() {
    }

    @Test
    void gettingAllCategories() {
        when(categoryRepository.findAll()).thenReturn(categories);
        categoryService.getAllCategories();
        verify(categoryRepository).findAll();
    }

    @Test
    void gettingSingleCategory_WhenCategoryExists() {
        when(categoryRepository.findById(singleCategory.getId())).thenReturn(Optional.of(singleCategory));
        categoryService.getCategory(singleCategory.getId());
    }

    @Test
    void gettingSingleCategory_WhenCategoryDoesNotExists() {
        when(categoryRepository.findById(singleCategory.getId())).thenReturn(Optional.empty());
        categoryService.getCategory(singleCategory.getId());
    }

    @Test
    void creatingCategory_WhenNameDoesNotExist() {
        when(categoryRepository.findByCategoryNameIgnoreCase(categoryDTO.getCategoryName())).thenReturn(Optional.empty());
        categoryService.createCategory(categoryDTO);
        verify(categoryRepository).save(any());
    }

    @Test
    void creatingCategory_WhenNameExist() {
        when(categoryRepository.findByCategoryNameIgnoreCase(categoryDTO.getCategoryName())).thenReturn(Optional.of(singleCategory));
        assertThrows(IllegalStateException.class, () -> categoryService.createCategory(categoryDTO));
    }

    @Test
    void updateCategory_WhenCategoryExist() {
        when(categoryRepository.findById(singleCategory.getId())).thenReturn(Optional.of(singleCategory));
        when(categoryRepository.findByCategoryNameIgnoreCase(categoryDTO.getCategoryName())).thenReturn(Optional.empty());
        categoryService.updateCategories(singleCategory.getId(), categoryDTO);
        verify(categoryRepository).save(any());
    }

    @Test
    void deleteCategory_WhenCategoryExist() {
        when(categoryRepository.findById(singleCategory.getId())).thenReturn(Optional.of(singleCategory));
        categoryService.deleteCategory(singleCategory.getId());
        verify(categoryRepository).deleteById(singleCategory.getId());
    }
    @Test
    void deletingCategory_WhenCategoryDoesNotExist(){
        when(categoryRepository.findById(singleCategory.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, ()->categoryService.deleteCategory(singleCategory.getId()) );
        verify(categoryRepository, never()).deleteById(singleCategory.getId());
    }

}