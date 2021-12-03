package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.WorkItem;

import java.util.List;

public interface ReviewerService extends BaseCRUDService<Reviewer> {
    List<Reviewer> getAllReviewersForWorkItem(WorkItem workItem);
}
