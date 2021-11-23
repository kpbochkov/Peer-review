package com.finalproject.peerreview2021.repositories.contracts;

import com.finalproject.peerreview2021.models.WorkItem;

import java.util.List;
import java.util.Optional;

public interface WorkItemRepository extends BaseCRUDRepository<WorkItem> {

    //ToDo add method "assign additional reviewers"

    List<WorkItem> filter(Optional<String> name, Optional<String> status,
                          Optional<String> sortParam);

}
