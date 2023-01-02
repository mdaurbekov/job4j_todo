package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class PriorityRepository {
    private final CrudRepository crudRepository;

    public List<Priority> getAll() {
        return crudRepository.query("from Priority", Priority.class);
    }

    public Optional<Priority> findById(int id) {
        return crudRepository.optional(
                "from Priority where id = :fId", Priority.class,
                Map.of("fId", id)
        );
    }
}
