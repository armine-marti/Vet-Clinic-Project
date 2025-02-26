package org.example.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.dto.pet.SavePetRequest;
import org.example.vetclinic.entity.Pet;
import org.example.vetclinic.entity.StatusPet;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.mapper.PetMapper;
import org.example.vetclinic.security.CurrentUser;
import org.example.vetclinic.service.PetService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final PetMapper petMapper;

    @GetMapping
    public String userPets(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        List<PetDto> pets = petService.getAllByStatusPetAndUserId(StatusPet.PRESENT, user.getId());
        modelMap.put("pets", pets);
        modelMap.put("currentUser", currentUser);
        return "pet/pets";
    }

    @GetMapping("/addPet")
    public String addPet(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null) {
            return "redirect:/login";
        }
        modelMap.put("savePetRequest", new SavePetRequest());
        modelMap.put("currentUser", currentUser);
        return "pet/addPet";
    }

    @PostMapping("/addPet")
    public String addPet(@Valid @ModelAttribute SavePetRequest savePetRequest,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         ModelMap modelMap,
                         @AuthenticationPrincipal CurrentUser currentUser) {
        if (bindingResult.hasErrors()) {
            modelMap.put("bindingResult", bindingResult);
            return "pet/addPet";
        }

        User user = currentUser.getUser();
        boolean petExists = petService.existsByNameAndUserId(savePetRequest.getName(), user.getId());
        if (petExists) {
            bindingResult.rejectValue("name", "error.savePetRequest",
                    "You already have a pet with this name!");
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
        Pet petOrNull = petService.getByNameOrNull(name);
        if (petOrNull != null) {
            modelMap.put("savePetRequest", petMapper.toSavePetRequest(petOrNull));
            return "pet/editPet";
        }
        return "redirect:/pets";
    }

    @PostMapping("/editPet")
    public String editPet(
            @RequestParam("oldName") String oldName,
            @Valid @ModelAttribute SavePetRequest savePetRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        if (bindingResult.hasErrors()) {
            return "pet/editPet";
        }

        User user = currentUser.getUser();
        Pet pet = petService.getByNameAndUserIdOrNull(oldName, user.getId());

        if (pet == null) {
            bindingResult.rejectValue("name", "error.savePetRequest", "Pet not found!");
            return "pet/editPet";
        }

        boolean petExists = petService.existsByNameAndUserId(savePetRequest.getName(), user.getId());

        if (!Objects.equals(oldName, savePetRequest.getName()) && petExists) {
            bindingResult.rejectValue("name", "error.savePetRequest",
                    "You already have a pet with this name!");
            return "pet/editPet";
        }

        Pet updatedPet = petMapper.partialUpdate(savePetRequest, pet);
        petService.save(updatedPet);
        redirectAttributes.addFlashAttribute("success", "Pet has been updated!");
        return "redirect:/pets";
    }

    @PostMapping("/deletePet")
    public String deletePet(@RequestParam("name") String name, RedirectAttributes redirectAttributes,
                            @AuthenticationPrincipal CurrentUser currentUser) {
        petService.deletePet(name, currentUser.getUser().getId());
        redirectAttributes.addFlashAttribute("success", "Pet has been deleted!");
        return "redirect:/pets";
    }
}

