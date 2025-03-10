package org.example.vetclinic.service;

import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.dto.pet.PetDtoBooking;
import org.example.vetclinic.entity.Pet;
import org.example.vetclinic.entity.StatusPet;
import org.mapstruct.Named;

import java.util.List;

/**
 * Service interface for managing pets.
 * This interface provides methods to perform CRUD operations on pets
 * and other functionalities related to pets.
 */
public interface PetService {

    /**
     * Saves a new pet to the system.
     *
     * @param pet The {@link Pet} entity to save.
     * @return The saved {@link Pet} entity.
     */
    Pet save(Pet pet);

    /**
     * Retrieves a pet by its ID.
     *
     * @param petId The ID of the pet to retrieve.
     * @return The {@link Pet} entity with the specified ID.
     * @throws Exception if no pet is found with the provided ID.
     */
    @Named("getPetById")
    Pet getById(int petId);

    /**
     * Retrieves all pets belonging to a specific user.
     *
     * @param userId The ID of the user whose pets are to be retrieved.
     * @return A list of {@link PetDto} representing the pets of the user.
     */
    List<PetDto> petsByUserId(int userId);

    /**
     * Retrieves all pets belonging to a specific user for booking purposes.
     *
     * @param userId The ID of the user whose pets are to be retrieved.
     * @return A list of {@link PetDtoBooking} representing the pets of the user for booking.
     */
    List<PetDtoBooking> petsDtoBookingByUserId(int userId);

    /**
     * Retrieves a pet by its name. Returns null if no pet with the given name is found.
     *
     * @param name The name of the pet to retrieve.
     * @return The {@link Pet} entity with the specified name, or null if not found.
     */
    Pet getByNameOrNull(String name);

    /**
     * Soft-deletes a pet by its name and user ID.
     *
     * @param name   The name of the pet to delete.
     * @param userId The ID of the user who owns the pet.
     */
    void deletePet(String name, int userId);

    /**
     * Retrieves a pet by its name and user ID.
     *
     * @param name   The name of the pet to retrieve.
     * @param userId The ID of the user who owns the pet.
     * @return The {@link Pet} entity with the specified name and user ID.
     * @throws Exception if no pet is found with the provided name and user ID.
     */
    Pet getByNameAndUserId(String name, int userId);

    /**
     * Retrieves a pet by its name and user ID. Returns null if no pet is found.
     *
     * @param name   The name of the pet to retrieve.
     * @param userId The ID of the user who owns the pet.
     * @return The {@link Pet} entity with the specified name and user ID, or null if not found.
     */
    Pet getByNameAndUserIdOrNull(String name, int userId);

    /**
     * Retrieves all pets with a specific status and belonging to a specific user, for booking purposes.
     *
     * @param statusPet The status of the pets to retrieve.
     * @param userId    The ID of the user whose pets are to be retrieved.
     * @return A list of {@link PetDtoBooking} representing the pets with the specified status for booking.
     */
    List<PetDtoBooking> getAllByStatusPetAndUserIdForBooking(StatusPet statusPet, int userId);

    /**
     * Retrieves all pets with a specific status and belonging to a specific user.
     *
     * @param statusPet The status of the pets to retrieve.
     * @param userId    The ID of the user whose pets are to be retrieved.
     * @return A list of {@link PetDto} representing the pets with the specified status.
     */
    List<PetDto> getAllByStatusPetAndUserId(StatusPet statusPet, int userId);

    /**
     * Checks if a pet exists with the given name and user ID.
     *
     * @param name   The name of the pet to check.
     * @param userId The ID of the user who owns the pet.
     * @return True if a pet exists with the specified name and user ID, false otherwise.
     */
    boolean existsByNameAndUserId(String name, int userId);
}
