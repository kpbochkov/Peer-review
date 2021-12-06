package com.finalproject.peerreview2021.services.modelmappers;

import com.finalproject.peerreview2021.models.Comment;
import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.models.dto.CommentDto;
import com.finalproject.peerreview2021.models.dto.ReviewerDto;
import com.finalproject.peerreview2021.repositories.contracts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentModelMapper {
    private final ReviewerRepository reviewerRepository;
    private final UserRepository userRepository;
    private final WorkItemRepository workItemRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentModelMapper(ReviewerRepository reviewerRepository, UserRepository userRepository,
                              WorkItemRepository workItemRepository, StatusRepository statusRepository, CommentRepository commentRepository) {
        this.reviewerRepository = reviewerRepository;
        this.userRepository = userRepository;
        this.workItemRepository = workItemRepository;
        this.commentRepository = commentRepository;
    }


    public Comment fromDto(CommentDto commentDto) {
        Comment comment = new Comment();
        dtoToObject(commentDto, comment);
        return comment;
    }

    public Comment fromDto(CommentDto commentDto, int id) {
        Comment comment = commentRepository.getById(id);
        dtoToObject(commentDto, comment);
        return comment;
    }

    private void dtoToObject(CommentDto commentDto, Comment comment) {
        User user = userRepository.getById(commentDto.getCreatedBy());
        comment.setUser(user);
        WorkItem workItem = workItemRepository.getById(commentDto.getWorkItemId());
        comment.setWorkItem(workItem);
        comment.setContent(commentDto.getContent());
    }
}
