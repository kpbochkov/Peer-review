package com.finalproject.peerreview2021.controllers.mvc;

import com.finalproject.peerreview2021.controllers.AuthenticationHelper;
import com.finalproject.peerreview2021.exceptions.AuthenticationFailureException;
import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.models.dto.ReviewerDto;
import com.finalproject.peerreview2021.models.dto.WorkItemDto;
import com.finalproject.peerreview2021.services.contracts.ReviewerService;
import com.finalproject.peerreview2021.services.contracts.TeamService;
import com.finalproject.peerreview2021.services.contracts.UserService;
import com.finalproject.peerreview2021.services.contracts.WorkItemService;
import com.finalproject.peerreview2021.services.modelmappers.WorkItemModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/workitems")
public class WorkItemMvcController {

    private final AuthenticationHelper authenticationHelper;
    private final WorkItemModelMapper workItemModelMapper;
    private final WorkItemService workItemService;
    private final ReviewerService reviewerService;
    private final UserService userService;
    private final TeamService teamService;


    public WorkItemMvcController(AuthenticationHelper authenticationHelper, WorkItemModelMapper workItemModelMapper,
                                 WorkItemService workItemService, TeamService teamService,
                                 ReviewerService reviewerService, UserService userService) {
        this.authenticationHelper = authenticationHelper;
        this.workItemModelMapper = workItemModelMapper;
        this.workItemService = workItemService;
        this.reviewerService = reviewerService;
        this.userService = userService;
        this.teamService = teamService;
    }

    @GetMapping()
    public String showAllWorkItems(Model model, HttpSession session) {
        User user = authenticationHelper.tryGetUser(session);
        model.addAttribute("workitems", workItemService.getAllWorkItemsForUser(user));
        return "workitems-user";
    }

    @GetMapping("/new")
    public String showNewWorkItemPage(Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
        model.addAttribute("workitem", new WorkItemDto());
        model.addAttribute("teams", user.getTeams());
        return "workitem-new";
    }

    @PostMapping("/new")
    public String createWorkItem(@Valid @ModelAttribute("workitem") WorkItemDto workItemDto,
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
            return "workitem-new";
        }

        try {
            WorkItem workItem = workItemModelMapper.fromDto(workItemDto);
            workItem.setCreatedBy(user);
            workItemService.create(workItem);
            return "redirect:/dashboard";
        } catch (DuplicateEntityException e) {
            errors.rejectValue("name", "duplicate_workitem", e.getMessage());
            return "workitem-new";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
    }

    @GetMapping("/{id}")
    public String showSingleWorkItem(@ModelAttribute("reviewer") ReviewerDto reviewerDto,
                                     @PathVariable int id, Model model,
                                     HttpSession session) {
        try {
            authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
        try {
            WorkItem workItem = workItemService.getById(id);
            Team team = workItem.getTeam();
            model.addAttribute("workitem", workItem);
            model.addAttribute("members", teamService.getTeamMembers(team));
            return "workitem";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @PostMapping("/reviewer/{workItemId}")
    public String addReviewer(@Valid @ModelAttribute("reviewer") ReviewerDto reviewerDto,
                              @PathVariable int workItemId,
                              BindingResult errors, Model model,
                              HttpSession session) {
        if (errors.hasErrors()) {
            return "workitem";
        }

        try {
            WorkItem workItem = workItemService.getById(workItemId);
            Reviewer reviewer = new Reviewer();
            reviewer.setWorkItem(workItem);
            reviewer.setUser(userService.getById(reviewerDto.getReviewerId()));
            reviewerService.create(reviewer);
            return "redirect:/workitems/" + workItemId;
        } catch (UnauthorizedOperationException e) {
            errors.rejectValue("reviewerId", "not_allowed", e.getMessage());
            return "access-denied";
        }
    }

    @GetMapping("/{id}/update")
    public String showEditWorkItemPage(@PathVariable int id, Model model,
                                       HttpSession session) {
        try {
            WorkItem workItem = workItemService.getById(id);
            WorkItemDto workItemDto = workItemModelMapper.toDto(workItem);
            model.addAttribute("workitemId", id);
            model.addAttribute("workitem", workItemDto);
            model.addAttribute("teams", workItem.getCreatedBy().getTeams());
            return "workitem-update";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @PostMapping("/{id}/update")
    public String updateShipment(@PathVariable int id,
                                 @Valid @ModelAttribute("workitem") WorkItemDto workItemDto,
                                 BindingResult errors,
                                 Model model,
                                 HttpSession session) {
        try {
            WorkItem workItem = workItemModelMapper.fromDto(workItemDto, id);
            workItemService.update(workItem);

            return "redirect:/workitems";
        } catch (DuplicateEntityException e) {
            errors.rejectValue("name", "duplicate_workitem", e.getMessage());
            return "workitem-update";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteShipment(@PathVariable int id, Model model,
                                 HttpSession session) {
        try {
            workItemService.delete(id);

            return "redirect:/workitems";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
    }
}
