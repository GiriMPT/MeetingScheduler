package com.assignment.bookings.scheduler.service;

import com.assignment.bookings.scheduler.model.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MeetingRequestFilterTest {

    private MeetingRequestFilter meetingRequestFilter;

    @Before
    public void setUp() throws Exception {
        meetingRequestFilter = new MeetingRequestFilter();

    }

    @Test
    public void shouldFilterOk() throws Exception {
        OfficeHours officeHours = withOfficeHours(9, 0, 17, 0);
        BookingDetails bookingDetails1 = withBookingDetails("EMP01", "2011-03-21 09:00:00", "2011-03-21 09:00", 2);
        BookingDetails bookingDetails2 = withBookingDetails("EMP02", "2011-02-21 12:00:00", "2011-02-21 12:00", 2);
        BookingDetails bookingDetails3 = withBookingDetails("EMP03", "2011-01-21 12:00:00", "2011-01-21 12:00", 2);
        BookingDetails bookingDetails4 = withBookingDetails("EMP04", "2011-10-21 12:00:00", "2011-03-21 12:00", 2);

        List<BookingDetails> list = Lists.newArrayList(bookingDetails1, bookingDetails2, bookingDetails3, bookingDetails4);

        Map<LocalDateTime, BookingDetails> expectedResult = Maps.newTreeMap();
        expectedResult.put(getLocalDateTime(2011, 3, 21, 9, 0, 0), bookingDetails1);
        expectedResult.put(getLocalDateTime(2011, 2, 21, 12, 0, 0), bookingDetails2);
        expectedResult.put( getLocalDateTime(2011, 1, 21, 12, 0, 0), bookingDetails3);
        expectedResult.put(getLocalDateTime(2011, 3, 21, 12, 0, 0), bookingDetails4);

        assertEquals(expectedResult, meetingRequestFilter.filter(withMeetingRequest(officeHours, list)));
    }

    @Test
    public void shouldNotFilterNonOverlappingMeetingAndOfficeOpenAllDayLong( ) {
        OfficeHours officeHours = officeOpenAllDay();
        BookingDetails bookingDetails1 = withBookingDetails("EMP01", "2011-03-21 09:00:00", "2011-03-21 09:00", 24);
        BookingDetails bookingDetails2 = withBookingDetails("EMP02", "2011-01-21 12:00:00", "2011-01-21 12:00", 14);
        BookingDetails bookingDetails3 = withBookingDetails("EMP03", "2011-01-19 12:00:00", "2011-01-19 12:00", 2);
        BookingDetails bookingDetails4 = withBookingDetails("EMP04", "2011-10-21 12:00:00", "2011-10-21 12:00", 2);

        List<BookingDetails> list = Lists.newArrayList(bookingDetails1, bookingDetails2, bookingDetails3, bookingDetails4);

        Map<LocalDateTime, BookingDetails> expectedResult = Maps.newTreeMap();
        expectedResult.put(getLocalDateTime(2011, 3, 21, 9, 0 , 0), bookingDetails1);
        expectedResult.put(getLocalDateTime(2011, 1, 21, 12, 0 , 0), bookingDetails2);
        expectedResult.put( getLocalDateTime(2011, 1, 19, 12, 0, 0), bookingDetails3);
        expectedResult.put(getLocalDateTime(2011, 10, 21, 12, 0, 0), bookingDetails4);
        assertEquals(expectedResult, meetingRequestFilter.filter(withMeetingRequest(officeHours, list)));
    }


    @Test
    public void shouldFilterOutMeetingsOutOfOfficeHours() {
        OfficeHours officeHours = withOfficeHours(9, 0, 17, 0);
        BookingDetails bookingDetails1 = withBookingDetails("EMP01", "2011-03-21 09:00:00", "2011-03-21 09:00", 11);
        BookingDetails bookingDetails2 = withBookingDetails("EMP02", "2011-03-21 08:00:00", "2011-03-21 08:00", 2);
        BookingDetails bookingDetails3 = withBookingDetails("EMP03", "2011-02-21 12:00:00", "2011-02-21 12:00", 2);
        BookingDetails bookingDetails4 = withBookingDetails("EMP04", "2011-02-21 16:00:00", "2011-02-21 16:00", 1);
        BookingDetails bookingDetails5 = withBookingDetails("EMP05", "2011-02-21 16:01:00", "2011-02-21 16:01", 1);

        List<BookingDetails> list = Lists.newArrayList(bookingDetails1, bookingDetails2, bookingDetails3, bookingDetails4, bookingDetails5);

        Map<LocalDateTime, BookingDetails> expectedResult = Maps.newTreeMap();
        expectedResult.put(getLocalDateTime(2011, 2, 21, 12, 0, 0), bookingDetails3);
        expectedResult.put(getLocalDateTime(2011, 2, 21, 16, 0, 0), bookingDetails4);
        assertEquals(expectedResult, meetingRequestFilter.filter(withMeetingRequest(officeHours, list)));
    }

    @Test
    public void shouldFilterOutOverlappingMeetingsAndRetainOldestMeetingRequest() {
        OfficeHours officeHours = withOfficeHours(9, 0, 17, 0);
        BookingDetails bookingDetails1 = withBookingDetails("EMP01", "2011-03-11 09:00:20", "2011-03-21 09:00", 2);
        BookingDetails bookingDetails2 = withBookingDetails("EMP02", "2011-03-10 09:00:20", "2011-03-21 10:00", 2);
        BookingDetails bookingDetails3 = withBookingDetails("EMP03", "2011-02-21 12:00:00", "2011-02-21 12:00", 2);

        List<BookingDetails> list = Lists.newArrayList(bookingDetails1, bookingDetails2, bookingDetails3);

        Map<LocalDateTime, BookingDetails> expectedResult = Maps.newTreeMap();
        expectedResult.put(getLocalDateTime(2011, 3, 21, 10, 0 , 0), bookingDetails2);
        expectedResult.put(getLocalDateTime(2011, 2, 21, 12, 0 , 0), bookingDetails3);
        assertEquals(expectedResult, meetingRequestFilter.filter(withMeetingRequest(officeHours, list)));

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


    private MeetingRequest withMeetingRequest(OfficeHours officeHours, List<BookingDetails> bookingDetails) {
        MeetingRequest meetingRequest = new MeetingRequest();
        meetingRequest.setOfficeHours(officeHours);
        meetingRequest.setBookingDetailsList(bookingDetails);
        return meetingRequest;
    }

    private OfficeHours officeOpenAllDay() {
        OfficeHours officeHours = new OfficeHours();
        officeHours.setStart(LocalTime.of(0, 0));
        officeHours.setEnd(LocalTime.of(23, 59, 59));
        return officeHours;
    }

    private OfficeHours withOfficeHours(int startHr, int startMin, int endHr, int endMin) {
        return OfficeHoursBuilder.builder()
                .withStartTime(LocalTime.of(startHr, startMin))
                .withEndTime(LocalTime.of(endHr, endMin))
                .build();
    }

    private BookingDetails withBookingDetails(String empId, String requestDt, String startDt, int duration) {
        LocalDateTime rdt = format(requestDt, "yyyy-MM-dd HH:mm:ss");
        LocalDateTime sdt = format(startDt, "yyyy-MM-dd HH:mm");

        return BookingDetailsBuilder.builder()
                .withEmpId(empId)
                .withRequestDt(rdt)
                .withStartDt(sdt)
                .withEndDt(duration).build();
    }

    private LocalDateTime format(String dt, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dt, formatter);
    }




}