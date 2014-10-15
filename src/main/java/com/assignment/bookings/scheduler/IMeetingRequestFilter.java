package com.assignment.bookings.scheduler;

import com.assignment.bookings.scheduler.model.BookingDetails;
import com.assignment.bookings.scheduler.model.MeetingRequest;

import java.time.LocalDateTime;
import java.util.Map;

public interface IMeetingRequestFilter {
    Map<LocalDateTime, BookingDetails> filter(MeetingRequest meetingRequest);
}
