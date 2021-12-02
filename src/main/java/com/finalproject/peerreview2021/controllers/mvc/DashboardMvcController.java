package com.finalproject.peerreview2021.controllers.mvc;

import com.finalproject.peerreview2021.controllers.AuthenticationHelper;
import com.finalproject.peerreview2021.exceptions.AuthenticationFailureException;
import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardMvcController {

    private final AuthenticationHelper authenticationHelper;

    public DashboardMvcController(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public String showDashboardPage(Model model,
                                   HttpSession session) {
        try {
             authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
        return "index";
    }
}



