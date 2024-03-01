package com.micosoft.simpletaskapp.task;

import com.micosoft.simpletaskapp.category.Category;
import com.micosoft.simpletaskapp.category.CategoryRepository;
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
class TaskServiceTest {
    TaskDTO taskDTO;
    Category category = new Category(UUID.randomUUID(), "Home", "#fff");
    Task task;
    List<Task> tasks = new ArrayList<>(
            List.of(
                    new Task("Clean House", false, category),
                    new Task("Clean utensil", false, category),
                    new Task("get kids ", false, category)
            )
    );
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskDTO = new TaskDTO("Clean House", true);
        task = new Task("get kids ", false, category);
    }

    @Test
    void gettingAllTask() {
        when(taskRepository.findByCategory_Id(category.getId())).thenReturn(tasks);
        taskService.getAllTasks(category.getId());
        verify(taskRepository).findByCategory_Id(category.getId());
    }

    @Test
    void creatingTask() {
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        taskService.createTask(category.getId(), taskDTO);
        verify(categoryRepository).save(any());
        verify(taskRepository).save(any());
    }

    @Test
    void updatingTask_WhenNotAvailable() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> taskService.updateTask(task.getId(), taskDTO));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void updatingTask_WhenAvailable() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        taskService.updateTask(task.getId(), taskDTO);
        verify(taskRepository).save(any());
    }

    @Test
    void deleteTask_WhenExist() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        taskService.deleteTask(task.getId());
        verify(taskRepository).save(any());
        verify(taskRepository).deleteById(task.getId());
    }
}