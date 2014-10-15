package com.assignment.bookings.scheduler.service;

import com.assignment.bookings.scheduler.IMeetingRequestBuilder;
import com.assignment.bookings.scheduler.exceptions.IllegalTextFormatException;
import com.assignment.bookings.scheduler.model.BookingDetails;
import com.assignment.bookings.scheduler.model.MeetingRequest;
import com.assignment.bookings.scheduler.model.OfficeHours;
import com.assignment.bookings.scheduler.parsers.OfficeHoursParser;
import com.assignment.bookings.scheduler.parsers.RequestSubmissionParser;
import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.List;


public class MeetingRequestBuilder implements IMeetingRequestBuilder {

    private final OfficeHoursParser officeHoursParser;
    private final RequestSubmissionParser requestSubmissionParser;

    public MeetingRequestBuilder(OfficeHoursParser officeHoursParser, RequestSubmissionParser requestSubmissionParser) {
        this.officeHoursParser = officeHoursParser;
        this.requestSubmissionParser = requestSubmissionParser;
    }

    @Override
    public MeetingRequest parse(String input) throws IllegalTextFormatException {
        final List<String> allLines = Splitter.on("\n").trimResults().omitEmptyStrings().splitToList(input);
        final OfficeHours officeHours = officeHoursParser.parse(allLines.get(0));
        final List<BookingDetails> bookingDetails = new ArrayList<>();

        if(allLines.size() > 1) {
            for (int i = 1; i < allLines.size(); i += 2) {
                if (i + 1 >= allLines.size()) {
                    throw new IllegalTextFormatException("invalid message structure");
                }
                bookingDetails.add(requestSubmissionParser.parse(allLines.get(i), allLines.get(i + 1)));
            }
        }


        final MeetingRequest meetingRequest = new MeetingRequest();
        meetingRequest.setOfficeHours(officeHours);
        meetingRequest.setBookingDetailsList(bookingDetails);
        return meetingRequest;

    }
}
