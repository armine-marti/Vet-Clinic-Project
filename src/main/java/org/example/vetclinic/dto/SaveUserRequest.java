package org.example.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.UserType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserRequest {

    private String name;
    private String surname;
    private String email;
    private String password;
    private UserType userType;

}
