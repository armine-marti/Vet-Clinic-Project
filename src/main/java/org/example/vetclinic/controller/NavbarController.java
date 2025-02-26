package org.example.vetclinic.controller;

import org.springframework.stereotype.Controller;
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
    public String contactUs() {
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
