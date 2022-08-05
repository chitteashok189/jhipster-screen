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
import org.jhipster.blog.domain.Pest;
import org.jhipster.blog.domain.enumeration.ThreatLevel;
import org.jhipster.blog.repository.PestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PestResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final ThreatLevel DEFAULT_THREAT_LEVEL = ThreatLevel.High;
    private static final ThreatLevel UPDATED_THREAT_LEVEL = ThreatLevel.Moderate;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SCIENTIFIC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCIENTIFIC_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ATTACHEMENTS = 1L;
    private static final Long UPDATED_ATTACHEMENTS = 2L;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/pests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PestRepository pestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPestMockMvc;

    private Pest pest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pest createEntity(EntityManager em) {
        Pest pest = new Pest()
            .gUID(DEFAULT_G_UID)
            .threatLevel(DEFAULT_THREAT_LEVEL)
            .name(DEFAULT_NAME)
            .scientificName(DEFAULT_SCIENTIFIC_NAME)
            .attachements(DEFAULT_ATTACHEMENTS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return pest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pest createUpdatedEntity(EntityManager em) {
        Pest pest = new Pest()
            .gUID(UPDATED_G_UID)
            .threatLevel(UPDATED_THREAT_LEVEL)
            .name(UPDATED_NAME)
            .scientificName(UPDATED_SCIENTIFIC_NAME)
            .attachements(UPDATED_ATTACHEMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return pest;
    }

    @BeforeEach
    public void initTest() {
        pest = createEntity(em);
    }

    @Test
    @Transactional
    void createPest() throws Exception {
        int databaseSizeBeforeCreate = pestRepository.findAll().size();
        // Create the Pest
        restPestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pest)))
            .andExpect(status().isCreated());

        // Validate the Pest in the database
        List<Pest> pestList = pestRepository.findAll();
        assertThat(pestList).hasSize(databaseSizeBeforeCreate + 1);
        Pest testPest = pestList.get(pestList.size() - 1);
        assertThat(testPest.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPest.getThreatLevel()).isEqualTo(DEFAULT_THREAT_LEVEL);
        assertThat(testPest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPest.getScientificName()).isEqualTo(DEFAULT_SCIENTIFIC_NAME);
        assertThat(testPest.getAttachements()).isEqualTo(DEFAULT_ATTACHEMENTS);
        assertThat(testPest.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPest.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPest.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPest.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPestWithExistingId() throws Exception {
        // Create the Pest with an existing ID
        pest.setId(1L);

        int databaseSizeBeforeCreate = pestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pest)))
            .andExpect(status().isBadRequest());

        // Validate the Pest in the database
        List<Pest> pestList = pestRepository.findAll();
        assertThat(pestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPests() throws Exception {
        // Initialize the database
        pestRepository.saveAndFlush(pest);

        // Get all the pestList
        restPestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pest.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].threatLevel").value(hasItem(DEFAULT_THREAT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].scientificName").value(hasItem(DEFAULT_SCIENTIFIC_NAME)))
            .andExpect(jsonPath("$.[*].attachements").value(hasItem(DEFAULT_ATTACHEMENTS.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPest() throws Exception {
        // Initialize the database
        pestRepository.saveAndFlush(pest);

        // Get the pest
        restPestMockMvc
            .perform(get(ENTITY_API_URL_ID, pest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pest.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.threatLevel").value(DEFAULT_THREAT_LEVEL.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.scientificName").value(DEFAULT_SCIENTIFIC_NAME))
            .andExpect(jsonPath("$.attachements").value(DEFAULT_ATTACHEMENTS.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPest() throws Exception {
        // Get the pest
        restPestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPest() throws Exception {
        // Initialize the database
        pestRepository.saveAndFlush(pest);

        int databaseSizeBeforeUpdate = pestRepository.findAll().size();

        // Update the pest
        Pest updatedPest = pestRepository.findById(pest.getId()).get();
        // Disconnect from session so that the updates on updatedPest are not directly saved in db
        em.detach(updatedPest);
        updatedPest
            .gUID(UPDATED_G_UID)
            .threatLevel(UPDATED_THREAT_LEVEL)
            .name(UPDATED_NAME)
            .scientificName(UPDATED_SCIENTIFIC_NAME)
            .attachements(UPDATED_ATTACHEMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPest))
            )
            .andExpect(status().isOk());

        // Validate the Pest in the database
        List<Pest> pestList = pestRepository.findAll();
        assertThat(pestList).hasSize(databaseSizeBeforeUpdate);
        Pest testPest = pestList.get(pestList.size() - 1);
        assertThat(testPest.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPest.getThreatLevel()).isEqualTo(UPDATED_THREAT_LEVEL);
        assertThat(testPest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPest.getScientificName()).isEqualTo(UPDATED_SCIENTIFIC_NAME);
        assertThat(testPest.getAttachements()).isEqualTo(UPDATED_ATTACHEMENTS);
        assertThat(testPest.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPest.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPest.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPest.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPest() throws Exception {
        int databaseSizeBeforeUpdate = pestRepository.findAll().size();
        pest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pest))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pest in the database
        List<Pest> pestList = pestRepository.findAll();
        assertThat(pestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPest() throws Exception {
        int databaseSizeBeforeUpdate = pestRepository.findAll().size();
        pest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pest))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pest in the database
        List<Pest> pestList = pestRepository.findAll();
        assertThat(pestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPest() throws Exception {
        int databaseSizeBeforeUpdate = pestRepository.findAll().size();
        pest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pest in the database
        List<Pest> pestList = pestRepository.findAll();
        assertThat(pestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePestWithPatch() throws Exception {
        // Initialize the database
        pestRepository.saveAndFlush(pest);

        int databaseSizeBeforeUpdate = pestRepository.findAll().size();

        // Update the pest using partial update
        Pest partialUpdatedPest = new Pest();
        partialUpdatedPest.setId(pest.getId());

        partialUpdatedPest
            .threatLevel(UPDATED_THREAT_LEVEL)
            .scientificName(UPDATED_SCIENTIFIC_NAME)
            .attachements(UPDATED_ATTACHEMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPest))
            )
            .andExpect(status().isOk());

        // Validate the Pest in the database
        List<Pest> pestList = pestRepository.findAll();
        assertThat(pestList).hasSize(databaseSizeBeforeUpdate);
        Pest testPest = pestList.get(pestList.size() - 1);
        assertThat(testPest.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPest.getThreatLevel()).isEqualTo(UPDATED_THREAT_LEVEL);
        assertThat(testPest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPest.getScientificName()).isEqualTo(UPDATED_SCIENTIFIC_NAME);
        assertThat(testPest.getAttachements()).isEqualTo(UPDATED_ATTACHEMENTS);
        assertThat(testPest.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPest.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPest.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPest.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePestWithPatch() throws Exception {
        // Initialize the database
        pestRepository.saveAndFlush(pest);

        int databaseSizeBeforeUpdate = pestRepository.findAll().size();

        // Update the pest using partial update
        Pest partialUpdatedPest = new Pest();
        partialUpdatedPest.setId(pest.getId());

        partialUpdatedPest
            .gUID(UPDATED_G_UID)
            .threatLevel(UPDATED_THREAT_LEVEL)
            .name(UPDATED_NAME)
            .scientificName(UPDATED_SCIENTIFIC_NAME)
            .attachements(UPDATED_ATTACHEMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPest))
            )
            .andExpect(status().isOk());

        // Validate the Pest in the database
        List<Pest> pestList = pestRepository.findAll();
        assertThat(pestList).hasSize(databaseSizeBeforeUpdate);
        Pest testPest = pestList.get(pestList.size() - 1);
        assertThat(testPest.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPest.getThreatLevel()).isEqualTo(UPDATED_THREAT_LEVEL);
        assertThat(testPest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPest.getScientificName()).isEqualTo(UPDATED_SCIENTIFIC_NAME);
        assertThat(testPest.getAttachements()).isEqualTo(UPDATED_ATTACHEMENTS);
        assertThat(testPest.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPest.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPest.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPest.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPest() throws Exception {
        int databaseSizeBeforeUpdate = pestRepository.findAll().size();
        pest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pest))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pest in the database
        List<Pest> pestList = pestRepository.findAll();
        assertThat(pestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPest() throws Exception {
        int databaseSizeBeforeUpdate = pestRepository.findAll().size();
        pest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pest))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pest in the database
        List<Pest> pestList = pestRepository.findAll();
        assertThat(pestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPest() throws Exception {
        int databaseSizeBeforeUpdate = pestRepository.findAll().size();
        pest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pest in the database
        List<Pest> pestList = pestRepository.findAll();
        assertThat(pestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePest() throws Exception {
        // Initialize the database
        pestRepository.saveAndFlush(pest);

        int databaseSizeBeforeDelete = pestRepository.findAll().size();

        // Delete the pest
        restPestMockMvc
            .perform(delete(ENTITY_API_URL_ID, pest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pest> pestList = pestRepository.findAll();
        assertThat(pestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
