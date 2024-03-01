package com.micosoft.simpletaskapp.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findByCategoryNameIgnoreCase(String categoryName);


    Optional<Category> findByIdAndAppUser_Uuid(UUID id, UUID uuid);

    List<Category> findByAppUser_Uuid(UUID uuid);

    Optional<Category> findByAppUser_UuidAndCategoryNameIgnoreCase(UUID uuid, String categoryName);

}