package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.models.Comment;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.repositories.CommentRepositoryImpl;
import com.finalproject.peerreview2021.repositories.contracts.CommentRepository;
import com.finalproject.peerreview2021.services.contracts.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void create(Comment entity) {
        commentRepository.create(entity);
    }

    public List<Comment> getAllWorkItemComments(WorkItem workItem) {
        return commentRepository.getAllWorkItemComments(workItem);
    }
}
