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
import org.jhipster.blog.domain.Harvest;
import org.jhipster.blog.repository.HarvestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HarvestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HarvestResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Instant DEFAULT_HARVESTING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HARVESTING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/harvests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHarvestMockMvc;

    private Harvest harvest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Harvest createEntity(EntityManager em) {
        Harvest harvest = new Harvest()
            .gUID(DEFAULT_G_UID)
            .harvestingDate(DEFAULT_HARVESTING_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return harvest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Harvest createUpdatedEntity(EntityManager em) {
        Harvest harvest = new Harvest()
            .gUID(UPDATED_G_UID)
            .harvestingDate(UPDATED_HARVESTING_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return harvest;
    }

    @BeforeEach
    public void initTest() {
        harvest = createEntity(em);
    }

    @Test
    @Transactional
    void createHarvest() throws Exception {
        int databaseSizeBeforeCreate = harvestRepository.findAll().size();
        // Create the Harvest
        restHarvestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(harvest)))
            .andExpect(status().isCreated());

        // Validate the Harvest in the database
        List<Harvest> harvestList = harvestRepository.findAll();
        assertThat(harvestList).hasSize(databaseSizeBeforeCreate + 1);
        Harvest testHarvest = harvestList.get(harvestList.size() - 1);
        assertThat(testHarvest.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testHarvest.getHarvestingDate()).isEqualTo(DEFAULT_HARVESTING_DATE);
        assertThat(testHarvest.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testHarvest.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testHarvest.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testHarvest.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createHarvestWithExistingId() throws Exception {
        // Create the Harvest with an existing ID
        harvest.setId(1L);

        int databaseSizeBeforeCreate = harvestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHarvestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(harvest)))
            .andExpect(status().isBadRequest());

        // Validate the Harvest in the database
        List<Harvest> harvestList = harvestRepository.findAll();
        assertThat(harvestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHarvests() throws Exception {
        // Initialize the database
        harvestRepository.saveAndFlush(harvest);

        // Get all the harvestList
        restHarvestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(harvest.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].harvestingDate").value(hasItem(DEFAULT_HARVESTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getHarvest() throws Exception {
        // Initialize the database
        harvestRepository.saveAndFlush(harvest);

        // Get the harvest
        restHarvestMockMvc
            .perform(get(ENTITY_API_URL_ID, harvest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(harvest.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.harvestingDate").value(DEFAULT_HARVESTING_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingHarvest() throws Exception {
        // Get the harvest
        restHarvestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHarvest() throws Exception {
        // Initialize the database
        harvestRepository.saveAndFlush(harvest);

        int databaseSizeBeforeUpdate = harvestRepository.findAll().size();

        // Update the harvest
        Harvest updatedHarvest = harvestRepository.findById(harvest.getId()).get();
        // Disconnect from session so that the updates on updatedHarvest are not directly saved in db
        em.detach(updatedHarvest);
        updatedHarvest
            .gUID(UPDATED_G_UID)
            .harvestingDate(UPDATED_HARVESTING_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restHarvestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHarvest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHarvest))
            )
            .andExpect(status().isOk());

        // Validate the Harvest in the database
        List<Harvest> harvestList = harvestRepository.findAll();
        assertThat(harvestList).hasSize(databaseSizeBeforeUpdate);
        Harvest testHarvest = harvestList.get(harvestList.size() - 1);
        assertThat(testHarvest.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testHarvest.getHarvestingDate()).isEqualTo(UPDATED_HARVESTING_DATE);
        assertThat(testHarvest.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testHarvest.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testHarvest.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testHarvest.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingHarvest() throws Exception {
        int databaseSizeBeforeUpdate = harvestRepository.findAll().size();
        harvest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHarvestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, harvest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(harvest))
            )
            .andExpect(status().isBadRequest());

        // Validate the Harvest in the database
        List<Harvest> harvestList = harvestRepository.findAll();
        assertThat(harvestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHarvest() throws Exception {
        int databaseSizeBeforeUpdate = harvestRepository.findAll().size();
        harvest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHarvestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(harvest))
            )
            .andExpect(status().isBadRequest());

        // Validate the Harvest in the database
        List<Harvest> harvestList = harvestRepository.findAll();
        assertThat(harvestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHarvest() throws Exception {
        int databaseSizeBeforeUpdate = harvestRepository.findAll().size();
        harvest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHarvestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(harvest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Harvest in the database
        List<Harvest> harvestList = harvestRepository.findAll();
        assertThat(harvestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHarvestWithPatch() throws Exception {
        // Initialize the database
        harvestRepository.saveAndFlush(harvest);

        int databaseSizeBeforeUpdate = harvestRepository.findAll().size();

        // Update the harvest using partial update
        Harvest partialUpdatedHarvest = new Harvest();
        partialUpdatedHarvest.setId(harvest.getId());

        restHarvestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHarvest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHarvest))
            )
            .andExpect(status().isOk());

        // Validate the Harvest in the database
        List<Harvest> harvestList = harvestRepository.findAll();
        assertThat(harvestList).hasSize(databaseSizeBeforeUpdate);
        Harvest testHarvest = harvestList.get(harvestList.size() - 1);
        assertThat(testHarvest.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testHarvest.getHarvestingDate()).isEqualTo(DEFAULT_HARVESTING_DATE);
        assertThat(testHarvest.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testHarvest.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testHarvest.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testHarvest.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateHarvestWithPatch() throws Exception {
        // Initialize the database
        harvestRepository.saveAndFlush(harvest);

        int databaseSizeBeforeUpdate = harvestRepository.findAll().size();

        // Update the harvest using partial update
        Harvest partialUpdatedHarvest = new Harvest();
        partialUpdatedHarvest.setId(harvest.getId());

        partialUpdatedHarvest
            .gUID(UPDATED_G_UID)
            .harvestingDate(UPDATED_HARVESTING_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restHarvestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHarvest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHarvest))
            )
            .andExpect(status().isOk());

        // Validate the Harvest in the database
        List<Harvest> harvestList = harvestRepository.findAll();
        assertThat(harvestList).hasSize(databaseSizeBeforeUpdate);
        Harvest testHarvest = harvestList.get(harvestList.size() - 1);
        assertThat(testHarvest.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testHarvest.getHarvestingDate()).isEqualTo(UPDATED_HARVESTING_DATE);
        assertThat(testHarvest.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testHarvest.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testHarvest.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testHarvest.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingHarvest() throws Exception {
        int databaseSizeBeforeUpdate = harvestRepository.findAll().size();
        harvest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHarvestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, harvest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(harvest))
            )
            .andExpect(status().isBadRequest());

        // Validate the Harvest in the database
        List<Harvest> harvestList = harvestRepository.findAll();
        assertThat(harvestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHarvest() throws Exception {
        int databaseSizeBeforeUpdate = harvestRepository.findAll().size();
        harvest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHarvestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(harvest))
            )
            .andExpect(status().isBadRequest());

        // Validate the Harvest in the database
        List<Harvest> harvestList = harvestRepository.findAll();
        assertThat(harvestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHarvest() throws Exception {
        int databaseSizeBeforeUpdate = harvestRepository.findAll().size();
        harvest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHarvestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(harvest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Harvest in the database
        List<Harvest> harvestList = harvestRepository.findAll();
        assertThat(harvestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHarvest() throws Exception {
        // Initialize the database
        harvestRepository.saveAndFlush(harvest);

        int databaseSizeBeforeDelete = harvestRepository.findAll().size();

        // Delete the harvest
        restHarvestMockMvc
            .perform(delete(ENTITY_API_URL_ID, harvest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Harvest> harvestList = harvestRepository.findAll();
        assertThat(harvestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
