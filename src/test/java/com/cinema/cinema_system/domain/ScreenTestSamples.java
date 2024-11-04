package com.cinema.cinema_system.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ScreenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Screen getScreenSample1() {
        return new Screen().id(1L).name("name1").totalSeats(1);
    }

    public static Screen getScreenSample2() {
        return new Screen().id(2L).name("name2").totalSeats(2);
    }

    public static Screen getScreenRandomSampleGenerator() {
        return new Screen().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).totalSeats(intCount.incrementAndGet());
    }
}
