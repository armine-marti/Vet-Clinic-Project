package org.example.vetclinic.repository;

import org.example.vetclinic.entity.Appointment;
import org.example.vetclinic.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing `Appointment` entities.
 * This interface provides CRUD operations and custom queries for `Appointment` entities.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    /**
     * Finds all appointments for a given user by their userId.
     *
     * @param userId The user ID for which to retrieve appointments.
     * @return A list of appointments for the specified user.
     */
    List<Appointment> findAllByUserId(int userId);

    /**
     * Finds an appointment by user ID and start time.
     *
     * @param userId    The ID of the user for whom to find the appointment.
     * @param startTime The start time of the appointment.
     * @return An Optional containing the appointment if found, otherwise empty.
     */
    Optional<Appointment> findByUserIdAndStartTime(int userId, Date startTime);

    /**
     * Checks if an appointment exists for a given start time and doctor ID.
     *
     * @param startTime The start time of the appointment.
     * @param doctorId  The ID of the doctor.
     * @return True if an appointment exists at the specified start time with the given doctor; false otherwise.
     */
    boolean existsByStartTimeAndDoctorId(Date startTime, int doctorId);

    /**
     * Cancels an appointment by setting its status to 'CANCELED'.
     * This is a custom update query for canceling appointments.
     *
     * @param appointmentId The ID of the appointment to cancel.
     */
    @Modifying
    @Query("UPDATE Appointment a SET a.status = 'CANCELED' WHERE a.id = :appointmentId")
    void cancelAppointment(@Param("appointmentId") int appointmentId);

    /**
     * Checks if an appointment with a given title exists for a user.
     *
     * @param title  The title of the appointment.
     * @param userId The user ID.
     * @return True if an appointment with the given title exists for the user; false otherwise.
     */
    boolean existsAppointmentByTitleAndUserId(String title, int userId);

    /**
     * Checks if an appointment exists with a given start time for a user.
     *
     * @param startTime The start time of the appointment.
     * @param userId    The user ID.
     * @return True if an appointment exists at the specified start time for the user; false otherwise.
     */
    boolean existsAppointmentByStartTimeAndUserId(Date startTime, int userId);

    /**
     * Finds all appointments for a given user with a status and that start after the specified time.
     *
     * @param status The status of the appointment.
     * @param userId The user ID.
     * @param now    The time after which the appointment should start.
     * @return A list of appointments for the user that match the specified status and have a start time after the provided time.
     */
    List<Appointment> findAllByStatusAndUserIdAndStartTimeIsAfter(Status status, int userId, Date now);

    /**
     * Checks if an appointment exists for a given start time and user ID.
     *
     * @param startTime The start time of the appointment.
     * @param userId    The user ID.
     * @return True if an appointment exists at the specified start time for the user; false otherwise.
     */
    boolean existsByStartTimeAndUserId(Date startTime, int userId);

    /**
     * Finds an appointment by its title and user ID.
     *
     * @param title  The title of the appointment.
     * @param userId The user ID.
     * @return An Optional containing the appointment if found, otherwise empty.
     */
    Optional<Appointment> findByTitleAndUserId(String title, int userId);

    /**
     * Finds an appointment by its title and the user's surname.
     *
     * @param title   The title of the appointment.
     * @param surname The surname of the user.
     * @return An Optional containing the appointment if found, otherwise empty.
     */
    Optional<Appointment> findByTitleAndUserSurname(String title, String surname);

    /**
     * Deletes an appointment by its title and user ID.
     *
     * @param title  The title of the appointment.
     * @param userId The user ID.
     */
    void deleteByTitleAndUserId(String title, int userId);
}
