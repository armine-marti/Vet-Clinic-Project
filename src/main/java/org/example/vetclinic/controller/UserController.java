package org.example.vetclinic.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.vetclinic.dto.user.EditUserRequest;
import org.example.vetclinic.dto.user.SaveUserRequest;
import org.example.vetclinic.dto.user.UserDto;
import org.example.vetclinic.entity.StatusUser;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.mapper.UserMapper;
import org.example.vetclinic.security.CurrentUser;
import org.example.vetclinic.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/menu")
    public String menu(HttpSession session, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        String token = (String) session.getAttribute("token");
        if (token != null && !token.isEmpty()) {
            model.addAttribute("token", token);
        }
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser.getUser());
        }
        return "/users/menu";
    }

    @GetMapping("/allUsers")
    public String allUsers(ModelMap modelMap) {
        List<UserDto> users = userMapper.toDtoList(userService.getAllByStatusUser(StatusUser.ACTIVE));
        modelMap.put("users", users);
        return "users/allUsers";

    }

    @GetMapping("/editUser")
    public String editUser(@RequestParam("email") String email,
                           ModelMap modelMap) {
        User userOrNull = userService.getByEmail(email);
        if (userOrNull != null) {
            modelMap.put("editUserRequest", userMapper.toEditUserRequest(userOrNull));
            return "users/editUser";
        }
        return "users/allUsers";
    }

    @PostMapping("/editUser")
    public String editUser(
            @Valid @ModelAttribute EditUserRequest editUserRequest,
            @RequestParam("oldEmail") String oldEmail,
            BindingResult bindingResult,
            ModelMap modelMap) {

        if (bindingResult.hasErrors()) {
            modelMap.put("bindingResult", bindingResult);
            return "users/editUser";
        }

        User user = userService.getByEmail(oldEmail);

        if ((!oldEmail.equals(editUserRequest.getEmail())
                && userService.existsByEmail(editUserRequest.getEmail()))) {
            bindingResult.rejectValue("email", "error.editUserRequest",
                    "Please choose a different email for the user!");
            if (user == null) {
                bindingResult.rejectValue("name", "error.editUserRequest", "User not found");
            }
            return "users/editUser";
        }

        User updatedUser = userMapper.partialUpdate(editUserRequest, user);
        updatedUser.setId(editUserRequest.getId());
        updatedUser.setStatusUser(editUserRequest.getStatusUser());
        userService.save(updatedUser);

        return "redirect:/users/allUsers";

    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int userId, RedirectAttributes redirectAttributes) {
        userService.deleteUser(userId);
        redirectAttributes.addFlashAttribute("success", "User has been deleted!");
        return "redirect:/users/allUsers";
    }

    @GetMapping("/errorPage")
    public String showErrorPage(@RequestParam(value = "message", required = false,
            defaultValue = "An unexpected error occurred. User cannot be found") String message, Model model) {
        model.addAttribute("message", message);
        return "errorPage";
    }

    @GetMapping("/editAccount")
    public String editAccount(@RequestParam("email") String email,  ModelMap modelMap) {
        User userOrNull = userService.getByEmail(email);
        if (userOrNull != null) {
            modelMap.put("saveUserRequest", userMapper.toSaveUserRequest(userOrNull));
            return "users/editAccount";
        }
        return "users/menu";
    }

    @PostMapping("/editAccount")
    public String editAccount(
            @ModelAttribute SaveUserRequest saveUserRequest,
            @RequestParam("oldEmail") String oldEmail,
            @Valid BindingResult bindingResult,
            ModelMap modelMap) {

        if (bindingResult.hasErrors()) {
            modelMap.put("bindingResult", bindingResult);
            return "users/editAccount";
        }

        User user = userService.getByEmail(oldEmail);
        saveUserRequest.setUserType(user.getUserType());
        user.setEmail(saveUserRequest.getEmail());
        String encodedPassword = passwordEncoder.encode(saveUserRequest.getPassword());
        saveUserRequest.setPassword(encodedPassword);
        if ((!oldEmail.equals(saveUserRequest.getEmail())
                && userService.existsByEmail(saveUserRequest.getEmail()))) {
            bindingResult.rejectValue("email", "error.saveUserRequest",
                    "Please choose a different email!");
            return "users/editAccount";
        }

        User updatedUser = userMapper.partialUpdate(saveUserRequest, user);
        userService.save(updatedUser);

        return "redirect:/auth/login";

    }

}
