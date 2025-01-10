package com.sisara.task_application.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sisara.task_application.dto.TaskDto;
import com.sisara.task_application.model.Task;
import com.sisara.task_application.model.Task.Status;
import com.sisara.task_application.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TaskDto> listTasks(){
        return taskRepository.findAll()
        .stream()
        .map(task -> modelMapper.map(task, TaskDto.class))
        .collect(Collectors.toList());
    }

    public TaskDto createTask(@RequestBody TaskDto taskDto){
        Task task = modelMapper.map(taskDto, Task.class);
        Task savedTask = taskRepository.save(task);

        return modelMapper.map(savedTask, TaskDto.class);
    }

    public TaskDto getTaskbyId(int id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(task -> modelMapper.map(task, TaskDto.class))
        .orElse(null);  
    }

    public  TaskDto updateTask(Integer id,TaskDto taskDto){
        Optional<Task> optionalTask = taskRepository.findById(id);
        modelMapper.map(taskDto, optionalTask);
        Task updatedTask = taskRepository.save(optionalTask.get());
        return modelMapper.map(updatedTask, TaskDto.class);
    }

    public  TaskDto deleteTask(Integer id){
    
        Optional<Task> optionalTask = taskRepository.findById(id);
        taskRepository.deleteById(id);
        return (modelMapper.map(optionalTask.get() ,TaskDto.class));
    }

    public List<TaskDto> findTaskStatus(String status) {
        List<Task> tasks;

        Status statusEnum = Status.valueOf(status.toUpperCase());
        tasks = taskRepository.findTaskByStatus(statusEnum); 
        
        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptyList(); 
        }

        return tasks.stream()
                    .map(task -> modelMapper.map(task, TaskDto.class))
                    .collect(Collectors.toList());
    }
    
    public Page<TaskDto> listTasksPage(Pageable pageable) {
        return taskRepository.findAll(pageable).map(task -> modelMapper.map(task, TaskDto.class));
    }
    
}
