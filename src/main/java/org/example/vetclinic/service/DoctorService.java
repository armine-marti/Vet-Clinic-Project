package org.example.vetclinic.service;

import org.example.vetclinic.dto.doctor.DoctorDto;
import org.example.vetclinic.entity.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor getById(int id);

    List<DoctorDto> getAll();
}
