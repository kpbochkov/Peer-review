package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.models.Status;
import com.finalproject.peerreview2021.repositories.contracts.StatusRepository;
import com.finalproject.peerreview2021.services.contracts.StatusService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 04.12.2021
 * Time: 20:00
 */
@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public List<Status> getAll() {
        return statusRepository.getAll();
    }

    @Override
    public Status getById(int id) {
        return statusRepository.getById(id);
    }
}
