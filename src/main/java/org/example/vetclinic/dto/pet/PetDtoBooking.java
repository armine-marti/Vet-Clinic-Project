package org.example.vetclinic.dto.pet;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.Gender;
import org.example.vetclinic.entity.PetType;
import org.example.vetclinic.entity.Size;
import org.example.vetclinic.entity.StatusPet;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Data Transfer Object (DTO) representing a pet specifically for booking purposes.
 * This class is used to transfer essential pet-related data, including the pet's details such as name, type, size,
 * birthday, weight, gender, and status.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetDtoBooking {

    private int id;
    private String name;
    private PetType petType;
    private Size size;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private double weight;
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private StatusPet statusPet;

}
