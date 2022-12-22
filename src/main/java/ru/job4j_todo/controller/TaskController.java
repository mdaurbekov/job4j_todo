package ru.job4j_todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j_todo.model.Task;
import ru.job4j_todo.model.User;
import ru.job4j_todo.service.TaskService;
import ru.job4j_todo.util.UserSession;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@ThreadSafe
@Controller
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/tasks")
    public String tasks(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.getAll());
        return "tasks";
    }

    @GetMapping("/onlyDone")
    public String onlyDone(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.getOnly(true));
        return "tasks";
    }

    @GetMapping("/onlyNotDone")
    public String onlyNotDone(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.getOnly(false));
        return "tasks";
    }

    @GetMapping("/formAddTask")
    public String addTask(Model model) {
        model.addAttribute("post", new Task(0, "Описание",
                LocalDateTime.now(), false));
        return "addTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.add(task);
        return "redirect:/tasks";
    }

    @GetMapping("/formViewTask/{Id}")
    public String formViewTask(Model model, @PathVariable("Id") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "viewTask";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/formUpdateTask/{id}")
    public String formUpdatePost(Model model, @PathVariable("id") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "updateTask";
    }

    @GetMapping("/executeTask/{id}")
    public String executeTask(@PathVariable("id") int id) {
        taskService.execute(id);
        return "redirect:/tasks";
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable("id") int id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }

}
