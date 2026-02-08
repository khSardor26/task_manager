package com.example.demo.service.impl;

import com.example.demo.domain.UpdateTaskRequest;
import com.example.demo.domain.dto.CreateTaskRequestDto;
import com.example.demo.domain.dto.TaskDto;
import com.example.demo.domain.entity.Task;
import com.example.demo.domain.entity.TaskStatus;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }


    public TaskDto createTask(CreateTaskRequestDto request){
        Instant now = Instant.now();

        Task newTask = Task.builder()
                .created(now)
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .status(TaskStatus.OPEN)
                .priority(request.priority())
                .updated(now)
                .build();

        taskRepository.save(newTask);

        return taskMapper.toDto(newTask);
    }

    public TaskDto getById(UUID id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No task found with id: " + id));

        return taskMapper.toDto(task);
    }

    public List<Task> getAll(){
        return taskRepository.findAll(Sort.by(ASC, "created"));
    }


    public TaskDto update(UUID id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No task found with id: " + id));

        if (request.title() != null) task.setTitle(request.title());
        if (request.description() != null) task.setDescription(request.description());
        if (request.dueDate() != null) task.setDueDate(request.dueDate());
        if (request.priority() != null) task.setPriority(request.priority());
        if (request.status() != null) task.setStatus(request.status());

        return taskMapper.toDto(task);
    }


    public void delete(UUID id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No task found with id: " + id));
        taskRepository.deleteById(id);
    }



}
