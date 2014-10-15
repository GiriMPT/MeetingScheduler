package com.assignment.bookings.scheduler.utils;

import com.assignment.bookings.scheduler.exceptions.IllegalTextFormatException;
import org.apache.commons.lang3.StringUtils;


public class ValidationUtils {

    public static void validateOfficeHours(String input) throws IllegalTextFormatException {
        if (StringUtils.isBlank(input)) {
            throw new IllegalArgumentException(
                    "office hours cannot be null or empty.");
        }

        if (input.length() != 9) {
            throw new IllegalTextFormatException(
                    "office hours format should be 'hhmm hhmm', with length = 9");
        }

    }

    public static void validateLines(String line1, String line2) throws IllegalTextFormatException {
        if (StringUtils.isBlank(line1) || line1.length() < 21) {
            throw new IllegalTextFormatException("Invalid record");
        }

        if (StringUtils.isBlank(line2) || line2.length() < 18) {
            throw new IllegalTextFormatException("Invalid record");
        }
    }

    public static void validateInputBatch(String inputBatch) {
        if (StringUtils.isBlank(inputBatch)) {
            throw new IllegalArgumentException("inputBatch cannot be null or empty");
        }
    }
}
