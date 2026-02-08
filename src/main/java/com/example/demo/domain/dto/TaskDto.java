package com.example.demo.domain.dto;

import com.example.demo.domain.entity.TaskPriority;
import com.example.demo.domain.entity.TaskStatus;
import lombok.Builder;

import java.time.LocalDate;


@Builder
public record TaskDto(
        String title,
        String description,
        LocalDate dueDate,
        TaskStatus status,
        TaskPriority priority
) {
}
