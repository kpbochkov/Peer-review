package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 23.11.2021
 * Time: 11:28
 */
public interface WorkItemService{

    void create(WorkItem entity);

    List<WorkItem> getAll();

    WorkItem getById(int id);

    void update(WorkItem entity);

    void delete(int id);

    List<WorkItem> filter(Optional<String> title, Optional<String> status,
                          Optional<String> reviewer, Optional<String> sortParam);

    List<WorkItem> getAllWorkItemsForUser(User user);

    List<WorkItem> getAllWorkItemsForTeam(Team team);

    List<WorkItem> getAllWorkItemsForReviewer(User user);
}
