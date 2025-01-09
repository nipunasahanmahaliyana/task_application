package com.sisara.task_application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.sisara.task_application.dto.TaskDto;
import com.sisara.task_application.model.Task;
import com.sisara.task_application.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public List<TaskDto> getAllTasks(){
        List<TaskDto> tasks = taskService.listTasks();
        return tasks;
    }

    @PostMapping("/add")
         public String addTask(@RequestBody Task task){
        taskService.createTask(task);
        return ("Created");
    }
}
