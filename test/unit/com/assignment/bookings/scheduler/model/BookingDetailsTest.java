
package com.assignment.bookings.scheduler.model;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BookingDetailsTest {

    @Test
    public void shouldReturnTrueWhenBookingDetailsOverLap() {
        BookingDetails bookingDetails1 = withBookingDetails("2011-03-21 09:00",2);
        BookingDetails bookingDetails2 = withBookingDetails("2011-03-21 09:00",2);

        assertTrue(bookingDetails1.overlapsWith(bookingDetails2));

        BookingDetails bookingDetails3 = withBookingDetails("2011-03-21 10:00",2);
        BookingDetails bookingDetails4 = withBookingDetails("2011-03-21 09:00",3);

        assertTrue(bookingDetails3.overlapsWith(bookingDetails4));

        BookingDetails bookingDetails5 = withBookingDetails("2011-03-21 10:00",2);
        BookingDetails bookingDetails6 = withBookingDetails("2011-03-21 09:00",2);

        assertTrue(bookingDetails5.overlapsWith(bookingDetails6));

        BookingDetails bookingDetails7 = withBookingDetails("2011-03-21 10:00",12);
        BookingDetails bookingDetails8 = withBookingDetails("2011-03-21 09:15",2);

        assertTrue(bookingDetails7.overlapsWith(bookingDetails8));
    }

    @Test
    public void shouldReturnFalseWhenBookingDetailsDoesNotOverLap() {
        BookingDetails bookingDetails1 = withBookingDetails("2012-03-21 09:00",2);
        BookingDetails bookingDetails2 = withBookingDetails("2011-03-21 09:00",2);

        assertFalse(bookingDetails1.overlapsWith(bookingDetails2));

        BookingDetails bookingDetails3 = withBookingDetails("2011-03-21 08:00",2);
        BookingDetails bookingDetails4 = withBookingDetails("2011-03-21 11:00",2);

        assertFalse(bookingDetails3.overlapsWith(bookingDetails4));

        BookingDetails bookingDetails9 = withBookingDetails("2011-03-21 10:00",1);
        BookingDetails bookingDetails10 = withBookingDetails("2011-03-21 11:00",15);

        assertFalse(bookingDetails9.overlapsWith(bookingDetails10));

        BookingDetails bookingDetails6 = withBookingDetails("2011-03-21 09:00",1);
        BookingDetails bookingDetails7 = withBookingDetails("2011-03-21 10:00",1);

        assertFalse(bookingDetails6.overlapsWith(bookingDetails7));

        BookingDetails bookingDetails11 = withBookingDetails("2011-03-21 10:00",1);
        BookingDetails bookingDetails12 = withBookingDetails("2011-03-21 09:00",1);

        assertFalse(bookingDetails11.overlapsWith(bookingDetails12));
    }

    private BookingDetails withBookingDetails(String startDt, int duration) {
        LocalDateTime sdt = format(startDt, "yyyy-MM-dd HH:mm");

        return BookingDetailsBuilder.builder()
                .withEmpId("emp01")
                .withRequestDt(null)
                .withStartDt(sdt)
                .withEndDt(duration).build();
    }

    private LocalDateTime format(String dt, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dt, formatter);
    }


}