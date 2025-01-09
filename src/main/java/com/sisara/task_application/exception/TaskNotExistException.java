package com.sisara.task_application.exception;

public class TaskNotExistException extends IllegalArgumentException{
    public TaskNotExistException(String msg){
        super(msg);
    }
}
