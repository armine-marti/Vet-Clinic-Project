package org.example.vetclinic.mapper;

import org.example.vetclinic.dto.user.EditUserRequest;
import org.example.vetclinic.dto.user.SaveUserRequest;
import org.example.vetclinic.dto.user.UserDto;
import org.example.vetclinic.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper interface for converting between User entities, DTOs, and request objects.
 * This interface utilizes MapStruct to map between the User entity and various
 * representations such as UserDto, SaveUserRequest, EditUserRequest, and others.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts a SaveUserRequest to a User entity.
     *
     * @param saveUserRequest The SaveUserRequest containing the user data to be mapped.
     * @return The mapped User entity.
     */
    User toEntity(SaveUserRequest saveUserRequest);

    /**
     * Converts a list of User entities to a list of UserDto objects.
     *
     * @param users The list of User entities to convert.
     * @return The list of mapped UserDto objects.
     */
    List<UserDto> toDtoList(List<User> users);

    /**
     * Converts a User entity to a SaveUserRequest object.
     *
     * @param user The User entity to be converted.
     * @return The mapped SaveUserRequest.
     */
    SaveUserRequest toSaveUserRequest(User user);

    /**
     * Converts a User entity to an EditUserRequest object.
     *
     * @param user The User entity to be converted.
     * @return The mapped EditUserRequest.
     */
    EditUserRequest toEditUserRequest(User user);

    /**
     * Partially updates an existing User entity with the provided EditUserRequest.
     * This method will only update the fields present in the EditUserRequest.
     *
     * @param editUserRequest The EditUserRequest containing the updated data.
     * @param user            The existing User entity to update.
     * @return The updated User entity.
     */
    User partialUpdate(EditUserRequest editUserRequest, @MappingTarget User user);

    /**
     * Partially updates an existing User entity with the provided SaveUserRequest.
     * This method will only update the fields present in the SaveUserRequest.
     *
     * @param saveUserRequest The SaveUserRequest containing the updated data.
     * @param user            The existing User entity to update.
     * @return The updated User entity.
     */
    User partialUpdate(SaveUserRequest saveUserRequest, @MappingTarget User user);

}
