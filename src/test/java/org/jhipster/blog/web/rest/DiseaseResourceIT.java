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
import org.jhipster.blog.domain.Disease;
import org.jhipster.blog.domain.enumeration.DisThreatLevel;
import org.jhipster.blog.repository.DiseaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DiseaseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiseaseResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final DisThreatLevel DEFAULT_THREAT_LEVEL = DisThreatLevel.High;
    private static final DisThreatLevel UPDATED_THREAT_LEVEL = DisThreatLevel.Moderate;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAUSAL_ORGANISM = "AAAAAAAAAA";
    private static final String UPDATED_CAUSAL_ORGANISM = "BBBBBBBBBB";

    private static final Long DEFAULT_ATTACHMENTS = 1L;
    private static final Long UPDATED_ATTACHMENTS = 2L;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/diseases";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiseaseMockMvc;

    private Disease disease;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disease createEntity(EntityManager em) {
        Disease disease = new Disease()
            .gUID(DEFAULT_G_UID)
            .threatLevel(DEFAULT_THREAT_LEVEL)
            .name(DEFAULT_NAME)
            .causalOrganism(DEFAULT_CAUSAL_ORGANISM)
            .attachments(DEFAULT_ATTACHMENTS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return disease;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disease createUpdatedEntity(EntityManager em) {
        Disease disease = new Disease()
            .gUID(UPDATED_G_UID)
            .threatLevel(UPDATED_THREAT_LEVEL)
            .name(UPDATED_NAME)
            .causalOrganism(UPDATED_CAUSAL_ORGANISM)
            .attachments(UPDATED_ATTACHMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return disease;
    }

    @BeforeEach
    public void initTest() {
        disease = createEntity(em);
    }

    @Test
    @Transactional
    void createDisease() throws Exception {
        int databaseSizeBeforeCreate = diseaseRepository.findAll().size();
        // Create the Disease
        restDiseaseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disease)))
            .andExpect(status().isCreated());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeCreate + 1);
        Disease testDisease = diseaseList.get(diseaseList.size() - 1);
        assertThat(testDisease.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testDisease.getThreatLevel()).isEqualTo(DEFAULT_THREAT_LEVEL);
        assertThat(testDisease.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDisease.getCausalOrganism()).isEqualTo(DEFAULT_CAUSAL_ORGANISM);
        assertThat(testDisease.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testDisease.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDisease.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDisease.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDisease.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createDiseaseWithExistingId() throws Exception {
        // Create the Disease with an existing ID
        disease.setId(1L);

        int databaseSizeBeforeCreate = diseaseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiseaseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disease)))
            .andExpect(status().isBadRequest());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDiseases() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

        // Get all the diseaseList
        restDiseaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disease.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].threatLevel").value(hasItem(DEFAULT_THREAT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].causalOrganism").value(hasItem(DEFAULT_CAUSAL_ORGANISM)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(DEFAULT_ATTACHMENTS.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getDisease() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

        // Get the disease
        restDiseaseMockMvc
            .perform(get(ENTITY_API_URL_ID, disease.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disease.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.threatLevel").value(DEFAULT_THREAT_LEVEL.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.causalOrganism").value(DEFAULT_CAUSAL_ORGANISM))
            .andExpect(jsonPath("$.attachments").value(DEFAULT_ATTACHMENTS.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingDisease() throws Exception {
        // Get the disease
        restDiseaseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDisease() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

        int databaseSizeBeforeUpdate = diseaseRepository.findAll().size();

        // Update the disease
        Disease updatedDisease = diseaseRepository.findById(disease.getId()).get();
        // Disconnect from session so that the updates on updatedDisease are not directly saved in db
        em.detach(updatedDisease);
        updatedDisease
            .gUID(UPDATED_G_UID)
            .threatLevel(UPDATED_THREAT_LEVEL)
            .name(UPDATED_NAME)
            .causalOrganism(UPDATED_CAUSAL_ORGANISM)
            .attachments(UPDATED_ATTACHMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDiseaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDisease.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDisease))
            )
            .andExpect(status().isOk());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeUpdate);
        Disease testDisease = diseaseList.get(diseaseList.size() - 1);
        assertThat(testDisease.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testDisease.getThreatLevel()).isEqualTo(UPDATED_THREAT_LEVEL);
        assertThat(testDisease.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDisease.getCausalOrganism()).isEqualTo(UPDATED_CAUSAL_ORGANISM);
        assertThat(testDisease.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testDisease.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDisease.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDisease.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDisease.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingDisease() throws Exception {
        int databaseSizeBeforeUpdate = diseaseRepository.findAll().size();
        disease.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiseaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, disease.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disease))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDisease() throws Exception {
        int databaseSizeBeforeUpdate = diseaseRepository.findAll().size();
        disease.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiseaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disease))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDisease() throws Exception {
        int databaseSizeBeforeUpdate = diseaseRepository.findAll().size();
        disease.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiseaseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disease)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiseaseWithPatch() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

        int databaseSizeBeforeUpdate = diseaseRepository.findAll().size();

        // Update the disease using partial update
        Disease partialUpdatedDisease = new Disease();
        partialUpdatedDisease.setId(disease.getId());

        partialUpdatedDisease.causalOrganism(UPDATED_CAUSAL_ORGANISM).attachments(UPDATED_ATTACHMENTS).updatedBy(UPDATED_UPDATED_BY);

        restDiseaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisease.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDisease))
            )
            .andExpect(status().isOk());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeUpdate);
        Disease testDisease = diseaseList.get(diseaseList.size() - 1);
        assertThat(testDisease.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testDisease.getThreatLevel()).isEqualTo(DEFAULT_THREAT_LEVEL);
        assertThat(testDisease.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDisease.getCausalOrganism()).isEqualTo(UPDATED_CAUSAL_ORGANISM);
        assertThat(testDisease.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testDisease.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDisease.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDisease.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDisease.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateDiseaseWithPatch() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

        int databaseSizeBeforeUpdate = diseaseRepository.findAll().size();

        // Update the disease using partial update
        Disease partialUpdatedDisease = new Disease();
        partialUpdatedDisease.setId(disease.getId());

        partialUpdatedDisease
            .gUID(UPDATED_G_UID)
            .threatLevel(UPDATED_THREAT_LEVEL)
            .name(UPDATED_NAME)
            .causalOrganism(UPDATED_CAUSAL_ORGANISM)
            .attachments(UPDATED_ATTACHMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDiseaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisease.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDisease))
            )
            .andExpect(status().isOk());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeUpdate);
        Disease testDisease = diseaseList.get(diseaseList.size() - 1);
        assertThat(testDisease.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testDisease.getThreatLevel()).isEqualTo(UPDATED_THREAT_LEVEL);
        assertThat(testDisease.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDisease.getCausalOrganism()).isEqualTo(UPDATED_CAUSAL_ORGANISM);
        assertThat(testDisease.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testDisease.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDisease.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDisease.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDisease.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingDisease() throws Exception {
        int databaseSizeBeforeUpdate = diseaseRepository.findAll().size();
        disease.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiseaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, disease.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(disease))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDisease() throws Exception {
        int databaseSizeBeforeUpdate = diseaseRepository.findAll().size();
        disease.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiseaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(disease))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDisease() throws Exception {
        int databaseSizeBeforeUpdate = diseaseRepository.findAll().size();
        disease.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiseaseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(disease)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDisease() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

        int databaseSizeBeforeDelete = diseaseRepository.findAll().size();

        // Delete the disease
        restDiseaseMockMvc
            .perform(delete(ENTITY_API_URL_ID, disease.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
