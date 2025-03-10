package org.example.vetclinic.service;

import org.example.vetclinic.dto.appointment.AppointmentDto;
import org.example.vetclinic.dto.appointment.SaveAppointmentRequest;
import org.example.vetclinic.entity.Appointment;
import org.example.vetclinic.entity.Status;

import java.util.Date;
import java.util.List;

/**
 * Service interface for handling business logic related to appointments.
 * This interface provides methods to perform CRUD operations on appointments
 * and various other appointment-related functionalities.
 */
public interface AppointmentService {

    /**
     * Retrieves a list of appointments associated with a user by their user ID.
     *
     * @param userId The ID of the user whose appointments are to be retrieved.
     * @return A list of {@link AppointmentDto} representing the user's appointments.
     */
    List<AppointmentDto> appointmentsByUserId(int userId);

    /**
     * Saves an appointment based on the provided appointment request.
     * Sets the status of the appointment to "BOOKED" before saving it.
     *
     * @param appointmentRequest The request object containing the details of the appointment to save.
     * @return The saved {@link Appointment} entity.
     */
    Appointment save(SaveAppointmentRequest appointmentRequest);

    /**
     * Cancels an appointment identified by its title and the user's ID.
     * Updates the appointment status to 'CANCELED'.
     *
     * @param title  The title of the appointment to cancel.
     * @param userId The ID of the user who owns the appointment.
     */
    void cancelAppointment(String title, int userId);

    /**
     * Checks if an appointment with the specified title exists for a given user.
     *
     * @param title  The title of the appointment to check.
     * @param userId The ID of the user to check for the appointment.
     * @return True if the appointment exists, false otherwise.
     */
    boolean existsByTitleAndUserId(String title, int userId);

    /**
     * Checks if an appointment with the specified start time exists for a given user.
     *
     * @param startTime The start time of the appointment to check.
     * @param userId    The ID of the user to check for the appointment.
     * @return True if an appointment exists with the specified start time for the user, false otherwise.
     */
    boolean existsByStartTimeAndUserId(Date startTime, int userId);

    /**
     * Retrieves an appointment by its title and the user's ID.
     *
     * @param title  The title of the appointment to retrieve.
     * @param userId The ID of the user to retrieve the appointment for.
     * @return The {@link Appointment} entity associated with the specified title and user ID.
     * @throws Exception if the appointment is not found.
     */
    Appointment getByTitleAndUserId(String title, int userId);

    /**
     * Retrieves a list of appointments with a specific status for a given user,
     * where the appointment's start time is in the future.
     *
     * @param status The status of the appointments to retrieve.
     * @param userId The ID of the user to retrieve appointments for.
     * @return A list of {@link Appointment} entities matching the specified criteria.
     */
    List<Appointment> getAllByStatusAndUserIdAndStartTimeIsFuture(Status status, int userId);

    /**
     * Retrieves a list of all appointments in the system.
     *
     * @return A list of all {@link Appointment} entities.
     */
    List<Appointment> getAllAppointments();

    /**
     * Retrieves an appointment by its title and the user's surname.
     *
     * @param title       The title of the appointment to retrieve.
     * @param userSurname The surname of the user to retrieve the appointment for.
     * @return The {@link Appointment} entity associated with the specified title and user surname.
     * @throws Exception if the appointment is not found.
     */
    Appointment getByTitleAndUserSurname(String title, String userSurname);

    /**
     * Deletes an appointment identified by its title and the user's ID.
     * The appointment is permanently removed from the system.
     *
     * @param title  The title of the appointment to delete.
     * @param userId The ID of the user to delete the appointment for.
     */
    void deleteAppointment(String title, int userId);

    /**
     * Saves an existing appointment.
     *
     * @param appointment The {@link Appointment} entity to save.
     * @return The saved {@link Appointment} entity.
     */
    Appointment save(Appointment appointment);
}
