package com.example.projectmanagement.service;

import com.example.projectmanagement.model.Task;
import com.example.projectmanagement.repository.TaskRepository;
import com.example.projectmanagement.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task updateTask(Task task) {
        if (!taskRepository.existsById(task.getId())) {
            throw new ResourceNotFoundException("Task not found");
        }
        return taskRepository.save(task);
    }

    public void deleteTask(UUID taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task not found");
        }
        taskRepository.deleteById(taskId);
    }
}