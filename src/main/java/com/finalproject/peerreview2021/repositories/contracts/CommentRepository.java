package com.finalproject.peerreview2021.repositories.contracts;

import com.finalproject.peerreview2021.models.Comment;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;

import java.util.List;

public interface CommentRepository extends BaseCRUDRepository<Comment> {
    void deleteUserComments(int id);

    List<Comment> getAllWorkItemComments(WorkItem workitem);
}
