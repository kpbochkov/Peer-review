package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.User;

import java.util.List;

public interface TeamService {

    void create(Team entity);

    Team getById(int id);

    void update(Team entity);

    void delete(int id);

    void addUserToTeam(User user, Team team);

    List<User> getTeamMembers(Team team);

    List<Team> getUserTeams(User user);

    void deleteTeamMember(User user, Team team);

    void leaveTeam(User user, Team team);

}
