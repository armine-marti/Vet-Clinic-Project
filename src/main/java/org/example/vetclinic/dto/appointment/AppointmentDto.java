package org.example.vetclinic.dto.appointment;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.dto.doctor.DoctorDto;
import org.example.vetclinic.dto.pet.PetDtoBooking;
import org.example.vetclinic.dto.user.UserDto;
import org.example.vetclinic.entity.Status;

import java.util.Date;

/**
 * Data Transfer Object (DTO) representing an appointment.
 * This class is used to transfer appointment-related data between different layers of the application.
 * It includes information about the appointment, the pet, the doctor, and the user associated with the appointment.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDto {

    private String title;
    private Date startTime;
    private Date finishTime;
    @Enumerated(EnumType.STRING)
    private Status status;

    private PetDtoBooking petDtoBooking;
    private int petId;
    private String petName;

    private DoctorDto doctorDto;
    private int doctorId;
    private String doctorSurname;

    private UserDto userDto;
    private Integer userId;
    private String userSurname;

}
