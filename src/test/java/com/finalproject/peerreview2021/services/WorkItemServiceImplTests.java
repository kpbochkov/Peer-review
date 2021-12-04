package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.Helpers;
import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.repositories.contracts.WorkItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static com.finalproject.peerreview2021.Helpers.createMockUser;
import static com.finalproject.peerreview2021.Helpers.createMockWorkItem;

@ExtendWith(MockitoExtension.class)
public class WorkItemServiceImplTests {

    @Mock
    WorkItemRepository mockRepository;

    @InjectMocks
    WorkItemServiceImpl service;

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

    @Test
    void filter_should_callRepository() {
        // Arrange
        Mockito.when(mockRepository.filter(Optional.empty(), Optional.empty(),
                        Optional.empty(), Optional.empty()))
                .thenReturn(new ArrayList<>());

        // Act
        service.filter(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .filter(Optional.empty(), Optional.empty(),
                        Optional.empty(), Optional.empty());
    }

    @Test
    public void getById_should_returnWorkItem_when_matchExists() {
        // Arrange
        WorkItem mockWorkItem = createMockWorkItem();
        Mockito.when(mockRepository.getById(mockWorkItem.getId()))
                .thenReturn(mockWorkItem);
        // Act
        WorkItem result = service.getById(mockWorkItem.getId());

        // Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(mockWorkItem.getId(), result.getId()),
                () -> Assertions.assertEquals(mockWorkItem.getTitle(), result.getTitle()),
                () -> Assertions.assertEquals(mockWorkItem.getDescription(), result.getDescription()),
                () -> Assertions.assertEquals(mockWorkItem.getCreatedBy(), result.getCreatedBy()),
                () -> Assertions.assertEquals(mockWorkItem.getTeam(), result.getTeam())
        );
    }

    @Test
    public void create_should_throw_when_workItemWithSameTitleExists() {
        // Arrange
        WorkItem mockWorkItem = createMockWorkItem();

        Mockito.when(mockRepository.getByField("title", mockWorkItem.getTitle()))
                .thenReturn(mockWorkItem);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockWorkItem));
    }

    @Test
    public void create_should_callRepository_when_workItemWithSameTitleDoesNotExist() {
        // Arrange
        WorkItem mockWorkItem = createMockWorkItem();

        Mockito.when(mockRepository.getByField("title", mockWorkItem.getTitle()))
                .thenThrow(EntityNotFoundException.class);

        // Act
        service.create(mockWorkItem);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .create(mockWorkItem);
    }

    @Test
    void create_shouldCreate_when_noDuplicateExists(){
        // Arrange
        WorkItem workItemToBeCreated = createMockWorkItem();
        workItemToBeCreated.setId(0);
        Mockito.when(mockRepository.getByField("title",
                workItemToBeCreated.getTitle())).thenThrow(EntityNotFoundException.class);
        // Act
        service.create(workItemToBeCreated);
        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .create(workItemToBeCreated);
    }


    @Test
    public void update_should_throwException_when_workItemTitleIsTaken() {
        // Arrange
        WorkItem mockWorkItem = createMockWorkItem();
        mockWorkItem.setTitle("test-title-with-twenty-chars");
        WorkItem workItemToBeUpdated = createMockWorkItem();
        workItemToBeUpdated.setId(2);
        workItemToBeUpdated.setTitle("test-title-with-twenty-chars");

        Mockito.when(mockRepository.getByField("title", workItemToBeUpdated.getTitle()))
                .thenReturn(workItemToBeUpdated);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> service.update(mockWorkItem));
    }


    @Test
    void update_shouldUpdate_when_noDuplicateExists() {
        // Arrange
        WorkItem workItemToBeUpdated = createMockWorkItem();
        Mockito.when(mockRepository.getByField("title",
                workItemToBeUpdated.getTitle())).thenThrow(EntityNotFoundException.class);
        Mockito.when(mockRepository.getById(workItemToBeUpdated.getId())).thenReturn(workItemToBeUpdated);
//        Mockito.when(mockRepository.getByField("phoneNumber",
//                userToBeUpdated.getPhoneNumber())).thenThrow(EntityNotFoundException.class);
        // Act
        service.update(workItemToBeUpdated);
        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .update(workItemToBeUpdated);
    }

}
