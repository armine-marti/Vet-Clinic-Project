package org.example.vetclinic.repository;

import org.example.vetclinic.entity.Doctor;
import org.example.vetclinic.entity.StatusDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing `Doctor` entities.
 * This interface extends `JpaRepository` and provides CRUD operations along with custom queries for `Doctor` entities.
 */
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    /**
     * Checks if a doctor exists by their email.
     *
     * @param email The email address of the doctor.
     * @return True if a doctor exists with the given email, otherwise false.
     */
    boolean existsByEmail(String email);

    /**
     * Finds all doctors by their status.
     *
     * @param statusDoctor The status of the doctor (e.g., CURRENT_EMPLOYEE, EX_EMPLOYEE).
     * @return A list of doctors who have the specified status.
     */
    List<Doctor> findAllByStatusDoctor(StatusDoctor statusDoctor);

    /**
     * Finds a doctor by their email.
     *
     * @param email The email address of the doctor.
     * @return An `Optional` containing the doctor if found, otherwise empty.
     */
    Optional<Doctor> findByEmail(String email);

    /**
     * Soft deletes a doctor by updating their status to 'EX_EMPLOYEE'.
     * This marks the doctor as an ex-employee without actually removing the record from the database.
     *
     * @param doctorId The ID of the doctor to soft delete.
     */
    @Modifying
    @Query("UPDATE Doctor d SET d.statusDoctor = 'EX_EMPLOYEE' WHERE d.id = :doctorId")
    void softDoctorDelete(@Param("doctorId") int doctorId);

    /**
     * Finds a doctor by their email and surname.
     *
     * @param email   The email address of the doctor.
     * @param surname The surname of the doctor.
     * @return An `Optional` containing the doctor if found, otherwise empty.
     */
    Optional<Doctor> findByEmailAndSurname(String email, String surname);
}
