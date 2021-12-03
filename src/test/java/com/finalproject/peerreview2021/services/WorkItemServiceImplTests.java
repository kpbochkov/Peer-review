package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
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
    public void create_should_throw_when_workItemWithSameNameExists() {
        // Arrange
        WorkItem mockWorkItem = createMockWorkItem();

        Mockito.when(mockRepository.getByField("title", mockWorkItem.getTitle()))
                .thenReturn(mockWorkItem);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockWorkItem));
    }

    @Test
    public void create_should_callRepository_when_beerWithSameNameDoesNotExist() {
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
    public void update_should_throwException_when_workItemTitleIsTaken() {
        // Arrange
        WorkItem mockWorkItem = createMockWorkItem();
        mockWorkItem.setTitle("test-title-with-twenty-chars");
        WorkItem anotherMockWorkItem = createMockWorkItem();
        anotherMockWorkItem.setId(2);
        anotherMockWorkItem.setTitle("test-title-with-twenty-chars");

        Mockito.when(mockRepository.getById(Mockito.anyInt()))
                .thenReturn(mockWorkItem);

        Mockito.when(mockRepository.getByField("title", anotherMockWorkItem.getTitle()))
                .thenReturn(anotherMockWorkItem);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> service.update(mockWorkItem));
    }
//
//    @Test
//    public void update_should_callRepository_when_tryingToUpdateExistingBeer() {
//        // Arrange
//        Beer mockBeer = createMockBeer();
//
//        Mockito.when(mockRepository.getById(Mockito.anyInt()))
//                .thenReturn(mockBeer);
//
//        Mockito.when(mockRepository.getByName(Mockito.anyString()))
//                .thenReturn(mockBeer);
//
//
//        // Act
//        service.update(mockBeer, mockBeer.getCreatedBy());
//
//        // Assert
//        Mockito.verify(mockRepository, Mockito.times(1))
//                .update(mockBeer);
//    }
//
//    @Test
//    void delete_should_throwException_when_initiatorIsNotAdminOrCreator() {
//        // Arrange
//        Beer mockBeer = createMockBeer();
//        User mockInitiator = createMockUser();
//        mockInitiator.setUsername("MockInitiator");
//
//        Mockito.when(mockRepository.getById(mockBeer.getId()))
//                .thenReturn(mockBeer);
//
//        // Act, Assert
//        Assertions.assertThrows(UnauthorizedOperationException.class,
//                () -> service.delete(mockBeer.getId(), mockInitiator));
//    }
//
//    @Test
//    void delete_should_callRepository_when_initiatorIsAdminAndIsNotCreator() {
//        // Arrange
//        Beer mockBeer = createMockBeer();
//        User mockInitiator = createMockAdmin();
//        mockInitiator.setUsername("MockInitiator");
//
//        Mockito.when(mockRepository.getById(mockBeer.getId()))
//                .thenReturn(mockBeer);
//
//        // Act
//        service.delete(mockBeer.getId(), mockInitiator);
//
//        // Assert
//        Mockito.verify(mockRepository, Mockito.times(1))
//                .delete(mockBeer.getId());
//    }
//
//    @Test
//    void delete_should_callRepository_when_initiatorIsNotAdminAndIsCreator() {
//        // Arrange
//        Beer mockBeer = createMockBeer();
//
//        Mockito.when(mockRepository.getById(mockBeer.getId()))
//                .thenReturn(mockBeer);
//
//        // Act
//        service.delete(mockBeer.getId(), mockBeer.getCreatedBy());
//
//        // Assert
//        Mockito.verify(mockRepository, Mockito.times(1))
//                .delete(mockBeer.getId());
//    }
}
