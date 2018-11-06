package com.github.zgeeks.tx.service.statistics.bucket;

import org.junit.Before;
import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoField;

public class TimeTest {

    private Clock clock;

    @Before
    public void setup() {
        clock = Clock.systemUTC();
    }

    @Test
    public void test_time1() {
        long epochMilliSecond = clock.millis();
        LocalDateTime epochSecond = LocalDateTime.ofEpochSecond(Duration.ofMillis(epochMilliSecond).getSeconds(),
                0, ZoneOffset.UTC);
        int millis = Duration.ofMillis(epochMilliSecond).getNano() / 1_000_000;
        long time = epochSecond.get(ChronoField.SECOND_OF_MINUTE) * 1_000 + millis;
        System.out.println(time);
    }

    @Test
    public void test_time3() {
        LocalTime now = LocalTime.now(clock);
        int millis = now.getNano() / 1_000_000;
        long time = now.get(ChronoField.SECOND_OF_MINUTE) * 1_000 + millis;
        System.out.println(time);
    }
}
