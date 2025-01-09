package com.sisara.task_application.controller;

import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.sisara.task_application.dto.TaskDto;
import com.sisara.task_application.model.Task;
import com.sisara.task_application.service.TaskService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(){
        List<TaskDto> tasks = taskService.listTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/add")
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto){
        TaskDto savedTask = taskService.createTask(taskDto);
        return new ResponseEntity<>(savedTask,HttpStatus.OK);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDto> getTasksbyId(@PathVariable("id") Long id) {
        TaskDto taskDto = taskService.getTaskbyId(id);
        return new ResponseEntity<>(taskDto,HttpStatus.OK);
    }

      @PutMapping("/update/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable("id") Long id,TaskDto taskDto){
        TaskDto updateTask = taskService.updateTask(id, taskDto);
        return new ResponseEntity<>(updateTask,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TaskDto> deleteTaskbyId(@PathVariable("id") Long id){
        TaskDto taskDto = taskService.deleteTask(id);

        return new ResponseEntity<>(taskDto,HttpStatus.OK);
    }
}
