package org.example.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.dto.pet.SavePetRequest;
import org.example.vetclinic.entity.Pet;
import org.example.vetclinic.entity.StatusPet;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.mapper.PetMapper;
import org.example.vetclinic.repository.PetRepository;
import org.example.vetclinic.security.CurrentUser;
import org.example.vetclinic.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pets")
@RequiredArgsConstructor
@Slf4j
public class PetController {
    @Autowired
    private final PetService petService;
    private final PetMapper petMapper;
    private final PetRepository petRepository;

    @GetMapping
    public String userPets(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null) {
            return "redirect:/login";
        }
        User user = currentUser.getUser();
        int userId = user.getId();
        List<PetDto> pets = petService.petsByUserId(userId);
        pets = pets.stream()
                .filter(pet -> !StatusPet.DELETED.equals(pet.getStatusPet()))
                .collect(Collectors.toList());
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

        if (currentUser == null) {
            return "redirect:/login";
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
    public String editPet(@RequestParam("name") String name, ModelMap modelMap, @AuthenticationPrincipal CurrentUser
            currentUser) {
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
                          RedirectAttributes redirectAttributes, @AuthenticationPrincipal CurrentUser currentUser) {
        if (bindingResult.hasErrors()) {
            return "pet/editPet";
        }

        User user = currentUser.getUser();
        Pet pet = petRepository.findByNameAndUserId(oldName, user.getId()).orElse(null);

        if (pet == null) {
            bindingResult.rejectValue("name", "error.savePetRequest", "Pet not found!");
            return "pet/editPet";
        }

        boolean petExists = petService.existsByNameAndUserId(savePetRequest.getName(), user.getId());

        if (!oldName.equals(savePetRequest.getName()) && petExists) {
            bindingResult.rejectValue("name", "error.savePetRequest",
                    "You already have a pet with this name!");
            return "pet/editPet";
        }
        pet.setName(savePetRequest.getName());
        pet.setPetType(savePetRequest.getPetType());
        pet.setSize(savePetRequest.getSize());
        pet.setWeight(savePetRequest.getWeight());
        pet.setBirthday(savePetRequest.getBirthday());
        pet.setGender(savePetRequest.getGender());

        petService.save(pet);

        redirectAttributes.addFlashAttribute("success", "Pet has been updated!");
        return "redirect:/pets";

    }

    @PostMapping("/deletePet")
    public String deletePet(@RequestParam("name") String name, RedirectAttributes redirectAttributes,
                            @AuthenticationPrincipal CurrentUser currentUser) {
        petService.getPetDeleted(name, currentUser.getUser().getId());
        redirectAttributes.addFlashAttribute("success", "Pet has been deleted!");
        return "redirect:/pets";
    }
}

