package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;

@ThreadSafe
@Repository
@AllArgsConstructor
public class TaskRepository {
    private final SessionFactory sf;


    public boolean add(Task task) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public Task findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task result = session.get(Task.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }
    public List<Task> getAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> result = session.createQuery("from Task", Task.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<Task> getOnly(boolean bool) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<Task> query = session.createQuery("from Task as t where t.done = :fbool", Task.class);
        query.setParameter("fbool", bool);
        List<Task> rezult = query.list();
        session.getTransaction().commit();
        session.close();
        return rezult;
    }

    public boolean delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task task = new Task();
        task.setId(id);
        session.delete(task);
        session.getTransaction().commit();
        session.close();
        return true;
    }


    public boolean execute(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery(
                        "UPDATE Task SET done = true WHERE id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public boolean update(Task task) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(task);
        session.getTransaction().commit();
        session.close();
        return true;
    }

}
