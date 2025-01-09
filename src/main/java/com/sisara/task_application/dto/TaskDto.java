package com.sisara.task_application.dto;

import java.time.LocalDateTime;

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

    @NotNull
    private String description;
    private @NotNull Status status;
    private @NotNull LocalDateTime createdAt;

    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }

    
}
