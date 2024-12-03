package com.example.projectmanagement.service;

import com.example.projectmanagement.model.Project;
import com.example.projectmanagement.model.Task;
import com.example.projectmanagement.model.TaskStatus;
import com.example.projectmanagement.dto.ProjectSummaryDTO;
import com.example.projectmanagement.repository.ProjectRepository;
import com.example.projectmanagement.repository.TaskRepository;
import com.example.projectmanagement.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProject(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }

    public Task addTask(Task task) {
        getProject(task.getProjectId()); // Verify project exists
        return taskRepository.save(task);
    }

    public Page<Task> getProjectTasks(UUID projectId, TaskStatus status, LocalDate dueBefore, Pageable pageable) {
        if (status != null) {
            return taskRepository.findByProjectIdAndStatus(projectId, status, pageable);
        } else if (dueBefore != null) {
            return taskRepository.findByProjectIdAndDueDateBefore(projectId, dueBefore, pageable);
        }
        return taskRepository.findByProjectId(projectId, pageable);
    }

    public List<ProjectSummaryDTO> getProjectsSummary() {
        List<Project> projects = getAllProjects();
        return projects.stream().map(project -> {
            ProjectSummaryDTO summary = new ProjectSummaryDTO();
            summary.setId(project.getId());
            summary.setName(project.getName());

            Page<Task> tasks = getProjectTasks(project.getId(), null, null, Pageable.unpaged());
            Map<TaskStatus, Long> taskCountByStatus = tasks.getContent().stream()
                    .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));

            summary.setTaskCountByStatus(taskCountByStatus);
            return summary;
        }).collect(Collectors.toList());
    }
}