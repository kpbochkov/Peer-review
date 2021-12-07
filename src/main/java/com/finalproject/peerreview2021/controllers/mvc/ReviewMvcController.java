package com.finalproject.peerreview2021.controllers.mvc;

import com.finalproject.peerreview2021.controllers.AuthenticationHelper;
import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.services.contracts.ReviewerService;
import com.finalproject.peerreview2021.services.contracts.WorkItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
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

    public ReviewMvcController(AuthenticationHelper authenticationHelper, WorkItemService workItemService, ReviewerService reviewerService) {
        this.authenticationHelper = authenticationHelper;
        this.workItemService = workItemService;
        this.reviewerService = reviewerService;
    }

    @GetMapping()
    public String showAllWorkItems(Model model, HttpSession session) {
        User user = authenticationHelper.tryGetUser(session);
        model.addAttribute("workitems", workItemService.getAllWorkItemsForReviewer(user));
        return "workitems-user";
    }
}
