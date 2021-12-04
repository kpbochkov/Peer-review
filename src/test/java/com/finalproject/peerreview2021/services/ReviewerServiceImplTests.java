package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.Helpers;
import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.repositories.contracts.ReviewerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 04.12.2021
 * Time: 13:00
 */

@ExtendWith(MockitoExtension.class)
public class ReviewerServiceImplTests {

    @Mock
    ReviewerRepository mockRepository;

    @InjectMocks
    ReviewerServiceImpl service;

    @Test
    void create_should_throw_when_reviewerIsCreatorOfWorkItem(){
        // Arrange
        User user = Helpers.createMockUser();
        WorkItem workItem = Helpers.createMockWorkItem();
        workItem.setCreatedBy(user);
        Reviewer reviewer = new Reviewer();
        reviewer.setUser(user);
        reviewer.setWorkItem(workItem);
        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> service.create(reviewer));
    }

    @Test
    void create_should_throw_when_reviewerIsNotPartOfWorkItemTeam(){
        // Arrange
        User user = Helpers.createMockUser();
        user.setId(55);
        WorkItem workItem = Helpers.createMockWorkItem();
        workItem.getTeam().setMembers(Set.of());
        Reviewer reviewer = new Reviewer();
        reviewer.setUser(user);
        reviewer.setWorkItem(workItem);
        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> service.create(reviewer));
    }

    @Test
    void create_should_throw_when_reviewerAlreadyAssignedToWorkItem(){
        // Arrange
        User user = Helpers.createMockUser();
        user.setId(55);
        WorkItem workItem = Helpers.createMockWorkItem();
        workItem.getTeam().setMembers(Set.of(user));
        Reviewer reviewer = new Reviewer();
        reviewer.setUser(user);
        reviewer.setWorkItem(workItem);

        Mockito.when(mockRepository.getAllReviewersForWorkItem(workItem))
                .thenReturn(List.of(user));
        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> service.create(reviewer));
    }
}
