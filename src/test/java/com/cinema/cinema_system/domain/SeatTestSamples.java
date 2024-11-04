package com.cinema.cinema_system.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SeatTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Seat getSeatSample1() {
        return new Seat().id(1L).seatNumber("seatNumber1").seatType("seatType1");
    }

    public static Seat getSeatSample2() {
        return new Seat().id(2L).seatNumber("seatNumber2").seatType("seatType2");
    }

    public static Seat getSeatRandomSampleGenerator() {
        return new Seat().id(longCount.incrementAndGet()).seatNumber(UUID.randomUUID().toString()).seatType(UUID.randomUUID().toString());
    }
}
