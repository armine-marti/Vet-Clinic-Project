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

/**
 * Mapper interface for converting between Appointment entities, DTOs, and request objects.
 * This interface uses MapStruct to automate the conversion and mapping of Appointment data.
 * The mapping logic handles fields such as pet, doctor, and user data, and ensures that IDs are properly
 * mapped to their respective objects.
 */
@Mapper(
        componentModel = "spring",
        uses = {DoctorService.class, PetService.class, UserService.class},
        imports = {DoctorService.class, PetService.class, UserService.class}
)
public interface AppointmentMapper {

    /**
     * Converts a SaveAppointmentRequest to an Appointment entity.
     * Maps fields such as doctorId and petId to their respective entities (Doctor and Pet).
     *
     * @param saveAppointmentRequest The SaveAppointmentRequest object to convert.
     * @return The mapped Appointment entity.
     */
    @Mapping(target = "doctor", source = "doctorId", qualifiedByName = "getById")
    @Mapping(target = "pet", source = "petId", qualifiedByName = "getPetById")
    @Mapping(target = "user.id", source = "userId")
    Appointment fromSaveRequestToEntity(SaveAppointmentRequest saveAppointmentRequest);

    /**
     * Converts an Appointment entity to an AppointmentDto.
     * Maps fields such as pet.name to petName, doctor.surname to doctorSurname, and user.id to userId.
     *
     * @param appointment The Appointment entity to convert.
     * @return The mapped AppointmentDto.
     */
    @Mapping(source = "pet.name", target = "petName")
    @Mapping(source = "doctor.surname", target = "doctorSurname")
    @Mapping(source = "pet.id", target = "petId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.surname", target = "userSurname")
    AppointmentDto toDto(Appointment appointment);

    /**
     * Converts a list of Appointment entities to a list of AppointmentDto objects.
     *
     * @param appointments The list of Appointment entities to convert.
     * @return The list of mapped AppointmentDto objects.
     */
    List<AppointmentDto> toDtoList(List<Appointment> appointments);

    /**
     * Converts an Appointment entity to a SaveAppointmentRequest.
     * Maps fields such as pet.name to petName, doctor.surname to doctorSurname, and user.id to userId.
     *
     * @param appointment The Appointment entity to convert.
     * @return The mapped SaveAppointmentRequest.
     */
    @Mapping(source = "pet.name", target = "petName")
    @Mapping(source = "doctor.surname", target = "doctorSurname")
    @Mapping(source = "pet.id", target = "petId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.surname", target = "userSurname")
    SaveAppointmentRequest toSaveAppointmentRequest(Appointment appointment);

    /**
     * Converts an AppointmentDto to an Appointment entity.
     * Maps doctorId, petId, and userId to their respective entities.
     *
     * @param appointmentDto The AppointmentDto object to convert.
     * @return The mapped Appointment entity.
     */
    @Mapping(target = "doctor", source = "doctorId")
    @Mapping(target = "pet", source = "petId")
    @Mapping(target = "user.id", source = "userId")
    Appointment fromDtoToEntity(AppointmentDto appointmentDto);

    /**
     * Partially updates an Appointment entity with data from a SaveAppointmentRequest.
     * The existing Appointment entity is updated, and its fields are modified with the values from the request.
     *
     * @param saveRequest The SaveAppointmentRequest containing updated data.
     * @param appointment The existing Appointment entity to update.
     * @return The updated Appointment entity.
     */
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "pet.id", source = "petId")
    Appointment partialUpdate(SaveAppointmentRequest saveRequest, @MappingTarget Appointment appointment);
}
