package com.sisara.task_application.service;

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

    public List<TaskDto> listTasks(){
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
    }

    public void createTask(@RequestBody Task task){
        taskRepository.save(task);
    }
}
