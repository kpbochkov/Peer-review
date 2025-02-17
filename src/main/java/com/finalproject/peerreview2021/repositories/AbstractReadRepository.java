package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.repositories.contracts.BaseReadRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class AbstractReadRepository<T> implements BaseReadRepository<T> {

    private final Class<T> clazz;
    private final SessionFactory sessionFactory;


    public AbstractReadRepository(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    /**
     * Retrieves an entity from the database that has a <code>field</code> equal to <code>value</code>.
     * <br/>
     * Example: <code>getByField("id", 1, Parcel.class)</code>
     * will execute the following HQL: <code>from Parcel where id = 1;</code>
     *
     * @param name  the name of the field
     * @param value the value of the field
     * @return an entity that matches the given criteria
     */
    @Override
    public <V> T getByField(String name, V value) {
        final String query = String.format("from %s where %s = :value", clazz.getSimpleName(), name);
        final String notFoundErrorMessage = String.format("%s with %s %s not found", clazz.getSimpleName(), name, value);

        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery(query, clazz)
                    .setParameter("value", value)
                    .uniqueResultOptional()
                    .orElseThrow(() -> new EntityNotFoundException(clazz.getSimpleName(), name, value.toString()));
        }
    }

    @Override
    public <V> List<T> getListByField(String name, V value) {
        final String query = String.format("from %s where %s = :value", clazz.getSimpleName(), name);

        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery(query, clazz)
                    .setParameter("value", value)
                    .list();
        }
    }


    @Override
    public T getById(int id) {
        return getByField("id", id);
    }

    @Override
    public List<T> getAll() {
        try (Session session = sessionFactory.openSession()) {
            List<T> list = session.createQuery(String.format("from %s ", clazz.getName()), clazz).list();
            for (T t : list) {
                System.out.println(t.toString());
            }
            return list;
        }
    }
}
