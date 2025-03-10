package org.example.vetclinic.mapper;

import org.example.vetclinic.dto.doctor.DoctorDto;
import org.example.vetclinic.dto.doctor.SaveDoctorRequest;
import org.example.vetclinic.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper interface for converting between Doctor entities, DTOs, and request objects.
 * This interface uses MapStruct to automate the conversion and mapping of Doctor data.
 * The mapping logic handles the conversion of fields such as doctor name, surname, email,
 * and specialization between DTOs and entities.
 */
@Mapper(componentModel = "spring")
public interface DoctorMapper {

    /**
     * Converts a list of Doctor entities to a list of DoctorDto objects.
     *
     * @param doctors The list of Doctor entities to convert.
     * @return The list of mapped DoctorDto objects.
     */
    List<DoctorDto> entityListToDto(List<Doctor> doctors);

    /**
     * Converts a Doctor entity to a DoctorDto.
     *
     * @param doctor The Doctor entity to convert.
     * @return The mapped DoctorDto.
     */
    DoctorDto toDto(Doctor doctor);

    /**
     * Converts a SaveDoctorRequest to a Doctor entity.
     *
     * @param saveDoctorRequest The SaveDoctorRequest object to convert.
     * @return The mapped Doctor entity.
     */
    Doctor toEntity(SaveDoctorRequest saveDoctorRequest);

    /**
     * Converts a Doctor entity to a SaveDoctorRequest.
     *
     * @param doctor The Doctor entity to convert.
     * @return The mapped SaveDoctorRequest.
     */
    SaveDoctorRequest toSaveDoctorRequest(Doctor doctor);


    /**
     * Partially updates an existing Doctor entity with data from a SaveDoctorRequest.
     * This method will only update the fields that are present in the request.
     *
     * @param saveRequest The SaveDoctorRequest containing updated data.
     * @param doctor      The existing Doctor entity to update.
     * @return The updated Doctor entity.
     */
    Doctor partialUpdate(SaveDoctorRequest saveRequest, @MappingTarget Doctor doctor);


}
