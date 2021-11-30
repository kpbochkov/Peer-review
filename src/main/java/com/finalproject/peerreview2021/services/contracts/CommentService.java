package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.Comment;
import com.finalproject.peerreview2021.models.WorkItem;

import java.util.List;

public interface CommentService extends BaseCRUDService<Comment> {
    List<Comment> getAllWorkItemComments(WorkItem workitem);
}
