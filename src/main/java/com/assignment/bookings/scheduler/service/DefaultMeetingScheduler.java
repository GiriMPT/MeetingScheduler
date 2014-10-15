package com.assignment.bookings.scheduler.service;

import com.assignment.bookings.scheduler.IMeetingRequestBuilder;
import com.assignment.bookings.scheduler.IMeetingRequestFilter;
import com.assignment.bookings.scheduler.IMeetingResponseBuilder;
import com.assignment.bookings.scheduler.IMeetingScheduler;
import com.assignment.bookings.scheduler.exceptions.IllegalTextFormatException;
import com.assignment.bookings.scheduler.model.BookingDetails;
import com.assignment.bookings.scheduler.model.MeetingRequest;
import com.assignment.bookings.scheduler.utils.ValidationUtils;

import java.time.LocalDateTime;
import java.util.Map;

public class DefaultMeetingScheduler implements IMeetingScheduler {

    private final IMeetingRequestBuilder meetingRequestBuilder;
    private final IMeetingRequestFilter meetingRequestFilter;
    private final IMeetingResponseBuilder meetingResponseBuilder;

    public DefaultMeetingScheduler(IMeetingRequestBuilder meetingRequestBuilder, IMeetingRequestFilter meetingRequestFilter,
                                   IMeetingResponseBuilder meetingResponseBuilder) {
        this.meetingRequestBuilder = meetingRequestBuilder;
        this.meetingRequestFilter = meetingRequestFilter;
        this.meetingResponseBuilder = meetingResponseBuilder;
    }

    @Override
    public String getMeetingsCalendar(String inputBatch) throws IllegalArgumentException, IllegalTextFormatException {
        ValidationUtils.validateInputBatch(inputBatch);

        final MeetingRequest meetingRequest = meetingRequestBuilder.parse(inputBatch);
        final Map<LocalDateTime, BookingDetails> filteredData = meetingRequestFilter.filter(meetingRequest);
        return meetingResponseBuilder.build(filteredData);
    }


}
