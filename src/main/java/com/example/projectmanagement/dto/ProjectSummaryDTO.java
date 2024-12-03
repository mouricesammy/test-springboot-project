package com.example.projectmanagement.dto;

import lombok.Data;
import java.util.Map;
import java.util.UUID;

import com.example.projectmanagement.model.TaskStatus;

@Data
public class ProjectSummaryDTO {
    private UUID id;
    private String name;
    private Map<TaskStatus, Long> taskCountByStatus;
}