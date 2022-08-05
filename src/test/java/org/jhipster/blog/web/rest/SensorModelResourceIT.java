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
import org.jhipster.blog.domain.SensorModel;
import org.jhipster.blog.repository.SensorModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SensorModelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SensorModelResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Long DEFAULT_SENSOR_TYPE = 1L;
    private static final Long UPDATED_SENSOR_TYPE = 2L;

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SENSOR_MEASURE = 1;
    private static final Integer UPDATED_SENSOR_MEASURE = 2;

    private static final String DEFAULT_MODEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROPERTIES = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTIES = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/sensor-models";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SensorModelRepository sensorModelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSensorModelMockMvc;

    private SensorModel sensorModel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SensorModel createEntity(EntityManager em) {
        SensorModel sensorModel = new SensorModel()
            .gUID(DEFAULT_G_UID)
            .sensorType(DEFAULT_SENSOR_TYPE)
            .manufacturer(DEFAULT_MANUFACTURER)
            .productCode(DEFAULT_PRODUCT_CODE)
            .sensorMeasure(DEFAULT_SENSOR_MEASURE)
            .modelName(DEFAULT_MODEL_NAME)
            .properties(DEFAULT_PROPERTIES)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return sensorModel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SensorModel createUpdatedEntity(EntityManager em) {
        SensorModel sensorModel = new SensorModel()
            .gUID(UPDATED_G_UID)
            .sensorType(UPDATED_SENSOR_TYPE)
            .manufacturer(UPDATED_MANUFACTURER)
            .productCode(UPDATED_PRODUCT_CODE)
            .sensorMeasure(UPDATED_SENSOR_MEASURE)
            .modelName(UPDATED_MODEL_NAME)
            .properties(UPDATED_PROPERTIES)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return sensorModel;
    }

    @BeforeEach
    public void initTest() {
        sensorModel = createEntity(em);
    }

    @Test
    @Transactional
    void createSensorModel() throws Exception {
        int databaseSizeBeforeCreate = sensorModelRepository.findAll().size();
        // Create the SensorModel
        restSensorModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sensorModel)))
            .andExpect(status().isCreated());

        // Validate the SensorModel in the database
        List<SensorModel> sensorModelList = sensorModelRepository.findAll();
        assertThat(sensorModelList).hasSize(databaseSizeBeforeCreate + 1);
        SensorModel testSensorModel = sensorModelList.get(sensorModelList.size() - 1);
        assertThat(testSensorModel.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testSensorModel.getSensorType()).isEqualTo(DEFAULT_SENSOR_TYPE);
        assertThat(testSensorModel.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testSensorModel.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testSensorModel.getSensorMeasure()).isEqualTo(DEFAULT_SENSOR_MEASURE);
        assertThat(testSensorModel.getModelName()).isEqualTo(DEFAULT_MODEL_NAME);
        assertThat(testSensorModel.getProperties()).isEqualTo(DEFAULT_PROPERTIES);
        assertThat(testSensorModel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSensorModel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSensorModel.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSensorModel.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSensorModel.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createSensorModelWithExistingId() throws Exception {
        // Create the SensorModel with an existing ID
        sensorModel.setId(1L);

        int databaseSizeBeforeCreate = sensorModelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSensorModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sensorModel)))
            .andExpect(status().isBadRequest());

        // Validate the SensorModel in the database
        List<SensorModel> sensorModelList = sensorModelRepository.findAll();
        assertThat(sensorModelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSensorModels() throws Exception {
        // Initialize the database
        sensorModelRepository.saveAndFlush(sensorModel);

        // Get all the sensorModelList
        restSensorModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensorModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].sensorType").value(hasItem(DEFAULT_SENSOR_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE)))
            .andExpect(jsonPath("$.[*].sensorMeasure").value(hasItem(DEFAULT_SENSOR_MEASURE)))
            .andExpect(jsonPath("$.[*].modelName").value(hasItem(DEFAULT_MODEL_NAME)))
            .andExpect(jsonPath("$.[*].properties").value(hasItem(DEFAULT_PROPERTIES)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getSensorModel() throws Exception {
        // Initialize the database
        sensorModelRepository.saveAndFlush(sensorModel);

        // Get the sensorModel
        restSensorModelMockMvc
            .perform(get(ENTITY_API_URL_ID, sensorModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sensorModel.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.sensorType").value(DEFAULT_SENSOR_TYPE.intValue()))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER))
            .andExpect(jsonPath("$.productCode").value(DEFAULT_PRODUCT_CODE))
            .andExpect(jsonPath("$.sensorMeasure").value(DEFAULT_SENSOR_MEASURE))
            .andExpect(jsonPath("$.modelName").value(DEFAULT_MODEL_NAME))
            .andExpect(jsonPath("$.properties").value(DEFAULT_PROPERTIES))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingSensorModel() throws Exception {
        // Get the sensorModel
        restSensorModelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSensorModel() throws Exception {
        // Initialize the database
        sensorModelRepository.saveAndFlush(sensorModel);

        int databaseSizeBeforeUpdate = sensorModelRepository.findAll().size();

        // Update the sensorModel
        SensorModel updatedSensorModel = sensorModelRepository.findById(sensorModel.getId()).get();
        // Disconnect from session so that the updates on updatedSensorModel are not directly saved in db
        em.detach(updatedSensorModel);
        updatedSensorModel
            .gUID(UPDATED_G_UID)
            .sensorType(UPDATED_SENSOR_TYPE)
            .manufacturer(UPDATED_MANUFACTURER)
            .productCode(UPDATED_PRODUCT_CODE)
            .sensorMeasure(UPDATED_SENSOR_MEASURE)
            .modelName(UPDATED_MODEL_NAME)
            .properties(UPDATED_PROPERTIES)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSensorModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSensorModel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSensorModel))
            )
            .andExpect(status().isOk());

        // Validate the SensorModel in the database
        List<SensorModel> sensorModelList = sensorModelRepository.findAll();
        assertThat(sensorModelList).hasSize(databaseSizeBeforeUpdate);
        SensorModel testSensorModel = sensorModelList.get(sensorModelList.size() - 1);
        assertThat(testSensorModel.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSensorModel.getSensorType()).isEqualTo(UPDATED_SENSOR_TYPE);
        assertThat(testSensorModel.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testSensorModel.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testSensorModel.getSensorMeasure()).isEqualTo(UPDATED_SENSOR_MEASURE);
        assertThat(testSensorModel.getModelName()).isEqualTo(UPDATED_MODEL_NAME);
        assertThat(testSensorModel.getProperties()).isEqualTo(UPDATED_PROPERTIES);
        assertThat(testSensorModel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSensorModel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSensorModel.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSensorModel.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSensorModel.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingSensorModel() throws Exception {
        int databaseSizeBeforeUpdate = sensorModelRepository.findAll().size();
        sensorModel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensorModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sensorModel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sensorModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the SensorModel in the database
        List<SensorModel> sensorModelList = sensorModelRepository.findAll();
        assertThat(sensorModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSensorModel() throws Exception {
        int databaseSizeBeforeUpdate = sensorModelRepository.findAll().size();
        sensorModel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sensorModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the SensorModel in the database
        List<SensorModel> sensorModelList = sensorModelRepository.findAll();
        assertThat(sensorModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSensorModel() throws Exception {
        int databaseSizeBeforeUpdate = sensorModelRepository.findAll().size();
        sensorModel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorModelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sensorModel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SensorModel in the database
        List<SensorModel> sensorModelList = sensorModelRepository.findAll();
        assertThat(sensorModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSensorModelWithPatch() throws Exception {
        // Initialize the database
        sensorModelRepository.saveAndFlush(sensorModel);

        int databaseSizeBeforeUpdate = sensorModelRepository.findAll().size();

        // Update the sensorModel using partial update
        SensorModel partialUpdatedSensorModel = new SensorModel();
        partialUpdatedSensorModel.setId(sensorModel.getId());

        partialUpdatedSensorModel
            .gUID(UPDATED_G_UID)
            .sensorType(UPDATED_SENSOR_TYPE)
            .manufacturer(UPDATED_MANUFACTURER)
            .sensorMeasure(UPDATED_SENSOR_MEASURE)
            .modelName(UPDATED_MODEL_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON);

        restSensorModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSensorModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSensorModel))
            )
            .andExpect(status().isOk());

        // Validate the SensorModel in the database
        List<SensorModel> sensorModelList = sensorModelRepository.findAll();
        assertThat(sensorModelList).hasSize(databaseSizeBeforeUpdate);
        SensorModel testSensorModel = sensorModelList.get(sensorModelList.size() - 1);
        assertThat(testSensorModel.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSensorModel.getSensorType()).isEqualTo(UPDATED_SENSOR_TYPE);
        assertThat(testSensorModel.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testSensorModel.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testSensorModel.getSensorMeasure()).isEqualTo(UPDATED_SENSOR_MEASURE);
        assertThat(testSensorModel.getModelName()).isEqualTo(UPDATED_MODEL_NAME);
        assertThat(testSensorModel.getProperties()).isEqualTo(DEFAULT_PROPERTIES);
        assertThat(testSensorModel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSensorModel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSensorModel.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSensorModel.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSensorModel.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateSensorModelWithPatch() throws Exception {
        // Initialize the database
        sensorModelRepository.saveAndFlush(sensorModel);

        int databaseSizeBeforeUpdate = sensorModelRepository.findAll().size();

        // Update the sensorModel using partial update
        SensorModel partialUpdatedSensorModel = new SensorModel();
        partialUpdatedSensorModel.setId(sensorModel.getId());

        partialUpdatedSensorModel
            .gUID(UPDATED_G_UID)
            .sensorType(UPDATED_SENSOR_TYPE)
            .manufacturer(UPDATED_MANUFACTURER)
            .productCode(UPDATED_PRODUCT_CODE)
            .sensorMeasure(UPDATED_SENSOR_MEASURE)
            .modelName(UPDATED_MODEL_NAME)
            .properties(UPDATED_PROPERTIES)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSensorModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSensorModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSensorModel))
            )
            .andExpect(status().isOk());

        // Validate the SensorModel in the database
        List<SensorModel> sensorModelList = sensorModelRepository.findAll();
        assertThat(sensorModelList).hasSize(databaseSizeBeforeUpdate);
        SensorModel testSensorModel = sensorModelList.get(sensorModelList.size() - 1);
        assertThat(testSensorModel.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSensorModel.getSensorType()).isEqualTo(UPDATED_SENSOR_TYPE);
        assertThat(testSensorModel.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testSensorModel.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testSensorModel.getSensorMeasure()).isEqualTo(UPDATED_SENSOR_MEASURE);
        assertThat(testSensorModel.getModelName()).isEqualTo(UPDATED_MODEL_NAME);
        assertThat(testSensorModel.getProperties()).isEqualTo(UPDATED_PROPERTIES);
        assertThat(testSensorModel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSensorModel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSensorModel.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSensorModel.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSensorModel.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingSensorModel() throws Exception {
        int databaseSizeBeforeUpdate = sensorModelRepository.findAll().size();
        sensorModel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensorModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sensorModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sensorModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the SensorModel in the database
        List<SensorModel> sensorModelList = sensorModelRepository.findAll();
        assertThat(sensorModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSensorModel() throws Exception {
        int databaseSizeBeforeUpdate = sensorModelRepository.findAll().size();
        sensorModel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sensorModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the SensorModel in the database
        List<SensorModel> sensorModelList = sensorModelRepository.findAll();
        assertThat(sensorModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSensorModel() throws Exception {
        int databaseSizeBeforeUpdate = sensorModelRepository.findAll().size();
        sensorModel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorModelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sensorModel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SensorModel in the database
        List<SensorModel> sensorModelList = sensorModelRepository.findAll();
        assertThat(sensorModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSensorModel() throws Exception {
        // Initialize the database
        sensorModelRepository.saveAndFlush(sensorModel);

        int databaseSizeBeforeDelete = sensorModelRepository.findAll().size();

        // Delete the sensorModel
        restSensorModelMockMvc
            .perform(delete(ENTITY_API_URL_ID, sensorModel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SensorModel> sensorModelList = sensorModelRepository.findAll();
        assertThat(sensorModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
