package com.example.demo.domain.dto;

import com.example.demo.domain.entity.TaskPriority;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



import java.time.LocalDate;

public record CreateTaskRequestDto(

        @NotBlank(message = ERROR_MESSAGE)
//        @Length(max = 255, message = ERROR_MESSAGE)
        String title,

        @Nullable
        String description,

        @NotNull
        @FutureOrPresent(message = ERROR_MESSAGE_DUE_DATE)
        LocalDate dueDate,

        @NotNull
        TaskPriority priority

) {

        private static final String ERROR_MESSAGE = "Length must be between 1 and 255";
        private static final String ERROR_MESSAGE_DUE_DATE = "Due Date can not be in the past";


}
