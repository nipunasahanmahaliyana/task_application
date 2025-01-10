package com.sisara.task_application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import static org.mockito.BDDMockito.willDoNothing;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    private Task task;
    private TaskDto taskDto;

    @BeforeEach
    public void setup() {
        task = new Task();
        task.setId(1);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(Status.PENDING);

        taskDto = new TaskDto();
        taskDto.setId(1);
        taskDto.setTitle("Test Task");
        taskDto.setDescription("Test Description");
        taskDto.setStatus(Status.PENDING);
    }

    @DisplayName("JUnit test for listTasks method")
    @Test
    public void givenTasksList_whenListTasks_thenReturnTaskDtoList() {
 
        List<Task> tasks = List.of(task);
        given(taskRepository.findAll()).willReturn(tasks);
        given(modelMapper.map(task, TaskDto.class)).willReturn(taskDto);
        List<TaskDto> taskDtos = taskService.listTasks();
        assertThat(taskDtos).isNotNull();
        assertThat(taskDtos.size()).isEqualTo(1);
        assertThat(taskDtos.get(0).getTitle()).isEqualTo("Test Task");
    }

    @DisplayName("JUnit test for createTask method")
    @Test
    public void givenTaskDto_whenCreateTask_thenReturnSavedTaskDto() {
   
        given(modelMapper.map(taskDto, Task.class)).willReturn(task);
        given(taskRepository.save(task)).willReturn(task);
        given(modelMapper.map(task, TaskDto.class)).willReturn(taskDto);
        TaskDto savedTaskDto = taskService.createTask(taskDto);
        assertThat(savedTaskDto).isNotNull();
        assertThat(savedTaskDto.getId()).isEqualTo(1);
    }

    @DisplayName("JUnit test for getTaskById method")
    @Test
    public void givenTaskId_whenGetTaskById_thenReturnTaskDto() {

        given(taskRepository.findById(1)).willReturn(Optional.of(task));
        given(modelMapper.map(task, TaskDto.class)).willReturn(taskDto);

        TaskDto foundTaskDto = taskService.getTaskbyId(1);
    
        // Then
        assertThat(foundTaskDto).isNotNull();
        assertThat(foundTaskDto.getId()).isEqualTo(1);
    }

    @DisplayName("JUnit test for updateTask method")
@Test
public void givenTaskIdAndTaskDto_whenUpdateTask_thenReturnUpdatedTaskDto() {
    // Given
    given(taskRepository.findById(1)).willReturn(Optional.of(task));
    given(taskRepository.save(task)).willReturn(task);
    given(modelMapper.map(task, TaskDto.class)).willReturn(taskDto);

    // When
    TaskDto updatedTaskDto = taskService.updateTask(1, taskDto);

    // Then
    assertThat(updatedTaskDto).isNotNull();
    assertThat(updatedTaskDto.getId()).isEqualTo(1L);
}

@DisplayName("JUnit test for deleteTask method")
@Test
public void givenTaskId_whenDeleteTask_thenReturnDeletedTaskDto() {
    // Given
    given(taskRepository.findById(1)).willReturn(Optional.of(task));
    willDoNothing().given(taskRepository).deleteById(1);
    given(modelMapper.map(task, TaskDto.class)).willReturn(taskDto);

    // When
    TaskDto deletedTaskDto = taskService.deleteTask(1);

    // Then
    assertThat(deletedTaskDto).isNotNull();
    assertThat(deletedTaskDto.getId()).isEqualTo(1L);
    verify(taskRepository, times(1)).deleteById(1);
}


@DisplayName("JUnit test for findTaskStatus method")
@Test
public void givenStatus_whenFindTaskStatus_thenReturnTaskDtoList() {
    // Given
    List<Task> tasks = List.of(task);
    given(taskRepository.findTaskByStatus(Status.PENDING)).willReturn(tasks);
    given(modelMapper.map(task, TaskDto.class)).willReturn(taskDto);
    List<TaskDto> taskDtos = taskService.findTaskStatus("PENDING");
    assertThat(taskDtos).isNotNull();
    assertThat(taskDtos.size()).isEqualTo(1);
    assertThat(taskDtos.get(0).getStatus()).isEqualTo(Status.PENDING);
}


@DisplayName("JUnit test for listTasksPage method")
@Test
public void givenPageable_whenListTasksPage_thenReturnTaskDtoPage() {

    Pageable pageable = PageRequest.of(0, 10);
    Page<Task> taskPage = new PageImpl<>(List.of(task), pageable, 1);
    given(taskRepository.findAll(pageable)).willReturn(taskPage);
    given(modelMapper.map(task, TaskDto.class)).willReturn(taskDto);
    Page<TaskDto> taskDtoPage = taskService.listTasksPage(pageable);
    assertThat(taskDtoPage).isNotNull();
    assertThat(taskDtoPage.getTotalElements()).isEqualTo(1);
    assertThat(taskDtoPage.getContent().get(0).getTitle()).isEqualTo("Test Task");
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
