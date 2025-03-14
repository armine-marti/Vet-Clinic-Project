package org.example.vetclinic.dto.doctor;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.Specialization;
import org.example.vetclinic.entity.StatusDoctor;

/**
 * Data Transfer Object (DTO) representing a doctor.
 * This class is used to transfer doctor-related data between different layers of the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDto {

    private int id;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
    private String email;
    @Enumerated(EnumType.STRING)
    private StatusDoctor statusDoctor;
}
