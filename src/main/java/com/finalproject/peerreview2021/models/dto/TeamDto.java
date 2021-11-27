package com.finalproject.peerreview2021.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TeamDto {
    @JsonIgnore
    private Integer id;

    @NotNull(message = "Team name can not be empty")
    @Size(min = 3, max = 30, message = "Team name should be between 3 and 30 symbols")
    private String name;

    @NotNull(message = "Team must have an owner")
    private int owner;

    public TeamDto() {
    }

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

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }


}
