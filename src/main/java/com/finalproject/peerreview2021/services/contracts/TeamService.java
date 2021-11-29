package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.User;

import java.util.List;

public interface TeamService extends BaseCRUDService<Team> {
    void addUserToTeam(User user, Team team);

    List<User> getTeamMembers(Team team);

    void deleteTeamMember(User user, Team team);
}
