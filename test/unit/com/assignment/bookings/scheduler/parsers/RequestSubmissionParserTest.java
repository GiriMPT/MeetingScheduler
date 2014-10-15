package com.assignment.bookings.scheduler.parsers;

import com.assignment.bookings.scheduler.exceptions.IllegalTextFormatException;
import com.assignment.bookings.scheduler.model.BookingDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RequestSubmissionParserTest {

    private RequestSubmissionParser requestSubmissionParser;

    private MeetingDateLineParser meetingDateLineParser;

    @Before
    public void setUp() throws Exception {
        meetingDateLineParser = new MeetingDateLineParser();
        requestSubmissionParser = new RequestSubmissionParser(meetingDateLineParser);
    }

    @Test
    public void testParse() throws Exception {
        BookingDetails bookingDetails = withBookingDetails("EMP01", 2011, 3, 21, 9, 0, 2, LocalDateTime.of(2011, 3, 17, 10, 17, 6));
        assertEquals(bookingDetails, requestSubmissionParser.parse("2011-03-17 10:17:06 EMP01", "2011-03-21 09:00 2"));
    }

    @Test(expected = IllegalTextFormatException.class)
    public void exceptionWhenLineOneIsNull() throws IllegalTextFormatException {
        requestSubmissionParser.parse(null, "2011-03-21 09:00 2");
    }

    @Test(expected = IllegalTextFormatException.class)
    public void exceptionWhenLineOneIsEmpty() throws IllegalTextFormatException {
        requestSubmissionParser.parse(" ", "2011-03-21 09:00 2");
    }

    @Test(expected = IllegalTextFormatException.class)
    public void exceptionWhenLineOneLengthIsLessThan21() throws IllegalTextFormatException {
        requestSubmissionParser.parse("1212", "2011-03-21 09:00 2");
    }

    @Test(expected = IllegalTextFormatException.class)
    public void exceptionWhenLineTwoIsNull() throws IllegalTextFormatException {
        requestSubmissionParser.parse("2011-03-17 10:17:06 EMP01", null);
    }

    @Test(expected = IllegalTextFormatException.class)
    public void exceptionWhenLineTwoIsEmpty() throws IllegalTextFormatException {
        requestSubmissionParser.parse("2011-03-17 10:17:06 EMP01", " ");
    }

    @Test(expected = IllegalTextFormatException.class)
    public void exceptionWhenLineTwoLengthIsLessThan18() throws IllegalTextFormatException {
        requestSubmissionParser.parse("2011-03-17 10:17:06 EMP01", "2011-03-21 09:00 ");
    }

    private BookingDetails withBookingDetails(String empId, int year, int month, int day, int hour, int minute, int duration, LocalDateTime requestDt) {
        LocalDateTime startDate1 = getLocalDateTime(year, month, day, hour, minute, 0);
        LocalDateTime endDate1 = getLocalDateTime(year, month, day, hour, minute, 0);

        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setMeetingStartDt(startDate1);
        bookingDetails.setMeetingEndDt( endDate1.plusHours(duration));
        bookingDetails.setEmpId(empId);
        bookingDetails.setRequestedDt(requestDt);
        return bookingDetails;
    }

    private LocalDateTime getLocalDateTime(int year, int month, int day, int hour, int minute, int sec) {
        return LocalDateTime.of(getLocalDate(year, month, day), getLocalTime(hour, minute, sec));
    }

    private LocalDate getLocalDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    private LocalTime getLocalTime(int hour, int minute, int sec) {
        return LocalTime.of(hour, minute, sec);
    }


}