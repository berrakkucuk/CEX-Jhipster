package com.cinema.cinema_system.service.mapper;

import static com.cinema.cinema_system.domain.ScreenAsserts.*;
import static com.cinema.cinema_system.domain.ScreenTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScreenMapperTest {

    private ScreenMapper screenMapper;

    @BeforeEach
    void setUp() {
        screenMapper = new ScreenMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getScreenSample1();
        var actual = screenMapper.toEntity(screenMapper.toDto(expected));
        assertScreenAllPropertiesEquals(expected, actual);
    }
}
