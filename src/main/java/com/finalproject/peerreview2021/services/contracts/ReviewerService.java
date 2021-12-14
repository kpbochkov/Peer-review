package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.Status;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;

import java.util.List;

public interface ReviewerService {

    void create(Reviewer entity);

    Reviewer getById(int id);

    List<Reviewer> getAll();

    void update(Reviewer entity);

    void delete(int id);

    List<User> getAllReviewersForWorkItemAsUsers(WorkItem workItem);

    List<Reviewer> getAllReviewersForWorkItem(WorkItem workItem);

    List<Reviewer> getAllReviewersForUser(User user);

    void setStatus(Reviewer reviewer, Status status);
}
