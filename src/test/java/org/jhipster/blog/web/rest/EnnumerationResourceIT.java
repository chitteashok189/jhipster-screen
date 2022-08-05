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
import org.jhipster.blog.domain.Ennumeration;
import org.jhipster.blog.repository.EnnumerationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EnnumerationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnnumerationResourceIT {

    private static final Integer DEFAULT_ENUM_CODE = 1;
    private static final Integer UPDATED_ENUM_CODE = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ennumerations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnnumerationRepository ennumerationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnnumerationMockMvc;

    private Ennumeration ennumeration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ennumeration createEntity(EntityManager em) {
        Ennumeration ennumeration = new Ennumeration().enumCode(DEFAULT_ENUM_CODE).description(DEFAULT_DESCRIPTION);
        return ennumeration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ennumeration createUpdatedEntity(EntityManager em) {
        Ennumeration ennumeration = new Ennumeration().enumCode(UPDATED_ENUM_CODE).description(UPDATED_DESCRIPTION);
        return ennumeration;
    }

    @BeforeEach
    public void initTest() {
        ennumeration = createEntity(em);
    }

    @Test
    @Transactional
    void createEnnumeration() throws Exception {
        int databaseSizeBeforeCreate = ennumerationRepository.findAll().size();
        // Create the Ennumeration
        restEnnumerationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ennumeration)))
            .andExpect(status().isCreated());

        // Validate the Ennumeration in the database
        List<Ennumeration> ennumerationList = ennumerationRepository.findAll();
        assertThat(ennumerationList).hasSize(databaseSizeBeforeCreate + 1);
        Ennumeration testEnnumeration = ennumerationList.get(ennumerationList.size() - 1);
        assertThat(testEnnumeration.getEnumCode()).isEqualTo(DEFAULT_ENUM_CODE);
        assertThat(testEnnumeration.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createEnnumerationWithExistingId() throws Exception {
        // Create the Ennumeration with an existing ID
        ennumeration.setId(1L);

        int databaseSizeBeforeCreate = ennumerationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnnumerationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ennumeration)))
            .andExpect(status().isBadRequest());

        // Validate the Ennumeration in the database
        List<Ennumeration> ennumerationList = ennumerationRepository.findAll();
        assertThat(ennumerationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnnumerations() throws Exception {
        // Initialize the database
        ennumerationRepository.saveAndFlush(ennumeration);

        // Get all the ennumerationList
        restEnnumerationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ennumeration.getId().intValue())))
            .andExpect(jsonPath("$.[*].enumCode").value(hasItem(DEFAULT_ENUM_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getEnnumeration() throws Exception {
        // Initialize the database
        ennumerationRepository.saveAndFlush(ennumeration);

        // Get the ennumeration
        restEnnumerationMockMvc
            .perform(get(ENTITY_API_URL_ID, ennumeration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ennumeration.getId().intValue()))
            .andExpect(jsonPath("$.enumCode").value(DEFAULT_ENUM_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingEnnumeration() throws Exception {
        // Get the ennumeration
        restEnnumerationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEnnumeration() throws Exception {
        // Initialize the database
        ennumerationRepository.saveAndFlush(ennumeration);

        int databaseSizeBeforeUpdate = ennumerationRepository.findAll().size();

        // Update the ennumeration
        Ennumeration updatedEnnumeration = ennumerationRepository.findById(ennumeration.getId()).get();
        // Disconnect from session so that the updates on updatedEnnumeration are not directly saved in db
        em.detach(updatedEnnumeration);
        updatedEnnumeration.enumCode(UPDATED_ENUM_CODE).description(UPDATED_DESCRIPTION);

        restEnnumerationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnnumeration.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEnnumeration))
            )
            .andExpect(status().isOk());

        // Validate the Ennumeration in the database
        List<Ennumeration> ennumerationList = ennumerationRepository.findAll();
        assertThat(ennumerationList).hasSize(databaseSizeBeforeUpdate);
        Ennumeration testEnnumeration = ennumerationList.get(ennumerationList.size() - 1);
        assertThat(testEnnumeration.getEnumCode()).isEqualTo(UPDATED_ENUM_CODE);
        assertThat(testEnnumeration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingEnnumeration() throws Exception {
        int databaseSizeBeforeUpdate = ennumerationRepository.findAll().size();
        ennumeration.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnnumerationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ennumeration.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ennumeration))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ennumeration in the database
        List<Ennumeration> ennumerationList = ennumerationRepository.findAll();
        assertThat(ennumerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnnumeration() throws Exception {
        int databaseSizeBeforeUpdate = ennumerationRepository.findAll().size();
        ennumeration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnnumerationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ennumeration))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ennumeration in the database
        List<Ennumeration> ennumerationList = ennumerationRepository.findAll();
        assertThat(ennumerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnnumeration() throws Exception {
        int databaseSizeBeforeUpdate = ennumerationRepository.findAll().size();
        ennumeration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnnumerationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ennumeration)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ennumeration in the database
        List<Ennumeration> ennumerationList = ennumerationRepository.findAll();
        assertThat(ennumerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnnumerationWithPatch() throws Exception {
        // Initialize the database
        ennumerationRepository.saveAndFlush(ennumeration);

        int databaseSizeBeforeUpdate = ennumerationRepository.findAll().size();

        // Update the ennumeration using partial update
        Ennumeration partialUpdatedEnnumeration = new Ennumeration();
        partialUpdatedEnnumeration.setId(ennumeration.getId());

        partialUpdatedEnnumeration.description(UPDATED_DESCRIPTION);

        restEnnumerationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnnumeration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnnumeration))
            )
            .andExpect(status().isOk());

        // Validate the Ennumeration in the database
        List<Ennumeration> ennumerationList = ennumerationRepository.findAll();
        assertThat(ennumerationList).hasSize(databaseSizeBeforeUpdate);
        Ennumeration testEnnumeration = ennumerationList.get(ennumerationList.size() - 1);
        assertThat(testEnnumeration.getEnumCode()).isEqualTo(DEFAULT_ENUM_CODE);
        assertThat(testEnnumeration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateEnnumerationWithPatch() throws Exception {
        // Initialize the database
        ennumerationRepository.saveAndFlush(ennumeration);

        int databaseSizeBeforeUpdate = ennumerationRepository.findAll().size();

        // Update the ennumeration using partial update
        Ennumeration partialUpdatedEnnumeration = new Ennumeration();
        partialUpdatedEnnumeration.setId(ennumeration.getId());

        partialUpdatedEnnumeration.enumCode(UPDATED_ENUM_CODE).description(UPDATED_DESCRIPTION);

        restEnnumerationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnnumeration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnnumeration))
            )
            .andExpect(status().isOk());

        // Validate the Ennumeration in the database
        List<Ennumeration> ennumerationList = ennumerationRepository.findAll();
        assertThat(ennumerationList).hasSize(databaseSizeBeforeUpdate);
        Ennumeration testEnnumeration = ennumerationList.get(ennumerationList.size() - 1);
        assertThat(testEnnumeration.getEnumCode()).isEqualTo(UPDATED_ENUM_CODE);
        assertThat(testEnnumeration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingEnnumeration() throws Exception {
        int databaseSizeBeforeUpdate = ennumerationRepository.findAll().size();
        ennumeration.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnnumerationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ennumeration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ennumeration))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ennumeration in the database
        List<Ennumeration> ennumerationList = ennumerationRepository.findAll();
        assertThat(ennumerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnnumeration() throws Exception {
        int databaseSizeBeforeUpdate = ennumerationRepository.findAll().size();
        ennumeration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnnumerationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ennumeration))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ennumeration in the database
        List<Ennumeration> ennumerationList = ennumerationRepository.findAll();
        assertThat(ennumerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnnumeration() throws Exception {
        int databaseSizeBeforeUpdate = ennumerationRepository.findAll().size();
        ennumeration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnnumerationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ennumeration))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ennumeration in the database
        List<Ennumeration> ennumerationList = ennumerationRepository.findAll();
        assertThat(ennumerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnnumeration() throws Exception {
        // Initialize the database
        ennumerationRepository.saveAndFlush(ennumeration);

        int databaseSizeBeforeDelete = ennumerationRepository.findAll().size();

        // Delete the ennumeration
        restEnnumerationMockMvc
            .perform(delete(ENTITY_API_URL_ID, ennumeration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ennumeration> ennumerationList = ennumerationRepository.findAll();
        assertThat(ennumerationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
