package com.finalproject.peerreview2021.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

public class ReviewerDto {
    @JsonIgnore
    private Integer id;

    @NotNull(message = "Work item must have a reviewer")
    private int reviewerId;

    @NotNull(message = "Reviewer must be assign to a work item")
    private int workitemId;

    @JsonIgnore
    private int statusId;

    public ReviewerDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(int reviewerId) {
        this.reviewerId = reviewerId;
    }

    public int getWorkitemId() {
        return workitemId;
    }

    public void setWorkitemId(int workitemId) {
        this.workitemId = workitemId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
