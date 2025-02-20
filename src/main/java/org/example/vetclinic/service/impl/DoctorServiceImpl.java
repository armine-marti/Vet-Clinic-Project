package org.example.vetclinic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.dto.doctor.DoctorDto;
import org.example.vetclinic.entity.Doctor;
import org.example.vetclinic.mapper.DoctorMapper;
import org.example.vetclinic.repository.DoctorRepository;
import org.example.vetclinic.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public Doctor getById(int id) {
        return doctorRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public List<DoctorDto> findAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctorMapper.doctorDtoList(doctors);
    }

}
