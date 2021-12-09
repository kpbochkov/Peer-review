package com.finalproject.peerreview2021.controllers.mvc;

import com.finalproject.peerreview2021.controllers.AuthenticationHelper;
import com.finalproject.peerreview2021.exceptions.AuthenticationFailureException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.TeamInvitation;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.wrappers.TeamWorkItemsWrapper;
import com.finalproject.peerreview2021.services.contracts.TeamInvitationService;
import com.finalproject.peerreview2021.services.contracts.TeamService;
import com.finalproject.peerreview2021.services.contracts.UserService;
import com.finalproject.peerreview2021.services.contracts.WorkItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final UserService userService;
    private final TeamInvitationService teamInvitationService;

    public TeamMvcController(AuthenticationHelper authenticationHelper, TeamService teamService, WorkItemService workItemService, UserService userService, TeamInvitationService teamInvitationService) {
        this.authenticationHelper = authenticationHelper;
        this.teamService = teamService;
        this.workItemService = workItemService;
        this.userService = userService;
        this.teamInvitationService = teamInvitationService;
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
        List<User> allUsers = userService.getAll();
        for (Team team : teamService.getUserTeams(user)) {
            TeamWorkItemsWrapper wrapper = new TeamWorkItemsWrapper();
            wrapper.setTeam(team);
            wrapper.setWorkItems(workItemService.getAllWorkItemsForTeam(team));
            List<User> possibleInvitees = new ArrayList<>(allUsers);
            possibleInvitees.removeAll(team.getMembers());
            wrapper.setPossibleInvitees(possibleInvitees);
            teamsWorkItems.add(wrapper);
        }
        model.addAttribute("teamsWorkItems", teamsWorkItems);
        return "teams";
    }

    @GetMapping("/{teamId}/invite/{userId}")
    public String inviteUser(@PathVariable int teamId,
                             @PathVariable int userId,
                             Model model,
                             HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
            TeamInvitation teamInvitation = new TeamInvitation();
            User userToInvite = userService.getById(userId);
            teamInvitation.setTeam(teamService.getById(teamId));
            teamInvitation.setUser(userToInvite);
            teamInvitationService.create(user, teamInvitation);
            return "redirect:/teams";
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
    }

    @GetMapping("/accept/{invitationId}")
    public String acceptInvitation(@PathVariable int invitationId, HttpSession session, Model model) {
        try {
            User user = authenticationHelper.tryGetUser(session);
            TeamInvitation teamInvitation = teamInvitationService.getById(invitationId);
            teamInvitationService.accept(user, teamInvitation);
            return "redirect:/dashboard";
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
    }

    @GetMapping("/decline/{invitationId}")
    public String declineInvitation(@PathVariable int invitationId, HttpSession session, Model model) {
        try {
            User user = authenticationHelper.tryGetUser(session);
            TeamInvitation teamInvitation = teamInvitationService.getById(invitationId);
            teamInvitationService.decline(user, teamInvitation);
            return "redirect:/dashboard";
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
    }

    @GetMapping("/leave/{teamId}")
    public String leaveTeam(@PathVariable int teamId, HttpSession session, Model model) {
        try {
            User user = authenticationHelper.tryGetUser(session);
            Team team = teamService.getById(teamId);
            teamService.leaveTeam(user, team);
            return "redirect:/teams";
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
    }
}
