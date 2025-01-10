# Task Management Application

## Overview
This application is a Task Management system built using Spring Boot. It provides CRUD operations for tasks, supports pagination, and filters tasks by their status. The project implements clear separation of concerns with DTOs, services, repositories, and controllers. It uses an H2 in-memory database for easy setup and testing.

## Features
- **CRUD Operations**: Create, read, update, and delete tasks.
- **Pagination**: Retrieve tasks in a paginated manner.
- **Filter by Status**: Filter tasks by their status (e.g., PENDING, COMPLETED).
- **Custom API Responses**: Unified API response structure using `ApiResponse` class.
- **Exception Handling**: Custom exception classes for specific error handling.

## Prerequisites
- Java 11 or higher
- Maven
- Any Java IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code with Java extensions)

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository_url>
cd task-management-application
```

### 2. Run with H2 Database
The application uses an H2 in-memory database, so no additional setup is required. Configuration is in the `application.properties` file:
```properties
spring.application.name=task_application
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sisara_db
spring.datasource.password=
spring.h2.console.enabled=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

```

You can access the H2 database console at [http://localhost:8080/h2-console](http://localhost:8080/h2-console).

### 3. Run the Application
  ## Folder Structure
  ```
  src/main/java
  ├── com.sisara.task_application
  │   ├── configuration
  │   ├── controller
  │   ├── dto
  │   ├── exception
  │   ├── model
  │   ├── repository
  │   ├── response
  │   ├── service
  │   └── TaskApplication.java
  ```

#### Using IDE:
1. Open the project in your preferred IDE.
2. Navigate to the main class `TaskApplication.java`.
3. Run the application.

#### Using Command Line:
```bash
mvn spring-boot:run
```

The application will start on [http://localhost:8080](http://localhost:8080).

### 4. Test the APIs
You can use tools like Postman or cURL to test the API endpoints. Example endpoints:
- `GET /tasks`: Fetch all tasks. - http://localhost:8080/tasks
- `POST /add`: Add a new task. - http://localhost:8080/add
  status can only equal to  (PENDING, IN_PROGRESS, COMPLETED) ,there for i used enum for that.
  body: {
    "title": "Write Unit Tests",
    "description":"Testing",
    "status": "IN_PROGRESS"
  }

- `GET /task/{id}`: Get task by ID. - http://localhost:8080/task/2
- `PUT /update/{id}`: Update a task. - http://localhost:8080/update/2
- `DELETE /delete/{id}`: Delete a task. - http://localhost:8080/delete/2
- `GET /tasks/{status}`: Filter tasks by status. - http://localhost:8080/tasks/PENDING
- `GET /tasksPage`: Get paginated task results. - http://localhost:8080/tasksPage?page=1&size&sortBy=createdAt&sortDir=desc

## How to Test the Application
### Unit Tests
1. Navigate to the `src/test/java` directory.
2. Write test cases for the service layer using JUnit and Mockito.
3. Example:
```java
@Test
public void testGetTaskById() {
    when(taskRepository.findById(1)).thenReturn(Optional.of(new Task(1, "Test Task", "Test Description", Status.PENDING)));
    TaskDto task = taskService.getTaskById(1);
    assertEquals("Test Task", task.getTitle());
}
```

### Integration Tests
1. Use `MockMvc` to test API endpoints.
2. Example:
```java
@Test
public void testGetTasks() throws Exception {
    mockMvc.perform(get("/tasks"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.success").value(true));
}
```

### Testing with Postman
1. Import the API collection in Postman.
2. Use the base URL `http://localhost:8080` and test various endpoints.
3. Verify the response structure matches the `ApiResponse` format.

## Summary of Implementation
  - used Lombook dependency for generate Getters, setters, and constructors
### APIResponse Class
The `ApiResponse` class is a generic wrapper used to structure API responses with metadata:
```java
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    // Getters, setters, and constructors
}
```

### Exception Classes
Custom exceptions are created to handle specific scenarios:
Ex:
- `BadRequestException`: Thrown for invalid input.
- `ResourceNotFoundException`: Thrown when a resource is not found.
- `DataIntegrityViolationException`: Thrown for database constraint violations.

### Service Layer
The service layer contains business logic. The `TaskService` class handles CRUD operations, mapping entities to DTOs, and additional features like pagination and filtering.

### DTOs
DTOs (Data Transfer Objects) are used to decouple the internal model from the API representation. This improves maintainability and security by exposing only necessary fields.

### Custom Mappings
The project uses `ModelMapper` for object mapping, with custom configurations for mismatched field names between `Task` and `TaskDto`.

### Testing
- **Unit Tests**: Written using JUnit and Mockito to test service logic.
- **Integration Tests**: Using MockMvc to test API endpoints.
- Logs are saved in `logs/test.log`.

