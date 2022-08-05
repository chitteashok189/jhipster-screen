package org.jhipster.blog.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.blog.IntegrationTest;
import org.jhipster.blog.domain.EnnumerationType;
import org.jhipster.blog.repository.EnnumerationTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EnnumerationTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnnumerationTypeResourceIT {

    private static final Long DEFAULT_ENNUMERATION_TYPE = 1L;
    private static final Long UPDATED_ENNUMERATION_TYPE = 2L;

    private static final Boolean DEFAULT_HAS_TABLE = false;
    private static final Boolean UPDATED_HAS_TABLE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ENNUMERATION = "AAAAAAAAAA";
    private static final String UPDATED_ENNUMERATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ennumeration-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnnumerationTypeRepository ennumerationTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnnumerationTypeMockMvc;

    private EnnumerationType ennumerationType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnnumerationType createEntity(EntityManager em) {
        EnnumerationType ennumerationType = new EnnumerationType()
            .ennumerationType(DEFAULT_ENNUMERATION_TYPE)
            .hasTable(DEFAULT_HAS_TABLE)
            .description(DEFAULT_DESCRIPTION)
            .ennumeration(DEFAULT_ENNUMERATION);
        return ennumerationType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnnumerationType createUpdatedEntity(EntityManager em) {
        EnnumerationType ennumerationType = new EnnumerationType()
            .ennumerationType(UPDATED_ENNUMERATION_TYPE)
            .hasTable(UPDATED_HAS_TABLE)
            .description(UPDATED_DESCRIPTION)
            .ennumeration(UPDATED_ENNUMERATION);
        return ennumerationType;
    }

    @BeforeEach
    public void initTest() {
        ennumerationType = createEntity(em);
    }

    @Test
    @Transactional
    void createEnnumerationType() throws Exception {
        int databaseSizeBeforeCreate = ennumerationTypeRepository.findAll().size();
        // Create the EnnumerationType
        restEnnumerationTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ennumerationType))
            )
            .andExpect(status().isCreated());

        // Validate the EnnumerationType in the database
        List<EnnumerationType> ennumerationTypeList = ennumerationTypeRepository.findAll();
        assertThat(ennumerationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EnnumerationType testEnnumerationType = ennumerationTypeList.get(ennumerationTypeList.size() - 1);
        assertThat(testEnnumerationType.getEnnumerationType()).isEqualTo(DEFAULT_ENNUMERATION_TYPE);
        assertThat(testEnnumerationType.getHasTable()).isEqualTo(DEFAULT_HAS_TABLE);
        assertThat(testEnnumerationType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEnnumerationType.getEnnumeration()).isEqualTo(DEFAULT_ENNUMERATION);
    }

    @Test
    @Transactional
    void createEnnumerationTypeWithExistingId() throws Exception {
        // Create the EnnumerationType with an existing ID
        ennumerationType.setId(1L);

        int databaseSizeBeforeCreate = ennumerationTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnnumerationTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ennumerationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnnumerationType in the database
        List<EnnumerationType> ennumerationTypeList = ennumerationTypeRepository.findAll();
        assertThat(ennumerationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnnumerationTypes() throws Exception {
        // Initialize the database
        ennumerationTypeRepository.saveAndFlush(ennumerationType);

        // Get all the ennumerationTypeList
        restEnnumerationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ennumerationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].ennumerationType").value(hasItem(DEFAULT_ENNUMERATION_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].hasTable").value(hasItem(DEFAULT_HAS_TABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].ennumeration").value(hasItem(DEFAULT_ENNUMERATION)));
    }

    @Test
    @Transactional
    void getEnnumerationType() throws Exception {
        // Initialize the database
        ennumerationTypeRepository.saveAndFlush(ennumerationType);

        // Get the ennumerationType
        restEnnumerationTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, ennumerationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ennumerationType.getId().intValue()))
            .andExpect(jsonPath("$.ennumerationType").value(DEFAULT_ENNUMERATION_TYPE.intValue()))
            .andExpect(jsonPath("$.hasTable").value(DEFAULT_HAS_TABLE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.ennumeration").value(DEFAULT_ENNUMERATION));
    }

    @Test
    @Transactional
    void getNonExistingEnnumerationType() throws Exception {
        // Get the ennumerationType
        restEnnumerationTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEnnumerationType() throws Exception {
        // Initialize the database
        ennumerationTypeRepository.saveAndFlush(ennumerationType);

        int databaseSizeBeforeUpdate = ennumerationTypeRepository.findAll().size();

        // Update the ennumerationType
        EnnumerationType updatedEnnumerationType = ennumerationTypeRepository.findById(ennumerationType.getId()).get();
        // Disconnect from session so that the updates on updatedEnnumerationType are not directly saved in db
        em.detach(updatedEnnumerationType);
        updatedEnnumerationType
            .ennumerationType(UPDATED_ENNUMERATION_TYPE)
            .hasTable(UPDATED_HAS_TABLE)
            .description(UPDATED_DESCRIPTION)
            .ennumeration(UPDATED_ENNUMERATION);

        restEnnumerationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnnumerationType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEnnumerationType))
            )
            .andExpect(status().isOk());

        // Validate the EnnumerationType in the database
        List<EnnumerationType> ennumerationTypeList = ennumerationTypeRepository.findAll();
        assertThat(ennumerationTypeList).hasSize(databaseSizeBeforeUpdate);
        EnnumerationType testEnnumerationType = ennumerationTypeList.get(ennumerationTypeList.size() - 1);
        assertThat(testEnnumerationType.getEnnumerationType()).isEqualTo(UPDATED_ENNUMERATION_TYPE);
        assertThat(testEnnumerationType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testEnnumerationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEnnumerationType.getEnnumeration()).isEqualTo(UPDATED_ENNUMERATION);
    }

    @Test
    @Transactional
    void putNonExistingEnnumerationType() throws Exception {
        int databaseSizeBeforeUpdate = ennumerationTypeRepository.findAll().size();
        ennumerationType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnnumerationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ennumerationType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ennumerationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnnumerationType in the database
        List<EnnumerationType> ennumerationTypeList = ennumerationTypeRepository.findAll();
        assertThat(ennumerationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnnumerationType() throws Exception {
        int databaseSizeBeforeUpdate = ennumerationTypeRepository.findAll().size();
        ennumerationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnnumerationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ennumerationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnnumerationType in the database
        List<EnnumerationType> ennumerationTypeList = ennumerationTypeRepository.findAll();
        assertThat(ennumerationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnnumerationType() throws Exception {
        int databaseSizeBeforeUpdate = ennumerationTypeRepository.findAll().size();
        ennumerationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnnumerationTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ennumerationType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnnumerationType in the database
        List<EnnumerationType> ennumerationTypeList = ennumerationTypeRepository.findAll();
        assertThat(ennumerationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnnumerationTypeWithPatch() throws Exception {
        // Initialize the database
        ennumerationTypeRepository.saveAndFlush(ennumerationType);

        int databaseSizeBeforeUpdate = ennumerationTypeRepository.findAll().size();

        // Update the ennumerationType using partial update
        EnnumerationType partialUpdatedEnnumerationType = new EnnumerationType();
        partialUpdatedEnnumerationType.setId(ennumerationType.getId());

        partialUpdatedEnnumerationType.hasTable(UPDATED_HAS_TABLE).description(UPDATED_DESCRIPTION);

        restEnnumerationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnnumerationType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnnumerationType))
            )
            .andExpect(status().isOk());

        // Validate the EnnumerationType in the database
        List<EnnumerationType> ennumerationTypeList = ennumerationTypeRepository.findAll();
        assertThat(ennumerationTypeList).hasSize(databaseSizeBeforeUpdate);
        EnnumerationType testEnnumerationType = ennumerationTypeList.get(ennumerationTypeList.size() - 1);
        assertThat(testEnnumerationType.getEnnumerationType()).isEqualTo(DEFAULT_ENNUMERATION_TYPE);
        assertThat(testEnnumerationType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testEnnumerationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEnnumerationType.getEnnumeration()).isEqualTo(DEFAULT_ENNUMERATION);
    }

    @Test
    @Transactional
    void fullUpdateEnnumerationTypeWithPatch() throws Exception {
        // Initialize the database
        ennumerationTypeRepository.saveAndFlush(ennumerationType);

        int databaseSizeBeforeUpdate = ennumerationTypeRepository.findAll().size();

        // Update the ennumerationType using partial update
        EnnumerationType partialUpdatedEnnumerationType = new EnnumerationType();
        partialUpdatedEnnumerationType.setId(ennumerationType.getId());

        partialUpdatedEnnumerationType
            .ennumerationType(UPDATED_ENNUMERATION_TYPE)
            .hasTable(UPDATED_HAS_TABLE)
            .description(UPDATED_DESCRIPTION)
            .ennumeration(UPDATED_ENNUMERATION);

        restEnnumerationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnnumerationType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnnumerationType))
            )
            .andExpect(status().isOk());

        // Validate the EnnumerationType in the database
        List<EnnumerationType> ennumerationTypeList = ennumerationTypeRepository.findAll();
        assertThat(ennumerationTypeList).hasSize(databaseSizeBeforeUpdate);
        EnnumerationType testEnnumerationType = ennumerationTypeList.get(ennumerationTypeList.size() - 1);
        assertThat(testEnnumerationType.getEnnumerationType()).isEqualTo(UPDATED_ENNUMERATION_TYPE);
        assertThat(testEnnumerationType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testEnnumerationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEnnumerationType.getEnnumeration()).isEqualTo(UPDATED_ENNUMERATION);
    }

    @Test
    @Transactional
    void patchNonExistingEnnumerationType() throws Exception {
        int databaseSizeBeforeUpdate = ennumerationTypeRepository.findAll().size();
        ennumerationType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnnumerationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ennumerationType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ennumerationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnnumerationType in the database
        List<EnnumerationType> ennumerationTypeList = ennumerationTypeRepository.findAll();
        assertThat(ennumerationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnnumerationType() throws Exception {
        int databaseSizeBeforeUpdate = ennumerationTypeRepository.findAll().size();
        ennumerationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnnumerationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ennumerationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnnumerationType in the database
        List<EnnumerationType> ennumerationTypeList = ennumerationTypeRepository.findAll();
        assertThat(ennumerationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnnumerationType() throws Exception {
        int databaseSizeBeforeUpdate = ennumerationTypeRepository.findAll().size();
        ennumerationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnnumerationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ennumerationType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnnumerationType in the database
        List<EnnumerationType> ennumerationTypeList = ennumerationTypeRepository.findAll();
        assertThat(ennumerationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnnumerationType() throws Exception {
        // Initialize the database
        ennumerationTypeRepository.saveAndFlush(ennumerationType);

        int databaseSizeBeforeDelete = ennumerationTypeRepository.findAll().size();

        // Delete the ennumerationType
        restEnnumerationTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, ennumerationType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnnumerationType> ennumerationTypeList = ennumerationTypeRepository.findAll();
        assertThat(ennumerationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
