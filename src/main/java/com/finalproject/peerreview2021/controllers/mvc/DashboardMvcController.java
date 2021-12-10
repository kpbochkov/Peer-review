package com.finalproject.peerreview2021.controllers.mvc;

import com.finalproject.peerreview2021.controllers.AuthenticationHelper;
import com.finalproject.peerreview2021.exceptions.AuthenticationFailureException;
import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.services.contracts.ReviewerService;
import com.finalproject.peerreview2021.services.contracts.TeamInvitationService;
import com.finalproject.peerreview2021.services.contracts.UserService;
import com.finalproject.peerreview2021.services.contracts.WorkItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardMvcController {

    private final AuthenticationHelper authenticationHelper;
    private final TeamInvitationService teamInvitationService;
    private final WorkItemService workItemService;
    private final ReviewerService reviewerService;

    public DashboardMvcController(AuthenticationHelper authenticationHelper,
                                  UserService userService, TeamInvitationService teamInvitationService,
                                  WorkItemService workItemService, ReviewerService reviewerService) {
        this.authenticationHelper = authenticationHelper;
        this.teamInvitationService = teamInvitationService;
        this.workItemService = workItemService;
        this.reviewerService = reviewerService;
    }

//    @ModelAttribute("getPhoto")
//    public String getPhoto(HttpSession session) {
//        User user = authenticationHelper.tryGetUser(session);
//        return user.getStringPhoto(user.getPhoto());
//    }

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
        List<WorkItem> userWorkItems = workItemService.getAllWorkItemsForUser(user);
        List<Reviewer> allReviewsForUserWorkItems = userWorkItems.stream()
                .map(reviewerService::getAllReviewersForWorkItem)
                .flatMap(List::stream).collect(Collectors.toList());
        List<Reviewer> userReviews = reviewerService.getAllReviewersForUser(user);
        long completedReviews = userReviews.stream().filter(r -> r.getStatus().getId()>2).count();
        model.addAttribute("teamInvitations", teamInvitationService.getUserInvitations(user));
        model.addAttribute("pendingWorkitems", userReviews.
                stream().filter(w -> w.getStatus().getId()==1).count());
        model.addAttribute("completedReviews", completedReviews);
        model.addAttribute("completedReviewsPercentage", Math.round(((float)completedReviews/userReviews.size())*100));
        model.addAttribute("timesChangeRequested", allReviewsForUserWorkItems.stream()
                .filter(r -> r.getStatus().getId()==3).count());
        model.addAttribute("currentUser", user);

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



