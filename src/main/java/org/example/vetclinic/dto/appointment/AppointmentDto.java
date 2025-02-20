package org.example.vetclinic.dto.appointment;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.dto.UserDto;
import org.example.vetclinic.dto.doctor.DoctorDto;
import org.example.vetclinic.dto.pet.PetDtoBooking;
import org.example.vetclinic.entity.Status;

import java.util.Date;

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
    private int userId;

}
