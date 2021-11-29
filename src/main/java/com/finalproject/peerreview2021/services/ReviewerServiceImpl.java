package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.repositories.contracts.ReviewerRepository;
import com.finalproject.peerreview2021.services.contracts.ReviewerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ReviewerServiceImpl implements ReviewerService {
    private final ReviewerRepository reviewerRepository;


    public ReviewerServiceImpl(ReviewerRepository reviewerRepository) {
        this.reviewerRepository = reviewerRepository;
    }

    @Override
    public void create(Reviewer entity) {
        Set<User> users = entity.getWorkItem().getTeam().getMembers();
        for (User user : users) {
            System.out.println(user.getUsername());
        }
        System.out.println("----------");
        User user = entity.getUser();
        System.out.println(user.getUsername());
        System.out.println(users.contains(user));
        if (entity.getWorkItem().getTeam().getMembers().contains((entity.getUser()))) {
            reviewerRepository.create(entity);
        } else {
            throw new UnauthorizedOperationException(" Reviewers must be part of the team" +
                    " where the work item is created");
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
