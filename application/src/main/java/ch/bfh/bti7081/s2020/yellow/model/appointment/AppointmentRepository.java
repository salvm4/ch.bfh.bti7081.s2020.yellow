package ch.bfh.bti7081.s2020.yellow.model.appointment;

import ch.bfh.bti7081.s2020.yellow.model.Repository;
import ch.bfh.bti7081.s2020.yellow.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class AppointmentRepository implements Repository<Appointment> {
    Session session;

    public AppointmentRepository() {
        session = HibernateUtil.getHibernateSession();
    }

    @Override
    public void delete(long id) {
        session.beginTransaction();
        session.delete(getById(id));
        session.getTransaction().commit();
    }

    @Override
    public Query<Appointment> getAll() {
        return session.createQuery("From Appointment ");
    }

    @Override
    public Appointment getById(long id) {
        return session.get(Appointment.class, id);
    }

    @Override
    public void save(Appointment entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }
}
