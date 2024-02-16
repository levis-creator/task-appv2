package com.micosoft.simpletaskapp.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private UUID id;
    private String taskName;
    private boolean completed;

    public TaskDTO(String taskName, boolean completed) {
        this.taskName = taskName;
        this.completed = completed;
    }
}
