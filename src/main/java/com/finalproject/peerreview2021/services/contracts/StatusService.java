package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.Status;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 04.12.2021
 * Time: 19:59
 */
public interface StatusService {
    List<Status> getAll();

    Status getById(int id);
}
