package com.example.projectmanagement.repository;

import com.example.projectmanagement.model.Task;
import com.example.projectmanagement.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    Page<Task> findByProjectId(UUID projectId, Pageable pageable);
    Page<Task> findByProjectIdAndStatus(UUID projectId, TaskStatus status, Pageable pageable);
    Page<Task> findByProjectIdAndDueDateBefore(UUID projectId, LocalDate dueDate, Pageable pageable);
}