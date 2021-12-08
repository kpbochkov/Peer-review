package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.TeamInvitation;
import com.finalproject.peerreview2021.models.User;

public interface TeamInvitationService extends BaseCRUDService<TeamInvitation> {
    void create(User inviter, TeamInvitation teamInvitation);
}
