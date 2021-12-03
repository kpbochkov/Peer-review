package com.finalproject.peerreview2021.services.modelmappers;

import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.models.dto.WorkItemDto;
import com.finalproject.peerreview2021.repositories.contracts.StatusRepository;
import com.finalproject.peerreview2021.repositories.contracts.TeamRepository;
import com.finalproject.peerreview2021.repositories.contracts.UserRepository;
import com.finalproject.peerreview2021.repositories.contracts.WorkItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 23.11.2021
 * Time: 12:43
 */
@Component
public class WorkItemModelMapper {

    private final WorkItemRepository workItemRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final StatusRepository statusRepository;

    @Autowired
    public WorkItemModelMapper(WorkItemRepository workItemRepository, UserRepository userRepository, TeamRepository teamRepository, StatusRepository statusRepository) {
        this.workItemRepository = workItemRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.statusRepository = statusRepository;
    }

    public WorkItem fromDto(WorkItemDto dto){
        WorkItem workItem = new WorkItem();
        dtoToObject(dto, workItem);
        return workItem;
    }

    public WorkItem fromDto(WorkItemDto dto, int id){
        WorkItem workItem = workItemRepository.getById(id);
        dtoToObject(dto, workItem);
        return workItem;
    }

    private void dtoToObject(WorkItemDto dto, WorkItem workItem) {
        workItem.setTitle(dto.getTitle());
        workItem.setDescription(dto.getDescription());
//        workItem.setCreatedBy(userRepository.getById(dto.getCreatedBy()));
        workItem.setTeam(teamRepository.getById(dto.getTeamId()));
        if(workItem.getStatus() == null) {
            workItem.setStatus(statusRepository.getByField("name", "Pending"));
        }
    }
}
