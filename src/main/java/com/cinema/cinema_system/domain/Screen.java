package com.cinema.cinema_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Screen.
 */
@Entity
@Table(name = "screen")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Screen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "total_seats")
    private Integer totalSeats;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "screen")
    @JsonIgnoreProperties(value = { "booking", "screen" }, allowSetters = true)
    private Set<Seat> seats = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "screens", "bookings" }, allowSetters = true)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "seats", "screens", "movie", "customer" }, allowSetters = true)
    private Booking booking;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Screen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Screen name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalSeats() {
        return this.totalSeats;
    }

    public Screen totalSeats(Integer totalSeats) {
        this.setTotalSeats(totalSeats);
        return this;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Set<Seat> getSeats() {
        return this.seats;
    }

    public void setSeats(Set<Seat> seats) {
        if (this.seats != null) {
            this.seats.forEach(i -> i.setScreen(null));
        }
        if (seats != null) {
            seats.forEach(i -> i.setScreen(this));
        }
        this.seats = seats;
    }

    public Screen seats(Set<Seat> seats) {
        this.setSeats(seats);
        return this;
    }

    public Screen addSeat(Seat seat) {
        this.seats.add(seat);
        seat.setScreen(this);
        return this;
    }

    public Screen removeSeat(Seat seat) {
        this.seats.remove(seat);
        seat.setScreen(null);
        return this;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Screen movie(Movie movie) {
        this.setMovie(movie);
        return this;
    }

    public Booking getBooking() {
        return this.booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Screen booking(Booking booking) {
        this.setBooking(booking);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Screen)) {
            return false;
        }
        return getId() != null && getId().equals(((Screen) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Screen{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", totalSeats=" + getTotalSeats() +
            "}";
    }
}
