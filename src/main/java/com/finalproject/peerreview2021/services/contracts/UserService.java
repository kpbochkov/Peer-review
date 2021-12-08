package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;

import java.util.List;
import java.util.Optional;

public interface UserService extends BaseCRUDService<User> {

    List<User> filter(Optional<String> username, Optional<String> email, Optional<Integer> phone);

    List<User> getPossibleAssignees(WorkItem workItem);

}
