package org.example.vetclinic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthRequest {

    @NotBlank(message = "Please enter your email")
    @Email(message = "Invalid email format")
    String email;

    @NotBlank(message = "Please enter your password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;
}