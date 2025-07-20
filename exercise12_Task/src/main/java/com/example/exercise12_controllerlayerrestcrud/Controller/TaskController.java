package com.example.exercise12_controllerlayerrestcrud.Controller;


import com.example.exercise12_controllerlayerrestcrud.Api.ApiResponse;
import com.example.exercise12_controllerlayerrestcrud.Model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    ArrayList<Task> tasks = new ArrayList<>();

    @PostMapping("/create")
    public ApiResponse createTask(@RequestBody Task task){
        tasks.add(task);
        return new ApiResponse("Task created successfully", HttpStatus.CREATED.value());
    }

    @PutMapping("/update/{index}")
    public ApiResponse updateTask(@RequestBody Task task, @PathVariable int index){
        if(index >= tasks.size()){
            return new ApiResponse("Task not found", HttpStatus.NOT_FOUND.value());
        }
        tasks.set(index, task);
        return new ApiResponse("Task updated successfully", HttpStatus.OK.value());
    }
    @DeleteMapping("/delete/{index}")
    public ApiResponse deleteTask(@PathVariable int index){
        if(index >= tasks.size()){
            return new ApiResponse("Task not found", HttpStatus.NOT_FOUND.value());
        }
        tasks.remove(index);
        return new ApiResponse("Task deleted successfully", HttpStatus.OK.value());
    }

    @PutMapping("/toggle-status/{index}")
    public ApiResponse toggleStatus(@PathVariable int index){
        if(index >= tasks.size()){
            return new ApiResponse("Task not found", HttpStatus.NOT_FOUND.value());
        }
        tasks.get(index).setStatus(!tasks.get(index).isStatus());
        return new ApiResponse("Task status updated successfully", HttpStatus.OK.value());
    }
    @GetMapping("/search/by-title/{title}")
    public Object searchTask(@PathVariable String title){
        for(Task task : tasks){
            if(task.getTitle().equalsIgnoreCase(title)){
                return task;
            }
        }
        return new ApiResponse("Task not found", HttpStatus.NOT_FOUND.value());
    }

    @GetMapping("/search/by-id/{id}")
    public Object searchTaskById(@PathVariable int id){
        for(Task task : tasks){
            if(task.getId() == id){
                return task;
            }
        }
        return new ApiResponse("Task not found", HttpStatus.NOT_FOUND.value());
    }







}
