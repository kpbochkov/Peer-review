package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.repositories.contracts.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.finalproject.peerreview2021.Helpers;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 04.12.2021
 * Time: 11:08
 */

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    UserRepository mockRepository;

    @InjectMocks
    UserServiceImpl service;

    @Test
    void create_shouldThrow_when_duplicateNameExists(){
        // Arrange
        User userInRepo = Helpers.createMockUser();
        User userToBeCreated = Helpers.createMockUser();
        userToBeCreated.setId(0);
        Mockito.when(mockRepository.getByField("username",
                userToBeCreated.getUsername())).thenReturn(userInRepo);
        Mockito.when(mockRepository.getByField("email",
                userToBeCreated.getEmail())).thenThrow(EntityNotFoundException.class);
        Mockito.when(mockRepository.getByField("phoneNumber",
                userToBeCreated.getPhoneNumber())).thenThrow(EntityNotFoundException.class);
        // Act & Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> service.create(userToBeCreated));
    }

    @Test
    void create_shouldThrow_when_duplicateEmailExists(){
        // Arrange
        User userInRepo = Helpers.createMockUser();
        User userToBeCreated = Helpers.createMockUser();
        userToBeCreated.setId(0);
        Mockito.when(mockRepository.getByField("username",
                userToBeCreated.getUsername())).thenThrow(EntityNotFoundException.class);
        Mockito.when(mockRepository.getByField("email",
                userToBeCreated.getEmail())).thenReturn(userInRepo);
        Mockito.when(mockRepository.getByField("phoneNumber",
                userToBeCreated.getPhoneNumber())).thenThrow(EntityNotFoundException.class);
        // Act & Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> service.create(userToBeCreated));
    }

    @Test
    void create_shouldThrow_when_duplicatePhoneExists(){
        // Arrange
        User userInRepo = Helpers.createMockUser();
        User userToBeCreated = Helpers.createMockUser();
        userToBeCreated.setId(0);
        Mockito.when(mockRepository.getByField("username",
                userToBeCreated.getUsername())).thenThrow(EntityNotFoundException.class);
        Mockito.when(mockRepository.getByField("email",
                userToBeCreated.getEmail())).thenThrow(EntityNotFoundException.class);
        Mockito.when(mockRepository.getByField("phoneNumber",
                userToBeCreated.getPhoneNumber())).thenReturn(userInRepo);
        // Act & Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> service.create(userToBeCreated));
    }

    @Test
    void create_shouldCreate_when_noDuplicateExists(){
        // Arrange
        User userToBeCreated = Helpers.createMockUser();
        userToBeCreated.setId(0);
        Mockito.when(mockRepository.getByField("username",
                userToBeCreated.getUsername())).thenThrow(EntityNotFoundException.class);
        Mockito.when(mockRepository.getByField("email",
                userToBeCreated.getEmail())).thenThrow(EntityNotFoundException.class);
        Mockito.when(mockRepository.getByField("phoneNumber",
                userToBeCreated.getPhoneNumber())).thenThrow(EntityNotFoundException.class);
        // Act
        service.create(userToBeCreated);
        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .create(userToBeCreated);
    }

    @Test
    void update_shouldUpdate_when_noDuplicateExists(){
        // Arrange
        User userToBeUpdated = Helpers.createMockUser();
        Mockito.when(mockRepository.getByField("username",
                userToBeUpdated.getUsername())).thenThrow(EntityNotFoundException.class);
        Mockito.when(mockRepository.getByField("email",
                userToBeUpdated.getEmail())).thenThrow(EntityNotFoundException.class);
        Mockito.when(mockRepository.getByField("phoneNumber",
                userToBeUpdated.getPhoneNumber())).thenThrow(EntityNotFoundException.class);
        // Act
        service.update(userToBeUpdated);
        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .update(userToBeUpdated);
    }

    @Test
    void getAll_should_callRepository() {
        // Arrange
        Mockito.when(mockRepository.getAll())
                .thenReturn(new ArrayList<>());

        // Act
        service.getAll();

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .getAll();
    }


}
