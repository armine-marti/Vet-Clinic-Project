package org.example.vetclinic.controller;

import org.example.vetclinic.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The {@code NavbarController} class is responsible for handling requests related to the navigation bar.
 * It provides endpoints for the different sections in the navbar, including "About Us", "Contact Us",
 * "Our Doctors", and "Our Services".
 * <p>
 * This controller uses the {@code @AuthenticationPrincipal} annotation to get the current user's information
 * and add the user type to the model to be used in the views.
 * </p>
 */
@Controller
@RequestMapping("/navbar")
public class NavbarController {

    /**
     * Handles the request for the "About Us" section of the navbar.
     *
     * @param currentUser the current authenticated user.
     * @param model       the model object used to pass data to the view.
     * @return the name of the "About Us" view template.
     */
    @GetMapping("/aboutUs")
    public String aboutUs(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("userType", currentUser.getUserType());
        return "navbar/aboutUs";
    }

    /**
     * Handles the request for the "Contact Us" section of the navbar.
     *
     * @param currentUser the current authenticated user.
     * @param model       the model object used to pass data to the view.
     * @return the name of the "Contact Us" view template.
     */
    @GetMapping("/contactUs")
    public String contactUs(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("userType", currentUser.getUserType());
        return "navbar/contactUs";
    }

    /**
     * Handles the request for the "Our Doctors" section of the navbar.
     *
     * @param currentUser the current authenticated user.
     * @param model       the model object used to pass data to the view.
     * @return the name of the "Our Doctors" view template.
     */
    @GetMapping("/ourDoctors")
    public String ourDoctors(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("userType", currentUser.getUserType());
        return "navbar/ourDoctors";
    }

    /**
     * Handles the request for the "Our Services" section of the navbar.
     *
     * @param currentUser the current authenticated user.
     * @param model       the model object used to pass data to the view.
     * @return the name of the "Our Services" view template.
     */
    @GetMapping("/ourServices")
    public String ourServices(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("userType", currentUser.getUserType());
        return "navbar/ourServices";
    }

}
