
package com.example.todo_app.Controller;

import com.example.todo_app.Services.TaskService;
import com.example.todo_app.models.Task;
import com.example.todo_app.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //  Display only logged-in user's tasks
    @GetMapping
    public String getTasks(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        // Fetch only that user’s tasks
        List<Task> tasks = taskService.getTasksByUser(user);
        model.addAttribute("tasks", tasks);
        model.addAttribute("username", user.getUsername());
        return "tasks";
    }

    //  Create a new task for the logged-in user
    @PostMapping
    public String createTask(@RequestParam String title, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        taskService.createTaskForUser(title, user);
        return "redirect:/tasks";
    }

    //  Delete only if the task belongs to the logged-in user
    @GetMapping("/{id}/delete")
    public String deleteTasks(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        taskService.deleteTaskForUser(id, user);
        return "redirect:/tasks";
    }

    //  Toggle only if the task belongs to the logged-in user
    @GetMapping("/{id}/toggle")
    public String toggleTasks(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        taskService.toggleTaskForUser(id, user);
        return "redirect:/tasks";
    }
}

