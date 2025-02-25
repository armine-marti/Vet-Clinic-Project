package org.example.vetclinic.mapper;

import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.dto.pet.PetDtoBooking;
import org.example.vetclinic.dto.pet.SavePetRequest;
import org.example.vetclinic.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {

    Pet toEntity(SavePetRequest savePetRequest);

    @Mapping(source = "userId", target = "user.id")
    Pet fromDtotoEntity(PetDto petDto);

    PetDto toDto(Pet pet);

    List<PetDto> toDtoList(List<Pet> pet);

    @Mapping(source = "user.id", target = "userId")
    List<PetDtoBooking> toPetDtoBooking(List<Pet> pets);

    SavePetRequest toSavePetRequest(Pet pet);

    @Mapping(source = "user.id", target = "userId")
    List<PetDto> toPetDtoList(List<Pet> pets);

    Pet partialUpdate(SavePetRequest saveRequest, @MappingTarget Pet pet);

}