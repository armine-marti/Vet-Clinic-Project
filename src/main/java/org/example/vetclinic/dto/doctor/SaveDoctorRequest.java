package org.example.vetclinic.dto.doctor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.Specialization;
import org.example.vetclinic.entity.StatusDoctor;

/**
 * DTO representing the data required to save or create a doctor.
 * This class is used to capture the doctor's name, surname, email, specialization, and status.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveDoctorRequest {
    @NotEmpty(message = "Name can't be empty")
    private String name;
    @NotEmpty(message = "Surname can't be empty")
    private String surname;
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email can't be empty")
    @NotNull(message = "Email can't be empty")
    private String email;
    @NotNull(message = "Doctor type must be chosen")
    private Specialization specialization;

    private StatusDoctor statusDoctor;

}
