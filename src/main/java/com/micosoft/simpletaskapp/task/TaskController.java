package com.micosoft.simpletaskapp.task;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "https://localhost:5173")
@RestController
@RequestMapping("/category/{categoryId}/task")
@AllArgsConstructor
public class TaskController {
    @Autowired
    private final TaskService taskService;

    @GetMapping
    public List<TaskDTO>gettingAllTask(@PathVariable("categoryId") UUID categoryId){
        return taskService.getAllTasks(categoryId);
    }
    @PostMapping
    public void createTask(@PathVariable("categoryId") UUID categoryId, @RequestBody TaskDTO taskDTO){
        taskService.createTask(categoryId, taskDTO);
    }
    @PutMapping("{taskId}")
    public void updateTask(@PathVariable("taskId") UUID taskId, @RequestBody TaskDTO taskDTO){
        taskService.updateTask(taskId, taskDTO);
    }
    @DeleteMapping("{taskId}")
    public void  deleteTask(@PathVariable("taskId") UUID taskId){
        taskService.deleteTask(taskId);
    }
}
