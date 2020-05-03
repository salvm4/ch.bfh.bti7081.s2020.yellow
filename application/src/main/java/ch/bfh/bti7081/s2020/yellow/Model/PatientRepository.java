package ch.bfh.bti7081.s2020.yellow.Model;

import ch.bfh.bti7081.s2020.yellow.Model.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PatientRepository implements IRepository<Patient> {
    Session session;

    public PatientRepository() {
        session = HibernateUtil.getHibernateSession();
    }

    @Override
    public void Delete(long id) {
        session.beginTransaction();
        session.delete(GetById(id));
        session.getTransaction().commit();
    }

    @Override
    public Query<Patient> GetAll() {
        return session.createQuery("From Patient ");
    }

    @Override
    public Patient GetById(long id) {
        return session.get(Patient.class, id);
    }

    @Override
    public void Save(Patient entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }
}
