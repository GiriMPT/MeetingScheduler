package com.assignment.bookings.scheduler.model;


import java.time.LocalDateTime;

public class BookingDetailsBuilder {

    private BookingDetails bookingDetails = new BookingDetails();

    public static BookingDetailsBuilder builder() {
        return new BookingDetailsBuilder();
    }

    public BookingDetailsBuilder withEmpId(String empId) {
        bookingDetails.setEmpId(empId);
        return this;
    }

    public BookingDetailsBuilder withRequestDt(LocalDateTime localDateTime) {
        bookingDetails.setRequestedDt(localDateTime);
        return this;
    }

    public BookingDetailsBuilder withStartDt(LocalDateTime localDateTime) {
        bookingDetails.setMeetingStartDt(localDateTime);
        return this;
    }

    public BookingDetailsBuilder withEndDt(int duration) {
        bookingDetails.setMeetingEndDt(bookingDetails.getMeetingStartDt().plusHours(duration));
        return this;
    }

    public BookingDetails build() {
        return bookingDetails;
    }


}
