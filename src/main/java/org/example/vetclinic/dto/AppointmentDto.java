package org.example.vetclinic.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.Doctor;
import org.example.vetclinic.entity.Pet;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDto {
    private int id;
    private String title;
    private Date startTime;
    private Date finishTime;
    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

}
