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

/**
 * Implementation of the {@link PetService} interface that handles the business logic related to pets.
 * This service provides methods for managing pet entities, including saving, retrieving, updating, deleting,
 * and checking the existence of pets.
 * <p>
 * It interacts with the {@link PetRepository} for database operations and uses the {@link PetMapper}
 * to convert between pet entities and their corresponding DTOs.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    /**
     * Saves a pet entity to the database.
     * The status of the pet is set to {@link StatusPet#PRESENT} by default.
     *
     * @param pet The pet entity to be saved.
     * @return The saved {@link Pet} entity.
     */
    @Override
    public Pet save(Pet pet) {
        pet.setStatusPet(StatusPet.PRESENT);
        return petRepository.save(pet);
    }

    /**
     * Retrieves all pets for a given user by their user ID.
     *
     * @param userId The ID of the user whose pets are to be retrieved.
     * @return A list of {@link PetDto} representing the user's pets, or an empty list if no pets are found.
     */
    @Override
    public List<PetDto> petsByUserId(int userId) {
        List<Pet> pets = petRepository.findByUserId(userId);

        if (pets == null || pets.isEmpty()) {
            return Collections.emptyList();
        }

        return petMapper.toDtoList(pets);
    }

    /**
     * Retrieves a pet by its name, or returns null if no pet is found with the given name.
     *
     * @param name The name of the pet to be retrieved.
     * @return The {@link Pet} entity corresponding to the provided name, or null if not found.
     */
    @Override
    public Pet getByNameOrNull(String name) {
        return petRepository.findByName(name).orElse(null);
    }

    /**
     * Retrieves a pet by its ID.
     *
     * @param petId The ID of the pet to be retrieved.
     * @return The {@link Pet} entity corresponding to the provided ID.
     * @throws Exception if no pet is found with the provided ID.
     */
    @Override
    public Pet getById(int petId) {
        return petRepository.findById(petId)
                .orElseThrow();
    }

    /**
     * Retrieves a list of pets' booking details for a given user by their user ID.
     *
     * @param userId The ID of the user whose pets' booking details are to be retrieved.
     * @return A list of {@link PetDtoBooking} representing the user's pets' booking details.
     */
    @Override
    public List<PetDtoBooking> petsDtoBookingByUserId(int userId) {
        List<Pet> pets = petRepository.findByUserId(userId);
        return petMapper.toPetDtoBooking(pets);
    }

    /**
     * Soft deletes a pet by marking its status as {@link StatusPet#DELETED}.
     * This operation does not remove the pet from the database.
     *
     * @param name   The name of the pet to be deleted.
     * @param userId The ID of the user associated with the pet.
     */
    @Override
    @Transactional
    public void deletePet(String name, int userId) {
        Pet pet = getByNameAndUserId(name, userId);
        petRepository.softPetDelete(pet.getId());
    }

    /**
     * Retrieves a pet by its name and the associated user's ID.
     *
     * @param name   The name of the pet.
     * @param userId The ID of the user.
     * @return The {@link Pet} entity that matches the provided name and user ID.
     * @throws Exception if no pet is found with the provided name and user ID.
     */
    @Override
    public Pet getByNameAndUserId(String name, int userId) {
        return petRepository.findByNameAndUserId(name, userId).orElseThrow();
    }

    /**
     * Retrieves a pet by its name and the associated user's ID, or returns null if no such pet is found.
     *
     * @param name   The name of the pet.
     * @param userId The ID of the user.
     * @return The {@link Pet} entity that matches the provided name and user ID, or null if not found.
     */
    @Override
    public Pet getByNameAndUserIdOrNull(String name, int userId) {
        return petRepository.findByNameAndUserId(name, userId).orElse(null);
    }

    /**
     * Retrieves all pets for a given user by their user ID and the pet's status,
     * specifically for the purpose of booking.
     *
     * @param statusPet The status of the pets to be retrieved.
     * @param userId    The ID of the user whose pets are to be retrieved.
     * @return A list of {@link PetDtoBooking} representing the pets with the given status.
     */
    @Override
    public List<PetDtoBooking> getAllByStatusPetAndUserIdForBooking(StatusPet statusPet, int userId) {
        List<Pet> pets = petRepository.findAllByStatusPetAndUserId(statusPet, userId);
        return petMapper.toPetDtoBooking(pets);
    }

    /**
     * Retrieves all pets for a given user by their user ID and the pet's status.
     *
     * @param statusPet The status of the pets to be retrieved.
     * @param userId    The ID of the user whose pets are to be retrieved.
     * @return A list of {@link PetDto} representing the pets with the given status.
     */
    @Override
    public List<PetDto> getAllByStatusPetAndUserId(StatusPet statusPet, int userId) {
        List<Pet> pets = petRepository.findAllByStatusPetAndUserId(statusPet, userId);
        return petMapper.toPetDtoList(pets);
    }

    /**
     * Checks if a pet with the given name already exists for the specified user.
     *
     * @param name   The name of the pet.
     * @param userId The ID of the user.
     * @return True if a pet with the given name exists for the specified user, false otherwise.
     */
    @Override
    public boolean existsByNameAndUserId(String name, int userId) {
        return petRepository.existsByNameAndUserId(name, userId);
    }
}
