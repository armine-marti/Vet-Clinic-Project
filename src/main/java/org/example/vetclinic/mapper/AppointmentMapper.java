package org.example.vetclinic.mapper;

import org.example.vetclinic.dto.appointment.AppointmentDto;
import org.example.vetclinic.dto.appointment.SaveAppointmentRequest;
import org.example.vetclinic.entity.Appointment;
import org.example.vetclinic.service.DoctorService;
import org.example.vetclinic.service.PetService;
import org.example.vetclinic.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {DoctorService.class, PetService.class, UserService.class},
        imports = {DoctorService.class, PetService.class, UserService.class}
)
public interface AppointmentMapper {

    @Mapping(target = "doctor", source = "doctorId")
    @Mapping(target = "pet", source = "petId", qualifiedByName = "getPetById")
    @Mapping(target = "user.id", source = "userId")
    Appointment fromSaveRequestToEntity(SaveAppointmentRequest saveAppointmentRequest);

    @Mapping(source = "pet.name", target = "petName")
    @Mapping(source = "doctor.surname", target = "doctorSurname")
    @Mapping(source = "pet.id", target = "petId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.surname", target = "userSurname")
    AppointmentDto toDto(Appointment appointment);

    List<AppointmentDto> toDtoList(List<Appointment> appointments);

    @Mapping(source = "pet.name", target = "petName")
    @Mapping(source = "doctor.surname", target = "doctorSurname")
    @Mapping(source = "pet.id", target = "petId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.surname", target = "userSurname")
    SaveAppointmentRequest toSaveAppointmentRequest(Appointment appointment);

    @Mapping(target = "doctor", source = "doctorId")
    @Mapping(target = "pet", source = "petId")
    @Mapping(target = "user.id", source = "userId")
    Appointment fromDtoToEntity(AppointmentDto appointmentDto);

    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "pet.id", source = "petId")
    Appointment partialUpdate(SaveAppointmentRequest saveRequest, @MappingTarget Appointment appointment);
}
