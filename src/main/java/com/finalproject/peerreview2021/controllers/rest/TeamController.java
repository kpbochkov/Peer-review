package com.finalproject.peerreview2021.controllers.rest;

import com.finalproject.peerreview2021.controllers.AuthenticationHelper;
import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.exceptions.UpdateEntityException;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.dto.TeamDto;
import com.finalproject.peerreview2021.services.contracts.TeamService;
import com.finalproject.peerreview2021.services.contracts.UserService;
import com.finalproject.peerreview2021.services.modelmappers.TeamModelMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;
    private final TeamModelMapper teamModelMapper;
    private final AuthenticationHelper authenticationHelper;
    private final UserService userService;

    @Autowired
    public TeamController(TeamService teamService, TeamModelMapper teamModelMapper,
                          AuthenticationHelper authenticationHelper, UserService userService) {
        this.teamService = teamService;
        this.teamModelMapper = teamModelMapper;
        this.authenticationHelper = authenticationHelper;
        this.userService = userService;
    }

    @ApiOperation(value = "Get Team by ID")
    @GetMapping("/{id}")
    public Team getById(@PathVariable int id) {
        try {
            return teamService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ApiOperation(value = "Create new Team")
    @PostMapping
    public Team create(@Valid @RequestBody TeamDto teamDto) {
        try {
            Team team = teamModelMapper.fromDto(teamDto);
            teamService.create(team);
            return team;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @ApiOperation(value = "Update existing Team with the given id")
    @PutMapping("/{id}")
    public Team update(@PathVariable int id, @Valid @RequestBody TeamDto teamDto) {
        try {
            Team team = teamModelMapper.fromDto(teamDto, id);
            teamService.update(team);
            return team;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UpdateEntityException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    @ApiOperation(value = "Delete Team with the given id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            teamService.delete(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    /*
•	Add new members to a team (must)
•	Remove members from the team (must)
•	List team members (must)
     */

    @ApiOperation(value = "Add new members to a team")
    @PostMapping("/{teamId}")
    public void addUserToTeam(
            @PathVariable int teamId,
            @RequestBody Map<String, Object> body) {
        var team = teamService.getById(teamId);
        var user1 = (int) body.get("userId");

        User userToAdd;
        try {
            userToAdd = userService.getById(user1);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        try {
            teamService.addUserToTeam(userToAdd, team);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @ApiOperation(value = "Get Team members by Team ID")
    @GetMapping("/{id}/members")
    public List<User> getTeamMembers(@PathVariable int id) {
        try {
            Team team = teamService.getById(id);
            return teamService.getTeamMembers(team);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ApiOperation(value = "Delete Team members by User ID")
    @DeleteMapping("/{teamId}/members/{userId}")
    public void deleteTeamMember(@PathVariable int teamId,
                                 @PathVariable int userId) {
        try {
            Team team = teamService.getById(teamId);
            User user = userService.getById(userId);
            teamService.deleteTeamMember(user, team);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
