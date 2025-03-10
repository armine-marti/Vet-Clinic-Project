package org.example.vetclinic.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.StatusUser;
import org.example.vetclinic.entity.UserType;

/**
 * DTO representing the data required to save or create a new user.
 * This class captures the necessary user details such as name, surname, phone number, email, password, user type and user status.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserRequest {

    @NotEmpty(message = "Name can't be empty")
    private String name;
    private String surname;
    @Pattern(regexp = "^(\\+?[0-9]{10,15})$", message = "Phone number must be between 10 and 15 digits and may start with +")
    @NotEmpty(message = "Phone can't be empty")
    private String phone;
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email can't be empty")
    @NotNull(message = "Email can't be empty")
    private String email;
    @NotEmpty(message = "Password can't be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    private UserType userType;
    private StatusUser statusUser;


}
