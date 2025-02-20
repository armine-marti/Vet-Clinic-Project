package org.example.vetclinic.service;

import org.example.vetclinic.dto.appointment.AppointmentDto;
import org.example.vetclinic.dto.appointment.SaveAppointmentRequest;
import org.example.vetclinic.entity.Appointment;

import java.util.Date;
import java.util.List;

public interface AppointmentService {

    List<AppointmentDto> appointmentsByUserId(int userId);

    boolean existsByTimeAndUserId(Date startTime, int userId);

    Appointment save(SaveAppointmentRequest appointmentRequest);

    void getAppointmentCancelled(String title, int userId);

    boolean getAppointmentByTitleAndUserId(String title, int userId);
}
