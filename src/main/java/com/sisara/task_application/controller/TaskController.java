package com.sisara.task_application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.sisara.task_application.dto.TaskDto;
import com.sisara.task_application.response.ApiResponse;
import com.sisara.task_application.service.TaskService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse<TaskDto>> getAllTasks(){
        List<TaskDto> tasks = taskService.listTasks();
        return new ResponseEntity<ApiResponse<TaskDto>>(
        new ApiResponse<TaskDto>(true, "Tasks Fetched",tasks),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<TaskDto>> addTask(@RequestBody TaskDto taskDto){
        TaskDto savedTask = taskService.createTask(taskDto);
        return new ResponseEntity<ApiResponse<TaskDto>>(
        new ApiResponse<TaskDto>(true, "Task Added",savedTask),HttpStatus.OK);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> getTasksbyId(@PathVariable("id") Long id) {
        TaskDto taskDto = taskService.getTaskbyId(id);
        return new ResponseEntity<ApiResponse<TaskDto>>(
        new ApiResponse<TaskDto>(true, "Task found",taskDto),HttpStatus.OK);
    }

      @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> updateTask(@PathVariable("id") Long id,TaskDto taskDto){
        TaskDto updateTask = taskService.updateTask(id, taskDto);
        return new ResponseEntity<ApiResponse<TaskDto>>(
        new ApiResponse<TaskDto>(true, "Task updated",updateTask),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> deleteTaskbyId(@PathVariable("id") Long id){
        TaskDto taskDto = taskService.deleteTask(id);
        return new ResponseEntity<ApiResponse<TaskDto>>(
        new ApiResponse<TaskDto>(true, "Task Deleted ",taskDto),HttpStatus.OK);
    }

    @GetMapping("getbystatus/{status}")
    public ResponseEntity<ApiResponse<TaskDto>> putMethodName(@PathVariable("status") String status) {
        
        List<TaskDto> taskDtos = taskService.findTaskStatus(status);
        return new ResponseEntity<ApiResponse<TaskDto>>(
        new ApiResponse<TaskDto>(true, "Task found for Status",taskDtos),HttpStatus.OK);
    }
}
