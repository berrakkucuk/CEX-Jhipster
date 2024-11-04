package com.cinema.cinema_system.service.mapper;

import com.cinema.cinema_system.domain.Movie;
import com.cinema.cinema_system.service.dto.MovieDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Movie} and its DTO {@link MovieDTO}.
 */
@Mapper(componentModel = "spring")
public interface MovieMapper extends EntityMapper<MovieDTO, Movie> {}
