package com.cinema.cinema_system.service.mapper;

import com.cinema.cinema_system.domain.Customer;
import com.cinema.cinema_system.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {}
