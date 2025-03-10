package org.example.vetclinic.service;

import org.example.vetclinic.dto.doctor.DoctorDto;
import org.example.vetclinic.entity.Doctor;
import org.example.vetclinic.entity.StatusDoctor;
import org.mapstruct.Named;

import java.util.List;

/**
 * Service interface for managing doctors.
 * This interface provides methods to perform CRUD operations on doctors
 * and various other doctor-related functionalities.
 */
public interface DoctorService {
    /**
     * Retrieves a doctor by their ID.
     *
     * @param id The ID of the doctor to retrieve.
     * @return The {@link Doctor} entity with the specified ID.
     * @throws Exception if no doctor is found with the provided ID.
     */
    @Named("getById")
    Doctor getById(int id);

    /**
     * Retrieves all doctors in the system.
     *
     * @return A list of {@link DoctorDto} representing all doctors.
     */
    List<DoctorDto> getAll();

    /**
     * Checks if a doctor exists with the specified email.
     *
     * @param email The email of the doctor to check.
     * @return True if a doctor exists with the specified email, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Saves a new doctor to the system.
     *
     * @param doctor The {@link Doctor} entity to save.
     * @return The saved {@link Doctor} entity.
     */
    Doctor save(Doctor doctor);

    /**
     * Retrieves all doctors with the specified status.
     *
     * @param statusDoctor The status of the doctors to retrieve.
     * @return A list of {@link DoctorDto} representing the doctors with the specified status.
     */
    List<DoctorDto> getAllByStatusDoctor(StatusDoctor statusDoctor);

    /**
     * Soft-deletes a doctor by their ID.
     * The doctor’s status is updated to 'EX_EMPLOYEE', marking them as deleted.
     *
     * @param doctorId The ID of the doctor to delete.
     */
    void deleteDoctor(int doctorId);

    /**
     * Retrieves a doctor by their email.
     *
     * @param email The email of the doctor to retrieve.
     * @return The {@link Doctor} entity with the specified email.
     * @throws Exception if no doctor is found with the provided email.
     */
    Doctor getByEmail(String email);

    /**
     * Retrieves a doctor by their email and surname.
     *
     * @param email   The email of the doctor to retrieve.
     * @param surname The surname of the doctor to retrieve.
     * @return The {@link Doctor} entity with the specified email and surname.
     * @throws Exception if no doctor is found with the provided email and surname.
     */
    Doctor getByEmailAndSurname(String email, String surname);
}
