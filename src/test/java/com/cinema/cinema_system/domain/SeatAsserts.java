package com.cinema.cinema_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SeatAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSeatAllPropertiesEquals(Seat expected, Seat actual) {
        assertSeatAutoGeneratedPropertiesEquals(expected, actual);
        assertSeatAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSeatAllUpdatablePropertiesEquals(Seat expected, Seat actual) {
        assertSeatUpdatableFieldsEquals(expected, actual);
        assertSeatUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSeatAutoGeneratedPropertiesEquals(Seat expected, Seat actual) {
        assertThat(expected)
            .as("Verify Seat auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSeatUpdatableFieldsEquals(Seat expected, Seat actual) {
        assertThat(expected)
            .as("Verify Seat relevant properties")
            .satisfies(e -> assertThat(e.getSeatNumber()).as("check seatNumber").isEqualTo(actual.getSeatNumber()))
            .satisfies(e -> assertThat(e.getSeatType()).as("check seatType").isEqualTo(actual.getSeatType()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSeatUpdatableRelationshipsEquals(Seat expected, Seat actual) {
        assertThat(expected)
            .as("Verify Seat relationships")
            .satisfies(e -> assertThat(e.getBooking()).as("check booking").isEqualTo(actual.getBooking()))
            .satisfies(e -> assertThat(e.getScreen()).as("check screen").isEqualTo(actual.getScreen()));
    }
}
