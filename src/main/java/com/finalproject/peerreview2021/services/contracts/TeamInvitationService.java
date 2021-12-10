package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.TeamInvitation;
import com.finalproject.peerreview2021.models.User;

import java.util.List;

public interface TeamInvitationService extends BaseCRUDService<TeamInvitation> {
    void create(User inviter, TeamInvitation teamInvitation);

    List<TeamInvitation> getUserInvitations(User user);

    void accept(User user, TeamInvitation teamInvitation);

    void decline(User user, TeamInvitation teamInvitation);

    List<User> getInvitedUsers(Team team);
}
