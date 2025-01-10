package com.sisara.task_application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.sisara.task_application.dto.TaskDto;
import com.sisara.task_application.exception.BadRequestException;
import com.sisara.task_application.exception.DataIntegrityViolationException;
import com.sisara.task_application.exception.ResourceNotFoundException;
import com.sisara.task_application.response.ApiResponse;
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

            if (taskDto == null || taskDto.getTitle() == null || taskDto.getTitle().isEmpty() || taskDto.getDescription() == null || taskDto.getDescription().isEmpty() || taskDto.getStatus() == null){
                throw new BadRequestException("Task details cannot be empty.");
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
    
        } 
    }
    

    @GetMapping("/task/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> getTasksbyId(@PathVariable("id") Integer id) {
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
    public ResponseEntity<ApiResponse<TaskDto>> updateTask(@PathVariable("id") Integer id,TaskDto taskDto){
        try {
            TaskDto taskDtoValid = taskService.getTaskbyId(id);

            if (taskDtoValid == null) {
                throw new ResourceNotFoundException("Task with ID " + id + " not found.");
            }
    
            TaskDto updatedTask = taskService.updateTask(id, taskDto);
    
            if (updatedTask == null) {
                throw new BadRequestException("Task is invalid,put a valid one");
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
    public ResponseEntity<ApiResponse<TaskDto>> deleteTaskbyId(@PathVariable("id") Integer id){
        try {
            TaskDto taskDtoValid = taskService.getTaskbyId(id);

            if (taskDtoValid == null) {
                throw new ResourceNotFoundException("Task with ID " + id + " not found.");
            }
            
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

    @GetMapping("tasks/{status}")
    public ResponseEntity<ApiResponse<TaskDto>> getByStatus(@PathVariable("status") String status) {
        
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

@GetMapping("/tasksPage")
public ResponseEntity<ApiResponse<Page<TaskDto>>> getAllTasks(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
        @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
    try {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(page, size, sort);

        Page<TaskDto> taskPage = taskService.listTasksPage(pageable);

        ApiResponse<Page<TaskDto>> response = new ApiResponse<>(true, "Tasks fetched successfully", taskPage);
        return new ResponseEntity<>(response, HttpStatus.OK);

    } catch (Exception ex) {

        ApiResponse<Page<TaskDto>> errorResponse = new ApiResponse<>(false, "An error occurred while fetching tasks: " + ex.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}

