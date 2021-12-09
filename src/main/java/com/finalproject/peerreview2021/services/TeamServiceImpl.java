package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.exceptions.UpdateEntityException;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.repositories.contracts.TeamRepository;
import com.finalproject.peerreview2021.services.contracts.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void create(Team entity) {
        boolean nameAlreadyTaken = true;
        try {
            teamRepository.getByField("name", entity.getName());
        } catch (EntityNotFoundException e) {
            nameAlreadyTaken = false;
        }
        if (nameAlreadyTaken) {
            throw new DuplicateEntityException("Team", "name", entity.getName());
        }
        teamRepository.create(entity);
        addUserToTeam(entity.getUser(), entity);
    }

    @Override
    public Team getById(int id) {
        return teamRepository.getById(id);
    }

    @Override
    public void update(Team entity) {
        Team entityInRepository = teamRepository.getById(entity.getId());
        if (!Objects.equals(entityInRepository.getUser().getId(), entity.getUser().getId())) {
            throw new UpdateEntityException("Team", "owner");
        }
        teamRepository.update(entity);
    }

    @Override
    public List<Team> getAll() {
        return teamRepository.getAll();
    }

    @Override
    public void delete(int id) {
        teamRepository.delete(id);
    }

    @Override
    public <V> Team getByField(String name, V value) {
        return teamRepository.getByField(name, value);
    }

    @Override
    public void addUserToTeam(User user, Team team) {
        var newMembers = team.getMembers();
        if (newMembers == null) {
            Set<User> newMembersWithOwner = new HashSet<>();
            newMembersWithOwner.add(user);
            team.setMembers(newMembersWithOwner);
            teamRepository.update(team);
            return;
        }
        if (newMembers.contains(user)) {
            throw new DuplicateEntityException(
                    String.format("User with username %s is already in the team", user.getUsername()));
        }


        newMembers.add(user);
        team.setMembers(newMembers);
        teamRepository.update(team);
    }

    @Override
    public List<User> getTeamMembers(Team team) {
        return new ArrayList<>(team.getMembers());
    }

    @Override
    public List<Team> getUserTeams(User user) {
        return new ArrayList<>(user.getTeams());
    }

    @Override
    public void deleteTeamMember(User user, Team team) {
        team.getMembers().remove(user);
        teamRepository.update(team);
    }

    @Override
    public void leaveTeam(User user, Team team) {
        if (team.getUser().equals(user)) {
            throw new UnauthorizedOperationException("You are the owner, " +
                    "you cannot leave your own team!");
        }
        team.getMembers().remove(user);
        teamRepository.update(team);
    }
}
