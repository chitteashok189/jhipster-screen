package org.jhipster.blog.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.blog.web.rest.TestUtil.sameInstant;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.blog.IntegrationTest;
import org.jhipster.blog.domain.Scouting;
import org.jhipster.blog.domain.enumeration.CropState;
import org.jhipster.blog.domain.enumeration.ScoutingType;
import org.jhipster.blog.repository.ScoutingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ScoutingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScoutingResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Instant DEFAULT_SCOUTING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SCOUTING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SCOUT = "AAAAAAAAAA";
    private static final String UPDATED_SCOUT = "BBBBBBBBBB";

    private static final ScoutingType DEFAULT_SCOUTING_TYPE = ScoutingType.Growth;
    private static final ScoutingType UPDATED_SCOUTING_TYPE = ScoutingType.Weeds;

    private static final Integer DEFAULT_SCOUTING_COORDINATES = 1;
    private static final Integer UPDATED_SCOUTING_COORDINATES = 2;

    private static final Integer DEFAULT_SCOUTING_AREA = 1;
    private static final Integer UPDATED_SCOUTING_AREA = 2;

    private static final CropState DEFAULT_CROP_STATE = CropState.Bad;
    private static final CropState UPDATED_CROP_STATE = CropState.Normal;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/scoutings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ScoutingRepository scoutingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScoutingMockMvc;

    private Scouting scouting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scouting createEntity(EntityManager em) {
        Scouting scouting = new Scouting()
            .gUID(DEFAULT_G_UID)
            .scoutingDate(DEFAULT_SCOUTING_DATE)
            .scout(DEFAULT_SCOUT)
            .scoutingType(DEFAULT_SCOUTING_TYPE)
            .scoutingCoordinates(DEFAULT_SCOUTING_COORDINATES)
            .scoutingArea(DEFAULT_SCOUTING_AREA)
            .cropState(DEFAULT_CROP_STATE)
            .comments(DEFAULT_COMMENTS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return scouting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scouting createUpdatedEntity(EntityManager em) {
        Scouting scouting = new Scouting()
            .gUID(UPDATED_G_UID)
            .scoutingDate(UPDATED_SCOUTING_DATE)
            .scout(UPDATED_SCOUT)
            .scoutingType(UPDATED_SCOUTING_TYPE)
            .scoutingCoordinates(UPDATED_SCOUTING_COORDINATES)
            .scoutingArea(UPDATED_SCOUTING_AREA)
            .cropState(UPDATED_CROP_STATE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return scouting;
    }

    @BeforeEach
    public void initTest() {
        scouting = createEntity(em);
    }

    @Test
    @Transactional
    void createScouting() throws Exception {
        int databaseSizeBeforeCreate = scoutingRepository.findAll().size();
        // Create the Scouting
        restScoutingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scouting)))
            .andExpect(status().isCreated());

        // Validate the Scouting in the database
        List<Scouting> scoutingList = scoutingRepository.findAll();
        assertThat(scoutingList).hasSize(databaseSizeBeforeCreate + 1);
        Scouting testScouting = scoutingList.get(scoutingList.size() - 1);
        assertThat(testScouting.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testScouting.getScoutingDate()).isEqualTo(DEFAULT_SCOUTING_DATE);
        assertThat(testScouting.getScout()).isEqualTo(DEFAULT_SCOUT);
        assertThat(testScouting.getScoutingType()).isEqualTo(DEFAULT_SCOUTING_TYPE);
        assertThat(testScouting.getScoutingCoordinates()).isEqualTo(DEFAULT_SCOUTING_COORDINATES);
        assertThat(testScouting.getScoutingArea()).isEqualTo(DEFAULT_SCOUTING_AREA);
        assertThat(testScouting.getCropState()).isEqualTo(DEFAULT_CROP_STATE);
        assertThat(testScouting.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testScouting.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testScouting.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testScouting.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testScouting.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createScoutingWithExistingId() throws Exception {
        // Create the Scouting with an existing ID
        scouting.setId(1L);

        int databaseSizeBeforeCreate = scoutingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScoutingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scouting)))
            .andExpect(status().isBadRequest());

        // Validate the Scouting in the database
        List<Scouting> scoutingList = scoutingRepository.findAll();
        assertThat(scoutingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllScoutings() throws Exception {
        // Initialize the database
        scoutingRepository.saveAndFlush(scouting);

        // Get all the scoutingList
        restScoutingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scouting.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].scoutingDate").value(hasItem(DEFAULT_SCOUTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].scout").value(hasItem(DEFAULT_SCOUT)))
            .andExpect(jsonPath("$.[*].scoutingType").value(hasItem(DEFAULT_SCOUTING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].scoutingCoordinates").value(hasItem(DEFAULT_SCOUTING_COORDINATES)))
            .andExpect(jsonPath("$.[*].scoutingArea").value(hasItem(DEFAULT_SCOUTING_AREA)))
            .andExpect(jsonPath("$.[*].cropState").value(hasItem(DEFAULT_CROP_STATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getScouting() throws Exception {
        // Initialize the database
        scoutingRepository.saveAndFlush(scouting);

        // Get the scouting
        restScoutingMockMvc
            .perform(get(ENTITY_API_URL_ID, scouting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scouting.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.scoutingDate").value(DEFAULT_SCOUTING_DATE.toString()))
            .andExpect(jsonPath("$.scout").value(DEFAULT_SCOUT))
            .andExpect(jsonPath("$.scoutingType").value(DEFAULT_SCOUTING_TYPE.toString()))
            .andExpect(jsonPath("$.scoutingCoordinates").value(DEFAULT_SCOUTING_COORDINATES))
            .andExpect(jsonPath("$.scoutingArea").value(DEFAULT_SCOUTING_AREA))
            .andExpect(jsonPath("$.cropState").value(DEFAULT_CROP_STATE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingScouting() throws Exception {
        // Get the scouting
        restScoutingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewScouting() throws Exception {
        // Initialize the database
        scoutingRepository.saveAndFlush(scouting);

        int databaseSizeBeforeUpdate = scoutingRepository.findAll().size();

        // Update the scouting
        Scouting updatedScouting = scoutingRepository.findById(scouting.getId()).get();
        // Disconnect from session so that the updates on updatedScouting are not directly saved in db
        em.detach(updatedScouting);
        updatedScouting
            .gUID(UPDATED_G_UID)
            .scoutingDate(UPDATED_SCOUTING_DATE)
            .scout(UPDATED_SCOUT)
            .scoutingType(UPDATED_SCOUTING_TYPE)
            .scoutingCoordinates(UPDATED_SCOUTING_COORDINATES)
            .scoutingArea(UPDATED_SCOUTING_AREA)
            .cropState(UPDATED_CROP_STATE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restScoutingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedScouting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedScouting))
            )
            .andExpect(status().isOk());

        // Validate the Scouting in the database
        List<Scouting> scoutingList = scoutingRepository.findAll();
        assertThat(scoutingList).hasSize(databaseSizeBeforeUpdate);
        Scouting testScouting = scoutingList.get(scoutingList.size() - 1);
        assertThat(testScouting.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testScouting.getScoutingDate()).isEqualTo(UPDATED_SCOUTING_DATE);
        assertThat(testScouting.getScout()).isEqualTo(UPDATED_SCOUT);
        assertThat(testScouting.getScoutingType()).isEqualTo(UPDATED_SCOUTING_TYPE);
        assertThat(testScouting.getScoutingCoordinates()).isEqualTo(UPDATED_SCOUTING_COORDINATES);
        assertThat(testScouting.getScoutingArea()).isEqualTo(UPDATED_SCOUTING_AREA);
        assertThat(testScouting.getCropState()).isEqualTo(UPDATED_CROP_STATE);
        assertThat(testScouting.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testScouting.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testScouting.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testScouting.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testScouting.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingScouting() throws Exception {
        int databaseSizeBeforeUpdate = scoutingRepository.findAll().size();
        scouting.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoutingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scouting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scouting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scouting in the database
        List<Scouting> scoutingList = scoutingRepository.findAll();
        assertThat(scoutingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScouting() throws Exception {
        int databaseSizeBeforeUpdate = scoutingRepository.findAll().size();
        scouting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoutingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scouting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scouting in the database
        List<Scouting> scoutingList = scoutingRepository.findAll();
        assertThat(scoutingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScouting() throws Exception {
        int databaseSizeBeforeUpdate = scoutingRepository.findAll().size();
        scouting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoutingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scouting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Scouting in the database
        List<Scouting> scoutingList = scoutingRepository.findAll();
        assertThat(scoutingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScoutingWithPatch() throws Exception {
        // Initialize the database
        scoutingRepository.saveAndFlush(scouting);

        int databaseSizeBeforeUpdate = scoutingRepository.findAll().size();

        // Update the scouting using partial update
        Scouting partialUpdatedScouting = new Scouting();
        partialUpdatedScouting.setId(scouting.getId());

        partialUpdatedScouting
            .gUID(UPDATED_G_UID)
            .scoutingDate(UPDATED_SCOUTING_DATE)
            .scoutingType(UPDATED_SCOUTING_TYPE)
            .scoutingCoordinates(UPDATED_SCOUTING_COORDINATES)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restScoutingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScouting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScouting))
            )
            .andExpect(status().isOk());

        // Validate the Scouting in the database
        List<Scouting> scoutingList = scoutingRepository.findAll();
        assertThat(scoutingList).hasSize(databaseSizeBeforeUpdate);
        Scouting testScouting = scoutingList.get(scoutingList.size() - 1);
        assertThat(testScouting.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testScouting.getScoutingDate()).isEqualTo(UPDATED_SCOUTING_DATE);
        assertThat(testScouting.getScout()).isEqualTo(DEFAULT_SCOUT);
        assertThat(testScouting.getScoutingType()).isEqualTo(UPDATED_SCOUTING_TYPE);
        assertThat(testScouting.getScoutingCoordinates()).isEqualTo(UPDATED_SCOUTING_COORDINATES);
        assertThat(testScouting.getScoutingArea()).isEqualTo(DEFAULT_SCOUTING_AREA);
        assertThat(testScouting.getCropState()).isEqualTo(DEFAULT_CROP_STATE);
        assertThat(testScouting.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testScouting.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testScouting.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testScouting.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testScouting.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateScoutingWithPatch() throws Exception {
        // Initialize the database
        scoutingRepository.saveAndFlush(scouting);

        int databaseSizeBeforeUpdate = scoutingRepository.findAll().size();

        // Update the scouting using partial update
        Scouting partialUpdatedScouting = new Scouting();
        partialUpdatedScouting.setId(scouting.getId());

        partialUpdatedScouting
            .gUID(UPDATED_G_UID)
            .scoutingDate(UPDATED_SCOUTING_DATE)
            .scout(UPDATED_SCOUT)
            .scoutingType(UPDATED_SCOUTING_TYPE)
            .scoutingCoordinates(UPDATED_SCOUTING_COORDINATES)
            .scoutingArea(UPDATED_SCOUTING_AREA)
            .cropState(UPDATED_CROP_STATE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restScoutingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScouting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScouting))
            )
            .andExpect(status().isOk());

        // Validate the Scouting in the database
        List<Scouting> scoutingList = scoutingRepository.findAll();
        assertThat(scoutingList).hasSize(databaseSizeBeforeUpdate);
        Scouting testScouting = scoutingList.get(scoutingList.size() - 1);
        assertThat(testScouting.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testScouting.getScoutingDate()).isEqualTo(UPDATED_SCOUTING_DATE);
        assertThat(testScouting.getScout()).isEqualTo(UPDATED_SCOUT);
        assertThat(testScouting.getScoutingType()).isEqualTo(UPDATED_SCOUTING_TYPE);
        assertThat(testScouting.getScoutingCoordinates()).isEqualTo(UPDATED_SCOUTING_COORDINATES);
        assertThat(testScouting.getScoutingArea()).isEqualTo(UPDATED_SCOUTING_AREA);
        assertThat(testScouting.getCropState()).isEqualTo(UPDATED_CROP_STATE);
        assertThat(testScouting.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testScouting.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testScouting.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testScouting.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testScouting.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingScouting() throws Exception {
        int databaseSizeBeforeUpdate = scoutingRepository.findAll().size();
        scouting.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoutingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scouting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scouting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scouting in the database
        List<Scouting> scoutingList = scoutingRepository.findAll();
        assertThat(scoutingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScouting() throws Exception {
        int databaseSizeBeforeUpdate = scoutingRepository.findAll().size();
        scouting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoutingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scouting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scouting in the database
        List<Scouting> scoutingList = scoutingRepository.findAll();
        assertThat(scoutingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScouting() throws Exception {
        int databaseSizeBeforeUpdate = scoutingRepository.findAll().size();
        scouting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoutingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(scouting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Scouting in the database
        List<Scouting> scoutingList = scoutingRepository.findAll();
        assertThat(scoutingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScouting() throws Exception {
        // Initialize the database
        scoutingRepository.saveAndFlush(scouting);

        int databaseSizeBeforeDelete = scoutingRepository.findAll().size();

        // Delete the scouting
        restScoutingMockMvc
            .perform(delete(ENTITY_API_URL_ID, scouting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Scouting> scoutingList = scoutingRepository.findAll();
        assertThat(scoutingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
