package com.cinema.cinema_system.domain;

import static com.cinema.cinema_system.domain.BookingTestSamples.*;
import static com.cinema.cinema_system.domain.CustomerTestSamples.*;
import static com.cinema.cinema_system.domain.MovieTestSamples.*;
import static com.cinema.cinema_system.domain.ScreenTestSamples.*;
import static com.cinema.cinema_system.domain.SeatTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cinema.cinema_system.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BookingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Booking.class);
        Booking booking1 = getBookingSample1();
        Booking booking2 = new Booking();
        assertThat(booking1).isNotEqualTo(booking2);

        booking2.setId(booking1.getId());
        assertThat(booking1).isEqualTo(booking2);

        booking2 = getBookingSample2();
        assertThat(booking1).isNotEqualTo(booking2);
    }

    @Test
    void seatTest() {
        Booking booking = getBookingRandomSampleGenerator();
        Seat seatBack = getSeatRandomSampleGenerator();

        booking.addSeat(seatBack);
        assertThat(booking.getSeats()).containsOnly(seatBack);
        assertThat(seatBack.getBooking()).isEqualTo(booking);

        booking.removeSeat(seatBack);
        assertThat(booking.getSeats()).doesNotContain(seatBack);
        assertThat(seatBack.getBooking()).isNull();

        booking.seats(new HashSet<>(Set.of(seatBack)));
        assertThat(booking.getSeats()).containsOnly(seatBack);
        assertThat(seatBack.getBooking()).isEqualTo(booking);

        booking.setSeats(new HashSet<>());
        assertThat(booking.getSeats()).doesNotContain(seatBack);
        assertThat(seatBack.getBooking()).isNull();
    }

    @Test
    void screenTest() {
        Booking booking = getBookingRandomSampleGenerator();
        Screen screenBack = getScreenRandomSampleGenerator();

        booking.addScreen(screenBack);
        assertThat(booking.getScreens()).containsOnly(screenBack);
        assertThat(screenBack.getBooking()).isEqualTo(booking);

        booking.removeScreen(screenBack);
        assertThat(booking.getScreens()).doesNotContain(screenBack);
        assertThat(screenBack.getBooking()).isNull();

        booking.screens(new HashSet<>(Set.of(screenBack)));
        assertThat(booking.getScreens()).containsOnly(screenBack);
        assertThat(screenBack.getBooking()).isEqualTo(booking);

        booking.setScreens(new HashSet<>());
        assertThat(booking.getScreens()).doesNotContain(screenBack);
        assertThat(screenBack.getBooking()).isNull();
    }

    @Test
    void movieTest() {
        Booking booking = getBookingRandomSampleGenerator();
        Movie movieBack = getMovieRandomSampleGenerator();

        booking.setMovie(movieBack);
        assertThat(booking.getMovie()).isEqualTo(movieBack);

        booking.movie(null);
        assertThat(booking.getMovie()).isNull();
    }

    @Test
    void customerTest() {
        Booking booking = getBookingRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        booking.setCustomer(customerBack);
        assertThat(booking.getCustomer()).isEqualTo(customerBack);

        booking.customer(null);
        assertThat(booking.getCustomer()).isNull();
    }
}
