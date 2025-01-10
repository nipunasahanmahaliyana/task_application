package com.sisara.task_application;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sisara.task_application.dto.TaskDto;
import com.sisara.task_application.dto.TaskDto.StatusDto;
import com.sisara.task_application.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
public class TaskControllerIntegrationTest {
    
  @Autowired
    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskDto taskDto;

    @BeforeEach
    public void setup() {
        taskDto = new TaskDto(1, "Test Task", "Description", StatusDto.PENDING,LocalDateTime.now());
    }
    
        @Test
    public void testGetAllTasks() throws Exception {
        when(taskService.listTasks()).thenReturn(List.of(taskDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("Test Task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Tasks fetched successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }
    @Test
    public void testGetTaskById() throws Exception {
        when(taskService.getTaskbyId(1)).thenReturn(taskDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/task/{id}", 1))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Test Task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Task found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

     @Test
    public void testCreateTask() throws Exception {
        when(taskService.createTask(Mockito.any(TaskDto.class))).thenReturn(taskDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Test Task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Task Added"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    @Test
    public void testUpdateTask() throws Exception {
        TaskDto updatedTask = new TaskDto(1, "Updated Task", "Updated Description", StatusDto.COMPLETED,LocalDateTime.now());
        when(taskService.updateTask(1, updatedTask)).thenReturn(updatedTask);

        mockMvc.perform(MockMvcRequestBuilders.put("/update/{id}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedTask)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Updated Task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Task updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    @Test
    public void testDeleteTask() throws Exception {
        when(taskService.getTaskbyId(1)).thenReturn(taskDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/{id}", 1))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Task Deleted"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));

        verify(taskService).deleteTask(1);
    }

    @Test
    public void testGetByStatus() throws Exception {
        when(taskService.findTaskStatus("PENDING")).thenReturn(List.of(taskDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/{status}", "PENDING"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Tasks found for status: PENDING"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    @Test
    public void testPaginationAndSorting() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tasksPage")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "title")
                        .param("sortDir", "asc"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Tasks fetched successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

}
