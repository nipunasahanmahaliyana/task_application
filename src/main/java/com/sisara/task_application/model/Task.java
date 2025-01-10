package com.sisara.task_application.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private Status status;
    private LocalDateTime createdAt;

    @PrePersist
    protected void createdAt(){
        this.createdAt = LocalDateTime.now();
    }

    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }

}
