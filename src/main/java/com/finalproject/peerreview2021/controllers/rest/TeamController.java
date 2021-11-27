package com.finalproject.peerreview2021.controllers.rest;

import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.exceptions.UpdateEntityException;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.dto.TeamDto;
import com.finalproject.peerreview2021.services.contracts.TeamService;
import com.finalproject.peerreview2021.services.modelmappers.TeamModelMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamModelMapper teamModelMapper;

    @Autowired
    public TeamController(TeamService teamService, TeamModelMapper teamModelMapper) {
        this.teamService = teamService;
        this.teamModelMapper = teamModelMapper;
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

}
