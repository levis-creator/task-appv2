package com.micosoft.simpletaskapp.category;

import com.micosoft.simpletaskapp.task.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String categoryName;
    private String color;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public Category(UUID id, String categoryName, String color) {
        this.id = id;
        this.categoryName = categoryName;
        this.color = color;
    }

    public Category(String categoryName, String color) {
        this.categoryName = categoryName;
        this.color = color;
    }
}