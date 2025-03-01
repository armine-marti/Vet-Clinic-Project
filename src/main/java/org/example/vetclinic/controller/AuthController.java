package org.example.vetclinic.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.vetclinic.config.jwt.JwtTokenUtil;
import org.example.vetclinic.dto.user.SaveUserRequest;
import org.example.vetclinic.dto.user.UserAuthRequest;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.mapper.UserMapper;
import org.example.vetclinic.security.CurrentUser;
import org.example.vetclinic.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/")
    public String mainPage() {
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
        if (userService.existsByEmail(saveUserRequest.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Please use another email!");
            return "redirect:/auth/register";
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Please input correct data");
            return "register";
        }
        String encodedPassword = passwordEncoder.encode(saveUserRequest.getPassword());
        saveUserRequest.setPassword(encodedPassword);
        User user = userMapper.toEntity(saveUserRequest);
        userService.save(user);
        redirectAttributes.addFlashAttribute("success", "Registration successful!");
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("userAuthRequest", new UserAuthRequest());

        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserAuthRequest userAuthRequest, HttpSession session) {

        if (userAuthRequest.getEmail() == null || userAuthRequest.getEmail().isEmpty()) {
            System.out.println("empty email");
            return "redirect:/login?error";
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userAuthRequest.getEmail(), userAuthRequest.getPassword());
            Authentication newAuthentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(userAuthRequest.getEmail());
            String jwt = jwtTokenUtil.generateToken(userDetails);
            session.setAttribute("token", jwt);
            CurrentUser currentUser = (CurrentUser) userDetails;
            session.setAttribute("currentUser", currentUser);
            return redirectBasedOnRole(newAuthentication);
        } catch (BadCredentialsException e) {
            return "redirect:/login?error";
        }
    }

    private String redirectBasedOnRole(Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/adminMenu";
        } else if (authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_USER"))) {
            return "redirect:/users/menu";
        } else {
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/auth/login";
    }
}