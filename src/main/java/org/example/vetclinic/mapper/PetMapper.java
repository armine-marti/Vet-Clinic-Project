package org.example.vetclinic.mapper;

import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.dto.pet.PetDtoBooking;
import org.example.vetclinic.dto.pet.SavePetRequest;
import org.example.vetclinic.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper interface for converting between Pet entities, DTOs, and request objects.
 * This interface uses MapStruct to handle conversions between the Pet entity and different
 * representations such as PetDto, PetDtoBooking, and SavePetRequest.
 */
@Mapper(componentModel = "spring")
public interface PetMapper {

    /**
     * Converts a SavePetRequest to a Pet entity.
     *
     * @param savePetRequest The SavePetRequest object to convert.
     * @return The mapped Pet entity.
     */
    Pet toEntity(SavePetRequest savePetRequest);

    /**
     * Converts a PetDto to a Pet entity. This method maps the userId from the DTO to the corresponding User entity.
     *
     * @param petDto The PetDto object to convert.
     * @return The mapped Pet entity.
     */
    @Mapping(source = "userId", target = "user.id")
    Pet fromDtotoEntity(PetDto petDto);

    /**
     * Converts a Pet entity to a PetDto.
     *
     * @param pet The Pet entity to convert.
     * @return The mapped PetDto.
     */
    PetDto toDto(Pet pet);

    /**
     * Converts a list of Pet entities to a list of PetDto objects.
     *
     * @param pet The list of Pet entities to convert.
     * @return The list of mapped PetDto objects.
     */
    List<PetDto> toDtoList(List<Pet> pet);

    /**
     * Converts a list of Pet entities to a list of PetDtoBooking objects. This method also maps the userId
     * from the Pet entity to the userId in PetDtoBooking.
     *
     * @param pets The list of Pet entities to convert.
     * @return The list of mapped PetDtoBooking objects.
     */
    @Mapping(source = "user.id", target = "userId")
    List<PetDtoBooking> toPetDtoBooking(List<Pet> pets);

    /**
     * Converts a Pet entity to a SavePetRequest.
     *
     * @param pet The Pet entity to convert.
     * @return The mapped SavePetRequest.
     */
    SavePetRequest toSavePetRequest(Pet pet);

    /**
     * Converts a list of Pet entities to a list of PetDto objects and maps the userId from the Pet entity
     * to the userId in PetDto.
     *
     * @param pets The list of Pet entities to convert.
     * @return The list of mapped PetDto objects.
     */
    @Mapping(source = "user.id", target = "userId")
    List<PetDto> toPetDtoList(List<Pet> pets);

    /**
     * Partially updates an existing Pet entity with data from a SavePetRequest.
     * This method will only update the fields that are present in the request.
     *
     * @param saveRequest The SavePetRequest containing updated data.
     * @param pet         The existing Pet entity to update.
     * @return The updated Pet entity.
     */
    Pet partialUpdate(SavePetRequest saveRequest, @MappingTarget Pet pet);

}