package com.cinema.cinema_system.service.mapper;

import static com.cinema.cinema_system.domain.MovieAsserts.*;
import static com.cinema.cinema_system.domain.MovieTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieMapperTest {

    private MovieMapper movieMapper;

    @BeforeEach
    void setUp() {
        movieMapper = new MovieMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMovieSample1();
        var actual = movieMapper.toEntity(movieMapper.toDto(expected));
        assertMovieAllPropertiesEquals(expected, actual);
    }
}
