package com.assignment.bookings.scheduler.parsers;

import com.assignment.bookings.scheduler.model.BookingDetails;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MeetingDateLineParser {

    public void parse(String input, final BookingDetails bookingDetails) {
        bookingDetails.setMeetingStartDt(format(input.substring(0, 16)));
        int duration = Integer.parseInt(input.substring(17));
        bookingDetails.setMeetingEndDt(bookingDetails.getMeetingStartDt().plusHours(duration));
    }

    private LocalDateTime format(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(str, formatter);
    }
}
