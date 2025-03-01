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

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public List<AppointmentDto> appointmentsByUserId(int userId) {
        List<Appointment> appointments = appointmentRepository.findAllByUserId(userId);

        return appointmentMapper.toDtoList(appointments);
    }

    @Override
    public Appointment save(SaveAppointmentRequest appointmentRequest) {
        appointmentRequest.setStatus(Status.BOOKED);
        return appointmentRepository.save(
                appointmentMapper.fromSaveRequestToEntity(appointmentRequest)
        );
    }

    @Override
    @Transactional
    public void cancelAppointment(String title, int userId) {
        Appointment appointment = getByTitleAndUserId(title, userId);
        appointmentRepository.cancelAppointment(appointment.getId());
    }

    @Override
    public Appointment getByTitleAndUserId(String title, int userId) {
        return appointmentRepository.findByTitleAndUserId(title, userId).orElseThrow();
    }

    @Override
    public List<Appointment> getAllByStatusAndUserIdAndStartTimeIsFuture(Status status, int userId) {
        return appointmentRepository.findAllByStatusAndUserIdAndStartTimeIsAfter(status, userId, DateUtil.getCurrentDate());
    }

    @Override
    public boolean existsByTitleAndUserId(String title, int userId) {
        return appointmentRepository.existsAppointmentByTitleAndUserId(title, userId);
    }

    @Override
    public boolean existsByStartTimeAndUserId(Date startTime, int userId) {
        return appointmentRepository.existsByStartTimeAndUserId(startTime, userId);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getByTitleAndUserSurname(String title, String userSurname) {
        return appointmentRepository.findByTitleAndUserSurname(title, userSurname).orElseThrow();
    }

    @Override
    @Transactional
    public void deleteAppointment(String title, int userId) {
        appointmentRepository.deleteByTitleAndUserId(title, userId);
    }

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

}
