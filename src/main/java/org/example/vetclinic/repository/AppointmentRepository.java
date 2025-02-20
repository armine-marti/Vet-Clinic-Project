package org.example.vetclinic.repository;

import org.example.vetclinic.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {


    List<Appointment> findAllByUser_Id(int userId);

    Optional <Appointment> findByUserIdAndStartTime(int userId, Date startTime);

    boolean existsByStartTimeAndDoctorId(Date startTime, int doctorId);

    @Modifying
    @Query("UPDATE Appointment a SET a.status = 'CANCELED' WHERE a.id = :appointmentId")
    void softAppointmentCancel(@Param("appointmentId") int appointmentId);

    Optional<Appointment> findByTitleAndUserId(String title, int userId);


}
