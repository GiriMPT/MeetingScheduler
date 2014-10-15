package com.assignment.bookings.scheduler.service;

import com.assignment.bookings.scheduler.IMeetingRequestFilter;
import com.assignment.bookings.scheduler.model.BookingDetails;
import com.assignment.bookings.scheduler.model.MeetingRequest;
import com.assignment.bookings.scheduler.model.OfficeHours;
import com.google.common.collect.Maps;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class MeetingRequestFilter implements IMeetingRequestFilter {

    @Override
    public Map<LocalDateTime, BookingDetails> filter(MeetingRequest meetingRequest) {
        final OfficeHours officeHours = meetingRequest.getOfficeHours();
        final Map<LocalDateTime, BookingDetails> map = Maps.newTreeMap();

        meetingRequest.getBookingDetailsList().forEach(entry -> {
            LocalDateTime localDateTime = overlappedElementKey(entry, map);
            if (localDateTime == null) { // there is no overlap
                if (officeHours.isOpenAllDay() || isMeetingWithInOfficeHours(officeHours, entry)) {
                    map.put(entry.getMeetingStartDt(), entry);
                }
            } else { // overlap, then use the oldest submission
                if (entry.getRequestedDt().isBefore(map.get(localDateTime).getRequestedDt())) {
                    map.remove(localDateTime);
                    map.put(entry.getMeetingStartDt(), entry);
                }
            }
        });
        return map;
    }

    private LocalDateTime overlappedElementKey(BookingDetails bookingDetails, Map<LocalDateTime, BookingDetails> map) {
        final LocalDateTime[] overlappedStartDt = new LocalDateTime[1];
        map.forEach((k, v) -> {
            if (v.overlapsWith(bookingDetails)) {
                overlappedStartDt[0] = k;
            }
        });

        return overlappedStartDt[0];
    }

    private boolean isMeetingWithInOfficeHours(OfficeHours officeHours, BookingDetails bookingDetails) {
        return isAfter(bookingDetails.getMeetingStartDt().toLocalTime(), officeHours.getStart())
                && isBefore(bookingDetails.getMeetingEndDt().toLocalTime(), officeHours.getEnd())
                && meetingInSeconds(bookingDetails) <= officeHoursInSeconds(officeHours);
    }

    private boolean isAfter(LocalTime bookingStartTime, LocalTime officeStartTime) {
        return bookingStartTime.equals(officeStartTime) || bookingStartTime.isAfter(officeStartTime);
    }

    private boolean isBefore(LocalTime bookingEndTime, LocalTime officeEndTime) {
        return bookingEndTime.equals(officeEndTime) || bookingEndTime.isBefore(officeEndTime);
    }

    private long officeHoursInSeconds(OfficeHours officeHours) {
        return Duration.between(officeHours.getStart(), officeHours.getEnd()).getSeconds();
    }

    private long meetingInSeconds(BookingDetails bookingDetails) {
        return Duration.between(bookingDetails.getMeetingStartDt(), bookingDetails.getMeetingEndDt()).getSeconds();
    }
}
