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
import org.jhipster.blog.domain.PlantFactory;
import org.jhipster.blog.domain.enumeration.ProFarmType;
import org.jhipster.blog.domain.enumeration.ProSubType;
import org.jhipster.blog.repository.PlantFactoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlantFactoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlantFactoryResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_PLANT_FACTORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_FACTORY_NAME = "BBBBBBBBBB";

    private static final ProFarmType DEFAULT_PLANT_FACTORY_TYPE_ID = ProFarmType.SN_Shade;
    private static final ProFarmType UPDATED_PLANT_FACTORY_TYPE_ID = ProFarmType.Net;

    private static final ProSubType DEFAULT_PLANT_FACTORY_SUB_TYPE = ProSubType.Femto;
    private static final ProSubType UPDATED_PLANT_FACTORY_SUB_TYPE = ProSubType.Pico;

    private static final String DEFAULT_PLANT_FACTORY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_FACTORY_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/plant-factories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlantFactoryRepository plantFactoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlantFactoryMockMvc;

    private PlantFactory plantFactory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlantFactory createEntity(EntityManager em) {
        PlantFactory plantFactory = new PlantFactory()
            .gUID(DEFAULT_G_UID)
            .plantFactoryName(DEFAULT_PLANT_FACTORY_NAME)
            .plantFactoryTypeID(DEFAULT_PLANT_FACTORY_TYPE_ID)
            .plantFactorySubType(DEFAULT_PLANT_FACTORY_SUB_TYPE)
            .plantFactoryDescription(DEFAULT_PLANT_FACTORY_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return plantFactory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlantFactory createUpdatedEntity(EntityManager em) {
        PlantFactory plantFactory = new PlantFactory()
            .gUID(UPDATED_G_UID)
            .plantFactoryName(UPDATED_PLANT_FACTORY_NAME)
            .plantFactoryTypeID(UPDATED_PLANT_FACTORY_TYPE_ID)
            .plantFactorySubType(UPDATED_PLANT_FACTORY_SUB_TYPE)
            .plantFactoryDescription(UPDATED_PLANT_FACTORY_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return plantFactory;
    }

    @BeforeEach
    public void initTest() {
        plantFactory = createEntity(em);
    }

    @Test
    @Transactional
    void createPlantFactory() throws Exception {
        int databaseSizeBeforeCreate = plantFactoryRepository.findAll().size();
        // Create the PlantFactory
        restPlantFactoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plantFactory)))
            .andExpect(status().isCreated());

        // Validate the PlantFactory in the database
        List<PlantFactory> plantFactoryList = plantFactoryRepository.findAll();
        assertThat(plantFactoryList).hasSize(databaseSizeBeforeCreate + 1);
        PlantFactory testPlantFactory = plantFactoryList.get(plantFactoryList.size() - 1);
        assertThat(testPlantFactory.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPlantFactory.getPlantFactoryName()).isEqualTo(DEFAULT_PLANT_FACTORY_NAME);
        assertThat(testPlantFactory.getPlantFactoryTypeID()).isEqualTo(DEFAULT_PLANT_FACTORY_TYPE_ID);
        assertThat(testPlantFactory.getPlantFactorySubType()).isEqualTo(DEFAULT_PLANT_FACTORY_SUB_TYPE);
        assertThat(testPlantFactory.getPlantFactoryDescription()).isEqualTo(DEFAULT_PLANT_FACTORY_DESCRIPTION);
        assertThat(testPlantFactory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPlantFactory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPlantFactory.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPlantFactory.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPlantFactoryWithExistingId() throws Exception {
        // Create the PlantFactory with an existing ID
        plantFactory.setId(1L);

        int databaseSizeBeforeCreate = plantFactoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlantFactoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plantFactory)))
            .andExpect(status().isBadRequest());

        // Validate the PlantFactory in the database
        List<PlantFactory> plantFactoryList = plantFactoryRepository.findAll();
        assertThat(plantFactoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlantFactories() throws Exception {
        // Initialize the database
        plantFactoryRepository.saveAndFlush(plantFactory);

        // Get all the plantFactoryList
        restPlantFactoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plantFactory.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].plantFactoryName").value(hasItem(DEFAULT_PLANT_FACTORY_NAME)))
            .andExpect(jsonPath("$.[*].plantFactoryTypeID").value(hasItem(DEFAULT_PLANT_FACTORY_TYPE_ID.toString())))
            .andExpect(jsonPath("$.[*].plantFactorySubType").value(hasItem(DEFAULT_PLANT_FACTORY_SUB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].plantFactoryDescription").value(hasItem(DEFAULT_PLANT_FACTORY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPlantFactory() throws Exception {
        // Initialize the database
        plantFactoryRepository.saveAndFlush(plantFactory);

        // Get the plantFactory
        restPlantFactoryMockMvc
            .perform(get(ENTITY_API_URL_ID, plantFactory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plantFactory.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.plantFactoryName").value(DEFAULT_PLANT_FACTORY_NAME))
            .andExpect(jsonPath("$.plantFactoryTypeID").value(DEFAULT_PLANT_FACTORY_TYPE_ID.toString()))
            .andExpect(jsonPath("$.plantFactorySubType").value(DEFAULT_PLANT_FACTORY_SUB_TYPE.toString()))
            .andExpect(jsonPath("$.plantFactoryDescription").value(DEFAULT_PLANT_FACTORY_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPlantFactory() throws Exception {
        // Get the plantFactory
        restPlantFactoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlantFactory() throws Exception {
        // Initialize the database
        plantFactoryRepository.saveAndFlush(plantFactory);

        int databaseSizeBeforeUpdate = plantFactoryRepository.findAll().size();

        // Update the plantFactory
        PlantFactory updatedPlantFactory = plantFactoryRepository.findById(plantFactory.getId()).get();
        // Disconnect from session so that the updates on updatedPlantFactory are not directly saved in db
        em.detach(updatedPlantFactory);
        updatedPlantFactory
            .gUID(UPDATED_G_UID)
            .plantFactoryName(UPDATED_PLANT_FACTORY_NAME)
            .plantFactoryTypeID(UPDATED_PLANT_FACTORY_TYPE_ID)
            .plantFactorySubType(UPDATED_PLANT_FACTORY_SUB_TYPE)
            .plantFactoryDescription(UPDATED_PLANT_FACTORY_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPlantFactoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlantFactory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlantFactory))
            )
            .andExpect(status().isOk());

        // Validate the PlantFactory in the database
        List<PlantFactory> plantFactoryList = plantFactoryRepository.findAll();
        assertThat(plantFactoryList).hasSize(databaseSizeBeforeUpdate);
        PlantFactory testPlantFactory = plantFactoryList.get(plantFactoryList.size() - 1);
        assertThat(testPlantFactory.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPlantFactory.getPlantFactoryName()).isEqualTo(UPDATED_PLANT_FACTORY_NAME);
        assertThat(testPlantFactory.getPlantFactoryTypeID()).isEqualTo(UPDATED_PLANT_FACTORY_TYPE_ID);
        assertThat(testPlantFactory.getPlantFactorySubType()).isEqualTo(UPDATED_PLANT_FACTORY_SUB_TYPE);
        assertThat(testPlantFactory.getPlantFactoryDescription()).isEqualTo(UPDATED_PLANT_FACTORY_DESCRIPTION);
        assertThat(testPlantFactory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPlantFactory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPlantFactory.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPlantFactory.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPlantFactory() throws Exception {
        int databaseSizeBeforeUpdate = plantFactoryRepository.findAll().size();
        plantFactory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantFactoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plantFactory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plantFactory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantFactory in the database
        List<PlantFactory> plantFactoryList = plantFactoryRepository.findAll();
        assertThat(plantFactoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlantFactory() throws Exception {
        int databaseSizeBeforeUpdate = plantFactoryRepository.findAll().size();
        plantFactory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantFactoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plantFactory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantFactory in the database
        List<PlantFactory> plantFactoryList = plantFactoryRepository.findAll();
        assertThat(plantFactoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlantFactory() throws Exception {
        int databaseSizeBeforeUpdate = plantFactoryRepository.findAll().size();
        plantFactory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantFactoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plantFactory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlantFactory in the database
        List<PlantFactory> plantFactoryList = plantFactoryRepository.findAll();
        assertThat(plantFactoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlantFactoryWithPatch() throws Exception {
        // Initialize the database
        plantFactoryRepository.saveAndFlush(plantFactory);

        int databaseSizeBeforeUpdate = plantFactoryRepository.findAll().size();

        // Update the plantFactory using partial update
        PlantFactory partialUpdatedPlantFactory = new PlantFactory();
        partialUpdatedPlantFactory.setId(plantFactory.getId());

        partialUpdatedPlantFactory
            .gUID(UPDATED_G_UID)
            .plantFactoryTypeID(UPDATED_PLANT_FACTORY_TYPE_ID)
            .plantFactoryDescription(UPDATED_PLANT_FACTORY_DESCRIPTION)
            .updatedOn(UPDATED_UPDATED_ON);

        restPlantFactoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlantFactory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlantFactory))
            )
            .andExpect(status().isOk());

        // Validate the PlantFactory in the database
        List<PlantFactory> plantFactoryList = plantFactoryRepository.findAll();
        assertThat(plantFactoryList).hasSize(databaseSizeBeforeUpdate);
        PlantFactory testPlantFactory = plantFactoryList.get(plantFactoryList.size() - 1);
        assertThat(testPlantFactory.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPlantFactory.getPlantFactoryName()).isEqualTo(DEFAULT_PLANT_FACTORY_NAME);
        assertThat(testPlantFactory.getPlantFactoryTypeID()).isEqualTo(UPDATED_PLANT_FACTORY_TYPE_ID);
        assertThat(testPlantFactory.getPlantFactorySubType()).isEqualTo(DEFAULT_PLANT_FACTORY_SUB_TYPE);
        assertThat(testPlantFactory.getPlantFactoryDescription()).isEqualTo(UPDATED_PLANT_FACTORY_DESCRIPTION);
        assertThat(testPlantFactory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPlantFactory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPlantFactory.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPlantFactory.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePlantFactoryWithPatch() throws Exception {
        // Initialize the database
        plantFactoryRepository.saveAndFlush(plantFactory);

        int databaseSizeBeforeUpdate = plantFactoryRepository.findAll().size();

        // Update the plantFactory using partial update
        PlantFactory partialUpdatedPlantFactory = new PlantFactory();
        partialUpdatedPlantFactory.setId(plantFactory.getId());

        partialUpdatedPlantFactory
            .gUID(UPDATED_G_UID)
            .plantFactoryName(UPDATED_PLANT_FACTORY_NAME)
            .plantFactoryTypeID(UPDATED_PLANT_FACTORY_TYPE_ID)
            .plantFactorySubType(UPDATED_PLANT_FACTORY_SUB_TYPE)
            .plantFactoryDescription(UPDATED_PLANT_FACTORY_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPlantFactoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlantFactory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlantFactory))
            )
            .andExpect(status().isOk());

        // Validate the PlantFactory in the database
        List<PlantFactory> plantFactoryList = plantFactoryRepository.findAll();
        assertThat(plantFactoryList).hasSize(databaseSizeBeforeUpdate);
        PlantFactory testPlantFactory = plantFactoryList.get(plantFactoryList.size() - 1);
        assertThat(testPlantFactory.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPlantFactory.getPlantFactoryName()).isEqualTo(UPDATED_PLANT_FACTORY_NAME);
        assertThat(testPlantFactory.getPlantFactoryTypeID()).isEqualTo(UPDATED_PLANT_FACTORY_TYPE_ID);
        assertThat(testPlantFactory.getPlantFactorySubType()).isEqualTo(UPDATED_PLANT_FACTORY_SUB_TYPE);
        assertThat(testPlantFactory.getPlantFactoryDescription()).isEqualTo(UPDATED_PLANT_FACTORY_DESCRIPTION);
        assertThat(testPlantFactory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPlantFactory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPlantFactory.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPlantFactory.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPlantFactory() throws Exception {
        int databaseSizeBeforeUpdate = plantFactoryRepository.findAll().size();
        plantFactory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantFactoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plantFactory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plantFactory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantFactory in the database
        List<PlantFactory> plantFactoryList = plantFactoryRepository.findAll();
        assertThat(plantFactoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlantFactory() throws Exception {
        int databaseSizeBeforeUpdate = plantFactoryRepository.findAll().size();
        plantFactory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantFactoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plantFactory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantFactory in the database
        List<PlantFactory> plantFactoryList = plantFactoryRepository.findAll();
        assertThat(plantFactoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlantFactory() throws Exception {
        int databaseSizeBeforeUpdate = plantFactoryRepository.findAll().size();
        plantFactory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantFactoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plantFactory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlantFactory in the database
        List<PlantFactory> plantFactoryList = plantFactoryRepository.findAll();
        assertThat(plantFactoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlantFactory() throws Exception {
        // Initialize the database
        plantFactoryRepository.saveAndFlush(plantFactory);

        int databaseSizeBeforeDelete = plantFactoryRepository.findAll().size();

        // Delete the plantFactory
        restPlantFactoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, plantFactory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlantFactory> plantFactoryList = plantFactoryRepository.findAll();
        assertThat(plantFactoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
