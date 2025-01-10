package com.sisara.task_application.dto;

import java.time.LocalDateTime;
   
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskStatusDto {
        private int id;
        private String title;
        private String description;
        private FilterStatus status;
        private LocalDateTime createdAt;
    
        public enum FilterStatus {
            PENDING,
            IN_PROGRESS,
            COMPLETED
        }
    
}
