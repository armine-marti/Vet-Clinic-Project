package org.example.vetclinic.service;

import org.example.vetclinic.dto.doctor.DoctorDto;
import org.example.vetclinic.entity.Doctor;
import org.example.vetclinic.entity.StatusDoctor;
import org.mapstruct.Named;

import java.util.List;

public interface DoctorService {
    @Named("getById")
    Doctor getById(int id);

    List<DoctorDto> getAll();

    boolean existsByEmail(String email);

    Doctor save(Doctor doctor);

    List<DoctorDto> getAllByStatusDoctor(StatusDoctor statusDoctor);

    void deleteDoctor(int doctorId);

    Doctor getByEmail(String email);

    Doctor getByEmailAndSurname(String email, String surname);
}
