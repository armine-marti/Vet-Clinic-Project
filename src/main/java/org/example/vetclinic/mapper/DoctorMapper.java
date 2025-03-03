package org.example.vetclinic.mapper;

import org.example.vetclinic.dto.doctor.DoctorDto;
import org.example.vetclinic.dto.doctor.SaveDoctorRequest;
import org.example.vetclinic.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {


    List<DoctorDto> entityListToDto(List<Doctor> doctors);

    DoctorDto toDto(Doctor doctor);

    Doctor toEntity(SaveDoctorRequest saveDoctorRequest);

    SaveDoctorRequest toSaveDoctorRequest(Doctor doctor);


    Doctor partialUpdate(SaveDoctorRequest saveRequest, @MappingTarget Doctor doctor);


}
