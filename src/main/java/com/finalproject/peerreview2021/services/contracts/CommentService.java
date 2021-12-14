package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.Comment;
import com.finalproject.peerreview2021.models.WorkItem;

import java.util.List;

public interface CommentService {

    void create(Comment entity);

    List<Comment> getAllWorkItemComments(WorkItem workitem);
}
