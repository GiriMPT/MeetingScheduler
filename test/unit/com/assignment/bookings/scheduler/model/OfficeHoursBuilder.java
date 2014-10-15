package com.assignment.bookings.scheduler.model;


import java.time.LocalTime;

public class OfficeHoursBuilder {

    private OfficeHours officeHours = new OfficeHours();

    public static OfficeHoursBuilder builder() {
        return new OfficeHoursBuilder();
    }

    public OfficeHoursBuilder withStartTime(LocalTime localTime) {
        officeHours.setStart(localTime);
        return this;
    }

    public OfficeHoursBuilder withEndTime(LocalTime localTime) {
        officeHours.setEnd(localTime);
        return this;
    }

    public OfficeHours build() {
        return officeHours;
    }
}
