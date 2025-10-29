package com.example.todo_app.Services;

import com.example.todo_app.models.Task;
import com.example.todo_app.models.User;
import com.example.todo_app.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Get only user's tasks
    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    // Create task linked to user
    public void createTaskForUser(String title, User user) {
        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        task.setUser(user);
        taskRepository.save(task);
    }

    // Delete only if task belongs to user
    public void deleteTaskForUser(Long id, User user) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        taskOpt.ifPresent(task -> {
            if (task.getUser().getId().equals(user.getId())) {
                taskRepository.delete(task);
            }
        });
    }

    // Toggle only if task belongs to user
    public void toggleTaskForUser(Long id, User user) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        taskOpt.ifPresent(task -> {
            if (task.getUser().getId().equals(user.getId())) {
                task.setCompleted(!task.isCompleted());
                taskRepository.save(task);
            }
        });
    }
}
