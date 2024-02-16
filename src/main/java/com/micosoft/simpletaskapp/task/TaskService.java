package com.micosoft.simpletaskapp.task;

import com.micosoft.simpletaskapp.category.Category;
import com.micosoft.simpletaskapp.category.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor

@Service
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;
    @Autowired
    private final CategoryRepository categoryRepository;

    public List<TaskDTO> getAllTasks(UUID categoryId) {
        List<Task> tasksDb = taskRepository.findByCategory_Id(categoryId);
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task task : tasksDb) {
            TaskDTO taskDTO = new TaskDTO(task.getId(), task.getTaskName(), task.isCompleted());
            taskDTOList.add(taskDTO);
        }
        return taskDTOList;
    }

    public void createTask(UUID categoryId, TaskDTO taskDTO) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        Task task = new Task(taskDTO.getTaskName(), taskDTO.isCompleted(), category);
        assert category != null;
        category.getTasks().add(task);
        categoryRepository.save(category);
        task.setCategory(category);
        taskRepository.save(task);
    }

    public void updateTask(UUID taskId, TaskDTO taskDTO) {
        Task taskDb = taskRepository.findById(taskId).orElse(null);
        if (taskDb != null) {
            if (!taskDTO.getTaskName().equals(taskDb.getTaskName()) && !taskDTO.getTaskName().isEmpty()) {
                taskDb.setTaskName(taskDTO.getTaskName());
            }
            if (taskDb.isCompleted() != taskDTO.isCompleted()) {
                taskDb.setCompleted(taskDTO.isCompleted());
            }
            taskRepository.save(taskDb);
        } else {
            throw new IllegalStateException("Task does not exist");
        }
    }

    public void deleteTask(UUID taskId) {
        Task taskDb = taskRepository.findById(taskId).orElse(null);
        assert taskDb != null;
        taskDb.setCategory(null);
        taskRepository.save(taskDb);
        taskRepository.deleteById(taskId);
    }

}
