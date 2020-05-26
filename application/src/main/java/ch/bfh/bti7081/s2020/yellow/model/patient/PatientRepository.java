package ch.bfh.bti7081.s2020.yellow.model.patient;

import ch.bfh.bti7081.s2020.yellow.persistence.Repository;
import ch.bfh.bti7081.s2020.yellow.persistence.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PatientRepository implements Repository<Patient> {
    Session session;

    public PatientRepository() {
        session = HibernateUtil.getHibernateSession();
    }

    @Override
    public void delete(long id) {
        session.beginTransaction();
        session.delete(getById(id));
        session.getTransaction().commit();
    }

    @Override
    public Query<Patient> getAll() {
        return session.createQuery("From Patient ");
    }

    @Override
    public Patient getById(long id) {
        return session.get(Patient.class, id);
    }

    @Override
    public void save(Patient entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }
}
