package org.example.vetclinic.repository;

import org.example.vetclinic.entity.Pet;
import org.example.vetclinic.entity.StatusPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing `Pet` entities.
 * This interface extends `JpaRepository` and provides CRUD operations along with custom queries for `Pet` entities.
 */
public interface PetRepository extends JpaRepository<Pet, Integer> {

    /**
     * Finds all pets by the user ID.
     *
     * @param userId The ID of the user to fetch pets for.
     * @return A list of pets associated with the given user ID.
     */
    List<Pet> findByUserId(int userId);

    /**
     * Finds a pet by its name.
     *
     * @param name The name of the pet to search for.
     * @return An `Optional` containing the pet if found, otherwise empty.
     */
    Optional<Pet> findByName(String name);

    /**
     * Finds a pet by its name and the user ID.
     *
     * @param name The name of the pet to search for.
     * @param id   The user ID to match with the pet.
     * @return An `Optional` containing the pet if found, otherwise empty.
     */
    Optional<Pet> findByNameAndUserId(String name, int id);

    /**
     * Soft deletes a pet by setting its status to 'DELETED'.
     * This marks the pet as deleted without actually removing the record from the database.
     *
     * @param petId The ID of the pet to soft delete.
     */
    @Modifying
    @Query("UPDATE Pet p SET p.statusPet = 'DELETED' WHERE p.id = :petId")
    void softPetDelete(@Param("petId") int petId);

    /**
     * Checks if a pet exists by its name and associated user ID.
     *
     * @param userId The ID of the user and user name to match with the pet.
     * @return True if a pet exists with the given name and user ID, otherwise false.
     */
    boolean existsByNameAndUserId(String Name, int userId);

    /**
     * Finds all pets with a specific status and user ID.
     *
     * @param statusPet The status of the pet (e.g., PRESENT, DELETED).
     * @param userId    The ID of the user to fetch pets for.
     * @return A list of pets with the specified status and user ID.
     */
    List<Pet> findAllByStatusPetAndUserId(StatusPet statusPet, int userId);
}
