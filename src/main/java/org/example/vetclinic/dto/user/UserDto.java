package org.example.vetclinic.dto.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.StatusUser;
import org.example.vetclinic.entity.UserType;

/**
 * Data Transfer Object (DTO) for representing a user.
 * This class holds the essential information of a user such as their ID, name, surname, phone number, email, user type, and user status.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private int id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private StatusUser statusUser;


}
