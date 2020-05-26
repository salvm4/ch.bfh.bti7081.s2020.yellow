package ch.bfh.bti7081.s2020.yellow.model.task;

import ch.bfh.bti7081.s2020.yellow.persistence.Repository;
import ch.bfh.bti7081.s2020.yellow.persistence.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class TaskRepository implements Repository<Task> {

    private Session session;


    public TaskRepository() {
        session = HibernateUtil.getHibernateSession();
    }

    @Override
    public void delete(long id) {
        session.beginTransaction();
        session.delete(getById(id));
        session.getTransaction().commit();
    }

    @Override
    public Task getById(long id) {
        return session.get(Task.class, id);
    }

    @Override
    public Query<Task> getAll() {
        return session.createQuery("From Task ");
    }

    @Override
    public void save(Task entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }
}
