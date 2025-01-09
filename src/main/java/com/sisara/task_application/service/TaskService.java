package com.sisara.task_application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sisara.task_application.model.Task;
import com.sisara.task_application.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> listTasks(){
        List<Task> tasks = taskRepository.findAll();
        return tasks;
    }

    public void createTask(@RequestBody Task task){
        taskRepository.save(task);
    }
}
