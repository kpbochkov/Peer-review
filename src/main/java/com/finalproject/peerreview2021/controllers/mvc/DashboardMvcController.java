package com.finalproject.peerreview2021.controllers.mvc;

import com.finalproject.peerreview2021.controllers.AuthenticationHelper;
import com.finalproject.peerreview2021.exceptions.AuthenticationFailureException;
import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.services.contracts.TeamInvitationService;
import com.finalproject.peerreview2021.services.contracts.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardMvcController {

    private final AuthenticationHelper authenticationHelper;
    private final TeamInvitationService teamInvitationService;

    public DashboardMvcController(AuthenticationHelper authenticationHelper,
                                  UserService userService, TeamInvitationService teamInvitationService) {
        this.authenticationHelper = authenticationHelper;
        this.teamInvitationService = teamInvitationService;
    }


    @GetMapping
    public String showDashboardPage(Model model,
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
        model.addAttribute("teamInvitations", teamInvitationService.getUserInvitations(user));
        return "index";
    }

    @GetMapping("/profile")
    public String profilePage(Model model,
                              HttpSession session) {
        try {
            authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
        return "profile";
    }

//    @GetMapping("/search")
//    public String search(@ModelAttribute SearchWorkItemDto searchWorkItemDto, Model model) {
//        try {
//            userService.filter(Optional.of(searchWorkItemDto.getTitle()));
//            return "user";
//
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }
//    }
}



