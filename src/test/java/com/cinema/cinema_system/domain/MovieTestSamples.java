package com.cinema.cinema_system.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MovieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Movie getMovieSample1() {
        return new Movie().id(1L).title("title1").genre("genre1").duration(1);
    }

    public static Movie getMovieSample2() {
        return new Movie().id(2L).title("title2").genre("genre2").duration(2);
    }

    public static Movie getMovieRandomSampleGenerator() {
        return new Movie()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .genre(UUID.randomUUID().toString())
            .duration(intCount.incrementAndGet());
    }
}
