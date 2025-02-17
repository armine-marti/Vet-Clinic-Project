package org.example.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.dto.pet.SavePetRequest;
import org.example.vetclinic.entity.Pet;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.mapper.PetMapper;

import org.example.vetclinic.repository.PetRepository;
import org.example.vetclinic.security.CurrentUser;
import org.example.vetclinic.service.PetService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/pets")
@RequiredArgsConstructor
@Slf4j
public class PetController {

    private final PetService petService;
    private final PetMapper petMapper;
    private final PetRepository petRepository;

    @GetMapping
    public String userPets(ModelMap modelMap, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        CurrentUser currentUser = (CurrentUser) ((Authentication) principal).getPrincipal();
        User user = currentUser.getUser();
        int userId = user.getId();
        List<PetDto> pets = petService.petsByUserId(userId);
        modelMap.put("pets", pets);
        modelMap.put("currentUser", currentUser);

        return "pet/pets";
    }


    @GetMapping("/addPet")
    public String addPet(ModelMap modelMap, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        CurrentUser currentUser = (CurrentUser) ((Authentication) principal).getPrincipal();
        User user = currentUser.getUser();
        modelMap.put("savePetRequest", new SavePetRequest());
        modelMap.put("currentUser", currentUser);
        return "pet/addPet";
    }

    @PostMapping("/addPet")
    public String addPet(@Valid @ModelAttribute SavePetRequest savePetRequest,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "pet/addPet";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        User user = currentUser.getUser();
        boolean petExists = petRepository.existsByNameAndUserId(savePetRequest.getName(), user.getId());
        if (petExists) {
            bindingResult.rejectValue("name", "error.savePetRequest", "Pet name must be unique");
            return "pet/addPet";
        }
        Pet pet = petMapper.toEntity(savePetRequest);
        pet.setUser(user);
        petService.save(pet);
        redirectAttributes.addFlashAttribute("success", "You have added a new pet!");

        return "redirect:/pets";
    }

    @GetMapping("/editPet")
    public String editPet(@RequestParam("name") String name, ModelMap modelMap) {
        Pet pet = petService.findByName(name).orElse(null);
        if (pet != null) {
            modelMap.put("savePetRequest", petMapper.toSavePetRequest(pet));
            return "pet/editPet";
        }
        return "redirect:/pets";
    }

    @PostMapping("/editPet")
    public String editPet(@RequestParam("oldName") String oldName,
                          @Valid @ModelAttribute SavePetRequest savePetRequest,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "pet/editPet";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        User user = currentUser.getUser();
        Pet pet = petRepository.findByNameAndUserId(oldName, user.getId()).orElse(null);

        if (pet == null) {
            bindingResult.rejectValue("name", "error.savePetRequest", "Pet not found!");
            return "pet/editPet";
        }

        boolean petExists = petRepository.existsByNameAndUserId(savePetRequest.getName(), user.getId());

        if (!oldName.equals(savePetRequest.getName()) && petExists) {
            bindingResult.rejectValue("name", "error.savePetRequest", "You already have a pet with this name!");
            return "pet/editPet";
        }
        pet.setName(savePetRequest.getName());
        pet.setPetType(savePetRequest.getPetType());
        pet.setSize(savePetRequest.getSize());
        pet.setWeight(savePetRequest.getWeight());

        petService.save(pet);

        redirectAttributes.addFlashAttribute("success", "Pet has been updated!");
        return "redirect:/pets";
    }

    @PostMapping("/deletePet")
    public String deletePet(@RequestParam("name") String name, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        User user = currentUser.getUser();
        Pet pet = petRepository.findByNameAndUserId(name, user.getId()).orElse(null);

        if (pet == null) {
            redirectAttributes.addFlashAttribute("error", "Pet not found!");
            return "redirect:/pets";
        }
        petService.delete(pet);
        redirectAttributes.addFlashAttribute("success", "Pet has been deleted!");
        return "redirect:/pets";
    }
}

