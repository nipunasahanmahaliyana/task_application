package com.sisara.task_application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sisara.task_application.model.Task;
import com.sisara.task_application.model.Task.Status;
public interface TaskRepository extends JpaRepository<Task,Integer> {

    @Query(value = "SELECT * FROM Task WHERE status = ?1", nativeQuery = true)
    List<Task> findTaskByStatus(Status status);

}
