package org.example.vetclinic.controller;

import org.example.vetclinic.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/navbar")
public class NavbarController {

    @GetMapping("/aboutUs")
    public String aboutUs() {
        return "navbar/aboutUs";
    }

    @GetMapping("/contactUs")
    public String contactUs(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("userType", currentUser.getUserType());
        return "navbar/contactUs";
    }

    @GetMapping("/ourDoctors")
    public String ourDoctors() {
        return "navbar/ourDoctors";
    }

    @GetMapping("/ourServices")
    public String ourServices() {
        return "navbar/ourServices";
    }

}
