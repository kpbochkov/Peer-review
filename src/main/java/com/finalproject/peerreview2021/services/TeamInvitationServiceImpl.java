package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.TeamInvitation;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.repositories.contracts.TeamInvitationRepository;
import com.finalproject.peerreview2021.services.contracts.TeamInvitationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamInvitationServiceImpl implements TeamInvitationService {
    private final TeamInvitationRepository teamInvitationRepository;

    public TeamInvitationServiceImpl(TeamInvitationRepository teamInvitationRepository) {
        this.teamInvitationRepository = teamInvitationRepository;
    }

    @Override
    public void create(TeamInvitation entity) {
        teamInvitationRepository.create(entity);
    }

    @Override
    public List<TeamInvitation> getAll() {
        return teamInvitationRepository.getAll();
    }

    @Override
    public <V> TeamInvitation getByField(String name, V value) {
        return teamInvitationRepository.getByField(name, value);
    }

    @Override
    public TeamInvitation getById(int id) {
        return teamInvitationRepository.getById(id);
    }

    @Override
    public void update(TeamInvitation entity) {
        teamInvitationRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        teamInvitationRepository.delete(id);
    }

    @Override
    public void create(User inviter, TeamInvitation teamInvitation) {
        if (!inviter.getTeams().contains(teamInvitation.getTeam())) {
            throw new UnauthorizedOperationException("You are not part of the team");
        }
        if (teamInvitation.getUser().getTeams().contains(teamInvitation.getTeam())) {
            return;
        }
        teamInvitationRepository.create(teamInvitation);
    }
}
