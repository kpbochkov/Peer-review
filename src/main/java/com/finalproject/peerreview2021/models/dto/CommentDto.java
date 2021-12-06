package com.finalproject.peerreview2021.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentDto {
    @JsonIgnore
    private Integer id;

    @NotNull(message = "Comment must have a creator")
    private int createdBy;

    @NotNull(message = "Comment must have a Work Item")
    private int workItemId;

    @NotNull(message = "Comment can't be empty")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(int workItemId) {
        this.workItemId = workItemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
