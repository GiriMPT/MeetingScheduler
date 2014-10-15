package com.assignment.bookings.scheduler.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

public class BookingDetails {

    private LocalDateTime requestedDt;
    private LocalDateTime meetingStartDt;
    private LocalDateTime meetingEndDt;
    private String empId;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public LocalDateTime getMeetingEndDt() {
        return meetingEndDt;
    }

    public void setMeetingEndDt(LocalDateTime meetingEndDt) {
        this.meetingEndDt = meetingEndDt;
    }

    public LocalDateTime getMeetingStartDt() {
        return meetingStartDt;
    }

    public void setMeetingStartDt(LocalDateTime meetingStartDt) {
        this.meetingStartDt = meetingStartDt;
    }

    public LocalDateTime getRequestedDt() {
        return requestedDt;
    }

    public void setRequestedDt(LocalDateTime requestedDt) {
        this.requestedDt = requestedDt;
    }


    private boolean inBetween(LocalDateTime value) {
        return (value.isAfter(meetingStartDt))
                && (value.isBefore(meetingEndDt) ) ;
    }

    public boolean overlapsWith(BookingDetails that) {
        return isSame(that) || (that.inBetween(this.meetingStartDt) || that.inBetween(this.meetingEndDt));
    }

    private boolean isSame(BookingDetails that) {
        return this.meetingStartDt.isEqual(that.meetingStartDt)
                    &&
                this.meetingEndDt.isEqual(that.meetingEndDt);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }



}
