package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class PriorityService {
    private final PriorityRepository priorityRepository;

    public List<Priority> getAll() {
        return priorityRepository.getAll();
    }

    public Optional<Priority> findById(int id) {
        return priorityRepository.findById(id);
    }
}
