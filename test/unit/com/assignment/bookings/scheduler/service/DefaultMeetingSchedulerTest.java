package com.assignment.bookings.scheduler.service;

import com.assignment.bookings.scheduler.exceptions.IllegalTextFormatException;
import com.assignment.bookings.scheduler.model.BookingDetails;
import com.assignment.bookings.scheduler.model.MeetingRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class DefaultMeetingSchedulerTest {

    @Mock
    private MeetingRequestBuilder meetingRequestBuilder;

    @Mock
    private MeetingRequestFilter meetingRequestFilter;

    @Mock
    private MeetingResponseBuilder meetingResponseBuilder;

    private DefaultMeetingScheduler meetingScheduler;

    @Before
    public void setUp() throws Exception {
        meetingScheduler = new DefaultMeetingScheduler(meetingRequestBuilder, meetingRequestFilter, meetingResponseBuilder);
    }

    @Test
    public void getMeetingsCalendar() throws Exception {
        meetingScheduler.getMeetingsCalendar("someString");

        InOrder inOrder = inOrder(meetingRequestBuilder, meetingRequestFilter, meetingResponseBuilder);
        inOrder.verify(meetingRequestBuilder).parse(any(String.class));
        inOrder.verify(meetingRequestFilter).filter(any(MeetingRequest.class));
        inOrder.verify(meetingResponseBuilder).build(Matchers.<Map<LocalDateTime, BookingDetails>>any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenInputIsNull() throws IllegalTextFormatException {
        meetingScheduler.getMeetingsCalendar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenInputIsEmpty() throws IllegalTextFormatException {
        meetingScheduler.getMeetingsCalendar(" ");
    }
}