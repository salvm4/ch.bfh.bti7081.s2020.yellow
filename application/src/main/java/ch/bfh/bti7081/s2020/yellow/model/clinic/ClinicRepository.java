package ch.bfh.bti7081.s2020.yellow.model.clinic;

import ch.bfh.bti7081.s2020.yellow.persistence.Repository;
import ch.bfh.bti7081.s2020.yellow.persistence.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ClinicRepository implements Repository<Clinic> {
    Session session;

    public ClinicRepository() {
        session = HibernateUtil.getHibernateSession();
    }

    @Override
    public void delete(long id) {
        session.beginTransaction();
        session.delete(getById(id));
        session.getTransaction().commit();
    }

    @Override
    public Query<Clinic> getAll() {
        return session.createQuery("From Clinic ");
    }

    @Override
    public Clinic getById(long id) {
        return session.get(Clinic.class, id);
    }

    @Override
    public void save(Clinic entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }
}
