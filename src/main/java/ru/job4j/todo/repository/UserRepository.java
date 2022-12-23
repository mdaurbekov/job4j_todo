package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
@ThreadSafe
public class UserRepository {
    private final SessionFactory sf;


    public User add(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }
    public Optional<User> findUserByLogin(String login) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<User> query = session.createQuery("from User as u where u.login = :flogin", User.class);
        query.setParameter("flogin", login);
        Optional<User> result = query.uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<User> query = session.createQuery("from User as u where u.login = :flogin and u.password = :fpassword", User.class);
        query.setParameter("flogin", login);
        query.setParameter("fpassword", password);
        Optional<User> result = query.uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
