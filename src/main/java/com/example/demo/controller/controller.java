package com.example.demo.controller;


import com.example.demo.domain.UpdateTaskRequest;
import com.example.demo.domain.dto.CreateTaskRequestDto;
import com.example.demo.domain.dto.TaskDto;
import com.example.demo.domain.entity.Task;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/tasks")
public class controller {

    private final TaskService taskService;


    public controller(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
    }

    @PostMapping()
    public ResponseEntity<TaskDto> createNew(@Valid @RequestBody CreateTaskRequestDto request){
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Task>> allGet(){
        return new ResponseEntity<>(taskService.getAll(),HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable UUID id){
        return new ResponseEntity<>(taskService.getById(id), HttpStatus.FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDto> patch(@PathVariable UUID id, @RequestBody UpdateTaskRequest req){
        return new ResponseEntity<>(taskService.update(id,req), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
