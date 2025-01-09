package com.sisara.task_application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.sisara.task_application.dto.TaskDto;
import com.sisara.task_application.exception.BadRequestException;
import com.sisara.task_application.exception.DataIntegrityViolationException;
import com.sisara.task_application.exception.ResourceNotFoundException;
import com.sisara.task_application.model.Task;
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
         try {
        List<TaskDto> tasks = taskService.listTasks();
        
        if (tasks == null || tasks.isEmpty()) {
            throw new ResourceNotFoundException("No tasks found.");
        }
        

        ApiResponse<TaskDto> response = new ApiResponse<>(true, "Tasks fetched successfully", tasks);
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    } catch (ResourceNotFoundException ex) {

        ApiResponse<TaskDto> response = new ApiResponse<>(false, ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        
    } catch (Exception ex) {

        ApiResponse<TaskDto> response = new ApiResponse<>(false, "An unexpected error occurred", null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<TaskDto>> addTask(@RequestBody TaskDto taskDto) {
        try {
            if (taskDto == null) {
                throw new BadRequestException("Task data is required.");
            }
            
            TaskDto savedTask = taskService.createTask(taskDto);
    
            ApiResponse<TaskDto> response = new ApiResponse<>(true, "Task Added", savedTask);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    
        } catch (BadRequestException ex) {
            ApiResponse<TaskDto> response = new ApiResponse<>(false, ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    
        } catch (DataIntegrityViolationException ex) {
            ApiResponse<TaskDto> response = new ApiResponse<>(false, "Data integrity violation: " + ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    
        } catch (Exception ex) {
            ApiResponse<TaskDto> response = new ApiResponse<>(false, "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("/task/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> getTasksbyId(@PathVariable("id") Long id) {
        try {
            TaskDto taskDto = taskService.getTaskbyId(id);
    
            if (taskDto == null) {
                throw new ResourceNotFoundException("Task with ID " + id + " not found.");
            }
    
            ApiResponse<TaskDto> response = new ApiResponse<>(true, "Task found", taskDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
    
        } catch (ResourceNotFoundException ex) {

            ApiResponse<TaskDto> response = new ApiResponse<>(false, ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    
        } catch (Exception ex) {

            ApiResponse<TaskDto> response = new ApiResponse<>(false, "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

      @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> updateTask(@PathVariable("id") Long id,TaskDto taskDto){
        try {
            TaskDto updatedTask = taskService.updateTask(id, taskDto);
    
            if (updatedTask == null) {
                throw new ResourceNotFoundException("Task is invalid,put a valid one");
            }
            
            TaskDto taskDtoValid = taskService.getTaskbyId(id);

            if (taskDtoValid == null) {
                throw new ResourceNotFoundException("Task with ID " + id + " not found.");
            }
    
            ApiResponse<TaskDto> response = new ApiResponse<>(true, "Task updated", updatedTask);
            return new ResponseEntity<>(response, HttpStatus.OK);
    
        } catch (ResourceNotFoundException ex) {
            ApiResponse<TaskDto> response = new ApiResponse<>(false, ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    
        } catch (BadRequestException ex) {
            ApiResponse<TaskDto> response = new ApiResponse<>(false, ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    
        } catch (Exception ex) {
            ApiResponse<TaskDto> response = new ApiResponse<>(false, "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> deleteTaskbyId(@PathVariable("id") Long id){
        try {

            TaskDto deletedTask = taskService.deleteTask(id);

            if (deletedTask == null) {
                throw new ResourceNotFoundException("Task with ID " + id + " not found.");
            }
    
            ApiResponse<TaskDto> response = new ApiResponse<>(true, "Task Deleted", deletedTask);
            return new ResponseEntity<>(response, HttpStatus.OK);
    
        } catch (ResourceNotFoundException ex) {

            ApiResponse<TaskDto> response = new ApiResponse<>(false, ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    
        } catch (Exception ex) {
            // Handle any other unexpected errors
            ApiResponse<TaskDto> response = new ApiResponse<>(false, "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getbystatus/{status}")
    public ResponseEntity<ApiResponse<TaskDto>> putMethodName(@PathVariable("status") String status) {
        
        try {

            List<TaskDto> taskDtos = taskService.findTaskStatus(status);
    
            if (taskDtos.isEmpty()) {
                throw new ResourceNotFoundException("No tasks found with status: " + status);
            }
    
            ApiResponse<TaskDto> response = new ApiResponse<>(true, "Tasks found for status: " + status, taskDtos);
            return new ResponseEntity<>(response, HttpStatus.OK);
    
        } catch (IllegalArgumentException ex) {

            ApiResponse<TaskDto> response = new ApiResponse<>(false, "Invalid status provided", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    
        } catch (ResourceNotFoundException ex) {

            ApiResponse<TaskDto> response = new ApiResponse<>(false, ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    
        } catch (Exception ex) {

            ApiResponse<TaskDto> response = new ApiResponse<>(false, "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
