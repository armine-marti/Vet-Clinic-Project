package org.example.vetclinic.util;

import lombok.experimental.UtilityClass;

import java.util.Date;

/**
 * Utility class for date-related operations.
 * <p>
 * This class contains static methods to provide common date operations,
 * such as getting the current date. It is marked as a utility class and
 * cannot be instantiated.
 * </p>
 */
@UtilityClass
public class DateUtil {
    /**
     * Returns the current date and time.
     * <p>
     * This method uses `new Date()` to get the current date and time based on the system's clock.
     * </p>
     *
     * @return The current date and time as a {@link Date} object.
     */
    public Date getCurrentDate() {
        return new Date();
    }
}
