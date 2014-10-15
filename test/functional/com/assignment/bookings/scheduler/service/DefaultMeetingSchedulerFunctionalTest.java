package com.assignment.bookings.scheduler.service;

import com.assignment.bookings.scheduler.exceptions.IllegalTextFormatException;
import com.assignment.bookings.scheduler.parsers.MeetingDateLineParser;
import com.assignment.bookings.scheduler.parsers.OfficeHoursParser;
import com.assignment.bookings.scheduler.parsers.RequestSubmissionParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.junit.Assert.*;

public class DefaultMeetingSchedulerFunctionalTest {

    private DefaultMeetingScheduler defaultMeetingScheduler;
    private OfficeHoursParser officeHoursParser;
    private RequestSubmissionParser requestSubmissionParser;
    private MeetingDateLineParser meetingDateLineParser;
    private MeetingRequestBuilder meetingRequestBuilder;
    private MeetingRequestFilter meetingRequestFilter;
    private MeetingResponseBuilder meetingResponseBuilder;

    @Before
    public void init() {
        officeHoursParser = new OfficeHoursParser();
        meetingDateLineParser = new MeetingDateLineParser();
        requestSubmissionParser = new RequestSubmissionParser(meetingDateLineParser);

        meetingRequestBuilder = new MeetingRequestBuilder(officeHoursParser, requestSubmissionParser);
        meetingRequestFilter = new MeetingRequestFilter();
        meetingResponseBuilder = new MeetingResponseBuilder();
        defaultMeetingScheduler = new DefaultMeetingScheduler(meetingRequestBuilder, meetingRequestFilter, meetingResponseBuilder);
    }

    @Test
    public void shouldGenerateOkMeetingsCalendar() throws IOException, URISyntaxException, IllegalTextFormatException {
        assertThat(getExpectedOutput(), equalToIgnoringWhiteSpace(defaultMeetingScheduler.getMeetingsCalendar(getInput())));
    }

    @Test
    public void shouldGenerateEmptyMeetingsCalendar() throws IllegalTextFormatException {
        assertTrue(defaultMeetingScheduler.getMeetingsCalendar("0900 1730").isEmpty());
    }

    @Test
    public void meetingsShouldNotOverlap() throws IllegalArgumentException,
            IllegalTextFormatException {

        String firstMeeting = "2011-03-13 16:00:00 EMP01\n"
                + "2011-03-22 16:30 1\n";

        String secondMeeting = "2011-03-14 15:00:00 EMP01\n"
                + "2011-03-22 15:30 2";

        String input = "0900 1730\n" + firstMeeting + secondMeeting;
        assertEquals("2011-03-22\n" + "16:30 17:30 EMP01\n", defaultMeetingScheduler.getMeetingsCalendar(input));
    }


    @Test
    public void requestsShouldBeProcessedInSubmitOrder() throws IllegalTextFormatException {
        String firstMeeting = "2011-03-13 16:00:00 EMP01\n"
                + "2011-03-22 16:30 1\n";

        String secondMeeting = "2011-03-14 15:00:00 EMP01\n"
                + "2011-03-22 15:30 2";

        String input = "0900 1730\n" + firstMeeting + secondMeeting;
        assertEquals("2011-03-22\n" + "16:30 17:30 EMP01\n", defaultMeetingScheduler.getMeetingsCalendar(input));
    }

    @Test
    public void meetingsShouldBeWithinOfficeHours() throws IllegalTextFormatException {
        String meetingStartsBeforeOfcHours = "2011-03-13 16:00:00 EMP01\n"
                + "2011-03-22 08:30 1\n";

        String meetingEndsAfterOfcHours = "2011-03-13 15:00:00 EMP01\n"
                + "2011-03-23 16:30 3\n";

        String correctMeeting = "2011-03-13 16:00:00 EMP01\n"
                + "2011-03-22 16:30 1\n";

        String input = "0900 1730\n" + meetingStartsBeforeOfcHours + meetingEndsAfterOfcHours + correctMeeting;
        assertEquals("2011-03-22\n" + "16:30 17:30 EMP01\n", defaultMeetingScheduler.getMeetingsCalendar(input));
    }

    @Test
    public void meetingShouldBeOrderedInChronologicalOrder() throws IllegalTextFormatException {
        String firstMeeting = "2011-03-13 16:00:00 EMP01\n"
                + "2011-03-22 16:30 1\n";

        String thirdMeeting = "2011-03-09 10:00:00 EMP01\n"
                + "2011-03-22 10:30 1\n";

        String secondMeeting = "2011-03-11 16:00:00 EMP01\n"
                + "2011-03-22 13:30 1\n";

        String fourthMeeting = "2011-03-08 10:00:00 EMP01\n"
                + "2011-03-23 10:30 1\n";

        String fifthMeeting = "2011-03-08 10:00:00 EMP01\n"
                + "2011-03-21 10:30 1\n";

        String input = "0900 1730\n" + firstMeeting + secondMeeting + thirdMeeting + fourthMeeting + fifthMeeting;

        String expected = "2011-03-21\n" + "10:30 11:30 EMP01\n"
                +  "2011-03-22\n" + "10:30 11:30 EMP01\n" + "13:30 14:30 EMP01\n" +"16:30 17:30 EMP01\n"
                + "2011-03-23\n" + "10:30 11:30 EMP01\n";

        assertEquals(expected, defaultMeetingScheduler.getMeetingsCalendar(input));
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionWhenInputIsNull() throws IllegalArgumentException, IllegalTextFormatException {
        defaultMeetingScheduler.getMeetingsCalendar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionWhenInputIsEmpty() throws IllegalArgumentException, IllegalTextFormatException {
        defaultMeetingScheduler.getMeetingsCalendar(" ");
    }

    @Test(expected = IllegalTextFormatException.class)
    public void exceptionWhenOfficeHoursIsInvalid() throws IllegalTextFormatException {
        defaultMeetingScheduler.getMeetingsCalendar("0900 1730 txtxtxt");
    }

    private String getExpectedOutput() {
        return "2011-03-21\n" +
                "09:00 11:00 EMP002\n" +
                "2011-03-22\n" +
                "14:00 16:00 EMP003\n" +
                "16:00 17:00 EMP004";
    }

    private String getInput() {
        return "0900 1730\n\n" +
                "2011-03-17 10:17:06 EMP001\n\n" +
                "2011-03-21 09:00 2\n\n" +
                "2011-03-16 12:34:56 EMP002\n\n" +
                "2011-03-21 09:00 2\n\n" +
                "2011-03-16 09:28:23 EMP003\n\n" +
                "2011-03-22 14:00 2\n\n" +
                "2011-03-17 11:23:45 EMP004\n\n" +
                "2011-03-22 16:00 1\n\n" +
                "2011-03-15 17:29:12 EMP005\n\n" +
                "2011-03-21 16:00 3\n";
    }

}
