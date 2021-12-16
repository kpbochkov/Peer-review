package com.finalproject.peerreview2021.controllers.mvc;

import com.finalproject.peerreview2021.controllers.AuthenticationHelper;
import com.finalproject.peerreview2021.models.Notification;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.services.contracts.NotificationService;
import com.finalproject.peerreview2021.services.contracts.ReviewerService;
import com.finalproject.peerreview2021.services.contracts.WorkItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 07.12.2021
 * Time: 17:46
 */
@Controller
@RequestMapping("/reviews")
public class ReviewMvcController {

    private final AuthenticationHelper authenticationHelper;
    private final WorkItemService workItemService;
    private final ReviewerService reviewerService;
    private final NotificationService notificationService;

    public ReviewMvcController(AuthenticationHelper authenticationHelper, WorkItemService workItemService,
                               ReviewerService reviewerService, NotificationService notificationService) {
        this.authenticationHelper = authenticationHelper;
        this.workItemService = workItemService;
        this.reviewerService = reviewerService;
        this.notificationService = notificationService;
    }

    @ModelAttribute("getPhoto")
    public String getPhoto(HttpSession session) {
        User user = authenticationHelper.tryGetUser(session);
        return user.getImage();
    }

    @ModelAttribute("newNotificationsCount")
    public long newNotificationsCount(HttpSession session) {
        User user = authenticationHelper.tryGetUser(session);
        List<Notification> notifications = notificationService.getUserNotifications(user);
        return notifications.stream().filter(n -> n.getSeen().equals(false)).count();
    }

    @GetMapping()
    public String showAllWorkItems(Model model, HttpSession session) {
        User user = authenticationHelper.tryGetUser(session);
        model.addAttribute("workitems", workItemService.getAllWorkItemsForReviewer(user));
        model.addAttribute("notifications", notificationService.getUserNotifications(user));

        return "workitems-user";
    }
}
