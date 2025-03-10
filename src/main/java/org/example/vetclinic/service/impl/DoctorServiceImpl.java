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

/**
 * Implementation of the {@link DoctorService} interface that handles the business logic related to doctors.
 * This service provides methods for managing doctor entities, including retrieving, saving, updating, and deleting doctors.
 * <p>
 * It interacts with the {@link DoctorRepository} for database operations and uses the {@link DoctorMapper}
 * to convert between doctor entities and their corresponding DTOs.
 * <p>
 * The service also includes functionality for querying doctors by email, surname, and status.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    /**
     * Retrieves a doctor by their ID.
     *
     * @param doctorId The ID of the doctor to be retrieved.
     * @return The {@link Doctor} entity corresponding to the provided ID.
     * @throws Exception if no doctor is found with the provided ID.
     */
    @Override
    @Named("getById")
    public Doctor getById(int doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow();
    }

    /**
     * Retrieves all doctors from the database.
     *
     * @return A list of {@link DoctorDto} representing all doctors.
     */
    @Override
    public List<DoctorDto> getAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctorMapper.entityListToDto(doctors);
    }


    /**
     * Checks if a doctor with the given email already exists in the database.
     *
     * @param email The email to be checked.
     * @return True if a doctor with the given email exists, false otherwise.
     */
    @Override
    public boolean existsByEmail(String email) {
        return doctorRepository.existsByEmail(email);
    }

    /**
     * Retrieves a doctor by their email.
     *
     * @param email The email of the doctor to be retrieved.
     * @return The {@link Doctor} entity corresponding to the provided email.
     * @throws Exception if no doctor is found with the provided email.
     */
    @Override
    public Doctor getByEmail(String email) {
        return doctorRepository.findByEmail(email).orElseThrow();
    }

    /**
     * Saves a new doctor entity to the database.
     * The status of the doctor is set to {@link StatusDoctor#CURRENT_EMPLOYEE} by default.
     *
     * @param doctor The doctor entity to be saved.
     * @return The saved {@link Doctor} entity.
     */
    @Override
    public Doctor save(Doctor doctor) {
        doctor.setStatusDoctor(StatusDoctor.CURRENT_EMPLOYEE);
        return doctorRepository.save(doctor);
    }

    /**
     * Retrieves all doctors with the specified status.
     *
     * @param statusDoctor The status of the doctors to be retrieved.
     * @return A list of {@link DoctorDto} representing the doctors with the given status.
     */
    @Override
    public List<DoctorDto> getAllByStatusDoctor(StatusDoctor statusDoctor) {
        List<Doctor> doctors = doctorRepository.findAllByStatusDoctor(statusDoctor);
        return doctorMapper.entityListToDto(doctors);
    }

    /**
     * Soft deletes a doctor by setting their status to {@link StatusDoctor#EX_EMPLOYEE}.
     * This operation does not remove the doctor from the database.
     * It is done within a transaction to ensure consistency.
     *
     * @param doctorId The ID of the doctor to be deleted.
     */
    @Override
    @Transactional
    public void deleteDoctor(int doctorId) {
        Doctor doctor = getById(doctorId);
        doctorRepository.softDoctorDelete(doctor.getId());
    }

    /**
     * Retrieves a doctor by their email and surname.
     *
     * @param email   The email of the doctor.
     * @param surname The surname of the doctor.
     * @return The {@link Doctor} entity that matches the provided email and surname.
     * @throws Exception if no doctor is found with the provided email and surname.
     */
    @Override
    public Doctor getByEmailAndSurname(String email, String surname) {
        return doctorRepository.findByEmailAndSurname(email, surname).orElseThrow();
    }
}
