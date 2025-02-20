package org.example.vetclinic.service;

import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.dto.pet.PetDtoBooking;
import org.example.vetclinic.entity.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {


    Pet save(Pet pet);

    Pet getById(int petId);

    List<PetDto> petsByUserId(int userId);

    List<PetDtoBooking> petsDtoBookingByUserId(int userId);

    boolean existsByNameAndUserId(String Name, int userId);

    Optional<Pet> findByName(String name);

    void getPetDeleted(String name, int userId);
}
