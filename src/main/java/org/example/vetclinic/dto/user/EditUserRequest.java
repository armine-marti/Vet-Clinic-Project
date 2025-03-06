package org.example.vetclinic.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.StatusUser;
import org.example.vetclinic.entity.UserType;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequest {

    private int id;
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
    @NotNull(message = "User type must be chosen")
    private UserType userType;
    @NotNull(message = "User status must be chosen")
    private StatusUser statusUser;
}
