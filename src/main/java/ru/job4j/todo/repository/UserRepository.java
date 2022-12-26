package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ThreadSafe
public class UserRepository {
    private final SessionFactory sf;
    private final CrudRepository crudRepository;


    public Optional<User> add(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User> findUserByLogin(String login) {
        return crudRepository.optional(
                "from User as u where u.login = :flogin", User.class,
                Map.of("flogin", login)
        );

    }

    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "from User as u where u.login = :flogin and u.password = :fpassword", User.class,
                Map.of("flogin", login, "fpassword", password)
        );
    }
}
