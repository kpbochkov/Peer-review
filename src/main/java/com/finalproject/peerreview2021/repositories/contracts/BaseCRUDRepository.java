package com.finalproject.peerreview2021.repositories.contracts;

import org.hibernate.Session;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 23.11.2021
 * Time: 11:43
 */
public interface BaseCRUDRepository<T> extends BaseReadRepository<T>{
    public void delete(int id);

    public void create(T entity);

    public void update(T entity);
}
