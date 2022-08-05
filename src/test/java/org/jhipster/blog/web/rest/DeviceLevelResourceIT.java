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
import org.jhipster.blog.domain.DeviceLevel;
import org.jhipster.blog.repository.DeviceLevelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeviceLevelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeviceLevelResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_LEVEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_DEVICE_LEVEL_TYPE = 1L;
    private static final Long UPDATED_DEVICE_LEVEL_TYPE = 2L;

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRODUCT_CODE = 1;
    private static final Integer UPDATED_PRODUCT_CODE = 2;

    private static final Integer DEFAULT_PORTS = 1;
    private static final Integer UPDATED_PORTS = 2;

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

    private static final String ENTITY_API_URL = "/api/device-levels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeviceLevelRepository deviceLevelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceLevelMockMvc;

    private DeviceLevel deviceLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceLevel createEntity(EntityManager em) {
        DeviceLevel deviceLevel = new DeviceLevel()
            .gUID(DEFAULT_G_UID)
            .levelName(DEFAULT_LEVEL_NAME)
            .deviceLevelType(DEFAULT_DEVICE_LEVEL_TYPE)
            .manufacturer(DEFAULT_MANUFACTURER)
            .productCode(DEFAULT_PRODUCT_CODE)
            .ports(DEFAULT_PORTS)
            .properties(DEFAULT_PROPERTIES)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return deviceLevel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceLevel createUpdatedEntity(EntityManager em) {
        DeviceLevel deviceLevel = new DeviceLevel()
            .gUID(UPDATED_G_UID)
            .levelName(UPDATED_LEVEL_NAME)
            .deviceLevelType(UPDATED_DEVICE_LEVEL_TYPE)
            .manufacturer(UPDATED_MANUFACTURER)
            .productCode(UPDATED_PRODUCT_CODE)
            .ports(UPDATED_PORTS)
            .properties(UPDATED_PROPERTIES)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return deviceLevel;
    }

    @BeforeEach
    public void initTest() {
        deviceLevel = createEntity(em);
    }

    @Test
    @Transactional
    void createDeviceLevel() throws Exception {
        int databaseSizeBeforeCreate = deviceLevelRepository.findAll().size();
        // Create the DeviceLevel
        restDeviceLevelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceLevel)))
            .andExpect(status().isCreated());

        // Validate the DeviceLevel in the database
        List<DeviceLevel> deviceLevelList = deviceLevelRepository.findAll();
        assertThat(deviceLevelList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceLevel testDeviceLevel = deviceLevelList.get(deviceLevelList.size() - 1);
        assertThat(testDeviceLevel.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testDeviceLevel.getLevelName()).isEqualTo(DEFAULT_LEVEL_NAME);
        assertThat(testDeviceLevel.getDeviceLevelType()).isEqualTo(DEFAULT_DEVICE_LEVEL_TYPE);
        assertThat(testDeviceLevel.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testDeviceLevel.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testDeviceLevel.getPorts()).isEqualTo(DEFAULT_PORTS);
        assertThat(testDeviceLevel.getProperties()).isEqualTo(DEFAULT_PROPERTIES);
        assertThat(testDeviceLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDeviceLevel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDeviceLevel.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDeviceLevel.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDeviceLevel.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createDeviceLevelWithExistingId() throws Exception {
        // Create the DeviceLevel with an existing ID
        deviceLevel.setId(1L);

        int databaseSizeBeforeCreate = deviceLevelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceLevelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceLevel)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceLevel in the database
        List<DeviceLevel> deviceLevelList = deviceLevelRepository.findAll();
        assertThat(deviceLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeviceLevels() throws Exception {
        // Initialize the database
        deviceLevelRepository.saveAndFlush(deviceLevel);

        // Get all the deviceLevelList
        restDeviceLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].levelName").value(hasItem(DEFAULT_LEVEL_NAME)))
            .andExpect(jsonPath("$.[*].deviceLevelType").value(hasItem(DEFAULT_DEVICE_LEVEL_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE)))
            .andExpect(jsonPath("$.[*].ports").value(hasItem(DEFAULT_PORTS)))
            .andExpect(jsonPath("$.[*].properties").value(hasItem(DEFAULT_PROPERTIES)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getDeviceLevel() throws Exception {
        // Initialize the database
        deviceLevelRepository.saveAndFlush(deviceLevel);

        // Get the deviceLevel
        restDeviceLevelMockMvc
            .perform(get(ENTITY_API_URL_ID, deviceLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deviceLevel.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.levelName").value(DEFAULT_LEVEL_NAME))
            .andExpect(jsonPath("$.deviceLevelType").value(DEFAULT_DEVICE_LEVEL_TYPE.intValue()))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER))
            .andExpect(jsonPath("$.productCode").value(DEFAULT_PRODUCT_CODE))
            .andExpect(jsonPath("$.ports").value(DEFAULT_PORTS))
            .andExpect(jsonPath("$.properties").value(DEFAULT_PROPERTIES))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingDeviceLevel() throws Exception {
        // Get the deviceLevel
        restDeviceLevelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDeviceLevel() throws Exception {
        // Initialize the database
        deviceLevelRepository.saveAndFlush(deviceLevel);

        int databaseSizeBeforeUpdate = deviceLevelRepository.findAll().size();

        // Update the deviceLevel
        DeviceLevel updatedDeviceLevel = deviceLevelRepository.findById(deviceLevel.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceLevel are not directly saved in db
        em.detach(updatedDeviceLevel);
        updatedDeviceLevel
            .gUID(UPDATED_G_UID)
            .levelName(UPDATED_LEVEL_NAME)
            .deviceLevelType(UPDATED_DEVICE_LEVEL_TYPE)
            .manufacturer(UPDATED_MANUFACTURER)
            .productCode(UPDATED_PRODUCT_CODE)
            .ports(UPDATED_PORTS)
            .properties(UPDATED_PROPERTIES)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDeviceLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDeviceLevel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDeviceLevel))
            )
            .andExpect(status().isOk());

        // Validate the DeviceLevel in the database
        List<DeviceLevel> deviceLevelList = deviceLevelRepository.findAll();
        assertThat(deviceLevelList).hasSize(databaseSizeBeforeUpdate);
        DeviceLevel testDeviceLevel = deviceLevelList.get(deviceLevelList.size() - 1);
        assertThat(testDeviceLevel.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testDeviceLevel.getLevelName()).isEqualTo(UPDATED_LEVEL_NAME);
        assertThat(testDeviceLevel.getDeviceLevelType()).isEqualTo(UPDATED_DEVICE_LEVEL_TYPE);
        assertThat(testDeviceLevel.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testDeviceLevel.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testDeviceLevel.getPorts()).isEqualTo(UPDATED_PORTS);
        assertThat(testDeviceLevel.getProperties()).isEqualTo(UPDATED_PROPERTIES);
        assertThat(testDeviceLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeviceLevel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDeviceLevel.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDeviceLevel.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDeviceLevel.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingDeviceLevel() throws Exception {
        int databaseSizeBeforeUpdate = deviceLevelRepository.findAll().size();
        deviceLevel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deviceLevel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceLevel in the database
        List<DeviceLevel> deviceLevelList = deviceLevelRepository.findAll();
        assertThat(deviceLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeviceLevel() throws Exception {
        int databaseSizeBeforeUpdate = deviceLevelRepository.findAll().size();
        deviceLevel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceLevel in the database
        List<DeviceLevel> deviceLevelList = deviceLevelRepository.findAll();
        assertThat(deviceLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeviceLevel() throws Exception {
        int databaseSizeBeforeUpdate = deviceLevelRepository.findAll().size();
        deviceLevel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceLevelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceLevel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeviceLevel in the database
        List<DeviceLevel> deviceLevelList = deviceLevelRepository.findAll();
        assertThat(deviceLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeviceLevelWithPatch() throws Exception {
        // Initialize the database
        deviceLevelRepository.saveAndFlush(deviceLevel);

        int databaseSizeBeforeUpdate = deviceLevelRepository.findAll().size();

        // Update the deviceLevel using partial update
        DeviceLevel partialUpdatedDeviceLevel = new DeviceLevel();
        partialUpdatedDeviceLevel.setId(deviceLevel.getId());

        partialUpdatedDeviceLevel
            .gUID(UPDATED_G_UID)
            .levelName(UPDATED_LEVEL_NAME)
            .ports(UPDATED_PORTS)
            .properties(UPDATED_PROPERTIES)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);

        restDeviceLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeviceLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeviceLevel))
            )
            .andExpect(status().isOk());

        // Validate the DeviceLevel in the database
        List<DeviceLevel> deviceLevelList = deviceLevelRepository.findAll();
        assertThat(deviceLevelList).hasSize(databaseSizeBeforeUpdate);
        DeviceLevel testDeviceLevel = deviceLevelList.get(deviceLevelList.size() - 1);
        assertThat(testDeviceLevel.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testDeviceLevel.getLevelName()).isEqualTo(UPDATED_LEVEL_NAME);
        assertThat(testDeviceLevel.getDeviceLevelType()).isEqualTo(DEFAULT_DEVICE_LEVEL_TYPE);
        assertThat(testDeviceLevel.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testDeviceLevel.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testDeviceLevel.getPorts()).isEqualTo(UPDATED_PORTS);
        assertThat(testDeviceLevel.getProperties()).isEqualTo(UPDATED_PROPERTIES);
        assertThat(testDeviceLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDeviceLevel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDeviceLevel.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDeviceLevel.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDeviceLevel.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateDeviceLevelWithPatch() throws Exception {
        // Initialize the database
        deviceLevelRepository.saveAndFlush(deviceLevel);

        int databaseSizeBeforeUpdate = deviceLevelRepository.findAll().size();

        // Update the deviceLevel using partial update
        DeviceLevel partialUpdatedDeviceLevel = new DeviceLevel();
        partialUpdatedDeviceLevel.setId(deviceLevel.getId());

        partialUpdatedDeviceLevel
            .gUID(UPDATED_G_UID)
            .levelName(UPDATED_LEVEL_NAME)
            .deviceLevelType(UPDATED_DEVICE_LEVEL_TYPE)
            .manufacturer(UPDATED_MANUFACTURER)
            .productCode(UPDATED_PRODUCT_CODE)
            .ports(UPDATED_PORTS)
            .properties(UPDATED_PROPERTIES)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDeviceLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeviceLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeviceLevel))
            )
            .andExpect(status().isOk());

        // Validate the DeviceLevel in the database
        List<DeviceLevel> deviceLevelList = deviceLevelRepository.findAll();
        assertThat(deviceLevelList).hasSize(databaseSizeBeforeUpdate);
        DeviceLevel testDeviceLevel = deviceLevelList.get(deviceLevelList.size() - 1);
        assertThat(testDeviceLevel.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testDeviceLevel.getLevelName()).isEqualTo(UPDATED_LEVEL_NAME);
        assertThat(testDeviceLevel.getDeviceLevelType()).isEqualTo(UPDATED_DEVICE_LEVEL_TYPE);
        assertThat(testDeviceLevel.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testDeviceLevel.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testDeviceLevel.getPorts()).isEqualTo(UPDATED_PORTS);
        assertThat(testDeviceLevel.getProperties()).isEqualTo(UPDATED_PROPERTIES);
        assertThat(testDeviceLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeviceLevel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDeviceLevel.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDeviceLevel.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDeviceLevel.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingDeviceLevel() throws Exception {
        int databaseSizeBeforeUpdate = deviceLevelRepository.findAll().size();
        deviceLevel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deviceLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceLevel in the database
        List<DeviceLevel> deviceLevelList = deviceLevelRepository.findAll();
        assertThat(deviceLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeviceLevel() throws Exception {
        int databaseSizeBeforeUpdate = deviceLevelRepository.findAll().size();
        deviceLevel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceLevel in the database
        List<DeviceLevel> deviceLevelList = deviceLevelRepository.findAll();
        assertThat(deviceLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeviceLevel() throws Exception {
        int databaseSizeBeforeUpdate = deviceLevelRepository.findAll().size();
        deviceLevel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceLevelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(deviceLevel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeviceLevel in the database
        List<DeviceLevel> deviceLevelList = deviceLevelRepository.findAll();
        assertThat(deviceLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeviceLevel() throws Exception {
        // Initialize the database
        deviceLevelRepository.saveAndFlush(deviceLevel);

        int databaseSizeBeforeDelete = deviceLevelRepository.findAll().size();

        // Delete the deviceLevel
        restDeviceLevelMockMvc
            .perform(delete(ENTITY_API_URL_ID, deviceLevel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceLevel> deviceLevelList = deviceLevelRepository.findAll();
        assertThat(deviceLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
