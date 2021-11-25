package com.finalproject.peerreview2021.services.contracts;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 23.11.2021
 * Time: 11:30
 */
public interface BaseCRUDService<T> {

    void create(T entity);

    List<T> getAll();

    <V> T getByField(String name, V value);

    T getById(int id);

    void update(T entity);

    void delete(int id);
}
