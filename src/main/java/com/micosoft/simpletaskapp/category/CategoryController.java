package com.micosoft.simpletaskapp.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;


    @GetMapping
    public List<CategoryDTO> getAllCategory(Principal principal) {
        return categoryService.getAllCategories(principal);
    }

    @GetMapping("{id}")
    public CategoryDTO getSingleCategory(@PathVariable UUID id, Principal principal) {
        return categoryService.getCategory(id, principal);
    }

    @PostMapping
    public Category createCategory(Principal principal,@RequestBody CategoryDTO categoryDTO) {
       return categoryService.createCategory(principal, categoryDTO);

    }

    @PutMapping("{id}")
    public ResponseEntity<Category> updatingCategory(@PathVariable UUID id, @RequestBody CategoryDTO categoryDTO, Principal principal){
        return categoryService.updateCategories(id, categoryDTO, principal);
    }
    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable UUID id, Principal principal){
        categoryService.deleteCategory(id, principal);
    }

}
