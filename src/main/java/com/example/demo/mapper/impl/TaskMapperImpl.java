package com.example.demo.mapper.impl;

import com.example.demo.domain.CreateTaskRequest;
import com.example.demo.domain.dto.CreateTaskRequestDto;
import com.example.demo.domain.dto.TaskDto;
import com.example.demo.domain.entity.Task;
import com.example.demo.mapper.TaskMapper;
import org.springframework.stereotype.Component;


@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public CreateTaskRequest fromDto(CreateTaskRequestDto requestDto) {
        return CreateTaskRequest.builder()
                .title(requestDto.title())
                .description(requestDto.description())
                .dueDate(requestDto.dueDate())
                .priority(requestDto.priority())
                .build();
    }

    @Override
    public TaskDto toDto(Task request) {
        return TaskDto.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .status(request.getStatus())
                .priority(request.getPriority())
                .build();
    }
}
