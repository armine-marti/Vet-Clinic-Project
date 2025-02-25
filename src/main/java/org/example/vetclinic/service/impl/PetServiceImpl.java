package org.example.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.dto.pet.PetDtoBooking;
import org.example.vetclinic.entity.Pet;
import org.example.vetclinic.entity.StatusPet;
import org.example.vetclinic.mapper.PetMapper;
import org.example.vetclinic.repository.PetRepository;
import org.example.vetclinic.service.PetService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    @Override
    public Pet save(Pet pet) {
        pet.setStatusPet(StatusPet.PRESENT);
        return petRepository.save(pet);
    }

    @Override
    public List<PetDto> petsByUserId(int userId) {
        List<Pet> pets = petRepository.findByUserId(userId);

        if (pets == null || pets.isEmpty()) {
            return Collections.emptyList();
        }

        return petMapper.toDtoList(pets);
    }

    @Override
    public Pet getByNameOrNull(String name) {
        return petRepository.findByName(name).orElse(null);
    }

    @Override
    public Pet getById(int petId) {
        return petRepository.findById(petId)
                .orElseThrow();
    }

    @Override
    public List<PetDtoBooking> petsDtoBookingByUserId(int userId) {
        List<Pet> pets = petRepository.findByUserId(userId);
        return petMapper.toPetDtoBooking(pets);
    }

    @Override
    @Transactional
    public void deletePet(String name, int userId) {
        Pet pet = getByNameAndUserId(name, userId);
        petRepository.softPetDelete(pet.getId());
    }

    @Override
    public Pet getByNameAndUserId(String name, int userId) {
        return petRepository.findByNameAndUserId(name, userId).orElseThrow();
    }

    @Override
    public Pet getByNameAndUserIdOrNull(String name, int userId) {
        return petRepository.findByNameAndUserId(name, userId).orElse(null);
    }

    @Override
    public List<PetDtoBooking> getAllByStatusPetAndUserIdForBooking(StatusPet statusPet, int userId) {
        List<Pet> pets = petRepository.findAllByStatusPetAndUserId(statusPet, userId);
        return petMapper.toPetDtoBooking(pets);
    }

    @Override
    public List<PetDto> getAllByStatusPetAndUserId(StatusPet statusPet, int userId) {
        List<Pet> pets = petRepository.findAllByStatusPetAndUserId(statusPet, userId);
        return petMapper.toPetDtoList(pets);
    }

    @Override
    public boolean existsByNameAndUserId(String name, int userId) {
        return petRepository.existsByNameAndUserId(name, userId);
    }
}
