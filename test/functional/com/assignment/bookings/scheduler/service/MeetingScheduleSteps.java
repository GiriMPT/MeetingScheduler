package com.assignment.bookings.scheduler.service;


import com.assignment.bookings.scheduler.parsers.MeetingDateLineParser;
import com.assignment.bookings.scheduler.parsers.OfficeHoursParser;
import com.assignment.bookings.scheduler.parsers.RequestSubmissionParser;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.junit.Assert.assertThat;

public class MeetingScheduleSteps {

    private DefaultMeetingScheduler defaultMeetingScheduler;
    private OfficeHoursParser officeHoursParser;
    private RequestSubmissionParser requestSubmissionParser;
    private MeetingDateLineParser meetingDateLineParser;
    private MeetingRequestBuilder meetingRequestBuilder;
    private MeetingRequestFilter meetingRequestFilter;
    private MeetingResponseBuilder meetingResponseBuilder;
    private String scheduledMeetings;

    @Before
    public void before() {
        officeHoursParser = new OfficeHoursParser();
        meetingDateLineParser = new MeetingDateLineParser();
        requestSubmissionParser = new RequestSubmissionParser(meetingDateLineParser);

        meetingRequestBuilder = new MeetingRequestBuilder(officeHoursParser, requestSubmissionParser);
        meetingRequestFilter = new MeetingRequestFilter();
        meetingResponseBuilder = new MeetingResponseBuilder();
        defaultMeetingScheduler = new DefaultMeetingScheduler(meetingRequestBuilder, meetingRequestFilter, meetingResponseBuilder);
    }

    @Given("^the batch meeting request$")
    public void the_office_hours_is() throws Throwable {
    }

    @When("^the meeting requests are as below$")
    public void the_meeting_requests_are_as_below(String arg1) throws Throwable {
        scheduledMeetings = defaultMeetingScheduler.getMeetingsCalendar(arg1);
    }

    @Then("^the output of the request should be$")
    public void the_output_of_the_request_should_be(String arg1) throws Throwable {
        assertThat(arg1, equalToIgnoringWhiteSpace(scheduledMeetings));
    }
}
