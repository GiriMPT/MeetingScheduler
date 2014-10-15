package com.assignment.bookings.scheduler.parsers;

import com.assignment.bookings.scheduler.exceptions.IllegalTextFormatException;
import com.assignment.bookings.scheduler.model.OfficeHours;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class OfficeHoursParserTest {

    private OfficeHoursParser officeHoursParser;

    @Before
    public void setUp() throws Exception {
       officeHoursParser = new OfficeHoursParser();
    }

    @Test
    public void shouldParseOfficeHours() throws Exception {
        OfficeHours officeHours = new OfficeHours();
        officeHours.setStart(LocalTime.of(9, 0));
        officeHours.setEnd(LocalTime.of(17, 30));
        assertEquals(officeHours, officeHoursParser.parse("0900 1730"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenEmpty() throws IllegalTextFormatException {
         officeHoursParser.parse(" ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNull() throws IllegalTextFormatException {
        officeHoursParser.parse(null);
    }

    @Test(expected = IllegalTextFormatException.class)
    public void shouldThrowExceptionWhenLineLengthIsNotNine() throws IllegalTextFormatException {
        officeHoursParser.parse("121212");
    }

}