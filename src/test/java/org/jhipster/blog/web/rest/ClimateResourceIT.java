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
import org.jhipster.blog.domain.Climate;
import org.jhipster.blog.domain.enumeration.CliSource;
import org.jhipster.blog.repository.ClimateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClimateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClimateResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final CliSource DEFAULT_SOURCE = CliSource.Automatic;
    private static final CliSource UPDATED_SOURCE = CliSource.Manual;

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

    private static final Integer DEFAULT_DEW_POINT = 1;
    private static final Integer UPDATED_DEW_POINT = 2;

    private static final Integer DEFAULT_SOLAR_RADIATION = 1;
    private static final Integer UPDATED_SOLAR_RADIATION = 2;

    private static final Integer DEFAULT_HEAT_INDEX = 1;
    private static final Integer UPDATED_HEAT_INDEX = 2;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/climates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClimateRepository climateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClimateMockMvc;

    private Climate climate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Climate createEntity(EntityManager em) {
        Climate climate = new Climate()
            .gUID(DEFAULT_G_UID)
            .source(DEFAULT_SOURCE)
            .airTemperature(DEFAULT_AIR_TEMPERATURE)
            .relativeHumidity(DEFAULT_RELATIVE_HUMIDITY)
            .vPD(DEFAULT_V_PD)
            .evapotranspiration(DEFAULT_EVAPOTRANSPIRATION)
            .barometricPressure(DEFAULT_BAROMETRIC_PRESSURE)
            .seaLevelPressure(DEFAULT_SEA_LEVEL_PRESSURE)
            .co2Level(DEFAULT_CO_2_LEVEL)
            .dewPoint(DEFAULT_DEW_POINT)
            .solarRadiation(DEFAULT_SOLAR_RADIATION)
            .heatIndex(DEFAULT_HEAT_INDEX)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return climate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Climate createUpdatedEntity(EntityManager em) {
        Climate climate = new Climate()
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .airTemperature(UPDATED_AIR_TEMPERATURE)
            .relativeHumidity(UPDATED_RELATIVE_HUMIDITY)
            .vPD(UPDATED_V_PD)
            .evapotranspiration(UPDATED_EVAPOTRANSPIRATION)
            .barometricPressure(UPDATED_BAROMETRIC_PRESSURE)
            .seaLevelPressure(UPDATED_SEA_LEVEL_PRESSURE)
            .co2Level(UPDATED_CO_2_LEVEL)
            .dewPoint(UPDATED_DEW_POINT)
            .solarRadiation(UPDATED_SOLAR_RADIATION)
            .heatIndex(UPDATED_HEAT_INDEX)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return climate;
    }

    @BeforeEach
    public void initTest() {
        climate = createEntity(em);
    }

    @Test
    @Transactional
    void createClimate() throws Exception {
        int databaseSizeBeforeCreate = climateRepository.findAll().size();
        // Create the Climate
        restClimateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(climate)))
            .andExpect(status().isCreated());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeCreate + 1);
        Climate testClimate = climateList.get(climateList.size() - 1);
        assertThat(testClimate.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testClimate.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testClimate.getAirTemperature()).isEqualTo(DEFAULT_AIR_TEMPERATURE);
        assertThat(testClimate.getRelativeHumidity()).isEqualTo(DEFAULT_RELATIVE_HUMIDITY);
        assertThat(testClimate.getvPD()).isEqualTo(DEFAULT_V_PD);
        assertThat(testClimate.getEvapotranspiration()).isEqualTo(DEFAULT_EVAPOTRANSPIRATION);
        assertThat(testClimate.getBarometricPressure()).isEqualTo(DEFAULT_BAROMETRIC_PRESSURE);
        assertThat(testClimate.getSeaLevelPressure()).isEqualTo(DEFAULT_SEA_LEVEL_PRESSURE);
        assertThat(testClimate.getCo2Level()).isEqualTo(DEFAULT_CO_2_LEVEL);
        assertThat(testClimate.getDewPoint()).isEqualTo(DEFAULT_DEW_POINT);
        assertThat(testClimate.getSolarRadiation()).isEqualTo(DEFAULT_SOLAR_RADIATION);
        assertThat(testClimate.getHeatIndex()).isEqualTo(DEFAULT_HEAT_INDEX);
        assertThat(testClimate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClimate.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testClimate.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testClimate.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createClimateWithExistingId() throws Exception {
        // Create the Climate with an existing ID
        climate.setId(1L);

        int databaseSizeBeforeCreate = climateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClimateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(climate)))
            .andExpect(status().isBadRequest());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClimates() throws Exception {
        // Initialize the database
        climateRepository.saveAndFlush(climate);

        // Get all the climateList
        restClimateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(climate.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].airTemperature").value(hasItem(DEFAULT_AIR_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].relativeHumidity").value(hasItem(DEFAULT_RELATIVE_HUMIDITY)))
            .andExpect(jsonPath("$.[*].vPD").value(hasItem(DEFAULT_V_PD)))
            .andExpect(jsonPath("$.[*].evapotranspiration").value(hasItem(DEFAULT_EVAPOTRANSPIRATION)))
            .andExpect(jsonPath("$.[*].barometricPressure").value(hasItem(DEFAULT_BAROMETRIC_PRESSURE)))
            .andExpect(jsonPath("$.[*].seaLevelPressure").value(hasItem(DEFAULT_SEA_LEVEL_PRESSURE)))
            .andExpect(jsonPath("$.[*].co2Level").value(hasItem(DEFAULT_CO_2_LEVEL)))
            .andExpect(jsonPath("$.[*].dewPoint").value(hasItem(DEFAULT_DEW_POINT)))
            .andExpect(jsonPath("$.[*].solarRadiation").value(hasItem(DEFAULT_SOLAR_RADIATION)))
            .andExpect(jsonPath("$.[*].heatIndex").value(hasItem(DEFAULT_HEAT_INDEX)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getClimate() throws Exception {
        // Initialize the database
        climateRepository.saveAndFlush(climate);

        // Get the climate
        restClimateMockMvc
            .perform(get(ENTITY_API_URL_ID, climate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(climate.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.airTemperature").value(DEFAULT_AIR_TEMPERATURE))
            .andExpect(jsonPath("$.relativeHumidity").value(DEFAULT_RELATIVE_HUMIDITY))
            .andExpect(jsonPath("$.vPD").value(DEFAULT_V_PD))
            .andExpect(jsonPath("$.evapotranspiration").value(DEFAULT_EVAPOTRANSPIRATION))
            .andExpect(jsonPath("$.barometricPressure").value(DEFAULT_BAROMETRIC_PRESSURE))
            .andExpect(jsonPath("$.seaLevelPressure").value(DEFAULT_SEA_LEVEL_PRESSURE))
            .andExpect(jsonPath("$.co2Level").value(DEFAULT_CO_2_LEVEL))
            .andExpect(jsonPath("$.dewPoint").value(DEFAULT_DEW_POINT))
            .andExpect(jsonPath("$.solarRadiation").value(DEFAULT_SOLAR_RADIATION))
            .andExpect(jsonPath("$.heatIndex").value(DEFAULT_HEAT_INDEX))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingClimate() throws Exception {
        // Get the climate
        restClimateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClimate() throws Exception {
        // Initialize the database
        climateRepository.saveAndFlush(climate);

        int databaseSizeBeforeUpdate = climateRepository.findAll().size();

        // Update the climate
        Climate updatedClimate = climateRepository.findById(climate.getId()).get();
        // Disconnect from session so that the updates on updatedClimate are not directly saved in db
        em.detach(updatedClimate);
        updatedClimate
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .airTemperature(UPDATED_AIR_TEMPERATURE)
            .relativeHumidity(UPDATED_RELATIVE_HUMIDITY)
            .vPD(UPDATED_V_PD)
            .evapotranspiration(UPDATED_EVAPOTRANSPIRATION)
            .barometricPressure(UPDATED_BAROMETRIC_PRESSURE)
            .seaLevelPressure(UPDATED_SEA_LEVEL_PRESSURE)
            .co2Level(UPDATED_CO_2_LEVEL)
            .dewPoint(UPDATED_DEW_POINT)
            .solarRadiation(UPDATED_SOLAR_RADIATION)
            .heatIndex(UPDATED_HEAT_INDEX)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restClimateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClimate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClimate))
            )
            .andExpect(status().isOk());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeUpdate);
        Climate testClimate = climateList.get(climateList.size() - 1);
        assertThat(testClimate.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testClimate.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testClimate.getAirTemperature()).isEqualTo(UPDATED_AIR_TEMPERATURE);
        assertThat(testClimate.getRelativeHumidity()).isEqualTo(UPDATED_RELATIVE_HUMIDITY);
        assertThat(testClimate.getvPD()).isEqualTo(UPDATED_V_PD);
        assertThat(testClimate.getEvapotranspiration()).isEqualTo(UPDATED_EVAPOTRANSPIRATION);
        assertThat(testClimate.getBarometricPressure()).isEqualTo(UPDATED_BAROMETRIC_PRESSURE);
        assertThat(testClimate.getSeaLevelPressure()).isEqualTo(UPDATED_SEA_LEVEL_PRESSURE);
        assertThat(testClimate.getCo2Level()).isEqualTo(UPDATED_CO_2_LEVEL);
        assertThat(testClimate.getDewPoint()).isEqualTo(UPDATED_DEW_POINT);
        assertThat(testClimate.getSolarRadiation()).isEqualTo(UPDATED_SOLAR_RADIATION);
        assertThat(testClimate.getHeatIndex()).isEqualTo(UPDATED_HEAT_INDEX);
        assertThat(testClimate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClimate.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testClimate.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testClimate.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingClimate() throws Exception {
        int databaseSizeBeforeUpdate = climateRepository.findAll().size();
        climate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClimateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, climate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(climate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClimate() throws Exception {
        int databaseSizeBeforeUpdate = climateRepository.findAll().size();
        climate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClimateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(climate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClimate() throws Exception {
        int databaseSizeBeforeUpdate = climateRepository.findAll().size();
        climate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClimateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(climate)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClimateWithPatch() throws Exception {
        // Initialize the database
        climateRepository.saveAndFlush(climate);

        int databaseSizeBeforeUpdate = climateRepository.findAll().size();

        // Update the climate using partial update
        Climate partialUpdatedClimate = new Climate();
        partialUpdatedClimate.setId(climate.getId());

        partialUpdatedClimate
            .gUID(UPDATED_G_UID)
            .airTemperature(UPDATED_AIR_TEMPERATURE)
            .vPD(UPDATED_V_PD)
            .barometricPressure(UPDATED_BAROMETRIC_PRESSURE)
            .solarRadiation(UPDATED_SOLAR_RADIATION)
            .heatIndex(UPDATED_HEAT_INDEX)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON);

        restClimateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClimate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClimate))
            )
            .andExpect(status().isOk());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeUpdate);
        Climate testClimate = climateList.get(climateList.size() - 1);
        assertThat(testClimate.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testClimate.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testClimate.getAirTemperature()).isEqualTo(UPDATED_AIR_TEMPERATURE);
        assertThat(testClimate.getRelativeHumidity()).isEqualTo(DEFAULT_RELATIVE_HUMIDITY);
        assertThat(testClimate.getvPD()).isEqualTo(UPDATED_V_PD);
        assertThat(testClimate.getEvapotranspiration()).isEqualTo(DEFAULT_EVAPOTRANSPIRATION);
        assertThat(testClimate.getBarometricPressure()).isEqualTo(UPDATED_BAROMETRIC_PRESSURE);
        assertThat(testClimate.getSeaLevelPressure()).isEqualTo(DEFAULT_SEA_LEVEL_PRESSURE);
        assertThat(testClimate.getCo2Level()).isEqualTo(DEFAULT_CO_2_LEVEL);
        assertThat(testClimate.getDewPoint()).isEqualTo(DEFAULT_DEW_POINT);
        assertThat(testClimate.getSolarRadiation()).isEqualTo(UPDATED_SOLAR_RADIATION);
        assertThat(testClimate.getHeatIndex()).isEqualTo(UPDATED_HEAT_INDEX);
        assertThat(testClimate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClimate.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testClimate.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testClimate.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateClimateWithPatch() throws Exception {
        // Initialize the database
        climateRepository.saveAndFlush(climate);

        int databaseSizeBeforeUpdate = climateRepository.findAll().size();

        // Update the climate using partial update
        Climate partialUpdatedClimate = new Climate();
        partialUpdatedClimate.setId(climate.getId());

        partialUpdatedClimate
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .airTemperature(UPDATED_AIR_TEMPERATURE)
            .relativeHumidity(UPDATED_RELATIVE_HUMIDITY)
            .vPD(UPDATED_V_PD)
            .evapotranspiration(UPDATED_EVAPOTRANSPIRATION)
            .barometricPressure(UPDATED_BAROMETRIC_PRESSURE)
            .seaLevelPressure(UPDATED_SEA_LEVEL_PRESSURE)
            .co2Level(UPDATED_CO_2_LEVEL)
            .dewPoint(UPDATED_DEW_POINT)
            .solarRadiation(UPDATED_SOLAR_RADIATION)
            .heatIndex(UPDATED_HEAT_INDEX)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restClimateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClimate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClimate))
            )
            .andExpect(status().isOk());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeUpdate);
        Climate testClimate = climateList.get(climateList.size() - 1);
        assertThat(testClimate.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testClimate.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testClimate.getAirTemperature()).isEqualTo(UPDATED_AIR_TEMPERATURE);
        assertThat(testClimate.getRelativeHumidity()).isEqualTo(UPDATED_RELATIVE_HUMIDITY);
        assertThat(testClimate.getvPD()).isEqualTo(UPDATED_V_PD);
        assertThat(testClimate.getEvapotranspiration()).isEqualTo(UPDATED_EVAPOTRANSPIRATION);
        assertThat(testClimate.getBarometricPressure()).isEqualTo(UPDATED_BAROMETRIC_PRESSURE);
        assertThat(testClimate.getSeaLevelPressure()).isEqualTo(UPDATED_SEA_LEVEL_PRESSURE);
        assertThat(testClimate.getCo2Level()).isEqualTo(UPDATED_CO_2_LEVEL);
        assertThat(testClimate.getDewPoint()).isEqualTo(UPDATED_DEW_POINT);
        assertThat(testClimate.getSolarRadiation()).isEqualTo(UPDATED_SOLAR_RADIATION);
        assertThat(testClimate.getHeatIndex()).isEqualTo(UPDATED_HEAT_INDEX);
        assertThat(testClimate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClimate.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testClimate.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testClimate.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingClimate() throws Exception {
        int databaseSizeBeforeUpdate = climateRepository.findAll().size();
        climate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClimateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, climate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(climate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClimate() throws Exception {
        int databaseSizeBeforeUpdate = climateRepository.findAll().size();
        climate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClimateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(climate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClimate() throws Exception {
        int databaseSizeBeforeUpdate = climateRepository.findAll().size();
        climate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClimateMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(climate)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClimate() throws Exception {
        // Initialize the database
        climateRepository.saveAndFlush(climate);

        int databaseSizeBeforeDelete = climateRepository.findAll().size();

        // Delete the climate
        restClimateMockMvc
            .perform(delete(ENTITY_API_URL_ID, climate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
