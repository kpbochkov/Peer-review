package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.TeamInvitation;
import com.finalproject.peerreview2021.models.User;

import java.util.List;

public interface TeamInvitationService {
    void create(User inviter, TeamInvitation teamInvitation);

    TeamInvitation getById(int id);

    void delete(int id);

    List<TeamInvitation> getUserInvitations(User user);

    void accept(User user, TeamInvitation teamInvitation);

    void decline(User user, TeamInvitation teamInvitation);

    List<User> getInvitedUsers(Team team);
}
