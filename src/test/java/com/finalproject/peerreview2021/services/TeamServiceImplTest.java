package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.Helpers;
import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.repositories.contracts.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 04.12.2021
 * Time: 12:02
 */

@ExtendWith(MockitoExtension.class)
public class TeamServiceImplTest {
    @Mock
    TeamRepository mockRepository;

    @InjectMocks
    TeamServiceImpl service;

    @Test
    void create_shouldThrow_when_duplicateNameExists(){
        // Arrange
        Team teamInRepo = Helpers.createMockTeam();
        Team teamToBeCreated = Helpers.createMockTeam();
        teamToBeCreated.setId(0);
        Mockito.when(mockRepository.getByField("name",
                teamToBeCreated.getName())).thenReturn(teamInRepo);
        // Act & Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> service.create(teamToBeCreated));
    }

    @Test
    void create_shouldCreate_when_noDuplicateExists(){
        // Arrange
        Team teamToBeCreated = Helpers.createMockTeam();
        teamToBeCreated.setId(0);
        Mockito.when(mockRepository.getByField("name",
                teamToBeCreated.getName())).thenThrow(EntityNotFoundException.class);
        // Act
        service.create(teamToBeCreated);
        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .create(teamToBeCreated);
    }

    @Test
    void addUserToTeam_should_throw_when_userAlreadyInTeam(){
        // Arrange
        User user = Helpers.createMockUser();
        Team team = Helpers.createMockTeam();
        team.setMembers(Set.of(user));
        // Act & Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> service.addUserToTeam(user, team));
    }

    @Test
    void addUserToTeam_should_add_when_userNotInTeam() {
        // Arrange
        User user = Helpers.createMockUser();
        Team team = Helpers.createMockTeam();
        team.setMembers(new HashSet<>());
        // Act
        service.addUserToTeam(user, team);
        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .update(team);
    }
}
