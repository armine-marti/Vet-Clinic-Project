package org.example.vetclinic.mapper;

import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.dto.pet.SavePetRequest;
import org.example.vetclinic.entity.Pet;
import org.mapstruct.Mapper;


import java.util.List;
@Mapper(componentModel = "spring")
public interface PetMapper {

    Pet toEntity(SavePetRequest savePetRequest);

    PetDto toDto(Pet pet);


    List<PetDto> toDtoList(List<Pet> pets);


    SavePetRequest toSavePetRequest(Pet pet);


}