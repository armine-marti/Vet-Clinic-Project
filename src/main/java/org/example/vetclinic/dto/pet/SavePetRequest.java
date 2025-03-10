package org.example.vetclinic.dto.pet;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.Gender;
import org.example.vetclinic.entity.PetType;
import org.example.vetclinic.entity.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * DTO representing the data required to save or create a pet.
 * This class captures the necessary pet details such as name, type, size, birthday, weight, and gender for saving a pet.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavePetRequest {

    @NotBlank(message = "Name cannot be empty")
    @jakarta.validation.constraints.Size(max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name can only contain letters and spaces")
    private String name;
    @NotNull(message = "Pet type must be chosen")
    private PetType petType;
    @NotNull(message = "Please choose the size of your pet")
    private Size size;
    @NotNull(message = "Please input your pet birthday")
    @PastOrPresent(message = "Birthday must be in the past or present")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @Positive(message = "Weight must be a positive number")
    @Max(value = 200, message = "Weight cannot exceed 200 kg")
    private double weight;
    @NotNull
    private Gender gender;
    private int userId;


}
