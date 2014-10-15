package com.assignment.bookings.scheduler.model;

import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OfficeHoursTest {

    @Test
    public void officeOpenAllDay() throws Exception {
        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(23, 59, 59);

        assertTrue(OfficeHoursBuilder.builder().withEndTime(endTime).withStartTime(startTime).build().isOpenAllDay());
    }

    @Test
    public void officeNotOpenAllDay() throws Exception {
        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        assertFalse(OfficeHoursBuilder.builder().withEndTime(endTime).withStartTime(startTime).build().isOpenAllDay());
    }
}