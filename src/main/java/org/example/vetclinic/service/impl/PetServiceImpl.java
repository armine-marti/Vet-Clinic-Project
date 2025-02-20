package org.example.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.dto.pet.PetDtoBooking;
import org.example.vetclinic.entity.Pet;
import org.example.vetclinic.mapper.PetMapper;
import org.example.vetclinic.repository.PetRepository;
import org.example.vetclinic.service.PetService;
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
    public Pet save(Pet pet) {

        if (pet.getId() != 0) {
            return petRepository.save(pet);
        } else {
            return petRepository.save(pet);
        }
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
    public Pet getById(int petId) {
        return petRepository.findById(petId)
                .orElseThrow();
    }

    @Override
    public List<PetDtoBooking> petsDtoBookingByUserId(int userId) {
        List<Pet> pets = petRepository.findByUserId(userId);
        List<PetDtoBooking> petsDtoBookings = petMapper.toPetDtoBooking(pets);
        return petsDtoBookings;
    }

    @Override
    public boolean existsByNameAndUserId(String Name, int userId) {
        Pet pet = petRepository.findByNameAndUserId(Name, userId).orElse(null);
        if (pet != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void getPetDeleted(String name, int userId) {
        Pet pet = petRepository.findByNameAndUserId(name, userId).orElse(null);
        petRepository.softPetDelete(pet.getId());
    }
}
