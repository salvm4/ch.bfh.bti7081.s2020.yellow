package ch.bfh.bti7081.s2020.yellow.model.appointment;

import ch.bfh.bti7081.s2020.yellow.persistence.Repository;
import ch.bfh.bti7081.s2020.yellow.persistence.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.sql.Timestamp;

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

    public Query<Appointment> getAllFuture() {
        return session.createQuery("From Appointment where startTime > '" + new Timestamp(System.currentTimeMillis()) + "'");
    }
    
    public Query<Appointment> getAllPast() {
        return session.createQuery("From Appointment where startTime < '" + new Timestamp(System.currentTimeMillis()) + "'");
    }
}
