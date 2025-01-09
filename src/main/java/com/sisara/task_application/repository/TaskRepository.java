package com.sisara.task_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisara.task_application.model.Task;

public interface TaskRepository extends JpaRepository<Task,Long> {

}
