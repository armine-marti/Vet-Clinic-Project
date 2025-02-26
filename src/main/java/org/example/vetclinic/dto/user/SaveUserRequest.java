package org.example.vetclinic.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.UserType;

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
    @NotNull(message = "User type must be chosen")
    private UserType userType;

}
