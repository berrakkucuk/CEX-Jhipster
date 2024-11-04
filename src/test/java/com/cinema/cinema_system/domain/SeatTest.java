package com.cinema.cinema_system.domain;

import static com.cinema.cinema_system.domain.BookingTestSamples.*;
import static com.cinema.cinema_system.domain.ScreenTestSamples.*;
import static com.cinema.cinema_system.domain.SeatTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cinema.cinema_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seat.class);
        Seat seat1 = getSeatSample1();
        Seat seat2 = new Seat();
        assertThat(seat1).isNotEqualTo(seat2);

        seat2.setId(seat1.getId());
        assertThat(seat1).isEqualTo(seat2);

        seat2 = getSeatSample2();
        assertThat(seat1).isNotEqualTo(seat2);
    }

    @Test
    void bookingTest() {
        Seat seat = getSeatRandomSampleGenerator();
        Booking bookingBack = getBookingRandomSampleGenerator();

        seat.setBooking(bookingBack);
        assertThat(seat.getBooking()).isEqualTo(bookingBack);

        seat.booking(null);
        assertThat(seat.getBooking()).isNull();
    }

    @Test
    void screenTest() {
        Seat seat = getSeatRandomSampleGenerator();
        Screen screenBack = getScreenRandomSampleGenerator();

        seat.setScreen(screenBack);
        assertThat(seat.getScreen()).isEqualTo(screenBack);

        seat.screen(null);
        assertThat(seat.getScreen()).isNull();
    }
}
