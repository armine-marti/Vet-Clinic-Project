package org.example.vetclinic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.vetclinic.entity.UserType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserAuthRequest {

    @NotBlank(message = "Please enter your email")
    @Email(message = "Invalid email format")
    String email;

    @NotBlank(message = "Please enter your password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;

    @NotNull
    UserType usertype;
}