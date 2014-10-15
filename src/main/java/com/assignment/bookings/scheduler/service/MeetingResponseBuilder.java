package com.assignment.bookings.scheduler.service;

import com.assignment.bookings.scheduler.IMeetingResponseBuilder;
import com.assignment.bookings.scheduler.model.BookingDetails;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;


public class MeetingResponseBuilder implements IMeetingResponseBuilder {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm";

    @Override
    public String build(Map<LocalDateTime, BookingDetails> map) {
        final LocalDateTime[] localDateTime = {null};
        final StringBuilder builder = new StringBuilder();
        map.values().forEach(entry -> {
            if (localDateTime[0] == null || !localDateTime[0].toLocalDate().isEqual(entry.getMeetingStartDt().toLocalDate())) {
                builder.append(format(entry.getMeetingStartDt(), DATE_FORMAT))
                        .append("\n");
                localDateTime[0] = entry.getMeetingStartDt();
            }

            builder.append(format(entry.getMeetingStartDt(), TIME_FORMAT)).append(" ")
                    .append(format(entry.getMeetingEndDt(), TIME_FORMAT)).append(" ")
                    .append(entry.getEmpId()).append("\n");
        });
        return builder.toString();
    }

    private String format(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = ofPattern(format);
        return localDateTime.format(formatter);
    }
}
