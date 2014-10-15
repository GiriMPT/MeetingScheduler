package com.assignment.bookings.scheduler.service;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by girish on 06/10/14.
 */
//incomplete
@RunWith(Cucumber.class)
@Cucumber.Options(
        format = {"pretty", "html:target/cucumber"},
        features = {"test/resources"}
)

public class MeetingSchedulerCukesRunner {
}
