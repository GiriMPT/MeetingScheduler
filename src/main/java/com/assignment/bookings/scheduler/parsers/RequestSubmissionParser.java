package com.assignment.bookings.scheduler.parsers;

import com.assignment.bookings.scheduler.exceptions.IllegalTextFormatException;
import com.assignment.bookings.scheduler.model.BookingDetails;
import com.assignment.bookings.scheduler.utils.ValidationUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class RequestSubmissionParser  {

    private final MeetingDateLineParser meetingDateLineParser;

    public RequestSubmissionParser(MeetingDateLineParser meetingDateLineParser) {
        this.meetingDateLineParser = meetingDateLineParser;
    }

    public BookingDetails parse(String line1, String line2) throws IllegalTextFormatException {
        ValidationUtils.validateLines(line1, line2);
        final BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setRequestedDt(format(line1.substring(0, 19)));
        bookingDetails.setEmpId(line1.substring(20));

        meetingDateLineParser.parse(line2, bookingDetails);

        return bookingDetails;
    }

    private LocalDateTime format(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(str, formatter);
    }
}
