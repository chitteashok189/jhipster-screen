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
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.blog.IntegrationTest;
import org.jhipster.blog.domain.Seed;
import org.jhipster.blog.domain.enumeration.SeedClass;
import org.jhipster.blog.domain.enumeration.SeedRate;
import org.jhipster.blog.domain.enumeration.Treatment;
import org.jhipster.blog.repository.SeedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SeedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeedResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_BREEDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_BREEDER_ID = "BBBBBBBBBB";

    private static final SeedClass DEFAULT_SEED_CLASS = SeedClass.Certified;
    private static final SeedClass UPDATED_SEED_CLASS = SeedClass.Foundation;

    private static final String DEFAULT_VARIETY = "AAAAAAAAAA";
    private static final String UPDATED_VARIETY = "BBBBBBBBBB";

    private static final SeedRate DEFAULT_SEED_RATE = SeedRate.Seeds_Ha;
    private static final SeedRate UPDATED_SEED_RATE = SeedRate.Kg_Ha;

    private static final Integer DEFAULT_GERMINATION_PERCENTAGE = 1;
    private static final Integer UPDATED_GERMINATION_PERCENTAGE = 2;

    private static final Treatment DEFAULT_TREATMENT = Treatment.Dry;
    private static final Treatment UPDATED_TREATMENT = Treatment.Treatment;

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/seeds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SeedRepository seedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeedMockMvc;

    private Seed seed;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seed createEntity(EntityManager em) {
        Seed seed = new Seed()
            .gUID(DEFAULT_G_UID)
            .breederID(DEFAULT_BREEDER_ID)
            .seedClass(DEFAULT_SEED_CLASS)
            .variety(DEFAULT_VARIETY)
            .seedRate(DEFAULT_SEED_RATE)
            .germinationPercentage(DEFAULT_GERMINATION_PERCENTAGE)
            .treatment(DEFAULT_TREATMENT)
            .origin(DEFAULT_ORIGIN)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return seed;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seed createUpdatedEntity(EntityManager em) {
        Seed seed = new Seed()
            .gUID(UPDATED_G_UID)
            .breederID(UPDATED_BREEDER_ID)
            .seedClass(UPDATED_SEED_CLASS)
            .variety(UPDATED_VARIETY)
            .seedRate(UPDATED_SEED_RATE)
            .germinationPercentage(UPDATED_GERMINATION_PERCENTAGE)
            .treatment(UPDATED_TREATMENT)
            .origin(UPDATED_ORIGIN)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return seed;
    }

    @BeforeEach
    public void initTest() {
        seed = createEntity(em);
    }

    @Test
    @Transactional
    void createSeed() throws Exception {
        int databaseSizeBeforeCreate = seedRepository.findAll().size();
        // Create the Seed
        restSeedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seed)))
            .andExpect(status().isCreated());

        // Validate the Seed in the database
        List<Seed> seedList = seedRepository.findAll();
        assertThat(seedList).hasSize(databaseSizeBeforeCreate + 1);
        Seed testSeed = seedList.get(seedList.size() - 1);
        assertThat(testSeed.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testSeed.getBreederID()).isEqualTo(DEFAULT_BREEDER_ID);
        assertThat(testSeed.getSeedClass()).isEqualTo(DEFAULT_SEED_CLASS);
        assertThat(testSeed.getVariety()).isEqualTo(DEFAULT_VARIETY);
        assertThat(testSeed.getSeedRate()).isEqualTo(DEFAULT_SEED_RATE);
        assertThat(testSeed.getGerminationPercentage()).isEqualTo(DEFAULT_GERMINATION_PERCENTAGE);
        assertThat(testSeed.getTreatment()).isEqualTo(DEFAULT_TREATMENT);
        assertThat(testSeed.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testSeed.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSeed.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSeed.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSeed.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createSeedWithExistingId() throws Exception {
        // Create the Seed with an existing ID
        seed.setId(1L);

        int databaseSizeBeforeCreate = seedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seed)))
            .andExpect(status().isBadRequest());

        // Validate the Seed in the database
        List<Seed> seedList = seedRepository.findAll();
        assertThat(seedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSeeds() throws Exception {
        // Initialize the database
        seedRepository.saveAndFlush(seed);

        // Get all the seedList
        restSeedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seed.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].breederID").value(hasItem(DEFAULT_BREEDER_ID)))
            .andExpect(jsonPath("$.[*].seedClass").value(hasItem(DEFAULT_SEED_CLASS.toString())))
            .andExpect(jsonPath("$.[*].variety").value(hasItem(DEFAULT_VARIETY)))
            .andExpect(jsonPath("$.[*].seedRate").value(hasItem(DEFAULT_SEED_RATE.toString())))
            .andExpect(jsonPath("$.[*].germinationPercentage").value(hasItem(DEFAULT_GERMINATION_PERCENTAGE)))
            .andExpect(jsonPath("$.[*].treatment").value(hasItem(DEFAULT_TREATMENT.toString())))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getSeed() throws Exception {
        // Initialize the database
        seedRepository.saveAndFlush(seed);

        // Get the seed
        restSeedMockMvc
            .perform(get(ENTITY_API_URL_ID, seed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seed.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.breederID").value(DEFAULT_BREEDER_ID))
            .andExpect(jsonPath("$.seedClass").value(DEFAULT_SEED_CLASS.toString()))
            .andExpect(jsonPath("$.variety").value(DEFAULT_VARIETY))
            .andExpect(jsonPath("$.seedRate").value(DEFAULT_SEED_RATE.toString()))
            .andExpect(jsonPath("$.germinationPercentage").value(DEFAULT_GERMINATION_PERCENTAGE))
            .andExpect(jsonPath("$.treatment").value(DEFAULT_TREATMENT.toString()))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingSeed() throws Exception {
        // Get the seed
        restSeedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSeed() throws Exception {
        // Initialize the database
        seedRepository.saveAndFlush(seed);

        int databaseSizeBeforeUpdate = seedRepository.findAll().size();

        // Update the seed
        Seed updatedSeed = seedRepository.findById(seed.getId()).get();
        // Disconnect from session so that the updates on updatedSeed are not directly saved in db
        em.detach(updatedSeed);
        updatedSeed
            .gUID(UPDATED_G_UID)
            .breederID(UPDATED_BREEDER_ID)
            .seedClass(UPDATED_SEED_CLASS)
            .variety(UPDATED_VARIETY)
            .seedRate(UPDATED_SEED_RATE)
            .germinationPercentage(UPDATED_GERMINATION_PERCENTAGE)
            .treatment(UPDATED_TREATMENT)
            .origin(UPDATED_ORIGIN)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSeedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSeed.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSeed))
            )
            .andExpect(status().isOk());

        // Validate the Seed in the database
        List<Seed> seedList = seedRepository.findAll();
        assertThat(seedList).hasSize(databaseSizeBeforeUpdate);
        Seed testSeed = seedList.get(seedList.size() - 1);
        assertThat(testSeed.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSeed.getBreederID()).isEqualTo(UPDATED_BREEDER_ID);
        assertThat(testSeed.getSeedClass()).isEqualTo(UPDATED_SEED_CLASS);
        assertThat(testSeed.getVariety()).isEqualTo(UPDATED_VARIETY);
        assertThat(testSeed.getSeedRate()).isEqualTo(UPDATED_SEED_RATE);
        assertThat(testSeed.getGerminationPercentage()).isEqualTo(UPDATED_GERMINATION_PERCENTAGE);
        assertThat(testSeed.getTreatment()).isEqualTo(UPDATED_TREATMENT);
        assertThat(testSeed.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testSeed.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSeed.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSeed.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSeed.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingSeed() throws Exception {
        int databaseSizeBeforeUpdate = seedRepository.findAll().size();
        seed.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seed.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seed))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seed in the database
        List<Seed> seedList = seedRepository.findAll();
        assertThat(seedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeed() throws Exception {
        int databaseSizeBeforeUpdate = seedRepository.findAll().size();
        seed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seed))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seed in the database
        List<Seed> seedList = seedRepository.findAll();
        assertThat(seedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeed() throws Exception {
        int databaseSizeBeforeUpdate = seedRepository.findAll().size();
        seed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seed)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seed in the database
        List<Seed> seedList = seedRepository.findAll();
        assertThat(seedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeedWithPatch() throws Exception {
        // Initialize the database
        seedRepository.saveAndFlush(seed);

        int databaseSizeBeforeUpdate = seedRepository.findAll().size();

        // Update the seed using partial update
        Seed partialUpdatedSeed = new Seed();
        partialUpdatedSeed.setId(seed.getId());

        partialUpdatedSeed
            .gUID(UPDATED_G_UID)
            .seedRate(UPDATED_SEED_RATE)
            .germinationPercentage(UPDATED_GERMINATION_PERCENTAGE)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restSeedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeed))
            )
            .andExpect(status().isOk());

        // Validate the Seed in the database
        List<Seed> seedList = seedRepository.findAll();
        assertThat(seedList).hasSize(databaseSizeBeforeUpdate);
        Seed testSeed = seedList.get(seedList.size() - 1);
        assertThat(testSeed.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSeed.getBreederID()).isEqualTo(DEFAULT_BREEDER_ID);
        assertThat(testSeed.getSeedClass()).isEqualTo(DEFAULT_SEED_CLASS);
        assertThat(testSeed.getVariety()).isEqualTo(DEFAULT_VARIETY);
        assertThat(testSeed.getSeedRate()).isEqualTo(UPDATED_SEED_RATE);
        assertThat(testSeed.getGerminationPercentage()).isEqualTo(UPDATED_GERMINATION_PERCENTAGE);
        assertThat(testSeed.getTreatment()).isEqualTo(DEFAULT_TREATMENT);
        assertThat(testSeed.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testSeed.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSeed.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSeed.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSeed.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateSeedWithPatch() throws Exception {
        // Initialize the database
        seedRepository.saveAndFlush(seed);

        int databaseSizeBeforeUpdate = seedRepository.findAll().size();

        // Update the seed using partial update
        Seed partialUpdatedSeed = new Seed();
        partialUpdatedSeed.setId(seed.getId());

        partialUpdatedSeed
            .gUID(UPDATED_G_UID)
            .breederID(UPDATED_BREEDER_ID)
            .seedClass(UPDATED_SEED_CLASS)
            .variety(UPDATED_VARIETY)
            .seedRate(UPDATED_SEED_RATE)
            .germinationPercentage(UPDATED_GERMINATION_PERCENTAGE)
            .treatment(UPDATED_TREATMENT)
            .origin(UPDATED_ORIGIN)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSeedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeed))
            )
            .andExpect(status().isOk());

        // Validate the Seed in the database
        List<Seed> seedList = seedRepository.findAll();
        assertThat(seedList).hasSize(databaseSizeBeforeUpdate);
        Seed testSeed = seedList.get(seedList.size() - 1);
        assertThat(testSeed.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSeed.getBreederID()).isEqualTo(UPDATED_BREEDER_ID);
        assertThat(testSeed.getSeedClass()).isEqualTo(UPDATED_SEED_CLASS);
        assertThat(testSeed.getVariety()).isEqualTo(UPDATED_VARIETY);
        assertThat(testSeed.getSeedRate()).isEqualTo(UPDATED_SEED_RATE);
        assertThat(testSeed.getGerminationPercentage()).isEqualTo(UPDATED_GERMINATION_PERCENTAGE);
        assertThat(testSeed.getTreatment()).isEqualTo(UPDATED_TREATMENT);
        assertThat(testSeed.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testSeed.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSeed.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSeed.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSeed.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingSeed() throws Exception {
        int databaseSizeBeforeUpdate = seedRepository.findAll().size();
        seed.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seed))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seed in the database
        List<Seed> seedList = seedRepository.findAll();
        assertThat(seedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeed() throws Exception {
        int databaseSizeBeforeUpdate = seedRepository.findAll().size();
        seed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seed))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seed in the database
        List<Seed> seedList = seedRepository.findAll();
        assertThat(seedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeed() throws Exception {
        int databaseSizeBeforeUpdate = seedRepository.findAll().size();
        seed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(seed)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seed in the database
        List<Seed> seedList = seedRepository.findAll();
        assertThat(seedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeed() throws Exception {
        // Initialize the database
        seedRepository.saveAndFlush(seed);

        int databaseSizeBeforeDelete = seedRepository.findAll().size();

        // Delete the seed
        restSeedMockMvc
            .perform(delete(ENTITY_API_URL_ID, seed.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Seed> seedList = seedRepository.findAll();
        assertThat(seedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
