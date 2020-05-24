package ch.bfh.bti7081.s2020.yellow.model.drug;

import ch.bfh.bti7081.s2020.yellow.model.Repository;
import ch.bfh.bti7081.s2020.yellow.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class DrugRepository implements Repository<Drug> {

    private Session session;

    public DrugRepository() {
        session = HibernateUtil.getHibernateSession();
    }

    @Override
    public void delete(long id) {
        session.beginTransaction();
        session.delete(getById(id));
        session.getTransaction().commit();
    }

    @Override
    public Drug getById(long id) {
        return session.get(Drug.class, id);
    }

    @Override
    public Query<Drug> getAll() {
        return session.createQuery("From Drug ");
    }

    @Override
    public void save(Drug entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }
}
