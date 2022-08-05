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
import org.jhipster.blog.domain.PlantFactoryController;
import org.jhipster.blog.domain.enumeration.PlantSource;
import org.jhipster.blog.repository.PlantFactoryControllerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlantFactoryControllerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlantFactoryControllerResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final PlantSource DEFAULT_SOURCE = PlantSource.Automatic;
    private static final PlantSource UPDATED_SOURCE = PlantSource.Manual;

    private static final Integer DEFAULT_AIR_TEMPERATURE = 1;
    private static final Integer UPDATED_AIR_TEMPERATURE = 2;

    private static final Integer DEFAULT_RELATIVE_HUMIDITY = 1;
    private static final Integer UPDATED_RELATIVE_HUMIDITY = 2;

    private static final Integer DEFAULT_V_PD = 1;
    private static final Integer UPDATED_V_PD = 2;

    private static final Integer DEFAULT_EVAPOTRANSPIRATION = 1;
    private static final Integer UPDATED_EVAPOTRANSPIRATION = 2;

    private static final Integer DEFAULT_BAROMETRIC_PRESSURE = 1;
    private static final Integer UPDATED_BAROMETRIC_PRESSURE = 2;

    private static final Integer DEFAULT_SEA_LEVEL_PRESSURE = 1;
    private static final Integer UPDATED_SEA_LEVEL_PRESSURE = 2;

    private static final Integer DEFAULT_CO_2_LEVEL = 1;
    private static final Integer UPDATED_CO_2_LEVEL = 2;

    private static final Integer DEFAULT_NUTRIENT_TANK_LEVEL = 1;
    private static final Integer UPDATED_NUTRIENT_TANK_LEVEL = 2;

    private static final Integer DEFAULT_DEW_POINT = 1;
    private static final Integer UPDATED_DEW_POINT = 2;

    private static final Integer DEFAULT_HEAT_INDEX = 1;
    private static final Integer UPDATED_HEAT_INDEX = 2;

    private static final Integer DEFAULT_TURBIDITY = 1;
    private static final Integer UPDATED_TURBIDITY = 2;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/plant-factory-controllers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlantFactoryControllerRepository plantFactoryControllerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlantFactoryControllerMockMvc;

    private PlantFactoryController plantFactoryController;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlantFactoryController createEntity(EntityManager em) {
        PlantFactoryController plantFactoryController = new PlantFactoryController()
            .gUID(DEFAULT_G_UID)
            .source(DEFAULT_SOURCE)
            .airTemperature(DEFAULT_AIR_TEMPERATURE)
            .relativeHumidity(DEFAULT_RELATIVE_HUMIDITY)
            .vPD(DEFAULT_V_PD)
            .evapotranspiration(DEFAULT_EVAPOTRANSPIRATION)
            .barometricPressure(DEFAULT_BAROMETRIC_PRESSURE)
            .seaLevelPressure(DEFAULT_SEA_LEVEL_PRESSURE)
            .co2Level(DEFAULT_CO_2_LEVEL)
            .nutrientTankLevel(DEFAULT_NUTRIENT_TANK_LEVEL)
            .dewPoint(DEFAULT_DEW_POINT)
            .heatIndex(DEFAULT_HEAT_INDEX)
            .turbidity(DEFAULT_TURBIDITY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return plantFactoryController;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlantFactoryController createUpdatedEntity(EntityManager em) {
        PlantFactoryController plantFactoryController = new PlantFactoryController()
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .airTemperature(UPDATED_AIR_TEMPERATURE)
            .relativeHumidity(UPDATED_RELATIVE_HUMIDITY)
            .vPD(UPDATED_V_PD)
            .evapotranspiration(UPDATED_EVAPOTRANSPIRATION)
            .barometricPressure(UPDATED_BAROMETRIC_PRESSURE)
            .seaLevelPressure(UPDATED_SEA_LEVEL_PRESSURE)
            .co2Level(UPDATED_CO_2_LEVEL)
            .nutrientTankLevel(UPDATED_NUTRIENT_TANK_LEVEL)
            .dewPoint(UPDATED_DEW_POINT)
            .heatIndex(UPDATED_HEAT_INDEX)
            .turbidity(UPDATED_TURBIDITY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return plantFactoryController;
    }

    @BeforeEach
    public void initTest() {
        plantFactoryController = createEntity(em);
    }

    @Test
    @Transactional
    void createPlantFactoryController() throws Exception {
        int databaseSizeBeforeCreate = plantFactoryControllerRepository.findAll().size();
        // Create the PlantFactoryController
        restPlantFactoryControllerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plantFactoryController))
            )
            .andExpect(status().isCreated());

        // Validate the PlantFactoryController in the database
        List<PlantFactoryController> plantFactoryControllerList = plantFactoryControllerRepository.findAll();
        assertThat(plantFactoryControllerList).hasSize(databaseSizeBeforeCreate + 1);
        PlantFactoryController testPlantFactoryController = plantFactoryControllerList.get(plantFactoryControllerList.size() - 1);
        assertThat(testPlantFactoryController.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPlantFactoryController.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testPlantFactoryController.getAirTemperature()).isEqualTo(DEFAULT_AIR_TEMPERATURE);
        assertThat(testPlantFactoryController.getRelativeHumidity()).isEqualTo(DEFAULT_RELATIVE_HUMIDITY);
        assertThat(testPlantFactoryController.getvPD()).isEqualTo(DEFAULT_V_PD);
        assertThat(testPlantFactoryController.getEvapotranspiration()).isEqualTo(DEFAULT_EVAPOTRANSPIRATION);
        assertThat(testPlantFactoryController.getBarometricPressure()).isEqualTo(DEFAULT_BAROMETRIC_PRESSURE);
        assertThat(testPlantFactoryController.getSeaLevelPressure()).isEqualTo(DEFAULT_SEA_LEVEL_PRESSURE);
        assertThat(testPlantFactoryController.getCo2Level()).isEqualTo(DEFAULT_CO_2_LEVEL);
        assertThat(testPlantFactoryController.getNutrientTankLevel()).isEqualTo(DEFAULT_NUTRIENT_TANK_LEVEL);
        assertThat(testPlantFactoryController.getDewPoint()).isEqualTo(DEFAULT_DEW_POINT);
        assertThat(testPlantFactoryController.getHeatIndex()).isEqualTo(DEFAULT_HEAT_INDEX);
        assertThat(testPlantFactoryController.getTurbidity()).isEqualTo(DEFAULT_TURBIDITY);
        assertThat(testPlantFactoryController.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPlantFactoryController.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPlantFactoryController.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPlantFactoryController.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPlantFactoryControllerWithExistingId() throws Exception {
        // Create the PlantFactoryController with an existing ID
        plantFactoryController.setId(1L);

        int databaseSizeBeforeCreate = plantFactoryControllerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlantFactoryControllerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plantFactoryController))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantFactoryController in the database
        List<PlantFactoryController> plantFactoryControllerList = plantFactoryControllerRepository.findAll();
        assertThat(plantFactoryControllerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlantFactoryControllers() throws Exception {
        // Initialize the database
        plantFactoryControllerRepository.saveAndFlush(plantFactoryController);

        // Get all the plantFactoryControllerList
        restPlantFactoryControllerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plantFactoryController.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].airTemperature").value(hasItem(DEFAULT_AIR_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].relativeHumidity").value(hasItem(DEFAULT_RELATIVE_HUMIDITY)))
            .andExpect(jsonPath("$.[*].vPD").value(hasItem(DEFAULT_V_PD)))
            .andExpect(jsonPath("$.[*].evapotranspiration").value(hasItem(DEFAULT_EVAPOTRANSPIRATION)))
            .andExpect(jsonPath("$.[*].barometricPressure").value(hasItem(DEFAULT_BAROMETRIC_PRESSURE)))
            .andExpect(jsonPath("$.[*].seaLevelPressure").value(hasItem(DEFAULT_SEA_LEVEL_PRESSURE)))
            .andExpect(jsonPath("$.[*].co2Level").value(hasItem(DEFAULT_CO_2_LEVEL)))
            .andExpect(jsonPath("$.[*].nutrientTankLevel").value(hasItem(DEFAULT_NUTRIENT_TANK_LEVEL)))
            .andExpect(jsonPath("$.[*].dewPoint").value(hasItem(DEFAULT_DEW_POINT)))
            .andExpect(jsonPath("$.[*].heatIndex").value(hasItem(DEFAULT_HEAT_INDEX)))
            .andExpect(jsonPath("$.[*].turbidity").value(hasItem(DEFAULT_TURBIDITY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPlantFactoryController() throws Exception {
        // Initialize the database
        plantFactoryControllerRepository.saveAndFlush(plantFactoryController);

        // Get the plantFactoryController
        restPlantFactoryControllerMockMvc
            .perform(get(ENTITY_API_URL_ID, plantFactoryController.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plantFactoryController.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.airTemperature").value(DEFAULT_AIR_TEMPERATURE))
            .andExpect(jsonPath("$.relativeHumidity").value(DEFAULT_RELATIVE_HUMIDITY))
            .andExpect(jsonPath("$.vPD").value(DEFAULT_V_PD))
            .andExpect(jsonPath("$.evapotranspiration").value(DEFAULT_EVAPOTRANSPIRATION))
            .andExpect(jsonPath("$.barometricPressure").value(DEFAULT_BAROMETRIC_PRESSURE))
            .andExpect(jsonPath("$.seaLevelPressure").value(DEFAULT_SEA_LEVEL_PRESSURE))
            .andExpect(jsonPath("$.co2Level").value(DEFAULT_CO_2_LEVEL))
            .andExpect(jsonPath("$.nutrientTankLevel").value(DEFAULT_NUTRIENT_TANK_LEVEL))
            .andExpect(jsonPath("$.dewPoint").value(DEFAULT_DEW_POINT))
            .andExpect(jsonPath("$.heatIndex").value(DEFAULT_HEAT_INDEX))
            .andExpect(jsonPath("$.turbidity").value(DEFAULT_TURBIDITY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPlantFactoryController() throws Exception {
        // Get the plantFactoryController
        restPlantFactoryControllerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlantFactoryController() throws Exception {
        // Initialize the database
        plantFactoryControllerRepository.saveAndFlush(plantFactoryController);

        int databaseSizeBeforeUpdate = plantFactoryControllerRepository.findAll().size();

        // Update the plantFactoryController
        PlantFactoryController updatedPlantFactoryController = plantFactoryControllerRepository
            .findById(plantFactoryController.getId())
            .get();
        // Disconnect from session so that the updates on updatedPlantFactoryController are not directly saved in db
        em.detach(updatedPlantFactoryController);
        updatedPlantFactoryController
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .airTemperature(UPDATED_AIR_TEMPERATURE)
            .relativeHumidity(UPDATED_RELATIVE_HUMIDITY)
            .vPD(UPDATED_V_PD)
            .evapotranspiration(UPDATED_EVAPOTRANSPIRATION)
            .barometricPressure(UPDATED_BAROMETRIC_PRESSURE)
            .seaLevelPressure(UPDATED_SEA_LEVEL_PRESSURE)
            .co2Level(UPDATED_CO_2_LEVEL)
            .nutrientTankLevel(UPDATED_NUTRIENT_TANK_LEVEL)
            .dewPoint(UPDATED_DEW_POINT)
            .heatIndex(UPDATED_HEAT_INDEX)
            .turbidity(UPDATED_TURBIDITY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPlantFactoryControllerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlantFactoryController.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlantFactoryController))
            )
            .andExpect(status().isOk());

        // Validate the PlantFactoryController in the database
        List<PlantFactoryController> plantFactoryControllerList = plantFactoryControllerRepository.findAll();
        assertThat(plantFactoryControllerList).hasSize(databaseSizeBeforeUpdate);
        PlantFactoryController testPlantFactoryController = plantFactoryControllerList.get(plantFactoryControllerList.size() - 1);
        assertThat(testPlantFactoryController.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPlantFactoryController.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testPlantFactoryController.getAirTemperature()).isEqualTo(UPDATED_AIR_TEMPERATURE);
        assertThat(testPlantFactoryController.getRelativeHumidity()).isEqualTo(UPDATED_RELATIVE_HUMIDITY);
        assertThat(testPlantFactoryController.getvPD()).isEqualTo(UPDATED_V_PD);
        assertThat(testPlantFactoryController.getEvapotranspiration()).isEqualTo(UPDATED_EVAPOTRANSPIRATION);
        assertThat(testPlantFactoryController.getBarometricPressure()).isEqualTo(UPDATED_BAROMETRIC_PRESSURE);
        assertThat(testPlantFactoryController.getSeaLevelPressure()).isEqualTo(UPDATED_SEA_LEVEL_PRESSURE);
        assertThat(testPlantFactoryController.getCo2Level()).isEqualTo(UPDATED_CO_2_LEVEL);
        assertThat(testPlantFactoryController.getNutrientTankLevel()).isEqualTo(UPDATED_NUTRIENT_TANK_LEVEL);
        assertThat(testPlantFactoryController.getDewPoint()).isEqualTo(UPDATED_DEW_POINT);
        assertThat(testPlantFactoryController.getHeatIndex()).isEqualTo(UPDATED_HEAT_INDEX);
        assertThat(testPlantFactoryController.getTurbidity()).isEqualTo(UPDATED_TURBIDITY);
        assertThat(testPlantFactoryController.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPlantFactoryController.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPlantFactoryController.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPlantFactoryController.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPlantFactoryController() throws Exception {
        int databaseSizeBeforeUpdate = plantFactoryControllerRepository.findAll().size();
        plantFactoryController.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantFactoryControllerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plantFactoryController.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plantFactoryController))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantFactoryController in the database
        List<PlantFactoryController> plantFactoryControllerList = plantFactoryControllerRepository.findAll();
        assertThat(plantFactoryControllerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlantFactoryController() throws Exception {
        int databaseSizeBeforeUpdate = plantFactoryControllerRepository.findAll().size();
        plantFactoryController.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantFactoryControllerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plantFactoryController))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantFactoryController in the database
        List<PlantFactoryController> plantFactoryControllerList = plantFactoryControllerRepository.findAll();
        assertThat(plantFactoryControllerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlantFactoryController() throws Exception {
        int databaseSizeBeforeUpdate = plantFactoryControllerRepository.findAll().size();
        plantFactoryController.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantFactoryControllerMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plantFactoryController))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlantFactoryController in the database
        List<PlantFactoryController> plantFactoryControllerList = plantFactoryControllerRepository.findAll();
        assertThat(plantFactoryControllerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlantFactoryControllerWithPatch() throws Exception {
        // Initialize the database
        plantFactoryControllerRepository.saveAndFlush(plantFactoryController);

        int databaseSizeBeforeUpdate = plantFactoryControllerRepository.findAll().size();

        // Update the plantFactoryController using partial update
        PlantFactoryController partialUpdatedPlantFactoryController = new PlantFactoryController();
        partialUpdatedPlantFactoryController.setId(plantFactoryController.getId());

        partialUpdatedPlantFactoryController
            .airTemperature(UPDATED_AIR_TEMPERATURE)
            .relativeHumidity(UPDATED_RELATIVE_HUMIDITY)
            .seaLevelPressure(UPDATED_SEA_LEVEL_PRESSURE)
            .co2Level(UPDATED_CO_2_LEVEL)
            .nutrientTankLevel(UPDATED_NUTRIENT_TANK_LEVEL)
            .dewPoint(UPDATED_DEW_POINT)
            .heatIndex(UPDATED_HEAT_INDEX)
            .turbidity(UPDATED_TURBIDITY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPlantFactoryControllerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlantFactoryController.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlantFactoryController))
            )
            .andExpect(status().isOk());

        // Validate the PlantFactoryController in the database
        List<PlantFactoryController> plantFactoryControllerList = plantFactoryControllerRepository.findAll();
        assertThat(plantFactoryControllerList).hasSize(databaseSizeBeforeUpdate);
        PlantFactoryController testPlantFactoryController = plantFactoryControllerList.get(plantFactoryControllerList.size() - 1);
        assertThat(testPlantFactoryController.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPlantFactoryController.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testPlantFactoryController.getAirTemperature()).isEqualTo(UPDATED_AIR_TEMPERATURE);
        assertThat(testPlantFactoryController.getRelativeHumidity()).isEqualTo(UPDATED_RELATIVE_HUMIDITY);
        assertThat(testPlantFactoryController.getvPD()).isEqualTo(DEFAULT_V_PD);
        assertThat(testPlantFactoryController.getEvapotranspiration()).isEqualTo(DEFAULT_EVAPOTRANSPIRATION);
        assertThat(testPlantFactoryController.getBarometricPressure()).isEqualTo(DEFAULT_BAROMETRIC_PRESSURE);
        assertThat(testPlantFactoryController.getSeaLevelPressure()).isEqualTo(UPDATED_SEA_LEVEL_PRESSURE);
        assertThat(testPlantFactoryController.getCo2Level()).isEqualTo(UPDATED_CO_2_LEVEL);
        assertThat(testPlantFactoryController.getNutrientTankLevel()).isEqualTo(UPDATED_NUTRIENT_TANK_LEVEL);
        assertThat(testPlantFactoryController.getDewPoint()).isEqualTo(UPDATED_DEW_POINT);
        assertThat(testPlantFactoryController.getHeatIndex()).isEqualTo(UPDATED_HEAT_INDEX);
        assertThat(testPlantFactoryController.getTurbidity()).isEqualTo(UPDATED_TURBIDITY);
        assertThat(testPlantFactoryController.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPlantFactoryController.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPlantFactoryController.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPlantFactoryController.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePlantFactoryControllerWithPatch() throws Exception {
        // Initialize the database
        plantFactoryControllerRepository.saveAndFlush(plantFactoryController);

        int databaseSizeBeforeUpdate = plantFactoryControllerRepository.findAll().size();

        // Update the plantFactoryController using partial update
        PlantFactoryController partialUpdatedPlantFactoryController = new PlantFactoryController();
        partialUpdatedPlantFactoryController.setId(plantFactoryController.getId());

        partialUpdatedPlantFactoryController
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .airTemperature(UPDATED_AIR_TEMPERATURE)
            .relativeHumidity(UPDATED_RELATIVE_HUMIDITY)
            .vPD(UPDATED_V_PD)
            .evapotranspiration(UPDATED_EVAPOTRANSPIRATION)
            .barometricPressure(UPDATED_BAROMETRIC_PRESSURE)
            .seaLevelPressure(UPDATED_SEA_LEVEL_PRESSURE)
            .co2Level(UPDATED_CO_2_LEVEL)
            .nutrientTankLevel(UPDATED_NUTRIENT_TANK_LEVEL)
            .dewPoint(UPDATED_DEW_POINT)
            .heatIndex(UPDATED_HEAT_INDEX)
            .turbidity(UPDATED_TURBIDITY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPlantFactoryControllerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlantFactoryController.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlantFactoryController))
            )
            .andExpect(status().isOk());

        // Validate the PlantFactoryController in the database
        List<PlantFactoryController> plantFactoryControllerList = plantFactoryControllerRepository.findAll();
        assertThat(plantFactoryControllerList).hasSize(databaseSizeBeforeUpdate);
        PlantFactoryController testPlantFactoryController = plantFactoryControllerList.get(plantFactoryControllerList.size() - 1);
        assertThat(testPlantFactoryController.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPlantFactoryController.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testPlantFactoryController.getAirTemperature()).isEqualTo(UPDATED_AIR_TEMPERATURE);
        assertThat(testPlantFactoryController.getRelativeHumidity()).isEqualTo(UPDATED_RELATIVE_HUMIDITY);
        assertThat(testPlantFactoryController.getvPD()).isEqualTo(UPDATED_V_PD);
        assertThat(testPlantFactoryController.getEvapotranspiration()).isEqualTo(UPDATED_EVAPOTRANSPIRATION);
        assertThat(testPlantFactoryController.getBarometricPressure()).isEqualTo(UPDATED_BAROMETRIC_PRESSURE);
        assertThat(testPlantFactoryController.getSeaLevelPressure()).isEqualTo(UPDATED_SEA_LEVEL_PRESSURE);
        assertThat(testPlantFactoryController.getCo2Level()).isEqualTo(UPDATED_CO_2_LEVEL);
        assertThat(testPlantFactoryController.getNutrientTankLevel()).isEqualTo(UPDATED_NUTRIENT_TANK_LEVEL);
        assertThat(testPlantFactoryController.getDewPoint()).isEqualTo(UPDATED_DEW_POINT);
        assertThat(testPlantFactoryController.getHeatIndex()).isEqualTo(UPDATED_HEAT_INDEX);
        assertThat(testPlantFactoryController.getTurbidity()).isEqualTo(UPDATED_TURBIDITY);
        assertThat(testPlantFactoryController.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPlantFactoryController.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPlantFactoryController.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPlantFactoryController.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPlantFactoryController() throws Exception {
        int databaseSizeBeforeUpdate = plantFactoryControllerRepository.findAll().size();
        plantFactoryController.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantFactoryControllerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plantFactoryController.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plantFactoryController))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantFactoryController in the database
        List<PlantFactoryController> plantFactoryControllerList = plantFactoryControllerRepository.findAll();
        assertThat(plantFactoryControllerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlantFactoryController() throws Exception {
        int databaseSizeBeforeUpdate = plantFactoryControllerRepository.findAll().size();
        plantFactoryController.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantFactoryControllerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plantFactoryController))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantFactoryController in the database
        List<PlantFactoryController> plantFactoryControllerList = plantFactoryControllerRepository.findAll();
        assertThat(plantFactoryControllerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlantFactoryController() throws Exception {
        int databaseSizeBeforeUpdate = plantFactoryControllerRepository.findAll().size();
        plantFactoryController.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantFactoryControllerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plantFactoryController))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlantFactoryController in the database
        List<PlantFactoryController> plantFactoryControllerList = plantFactoryControllerRepository.findAll();
        assertThat(plantFactoryControllerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlantFactoryController() throws Exception {
        // Initialize the database
        plantFactoryControllerRepository.saveAndFlush(plantFactoryController);

        int databaseSizeBeforeDelete = plantFactoryControllerRepository.findAll().size();

        // Delete the plantFactoryController
        restPlantFactoryControllerMockMvc
            .perform(delete(ENTITY_API_URL_ID, plantFactoryController.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlantFactoryController> plantFactoryControllerList = plantFactoryControllerRepository.findAll();
        assertThat(plantFactoryControllerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
