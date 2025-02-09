package org.example.vetclinic.service;

import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.entity.Pet;
import java.util.List;
import java.util.Optional;

public interface PetService {

    boolean existsByName(String name);

    boolean existsByNameAndUserId(String name, int userId);

    Pet save(Pet pet);

    List<Pet> getPetByUser();

    Optional<Pet> findById(int petId);

    List<PetDto> findAll();

    List<PetDto> petsByUserId(int userId);

    Optional<Pet> findByNameAndUserId(String name, int id);

    Optional<Pet> findByName(String name);

    void delete(Pet pet);
}
