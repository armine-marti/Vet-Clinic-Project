package org.example.vetclinic.mapper;

import org.example.vetclinic.dto.user.EditUserRequest;
import org.example.vetclinic.dto.user.SaveUserRequest;
import org.example.vetclinic.dto.user.UserDto;
import org.example.vetclinic.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(SaveUserRequest saveUserRequest);

    List<UserDto> toDtoList(List<User> users);

    SaveUserRequest toSaveUserRequest(User user);

    EditUserRequest toEditUserRequest(User user);

    User partialUpdate(EditUserRequest editUserRequest, @MappingTarget User user);

}
