package com.finalproject.peerreview2021.controllers.mvc;

import com.finalproject.peerreview2021.controllers.AuthenticationHelper;
import com.finalproject.peerreview2021.exceptions.AuthenticationFailureException;
import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.models.dto.WorkItemDto;
import com.finalproject.peerreview2021.services.contracts.WorkItemService;
import com.finalproject.peerreview2021.services.modelmappers.WorkItemModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/workitems")
public class WorkItemMvcController {

    private final AuthenticationHelper authenticationHelper;
    private final WorkItemModelMapper workItemModelMapper;
    private final WorkItemService workItemService;

    public WorkItemMvcController(AuthenticationHelper authenticationHelper, WorkItemModelMapper workItemModelMapper, WorkItemService workItemService) {
        this.authenticationHelper = authenticationHelper;
        this.workItemModelMapper = workItemModelMapper;
        this.workItemService = workItemService;
    }

    @GetMapping("/new")
    public String showNewWorkItemPage(Model model, HttpSession session) {
        try {
            authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
        model.addAttribute("workitem", new WorkItemDto());
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
}
