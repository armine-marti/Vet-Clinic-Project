package org.example.vetclinic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.entity.Pet;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.mapper.PetMapper;
import org.example.vetclinic.repository.PetRepository;
import org.example.vetclinic.security.CurrentUser;
import org.example.vetclinic.service.PetService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    @Override
    public boolean existsByName(String name) {
        return petRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndUserId(String name, int userId) {
        return petRepository.existsByNameAndUserId(name, userId);
    }

    @Override
    public Pet save(Pet pet) {

        if (pet.getId() != 0) {
            return petRepository.save(pet);
        } else {
            return petRepository.save(pet);
        }
    }

    @Override
    public List<Pet> getPetByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        User user = currentUser.getUser();
        return petRepository.findPetByUserId(user.getId());
    }

    @Override
    public Optional<Pet> findById(int petId) {
        return petRepository.findById(petId);
    }

    @Override
    public List<PetDto> petsByUserId(int userId) {
        List<Pet> pets = petRepository.findByUserId(userId);
        if (pets == null || pets.isEmpty()) {
            log.info("No pets found for user with ID: " + userId);
        } else {
            log.info("Found {} pets for user with ID: {}", pets.size(), userId);
        }
        return pets.stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Pet> findByName(String name) {
        return petRepository.findByName(name);
    }

    @Override
    public Optional<Pet> findByNameAndUserId(String name, int id) {
        return petRepository.findByNameAndUserId(name, id);
    }

    @Override
    public List<PetDto> findAll() {
        return petMapper.toDtoList(petRepository.findAll());
    }

    @Override
    public void delete(Pet pet) {
        petRepository.delete(pet);
    }
}
