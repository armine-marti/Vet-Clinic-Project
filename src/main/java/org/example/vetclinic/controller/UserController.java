package org.example.vetclinic.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.vetclinic.security.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @GetMapping("/menu")
    public String menu(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token != null && !token.isEmpty()) {
            model.addAttribute("token", token);

        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof CurrentUser currentUser) {
            model.addAttribute("currentUser", currentUser.getUser());

        }
        return "/users/menu";
    }


}
