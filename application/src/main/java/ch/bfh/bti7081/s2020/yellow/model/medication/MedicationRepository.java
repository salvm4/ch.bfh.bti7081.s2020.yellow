package ch.bfh.bti7081.s2020.yellow.model.medication;

import ch.bfh.bti7081.s2020.yellow.model.Repository;
import ch.bfh.bti7081.s2020.yellow.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class MedicationRepository implements Repository<Medication> {

    private Session session;

    public MedicationRepository() {
        session = HibernateUtil.getHibernateSession();
    }

    @Override
    public void delete(long id) {
        session.beginTransaction();
        session.delete(getById(id));
        session.getTransaction().commit();
    }

    @Override
    public Medication getById(long id) {
        return session.get(Medication.class, id);
    }

    @Override
    public Query<Medication> getAll() {
        return session.createQuery("From Medication ");
    }

    @Override
    public void save(Medication entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }
}
