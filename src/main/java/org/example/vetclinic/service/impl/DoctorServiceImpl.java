package org.example.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.dto.doctor.DoctorDto;
import org.example.vetclinic.entity.Doctor;
import org.example.vetclinic.entity.StatusDoctor;
import org.example.vetclinic.mapper.DoctorMapper;
import org.example.vetclinic.repository.DoctorRepository;
import org.example.vetclinic.service.DoctorService;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;


    @Override
    @Named("getById")
    public Doctor getById(int doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow();
    }

    @Override
    public List<DoctorDto> getAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctorMapper.entityListToDto(doctors);
    }


    @Override
    public boolean existsByEmail(String email) {
        return doctorRepository.existsByEmail(email);
    }


    @Override
    public Doctor getByEmail(String email) {
        return doctorRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public Doctor save(Doctor doctor) {
        doctor.setStatusDoctor(StatusDoctor.CURRENT_EMPLOYEE);
        return doctorRepository.save(doctor);
    }

    @Override
    public List<DoctorDto> getAllByStatusDoctor(StatusDoctor statusDoctor) {
        List<Doctor> doctors = doctorRepository.findAllByStatusDoctor(statusDoctor);
        return doctorMapper.entityListToDto(doctors);
    }

    @Override
    @Transactional
    public void deleteDoctor(int doctorId) {
        Doctor doctor = getById(doctorId);
        doctorRepository.softDoctorDelete(doctor.getId());
    }

    @Override
    public Doctor getByEmailAndSurname(String email, String surname) {
        return doctorRepository.findByEmailAndSurname(email, surname).orElseThrow();
    }
}
