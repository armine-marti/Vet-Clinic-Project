package org.example.vetclinic.util;

import lombok.RequiredArgsConstructor;
import org.example.vetclinic.repository.AppointmentRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class StartTimeCheck {

    private final AppointmentRepository appointmentRepository;

    public List<Date> getAppointmentsTimeRange(Date date) {
        List<Date> validTimes = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);

        Calendar endOfDay = Calendar.getInstance();
        endOfDay.setTime(date);
        endOfDay.set(Calendar.HOUR_OF_DAY, 19);
        endOfDay.set(Calendar.MINUTE, 30);
        endOfDay.set(Calendar.SECOND, 0);

        while (calendar.before(endOfDay)) {
            validTimes.add(calendar.getTime());
            calendar.add(Calendar.MINUTE, 30);
        }

        return validTimes;
    }

    public boolean isSameDateTime(Date userTime, Date dateInDB) {
        Calendar userTimeCal = Calendar.getInstance();
        userTimeCal.setTime(userTime);
        Calendar timeInDbCal = Calendar.getInstance();
        timeInDbCal.setTime(dateInDB);

        return userTimeCal.get(Calendar.YEAR) == timeInDbCal.get(Calendar.YEAR) &&
                userTimeCal.get(Calendar.MONTH) == timeInDbCal.get(Calendar.MONTH) &&
                userTimeCal.get(Calendar.DAY_OF_MONTH) == timeInDbCal.get(Calendar.DAY_OF_MONTH) &&
                userTimeCal.get(Calendar.HOUR_OF_DAY) == timeInDbCal.get(Calendar.HOUR_OF_DAY) &&
                userTimeCal.get(Calendar.MINUTE) == timeInDbCal.get(Calendar.MINUTE);
    }

    public boolean isValidAppointmentTime(Date appointmentTime) {
        List<Date> validTimes = getAppointmentsTimeRange(appointmentTime);

        for (Date validTime : validTimes) {
            if (isSameDateTime(validTime, appointmentTime)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateAppointmentsTime(Date startTime, int doctorId) {
        if (!isValidAppointmentTime(startTime)) {
            return false;
        }

        return !appointmentRepository.existsByStartTimeAndDoctorId(startTime, doctorId);
    }

}
