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
import org.jhipster.blog.domain.Disorder;
import org.jhipster.blog.repository.DisorderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DisorderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DisorderResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_PHYSIOLOGICAL_DISORDER = "AAAAAAAAAA";
    private static final String UPDATED_PHYSIOLOGICAL_DISORDER = "BBBBBBBBBB";

    private static final String DEFAULT_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_CAUSE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTROL_MEASURE = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL_MEASURE = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/disorders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DisorderRepository disorderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisorderMockMvc;

    private Disorder disorder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disorder createEntity(EntityManager em) {
        Disorder disorder = new Disorder()
            .gUID(DEFAULT_G_UID)
            .physiologicalDisorder(DEFAULT_PHYSIOLOGICAL_DISORDER)
            .cause(DEFAULT_CAUSE)
            .controlMeasure(DEFAULT_CONTROL_MEASURE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return disorder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disorder createUpdatedEntity(EntityManager em) {
        Disorder disorder = new Disorder()
            .gUID(UPDATED_G_UID)
            .physiologicalDisorder(UPDATED_PHYSIOLOGICAL_DISORDER)
            .cause(UPDATED_CAUSE)
            .controlMeasure(UPDATED_CONTROL_MEASURE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return disorder;
    }

    @BeforeEach
    public void initTest() {
        disorder = createEntity(em);
    }

    @Test
    @Transactional
    void createDisorder() throws Exception {
        int databaseSizeBeforeCreate = disorderRepository.findAll().size();
        // Create the Disorder
        restDisorderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disorder)))
            .andExpect(status().isCreated());

        // Validate the Disorder in the database
        List<Disorder> disorderList = disorderRepository.findAll();
        assertThat(disorderList).hasSize(databaseSizeBeforeCreate + 1);
        Disorder testDisorder = disorderList.get(disorderList.size() - 1);
        assertThat(testDisorder.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testDisorder.getPhysiologicalDisorder()).isEqualTo(DEFAULT_PHYSIOLOGICAL_DISORDER);
        assertThat(testDisorder.getCause()).isEqualTo(DEFAULT_CAUSE);
        assertThat(testDisorder.getControlMeasure()).isEqualTo(DEFAULT_CONTROL_MEASURE);
        assertThat(testDisorder.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDisorder.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDisorder.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDisorder.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createDisorderWithExistingId() throws Exception {
        // Create the Disorder with an existing ID
        disorder.setId(1L);

        int databaseSizeBeforeCreate = disorderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisorderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disorder)))
            .andExpect(status().isBadRequest());

        // Validate the Disorder in the database
        List<Disorder> disorderList = disorderRepository.findAll();
        assertThat(disorderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDisorders() throws Exception {
        // Initialize the database
        disorderRepository.saveAndFlush(disorder);

        // Get all the disorderList
        restDisorderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disorder.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].physiologicalDisorder").value(hasItem(DEFAULT_PHYSIOLOGICAL_DISORDER)))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE)))
            .andExpect(jsonPath("$.[*].controlMeasure").value(hasItem(DEFAULT_CONTROL_MEASURE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getDisorder() throws Exception {
        // Initialize the database
        disorderRepository.saveAndFlush(disorder);

        // Get the disorder
        restDisorderMockMvc
            .perform(get(ENTITY_API_URL_ID, disorder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disorder.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.physiologicalDisorder").value(DEFAULT_PHYSIOLOGICAL_DISORDER))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE))
            .andExpect(jsonPath("$.controlMeasure").value(DEFAULT_CONTROL_MEASURE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingDisorder() throws Exception {
        // Get the disorder
        restDisorderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDisorder() throws Exception {
        // Initialize the database
        disorderRepository.saveAndFlush(disorder);

        int databaseSizeBeforeUpdate = disorderRepository.findAll().size();

        // Update the disorder
        Disorder updatedDisorder = disorderRepository.findById(disorder.getId()).get();
        // Disconnect from session so that the updates on updatedDisorder are not directly saved in db
        em.detach(updatedDisorder);
        updatedDisorder
            .gUID(UPDATED_G_UID)
            .physiologicalDisorder(UPDATED_PHYSIOLOGICAL_DISORDER)
            .cause(UPDATED_CAUSE)
            .controlMeasure(UPDATED_CONTROL_MEASURE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDisorderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDisorder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDisorder))
            )
            .andExpect(status().isOk());

        // Validate the Disorder in the database
        List<Disorder> disorderList = disorderRepository.findAll();
        assertThat(disorderList).hasSize(databaseSizeBeforeUpdate);
        Disorder testDisorder = disorderList.get(disorderList.size() - 1);
        assertThat(testDisorder.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testDisorder.getPhysiologicalDisorder()).isEqualTo(UPDATED_PHYSIOLOGICAL_DISORDER);
        assertThat(testDisorder.getCause()).isEqualTo(UPDATED_CAUSE);
        assertThat(testDisorder.getControlMeasure()).isEqualTo(UPDATED_CONTROL_MEASURE);
        assertThat(testDisorder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDisorder.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDisorder.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDisorder.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingDisorder() throws Exception {
        int databaseSizeBeforeUpdate = disorderRepository.findAll().size();
        disorder.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisorderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, disorder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disorder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disorder in the database
        List<Disorder> disorderList = disorderRepository.findAll();
        assertThat(disorderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDisorder() throws Exception {
        int databaseSizeBeforeUpdate = disorderRepository.findAll().size();
        disorder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisorderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disorder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disorder in the database
        List<Disorder> disorderList = disorderRepository.findAll();
        assertThat(disorderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDisorder() throws Exception {
        int databaseSizeBeforeUpdate = disorderRepository.findAll().size();
        disorder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisorderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disorder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Disorder in the database
        List<Disorder> disorderList = disorderRepository.findAll();
        assertThat(disorderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDisorderWithPatch() throws Exception {
        // Initialize the database
        disorderRepository.saveAndFlush(disorder);

        int databaseSizeBeforeUpdate = disorderRepository.findAll().size();

        // Update the disorder using partial update
        Disorder partialUpdatedDisorder = new Disorder();
        partialUpdatedDisorder.setId(disorder.getId());

        partialUpdatedDisorder.physiologicalDisorder(UPDATED_PHYSIOLOGICAL_DISORDER);

        restDisorderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisorder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDisorder))
            )
            .andExpect(status().isOk());

        // Validate the Disorder in the database
        List<Disorder> disorderList = disorderRepository.findAll();
        assertThat(disorderList).hasSize(databaseSizeBeforeUpdate);
        Disorder testDisorder = disorderList.get(disorderList.size() - 1);
        assertThat(testDisorder.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testDisorder.getPhysiologicalDisorder()).isEqualTo(UPDATED_PHYSIOLOGICAL_DISORDER);
        assertThat(testDisorder.getCause()).isEqualTo(DEFAULT_CAUSE);
        assertThat(testDisorder.getControlMeasure()).isEqualTo(DEFAULT_CONTROL_MEASURE);
        assertThat(testDisorder.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDisorder.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDisorder.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDisorder.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateDisorderWithPatch() throws Exception {
        // Initialize the database
        disorderRepository.saveAndFlush(disorder);

        int databaseSizeBeforeUpdate = disorderRepository.findAll().size();

        // Update the disorder using partial update
        Disorder partialUpdatedDisorder = new Disorder();
        partialUpdatedDisorder.setId(disorder.getId());

        partialUpdatedDisorder
            .gUID(UPDATED_G_UID)
            .physiologicalDisorder(UPDATED_PHYSIOLOGICAL_DISORDER)
            .cause(UPDATED_CAUSE)
            .controlMeasure(UPDATED_CONTROL_MEASURE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDisorderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisorder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDisorder))
            )
            .andExpect(status().isOk());

        // Validate the Disorder in the database
        List<Disorder> disorderList = disorderRepository.findAll();
        assertThat(disorderList).hasSize(databaseSizeBeforeUpdate);
        Disorder testDisorder = disorderList.get(disorderList.size() - 1);
        assertThat(testDisorder.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testDisorder.getPhysiologicalDisorder()).isEqualTo(UPDATED_PHYSIOLOGICAL_DISORDER);
        assertThat(testDisorder.getCause()).isEqualTo(UPDATED_CAUSE);
        assertThat(testDisorder.getControlMeasure()).isEqualTo(UPDATED_CONTROL_MEASURE);
        assertThat(testDisorder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDisorder.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDisorder.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDisorder.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingDisorder() throws Exception {
        int databaseSizeBeforeUpdate = disorderRepository.findAll().size();
        disorder.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisorderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, disorder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(disorder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disorder in the database
        List<Disorder> disorderList = disorderRepository.findAll();
        assertThat(disorderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDisorder() throws Exception {
        int databaseSizeBeforeUpdate = disorderRepository.findAll().size();
        disorder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisorderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(disorder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disorder in the database
        List<Disorder> disorderList = disorderRepository.findAll();
        assertThat(disorderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDisorder() throws Exception {
        int databaseSizeBeforeUpdate = disorderRepository.findAll().size();
        disorder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisorderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(disorder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Disorder in the database
        List<Disorder> disorderList = disorderRepository.findAll();
        assertThat(disorderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDisorder() throws Exception {
        // Initialize the database
        disorderRepository.saveAndFlush(disorder);

        int databaseSizeBeforeDelete = disorderRepository.findAll().size();

        // Delete the disorder
        restDisorderMockMvc
            .perform(delete(ENTITY_API_URL_ID, disorder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Disorder> disorderList = disorderRepository.findAll();
        assertThat(disorderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
