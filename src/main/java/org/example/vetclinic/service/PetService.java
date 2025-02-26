package org.example.vetclinic.service;

import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.dto.pet.PetDtoBooking;
import org.example.vetclinic.entity.Pet;
import org.example.vetclinic.entity.StatusPet;
import org.mapstruct.Named;

import java.util.List;

public interface PetService {

    Pet save(Pet pet);

    @Named("getPetById")
    Pet getById(int petId);

    List<PetDto> petsByUserId(int userId);

    List<PetDtoBooking> petsDtoBookingByUserId(int userId);

    Pet getByNameOrNull(String name);

    void deletePet(String name, int userId);

    Pet getByNameAndUserId(String name, int userId);

    Pet getByNameAndUserIdOrNull(String name, int userId);

    List<PetDtoBooking> getAllByStatusPetAndUserIdForBooking(StatusPet statusPet, int userId);

    List<PetDto> getAllByStatusPetAndUserId(StatusPet statusPet, int userId);

    boolean existsByNameAndUserId(String name, int userId);
}
