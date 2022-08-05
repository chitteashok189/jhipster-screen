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
import org.jhipster.blog.domain.PestControl;
import org.jhipster.blog.domain.enumeration.ConType;
import org.jhipster.blog.repository.PestControlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PestControlResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PestControlResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_NATURE_OF_DAMAGE = "AAAAAAAAAA";
    private static final String UPDATED_NATURE_OF_DAMAGE = "BBBBBBBBBB";

    private static final ConType DEFAULT_CONTROL_TYPE = ConType.Biological;
    private static final ConType UPDATED_CONTROL_TYPE = ConType.Mechanical;

    private static final String DEFAULT_CONTROL_MEASURES = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL_MEASURES = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/pest-controls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PestControlRepository pestControlRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPestControlMockMvc;

    private PestControl pestControl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PestControl createEntity(EntityManager em) {
        PestControl pestControl = new PestControl()
            .gUID(DEFAULT_G_UID)
            .natureOfDamage(DEFAULT_NATURE_OF_DAMAGE)
            .controlType(DEFAULT_CONTROL_TYPE)
            .controlMeasures(DEFAULT_CONTROL_MEASURES)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return pestControl;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PestControl createUpdatedEntity(EntityManager em) {
        PestControl pestControl = new PestControl()
            .gUID(UPDATED_G_UID)
            .natureOfDamage(UPDATED_NATURE_OF_DAMAGE)
            .controlType(UPDATED_CONTROL_TYPE)
            .controlMeasures(UPDATED_CONTROL_MEASURES)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return pestControl;
    }

    @BeforeEach
    public void initTest() {
        pestControl = createEntity(em);
    }

    @Test
    @Transactional
    void createPestControl() throws Exception {
        int databaseSizeBeforeCreate = pestControlRepository.findAll().size();
        // Create the PestControl
        restPestControlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pestControl)))
            .andExpect(status().isCreated());

        // Validate the PestControl in the database
        List<PestControl> pestControlList = pestControlRepository.findAll();
        assertThat(pestControlList).hasSize(databaseSizeBeforeCreate + 1);
        PestControl testPestControl = pestControlList.get(pestControlList.size() - 1);
        assertThat(testPestControl.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPestControl.getNatureOfDamage()).isEqualTo(DEFAULT_NATURE_OF_DAMAGE);
        assertThat(testPestControl.getControlType()).isEqualTo(DEFAULT_CONTROL_TYPE);
        assertThat(testPestControl.getControlMeasures()).isEqualTo(DEFAULT_CONTROL_MEASURES);
        assertThat(testPestControl.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPestControl.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPestControl.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPestControl.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPestControlWithExistingId() throws Exception {
        // Create the PestControl with an existing ID
        pestControl.setId(1L);

        int databaseSizeBeforeCreate = pestControlRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPestControlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pestControl)))
            .andExpect(status().isBadRequest());

        // Validate the PestControl in the database
        List<PestControl> pestControlList = pestControlRepository.findAll();
        assertThat(pestControlList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPestControls() throws Exception {
        // Initialize the database
        pestControlRepository.saveAndFlush(pestControl);

        // Get all the pestControlList
        restPestControlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pestControl.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].natureOfDamage").value(hasItem(DEFAULT_NATURE_OF_DAMAGE)))
            .andExpect(jsonPath("$.[*].controlType").value(hasItem(DEFAULT_CONTROL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].controlMeasures").value(hasItem(DEFAULT_CONTROL_MEASURES)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPestControl() throws Exception {
        // Initialize the database
        pestControlRepository.saveAndFlush(pestControl);

        // Get the pestControl
        restPestControlMockMvc
            .perform(get(ENTITY_API_URL_ID, pestControl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pestControl.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.natureOfDamage").value(DEFAULT_NATURE_OF_DAMAGE))
            .andExpect(jsonPath("$.controlType").value(DEFAULT_CONTROL_TYPE.toString()))
            .andExpect(jsonPath("$.controlMeasures").value(DEFAULT_CONTROL_MEASURES))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPestControl() throws Exception {
        // Get the pestControl
        restPestControlMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPestControl() throws Exception {
        // Initialize the database
        pestControlRepository.saveAndFlush(pestControl);

        int databaseSizeBeforeUpdate = pestControlRepository.findAll().size();

        // Update the pestControl
        PestControl updatedPestControl = pestControlRepository.findById(pestControl.getId()).get();
        // Disconnect from session so that the updates on updatedPestControl are not directly saved in db
        em.detach(updatedPestControl);
        updatedPestControl
            .gUID(UPDATED_G_UID)
            .natureOfDamage(UPDATED_NATURE_OF_DAMAGE)
            .controlType(UPDATED_CONTROL_TYPE)
            .controlMeasures(UPDATED_CONTROL_MEASURES)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPestControlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPestControl.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPestControl))
            )
            .andExpect(status().isOk());

        // Validate the PestControl in the database
        List<PestControl> pestControlList = pestControlRepository.findAll();
        assertThat(pestControlList).hasSize(databaseSizeBeforeUpdate);
        PestControl testPestControl = pestControlList.get(pestControlList.size() - 1);
        assertThat(testPestControl.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPestControl.getNatureOfDamage()).isEqualTo(UPDATED_NATURE_OF_DAMAGE);
        assertThat(testPestControl.getControlType()).isEqualTo(UPDATED_CONTROL_TYPE);
        assertThat(testPestControl.getControlMeasures()).isEqualTo(UPDATED_CONTROL_MEASURES);
        assertThat(testPestControl.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPestControl.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPestControl.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPestControl.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPestControl() throws Exception {
        int databaseSizeBeforeUpdate = pestControlRepository.findAll().size();
        pestControl.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPestControlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pestControl.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pestControl))
            )
            .andExpect(status().isBadRequest());

        // Validate the PestControl in the database
        List<PestControl> pestControlList = pestControlRepository.findAll();
        assertThat(pestControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPestControl() throws Exception {
        int databaseSizeBeforeUpdate = pestControlRepository.findAll().size();
        pestControl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPestControlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pestControl))
            )
            .andExpect(status().isBadRequest());

        // Validate the PestControl in the database
        List<PestControl> pestControlList = pestControlRepository.findAll();
        assertThat(pestControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPestControl() throws Exception {
        int databaseSizeBeforeUpdate = pestControlRepository.findAll().size();
        pestControl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPestControlMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pestControl)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PestControl in the database
        List<PestControl> pestControlList = pestControlRepository.findAll();
        assertThat(pestControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePestControlWithPatch() throws Exception {
        // Initialize the database
        pestControlRepository.saveAndFlush(pestControl);

        int databaseSizeBeforeUpdate = pestControlRepository.findAll().size();

        // Update the pestControl using partial update
        PestControl partialUpdatedPestControl = new PestControl();
        partialUpdatedPestControl.setId(pestControl.getId());

        partialUpdatedPestControl.natureOfDamage(UPDATED_NATURE_OF_DAMAGE).controlType(UPDATED_CONTROL_TYPE).createdOn(UPDATED_CREATED_ON);

        restPestControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPestControl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPestControl))
            )
            .andExpect(status().isOk());

        // Validate the PestControl in the database
        List<PestControl> pestControlList = pestControlRepository.findAll();
        assertThat(pestControlList).hasSize(databaseSizeBeforeUpdate);
        PestControl testPestControl = pestControlList.get(pestControlList.size() - 1);
        assertThat(testPestControl.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPestControl.getNatureOfDamage()).isEqualTo(UPDATED_NATURE_OF_DAMAGE);
        assertThat(testPestControl.getControlType()).isEqualTo(UPDATED_CONTROL_TYPE);
        assertThat(testPestControl.getControlMeasures()).isEqualTo(DEFAULT_CONTROL_MEASURES);
        assertThat(testPestControl.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPestControl.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPestControl.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPestControl.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePestControlWithPatch() throws Exception {
        // Initialize the database
        pestControlRepository.saveAndFlush(pestControl);

        int databaseSizeBeforeUpdate = pestControlRepository.findAll().size();

        // Update the pestControl using partial update
        PestControl partialUpdatedPestControl = new PestControl();
        partialUpdatedPestControl.setId(pestControl.getId());

        partialUpdatedPestControl
            .gUID(UPDATED_G_UID)
            .natureOfDamage(UPDATED_NATURE_OF_DAMAGE)
            .controlType(UPDATED_CONTROL_TYPE)
            .controlMeasures(UPDATED_CONTROL_MEASURES)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPestControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPestControl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPestControl))
            )
            .andExpect(status().isOk());

        // Validate the PestControl in the database
        List<PestControl> pestControlList = pestControlRepository.findAll();
        assertThat(pestControlList).hasSize(databaseSizeBeforeUpdate);
        PestControl testPestControl = pestControlList.get(pestControlList.size() - 1);
        assertThat(testPestControl.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPestControl.getNatureOfDamage()).isEqualTo(UPDATED_NATURE_OF_DAMAGE);
        assertThat(testPestControl.getControlType()).isEqualTo(UPDATED_CONTROL_TYPE);
        assertThat(testPestControl.getControlMeasures()).isEqualTo(UPDATED_CONTROL_MEASURES);
        assertThat(testPestControl.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPestControl.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPestControl.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPestControl.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPestControl() throws Exception {
        int databaseSizeBeforeUpdate = pestControlRepository.findAll().size();
        pestControl.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPestControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pestControl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pestControl))
            )
            .andExpect(status().isBadRequest());

        // Validate the PestControl in the database
        List<PestControl> pestControlList = pestControlRepository.findAll();
        assertThat(pestControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPestControl() throws Exception {
        int databaseSizeBeforeUpdate = pestControlRepository.findAll().size();
        pestControl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPestControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pestControl))
            )
            .andExpect(status().isBadRequest());

        // Validate the PestControl in the database
        List<PestControl> pestControlList = pestControlRepository.findAll();
        assertThat(pestControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPestControl() throws Exception {
        int databaseSizeBeforeUpdate = pestControlRepository.findAll().size();
        pestControl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPestControlMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pestControl))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PestControl in the database
        List<PestControl> pestControlList = pestControlRepository.findAll();
        assertThat(pestControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePestControl() throws Exception {
        // Initialize the database
        pestControlRepository.saveAndFlush(pestControl);

        int databaseSizeBeforeDelete = pestControlRepository.findAll().size();

        // Delete the pestControl
        restPestControlMockMvc
            .perform(delete(ENTITY_API_URL_ID, pestControl.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PestControl> pestControlList = pestControlRepository.findAll();
        assertThat(pestControlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
