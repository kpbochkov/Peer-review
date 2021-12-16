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
}
