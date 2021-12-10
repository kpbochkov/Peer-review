package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.models.SoftDeletable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 10.12.2021
 * Time: 15:17
 */
public class AbstractCRUDSoftDeleteRepository<T extends SoftDeletable> extends AbstractReadSoftDeleteRepository<T> {

    private final SessionFactory sessionFactory;

    public AbstractCRUDSoftDeleteRepository(Class<T> clazz, SessionFactory sessionFactory) {
        super(clazz, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    public void delete(int id) {
        T toDelete = getById(id);
        toDelete.setActive(false);
        update(toDelete);
    }

    public void create(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }

    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
    }
}
