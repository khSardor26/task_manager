package com.example.demo.domain;

import com.example.demo.domain.entity.TaskPriority;
import com.example.demo.domain.entity.TaskStatus;

import java.time.LocalDate;

public record UpdateTaskRequest(
        String title,
        String description,
        LocalDate dueDate,
        TaskStatus status,
        TaskPriority priority
) {
}
