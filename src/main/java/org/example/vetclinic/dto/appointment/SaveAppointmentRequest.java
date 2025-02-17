package org.example.vetclinic.dto.appointment;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.Doctor;


import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAppointmentRequest {

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 100,message = "Title must be at most 100 characters")
    private String title;
    @NotNull(message = "Start time is required")
    @FutureOrPresent(message = "Start time must be in the present or future")
    private Date startTime;
    @NotNull(message = "Finish time is required")
    @Future(message = "Finish time must be in the future")
    private Date finishTime;
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
}
