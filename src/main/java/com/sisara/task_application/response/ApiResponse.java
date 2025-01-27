package com.sisara.task_application.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T task;
    private List<T> tasks;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, T task) {
        this.success = success;
        this.message = message;
        this.task = task;
    }

    public ApiResponse(boolean success, String message, List<T> tasks) {
        this.success = success;
        this.message = message;
        this.tasks = tasks;
    }

}
