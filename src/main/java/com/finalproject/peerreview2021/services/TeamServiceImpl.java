package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.exceptions.UpdateEntityException;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.repositories.contracts.TeamRepository;
import com.finalproject.peerreview2021.services.contracts.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void create(Team entity) {
        teamRepository.create(entity);
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
        if (!Objects.equals(entityInRepository.getName(), entity.getName())) {
            throw new UpdateEntityException("Team", "name");
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

}
