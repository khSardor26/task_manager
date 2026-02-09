package com.example.demo.mapper;

import com.example.demo.domain.CreateTaskRequest;
import com.example.demo.domain.dto.CreateTaskRequestDto;
import com.example.demo.domain.dto.TaskDto;
import com.example.demo.domain.entity.Task;

public interface TaskMapper {

    //CreateTaskRequest fromDto(CreateTaskRequestDto requestDto);

    TaskDto toDto(Task request);

}
