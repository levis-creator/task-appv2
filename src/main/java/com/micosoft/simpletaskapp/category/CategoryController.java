package com.micosoft.simpletaskapp.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@CrossOrigin
@RestController
@RequestMapping("/api/v1//category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;


    @GetMapping
    public List<CategoryDTO> getAllCategory() {
        return categoryService.getAllCategories();
    }

    @GetMapping("{id}")
    public CategoryDTO getSingleCategory(@PathVariable UUID id) {
        return categoryService.getCategory(id);
    }

    @PostMapping
    public void createCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);

    }

    @PutMapping("{id}")
    public void  updatingCategory(@PathVariable UUID id, @RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategories(id, categoryDTO);
    }
    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable UUID id){
        categoryService.deleteCategory(id);
    }

}
