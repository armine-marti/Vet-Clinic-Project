package org.example.vetclinic.service.impl;

import jakarta.annotation.PostConstruct;
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
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @PostConstruct
    public void init() {
        log.info("AppointmentServiceImpl injected: {}", this);
    }

    @Override
    public List<AppointmentDto> appointmentsByUserId(int userId) {

        List<Appointment> appointments = appointmentRepository.findAllByUser_Id(userId);
        List<AppointmentDto> appointmentsDtos = appointmentMapper.toDtoList(appointments);
        return appointmentsDtos;
    }

    @Override
    public boolean existsByTimeAndUserId(Date startTime, int userId) {
        Appointment appointment = appointmentRepository.findByUserIdAndStartTime(userId, startTime).orElse(null);
        return appointment != null;
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
    public void getAppointmentCancelled(String title, int userId) {
        Appointment appointment = appointmentRepository.findByTitleAndUserId(title, userId).orElse(null);
        appointmentRepository.softAppointmentCancel(appointment.getId());
    }

    @Override
    public boolean getAppointmentByTitleAndUserId(String title, int userId) {
        Appointment appointment = appointmentRepository.findByTitleAndUserId(title, userId).orElse(null);
        if (appointment != null) {
            return true;
        }
        return false;
    }

}
