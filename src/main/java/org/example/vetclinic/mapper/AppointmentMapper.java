package org.example.vetclinic.mapper;

import org.example.vetclinic.dto.appointment.AppointmentDto;
import org.example.vetclinic.dto.appointment.SaveAppointmentRequest;
import org.example.vetclinic.entity.Appointment;
import org.example.vetclinic.service.DoctorService;
import org.example.vetclinic.service.PetService;
import org.example.vetclinic.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {DoctorService.class, PetService.class, UserService.class},
        imports = {DoctorService.class, PetService.class, UserService.class}
)
public interface AppointmentMapper {

    @Mapping(target = "doctor", source = "doctorId")
    @Mapping(target = "pet", source = "petId")
    @Mapping(target = "user.id", source = "userId")
    Appointment fromSaveRequestToEntity(SaveAppointmentRequest saveAppointmentRequest);

    @Mapping(source = "pet.name", target = "petName")
    @Mapping(source = "doctor.surname", target = "doctorSurname")
    @Mapping(source = "pet.id", target = "petId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "user.id", target = "userId")
    AppointmentDto toDto(Appointment appointment);

    List<AppointmentDto> toDtoList(List<Appointment> appointments);

    @Mapping(source = "pet.name", target = "petName")
    @Mapping(source = "doctor.surname", target = "doctorSurname")
    @Mapping(source = "pet.id", target = "petId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "user.id", target = "userId")
    SaveAppointmentRequest toSaveAppointmentRequest(Appointment appointment);

    @Mapping(target = "doctor", source = "doctorId")
    @Mapping(target = "pet", source = "petId")
    @Mapping(target = "user.id", source = "userId")
    Appointment fromDtoToEntity(AppointmentDto appointmentDto);
}
