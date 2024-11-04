package com.cinema.cinema_system.service;

import com.cinema.cinema_system.domain.Seat;
import com.cinema.cinema_system.repository.SeatRepository;
import com.cinema.cinema_system.service.dto.SeatDTO;
import com.cinema.cinema_system.service.mapper.SeatMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cinema.cinema_system.domain.Seat}.
 */
@Service
@Transactional
public class SeatService {

    private static final Logger log = LoggerFactory.getLogger(SeatService.class);

    private final SeatRepository seatRepository;

    private final SeatMapper seatMapper;

    public SeatService(SeatRepository seatRepository, SeatMapper seatMapper) {
        this.seatRepository = seatRepository;
        this.seatMapper = seatMapper;
    }

    /**
     * Save a seat.
     *
     * @param seatDTO the entity to save.
     * @return the persisted entity.
     */
    public SeatDTO save(SeatDTO seatDTO) {
        log.debug("Request to save Seat : {}", seatDTO);
        Seat seat = seatMapper.toEntity(seatDTO);
        seat = seatRepository.save(seat);
        return seatMapper.toDto(seat);
    }

    /**
     * Update a seat.
     *
     * @param seatDTO the entity to save.
     * @return the persisted entity.
     */
    public SeatDTO update(SeatDTO seatDTO) {
        log.debug("Request to update Seat : {}", seatDTO);
        Seat seat = seatMapper.toEntity(seatDTO);
        seat = seatRepository.save(seat);
        return seatMapper.toDto(seat);
    }

    /**
     * Partially update a seat.
     *
     * @param seatDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SeatDTO> partialUpdate(SeatDTO seatDTO) {
        log.debug("Request to partially update Seat : {}", seatDTO);

        return seatRepository
            .findById(seatDTO.getId())
            .map(existingSeat -> {
                seatMapper.partialUpdate(existingSeat, seatDTO);

                return existingSeat;
            })
            .map(seatRepository::save)
            .map(seatMapper::toDto);
    }

    /**
     * Get all the seats.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SeatDTO> findAll() {
        log.debug("Request to get all Seats");
        return seatRepository.findAll().stream().map(seatMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one seat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SeatDTO> findOne(Long id) {
        log.debug("Request to get Seat : {}", id);
        return seatRepository.findById(id).map(seatMapper::toDto);
    }

    /**
     * Delete the seat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Seat : {}", id);
        seatRepository.deleteById(id);
    }
}
