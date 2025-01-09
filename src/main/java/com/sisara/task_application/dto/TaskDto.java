package com.sisara.task_application.dto;

import java.time.LocalDateTime;

import ch.qos.logback.core.status.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDto {

    private Long id;
    private @NotNull String title;
    private @NotNull String description;
    private @NotNull Status status;
    private @NotNull LocalDateTime createdAt;

    
}
