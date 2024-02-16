package com.micosoft.simpletaskapp.category;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDTO {
    private UUID id;
    private String categoryName;
    private String color;

    public CategoryDTO(String categoryName, String color) {
        this.categoryName = categoryName;
        this.color = color;
    }
}
