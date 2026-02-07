package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;



public record CreateTaskRequest(
        String title,
        String description,
        LocalDate dueDate,
        TaskStatus status,
        TaskPriority priority
) {

}
