package com.example.demo.domain.dto;

import com.example.demo.domain.entity.TaskPriority;
import com.example.demo.domain.entity.TaskStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;


@Builder
public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDate dueDate,
        TaskStatus status,
        TaskPriority priority
) {
}
