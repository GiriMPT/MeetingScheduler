package com.assignment.bookings.scheduler.service;

import com.assignment.bookings.scheduler.model.BookingDetails;
import com.assignment.bookings.scheduler.model.BookingDetailsBuilder;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.junit.Assert.assertThat;

public class MeetingResponseBuilderTest {
    private MeetingResponseBuilder meetingResponseBuilder;

    @Before
    public void init() {
        this.meetingResponseBuilder = new MeetingResponseBuilder();
    }

    @Test
    public void buildMeetingResponse() throws Exception {
        BookingDetails bookingDetails1 = withBookingDetails("EMP01", "2011-03-21 09:00:00", "2011-03-21 09:00", 1);
        BookingDetails bookingDetails2 = withBookingDetails("EMP02", "2011-03-21 16:00:00", "2011-03-21 16:00", 1);
        BookingDetails bookingDetails3 = withBookingDetails("EMP03", "2011-03-21 11:00:00", "2011-03-21 11:00", 1);
        BookingDetails bookingDetails4 = withBookingDetails("EMP04", "2011-10-21 12:00:00", "2011-10-21 12:00", 2);
        BookingDetails bookingDetails5 = withBookingDetails("EMP05", "2011-02-21 12:00:00", "2011-02-21 12:00", 2);

        StringBuilder builder = new StringBuilder();
        builder.append("2011-02-21\n")
                .append("12:00 14:00 EMP05\n")
                .append("2011-03-21\n")
                .append("09:00 10:00 EMP01\n")
                .append("11:00 12:00 EMP03\n")
                .append("16:00 17:00 EMP02\n")
                .append("2011-10-21\n")
                .append("12:00 14:00 EMP04");

        Map<LocalDateTime, BookingDetails> maps = Maps.newTreeMap();
        maps.put(LocalDateTime.of(getLocalDate(2011, 3, 21), getLocalTime(9, 0, 0)), bookingDetails1);
        maps.put(LocalDateTime.of(getLocalDate(2011, 3, 21), getLocalTime(16, 0, 0)), bookingDetails2);
        maps.put(LocalDateTime.of(getLocalDate(2011, 3, 21), getLocalTime(11, 0, 0)), bookingDetails3);
        maps.put(LocalDateTime.of(getLocalDate(2011, 10, 21), getLocalTime(12, 0, 0)), bookingDetails4);
        maps.put(LocalDateTime.of(getLocalDate(2011, 2, 21), getLocalTime(12, 0, 0)), bookingDetails5);

        assertThat(builder.toString(), equalToIgnoringWhiteSpace(meetingResponseBuilder.build(maps)));
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

    private LocalDate getLocalDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    private LocalTime getLocalTime(int hour, int minute, int sec) {
        return LocalTime.of(hour, minute, sec);
    }

}