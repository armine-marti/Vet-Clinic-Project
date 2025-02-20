package org.example.vetclinic.mapper;

import org.example.vetclinic.dto.SaveUserRequest;
import org.example.vetclinic.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(SaveUserRequest saveUserRequest);


}
