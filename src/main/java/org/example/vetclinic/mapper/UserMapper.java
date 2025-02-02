package org.example.vetclinic.mapper;
import org.example.vetclinic.dto.SaveUserRequest;

import org.example.vetclinic.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(SaveUserRequest saveUserRequest);


}
