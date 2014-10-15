package com.assignment.bookings.scheduler.service;

import com.assignment.bookings.scheduler.exceptions.IllegalTextFormatException;
import com.assignment.bookings.scheduler.model.BookingDetails;
import com.assignment.bookings.scheduler.model.BookingDetailsBuilder;
import com.assignment.bookings.scheduler.model.MeetingRequest;
import com.assignment.bookings.scheduler.model.OfficeHours;
import com.assignment.bookings.scheduler.parsers.OfficeHoursParser;
import com.assignment.bookings.scheduler.parsers.RequestSubmissionParser;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.assignment.bookings.scheduler.model.OfficeHoursBuilder.builder;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class MeetingRequestBuilderTest {

    private MeetingRequestBuilder meetingRequestBuilder;

    @Mock
    private OfficeHoursParser officeHoursParser;

    @Mock
    private RequestSubmissionParser requestSubmissionParser;


    @Before
    public void setUp() throws Exception {
        meetingRequestBuilder = new MeetingRequestBuilder(officeHoursParser, requestSubmissionParser);

    }

    @Test
    public void shouldParseInput() throws Exception {
        String input = "0900 1730\n" +
                "2011-03-17 10:17:06 EMP001\n" +
                "2011-03-21 09:00 2\n" +
                "2011-03-16 12:34:56 EMP002\n" +
                "2011-03-21 09:00 2";

        MeetingRequest expectedMeetingRequest = new MeetingRequest();
        OfficeHours expectedOfficeHours = withOfficeHours(9, 0, 17, 30);
        List<BookingDetails> bookingDetailsList = Lists.newArrayList(
                withBookingDetails("EMP01", "2011-03-17 10:17:06", "2011-03-21 09:00", 2),
                withBookingDetails("EMP02", "2011-03-16 12:34:56", "2011-03-21 09:00", 3)
        );
        expectedMeetingRequest.setOfficeHours(expectedOfficeHours);
        expectedMeetingRequest.setBookingDetailsList(bookingDetailsList);


        given(officeHoursParser.parse(anyString())).
                willReturn(withOfficeHours(9, 0, 17, 30));

        given(requestSubmissionParser.parse(anyString(), anyString()))
                .willReturn(withBookingDetails("EMP01", "2011-03-17 10:17:06", "2011-03-21 09:00", 2))
                .willReturn(withBookingDetails("EMP02", "2011-03-16 12:34:56", "2011-03-21 09:00", 3));

        assertEquals(expectedMeetingRequest, meetingRequestBuilder.parse(input));

    }

    @Test(expected = IllegalTextFormatException.class)
    public void exceptionWhenMessageFormatIsIncorrect() throws URISyntaxException, IOException, IllegalTextFormatException {
        String input = "0900 1730\n" +
                "2011-03-17 10:17:06 EMP001";
        given(officeHoursParser.parse(anyString())).
                willReturn(withOfficeHours(9, 0, 17, 30));
        meetingRequestBuilder.parse(input);
    }



    private OfficeHours withOfficeHours(int startHr, int startMin, int endHr, int endMin) {
        return builder()
                        .withStartTime(LocalTime.of(startHr, startMin))
                        .withEndTime(LocalTime.of(endHr, endMin))
                        .build();
    }

    private BookingDetails withBookingDetails(String empId, String requestDt, String startDt, int duration) {
        LocalDateTime rdt = format(requestDt, "yyyy-MM-dd HH:mm:ss");
        LocalDateTime sdt = format(startDt, "yyyy-MM-dd HH:mm");

        return BookingDetailsBuilder.builder()
                                .withEmpId(empId)
                                .withRequestDt(rdt)
                                .withStartDt(sdt)
                                .withEndDt(duration).build();
    }

    private LocalDateTime format(String dt, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dt, formatter);
    }
}