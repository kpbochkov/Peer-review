package com.finalproject.peerreview2021.repositories.contracts;

import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;

import java.util.List;

public interface ReviewerRepository extends BaseCRUDRepository<Reviewer> {
    List<User> getAllReviewersForWorkItemAsUsers(WorkItem workItem);

    List<Reviewer> getAllReviewersForWorkItem(WorkItem workItem);
}
