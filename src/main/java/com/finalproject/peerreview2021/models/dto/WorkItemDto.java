package com.finalproject.peerreview2021.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.peerreview2021.models.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 23.11.2021
 * Time: 12:24
 */
public class WorkItemDto {
    @JsonIgnore
    private Integer id;

    @NotNull(message = "Work item name can't be empty")
    @Size(min = 3, max = 30, message = "Work item name should be between 3 and 30 symbols")
    private String name;

    @NotNull(message = "Work item title can't be empty")
    @Size(min = 10, max = 80, message = "Work item title should be between 10 and 80 symbols")
    private String title;

    @NotNull(message = "Work item description can't be empty")
    @Size(min = 20, message = "Work item description should be at least 20 symbols")
    private String description;

    @NotNull(message = "Work item must have a creator")
    private int createdBy;

    @NotNull(message = "Work item must belong to a team")
    private int teamId;

    private List<Integer> reviewers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public List<Integer> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<Integer> reviewers) {
        this.reviewers = reviewers;
    }
}
