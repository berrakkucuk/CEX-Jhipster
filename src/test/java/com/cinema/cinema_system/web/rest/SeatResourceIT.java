package com.cinema.cinema_system.web.rest;

import static com.cinema.cinema_system.domain.SeatAsserts.*;
import static com.cinema.cinema_system.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cinema.cinema_system.IntegrationTest;
import com.cinema.cinema_system.domain.Seat;
import com.cinema.cinema_system.repository.SeatRepository;
import com.cinema.cinema_system.service.dto.SeatDTO;
import com.cinema.cinema_system.service.mapper.SeatMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SeatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeatResourceIT {

    private static final String DEFAULT_SEAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SEAT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SEAT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SEAT_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/seats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeatMockMvc;

    private Seat seat;

    private Seat insertedSeat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seat createEntity(EntityManager em) {
        Seat seat = new Seat().seatNumber(DEFAULT_SEAT_NUMBER).seatType(DEFAULT_SEAT_TYPE);
        return seat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seat createUpdatedEntity(EntityManager em) {
        Seat seat = new Seat().seatNumber(UPDATED_SEAT_NUMBER).seatType(UPDATED_SEAT_TYPE);
        return seat;
    }

    @BeforeEach
    public void initTest() {
        seat = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSeat != null) {
            seatRepository.delete(insertedSeat);
            insertedSeat = null;
        }
    }

    @Test
    @Transactional
    void createSeat() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);
        var returnedSeatDTO = om.readValue(
            restSeatMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(seatDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SeatDTO.class
        );

        // Validate the Seat in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSeat = seatMapper.toEntity(returnedSeatDTO);
        assertSeatUpdatableFieldsEquals(returnedSeat, getPersistedSeat(returnedSeat));

        insertedSeat = returnedSeat;
    }

    @Test
    @Transactional
    void createSeatWithExistingId() throws Exception {
        // Create the Seat with an existing ID
        seat.setId(1L);
        SeatDTO seatDTO = seatMapper.toDto(seat);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(seatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seat in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSeats() throws Exception {
        // Initialize the database
        insertedSeat = seatRepository.saveAndFlush(seat);

        // Get all the seatList
        restSeatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seat.getId().intValue())))
            .andExpect(jsonPath("$.[*].seatNumber").value(hasItem(DEFAULT_SEAT_NUMBER)))
            .andExpect(jsonPath("$.[*].seatType").value(hasItem(DEFAULT_SEAT_TYPE)));
    }

    @Test
    @Transactional
    void getSeat() throws Exception {
        // Initialize the database
        insertedSeat = seatRepository.saveAndFlush(seat);

        // Get the seat
        restSeatMockMvc
            .perform(get(ENTITY_API_URL_ID, seat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seat.getId().intValue()))
            .andExpect(jsonPath("$.seatNumber").value(DEFAULT_SEAT_NUMBER))
            .andExpect(jsonPath("$.seatType").value(DEFAULT_SEAT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingSeat() throws Exception {
        // Get the seat
        restSeatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSeat() throws Exception {
        // Initialize the database
        insertedSeat = seatRepository.saveAndFlush(seat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the seat
        Seat updatedSeat = seatRepository.findById(seat.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSeat are not directly saved in db
        em.detach(updatedSeat);
        updatedSeat.seatNumber(UPDATED_SEAT_NUMBER).seatType(UPDATED_SEAT_TYPE);
        SeatDTO seatDTO = seatMapper.toDto(updatedSeat);

        restSeatMockMvc
            .perform(put(ENTITY_API_URL_ID, seatDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(seatDTO)))
            .andExpect(status().isOk());

        // Validate the Seat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSeatToMatchAllProperties(updatedSeat);
    }

    @Test
    @Transactional
    void putNonExistingSeat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        seat.setId(longCount.incrementAndGet());

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeatMockMvc
            .perform(put(ENTITY_API_URL_ID, seatDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(seatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        seat.setId(longCount.incrementAndGet());

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        seat.setId(longCount.incrementAndGet());

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(seatDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeatWithPatch() throws Exception {
        // Initialize the database
        insertedSeat = seatRepository.saveAndFlush(seat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the seat using partial update
        Seat partialUpdatedSeat = new Seat();
        partialUpdatedSeat.setId(seat.getId());

        partialUpdatedSeat.seatType(UPDATED_SEAT_TYPE);

        restSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSeat))
            )
            .andExpect(status().isOk());

        // Validate the Seat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSeatUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSeat, seat), getPersistedSeat(seat));
    }

    @Test
    @Transactional
    void fullUpdateSeatWithPatch() throws Exception {
        // Initialize the database
        insertedSeat = seatRepository.saveAndFlush(seat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the seat using partial update
        Seat partialUpdatedSeat = new Seat();
        partialUpdatedSeat.setId(seat.getId());

        partialUpdatedSeat.seatNumber(UPDATED_SEAT_NUMBER).seatType(UPDATED_SEAT_TYPE);

        restSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSeat))
            )
            .andExpect(status().isOk());

        // Validate the Seat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSeatUpdatableFieldsEquals(partialUpdatedSeat, getPersistedSeat(partialUpdatedSeat));
    }

    @Test
    @Transactional
    void patchNonExistingSeat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        seat.setId(longCount.incrementAndGet());

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seatDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        seat.setId(longCount.incrementAndGet());

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        seat.setId(longCount.incrementAndGet());

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeatMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(seatDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeat() throws Exception {
        // Initialize the database
        insertedSeat = seatRepository.saveAndFlush(seat);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the seat
        restSeatMockMvc
            .perform(delete(ENTITY_API_URL_ID, seat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return seatRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Seat getPersistedSeat(Seat seat) {
        return seatRepository.findById(seat.getId()).orElseThrow();
    }

    protected void assertPersistedSeatToMatchAllProperties(Seat expectedSeat) {
        assertSeatAllPropertiesEquals(expectedSeat, getPersistedSeat(expectedSeat));
    }

    protected void assertPersistedSeatToMatchUpdatableProperties(Seat expectedSeat) {
        assertSeatAllUpdatablePropertiesEquals(expectedSeat, getPersistedSeat(expectedSeat));
    }
}
