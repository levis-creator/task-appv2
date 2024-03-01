package com.micosoft.simpletaskapp.category;

import com.micosoft.simpletaskapp.errors.exceptions.AlreadyExistException;
import com.micosoft.simpletaskapp.errors.exceptions.NotFoundException;
import com.micosoft.simpletaskapp.user.AppRole;
import com.micosoft.simpletaskapp.user.AppUser;
import com.micosoft.simpletaskapp.user.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    AppUserRepository appUserRepository;

    @InjectMocks
    CategoryService categoryService;
    Principal mockPrincipal = Mockito.mock(Principal.class);



    AppUser appUser=new AppUser(UUID.randomUUID(),"mock@email.com", null, "mockUser", AppRole.USER);

    List<Category> categories = new ArrayList<>(
            List.of(
                    new Category(UUID.randomUUID(), "Home", "#fff", appUser),
                    new Category(UUID.randomUUID(), "Grocery", "#fff", appUser),
                    new Category(UUID.randomUUID(), "School", "#fff", appUser)
            )
    );

    Category singleCategory = new Category(UUID.randomUUID(), "School", "#fff");
    CategoryDTO repeatCategory = new CategoryDTO(singleCategory.getId(), "School", "#fff");
    CategoryDTO categoryDTO = new CategoryDTO(UUID.randomUUID(), "School Exam", "#fff88");
    Principal principal;
    @BeforeEach
    void setUp() {
    }

    @Test
    void gettingAllCategoriesWhenUserNotNull() {
        Mockito.when(mockPrincipal.getName()).thenReturn("mockUser");
        when(appUserRepository.findByUsernameOrEmail(mockPrincipal.getName(), mockPrincipal.getName())).thenReturn(Optional.of(appUser));
        when(categoryRepository.findByAppUser_Uuid(appUser.getUuid())).thenReturn(categories);
        categoryService.getAllCategories(mockPrincipal);
        verify(categoryRepository).findByAppUser_Uuid(any());
    }
    @Test
    void gettinAllCategoryWhenUserisNull() {
        Mockito.when(mockPrincipal.getName()).thenReturn("mockUser");
        when(appUserRepository.findByUsernameOrEmail(mockPrincipal.getName(), mockPrincipal.getName())).thenReturn(Optional.empty());
        assertNull(categoryService.getAllCategories(mockPrincipal));
        verify(categoryRepository, never()).findByAppUser_Uuid(any());
    }


    @Test
    void gettingSingleCategory_WhenCategoryExists() {
        Mockito.when(mockPrincipal.getName()).thenReturn("mockUser");
        when(appUserRepository.findByUsernameOrEmail(mockPrincipal.getName(), mockPrincipal.getName())).thenReturn(Optional.of(appUser));

        when(categoryRepository.findByIdAndAppUser_Uuid(
                singleCategory.getId(),
                appUser.getUuid())).thenReturn(Optional.of(singleCategory));
        categoryService.getCategory(singleCategory.getId(), mockPrincipal);
    }

    @Test
    void gettingSingleCategory_WhenCategoryDoesNotExists() {
        Mockito.when(mockPrincipal.getName()).thenReturn("mockUser");
        when(appUserRepository.findByUsernameOrEmail(mockPrincipal.getName(), mockPrincipal.getName())).thenReturn(Optional.of(appUser));
        when(categoryRepository.findByIdAndAppUser_Uuid(
                singleCategory.getId(),
                appUser.getUuid())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()->categoryService.getCategory(singleCategory.getId(), mockPrincipal));

    }

    @Test
    void creatingCategory_WhenNameDoesNotExist() {
        Mockito.when(mockPrincipal.getName()).thenReturn("mockUser");
        when(appUserRepository.findByUsernameOrEmail(mockPrincipal.getName(), mockPrincipal.getName())).thenReturn(Optional.of(appUser));
        when(categoryRepository.findByAppUser_UuidAndCategoryNameIgnoreCase(appUser.getUuid(), categoryDTO.getCategoryName())).thenReturn(Optional.empty());
        categoryService.createCategory(mockPrincipal, categoryDTO);
        verify(categoryRepository).save(any());
    }

    @Test
    void creatingCategory_WhenNameExist() {
        Mockito.when(mockPrincipal.getName()).thenReturn("mockUser");
        when(appUserRepository.findByUsernameOrEmail(mockPrincipal.getName(), mockPrincipal.getName())).thenReturn(Optional.of(appUser));
        when(categoryRepository.findByAppUser_UuidAndCategoryNameIgnoreCase(appUser.getUuid(),categoryDTO.getCategoryName())).thenReturn(Optional.of(singleCategory));
        assertThrows(AlreadyExistException.class, () -> categoryService.createCategory(mockPrincipal, categoryDTO));
    }

    @Test
    void updateCategory_WhenCategoryExist() {
        Mockito.when(mockPrincipal.getName()).thenReturn("mockUser");
        when(appUserRepository.findByUsernameOrEmail(mockPrincipal.getName(), mockPrincipal.getName())).thenReturn(Optional.of(appUser));
        when(categoryRepository.findByIdAndAppUser_Uuid(singleCategory.getId(),appUser.getUuid())).thenReturn(Optional.of(singleCategory));
        when(categoryRepository.findByAppUser_UuidAndCategoryNameIgnoreCase(appUser.getUuid(), categoryDTO.getCategoryName())).thenReturn(Optional.empty());
        categoryService.updateCategories(singleCategory.getId(), categoryDTO, mockPrincipal);
        verify(categoryRepository).save(any());
    }

    @Test
    void updateCategory_WhenCategoryDoesNotExist() {
        Mockito.when(mockPrincipal.getName()).thenReturn("mockUser");
        when(appUserRepository.findByUsernameOrEmail(mockPrincipal.getName(), mockPrincipal.getName())).thenReturn(Optional.of(appUser));
        when(categoryRepository.findByIdAndAppUser_Uuid(singleCategory.getId(),appUser.getUuid())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()-> categoryService.updateCategories(singleCategory.getId(), categoryDTO, mockPrincipal));
        verify(categoryRepository, never()).save(any());
    }


    @Test
    void deleteCategory_WhenCategoryExist() {
        Mockito.when(mockPrincipal.getName()).thenReturn("mockUser");
        when(appUserRepository.findByUsernameOrEmail(mockPrincipal.getName(), mockPrincipal.getName())).thenReturn(Optional.of(appUser));
        when(categoryRepository.findByIdAndAppUser_Uuid(
                singleCategory.getId(),
                appUser.getUuid())).thenReturn(Optional.of(singleCategory));
        categoryService.deleteCategory(singleCategory.getId(), mockPrincipal);
        verify(categoryRepository).deleteById(singleCategory.getId());
    }
    @Test
    void deletingCategory_WhenCategoryDoesNotExist(){
        Mockito.when(mockPrincipal.getName()).thenReturn("mockUser");
        when(appUserRepository.findByUsernameOrEmail(mockPrincipal.getName(), mockPrincipal.getName())).thenReturn(Optional.of(appUser));
        when(categoryRepository.findByIdAndAppUser_Uuid(
                singleCategory.getId(),
                appUser.getUuid())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, ()->categoryService.deleteCategory(singleCategory.getId(), mockPrincipal) );
        verify(categoryRepository, never()).deleteById(singleCategory.getId());
    }

}