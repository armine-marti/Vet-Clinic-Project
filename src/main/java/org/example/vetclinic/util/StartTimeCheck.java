package org.example.vetclinic.util;

import lombok.RequiredArgsConstructor;
import org.example.vetclinic.repository.AppointmentRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class provides utility methods to check and validate the start time of appointments.
 * <p>
 * It ensures that the appointment times are valid within a specific range (from 08:30 AM to 07:30 PM)
 * and checks if the appointment time does not conflict with existing appointments for a specific doctor.
 * </p>
 */
@RequiredArgsConstructor
@Component
public class StartTimeCheck {

    private final AppointmentRepository appointmentRepository;

    /**
     * Generates a list of valid appointment times within the specified day.
     * <p>
     * The valid time range is from 08:30 AM to 07:30 PM, with appointments scheduled every 30 minutes.
     * </p>
     *
     * @param date The date for which the valid appointment times need to be calculated.
     * @return A list of valid {@link Date} objects representing appointment start times within the day.
     */
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

    /**
     * Compares two dates to check if they represent the same date and time (up to minute precision).
     *
     * @param userTime The date and time selected by the user.
     * @param dateInDB The date and time from the database.
     * @return True if the two dates represent the same time and date, false otherwise.
     */
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

    /**
     * Validates if a given appointment time is within the valid range (08:30 AM to 07:30 PM).
     *
     * @param appointmentTime The appointment time to be validated.
     * @return True if the appointment time is valid, false otherwise.
     */
    public boolean isValidAppointmentTime(Date appointmentTime) {
        List<Date> validTimes = getAppointmentsTimeRange(appointmentTime);

        for (Date validTime : validTimes) {
            if (isSameDateTime(validTime, appointmentTime)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates if an appointment can be scheduled at the specified start time for a given doctor.
     * <p>
     * The appointment time is validated to be within the valid time range and ensures there is no conflict
     * with existing appointments for the same doctor.
     * </p>
     *
     * @param startTime The start time of the appointment to be validated.
     * @param doctorId  The ID of the doctor for whom the appointment is being scheduled.
     * @return True if the appointment time is valid and not already booked for the doctor, false otherwise.
     */
    public boolean validateAppointmentsTime(Date startTime, int doctorId) {
        if (!isValidAppointmentTime(startTime)) {
            return false;
        }

        return !appointmentRepository.existsByStartTimeAndDoctorId(startTime, doctorId);
    }

}
