package com.assignment.bookings.scheduler;

import com.assignment.bookings.scheduler.model.BookingDetails;

import java.time.LocalDateTime;
import java.util.Map;

public interface IMeetingResponseBuilder {
    String build(Map<LocalDateTime, BookingDetails> map);
}
