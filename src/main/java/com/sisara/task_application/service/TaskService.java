package com.sisara.task_application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sisara.task_application.dto.TaskDto;
import com.sisara.task_application.exception.TaskNotExistException;
import com.sisara.task_application.model.Task;
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

    public TaskDto getTaskbyId(Long id){
        if (!taskRepository.existsById(id))
            throw new TaskNotExistException("Task id is invalid " + id);

        Optional<Task> optionalTask = taskRepository.findById(id);
        return (modelMapper.map(optionalTask.get() ,TaskDto.class));
    }

    public  TaskDto updateTask(Long id,TaskDto taskDto){

        Task existingTask = taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotExistException("Task not found with id: " + id));

        modelMapper.map(taskDto, existingTask);
        Task updatedTask = taskRepository.save(existingTask);
        return modelMapper.map(updatedTask, TaskDto.class);
    }

    public  TaskDto deleteTask(Long id){
        if(!taskRepository.existsById(id))
            throw new TaskNotExistException("Task is not found"+id);
        
        Optional<Task> optionalTask = taskRepository.findById(id);
        taskRepository.deleteById(id);
        return (modelMapper.map(optionalTask.get() ,TaskDto.class));
    }
}
