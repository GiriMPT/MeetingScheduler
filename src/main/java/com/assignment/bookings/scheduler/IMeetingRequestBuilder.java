package com.assignment.bookings.scheduler;

import com.assignment.bookings.scheduler.exceptions.IllegalTextFormatException;
import com.assignment.bookings.scheduler.model.MeetingRequest;


public interface IMeetingRequestBuilder {
    MeetingRequest parse(String input) throws IllegalTextFormatException;
}
