package com.cinema.cinema_system.domain;

import static com.cinema.cinema_system.domain.BookingTestSamples.*;
import static com.cinema.cinema_system.domain.MovieTestSamples.*;
import static com.cinema.cinema_system.domain.ScreenTestSamples.*;
import static com.cinema.cinema_system.domain.SeatTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cinema.cinema_system.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ScreenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Screen.class);
        Screen screen1 = getScreenSample1();
        Screen screen2 = new Screen();
        assertThat(screen1).isNotEqualTo(screen2);

        screen2.setId(screen1.getId());
        assertThat(screen1).isEqualTo(screen2);

        screen2 = getScreenSample2();
        assertThat(screen1).isNotEqualTo(screen2);
    }

    @Test
    void seatTest() {
        Screen screen = getScreenRandomSampleGenerator();
        Seat seatBack = getSeatRandomSampleGenerator();

        screen.addSeat(seatBack);
        assertThat(screen.getSeats()).containsOnly(seatBack);
        assertThat(seatBack.getScreen()).isEqualTo(screen);

        screen.removeSeat(seatBack);
        assertThat(screen.getSeats()).doesNotContain(seatBack);
        assertThat(seatBack.getScreen()).isNull();

        screen.seats(new HashSet<>(Set.of(seatBack)));
        assertThat(screen.getSeats()).containsOnly(seatBack);
        assertThat(seatBack.getScreen()).isEqualTo(screen);

        screen.setSeats(new HashSet<>());
        assertThat(screen.getSeats()).doesNotContain(seatBack);
        assertThat(seatBack.getScreen()).isNull();
    }

    @Test
    void movieTest() {
        Screen screen = getScreenRandomSampleGenerator();
        Movie movieBack = getMovieRandomSampleGenerator();

        screen.setMovie(movieBack);
        assertThat(screen.getMovie()).isEqualTo(movieBack);

        screen.movie(null);
        assertThat(screen.getMovie()).isNull();
    }

    @Test
    void bookingTest() {
        Screen screen = getScreenRandomSampleGenerator();
        Booking bookingBack = getBookingRandomSampleGenerator();

        screen.setBooking(bookingBack);
        assertThat(screen.getBooking()).isEqualTo(bookingBack);

        screen.booking(null);
        assertThat(screen.getBooking()).isNull();
    }
}
