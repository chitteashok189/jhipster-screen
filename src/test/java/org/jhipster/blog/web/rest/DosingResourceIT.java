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
import org.jhipster.blog.domain.Dosing;
import org.jhipster.blog.domain.enumeration.DosingSource;
import org.jhipster.blog.repository.DosingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DosingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DosingResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final DosingSource DEFAULT_SOURCE = DosingSource.Automatic;
    private static final DosingSource UPDATED_SOURCE = DosingSource.Manual;

    private static final Integer DEFAULT_P_H = 1;
    private static final Integer UPDATED_P_H = 2;

    private static final Integer DEFAULT_E_C = 1;
    private static final Integer UPDATED_E_C = 2;

    private static final Integer DEFAULT_D_O = 1;
    private static final Integer UPDATED_D_O = 2;

    private static final Integer DEFAULT_NUTRIENT_TEMPERATURE = 1;
    private static final Integer UPDATED_NUTRIENT_TEMPERATURE = 2;

    private static final Integer DEFAULT_SOLAR_RADIATION = 1;
    private static final Integer UPDATED_SOLAR_RADIATION = 2;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/dosings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DosingRepository dosingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDosingMockMvc;

    private Dosing dosing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dosing createEntity(EntityManager em) {
        Dosing dosing = new Dosing()
            .gUID(DEFAULT_G_UID)
            .source(DEFAULT_SOURCE)
            .pH(DEFAULT_P_H)
            .eC(DEFAULT_E_C)
            .dO(DEFAULT_D_O)
            .nutrientTemperature(DEFAULT_NUTRIENT_TEMPERATURE)
            .solarRadiation(DEFAULT_SOLAR_RADIATION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return dosing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dosing createUpdatedEntity(EntityManager em) {
        Dosing dosing = new Dosing()
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .pH(UPDATED_P_H)
            .eC(UPDATED_E_C)
            .dO(UPDATED_D_O)
            .nutrientTemperature(UPDATED_NUTRIENT_TEMPERATURE)
            .solarRadiation(UPDATED_SOLAR_RADIATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return dosing;
    }

    @BeforeEach
    public void initTest() {
        dosing = createEntity(em);
    }

    @Test
    @Transactional
    void createDosing() throws Exception {
        int databaseSizeBeforeCreate = dosingRepository.findAll().size();
        // Create the Dosing
        restDosingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dosing)))
            .andExpect(status().isCreated());

        // Validate the Dosing in the database
        List<Dosing> dosingList = dosingRepository.findAll();
        assertThat(dosingList).hasSize(databaseSizeBeforeCreate + 1);
        Dosing testDosing = dosingList.get(dosingList.size() - 1);
        assertThat(testDosing.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testDosing.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testDosing.getpH()).isEqualTo(DEFAULT_P_H);
        assertThat(testDosing.geteC()).isEqualTo(DEFAULT_E_C);
        assertThat(testDosing.getdO()).isEqualTo(DEFAULT_D_O);
        assertThat(testDosing.getNutrientTemperature()).isEqualTo(DEFAULT_NUTRIENT_TEMPERATURE);
        assertThat(testDosing.getSolarRadiation()).isEqualTo(DEFAULT_SOLAR_RADIATION);
        assertThat(testDosing.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDosing.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDosing.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDosing.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createDosingWithExistingId() throws Exception {
        // Create the Dosing with an existing ID
        dosing.setId(1L);

        int databaseSizeBeforeCreate = dosingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDosingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dosing)))
            .andExpect(status().isBadRequest());

        // Validate the Dosing in the database
        List<Dosing> dosingList = dosingRepository.findAll();
        assertThat(dosingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDosings() throws Exception {
        // Initialize the database
        dosingRepository.saveAndFlush(dosing);

        // Get all the dosingList
        restDosingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dosing.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].pH").value(hasItem(DEFAULT_P_H)))
            .andExpect(jsonPath("$.[*].eC").value(hasItem(DEFAULT_E_C)))
            .andExpect(jsonPath("$.[*].dO").value(hasItem(DEFAULT_D_O)))
            .andExpect(jsonPath("$.[*].nutrientTemperature").value(hasItem(DEFAULT_NUTRIENT_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].solarRadiation").value(hasItem(DEFAULT_SOLAR_RADIATION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getDosing() throws Exception {
        // Initialize the database
        dosingRepository.saveAndFlush(dosing);

        // Get the dosing
        restDosingMockMvc
            .perform(get(ENTITY_API_URL_ID, dosing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dosing.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.pH").value(DEFAULT_P_H))
            .andExpect(jsonPath("$.eC").value(DEFAULT_E_C))
            .andExpect(jsonPath("$.dO").value(DEFAULT_D_O))
            .andExpect(jsonPath("$.nutrientTemperature").value(DEFAULT_NUTRIENT_TEMPERATURE))
            .andExpect(jsonPath("$.solarRadiation").value(DEFAULT_SOLAR_RADIATION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingDosing() throws Exception {
        // Get the dosing
        restDosingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDosing() throws Exception {
        // Initialize the database
        dosingRepository.saveAndFlush(dosing);

        int databaseSizeBeforeUpdate = dosingRepository.findAll().size();

        // Update the dosing
        Dosing updatedDosing = dosingRepository.findById(dosing.getId()).get();
        // Disconnect from session so that the updates on updatedDosing are not directly saved in db
        em.detach(updatedDosing);
        updatedDosing
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .pH(UPDATED_P_H)
            .eC(UPDATED_E_C)
            .dO(UPDATED_D_O)
            .nutrientTemperature(UPDATED_NUTRIENT_TEMPERATURE)
            .solarRadiation(UPDATED_SOLAR_RADIATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDosingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDosing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDosing))
            )
            .andExpect(status().isOk());

        // Validate the Dosing in the database
        List<Dosing> dosingList = dosingRepository.findAll();
        assertThat(dosingList).hasSize(databaseSizeBeforeUpdate);
        Dosing testDosing = dosingList.get(dosingList.size() - 1);
        assertThat(testDosing.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testDosing.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testDosing.getpH()).isEqualTo(UPDATED_P_H);
        assertThat(testDosing.geteC()).isEqualTo(UPDATED_E_C);
        assertThat(testDosing.getdO()).isEqualTo(UPDATED_D_O);
        assertThat(testDosing.getNutrientTemperature()).isEqualTo(UPDATED_NUTRIENT_TEMPERATURE);
        assertThat(testDosing.getSolarRadiation()).isEqualTo(UPDATED_SOLAR_RADIATION);
        assertThat(testDosing.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDosing.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDosing.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDosing.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingDosing() throws Exception {
        int databaseSizeBeforeUpdate = dosingRepository.findAll().size();
        dosing.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDosingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dosing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dosing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dosing in the database
        List<Dosing> dosingList = dosingRepository.findAll();
        assertThat(dosingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDosing() throws Exception {
        int databaseSizeBeforeUpdate = dosingRepository.findAll().size();
        dosing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDosingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dosing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dosing in the database
        List<Dosing> dosingList = dosingRepository.findAll();
        assertThat(dosingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDosing() throws Exception {
        int databaseSizeBeforeUpdate = dosingRepository.findAll().size();
        dosing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDosingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dosing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dosing in the database
        List<Dosing> dosingList = dosingRepository.findAll();
        assertThat(dosingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDosingWithPatch() throws Exception {
        // Initialize the database
        dosingRepository.saveAndFlush(dosing);

        int databaseSizeBeforeUpdate = dosingRepository.findAll().size();

        // Update the dosing using partial update
        Dosing partialUpdatedDosing = new Dosing();
        partialUpdatedDosing.setId(dosing.getId());

        partialUpdatedDosing
            .dO(UPDATED_D_O)
            .nutrientTemperature(UPDATED_NUTRIENT_TEMPERATURE)
            .solarRadiation(UPDATED_SOLAR_RADIATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDosingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDosing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDosing))
            )
            .andExpect(status().isOk());

        // Validate the Dosing in the database
        List<Dosing> dosingList = dosingRepository.findAll();
        assertThat(dosingList).hasSize(databaseSizeBeforeUpdate);
        Dosing testDosing = dosingList.get(dosingList.size() - 1);
        assertThat(testDosing.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testDosing.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testDosing.getpH()).isEqualTo(DEFAULT_P_H);
        assertThat(testDosing.geteC()).isEqualTo(DEFAULT_E_C);
        assertThat(testDosing.getdO()).isEqualTo(UPDATED_D_O);
        assertThat(testDosing.getNutrientTemperature()).isEqualTo(UPDATED_NUTRIENT_TEMPERATURE);
        assertThat(testDosing.getSolarRadiation()).isEqualTo(UPDATED_SOLAR_RADIATION);
        assertThat(testDosing.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDosing.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDosing.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDosing.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateDosingWithPatch() throws Exception {
        // Initialize the database
        dosingRepository.saveAndFlush(dosing);

        int databaseSizeBeforeUpdate = dosingRepository.findAll().size();

        // Update the dosing using partial update
        Dosing partialUpdatedDosing = new Dosing();
        partialUpdatedDosing.setId(dosing.getId());

        partialUpdatedDosing
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .pH(UPDATED_P_H)
            .eC(UPDATED_E_C)
            .dO(UPDATED_D_O)
            .nutrientTemperature(UPDATED_NUTRIENT_TEMPERATURE)
            .solarRadiation(UPDATED_SOLAR_RADIATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDosingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDosing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDosing))
            )
            .andExpect(status().isOk());

        // Validate the Dosing in the database
        List<Dosing> dosingList = dosingRepository.findAll();
        assertThat(dosingList).hasSize(databaseSizeBeforeUpdate);
        Dosing testDosing = dosingList.get(dosingList.size() - 1);
        assertThat(testDosing.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testDosing.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testDosing.getpH()).isEqualTo(UPDATED_P_H);
        assertThat(testDosing.geteC()).isEqualTo(UPDATED_E_C);
        assertThat(testDosing.getdO()).isEqualTo(UPDATED_D_O);
        assertThat(testDosing.getNutrientTemperature()).isEqualTo(UPDATED_NUTRIENT_TEMPERATURE);
        assertThat(testDosing.getSolarRadiation()).isEqualTo(UPDATED_SOLAR_RADIATION);
        assertThat(testDosing.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDosing.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDosing.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDosing.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingDosing() throws Exception {
        int databaseSizeBeforeUpdate = dosingRepository.findAll().size();
        dosing.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDosingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dosing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dosing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dosing in the database
        List<Dosing> dosingList = dosingRepository.findAll();
        assertThat(dosingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDosing() throws Exception {
        int databaseSizeBeforeUpdate = dosingRepository.findAll().size();
        dosing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDosingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dosing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dosing in the database
        List<Dosing> dosingList = dosingRepository.findAll();
        assertThat(dosingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDosing() throws Exception {
        int databaseSizeBeforeUpdate = dosingRepository.findAll().size();
        dosing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDosingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dosing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dosing in the database
        List<Dosing> dosingList = dosingRepository.findAll();
        assertThat(dosingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDosing() throws Exception {
        // Initialize the database
        dosingRepository.saveAndFlush(dosing);

        int databaseSizeBeforeDelete = dosingRepository.findAll().size();

        // Delete the dosing
        restDosingMockMvc
            .perform(delete(ENTITY_API_URL_ID, dosing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dosing> dosingList = dosingRepository.findAll();
        assertThat(dosingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
