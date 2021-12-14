package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.Status;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.repositories.contracts.ReviewerRepository;
import com.finalproject.peerreview2021.repositories.contracts.StatusRepository;
import com.finalproject.peerreview2021.repositories.contracts.WorkItemRepository;
import com.finalproject.peerreview2021.services.contracts.NotificationService;
import com.finalproject.peerreview2021.services.contracts.ReviewerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewerServiceImpl implements ReviewerService {
    private final ReviewerRepository reviewerRepository;
    private final StatusRepository statusRepository;
    private final WorkItemRepository workItemRepository;
    private final NotificationService notificationService;


    public ReviewerServiceImpl(ReviewerRepository reviewerRepository, StatusRepository statusRepository, WorkItemRepository workItemRepository, NotificationService notificationService) {
        this.reviewerRepository = reviewerRepository;
        this.statusRepository = statusRepository;
        this.workItemRepository = workItemRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void create(Reviewer entity) {
        if (entity.getWorkItem().getCreatedBy().getId().equals(entity.getUser().getId())) {
            throw new UnauthorizedOperationException("Creator of work item cannot be assigned as" +
                    " a reviewer");
        }
        if (!entity.getWorkItem().getTeam().getMembers().contains(entity.getUser())) {
            throw new UnauthorizedOperationException("Reviewers must be part of the team" +
                    " where the work item is created");
        }
        if (getAllReviewersForWorkItemAsUsers(entity.getWorkItem()).contains(entity.getUser())) {
            throw new UnauthorizedOperationException("This Reviewer is already assigned to the Work Item!");
        }
        reviewerRepository.create(entity);
    }

    @Override
    public List<Reviewer> getAll() {
        return reviewerRepository.getAll();
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

    @Override
    public List<Reviewer> getAllReviewersForWorkItem(WorkItem workItem) {
        return reviewerRepository.getAllReviewersForWorkItem(workItem);
    }

    @Override
    public List<Reviewer> getAllReviewersForUser(User user) {
        return reviewerRepository.getAllReviewersForUser(user);
    }

    @Override
    public void setStatus(Reviewer reviewer, Status status) {
        if (reviewer.getStatus().getId().equals(status.getId())) {
            return;
        }
        reviewer.setStatus(status);
        reviewerRepository.update(reviewer);
        WorkItem workItem = reviewer.getWorkItem();
        List<Reviewer> reviewers = reviewerRepository.getAllReviewersForWorkItem(workItem);
        List<Integer> statuses = reviewers.stream()
                .map(r -> r.getStatus().getId())
                .collect(Collectors.toList());
        if (statuses.contains(5)) {
            workItem.setStatus(statusRepository.getById(5));
        } else if (statuses.stream().allMatch(s -> s.equals(4))) {
            workItem.setStatus(statusRepository.getById(4));
        } else if (!statuses.contains(5) && statuses.contains(3)) {
            workItem.setStatus(statusRepository.getById(3));
        } else if (statuses.contains(2)) {
            workItem.setStatus(statusRepository.getById(2));
        } else {
            workItem.setStatus(statusRepository.getById(1));
        }
        workItemRepository.update(workItem);

        List<User> usersToNotify = reviewerRepository.getAllReviewersForWorkItemAsUsers(workItem);
        usersToNotify.add(workItem.getCreatedBy());
        notificationService.notify(String.format("\"%s\" changed the review status for Work Item" +
                        " with title \"%s\" to \"%s\"", reviewer.getUser().getUsername(), workItem.getTitle(), status.getName()),
                usersToNotify);

    }

    @Override
    public List<User> getAllReviewersForWorkItemAsUsers(WorkItem workItem) {
        return reviewerRepository.getAllReviewersForWorkItemAsUsers(workItem);
    }
}
