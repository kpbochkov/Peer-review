package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.TeamInvitation;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.repositories.contracts.TeamInvitationRepository;
import com.finalproject.peerreview2021.repositories.contracts.TeamRepository;
import com.finalproject.peerreview2021.services.contracts.TeamInvitationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamInvitationServiceImpl implements TeamInvitationService {
    private final TeamInvitationRepository teamInvitationRepository;
    private final TeamRepository teamRepository;

    public TeamInvitationServiceImpl(TeamInvitationRepository teamInvitationRepository, TeamRepository teamRepository) {
        this.teamInvitationRepository = teamInvitationRepository;
        this.teamRepository = teamRepository;
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
        if (teamInvitationRepository.getListByField("user.id", teamInvitation.getUser().getId()).
                stream().map(TeamInvitation::getTeam).collect(Collectors.toList()).
                contains(teamInvitation.getTeam())) {
            return;
        }
        teamInvitationRepository.create(teamInvitation);
    }

    @Override
    public List<TeamInvitation> getUserInvitations(User user) {
        return teamInvitationRepository.getListByField("user.id",user.getId());
    }

    @Override
    public void accept(User user, TeamInvitation teamInvitation) {
        if (!user.equals(teamInvitation.getUser())) {
            throw new UnauthorizedOperationException("This invitation is not for you.");
        }
        teamInvitation.getTeam().getMembers().add(teamInvitation.getUser());
        teamRepository.update(teamInvitation.getTeam());
        teamInvitationRepository.delete(teamInvitation.getId());
    }

    @Override
    public void decline(User user, TeamInvitation teamInvitation) {
        if (!user.equals(teamInvitation.getUser())) {
            throw new UnauthorizedOperationException("This invitation is not for you.");
        }
        teamInvitationRepository.delete(teamInvitation.getId());
    }


}
