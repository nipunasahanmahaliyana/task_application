package com.sisara.task_application.exception;

public class TaskInvalidStatusException extends RuntimeException {
    public TaskInvalidStatusException (String message) {
        super(message);
    }
}
