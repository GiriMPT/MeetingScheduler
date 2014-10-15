package com.assignment.bookings.scheduler;


import com.assignment.bookings.scheduler.exceptions.IllegalTextFormatException;

public interface IMeetingScheduler {

    String getMeetingsCalendar(String inputBatch)
            throws IllegalArgumentException, IllegalTextFormatException;

}
