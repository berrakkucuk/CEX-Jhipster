package com.cinema.cinema_system.service.mapper;

import com.cinema.cinema_system.domain.Booking;
import com.cinema.cinema_system.domain.Movie;
import com.cinema.cinema_system.domain.Screen;
import com.cinema.cinema_system.service.dto.BookingDTO;
import com.cinema.cinema_system.service.dto.MovieDTO;
import com.cinema.cinema_system.service.dto.ScreenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Screen} and its DTO {@link ScreenDTO}.
 */
@Mapper(componentModel = "spring")
public interface ScreenMapper extends EntityMapper<ScreenDTO, Screen> {
    @Mapping(target = "movie", source = "movie", qualifiedByName = "movieId")
    @Mapping(target = "booking", source = "booking", qualifiedByName = "bookingId")
    ScreenDTO toDto(Screen s);

    @Named("movieId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MovieDTO toDtoMovieId(Movie movie);

    @Named("bookingId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BookingDTO toDtoBookingId(Booking booking);
}
