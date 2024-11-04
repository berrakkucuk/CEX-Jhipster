package com.cinema.cinema_system.web.rest;

import com.cinema.cinema_system.repository.SeatRepository;
import com.cinema.cinema_system.service.SeatService;
import com.cinema.cinema_system.service.dto.SeatDTO;
import com.cinema.cinema_system.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cinema.cinema_system.domain.Seat}.
 */
@RestController
@RequestMapping("/api/seats")
public class SeatResource {

    private static final Logger log = LoggerFactory.getLogger(SeatResource.class);

    private static final String ENTITY_NAME = "seat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeatService seatService;

    private final SeatRepository seatRepository;

    public SeatResource(SeatService seatService, SeatRepository seatRepository) {
        this.seatService = seatService;
        this.seatRepository = seatRepository;
    }

    /**
     * {@code POST  /seats} : Create a new seat.
     *
     * @param seatDTO the seatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seatDTO, or with status {@code 400 (Bad Request)} if the seat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SeatDTO> createSeat(@RequestBody SeatDTO seatDTO) throws URISyntaxException {
        log.debug("REST request to save Seat : {}", seatDTO);
        if (seatDTO.getId() != null) {
            throw new BadRequestAlertException("A new seat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        seatDTO = seatService.save(seatDTO);
        return ResponseEntity.created(new URI("/api/seats/" + seatDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, seatDTO.getId().toString()))
            .body(seatDTO);
    }

    /**
     * {@code PUT  /seats/:id} : Updates an existing seat.
     *
     * @param id the id of the seatDTO to save.
     * @param seatDTO the seatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seatDTO,
     * or with status {@code 400 (Bad Request)} if the seatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SeatDTO> updateSeat(@PathVariable(value = "id", required = false) final Long id, @RequestBody SeatDTO seatDTO)
        throws URISyntaxException {
        log.debug("REST request to update Seat : {}, {}", id, seatDTO);
        if (seatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        seatDTO = seatService.update(seatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seatDTO.getId().toString()))
            .body(seatDTO);
    }

    /**
     * {@code PATCH  /seats/:id} : Partial updates given fields of an existing seat, field will ignore if it is null
     *
     * @param id the id of the seatDTO to save.
     * @param seatDTO the seatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seatDTO,
     * or with status {@code 400 (Bad Request)} if the seatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the seatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the seatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SeatDTO> partialUpdateSeat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SeatDTO seatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Seat partially : {}, {}", id, seatDTO);
        if (seatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SeatDTO> result = seatService.partialUpdate(seatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seatDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /seats} : get all the seats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seats in body.
     */
    @GetMapping("")
    public List<SeatDTO> getAllSeats() {
        log.debug("REST request to get all Seats");
        return seatService.findAll();
    }

    /**
     * {@code GET  /seats/:id} : get the "id" seat.
     *
     * @param id the id of the seatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SeatDTO> getSeat(@PathVariable("id") Long id) {
        log.debug("REST request to get Seat : {}", id);
        Optional<SeatDTO> seatDTO = seatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seatDTO);
    }

    /**
     * {@code DELETE  /seats/:id} : delete the "id" seat.
     *
     * @param id the id of the seatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable("id") Long id) {
        log.debug("REST request to delete Seat : {}", id);
        seatService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
