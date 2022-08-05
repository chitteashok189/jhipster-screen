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
import org.jhipster.blog.domain.Irrigation;
import org.jhipster.blog.domain.enumeration.IrriSource;
import org.jhipster.blog.repository.IrrigationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IrrigationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IrrigationResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final IrriSource DEFAULT_SOURCE = IrriSource.Automatic;
    private static final IrriSource UPDATED_SOURCE = IrriSource.Manual;

    private static final Integer DEFAULT_NUTRIENT_LEVEL = 1;
    private static final Integer UPDATED_NUTRIENT_LEVEL = 2;

    private static final Integer DEFAULT_SOLAR_RADIATION = 1;
    private static final Integer UPDATED_SOLAR_RADIATION = 2;

    private static final Integer DEFAULT_INLET_FLOW = 1;
    private static final Integer UPDATED_INLET_FLOW = 2;

    private static final Integer DEFAULT_OUTLET_FLOW = 1;
    private static final Integer UPDATED_OUTLET_FLOW = 2;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/irrigations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IrrigationRepository irrigationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIrrigationMockMvc;

    private Irrigation irrigation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Irrigation createEntity(EntityManager em) {
        Irrigation irrigation = new Irrigation()
            .gUID(DEFAULT_G_UID)
            .source(DEFAULT_SOURCE)
            .nutrientLevel(DEFAULT_NUTRIENT_LEVEL)
            .solarRadiation(DEFAULT_SOLAR_RADIATION)
            .inletFlow(DEFAULT_INLET_FLOW)
            .outletFlow(DEFAULT_OUTLET_FLOW)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return irrigation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Irrigation createUpdatedEntity(EntityManager em) {
        Irrigation irrigation = new Irrigation()
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .nutrientLevel(UPDATED_NUTRIENT_LEVEL)
            .solarRadiation(UPDATED_SOLAR_RADIATION)
            .inletFlow(UPDATED_INLET_FLOW)
            .outletFlow(UPDATED_OUTLET_FLOW)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return irrigation;
    }

    @BeforeEach
    public void initTest() {
        irrigation = createEntity(em);
    }

    @Test
    @Transactional
    void createIrrigation() throws Exception {
        int databaseSizeBeforeCreate = irrigationRepository.findAll().size();
        // Create the Irrigation
        restIrrigationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(irrigation)))
            .andExpect(status().isCreated());

        // Validate the Irrigation in the database
        List<Irrigation> irrigationList = irrigationRepository.findAll();
        assertThat(irrigationList).hasSize(databaseSizeBeforeCreate + 1);
        Irrigation testIrrigation = irrigationList.get(irrigationList.size() - 1);
        assertThat(testIrrigation.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testIrrigation.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testIrrigation.getNutrientLevel()).isEqualTo(DEFAULT_NUTRIENT_LEVEL);
        assertThat(testIrrigation.getSolarRadiation()).isEqualTo(DEFAULT_SOLAR_RADIATION);
        assertThat(testIrrigation.getInletFlow()).isEqualTo(DEFAULT_INLET_FLOW);
        assertThat(testIrrigation.getOutletFlow()).isEqualTo(DEFAULT_OUTLET_FLOW);
        assertThat(testIrrigation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testIrrigation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testIrrigation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testIrrigation.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createIrrigationWithExistingId() throws Exception {
        // Create the Irrigation with an existing ID
        irrigation.setId(1L);

        int databaseSizeBeforeCreate = irrigationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIrrigationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(irrigation)))
            .andExpect(status().isBadRequest());

        // Validate the Irrigation in the database
        List<Irrigation> irrigationList = irrigationRepository.findAll();
        assertThat(irrigationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIrrigations() throws Exception {
        // Initialize the database
        irrigationRepository.saveAndFlush(irrigation);

        // Get all the irrigationList
        restIrrigationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(irrigation.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].nutrientLevel").value(hasItem(DEFAULT_NUTRIENT_LEVEL)))
            .andExpect(jsonPath("$.[*].solarRadiation").value(hasItem(DEFAULT_SOLAR_RADIATION)))
            .andExpect(jsonPath("$.[*].inletFlow").value(hasItem(DEFAULT_INLET_FLOW)))
            .andExpect(jsonPath("$.[*].outletFlow").value(hasItem(DEFAULT_OUTLET_FLOW)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getIrrigation() throws Exception {
        // Initialize the database
        irrigationRepository.saveAndFlush(irrigation);

        // Get the irrigation
        restIrrigationMockMvc
            .perform(get(ENTITY_API_URL_ID, irrigation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(irrigation.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.nutrientLevel").value(DEFAULT_NUTRIENT_LEVEL))
            .andExpect(jsonPath("$.solarRadiation").value(DEFAULT_SOLAR_RADIATION))
            .andExpect(jsonPath("$.inletFlow").value(DEFAULT_INLET_FLOW))
            .andExpect(jsonPath("$.outletFlow").value(DEFAULT_OUTLET_FLOW))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingIrrigation() throws Exception {
        // Get the irrigation
        restIrrigationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIrrigation() throws Exception {
        // Initialize the database
        irrigationRepository.saveAndFlush(irrigation);

        int databaseSizeBeforeUpdate = irrigationRepository.findAll().size();

        // Update the irrigation
        Irrigation updatedIrrigation = irrigationRepository.findById(irrigation.getId()).get();
        // Disconnect from session so that the updates on updatedIrrigation are not directly saved in db
        em.detach(updatedIrrigation);
        updatedIrrigation
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .nutrientLevel(UPDATED_NUTRIENT_LEVEL)
            .solarRadiation(UPDATED_SOLAR_RADIATION)
            .inletFlow(UPDATED_INLET_FLOW)
            .outletFlow(UPDATED_OUTLET_FLOW)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restIrrigationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIrrigation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIrrigation))
            )
            .andExpect(status().isOk());

        // Validate the Irrigation in the database
        List<Irrigation> irrigationList = irrigationRepository.findAll();
        assertThat(irrigationList).hasSize(databaseSizeBeforeUpdate);
        Irrigation testIrrigation = irrigationList.get(irrigationList.size() - 1);
        assertThat(testIrrigation.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testIrrigation.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testIrrigation.getNutrientLevel()).isEqualTo(UPDATED_NUTRIENT_LEVEL);
        assertThat(testIrrigation.getSolarRadiation()).isEqualTo(UPDATED_SOLAR_RADIATION);
        assertThat(testIrrigation.getInletFlow()).isEqualTo(UPDATED_INLET_FLOW);
        assertThat(testIrrigation.getOutletFlow()).isEqualTo(UPDATED_OUTLET_FLOW);
        assertThat(testIrrigation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testIrrigation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testIrrigation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testIrrigation.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingIrrigation() throws Exception {
        int databaseSizeBeforeUpdate = irrigationRepository.findAll().size();
        irrigation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIrrigationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, irrigation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(irrigation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Irrigation in the database
        List<Irrigation> irrigationList = irrigationRepository.findAll();
        assertThat(irrigationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIrrigation() throws Exception {
        int databaseSizeBeforeUpdate = irrigationRepository.findAll().size();
        irrigation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIrrigationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(irrigation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Irrigation in the database
        List<Irrigation> irrigationList = irrigationRepository.findAll();
        assertThat(irrigationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIrrigation() throws Exception {
        int databaseSizeBeforeUpdate = irrigationRepository.findAll().size();
        irrigation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIrrigationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(irrigation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Irrigation in the database
        List<Irrigation> irrigationList = irrigationRepository.findAll();
        assertThat(irrigationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIrrigationWithPatch() throws Exception {
        // Initialize the database
        irrigationRepository.saveAndFlush(irrigation);

        int databaseSizeBeforeUpdate = irrigationRepository.findAll().size();

        // Update the irrigation using partial update
        Irrigation partialUpdatedIrrigation = new Irrigation();
        partialUpdatedIrrigation.setId(irrigation.getId());

        partialUpdatedIrrigation
            .nutrientLevel(UPDATED_NUTRIENT_LEVEL)
            .solarRadiation(UPDATED_SOLAR_RADIATION)
            .inletFlow(UPDATED_INLET_FLOW)
            .updatedOn(UPDATED_UPDATED_ON);

        restIrrigationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIrrigation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIrrigation))
            )
            .andExpect(status().isOk());

        // Validate the Irrigation in the database
        List<Irrigation> irrigationList = irrigationRepository.findAll();
        assertThat(irrigationList).hasSize(databaseSizeBeforeUpdate);
        Irrigation testIrrigation = irrigationList.get(irrigationList.size() - 1);
        assertThat(testIrrigation.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testIrrigation.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testIrrigation.getNutrientLevel()).isEqualTo(UPDATED_NUTRIENT_LEVEL);
        assertThat(testIrrigation.getSolarRadiation()).isEqualTo(UPDATED_SOLAR_RADIATION);
        assertThat(testIrrigation.getInletFlow()).isEqualTo(UPDATED_INLET_FLOW);
        assertThat(testIrrigation.getOutletFlow()).isEqualTo(DEFAULT_OUTLET_FLOW);
        assertThat(testIrrigation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testIrrigation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testIrrigation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testIrrigation.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateIrrigationWithPatch() throws Exception {
        // Initialize the database
        irrigationRepository.saveAndFlush(irrigation);

        int databaseSizeBeforeUpdate = irrigationRepository.findAll().size();

        // Update the irrigation using partial update
        Irrigation partialUpdatedIrrigation = new Irrigation();
        partialUpdatedIrrigation.setId(irrigation.getId());

        partialUpdatedIrrigation
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .nutrientLevel(UPDATED_NUTRIENT_LEVEL)
            .solarRadiation(UPDATED_SOLAR_RADIATION)
            .inletFlow(UPDATED_INLET_FLOW)
            .outletFlow(UPDATED_OUTLET_FLOW)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restIrrigationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIrrigation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIrrigation))
            )
            .andExpect(status().isOk());

        // Validate the Irrigation in the database
        List<Irrigation> irrigationList = irrigationRepository.findAll();
        assertThat(irrigationList).hasSize(databaseSizeBeforeUpdate);
        Irrigation testIrrigation = irrigationList.get(irrigationList.size() - 1);
        assertThat(testIrrigation.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testIrrigation.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testIrrigation.getNutrientLevel()).isEqualTo(UPDATED_NUTRIENT_LEVEL);
        assertThat(testIrrigation.getSolarRadiation()).isEqualTo(UPDATED_SOLAR_RADIATION);
        assertThat(testIrrigation.getInletFlow()).isEqualTo(UPDATED_INLET_FLOW);
        assertThat(testIrrigation.getOutletFlow()).isEqualTo(UPDATED_OUTLET_FLOW);
        assertThat(testIrrigation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testIrrigation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testIrrigation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testIrrigation.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingIrrigation() throws Exception {
        int databaseSizeBeforeUpdate = irrigationRepository.findAll().size();
        irrigation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIrrigationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, irrigation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(irrigation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Irrigation in the database
        List<Irrigation> irrigationList = irrigationRepository.findAll();
        assertThat(irrigationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIrrigation() throws Exception {
        int databaseSizeBeforeUpdate = irrigationRepository.findAll().size();
        irrigation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIrrigationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(irrigation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Irrigation in the database
        List<Irrigation> irrigationList = irrigationRepository.findAll();
        assertThat(irrigationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIrrigation() throws Exception {
        int databaseSizeBeforeUpdate = irrigationRepository.findAll().size();
        irrigation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIrrigationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(irrigation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Irrigation in the database
        List<Irrigation> irrigationList = irrigationRepository.findAll();
        assertThat(irrigationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIrrigation() throws Exception {
        // Initialize the database
        irrigationRepository.saveAndFlush(irrigation);

        int databaseSizeBeforeDelete = irrigationRepository.findAll().size();

        // Delete the irrigation
        restIrrigationMockMvc
            .perform(delete(ENTITY_API_URL_ID, irrigation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Irrigation> irrigationList = irrigationRepository.findAll();
        assertThat(irrigationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
