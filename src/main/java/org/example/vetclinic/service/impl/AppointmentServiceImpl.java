package org.example.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.dto.appointment.AppointmentDto;
import org.example.vetclinic.dto.appointment.SaveAppointmentRequest;
import org.example.vetclinic.entity.Appointment;
import org.example.vetclinic.entity.Status;
import org.example.vetclinic.mapper.AppointmentMapper;
import org.example.vetclinic.repository.AppointmentRepository;
import org.example.vetclinic.service.AppointmentService;
import org.example.vetclinic.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Implementation of the {@link AppointmentService} interface that handles all the business logic related to appointments.
 * This service provides methods to create, retrieve, update, cancel, and delete appointments for users.
 * <p>
 * It interacts with the {@link AppointmentRepository} for database operations and uses the {@link AppointmentMapper}
 * to convert between entities and DTOs.
 * <p>
 * The service also includes logic for validating appointment times and ensuring no conflicting appointments
 * exist for a given user or doctor.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    /**
     * Fetches all appointments for a given user by their ID.
     *
     * @param userId The ID of the user whose appointments need to be fetched.
     * @return A list of {@link AppointmentDto} representing the user's appointments.
     */
    @Override
    public List<AppointmentDto> appointmentsByUserId(int userId) {
        List<Appointment> appointments = appointmentRepository.findAllByUserId(userId);

        return appointmentMapper.toDtoList(appointments);
    }

    /**
     * Saves a new appointment with the given details.
     * The status of the appointment is set to {@link Status#BOOKED} by default.
     *
     * @param appointmentRequest The details of the appointment to be saved.
     * @return The saved {@link Appointment} entity.
     */
    @Override
    public Appointment save(SaveAppointmentRequest appointmentRequest) {
        appointmentRequest.setStatus(Status.BOOKED);
        return appointmentRepository.save(
                appointmentMapper.fromSaveRequestToEntity(appointmentRequest)
        );
    }

    /**
     * Cancels an appointment by setting its status to {@link Status#CANCELED}.
     * This operation is done within a transaction to ensure data consistency.
     *
     * @param title  The title of the appointment to be canceled.
     * @param userId The ID of the user who owns the appointment.
     */
    @Override
    @Transactional
    public void cancelAppointment(String title, int userId) {
        Appointment appointment = getByTitleAndUserId(title, userId);
        appointmentRepository.cancelAppointment(appointment.getId());
    }

    /**
     * Retrieves an appointment based on the title and user ID.
     *
     * @param title  The title of the appointment to be fetched.
     * @param userId The ID of the user whose appointment is to be fetched.
     * @return The {@link Appointment} entity matching the given title and user ID.
     * @throws Exception if no appointment is found with the specified title and user ID.
     */
    @Override
    public Appointment getByTitleAndUserId(String title, int userId) {
        return appointmentRepository.findByTitleAndUserId(title, userId).orElseThrow();
    }

    /**
     * Retrieves all future appointments for a given user, filtered by status.
     *
     * @param status The status of the appointments to be retrieved.
     * @param userId The ID of the user whose appointments are to be fetched.
     * @return A list of future appointments that match the specified status and user ID.
     */
    @Override
    public List<Appointment> getAllByStatusAndUserIdAndStartTimeIsFuture(Status status, int userId) {
        return appointmentRepository.findAllByStatusAndUserIdAndStartTimeIsAfter(status, userId, DateUtil.getCurrentDate());
    }

    /**
     * Checks if an appointment with the given title already exists for the user.
     *
     * @param title  The title of the appointment to check.
     * @param userId The ID of the user to check for the appointment.
     * @return True if an appointment with the given title exists for the user, false otherwise.
     */
    @Override
    public boolean existsByTitleAndUserId(String title, int userId) {
        return appointmentRepository.existsAppointmentByTitleAndUserId(title, userId);
    }

    /**
     * Checks if an appointment with the given start time already exists for the user.
     *
     * @param startTime The start time of the appointment to check.
     * @param userId    The ID of the user to check for the appointment.
     * @return True if an appointment with the given start time exists for the user, false otherwise.
     */
    @Override
    public boolean existsByStartTimeAndUserId(Date startTime, int userId) {
        return appointmentRepository.existsByStartTimeAndUserId(startTime, userId);
    }

    /**
     * Retrieves all appointments from the database.
     *
     * @return A list of all {@link Appointment} entities in the database.
     */
    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    /**
     * Retrieves an appointment based on the title and the user's surname.
     *
     * @param title       The title of the appointment to fetch.
     * @param userSurname The surname of the user whose appointment to fetch.
     * @return The {@link Appointment} entity matching the given title and user's surname.
     * @throws Exception if no appointment is found with the specified title and surname.
     */
    @Override
    public Appointment getByTitleAndUserSurname(String title, String userSurname) {
        return appointmentRepository.findByTitleAndUserSurname(title, userSurname).orElseThrow();
    }

    /**
     * Deletes an appointment by title and user ID.
     * This operation removes the appointment from the database.
     *
     * @param title  The title of the appointment to be deleted.
     * @param userId The ID of the user whose appointment is to be deleted.
     */
    @Override
    @Transactional
    public void deleteAppointment(String title, int userId) {
        appointmentRepository.deleteByTitleAndUserId(title, userId);
    }

    /**
     * Saves an {@link Appointment} entity.
     *
     * @param appointment The {@link Appointment} entity to be saved.
     * @return The saved {@link Appointment} entity.
     */
    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

}
