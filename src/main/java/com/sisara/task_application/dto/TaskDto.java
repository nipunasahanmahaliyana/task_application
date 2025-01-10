package com.sisara.task_application.dto;

import java.time.LocalDateTime;

import com.sisara.task_application.model.Task.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDto {

    private int id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime createdAt;



}
