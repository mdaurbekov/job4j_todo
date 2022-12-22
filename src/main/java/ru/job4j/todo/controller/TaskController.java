package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@ThreadSafe
@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/")
    public String all(Model model, HttpSession session) {
        model.addAttribute("tasks", taskService.getAll());
        return "tasks";
    }

    @GetMapping("/onlyDone")
    public String onlyDone(Model model, HttpSession session) {
        model.addAttribute("tasks", taskService.getOnly(true));
        return "tasks";
    }

    @GetMapping("/onlyNotDone")
    public String onlyNotDone(Model model, HttpSession session) {
        model.addAttribute("tasks", taskService.getOnly(false));
        return "tasks";
    }

    @GetMapping("/formAdd")
    public String addTask(Model model) {
        model.addAttribute("post", new Task(0, "Описание",
                LocalDateTime.now(), false));
        return "addTask";
    }

    @PostMapping("/create")
    public String createTask(Model model, @ModelAttribute Task task) {
        boolean success = taskService.add(task);
        if (!success) {
            model.addAttribute("fail", true);
            return "error";
        }
        return "redirect:/tasks/";
    }

    @GetMapping("/formView/{Id}")
    public String formViewTask(Model model, @PathVariable("Id") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "viewTask";
    }

    @PostMapping("/update")
    public String updateTask(Model model, @ModelAttribute Task task) {
        boolean success = taskService.update(task);
        if (!success) {
            model.addAttribute("fail", true);
            return "error";
        }
        return "redirect:/tasks/";
    }

    @GetMapping("/formUpdate/{id}")
    public String formUpdatePost(Model model, @PathVariable("id") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "updateTask";
    }

    @GetMapping("/execute/{id}")
    public String executeTask(@PathVariable("id") int id) {
        taskService.execute(id);
        return "redirect:/tasks/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(Model model, @PathVariable("id") int id) {
        boolean success = taskService.delete(id);
        if (!success) {
            model.addAttribute("fail", true);
            return "error";
        }
        return "redirect:/tasks/";
    }

}
