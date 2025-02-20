package org.example.vetclinic.util;

import lombok.RequiredArgsConstructor;
import org.example.vetclinic.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class StartTimeCheck {


private final AppointmentRepository appointmentRepository;
    public List<Date> appointmentsTimeRange(Date date) {
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
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(userTime);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(dateInDB);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) &&
                cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY) &&
                cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE);
    }

    public boolean isValidAppointmentTime(Date appointmentTime) {
        List<Date> validTimes = appointmentsTimeRange(appointmentTime);

        for (Date validTime : validTimes) {
            if (isSameDateTime(validTime, appointmentTime)) {
                return true;
            }
        }
        return false;
    }

    public boolean appointmentsTimeValidation(Date startTime, int doctorId) {

        if (!isValidAppointmentTime(startTime)) {
            return false;
        }

        boolean isDoctorAvailable = appointmentRepository.existsByStartTimeAndDoctorId(startTime, doctorId);
        if (isDoctorAvailable) {
            return false;
        }
        return true;
    }

//   public Date removeSecondsAndMilliseconds(Date date) {
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        return calendar.getTime();
//    }

}
