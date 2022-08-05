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
import org.jhipster.blog.domain.Farm;
import org.jhipster.blog.domain.enumeration.FarmType;
import org.jhipster.blog.repository.FarmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FarmResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FarmResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_FARM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FARM_NAME = "BBBBBBBBBB";

    private static final FarmType DEFAULT_FARM_TYPE = FarmType.Organic;
    private static final FarmType UPDATED_FARM_TYPE = FarmType.Farm;

    private static final String DEFAULT_FARM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FARM_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/farms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFarmMockMvc;

    private Farm farm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Farm createEntity(EntityManager em) {
        Farm farm = new Farm()
            .gUID(DEFAULT_G_UID)
            .farmName(DEFAULT_FARM_NAME)
            .farmType(DEFAULT_FARM_TYPE)
            .farmDescription(DEFAULT_FARM_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return farm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Farm createUpdatedEntity(EntityManager em) {
        Farm farm = new Farm()
            .gUID(UPDATED_G_UID)
            .farmName(UPDATED_FARM_NAME)
            .farmType(UPDATED_FARM_TYPE)
            .farmDescription(UPDATED_FARM_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return farm;
    }

    @BeforeEach
    public void initTest() {
        farm = createEntity(em);
    }

    @Test
    @Transactional
    void createFarm() throws Exception {
        int databaseSizeBeforeCreate = farmRepository.findAll().size();
        // Create the Farm
        restFarmMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farm)))
            .andExpect(status().isCreated());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeCreate + 1);
        Farm testFarm = farmList.get(farmList.size() - 1);
        assertThat(testFarm.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testFarm.getFarmName()).isEqualTo(DEFAULT_FARM_NAME);
        assertThat(testFarm.getFarmType()).isEqualTo(DEFAULT_FARM_TYPE);
        assertThat(testFarm.getFarmDescription()).isEqualTo(DEFAULT_FARM_DESCRIPTION);
        assertThat(testFarm.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFarm.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testFarm.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFarm.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createFarmWithExistingId() throws Exception {
        // Create the Farm with an existing ID
        farm.setId(1L);

        int databaseSizeBeforeCreate = farmRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFarmMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farm)))
            .andExpect(status().isBadRequest());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFarms() throws Exception {
        // Initialize the database
        farmRepository.saveAndFlush(farm);

        // Get all the farmList
        restFarmMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(farm.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].farmName").value(hasItem(DEFAULT_FARM_NAME)))
            .andExpect(jsonPath("$.[*].farmType").value(hasItem(DEFAULT_FARM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].farmDescription").value(hasItem(DEFAULT_FARM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getFarm() throws Exception {
        // Initialize the database
        farmRepository.saveAndFlush(farm);

        // Get the farm
        restFarmMockMvc
            .perform(get(ENTITY_API_URL_ID, farm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(farm.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.farmName").value(DEFAULT_FARM_NAME))
            .andExpect(jsonPath("$.farmType").value(DEFAULT_FARM_TYPE.toString()))
            .andExpect(jsonPath("$.farmDescription").value(DEFAULT_FARM_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingFarm() throws Exception {
        // Get the farm
        restFarmMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFarm() throws Exception {
        // Initialize the database
        farmRepository.saveAndFlush(farm);

        int databaseSizeBeforeUpdate = farmRepository.findAll().size();

        // Update the farm
        Farm updatedFarm = farmRepository.findById(farm.getId()).get();
        // Disconnect from session so that the updates on updatedFarm are not directly saved in db
        em.detach(updatedFarm);
        updatedFarm
            .gUID(UPDATED_G_UID)
            .farmName(UPDATED_FARM_NAME)
            .farmType(UPDATED_FARM_TYPE)
            .farmDescription(UPDATED_FARM_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restFarmMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFarm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFarm))
            )
            .andExpect(status().isOk());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeUpdate);
        Farm testFarm = farmList.get(farmList.size() - 1);
        assertThat(testFarm.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testFarm.getFarmName()).isEqualTo(UPDATED_FARM_NAME);
        assertThat(testFarm.getFarmType()).isEqualTo(UPDATED_FARM_TYPE);
        assertThat(testFarm.getFarmDescription()).isEqualTo(UPDATED_FARM_DESCRIPTION);
        assertThat(testFarm.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFarm.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testFarm.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFarm.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingFarm() throws Exception {
        int databaseSizeBeforeUpdate = farmRepository.findAll().size();
        farm.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmMockMvc
            .perform(
                put(ENTITY_API_URL_ID, farm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(farm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFarm() throws Exception {
        int databaseSizeBeforeUpdate = farmRepository.findAll().size();
        farm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(farm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFarm() throws Exception {
        int databaseSizeBeforeUpdate = farmRepository.findAll().size();
        farm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFarmWithPatch() throws Exception {
        // Initialize the database
        farmRepository.saveAndFlush(farm);

        int databaseSizeBeforeUpdate = farmRepository.findAll().size();

        // Update the farm using partial update
        Farm partialUpdatedFarm = new Farm();
        partialUpdatedFarm.setId(farm.getId());

        partialUpdatedFarm
            .farmName(UPDATED_FARM_NAME)
            .farmType(UPDATED_FARM_TYPE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restFarmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFarm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFarm))
            )
            .andExpect(status().isOk());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeUpdate);
        Farm testFarm = farmList.get(farmList.size() - 1);
        assertThat(testFarm.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testFarm.getFarmName()).isEqualTo(UPDATED_FARM_NAME);
        assertThat(testFarm.getFarmType()).isEqualTo(UPDATED_FARM_TYPE);
        assertThat(testFarm.getFarmDescription()).isEqualTo(DEFAULT_FARM_DESCRIPTION);
        assertThat(testFarm.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFarm.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testFarm.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFarm.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateFarmWithPatch() throws Exception {
        // Initialize the database
        farmRepository.saveAndFlush(farm);

        int databaseSizeBeforeUpdate = farmRepository.findAll().size();

        // Update the farm using partial update
        Farm partialUpdatedFarm = new Farm();
        partialUpdatedFarm.setId(farm.getId());

        partialUpdatedFarm
            .gUID(UPDATED_G_UID)
            .farmName(UPDATED_FARM_NAME)
            .farmType(UPDATED_FARM_TYPE)
            .farmDescription(UPDATED_FARM_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restFarmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFarm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFarm))
            )
            .andExpect(status().isOk());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeUpdate);
        Farm testFarm = farmList.get(farmList.size() - 1);
        assertThat(testFarm.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testFarm.getFarmName()).isEqualTo(UPDATED_FARM_NAME);
        assertThat(testFarm.getFarmType()).isEqualTo(UPDATED_FARM_TYPE);
        assertThat(testFarm.getFarmDescription()).isEqualTo(UPDATED_FARM_DESCRIPTION);
        assertThat(testFarm.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFarm.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testFarm.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFarm.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingFarm() throws Exception {
        int databaseSizeBeforeUpdate = farmRepository.findAll().size();
        farm.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, farm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(farm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFarm() throws Exception {
        int databaseSizeBeforeUpdate = farmRepository.findAll().size();
        farm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(farm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFarm() throws Exception {
        int databaseSizeBeforeUpdate = farmRepository.findAll().size();
        farm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(farm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFarm() throws Exception {
        // Initialize the database
        farmRepository.saveAndFlush(farm);

        int databaseSizeBeforeDelete = farmRepository.findAll().size();

        // Delete the farm
        restFarmMockMvc
            .perform(delete(ENTITY_API_URL_ID, farm.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
