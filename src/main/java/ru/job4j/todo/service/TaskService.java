package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.TaskRepository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.util.UserSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final CategoryService categoryService;

    public boolean add(Task task, User user, List<Integer> categoriesId) {
        boolean result = false;
        task.setCategory(categoriesId.stream()
                .map(e -> categoryService.findById(e)
                        .orElseThrow(NoSuchElementException::new))
                .toList());
        if (categoryService.findById(task.getPriority().getId()).isPresent()) {
            task.setUser(user);
            task.setCreated(LocalDateTime.now());
            result = taskRepository.add(task);
        }
        return result;
    }

    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAll() {
        return taskRepository.getAll();
    }

    public List<Task> getOnly(boolean bool) {
        return taskRepository.getOnly(bool);
    }

    public boolean delete(int id) {
        return taskRepository.delete(id);
    }

    public boolean execute(int id) {
        return taskRepository.execute(id);
    }

    public boolean update(Task task) {
        return taskRepository.update(task);
    }


}
