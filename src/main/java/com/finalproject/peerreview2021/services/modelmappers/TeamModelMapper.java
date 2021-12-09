package com.finalproject.peerreview2021.services.modelmappers;

import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.models.dto.TeamDto;
import com.finalproject.peerreview2021.models.dto.WorkItemDto;
import com.finalproject.peerreview2021.repositories.contracts.TeamRepository;
import com.finalproject.peerreview2021.repositories.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamModelMapper {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Autowired
    public TeamModelMapper(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public Team fromDto(TeamDto dto, User user){
        Team team = new Team();
        team.setUser(user);
        dtoToObject(dto, team);
        return team;
    }

    public Team fromDto(TeamDto dto, int id){
        Team team = teamRepository.getById(id);
        dtoToObject(dto, team);
        return team;
    }

    public TeamDto toDto(Team team) {
        TeamDto teamDto = new TeamDto();
        teamDto.setName(team.getName());
        return teamDto;
    }

    private void dtoToObject(TeamDto dto, Team team) {
        team.setName(dto.getName());
    }

}
