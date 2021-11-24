package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.exceptions.UpdateEntityException;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.repositories.contracts.WorkItemRepository;
import com.finalproject.peerreview2021.services.contracts.WorkItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
//        boolean nameAlreadyTaken = true;
//        try {
//            workItemRepository.getByField("name", entity.getName());
//        } catch (EntityNotFoundException e) {
//            nameAlreadyTaken = false;
//        }
//        if (nameAlreadyTaken) {
//            throw new DuplicateEntityException("WorkItem", "name", entity.getName());
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
//        boolean nameAlreadyTaken = true;
//        try {
//            WorkItem entityInRepository = workItemRepository.getByField("name", entity.getName());
//            if(Objects.equals(entityInRepository.getId(), entity.getId())){
//                nameAlreadyTaken = false;
//            }
//        } catch (EntityNotFoundException e) {
//            nameAlreadyTaken = false;
//        }
//        if (nameAlreadyTaken) {
//            throw new DuplicateEntityException("WorkItem", "name", entity.getName());
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
}
