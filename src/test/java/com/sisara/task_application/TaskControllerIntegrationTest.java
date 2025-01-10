package com.sisara.task_application;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sisara.task_application.controller.TaskController;
import com.sisara.task_application.dto.TaskDto;
import com.sisara.task_application.model.Task.Status;
import com.sisara.task_application.service.TaskService;

@ExtendWith(SpringExtension.class)  // For JUnit 5 support
@SpringBootTest
public class TaskControllerIntegrationTest {
    private MockMvc mockMvc;

    @Mock
    TaskService taskService;

    @InjectMocks
     TaskController taskController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    
}
