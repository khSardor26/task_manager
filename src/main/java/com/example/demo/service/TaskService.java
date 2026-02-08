package com.example.demo.service;

import com.example.demo.domain.CreateTaskRequest;
import com.example.demo.domain.UpdateTaskRequest;
import com.example.demo.domain.dto.CreateTaskRequestDto;
import com.example.demo.domain.dto.TaskDto;
import com.example.demo.domain.entity.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    TaskDto createTask(CreateTaskRequestDto request);
    TaskDto getById(UUID id);
    List<Task> getAll();

    TaskDto update(UUID id, UpdateTaskRequest request);
    void delete(UUID id);



}
