package org.example.vetclinic.repository;

import org.example.vetclinic.entity.Doctor;
import org.example.vetclinic.entity.StatusDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    boolean existsByEmail(String email);


    List<Doctor> findAllByStatusDoctor(StatusDoctor statusDoctor);

    Optional<Doctor> findByEmail(String email);

    @Modifying
    @Query("UPDATE Doctor d SET d.statusDoctor = 'EX_EMPLOYEE' WHERE d.id = :doctorId")
    void softDoctorDelete(@Param("doctorId") int doctorId);

    Optional<Doctor> findByEmailAndSurname(String email, String surname);
}
