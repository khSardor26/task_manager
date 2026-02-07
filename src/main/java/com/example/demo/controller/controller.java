package com.example.demo.controller;


import com.example.demo.domain.CreateTaskRequest;
import com.example.demo.domain.entity.Task;
import com.example.demo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class controller {

    private final TaskService taskService;

    public controller(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/users")
    public ResponseEntity<Task> createNew(CreateTaskRequest request){
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.OK);

    }


}
