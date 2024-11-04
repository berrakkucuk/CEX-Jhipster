package com.cinema.cinema_system.service.mapper;

import com.cinema.cinema_system.domain.Booking;
import com.cinema.cinema_system.domain.Screen;
import com.cinema.cinema_system.domain.Seat;
import com.cinema.cinema_system.service.dto.BookingDTO;
import com.cinema.cinema_system.service.dto.ScreenDTO;
import com.cinema.cinema_system.service.dto.SeatDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Seat} and its DTO {@link SeatDTO}.
 */
@Mapper(componentModel = "spring")
public interface SeatMapper extends EntityMapper<SeatDTO, Seat> {
    @Mapping(target = "booking", source = "booking", qualifiedByName = "bookingId")
    @Mapping(target = "screen", source = "screen", qualifiedByName = "screenId")
    SeatDTO toDto(Seat s);

    @Named("bookingId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BookingDTO toDtoBookingId(Booking booking);

    @Named("screenId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ScreenDTO toDtoScreenId(Screen screen);
}
