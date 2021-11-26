package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.repositories.contracts.ReviewerRepository;
import com.finalproject.peerreview2021.services.contracts.ReviewerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewerServiceImpl implements ReviewerService {
    private final ReviewerRepository reviewerRepository;


    public ReviewerServiceImpl(ReviewerRepository reviewerRepository) {
        this.reviewerRepository = reviewerRepository;
    }

    @Override
    public void create(Reviewer entity) {
        if (entity.getWorkItem().getTeam().getMembers().contains((entity.getUser()))) {
            reviewerRepository.create(entity);
        }
    }

    @Override
    public List<Reviewer> getAll() {
        return reviewerRepository.getAll();
    }

    @Override
    public <V> Reviewer getByField(String name, V value) {
        return reviewerRepository.getByField(name, value);
    }

    @Override
    public Reviewer getById(int id) {
        return reviewerRepository.getById(id);
    }

    @Override
    public void update(Reviewer entity) {
        reviewerRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        reviewerRepository.delete(id);
    }
}
