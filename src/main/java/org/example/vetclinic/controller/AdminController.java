package org.example.vetclinic.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.vetclinic.security.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Controller responsible for handling requests related to the admin section of the application.
 * This controller maps routes related to the admin's menu and ensures that the appropriate data is available
 * to the views.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    /**
     * Handles GET requests to the "/menu" URL for the admin menu page.
     * This method retrieves the JWT token from the session (if available) and the current user's details,
     * and passes them to the view (admin menu) for rendering.
     *
     * @param session the HTTP session, used to retrieve the JWT token.
     * @param model the model used to pass attributes to the view.
     * @param currentUser the currently authenticated user, injected via the {@link AuthenticationPrincipal} annotation.
     * @return the name of the view to render (admin menu).
     */
    @GetMapping("/menu")
    public String menu(HttpSession session, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        String token = (String) session.getAttribute("token");
        if (token != null && !token.isEmpty()) {
            model.addAttribute("token", token);
        }

        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser.getUser());
        }
        return "/admin/menu";
    }

}
