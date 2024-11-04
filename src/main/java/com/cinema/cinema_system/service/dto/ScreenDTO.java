package com.cinema.cinema_system.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cinema.cinema_system.domain.Screen} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScreenDTO implements Serializable {

    private Long id;

    private String name;

    private Integer totalSeats;

    private MovieDTO movie;

    private BookingDTO booking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }

    public BookingDTO getBooking() {
        return booking;
    }

    public void setBooking(BookingDTO booking) {
        this.booking = booking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScreenDTO)) {
            return false;
        }

        ScreenDTO screenDTO = (ScreenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, screenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScreenDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", totalSeats=" + getTotalSeats() +
            ", movie=" + getMovie() +
            ", booking=" + getBooking() +
            "}";
    }
}
