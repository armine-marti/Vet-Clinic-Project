package org.example.vetclinic.repository;

import org.example.vetclinic.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    boolean existsByNameAndUserId(String name, int userId);

    boolean existsByName(String name);

    List<Pet> findPetByUserId(int userId);

    Optional<Pet> findById(int id);

    List<Pet> findByUserId(int userId);

    Optional<Pet> findByName(String name);

    Optional<Pet> findByNameAndUserId(String name, int id);
}
