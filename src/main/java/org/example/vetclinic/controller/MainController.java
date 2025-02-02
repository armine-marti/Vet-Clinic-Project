package org.example.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.dto.SaveUserRequest;
import org.example.vetclinic.dto.UserAuthRequest;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.mapper.UserMapper;
import org.example.vetclinic.security.CurrentUser;
import org.example.vetclinic.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String mainPage(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser.getUser());
        }
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("saveUserRequest", new SaveUserRequest());
        return "register";
    }


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute SaveUserRequest saveUserRequest,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "register";
        }
        String encodedPassword = passwordEncoder.encode(saveUserRequest.getPassword());
        saveUserRequest.setPassword(encodedPassword);
        User user = userMapper.toEntity(saveUserRequest);
        userService.save(user);
        redirectAttributes.addFlashAttribute("success", "Registration successful!");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("userAuthRequest", new UserAuthRequest());

        return "login";
    }

    @RequestMapping("/admin/menu")
    public String adminMenu() {
        return "admin/adminMenu";
    }

    @RequestMapping("/user/menu")
    public String userMenu() {
        return "user/userMenu";
    }

}
