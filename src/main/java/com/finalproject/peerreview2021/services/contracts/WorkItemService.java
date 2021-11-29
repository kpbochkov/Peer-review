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
public interface WorkItemService extends BaseCRUDService<WorkItem> {
    List<WorkItem> filter(Optional<String> title, Optional<String> status,
                          Optional<String> reviewer, Optional<String> sortParam);

    List<WorkItem> getAllWorkitemsForUser(User user);

    List<WorkItem> getAllWorkitemsForTeam(Team team);
}
