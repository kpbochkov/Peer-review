package com.finalproject.peerreview2021.controllers.mvc;

import com.finalproject.peerreview2021.controllers.AuthenticationHelper;
import com.finalproject.peerreview2021.exceptions.AuthenticationFailureException;
import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.Notification;
import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.models.dto.UserPhotoDto;
import com.finalproject.peerreview2021.services.contracts.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardMvcController {

    private final AuthenticationHelper authenticationHelper;
    private final TeamInvitationService teamInvitationService;
    private final WorkItemService workItemService;
    private final ReviewerService reviewerService;
    private final UserService userService;
    private final NotificationService notificationService;


    public DashboardMvcController(AuthenticationHelper authenticationHelper,
                                  UserService userService, TeamInvitationService teamInvitationService,
                                  WorkItemService workItemService, ReviewerService reviewerService,
                                  NotificationService notificationService) {
        this.authenticationHelper = authenticationHelper;
        this.teamInvitationService = teamInvitationService;
        this.workItemService = workItemService;
        this.reviewerService = reviewerService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @ModelAttribute("newNotificationsCount")
    public long newNotificationsCount(HttpSession session) {
        User user = authenticationHelper.tryGetUser(session);
        List<Notification> notifications = notificationService.getUserNotifications(user);
        return notifications.stream().filter(n -> n.getSeen().equals(false)).count();
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
        List<WorkItem> userWorkItems = workItemService.getAllWorkItemsForUser(user);
        List<Reviewer> allReviewsForUserWorkItems = userWorkItems.stream()
                .map(reviewerService::getAllReviewersForWorkItem)
                .flatMap(List::stream).collect(Collectors.toList());
        List<Reviewer> userReviews = reviewerService.getAllReviewersForUser(user);
        long completedReviews = userReviews.stream().filter(r -> r.getStatus().getId() > 2).count();
        model.addAttribute("teamInvitations", teamInvitationService.getUserInvitations(user));
        model.addAttribute("pendingWorkitems", userReviews.
                stream().filter(w -> w.getStatus().getId() == 1).count());
        model.addAttribute("completedReviews", completedReviews);
        model.addAttribute("completedReviewsPercentage", Math.round(((float) completedReviews / userReviews.size()) * 100));
        model.addAttribute("timesChangeRequested", allReviewsForUserWorkItems.stream()
                .filter(r -> r.getStatus().getId() == 3).count());
        model.addAttribute("currentUser", user);
        model.addAttribute("notifications", notificationService.getUserNotifications(user));
        model.addAttribute("getPhoto", user.getImage());

        return "index";
    }

    @GetMapping("/profile")
    public String profilePage(Model model,
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
        model.addAttribute("getPhoto", user.getImage());
        model.addAttribute("userPhotoDto", new UserPhotoDto());
        model.addAttribute("notifications", notificationService.getUserNotifications(user));

        return "profile";
    }

    @PostMapping("/profile")
    public String uploadPhoto(
            @RequestParam("photo") MultipartFile photo,
            Model model,
            HttpSession session) {
        try {
            User user = authenticationHelper.tryGetUser(session);
            user.store(photo);
            userService.update(user);
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/dashboard/profile";
    }

    @GetMapping("/notifications")
    public String showNotificationsPage(Model model,
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
        notificationService.markUserNotificationsSeen(user);
        List<Notification> sortedNotifications = notificationService.getUserNotifications(user);
        Collections.sort(sortedNotifications);
        model.addAttribute("notifications", sortedNotifications);
        model.addAttribute("getPhoto", user.getImage());
        return "notifications-user";
    }

    @GetMapping("/notification/{notificationId}")
    public String deleteNotification(@PathVariable int notificationId, HttpSession session, Model model) {
        try {
            User user = authenticationHelper.tryGetUser(session);
            notificationService.delete(notificationService.getById(notificationId).getId());
            return "redirect:/dashboard/notifications";
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
    }

    @GetMapping("/notification/dismissAll")
    public String dismissNotifications(Model model,
                                       HttpSession session) {
        try {
            User user = authenticationHelper.tryGetUser(session);
            notificationService.dismissAll(user);
            return "redirect:/dashboard/notifications";
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
    }
}



