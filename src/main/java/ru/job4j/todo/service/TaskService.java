package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.repository.TaskRepository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.List;

@ThreadSafe
@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public void add(Task task) {
        task.setCreated(LocalDateTime.now());
        taskRepository.add(task);
    }

    public Task findById(int id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAll() {
        return taskRepository.getAll();
    }

    public List<Task> getOnly(boolean bool) {
        return taskRepository.getOnly(bool);
    }

    public void delete(int id) {
        taskRepository.delete(id);
    }

    public void execute(int id) {
        taskRepository.execute(id);
    }

    public void update(Task task) {
        taskRepository.update(task);
    }


}
