package com.assignment.bookings.scheduler.parsers;

import com.assignment.bookings.scheduler.exceptions.IllegalTextFormatException;
import com.assignment.bookings.scheduler.model.OfficeHours;
import com.assignment.bookings.scheduler.utils.ValidationUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;

import static java.lang.Integer.valueOf;


public class OfficeHoursParser {

    public OfficeHours parse(String input) throws IllegalTextFormatException {
        ValidationUtils.validateOfficeHours(input);
        final OfficeHours officeHours = new OfficeHours();
        officeHours.setStart(toTime(StringUtils.substringBefore(input, " ")));
        officeHours.setEnd(toTime(StringUtils.substringAfter(input, " ")));
        return officeHours;
    }


    private LocalTime toTime(String value) {
            return LocalTime.of(valueOf(value.substring(0, 2)), valueOf(value.substring(2)));
    }

}
