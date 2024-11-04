package com.cinema.cinema_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "movie_time")
    private LocalDate movieTime;

    @Column(name = "payment_status")
    private String paymentStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "booking")
    @JsonIgnoreProperties(value = { "booking", "screen" }, allowSetters = true)
    private Set<Seat> seats = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "booking")
    @JsonIgnoreProperties(value = { "seats", "movie", "booking" }, allowSetters = true)
    private Set<Screen> screens = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "screens", "bookings" }, allowSetters = true)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bookings" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Booking id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMovieTime() {
        return this.movieTime;
    }

    public Booking movieTime(LocalDate movieTime) {
        this.setMovieTime(movieTime);
        return this;
    }

    public void setMovieTime(LocalDate movieTime) {
        this.movieTime = movieTime;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public Booking paymentStatus(String paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Set<Seat> getSeats() {
        return this.seats;
    }

    public void setSeats(Set<Seat> seats) {
        if (this.seats != null) {
            this.seats.forEach(i -> i.setBooking(null));
        }
        if (seats != null) {
            seats.forEach(i -> i.setBooking(this));
        }
        this.seats = seats;
    }

    public Booking seats(Set<Seat> seats) {
        this.setSeats(seats);
        return this;
    }

    public Booking addSeat(Seat seat) {
        this.seats.add(seat);
        seat.setBooking(this);
        return this;
    }

    public Booking removeSeat(Seat seat) {
        this.seats.remove(seat);
        seat.setBooking(null);
        return this;
    }

    public Set<Screen> getScreens() {
        return this.screens;
    }

    public void setScreens(Set<Screen> screens) {
        if (this.screens != null) {
            this.screens.forEach(i -> i.setBooking(null));
        }
        if (screens != null) {
            screens.forEach(i -> i.setBooking(this));
        }
        this.screens = screens;
    }

    public Booking screens(Set<Screen> screens) {
        this.setScreens(screens);
        return this;
    }

    public Booking addScreen(Screen screen) {
        this.screens.add(screen);
        screen.setBooking(this);
        return this;
    }

    public Booking removeScreen(Screen screen) {
        this.screens.remove(screen);
        screen.setBooking(null);
        return this;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Booking movie(Movie movie) {
        this.setMovie(movie);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Booking customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        return getId() != null && getId().equals(((Booking) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", movieTime='" + getMovieTime() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            "}";
    }
}
