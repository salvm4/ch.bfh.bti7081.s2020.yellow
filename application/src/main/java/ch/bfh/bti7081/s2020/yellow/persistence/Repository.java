package ch.bfh.bti7081.s2020.yellow.persistence;

import org.hibernate.query.Query;

public interface Repository<T> {
    void delete(long id);

    T getById(long id);

    Query<T> getAll();

    void save(T entity);
}
