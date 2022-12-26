package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.util.UserSession;

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
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.getAll());
        return "task/tasks";
    }

    @GetMapping("/onlyDone")
    public String onlyDone(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.getOnly(true));
        return "task/tasks";
    }

    @GetMapping("/onlyNotDone")
    public String onlyNotDone(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.getOnly(false));
        return "task/tasks";
    }

    @GetMapping("/formAdd")
    public String addTask(Model model) {
        model.addAttribute("task", new Task(0, "Описание",
                LocalDateTime.now(), false));
        return "/task/add";
    }

    @PostMapping("/create")
    public String create(Model model, @ModelAttribute Task task) {
        boolean success = taskService.add(task);
        if (!success) {
            model.addAttribute("fail", true);
            return "/shared/error";
        }
        return "redirect:/tasks/";
    }

    @GetMapping("/formView/{Id}")
    public String formView(Model model, @PathVariable("Id") int id, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("task", taskService.findById(id).get());
        return "/task/view";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute Task task) {
        boolean success = taskService.update(task);
        if (!success) {
            model.addAttribute("fail", true);
            return "/shared/error";
        }
        return "redirect:/tasks/";
    }

    @GetMapping("/formUpdate/{id}")
    public String formUpdate(Model model, @PathVariable("id") int id) {
        model.addAttribute("task", taskService.findById(id).get());
        return "/task/update";
    }

    @GetMapping("/execute/{id}")
    public String execute(@PathVariable("id") int id) {
        taskService.execute(id);
        return "redirect:/tasks/";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id) {
        boolean success = taskService.delete(id);
        if (!success) {
            model.addAttribute("fail", true);
            return "/shared/error";
        }
        return "redirect:/tasks/";
    }

}
