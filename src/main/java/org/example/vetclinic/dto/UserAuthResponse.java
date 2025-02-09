package org.example.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.UserType;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthResponse {

    private String name;
    private String email;
    private String phone;
    private UserType userType;
    private String token;

}