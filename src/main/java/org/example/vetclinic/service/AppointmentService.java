package org.example.vetclinic.service;

import org.example.vetclinic.dto.appointment.AppointmentDto;
import org.example.vetclinic.dto.appointment.SaveAppointmentRequest;
import org.example.vetclinic.entity.Appointment;
import org.example.vetclinic.entity.Status;

import java.util.Date;
import java.util.List;

public interface AppointmentService {

    List<AppointmentDto> appointmentsByUserId(int userId);

    Appointment save(SaveAppointmentRequest appointmentRequest);

    void cancelAppointment(String title, int userId);

    boolean existsByTitleAndUserId(String title, int userId);

    boolean existsByStartTimeAndUserId(Date startTime, int userId);

    Appointment getByTitleAndUserId(String title, int userId);

    List<Appointment> getAllByStatusAndUserIdAndStartTimeIsFuture(Status status, int userId);

    List<Appointment> getAllAppointments();

    Appointment getByTitleAndUserSurname(String title, String userSurname);

    void deleteAppointment(String title, int userId);

    Appointment save(Appointment appointment);
}
