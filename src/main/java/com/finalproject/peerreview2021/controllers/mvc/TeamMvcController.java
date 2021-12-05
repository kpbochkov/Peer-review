package com.finalproject.peerreview2021.controllers.mvc;

import com.finalproject.peerreview2021.controllers.AuthenticationHelper;
import com.finalproject.peerreview2021.exceptions.AuthenticationFailureException;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.wrappers.TeamWorkItemsWrapper;
import com.finalproject.peerreview2021.services.contracts.TeamService;
import com.finalproject.peerreview2021.services.contracts.WorkItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 05.12.2021
 * Time: 20:13
 */
@Controller
@RequestMapping("/teams")
public class TeamMvcController {

    private final AuthenticationHelper authenticationHelper;
    private final TeamService teamService;
    private final WorkItemService workItemService;

    public TeamMvcController(AuthenticationHelper authenticationHelper, TeamService teamService, WorkItemService workItemService) {
        this.authenticationHelper = authenticationHelper;
        this.teamService = teamService;
        this.workItemService = workItemService;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping()
    public String showTeamsPage(Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        }
        List<TeamWorkItemsWrapper> teamsWorkItems = new ArrayList<>();
        for (Team team : teamService.getUserTeams(user)) {
            TeamWorkItemsWrapper wrapper = new TeamWorkItemsWrapper();
            wrapper.setTeam(team);
            wrapper.setWorkItems(workItemService.getAllWorkItemsForTeam(team));
            teamsWorkItems.add(wrapper);
        }
        model.addAttribute("teamsWorkItems", teamsWorkItems);
        return "teams";
    }
}
