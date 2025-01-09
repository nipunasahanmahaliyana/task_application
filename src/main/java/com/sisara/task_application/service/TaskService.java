package com.sisara.task_application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sisara.task_application.dto.TaskDto;
import com.sisara.task_application.model.Task;
import com.sisara.task_application.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    private TaskDto convertToDto(Task task){
        return modelMapper.map(task,TaskDto.class);
    }

        // Convert DTO to Entity
    private Task convertToEntity(TaskDto taskDto) {
        Task task = modelMapper.map(taskDto, Task.class);
        task.setCreatedAt(LocalDateTime.now()); // Set additional fields if necessary
        return task;
    }

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
}
