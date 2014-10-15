package com.assignment.bookings.scheduler.parsers;

import com.assignment.bookings.scheduler.model.BookingDetails;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class MeetingDateLineParserTest {
    private MeetingDateLineParser meetingDateLineParser;

    @Before
    public void setUp() throws Exception {
        meetingDateLineParser = new MeetingDateLineParser();

    }

    @Test
    public void shouldParse() throws Exception {
        BookingDetails bookingDetails = new BookingDetails();
        meetingDateLineParser.parse("2011-03-21 09:00 2", bookingDetails);

        assertEquals(LocalDateTime.of(2011, 3, 21, 9, 0),bookingDetails.getMeetingStartDt());
        assertEquals(LocalDateTime.of(2011, 3, 21, 11, 0), bookingDetails.getMeetingEndDt());
    }

}