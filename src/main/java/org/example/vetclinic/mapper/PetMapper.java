package org.example.vetclinic.mapper;

import org.example.vetclinic.dto.pet.PetDto;
import org.example.vetclinic.dto.pet.SavePetRequest;
import org.example.vetclinic.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



import java.util.List;
@Mapper(componentModel = "spring")
public interface PetMapper {
    @Mapping(target = "id", ignore = true)
    Pet toEntity(SavePetRequest savePetRequest);

    @Mapping(source = "user.id", target = "userId")
            PetDto toDto(Pet pet);

    @Mapping(target = "id", ignore = true)
    List<PetDto> toDtoList(List<Pet> pets);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", ignore = true)
    SavePetRequest toSavePetRequest(Pet pet);


}