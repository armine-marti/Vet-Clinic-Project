package org.example.vetclinic.mapper;
import org.example.vetclinic.dto.SaveUserRequest;

import org.example.vetclinic.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toEntity(SaveUserRequest saveUserRequest);


}
