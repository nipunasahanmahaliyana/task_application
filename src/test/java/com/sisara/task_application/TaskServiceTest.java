package com.sisara.task_application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.sisara.task_application.dto.TaskDto;
import com.sisara.task_application.model.Task;
import com.sisara.task_application.model.Task.Status;
import com.sisara.task_application.repository.TaskRepository;
import com.sisara.task_application.service.TaskService;


public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
     ModelMapper modelMapper;

    TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService();
    }

    @Test
    void testGetAllTasks_shouldReturnEmptyList_whenNoTasksFound() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());
        List<TaskDto> taskDtos = taskService.listTasks();
        assertNotNull(taskDtos);
        assertTrue(taskDtos.isEmpty(), "Expected empty list of TaskDto");
    }

    @Test
    void testGetAllTasks_shouldReturnMappedTaskDtos_whenTasksExist() {
 
        Task task = new Task(1, "Task 1", "Test",Status.PENDING,LocalDateTime.now());
        List<Task> tasks = Arrays.asList(task);
        when(taskRepository.findAll()).thenReturn(tasks);

        TaskDto taskDto = new TaskDto(1, "Task 1", "Test description", Status.PENDING, LocalDateTime.now());

        when(modelMapper.map(task, TaskDto.class)).thenReturn(taskDto);

        List<TaskDto> taskDtos = taskService.listTasks();
        assertNotNull(taskDtos);
        assertEquals(1, taskDtos.size(), "Expected 1 TaskDto");
        assertEquals("Task 1", taskDtos.get(0).getTitle(), "Expected TaskDto title to be 'Task 1'");
        assertEquals(Status.PENDING, taskDtos.get(0).getStatus(), "Expected TaskDto status to be PENDING");
    }

  @Test
    void testGetAllTasks_shouldReturnEmptyList_whenRepositoryReturnsNull() {
        when(taskRepository.findAll()).thenReturn(null);
        List<TaskDto> taskDtos = taskService.listTasks();
        assertNotNull(taskDtos);
        assertTrue(taskDtos.isEmpty(), "Expected empty list of TaskDto when repository returns null");
    }
}
