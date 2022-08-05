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
import org.jhipster.blog.domain.DiseaseControl;
import org.jhipster.blog.domain.enumeration.DisConType;
import org.jhipster.blog.repository.DiseaseControlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DiseaseControlResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiseaseControlResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final DisConType DEFAULT_CONTROL_TYPE = DisConType.Biological;
    private static final DisConType UPDATED_CONTROL_TYPE = DisConType.Mechanical;

    private static final String DEFAULT_TREATMENT = "AAAAAAAAAA";
    private static final String UPDATED_TREATMENT = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/disease-controls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DiseaseControlRepository diseaseControlRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiseaseControlMockMvc;

    private DiseaseControl diseaseControl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiseaseControl createEntity(EntityManager em) {
        DiseaseControl diseaseControl = new DiseaseControl()
            .gUID(DEFAULT_G_UID)
            .controlType(DEFAULT_CONTROL_TYPE)
            .treatment(DEFAULT_TREATMENT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return diseaseControl;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiseaseControl createUpdatedEntity(EntityManager em) {
        DiseaseControl diseaseControl = new DiseaseControl()
            .gUID(UPDATED_G_UID)
            .controlType(UPDATED_CONTROL_TYPE)
            .treatment(UPDATED_TREATMENT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return diseaseControl;
    }

    @BeforeEach
    public void initTest() {
        diseaseControl = createEntity(em);
    }

    @Test
    @Transactional
    void createDiseaseControl() throws Exception {
        int databaseSizeBeforeCreate = diseaseControlRepository.findAll().size();
        // Create the DiseaseControl
        restDiseaseControlMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diseaseControl))
            )
            .andExpect(status().isCreated());

        // Validate the DiseaseControl in the database
        List<DiseaseControl> diseaseControlList = diseaseControlRepository.findAll();
        assertThat(diseaseControlList).hasSize(databaseSizeBeforeCreate + 1);
        DiseaseControl testDiseaseControl = diseaseControlList.get(diseaseControlList.size() - 1);
        assertThat(testDiseaseControl.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testDiseaseControl.getControlType()).isEqualTo(DEFAULT_CONTROL_TYPE);
        assertThat(testDiseaseControl.getTreatment()).isEqualTo(DEFAULT_TREATMENT);
        assertThat(testDiseaseControl.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDiseaseControl.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDiseaseControl.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDiseaseControl.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createDiseaseControlWithExistingId() throws Exception {
        // Create the DiseaseControl with an existing ID
        diseaseControl.setId(1L);

        int databaseSizeBeforeCreate = diseaseControlRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiseaseControlMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diseaseControl))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiseaseControl in the database
        List<DiseaseControl> diseaseControlList = diseaseControlRepository.findAll();
        assertThat(diseaseControlList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDiseaseControls() throws Exception {
        // Initialize the database
        diseaseControlRepository.saveAndFlush(diseaseControl);

        // Get all the diseaseControlList
        restDiseaseControlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diseaseControl.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].controlType").value(hasItem(DEFAULT_CONTROL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].treatment").value(hasItem(DEFAULT_TREATMENT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getDiseaseControl() throws Exception {
        // Initialize the database
        diseaseControlRepository.saveAndFlush(diseaseControl);

        // Get the diseaseControl
        restDiseaseControlMockMvc
            .perform(get(ENTITY_API_URL_ID, diseaseControl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diseaseControl.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.controlType").value(DEFAULT_CONTROL_TYPE.toString()))
            .andExpect(jsonPath("$.treatment").value(DEFAULT_TREATMENT))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingDiseaseControl() throws Exception {
        // Get the diseaseControl
        restDiseaseControlMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDiseaseControl() throws Exception {
        // Initialize the database
        diseaseControlRepository.saveAndFlush(diseaseControl);

        int databaseSizeBeforeUpdate = diseaseControlRepository.findAll().size();

        // Update the diseaseControl
        DiseaseControl updatedDiseaseControl = diseaseControlRepository.findById(diseaseControl.getId()).get();
        // Disconnect from session so that the updates on updatedDiseaseControl are not directly saved in db
        em.detach(updatedDiseaseControl);
        updatedDiseaseControl
            .gUID(UPDATED_G_UID)
            .controlType(UPDATED_CONTROL_TYPE)
            .treatment(UPDATED_TREATMENT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDiseaseControlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDiseaseControl.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDiseaseControl))
            )
            .andExpect(status().isOk());

        // Validate the DiseaseControl in the database
        List<DiseaseControl> diseaseControlList = diseaseControlRepository.findAll();
        assertThat(diseaseControlList).hasSize(databaseSizeBeforeUpdate);
        DiseaseControl testDiseaseControl = diseaseControlList.get(diseaseControlList.size() - 1);
        assertThat(testDiseaseControl.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testDiseaseControl.getControlType()).isEqualTo(UPDATED_CONTROL_TYPE);
        assertThat(testDiseaseControl.getTreatment()).isEqualTo(UPDATED_TREATMENT);
        assertThat(testDiseaseControl.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDiseaseControl.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDiseaseControl.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDiseaseControl.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingDiseaseControl() throws Exception {
        int databaseSizeBeforeUpdate = diseaseControlRepository.findAll().size();
        diseaseControl.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiseaseControlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diseaseControl.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diseaseControl))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiseaseControl in the database
        List<DiseaseControl> diseaseControlList = diseaseControlRepository.findAll();
        assertThat(diseaseControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiseaseControl() throws Exception {
        int databaseSizeBeforeUpdate = diseaseControlRepository.findAll().size();
        diseaseControl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiseaseControlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diseaseControl))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiseaseControl in the database
        List<DiseaseControl> diseaseControlList = diseaseControlRepository.findAll();
        assertThat(diseaseControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiseaseControl() throws Exception {
        int databaseSizeBeforeUpdate = diseaseControlRepository.findAll().size();
        diseaseControl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiseaseControlMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diseaseControl)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiseaseControl in the database
        List<DiseaseControl> diseaseControlList = diseaseControlRepository.findAll();
        assertThat(diseaseControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiseaseControlWithPatch() throws Exception {
        // Initialize the database
        diseaseControlRepository.saveAndFlush(diseaseControl);

        int databaseSizeBeforeUpdate = diseaseControlRepository.findAll().size();

        // Update the diseaseControl using partial update
        DiseaseControl partialUpdatedDiseaseControl = new DiseaseControl();
        partialUpdatedDiseaseControl.setId(diseaseControl.getId());

        partialUpdatedDiseaseControl
            .gUID(UPDATED_G_UID)
            .controlType(UPDATED_CONTROL_TYPE)
            .treatment(UPDATED_TREATMENT)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDiseaseControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiseaseControl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiseaseControl))
            )
            .andExpect(status().isOk());

        // Validate the DiseaseControl in the database
        List<DiseaseControl> diseaseControlList = diseaseControlRepository.findAll();
        assertThat(diseaseControlList).hasSize(databaseSizeBeforeUpdate);
        DiseaseControl testDiseaseControl = diseaseControlList.get(diseaseControlList.size() - 1);
        assertThat(testDiseaseControl.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testDiseaseControl.getControlType()).isEqualTo(UPDATED_CONTROL_TYPE);
        assertThat(testDiseaseControl.getTreatment()).isEqualTo(UPDATED_TREATMENT);
        assertThat(testDiseaseControl.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDiseaseControl.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDiseaseControl.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDiseaseControl.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateDiseaseControlWithPatch() throws Exception {
        // Initialize the database
        diseaseControlRepository.saveAndFlush(diseaseControl);

        int databaseSizeBeforeUpdate = diseaseControlRepository.findAll().size();

        // Update the diseaseControl using partial update
        DiseaseControl partialUpdatedDiseaseControl = new DiseaseControl();
        partialUpdatedDiseaseControl.setId(diseaseControl.getId());

        partialUpdatedDiseaseControl
            .gUID(UPDATED_G_UID)
            .controlType(UPDATED_CONTROL_TYPE)
            .treatment(UPDATED_TREATMENT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDiseaseControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiseaseControl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiseaseControl))
            )
            .andExpect(status().isOk());

        // Validate the DiseaseControl in the database
        List<DiseaseControl> diseaseControlList = diseaseControlRepository.findAll();
        assertThat(diseaseControlList).hasSize(databaseSizeBeforeUpdate);
        DiseaseControl testDiseaseControl = diseaseControlList.get(diseaseControlList.size() - 1);
        assertThat(testDiseaseControl.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testDiseaseControl.getControlType()).isEqualTo(UPDATED_CONTROL_TYPE);
        assertThat(testDiseaseControl.getTreatment()).isEqualTo(UPDATED_TREATMENT);
        assertThat(testDiseaseControl.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDiseaseControl.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDiseaseControl.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDiseaseControl.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingDiseaseControl() throws Exception {
        int databaseSizeBeforeUpdate = diseaseControlRepository.findAll().size();
        diseaseControl.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiseaseControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, diseaseControl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diseaseControl))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiseaseControl in the database
        List<DiseaseControl> diseaseControlList = diseaseControlRepository.findAll();
        assertThat(diseaseControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiseaseControl() throws Exception {
        int databaseSizeBeforeUpdate = diseaseControlRepository.findAll().size();
        diseaseControl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiseaseControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diseaseControl))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiseaseControl in the database
        List<DiseaseControl> diseaseControlList = diseaseControlRepository.findAll();
        assertThat(diseaseControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiseaseControl() throws Exception {
        int databaseSizeBeforeUpdate = diseaseControlRepository.findAll().size();
        diseaseControl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiseaseControlMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(diseaseControl))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiseaseControl in the database
        List<DiseaseControl> diseaseControlList = diseaseControlRepository.findAll();
        assertThat(diseaseControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiseaseControl() throws Exception {
        // Initialize the database
        diseaseControlRepository.saveAndFlush(diseaseControl);

        int databaseSizeBeforeDelete = diseaseControlRepository.findAll().size();

        // Delete the diseaseControl
        restDiseaseControlMockMvc
            .perform(delete(ENTITY_API_URL_ID, diseaseControl.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiseaseControl> diseaseControlList = diseaseControlRepository.findAll();
        assertThat(diseaseControlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
