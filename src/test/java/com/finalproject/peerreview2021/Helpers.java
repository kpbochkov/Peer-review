package com.finalproject.peerreview2021;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.peerreview2021.models.Status;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.models.dto.WorkItemDto;
import com.finalproject.peerreview2021.models.*;


public class Helpers {

    public static User createMockUser() {
        var mockUser = new User();
        mockUser.setId(1);
        mockUser.setEmail("mock@user.com");
        mockUser.setUsername("MockUsername");
        mockUser.setPassword("Testparola-123");
        mockUser.setPhoneNumber(12345678);
        return mockUser;
    }

    public static WorkItem createMockWorkItem() {
        var mockWorkItem = new WorkItem();
        mockWorkItem.setId(1);
        mockWorkItem.setTitle("TestWorkItem");
        mockWorkItem.setDescription("TestDescriptionForWorkItem");
        mockWorkItem.setCreatedBy(createMockUser());
        mockWorkItem.setStatus(createMockStatus());
        mockWorkItem.setTeam(createMockTeam());
        return mockWorkItem;
    }

    public static Status createMockStatus() {
        var mockStatus = new Status();
        mockStatus.setId(1);
        mockStatus.setName("Pending");
        return mockStatus;
    }

    public static Team createMockTeam() {
        var mockTeam = new Team();
        mockTeam.setId(1);
        mockTeam.setName("Ale");
        return mockTeam;
    }

    public static WorkItemDto createValidWorkItemDto() {
        WorkItemDto dto = new WorkItemDto();
        dto.setId(1);
        dto.setTitle("valid-title-for-workItem");
        dto.setDescription("valid-title-for-workItem-that-should-be-20-chars");
        dto.setCreatedBy(createMockUser().getId());
        return dto;
    }

    /**
     * Accepts an object and returns the stringified object.
     * Useful when you need to pass a body to a HTTP request.
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
