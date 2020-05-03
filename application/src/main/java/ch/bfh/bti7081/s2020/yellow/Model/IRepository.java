package ch.bfh.bti7081.s2020.yellow.Model;

import org.hibernate.query.Query;

public interface IRepository<T> {
    void Delete(long id);

    T GetById(long id);

    Query<T> GetAll();

    void Save(T entity);
}
