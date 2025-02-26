package org.example.vetclinic.mapper;

import org.example.vetclinic.dto.doctor.DoctorDto;
import org.example.vetclinic.entity.Doctor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    List<DoctorDto> doctorDtoList(List<Doctor> doctors);
}
