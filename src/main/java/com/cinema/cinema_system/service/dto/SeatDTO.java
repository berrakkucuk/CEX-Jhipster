package com.cinema.cinema_system.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cinema.cinema_system.domain.Seat} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SeatDTO implements Serializable {

    private Long id;

    private String seatNumber;

    private String seatType;

    private BookingDTO booking;

    private ScreenDTO screen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public BookingDTO getBooking() {
        return booking;
    }

    public void setBooking(BookingDTO booking) {
        this.booking = booking;
    }

    public ScreenDTO getScreen() {
        return screen;
    }

    public void setScreen(ScreenDTO screen) {
        this.screen = screen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeatDTO)) {
            return false;
        }

        SeatDTO seatDTO = (SeatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, seatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeatDTO{" +
            "id=" + getId() +
            ", seatNumber='" + getSeatNumber() + "'" +
            ", seatType='" + getSeatType() + "'" +
            ", booking=" + getBooking() +
            ", screen=" + getScreen() +
            "}";
    }
}
