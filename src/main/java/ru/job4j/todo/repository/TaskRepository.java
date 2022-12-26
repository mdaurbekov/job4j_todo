package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class TaskRepository {
    private final CrudRepository crudRepository;


    public boolean add(Task task) {
        crudRepository.run(session -> session.save(task));
        return true;
    }

    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                "from Task where id = :fId", Task.class,
                Map.of("fId", id)
        );
    }

    public List<Task> getAll() {
        return crudRepository.query("from Task", Task.class);
    }

    public List<Task> getOnly(boolean bool) {
        return crudRepository.query("from Task as t where t.done = :fbool",
                Task.class, Map.of("fbool", bool));
    }

    public boolean delete(int id) {
        crudRepository.run(
                "delete from Task where id = :fId",
                Map.of("fId", id)
        );
        return true;
    }


    public boolean execute(int id) {
        crudRepository.run(
                "UPDATE Task SET done = true WHERE id = :fId",
                Map.of("fId", id)
        );
        return true;
    }

    public boolean update(Task task) {
        crudRepository.run(session -> session.merge(task));
        return true;
    }

}
