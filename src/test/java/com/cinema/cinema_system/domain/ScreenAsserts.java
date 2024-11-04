package com.cinema.cinema_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ScreenAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertScreenAllPropertiesEquals(Screen expected, Screen actual) {
        assertScreenAutoGeneratedPropertiesEquals(expected, actual);
        assertScreenAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertScreenAllUpdatablePropertiesEquals(Screen expected, Screen actual) {
        assertScreenUpdatableFieldsEquals(expected, actual);
        assertScreenUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertScreenAutoGeneratedPropertiesEquals(Screen expected, Screen actual) {
        assertThat(expected)
            .as("Verify Screen auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertScreenUpdatableFieldsEquals(Screen expected, Screen actual) {
        assertThat(expected)
            .as("Verify Screen relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getTotalSeats()).as("check totalSeats").isEqualTo(actual.getTotalSeats()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertScreenUpdatableRelationshipsEquals(Screen expected, Screen actual) {
        assertThat(expected)
            .as("Verify Screen relationships")
            .satisfies(e -> assertThat(e.getMovie()).as("check movie").isEqualTo(actual.getMovie()))
            .satisfies(e -> assertThat(e.getBooking()).as("check booking").isEqualTo(actual.getBooking()));
    }
}