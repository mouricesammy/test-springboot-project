package com.example.projectmanagement.controller;

import com.example.projectmanagement.model.Project;
import com.example.projectmanagement.model.Task;
import com.example.projectmanagement.dto.ProjectSummaryDTO;
import com.example.projectmanagement.model.TaskStatus;
import com.example.projectmanagement.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @Operation(summary = "Create a new project")
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        return ResponseEntity.ok(projectService.createProject(project));
    }

    @GetMapping
    @Operation(summary = "Get all projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "Get a project by ID")
    public ResponseEntity<Project> getProject(@PathVariable UUID projectId) {
        return ResponseEntity.ok(projectService.getProject(projectId));
    }

    @PostMapping("/{projectId}/tasks")
    @Operation(summary = "Add a task to a project")
    public ResponseEntity<Task> addTask(@PathVariable UUID projectId, @Valid @RequestBody Task task) {
        task.setProjectId(projectId);
        return ResponseEntity.ok(projectService.addTask(task));
    }

    @GetMapping("/{projectId}/tasks")
    @Operation(summary = "Get tasks for a project with optional filters")
    public ResponseEntity<Page<Task>> getProjectTasks(
            @PathVariable UUID projectId,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) LocalDate dueBefore,
            Pageable pageable) {
        return ResponseEntity.ok(projectService.getProjectTasks(projectId, status, dueBefore, pageable));
    }

    @GetMapping("/summary")
    @Operation(summary = "Get project summaries with task counts")
    public ResponseEntity<List<ProjectSummaryDTO>> getProjectsSummary() {
        return ResponseEntity.ok(projectService.getProjectsSummary());
    }
}