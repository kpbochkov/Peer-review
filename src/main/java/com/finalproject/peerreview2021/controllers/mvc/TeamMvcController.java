package com.finalproject.peerreview2021.controllers.mvc;

import com.finalproject.peerreview2021.controllers.AuthenticationHelper;
import com.finalproject.peerreview2021.exceptions.AuthenticationFailureException;
import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.TeamInvitation;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.dto.TeamDto;
import com.finalproject.peerreview2021.models.wrappers.TeamWorkItemsWrapper;
import com.finalproject.peerreview2021.services.contracts.TeamInvitationService;
import com.finalproject.peerreview2021.services.contracts.TeamService;
import com.finalproject.peerreview2021.services.contracts.UserService;
import com.finalproject.peerreview2021.services.contracts.WorkItemService;
import com.finalproject.peerreview2021.services.modelmappers.TeamModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    private final TeamModelMapper teamModelMapper;

    public TeamMvcController(AuthenticationHelper authenticationHelper,
                             TeamService teamService, WorkItemService workItemService,
                             UserService userService, TeamInvitationService teamInvitationService,
                             TeamModelMapper teamModelMapper) {
        this.authenticationHelper = authenticationHelper;
        this.teamService = teamService;
        this.workItemService = workItemService;
        this.userService = userService;
        this.teamInvitationService = teamInvitationService;
        this.teamModelMapper = teamModelMapper;
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
            possibleInvitees.removeAll(teamInvitationService.getInvitedUsers(team));
            wrapper.setPossibleInvitees(possibleInvitees);
            teamsWorkItems.add(wrapper);
        }
        model.addAttribute("teamsWorkItems", teamsWorkItems);
        return "teams";
    }

    @GetMapping("/new")
    public String showNewTeamPage(Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
        model.addAttribute("team", new TeamDto());
        return "team-new";
    }

    @PostMapping("/new")
    public String createTeam(@Valid @ModelAttribute("team") TeamDto teamDto,
                             BindingResult errors, Model model,
                             HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
        if (errors.hasErrors()) {
            return "team-new";
        }

        try {
            Team team = teamModelMapper.fromDto(teamDto, user);
            teamService.create(team);
            return "redirect:/teams";
        } catch (DuplicateEntityException e) {
            errors.rejectValue("name", "duplicate_team", e.getMessage());
            return "team-new";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
    }

    @GetMapping("/{id}")
    public String showSingleTeam(@PathVariable int id, Model model,
                                 HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
        try {
            Team team = teamService.getById(id);
            model.addAttribute("team", team);
            model.addAttribute("members", userService.getMembersWithoutCurrentUser(team, user));
            model.addAttribute("currentUser", user);
            return "team";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @GetMapping("/{id}/update")
    public String showEditTeam(@PathVariable int id, Model model,
                               HttpSession session) {
        try {
            Team team = teamService.getById(id);
            TeamDto teamDto = teamModelMapper.toDto(team);
            model.addAttribute("teamId", id);
            model.addAttribute("team", teamDto);
            return "team-update";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @PostMapping("/{id}/update")
    public String updateTeam(@PathVariable int id,
                             @Valid @ModelAttribute("team") TeamDto teamDto,
                             BindingResult errors,
                             Model model,
                             HttpSession session) {
        try {
            Team team = teamModelMapper.fromDto(teamDto, id);
            teamService.update(team);

            return "redirect:/teams";
        } catch (DuplicateEntityException e) {
            errors.rejectValue("name", "duplicate_team", e.getMessage());
            return "team-update";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteTeam(@PathVariable int id, Model model,
                             HttpSession session) {
        try {
            teamService.delete(id);

            return "redirect:/teams";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
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

    @GetMapping("{teamId}/delete/{userId}")
    public String deleteUser(@PathVariable int userId,
                             @PathVariable int teamId,HttpSession session, Model model) {
        try {
            User user = authenticationHelper.tryGetUser(session);
            Team team = teamService.getById(teamId);
            teamService.deleteTeamMember(user, team);
            return "redirect:/teams";
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
    }
}
