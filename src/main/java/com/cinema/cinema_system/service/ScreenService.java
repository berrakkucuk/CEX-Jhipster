package com.cinema.cinema_system.service;

import com.cinema.cinema_system.domain.Screen;
import com.cinema.cinema_system.repository.ScreenRepository;
import com.cinema.cinema_system.service.dto.ScreenDTO;
import com.cinema.cinema_system.service.mapper.ScreenMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cinema.cinema_system.domain.Screen}.
 */
@Service
@Transactional
public class ScreenService {

    private static final Logger log = LoggerFactory.getLogger(ScreenService.class);

    private final ScreenRepository screenRepository;

    private final ScreenMapper screenMapper;

    public ScreenService(ScreenRepository screenRepository, ScreenMapper screenMapper) {
        this.screenRepository = screenRepository;
        this.screenMapper = screenMapper;
    }

    /**
     * Save a screen.
     *
     * @param screenDTO the entity to save.
     * @return the persisted entity.
     */
    public ScreenDTO save(ScreenDTO screenDTO) {
        log.debug("Request to save Screen : {}", screenDTO);
        Screen screen = screenMapper.toEntity(screenDTO);
        screen = screenRepository.save(screen);
        return screenMapper.toDto(screen);
    }

    /**
     * Update a screen.
     *
     * @param screenDTO the entity to save.
     * @return the persisted entity.
     */
    public ScreenDTO update(ScreenDTO screenDTO) {
        log.debug("Request to update Screen : {}", screenDTO);
        Screen screen = screenMapper.toEntity(screenDTO);
        screen = screenRepository.save(screen);
        return screenMapper.toDto(screen);
    }

    /**
     * Partially update a screen.
     *
     * @param screenDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ScreenDTO> partialUpdate(ScreenDTO screenDTO) {
        log.debug("Request to partially update Screen : {}", screenDTO);

        return screenRepository
            .findById(screenDTO.getId())
            .map(existingScreen -> {
                screenMapper.partialUpdate(existingScreen, screenDTO);

                return existingScreen;
            })
            .map(screenRepository::save)
            .map(screenMapper::toDto);
    }

    /**
     * Get all the screens.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ScreenDTO> findAll() {
        log.debug("Request to get all Screens");
        return screenRepository.findAll().stream().map(screenMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one screen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ScreenDTO> findOne(Long id) {
        log.debug("Request to get Screen : {}", id);
        return screenRepository.findById(id).map(screenMapper::toDto);
    }

    /**
     * Delete the screen by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Screen : {}", id);
        screenRepository.deleteById(id);
    }
}
