package com.sisara.task_application.exception;

public class DataIntegrityViolationException extends RuntimeException{
    public DataIntegrityViolationException(String message) {
        super(message);
    }
}
