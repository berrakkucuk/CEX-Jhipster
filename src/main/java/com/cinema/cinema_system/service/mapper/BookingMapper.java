package com.cinema.cinema_system.service.mapper;

import com.cinema.cinema_system.domain.Booking;
import com.cinema.cinema_system.domain.Customer;
import com.cinema.cinema_system.domain.Movie;
import com.cinema.cinema_system.service.dto.BookingDTO;
import com.cinema.cinema_system.service.dto.CustomerDTO;
import com.cinema.cinema_system.service.dto.MovieDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Booking} and its DTO {@link BookingDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookingMapper extends EntityMapper<BookingDTO, Booking> {
    @Mapping(target = "movie", source = "movie", qualifiedByName = "movieId")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    BookingDTO toDto(Booking s);

    @Named("movieId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MovieDTO toDtoMovieId(Movie movie);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
