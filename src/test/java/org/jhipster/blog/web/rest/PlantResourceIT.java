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
import org.jhipster.blog.domain.Plant;
import org.jhipster.blog.domain.enumeration.HarvestMonth;
import org.jhipster.blog.domain.enumeration.Seeding;
import org.jhipster.blog.domain.enumeration.TransplantMonth;
import org.jhipster.blog.repository.PlantRepository;
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
 * Integration tests for the {@link PlantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlantResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_COMMON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMMON_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SCIENTIFIC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCIENTIFIC_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FAMILY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PLANT_SPACING = 1;
    private static final Integer UPDATED_PLANT_SPACING = 2;

    private static final Seeding DEFAULT_SEEDING_MONTH = Seeding.January;
    private static final Seeding UPDATED_SEEDING_MONTH = Seeding.February;

    private static final TransplantMonth DEFAULT_TRANSPLANT_MONTH = TransplantMonth.January;
    private static final TransplantMonth UPDATED_TRANSPLANT_MONTH = TransplantMonth.February;

    private static final HarvestMonth DEFAULT_HARVEST_MONTH = HarvestMonth.January;
    private static final HarvestMonth UPDATED_HARVEST_MONTH = HarvestMonth.February;

    private static final Long DEFAULT_ORIGIN_COUNTRY = 1L;
    private static final Long UPDATED_ORIGIN_COUNTRY = 2L;

    private static final Integer DEFAULT_YEARLY_CROPS = 1;
    private static final Integer UPDATED_YEARLY_CROPS = 2;

    private static final Integer DEFAULT_NATIVE_TEMPERATURE = 1;
    private static final Integer UPDATED_NATIVE_TEMPERATURE = 2;

    private static final Integer DEFAULT_NATIVE_HUMIDITY = 1;
    private static final Integer UPDATED_NATIVE_HUMIDITY = 2;

    private static final Integer DEFAULT_NATIVE_DAY_DURATION = 1;
    private static final Integer UPDATED_NATIVE_DAY_DURATION = 2;

    private static final Integer DEFAULT_NATIVE_NIGHT_DURATION = 1;
    private static final Integer UPDATED_NATIVE_NIGHT_DURATION = 2;

    private static final Integer DEFAULT_NATIVE_SOIL_MOISTURE = 1;
    private static final Integer UPDATED_NATIVE_SOIL_MOISTURE = 2;

    private static final Integer DEFAULT_PLANTING_PERIOD = 1;
    private static final Integer UPDATED_PLANTING_PERIOD = 2;

    private static final Integer DEFAULT_YIELD_UNIT = 1;
    private static final Integer UPDATED_YIELD_UNIT = 2;

    private static final Integer DEFAULT_GROWTH_HEIGHT_MIN = 1;
    private static final Integer UPDATED_GROWTH_HEIGHT_MIN = 2;

    private static final Integer DEFAULT_GROWTH_HEIGHT_MAX = 1;
    private static final Integer UPDATED_GROWTH_HEIGHT_MAX = 2;

    private static final Integer DEFAULT_GROWN_SPREAD_MIN = 1;
    private static final Integer UPDATED_GROWN_SPREAD_MIN = 2;

    private static final Integer DEFAULT_GROWN_SPREAD_MAX = 1;
    private static final Integer UPDATED_GROWN_SPREAD_MAX = 2;

    private static final Integer DEFAULT_GROWN_WEIGHT_MAX = 1;
    private static final Integer UPDATED_GROWN_WEIGHT_MAX = 2;

    private static final Integer DEFAULT_GROWN_WEIGHT_MIN = 1;
    private static final Integer UPDATED_GROWN_WEIGHT_MIN = 2;

    private static final String DEFAULT_GROWING_MEDIA = "AAAAAAAAAA";
    private static final String UPDATED_GROWING_MEDIA = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENTS_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/plants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlantMockMvc;

    private Plant plant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plant createEntity(EntityManager em) {
        Plant plant = new Plant()
            .gUID(DEFAULT_G_UID)
            .commonName(DEFAULT_COMMON_NAME)
            .scientificName(DEFAULT_SCIENTIFIC_NAME)
            .familyName(DEFAULT_FAMILY_NAME)
            .plantSpacing(DEFAULT_PLANT_SPACING)
            .seedingMonth(DEFAULT_SEEDING_MONTH)
            .transplantMonth(DEFAULT_TRANSPLANT_MONTH)
            .harvestMonth(DEFAULT_HARVEST_MONTH)
            .originCountry(DEFAULT_ORIGIN_COUNTRY)
            .yearlyCrops(DEFAULT_YEARLY_CROPS)
            .nativeTemperature(DEFAULT_NATIVE_TEMPERATURE)
            .nativeHumidity(DEFAULT_NATIVE_HUMIDITY)
            .nativeDayDuration(DEFAULT_NATIVE_DAY_DURATION)
            .nativeNightDuration(DEFAULT_NATIVE_NIGHT_DURATION)
            .nativeSoilMoisture(DEFAULT_NATIVE_SOIL_MOISTURE)
            .plantingPeriod(DEFAULT_PLANTING_PERIOD)
            .yieldUnit(DEFAULT_YIELD_UNIT)
            .growthHeightMin(DEFAULT_GROWTH_HEIGHT_MIN)
            .growthHeightMax(DEFAULT_GROWTH_HEIGHT_MAX)
            .grownSpreadMin(DEFAULT_GROWN_SPREAD_MIN)
            .grownSpreadMax(DEFAULT_GROWN_SPREAD_MAX)
            .grownWeightMax(DEFAULT_GROWN_WEIGHT_MAX)
            .grownWeightMin(DEFAULT_GROWN_WEIGHT_MIN)
            .growingMedia(DEFAULT_GROWING_MEDIA)
            .documents(DEFAULT_DOCUMENTS)
            .notes(DEFAULT_NOTES)
            .attachments(DEFAULT_ATTACHMENTS)
            .attachmentsContentType(DEFAULT_ATTACHMENTS_CONTENT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return plant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plant createUpdatedEntity(EntityManager em) {
        Plant plant = new Plant()
            .gUID(UPDATED_G_UID)
            .commonName(UPDATED_COMMON_NAME)
            .scientificName(UPDATED_SCIENTIFIC_NAME)
            .familyName(UPDATED_FAMILY_NAME)
            .plantSpacing(UPDATED_PLANT_SPACING)
            .seedingMonth(UPDATED_SEEDING_MONTH)
            .transplantMonth(UPDATED_TRANSPLANT_MONTH)
            .harvestMonth(UPDATED_HARVEST_MONTH)
            .originCountry(UPDATED_ORIGIN_COUNTRY)
            .yearlyCrops(UPDATED_YEARLY_CROPS)
            .nativeTemperature(UPDATED_NATIVE_TEMPERATURE)
            .nativeHumidity(UPDATED_NATIVE_HUMIDITY)
            .nativeDayDuration(UPDATED_NATIVE_DAY_DURATION)
            .nativeNightDuration(UPDATED_NATIVE_NIGHT_DURATION)
            .nativeSoilMoisture(UPDATED_NATIVE_SOIL_MOISTURE)
            .plantingPeriod(UPDATED_PLANTING_PERIOD)
            .yieldUnit(UPDATED_YIELD_UNIT)
            .growthHeightMin(UPDATED_GROWTH_HEIGHT_MIN)
            .growthHeightMax(UPDATED_GROWTH_HEIGHT_MAX)
            .grownSpreadMin(UPDATED_GROWN_SPREAD_MIN)
            .grownSpreadMax(UPDATED_GROWN_SPREAD_MAX)
            .grownWeightMax(UPDATED_GROWN_WEIGHT_MAX)
            .grownWeightMin(UPDATED_GROWN_WEIGHT_MIN)
            .growingMedia(UPDATED_GROWING_MEDIA)
            .documents(UPDATED_DOCUMENTS)
            .notes(UPDATED_NOTES)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return plant;
    }

    @BeforeEach
    public void initTest() {
        plant = createEntity(em);
    }

    @Test
    @Transactional
    void createPlant() throws Exception {
        int databaseSizeBeforeCreate = plantRepository.findAll().size();
        // Create the Plant
        restPlantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plant)))
            .andExpect(status().isCreated());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeCreate + 1);
        Plant testPlant = plantList.get(plantList.size() - 1);
        assertThat(testPlant.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPlant.getCommonName()).isEqualTo(DEFAULT_COMMON_NAME);
        assertThat(testPlant.getScientificName()).isEqualTo(DEFAULT_SCIENTIFIC_NAME);
        assertThat(testPlant.getFamilyName()).isEqualTo(DEFAULT_FAMILY_NAME);
        assertThat(testPlant.getPlantSpacing()).isEqualTo(DEFAULT_PLANT_SPACING);
        assertThat(testPlant.getSeedingMonth()).isEqualTo(DEFAULT_SEEDING_MONTH);
        assertThat(testPlant.getTransplantMonth()).isEqualTo(DEFAULT_TRANSPLANT_MONTH);
        assertThat(testPlant.getHarvestMonth()).isEqualTo(DEFAULT_HARVEST_MONTH);
        assertThat(testPlant.getOriginCountry()).isEqualTo(DEFAULT_ORIGIN_COUNTRY);
        assertThat(testPlant.getYearlyCrops()).isEqualTo(DEFAULT_YEARLY_CROPS);
        assertThat(testPlant.getNativeTemperature()).isEqualTo(DEFAULT_NATIVE_TEMPERATURE);
        assertThat(testPlant.getNativeHumidity()).isEqualTo(DEFAULT_NATIVE_HUMIDITY);
        assertThat(testPlant.getNativeDayDuration()).isEqualTo(DEFAULT_NATIVE_DAY_DURATION);
        assertThat(testPlant.getNativeNightDuration()).isEqualTo(DEFAULT_NATIVE_NIGHT_DURATION);
        assertThat(testPlant.getNativeSoilMoisture()).isEqualTo(DEFAULT_NATIVE_SOIL_MOISTURE);
        assertThat(testPlant.getPlantingPeriod()).isEqualTo(DEFAULT_PLANTING_PERIOD);
        assertThat(testPlant.getYieldUnit()).isEqualTo(DEFAULT_YIELD_UNIT);
        assertThat(testPlant.getGrowthHeightMin()).isEqualTo(DEFAULT_GROWTH_HEIGHT_MIN);
        assertThat(testPlant.getGrowthHeightMax()).isEqualTo(DEFAULT_GROWTH_HEIGHT_MAX);
        assertThat(testPlant.getGrownSpreadMin()).isEqualTo(DEFAULT_GROWN_SPREAD_MIN);
        assertThat(testPlant.getGrownSpreadMax()).isEqualTo(DEFAULT_GROWN_SPREAD_MAX);
        assertThat(testPlant.getGrownWeightMax()).isEqualTo(DEFAULT_GROWN_WEIGHT_MAX);
        assertThat(testPlant.getGrownWeightMin()).isEqualTo(DEFAULT_GROWN_WEIGHT_MIN);
        assertThat(testPlant.getGrowingMedia()).isEqualTo(DEFAULT_GROWING_MEDIA);
        assertThat(testPlant.getDocuments()).isEqualTo(DEFAULT_DOCUMENTS);
        assertThat(testPlant.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testPlant.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testPlant.getAttachmentsContentType()).isEqualTo(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testPlant.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPlant.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPlant.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPlant.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPlantWithExistingId() throws Exception {
        // Create the Plant with an existing ID
        plant.setId(1L);

        int databaseSizeBeforeCreate = plantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plant)))
            .andExpect(status().isBadRequest());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlants() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList
        restPlantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plant.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].commonName").value(hasItem(DEFAULT_COMMON_NAME)))
            .andExpect(jsonPath("$.[*].scientificName").value(hasItem(DEFAULT_SCIENTIFIC_NAME)))
            .andExpect(jsonPath("$.[*].familyName").value(hasItem(DEFAULT_FAMILY_NAME)))
            .andExpect(jsonPath("$.[*].plantSpacing").value(hasItem(DEFAULT_PLANT_SPACING)))
            .andExpect(jsonPath("$.[*].seedingMonth").value(hasItem(DEFAULT_SEEDING_MONTH.toString())))
            .andExpect(jsonPath("$.[*].transplantMonth").value(hasItem(DEFAULT_TRANSPLANT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].harvestMonth").value(hasItem(DEFAULT_HARVEST_MONTH.toString())))
            .andExpect(jsonPath("$.[*].originCountry").value(hasItem(DEFAULT_ORIGIN_COUNTRY.intValue())))
            .andExpect(jsonPath("$.[*].yearlyCrops").value(hasItem(DEFAULT_YEARLY_CROPS)))
            .andExpect(jsonPath("$.[*].nativeTemperature").value(hasItem(DEFAULT_NATIVE_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].nativeHumidity").value(hasItem(DEFAULT_NATIVE_HUMIDITY)))
            .andExpect(jsonPath("$.[*].nativeDayDuration").value(hasItem(DEFAULT_NATIVE_DAY_DURATION)))
            .andExpect(jsonPath("$.[*].nativeNightDuration").value(hasItem(DEFAULT_NATIVE_NIGHT_DURATION)))
            .andExpect(jsonPath("$.[*].nativeSoilMoisture").value(hasItem(DEFAULT_NATIVE_SOIL_MOISTURE)))
            .andExpect(jsonPath("$.[*].plantingPeriod").value(hasItem(DEFAULT_PLANTING_PERIOD)))
            .andExpect(jsonPath("$.[*].yieldUnit").value(hasItem(DEFAULT_YIELD_UNIT)))
            .andExpect(jsonPath("$.[*].growthHeightMin").value(hasItem(DEFAULT_GROWTH_HEIGHT_MIN)))
            .andExpect(jsonPath("$.[*].growthHeightMax").value(hasItem(DEFAULT_GROWTH_HEIGHT_MAX)))
            .andExpect(jsonPath("$.[*].grownSpreadMin").value(hasItem(DEFAULT_GROWN_SPREAD_MIN)))
            .andExpect(jsonPath("$.[*].grownSpreadMax").value(hasItem(DEFAULT_GROWN_SPREAD_MAX)))
            .andExpect(jsonPath("$.[*].grownWeightMax").value(hasItem(DEFAULT_GROWN_WEIGHT_MAX)))
            .andExpect(jsonPath("$.[*].grownWeightMin").value(hasItem(DEFAULT_GROWN_WEIGHT_MIN)))
            .andExpect(jsonPath("$.[*].growingMedia").value(hasItem(DEFAULT_GROWING_MEDIA)))
            .andExpect(jsonPath("$.[*].documents").value(hasItem(DEFAULT_DOCUMENTS)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get the plant
        restPlantMockMvc
            .perform(get(ENTITY_API_URL_ID, plant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plant.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.commonName").value(DEFAULT_COMMON_NAME))
            .andExpect(jsonPath("$.scientificName").value(DEFAULT_SCIENTIFIC_NAME))
            .andExpect(jsonPath("$.familyName").value(DEFAULT_FAMILY_NAME))
            .andExpect(jsonPath("$.plantSpacing").value(DEFAULT_PLANT_SPACING))
            .andExpect(jsonPath("$.seedingMonth").value(DEFAULT_SEEDING_MONTH.toString()))
            .andExpect(jsonPath("$.transplantMonth").value(DEFAULT_TRANSPLANT_MONTH.toString()))
            .andExpect(jsonPath("$.harvestMonth").value(DEFAULT_HARVEST_MONTH.toString()))
            .andExpect(jsonPath("$.originCountry").value(DEFAULT_ORIGIN_COUNTRY.intValue()))
            .andExpect(jsonPath("$.yearlyCrops").value(DEFAULT_YEARLY_CROPS))
            .andExpect(jsonPath("$.nativeTemperature").value(DEFAULT_NATIVE_TEMPERATURE))
            .andExpect(jsonPath("$.nativeHumidity").value(DEFAULT_NATIVE_HUMIDITY))
            .andExpect(jsonPath("$.nativeDayDuration").value(DEFAULT_NATIVE_DAY_DURATION))
            .andExpect(jsonPath("$.nativeNightDuration").value(DEFAULT_NATIVE_NIGHT_DURATION))
            .andExpect(jsonPath("$.nativeSoilMoisture").value(DEFAULT_NATIVE_SOIL_MOISTURE))
            .andExpect(jsonPath("$.plantingPeriod").value(DEFAULT_PLANTING_PERIOD))
            .andExpect(jsonPath("$.yieldUnit").value(DEFAULT_YIELD_UNIT))
            .andExpect(jsonPath("$.growthHeightMin").value(DEFAULT_GROWTH_HEIGHT_MIN))
            .andExpect(jsonPath("$.growthHeightMax").value(DEFAULT_GROWTH_HEIGHT_MAX))
            .andExpect(jsonPath("$.grownSpreadMin").value(DEFAULT_GROWN_SPREAD_MIN))
            .andExpect(jsonPath("$.grownSpreadMax").value(DEFAULT_GROWN_SPREAD_MAX))
            .andExpect(jsonPath("$.grownWeightMax").value(DEFAULT_GROWN_WEIGHT_MAX))
            .andExpect(jsonPath("$.grownWeightMin").value(DEFAULT_GROWN_WEIGHT_MIN))
            .andExpect(jsonPath("$.growingMedia").value(DEFAULT_GROWING_MEDIA))
            .andExpect(jsonPath("$.documents").value(DEFAULT_DOCUMENTS))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.attachmentsContentType").value(DEFAULT_ATTACHMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachments").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPlant() throws Exception {
        // Get the plant
        restPlantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        int databaseSizeBeforeUpdate = plantRepository.findAll().size();

        // Update the plant
        Plant updatedPlant = plantRepository.findById(plant.getId()).get();
        // Disconnect from session so that the updates on updatedPlant are not directly saved in db
        em.detach(updatedPlant);
        updatedPlant
            .gUID(UPDATED_G_UID)
            .commonName(UPDATED_COMMON_NAME)
            .scientificName(UPDATED_SCIENTIFIC_NAME)
            .familyName(UPDATED_FAMILY_NAME)
            .plantSpacing(UPDATED_PLANT_SPACING)
            .seedingMonth(UPDATED_SEEDING_MONTH)
            .transplantMonth(UPDATED_TRANSPLANT_MONTH)
            .harvestMonth(UPDATED_HARVEST_MONTH)
            .originCountry(UPDATED_ORIGIN_COUNTRY)
            .yearlyCrops(UPDATED_YEARLY_CROPS)
            .nativeTemperature(UPDATED_NATIVE_TEMPERATURE)
            .nativeHumidity(UPDATED_NATIVE_HUMIDITY)
            .nativeDayDuration(UPDATED_NATIVE_DAY_DURATION)
            .nativeNightDuration(UPDATED_NATIVE_NIGHT_DURATION)
            .nativeSoilMoisture(UPDATED_NATIVE_SOIL_MOISTURE)
            .plantingPeriod(UPDATED_PLANTING_PERIOD)
            .yieldUnit(UPDATED_YIELD_UNIT)
            .growthHeightMin(UPDATED_GROWTH_HEIGHT_MIN)
            .growthHeightMax(UPDATED_GROWTH_HEIGHT_MAX)
            .grownSpreadMin(UPDATED_GROWN_SPREAD_MIN)
            .grownSpreadMax(UPDATED_GROWN_SPREAD_MAX)
            .grownWeightMax(UPDATED_GROWN_WEIGHT_MAX)
            .grownWeightMin(UPDATED_GROWN_WEIGHT_MIN)
            .growingMedia(UPDATED_GROWING_MEDIA)
            .documents(UPDATED_DOCUMENTS)
            .notes(UPDATED_NOTES)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlant))
            )
            .andExpect(status().isOk());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
        Plant testPlant = plantList.get(plantList.size() - 1);
        assertThat(testPlant.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPlant.getCommonName()).isEqualTo(UPDATED_COMMON_NAME);
        assertThat(testPlant.getScientificName()).isEqualTo(UPDATED_SCIENTIFIC_NAME);
        assertThat(testPlant.getFamilyName()).isEqualTo(UPDATED_FAMILY_NAME);
        assertThat(testPlant.getPlantSpacing()).isEqualTo(UPDATED_PLANT_SPACING);
        assertThat(testPlant.getSeedingMonth()).isEqualTo(UPDATED_SEEDING_MONTH);
        assertThat(testPlant.getTransplantMonth()).isEqualTo(UPDATED_TRANSPLANT_MONTH);
        assertThat(testPlant.getHarvestMonth()).isEqualTo(UPDATED_HARVEST_MONTH);
        assertThat(testPlant.getOriginCountry()).isEqualTo(UPDATED_ORIGIN_COUNTRY);
        assertThat(testPlant.getYearlyCrops()).isEqualTo(UPDATED_YEARLY_CROPS);
        assertThat(testPlant.getNativeTemperature()).isEqualTo(UPDATED_NATIVE_TEMPERATURE);
        assertThat(testPlant.getNativeHumidity()).isEqualTo(UPDATED_NATIVE_HUMIDITY);
        assertThat(testPlant.getNativeDayDuration()).isEqualTo(UPDATED_NATIVE_DAY_DURATION);
        assertThat(testPlant.getNativeNightDuration()).isEqualTo(UPDATED_NATIVE_NIGHT_DURATION);
        assertThat(testPlant.getNativeSoilMoisture()).isEqualTo(UPDATED_NATIVE_SOIL_MOISTURE);
        assertThat(testPlant.getPlantingPeriod()).isEqualTo(UPDATED_PLANTING_PERIOD);
        assertThat(testPlant.getYieldUnit()).isEqualTo(UPDATED_YIELD_UNIT);
        assertThat(testPlant.getGrowthHeightMin()).isEqualTo(UPDATED_GROWTH_HEIGHT_MIN);
        assertThat(testPlant.getGrowthHeightMax()).isEqualTo(UPDATED_GROWTH_HEIGHT_MAX);
        assertThat(testPlant.getGrownSpreadMin()).isEqualTo(UPDATED_GROWN_SPREAD_MIN);
        assertThat(testPlant.getGrownSpreadMax()).isEqualTo(UPDATED_GROWN_SPREAD_MAX);
        assertThat(testPlant.getGrownWeightMax()).isEqualTo(UPDATED_GROWN_WEIGHT_MAX);
        assertThat(testPlant.getGrownWeightMin()).isEqualTo(UPDATED_GROWN_WEIGHT_MIN);
        assertThat(testPlant.getGrowingMedia()).isEqualTo(UPDATED_GROWING_MEDIA);
        assertThat(testPlant.getDocuments()).isEqualTo(UPDATED_DOCUMENTS);
        assertThat(testPlant.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testPlant.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testPlant.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testPlant.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPlant.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPlant.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPlant.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPlant() throws Exception {
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();
        plant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlant() throws Exception {
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();
        plant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlant() throws Exception {
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();
        plant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlantWithPatch() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        int databaseSizeBeforeUpdate = plantRepository.findAll().size();

        // Update the plant using partial update
        Plant partialUpdatedPlant = new Plant();
        partialUpdatedPlant.setId(plant.getId());

        partialUpdatedPlant
            .gUID(UPDATED_G_UID)
            .familyName(UPDATED_FAMILY_NAME)
            .originCountry(UPDATED_ORIGIN_COUNTRY)
            .yearlyCrops(UPDATED_YEARLY_CROPS)
            .nativeHumidity(UPDATED_NATIVE_HUMIDITY)
            .nativeNightDuration(UPDATED_NATIVE_NIGHT_DURATION)
            .growthHeightMin(UPDATED_GROWTH_HEIGHT_MIN)
            .growthHeightMax(UPDATED_GROWTH_HEIGHT_MAX)
            .grownSpreadMin(UPDATED_GROWN_SPREAD_MIN)
            .grownSpreadMax(UPDATED_GROWN_SPREAD_MAX)
            .grownWeightMax(UPDATED_GROWN_WEIGHT_MAX)
            .documents(UPDATED_DOCUMENTS)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON);

        restPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlant))
            )
            .andExpect(status().isOk());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
        Plant testPlant = plantList.get(plantList.size() - 1);
        assertThat(testPlant.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPlant.getCommonName()).isEqualTo(DEFAULT_COMMON_NAME);
        assertThat(testPlant.getScientificName()).isEqualTo(DEFAULT_SCIENTIFIC_NAME);
        assertThat(testPlant.getFamilyName()).isEqualTo(UPDATED_FAMILY_NAME);
        assertThat(testPlant.getPlantSpacing()).isEqualTo(DEFAULT_PLANT_SPACING);
        assertThat(testPlant.getSeedingMonth()).isEqualTo(DEFAULT_SEEDING_MONTH);
        assertThat(testPlant.getTransplantMonth()).isEqualTo(DEFAULT_TRANSPLANT_MONTH);
        assertThat(testPlant.getHarvestMonth()).isEqualTo(DEFAULT_HARVEST_MONTH);
        assertThat(testPlant.getOriginCountry()).isEqualTo(UPDATED_ORIGIN_COUNTRY);
        assertThat(testPlant.getYearlyCrops()).isEqualTo(UPDATED_YEARLY_CROPS);
        assertThat(testPlant.getNativeTemperature()).isEqualTo(DEFAULT_NATIVE_TEMPERATURE);
        assertThat(testPlant.getNativeHumidity()).isEqualTo(UPDATED_NATIVE_HUMIDITY);
        assertThat(testPlant.getNativeDayDuration()).isEqualTo(DEFAULT_NATIVE_DAY_DURATION);
        assertThat(testPlant.getNativeNightDuration()).isEqualTo(UPDATED_NATIVE_NIGHT_DURATION);
        assertThat(testPlant.getNativeSoilMoisture()).isEqualTo(DEFAULT_NATIVE_SOIL_MOISTURE);
        assertThat(testPlant.getPlantingPeriod()).isEqualTo(DEFAULT_PLANTING_PERIOD);
        assertThat(testPlant.getYieldUnit()).isEqualTo(DEFAULT_YIELD_UNIT);
        assertThat(testPlant.getGrowthHeightMin()).isEqualTo(UPDATED_GROWTH_HEIGHT_MIN);
        assertThat(testPlant.getGrowthHeightMax()).isEqualTo(UPDATED_GROWTH_HEIGHT_MAX);
        assertThat(testPlant.getGrownSpreadMin()).isEqualTo(UPDATED_GROWN_SPREAD_MIN);
        assertThat(testPlant.getGrownSpreadMax()).isEqualTo(UPDATED_GROWN_SPREAD_MAX);
        assertThat(testPlant.getGrownWeightMax()).isEqualTo(UPDATED_GROWN_WEIGHT_MAX);
        assertThat(testPlant.getGrownWeightMin()).isEqualTo(DEFAULT_GROWN_WEIGHT_MIN);
        assertThat(testPlant.getGrowingMedia()).isEqualTo(DEFAULT_GROWING_MEDIA);
        assertThat(testPlant.getDocuments()).isEqualTo(UPDATED_DOCUMENTS);
        assertThat(testPlant.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testPlant.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testPlant.getAttachmentsContentType()).isEqualTo(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testPlant.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPlant.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPlant.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPlant.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePlantWithPatch() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        int databaseSizeBeforeUpdate = plantRepository.findAll().size();

        // Update the plant using partial update
        Plant partialUpdatedPlant = new Plant();
        partialUpdatedPlant.setId(plant.getId());

        partialUpdatedPlant
            .gUID(UPDATED_G_UID)
            .commonName(UPDATED_COMMON_NAME)
            .scientificName(UPDATED_SCIENTIFIC_NAME)
            .familyName(UPDATED_FAMILY_NAME)
            .plantSpacing(UPDATED_PLANT_SPACING)
            .seedingMonth(UPDATED_SEEDING_MONTH)
            .transplantMonth(UPDATED_TRANSPLANT_MONTH)
            .harvestMonth(UPDATED_HARVEST_MONTH)
            .originCountry(UPDATED_ORIGIN_COUNTRY)
            .yearlyCrops(UPDATED_YEARLY_CROPS)
            .nativeTemperature(UPDATED_NATIVE_TEMPERATURE)
            .nativeHumidity(UPDATED_NATIVE_HUMIDITY)
            .nativeDayDuration(UPDATED_NATIVE_DAY_DURATION)
            .nativeNightDuration(UPDATED_NATIVE_NIGHT_DURATION)
            .nativeSoilMoisture(UPDATED_NATIVE_SOIL_MOISTURE)
            .plantingPeriod(UPDATED_PLANTING_PERIOD)
            .yieldUnit(UPDATED_YIELD_UNIT)
            .growthHeightMin(UPDATED_GROWTH_HEIGHT_MIN)
            .growthHeightMax(UPDATED_GROWTH_HEIGHT_MAX)
            .grownSpreadMin(UPDATED_GROWN_SPREAD_MIN)
            .grownSpreadMax(UPDATED_GROWN_SPREAD_MAX)
            .grownWeightMax(UPDATED_GROWN_WEIGHT_MAX)
            .grownWeightMin(UPDATED_GROWN_WEIGHT_MIN)
            .growingMedia(UPDATED_GROWING_MEDIA)
            .documents(UPDATED_DOCUMENTS)
            .notes(UPDATED_NOTES)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlant))
            )
            .andExpect(status().isOk());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
        Plant testPlant = plantList.get(plantList.size() - 1);
        assertThat(testPlant.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPlant.getCommonName()).isEqualTo(UPDATED_COMMON_NAME);
        assertThat(testPlant.getScientificName()).isEqualTo(UPDATED_SCIENTIFIC_NAME);
        assertThat(testPlant.getFamilyName()).isEqualTo(UPDATED_FAMILY_NAME);
        assertThat(testPlant.getPlantSpacing()).isEqualTo(UPDATED_PLANT_SPACING);
        assertThat(testPlant.getSeedingMonth()).isEqualTo(UPDATED_SEEDING_MONTH);
        assertThat(testPlant.getTransplantMonth()).isEqualTo(UPDATED_TRANSPLANT_MONTH);
        assertThat(testPlant.getHarvestMonth()).isEqualTo(UPDATED_HARVEST_MONTH);
        assertThat(testPlant.getOriginCountry()).isEqualTo(UPDATED_ORIGIN_COUNTRY);
        assertThat(testPlant.getYearlyCrops()).isEqualTo(UPDATED_YEARLY_CROPS);
        assertThat(testPlant.getNativeTemperature()).isEqualTo(UPDATED_NATIVE_TEMPERATURE);
        assertThat(testPlant.getNativeHumidity()).isEqualTo(UPDATED_NATIVE_HUMIDITY);
        assertThat(testPlant.getNativeDayDuration()).isEqualTo(UPDATED_NATIVE_DAY_DURATION);
        assertThat(testPlant.getNativeNightDuration()).isEqualTo(UPDATED_NATIVE_NIGHT_DURATION);
        assertThat(testPlant.getNativeSoilMoisture()).isEqualTo(UPDATED_NATIVE_SOIL_MOISTURE);
        assertThat(testPlant.getPlantingPeriod()).isEqualTo(UPDATED_PLANTING_PERIOD);
        assertThat(testPlant.getYieldUnit()).isEqualTo(UPDATED_YIELD_UNIT);
        assertThat(testPlant.getGrowthHeightMin()).isEqualTo(UPDATED_GROWTH_HEIGHT_MIN);
        assertThat(testPlant.getGrowthHeightMax()).isEqualTo(UPDATED_GROWTH_HEIGHT_MAX);
        assertThat(testPlant.getGrownSpreadMin()).isEqualTo(UPDATED_GROWN_SPREAD_MIN);
        assertThat(testPlant.getGrownSpreadMax()).isEqualTo(UPDATED_GROWN_SPREAD_MAX);
        assertThat(testPlant.getGrownWeightMax()).isEqualTo(UPDATED_GROWN_WEIGHT_MAX);
        assertThat(testPlant.getGrownWeightMin()).isEqualTo(UPDATED_GROWN_WEIGHT_MIN);
        assertThat(testPlant.getGrowingMedia()).isEqualTo(UPDATED_GROWING_MEDIA);
        assertThat(testPlant.getDocuments()).isEqualTo(UPDATED_DOCUMENTS);
        assertThat(testPlant.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testPlant.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testPlant.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testPlant.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPlant.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPlant.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPlant.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPlant() throws Exception {
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();
        plant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlant() throws Exception {
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();
        plant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlant() throws Exception {
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();
        plant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        int databaseSizeBeforeDelete = plantRepository.findAll().size();

        // Delete the plant
        restPlantMockMvc
            .perform(delete(ENTITY_API_URL_ID, plant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
