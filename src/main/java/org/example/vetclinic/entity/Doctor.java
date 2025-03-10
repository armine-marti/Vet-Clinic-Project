package org.example.vetclinic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a doctor.
 * This class maps to the `doctor` table in the database and stores details about a doctor,
 * including their name, surname, email, specialization, and status.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
    @Enumerated(EnumType.STRING)
    private StatusDoctor statusDoctor;


}
