package com.finalproject.peerreview2021.services.modelmappers;

import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.models.dto.ReviewerDto;
import com.finalproject.peerreview2021.repositories.contracts.ReviewerRepository;
import com.finalproject.peerreview2021.repositories.contracts.StatusRepository;
import com.finalproject.peerreview2021.repositories.contracts.UserRepository;
import com.finalproject.peerreview2021.repositories.contracts.WorkItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewerModelMapper {

    private final ReviewerRepository reviewerRepository;
    private final UserRepository userRepository;
    private final WorkItemRepository workItemRepository;
    private final StatusRepository statusRepository;

    @Autowired
    public ReviewerModelMapper(ReviewerRepository reviewerRepository, UserRepository userRepository,
                               WorkItemRepository workItemRepository, StatusRepository statusRepository) {
        this.reviewerRepository = reviewerRepository;
        this.userRepository = userRepository;
        this.workItemRepository = workItemRepository;
        this.statusRepository = statusRepository;
    }


    public Reviewer fromDto(ReviewerDto reviewerDto) {
        Reviewer reviewer = new Reviewer();
        dtoToObject(reviewerDto, reviewer);
        return reviewer;
    }

    public Reviewer fromDto(ReviewerDto reviewerDto, int id) {
        Reviewer reviewer = reviewerRepository.getById(id);
        dtoToObject(reviewerDto, reviewer);
        return reviewer;
    }

    private void dtoToObject(ReviewerDto reviewerDto, Reviewer reviewer) {
        User user = userRepository.getById(reviewerDto.getReviewerId());
        reviewer.setUser(user);
        WorkItem workItem = workItemRepository.getById(reviewerDto.getWorkitemId());
        reviewer.setWorkItem(workItem);
        reviewer.setStatus(workItem.getStatus());
    }
}
