package com.example.demo.service.impl;

import com.example.demo.domain.CreateTaskRequest;
import com.example.demo.domain.entity.Task;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(CreateTaskRequest request){
        Instant now = Instant.now();

        Task newTask = Task.builder()
                .created(now)
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .status(request.status())
                .priority(request.priority())
                .updated(now)
                .build();

        taskRepository.save(newTask);
        return newTask;
    }

}
