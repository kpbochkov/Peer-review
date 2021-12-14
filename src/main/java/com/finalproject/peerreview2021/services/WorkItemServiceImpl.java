package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.exceptions.UpdateEntityException;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.repositories.contracts.WorkItemRepository;
import com.finalproject.peerreview2021.services.contracts.WorkItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 23.11.2021
 * Time: 11:26
 */
@Service
public class WorkItemServiceImpl implements WorkItemService {

    private final WorkItemRepository workItemRepository;

    @Autowired
    public WorkItemServiceImpl(WorkItemRepository workItemRepository) {
        this.workItemRepository = workItemRepository;
    }

    @Override
    public void create(WorkItem entity) {
//        boolean titleAlreadyTaken = true;
//        try {
//            workItemRepository.getByField("title", entity.getTitle());
//        } catch (EntityNotFoundException e) {
//            titleAlreadyTaken = false;
//        }
//        if (titleAlreadyTaken) {
//            throw new DuplicateEntityException("WorkItem", "title", entity.getTitle());
//        }
        workItemRepository.create(entity);
    }

    @Override
    public List<WorkItem> getAll() {
        return workItemRepository.getAll();
    }

    @Override
    public WorkItem getById(int id) {
        return workItemRepository.getById(id);
    }

    @Override
    public void update(WorkItem entity) {
//        boolean titleAlreadyTaken = true;
//        try {
//            WorkItem entityInRepository = workItemRepository.getByField("title", entity.getTitle());
//            if(Objects.equals(entityInRepository.getId(), entity.getId())){
//                titleAlreadyTaken = false;
//            }
//        } catch (EntityNotFoundException e) {
//            titleAlreadyTaken = false;
//        }
//        if (titleAlreadyTaken) {
//            throw new DuplicateEntityException("WorkItem", "title", entity.getTitle());
//        }
        WorkItem entityInRepository = workItemRepository.getById(entity.getId());
        if (!Objects.equals(entityInRepository.getCreatedBy().getId(), entity.getCreatedBy().getId())) {
            throw new UpdateEntityException("WorkItem", "createdBy");
        }
        if (!Objects.equals(entityInRepository.getTeam().getId(), entity.getTeam().getId())) {
            throw new UpdateEntityException("WorkItem", "teamOwner");
        }
        workItemRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        workItemRepository.delete(id);
    }

    @Override
    public List<WorkItem> filter(Optional<String> title, Optional<String> status, Optional<String> reviewer, Optional<String> sortParam) {
        return workItemRepository.filter(title, status, reviewer, sortParam);
    }

    @Override
    public List<WorkItem> getAllWorkItemsForUser(User user) {
        return workItemRepository.getAllWorkItemsForUser(user);
    }

    @Override
    public List<WorkItem> getAllWorkItemsForTeam(Team team) {
        return workItemRepository.showAllWorkItemsForTeam(team);
    }

    @Override
    public List<WorkItem> getAllWorkItemsForReviewer(User user) {
        return workItemRepository.getAllWorkItemsForReviewer(user);
    }
}
