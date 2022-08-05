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
import org.jhipster.blog.domain.Symptom;
import org.jhipster.blog.repository.SymptomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link SymptomResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SymptomResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SYMPTOM_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SYMPTOM_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SYMPTOM_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SYMPTOM_IMAGE_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/symptoms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSymptomMockMvc;

    private Symptom symptom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Symptom createEntity(EntityManager em) {
        Symptom symptom = new Symptom()
            .gUID(DEFAULT_G_UID)
            .observation(DEFAULT_OBSERVATION)
            .symptomImage(DEFAULT_SYMPTOM_IMAGE)
            .symptomImageContentType(DEFAULT_SYMPTOM_IMAGE_CONTENT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return symptom;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Symptom createUpdatedEntity(EntityManager em) {
        Symptom symptom = new Symptom()
            .gUID(UPDATED_G_UID)
            .observation(UPDATED_OBSERVATION)
            .symptomImage(UPDATED_SYMPTOM_IMAGE)
            .symptomImageContentType(UPDATED_SYMPTOM_IMAGE_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return symptom;
    }

    @BeforeEach
    public void initTest() {
        symptom = createEntity(em);
    }

    @Test
    @Transactional
    void createSymptom() throws Exception {
        int databaseSizeBeforeCreate = symptomRepository.findAll().size();
        // Create the Symptom
        restSymptomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(symptom)))
            .andExpect(status().isCreated());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeCreate + 1);
        Symptom testSymptom = symptomList.get(symptomList.size() - 1);
        assertThat(testSymptom.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testSymptom.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testSymptom.getSymptomImage()).isEqualTo(DEFAULT_SYMPTOM_IMAGE);
        assertThat(testSymptom.getSymptomImageContentType()).isEqualTo(DEFAULT_SYMPTOM_IMAGE_CONTENT_TYPE);
        assertThat(testSymptom.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSymptom.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSymptom.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSymptom.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createSymptomWithExistingId() throws Exception {
        // Create the Symptom with an existing ID
        symptom.setId(1L);

        int databaseSizeBeforeCreate = symptomRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSymptomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(symptom)))
            .andExpect(status().isBadRequest());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSymptoms() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList
        restSymptomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(symptom.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].symptomImageContentType").value(hasItem(DEFAULT_SYMPTOM_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].symptomImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_SYMPTOM_IMAGE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getSymptom() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get the symptom
        restSymptomMockMvc
            .perform(get(ENTITY_API_URL_ID, symptom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(symptom.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION))
            .andExpect(jsonPath("$.symptomImageContentType").value(DEFAULT_SYMPTOM_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.symptomImage").value(Base64Utils.encodeToString(DEFAULT_SYMPTOM_IMAGE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingSymptom() throws Exception {
        // Get the symptom
        restSymptomMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSymptom() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();

        // Update the symptom
        Symptom updatedSymptom = symptomRepository.findById(symptom.getId()).get();
        // Disconnect from session so that the updates on updatedSymptom are not directly saved in db
        em.detach(updatedSymptom);
        updatedSymptom
            .gUID(UPDATED_G_UID)
            .observation(UPDATED_OBSERVATION)
            .symptomImage(UPDATED_SYMPTOM_IMAGE)
            .symptomImageContentType(UPDATED_SYMPTOM_IMAGE_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSymptomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSymptom.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSymptom))
            )
            .andExpect(status().isOk());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
        Symptom testSymptom = symptomList.get(symptomList.size() - 1);
        assertThat(testSymptom.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSymptom.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testSymptom.getSymptomImage()).isEqualTo(UPDATED_SYMPTOM_IMAGE);
        assertThat(testSymptom.getSymptomImageContentType()).isEqualTo(UPDATED_SYMPTOM_IMAGE_CONTENT_TYPE);
        assertThat(testSymptom.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSymptom.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSymptom.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSymptom.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingSymptom() throws Exception {
        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();
        symptom.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSymptomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, symptom.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(symptom))
            )
            .andExpect(status().isBadRequest());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSymptom() throws Exception {
        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();
        symptom.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSymptomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(symptom))
            )
            .andExpect(status().isBadRequest());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSymptom() throws Exception {
        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();
        symptom.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSymptomMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(symptom)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSymptomWithPatch() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();

        // Update the symptom using partial update
        Symptom partialUpdatedSymptom = new Symptom();
        partialUpdatedSymptom.setId(symptom.getId());

        partialUpdatedSymptom
            .gUID(UPDATED_G_UID)
            .observation(UPDATED_OBSERVATION)
            .symptomImage(UPDATED_SYMPTOM_IMAGE)
            .symptomImageContentType(UPDATED_SYMPTOM_IMAGE_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON);

        restSymptomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSymptom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSymptom))
            )
            .andExpect(status().isOk());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
        Symptom testSymptom = symptomList.get(symptomList.size() - 1);
        assertThat(testSymptom.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSymptom.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testSymptom.getSymptomImage()).isEqualTo(UPDATED_SYMPTOM_IMAGE);
        assertThat(testSymptom.getSymptomImageContentType()).isEqualTo(UPDATED_SYMPTOM_IMAGE_CONTENT_TYPE);
        assertThat(testSymptom.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSymptom.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSymptom.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSymptom.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateSymptomWithPatch() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();

        // Update the symptom using partial update
        Symptom partialUpdatedSymptom = new Symptom();
        partialUpdatedSymptom.setId(symptom.getId());

        partialUpdatedSymptom
            .gUID(UPDATED_G_UID)
            .observation(UPDATED_OBSERVATION)
            .symptomImage(UPDATED_SYMPTOM_IMAGE)
            .symptomImageContentType(UPDATED_SYMPTOM_IMAGE_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSymptomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSymptom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSymptom))
            )
            .andExpect(status().isOk());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
        Symptom testSymptom = symptomList.get(symptomList.size() - 1);
        assertThat(testSymptom.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSymptom.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testSymptom.getSymptomImage()).isEqualTo(UPDATED_SYMPTOM_IMAGE);
        assertThat(testSymptom.getSymptomImageContentType()).isEqualTo(UPDATED_SYMPTOM_IMAGE_CONTENT_TYPE);
        assertThat(testSymptom.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSymptom.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSymptom.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSymptom.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingSymptom() throws Exception {
        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();
        symptom.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSymptomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, symptom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(symptom))
            )
            .andExpect(status().isBadRequest());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSymptom() throws Exception {
        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();
        symptom.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSymptomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(symptom))
            )
            .andExpect(status().isBadRequest());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSymptom() throws Exception {
        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();
        symptom.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSymptomMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(symptom)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSymptom() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        int databaseSizeBeforeDelete = symptomRepository.findAll().size();

        // Delete the symptom
        restSymptomMockMvc
            .perform(delete(ENTITY_API_URL_ID, symptom.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
