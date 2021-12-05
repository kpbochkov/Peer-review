package com.finalproject.peerreview2021.models.wrappers;

import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.WorkItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 05.12.2021
 * Time: 20:58
 */
public class TeamWorkItemsWrapper {
    Team team;

    List<WorkItem> workItems;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<WorkItem> getWorkItems() {
        return workItems;
    }

    public void setWorkItems(List<WorkItem> workItems) {
        this.workItems = workItems;
    }
}
