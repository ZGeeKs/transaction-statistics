package com.github.zgeeks.tx.util;

import java.time.OffsetTime;

import static java.time.Instant.ofEpochMilli;
import static java.time.OffsetTime.ofInstant;
import static java.time.ZoneOffset.UTC;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;

public final class TimeUtils {

    private static final int MILLIS_UNIT = 1_000_000;

    public static int milliSecondOfMinute(long epochMilliSecond) {
        OffsetTime epochTime = ofInstant(ofEpochMilli(epochMilliSecond), UTC);
        int millis = epochTime.getNano() / MILLIS_UNIT;
        return epochTime.get(SECOND_OF_MINUTE) * 1_000 + millis;
    }

    private TimeUtils() {}
}
