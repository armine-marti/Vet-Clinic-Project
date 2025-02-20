package org.example.vetclinic.repository;

import org.example.vetclinic.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    List<Pet> findByUserId(int userId);

    Optional<Pet> findByName(String name);

    Optional<Pet> findByNameAndUserId(String name, int id);

    @Modifying
    @Query("UPDATE Pet p SET p.statusPet = 'DELETED' WHERE p.id = :petId")
    void softPetDelete(@Param("petId") int petId);
}
