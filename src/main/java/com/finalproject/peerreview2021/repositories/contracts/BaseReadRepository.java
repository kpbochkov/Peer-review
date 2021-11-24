package com.finalproject.peerreview2021.repositories.contracts;

import java.util.List;

public interface BaseReadRepository<T> {

    List<T> getAll();

    T getById(int id);

    <V> T getByField(String name, V value);

    <V> List<T> getListByField(String name, V value);

}
