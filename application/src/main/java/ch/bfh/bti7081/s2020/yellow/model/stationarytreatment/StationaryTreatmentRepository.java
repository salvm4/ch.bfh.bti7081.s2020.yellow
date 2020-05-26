package ch.bfh.bti7081.s2020.yellow.model.stationarytreatment;

import ch.bfh.bti7081.s2020.yellow.persistence.Repository;
import ch.bfh.bti7081.s2020.yellow.persistence.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class StationaryTreatmentRepository implements Repository<StationaryTreatment> {
    Session session;

    public StationaryTreatmentRepository() {
        session = HibernateUtil.getHibernateSession();
    }

    @Override
    public void delete(long id) {
        session.beginTransaction();
        session.delete(getById(id));
        session.getTransaction().commit();
    }

    @Override
    public Query<StationaryTreatment> getAll() {
        return session.createQuery("From StationaryTreatment ");
    }

    @Override
    public StationaryTreatment getById(long id) {
        return session.get(StationaryTreatment.class, id);
    }

    @Override
    public void save(StationaryTreatment entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }
}
