package com.micosoft.simpletaskapp.category;

import com.micosoft.simpletaskapp.errors.exceptions.AlreadyExistException;
import com.micosoft.simpletaskapp.errors.exceptions.NotFoundException;
import com.micosoft.simpletaskapp.user.AppUser;
import com.micosoft.simpletaskapp.user.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final AppUserRepository appUserRepository;

    public List<CategoryDTO> getAllCategories(Principal principal) {

        AppUser user=appUserRepository.findByUsernameOrEmail(principal.getName(), principal.getName()).orElse(null);
        if(user!=null){
            List<Category> categoryDb = categoryRepository.findByAppUser_Uuid(user.getUuid());
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            for (Category category : categoryDb) {
                CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getCategoryName(), category.getColor());
                categoryDTOS.add(categoryDTO);
            }
            return ResponseEntity.ok().body(categoryDTOS).getBody();
        }

        return null;
    }

    public CategoryDTO getCategory(UUID id,Principal principal) {
        AppUser user=appUserRepository.findByUsernameOrEmail(principal.getName(), principal.getName()).orElse(null);
        assert user != null;
        Category categoryDb = categoryRepository.findByIdAndAppUser_Uuid(id, user.getUuid()).orElse(null);
        if (categoryDb == null) {
            throw new NotFoundException("Category not found");
        }
        return ResponseEntity.ok( new CategoryDTO(categoryDb.getId(), categoryDb.getCategoryName(), categoryDb.getColor())).getBody();
    }

    public Category createCategory(Principal principal, CategoryDTO categoryDTO) {
        AppUser user=appUserRepository.findByUsernameOrEmail(principal.getName(), principal.getName()).orElse(null);
        assert user != null;
        Category categoryDb = categoryRepository.findByAppUser_UuidAndCategoryNameIgnoreCase(user.getUuid(), categoryDTO.getCategoryName()).orElse(null);
        Category category = new Category();
        if (categoryDb == null) {
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setColor(categoryDTO.getColor());
            category.setAppUser(user);
            return ResponseEntity.created(null).body(  categoryRepository.save(category)).getBody();

        } else {
            throw new AlreadyExistException("Category already exists");
        }
    }


    public ResponseEntity<Category> updateCategories(UUID id, CategoryDTO categoryDTO, Principal principal) {
        AppUser user=appUserRepository.findByUsernameOrEmail(principal.getName(), principal.getName()).orElse(null);
        assert user != null;
        Category categoryDb = categoryRepository.findByIdAndAppUser_Uuid(id, user.getUuid()).orElse(null);

        if (categoryDb != null) {
            Category categoryNameExist=categoryRepository.findByAppUser_UuidAndCategoryNameIgnoreCase(user.getUuid(), categoryDTO.getCategoryName()).orElse(null);
            if (categoryNameExist == null && !categoryDb.getCategoryName().equals(categoryDTO.getCategoryName()) && !categoryDTO.getCategoryName().isEmpty()) {
                categoryDb.setCategoryName(categoryDTO.getCategoryName());
            }
            if (categoryNameExist == null && !categoryDb.getColor().equals(categoryDTO.getColor()) && !categoryDTO.getColor().isEmpty()) {
                categoryDb.setColor(categoryDTO.getColor());
            }
            return ResponseEntity.accepted().body(categoryRepository.save(categoryDb));
        }
        throw  new NotFoundException("Category does not exist");
    }


    public void deleteCategory(UUID id, Principal principal) {
        AppUser user=appUserRepository.findByUsernameOrEmail(principal.getName(), principal.getName()).orElse(null);
        assert user != null;
        Category categoryDb = categoryRepository.findByIdAndAppUser_Uuid(id, user.getUuid()).orElse(null);
        if (categoryDb != null) {
            categoryRepository.deleteById(id);
        } else {
            throw new NotFoundException("Category not available");
        }
    }
}
