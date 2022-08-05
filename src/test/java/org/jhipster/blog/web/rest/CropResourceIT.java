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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.blog.IntegrationTest;
import org.jhipster.blog.domain.Crop;
import org.jhipster.blog.domain.enumeration.CropTyp;
import org.jhipster.blog.domain.enumeration.GroPhase;
import org.jhipster.blog.domain.enumeration.Horti;
import org.jhipster.blog.domain.enumeration.PlantingMat;
import org.jhipster.blog.repository.CropRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CropResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CropResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_CROP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CROP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CROP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME = "BBBBBBBBBB";

    private static final CropTyp DEFAULT_CROP_TYPE = CropTyp.Food;
    private static final CropTyp UPDATED_CROP_TYPE = CropTyp.Crops;

    private static final Horti DEFAULT_HORTICULTURE_TYPE = Horti.Olericulture;
    private static final Horti UPDATED_HORTICULTURE_TYPE = Horti.Floriculture;

    private static final Boolean DEFAULT_IS_HYBRID = false;
    private static final Boolean UPDATED_IS_HYBRID = true;

    private static final String DEFAULT_CULTIVAR = "AAAAAAAAAA";
    private static final String UPDATED_CULTIVAR = "BBBBBBBBBB";

    private static final Instant DEFAULT_SOWING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SOWING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SOWING_DEPTH = 1;
    private static final Integer UPDATED_SOWING_DEPTH = 2;

    private static final Integer DEFAULT_ROW_SPACING_MAX = 1;
    private static final Integer UPDATED_ROW_SPACING_MAX = 2;

    private static final Integer DEFAULT_ROW_SPACING_MIN = 1;
    private static final Integer UPDATED_ROW_SPACING_MIN = 2;

    private static final Integer DEFAULT_SEED_DEPTH_MAX = 1;
    private static final Integer UPDATED_SEED_DEPTH_MAX = 2;

    private static final Integer DEFAULT_SEED_DEPTH_MIN = 1;
    private static final Integer UPDATED_SEED_DEPTH_MIN = 2;

    private static final Integer DEFAULT_SEED_SPACING_MAX = 1;
    private static final Integer UPDATED_SEED_SPACING_MAX = 2;

    private static final Integer DEFAULT_SEED_SPACING_MIN = 1;
    private static final Integer UPDATED_SEED_SPACING_MIN = 2;

    private static final Integer DEFAULT_YEARLY_CROPS = 1;
    private static final Integer UPDATED_YEARLY_CROPS = 2;

    private static final String DEFAULT_GROWING_SEASON = "AAAAAAAAAA";
    private static final String UPDATED_GROWING_SEASON = "BBBBBBBBBB";

    private static final GroPhase DEFAULT_GROWING_PHASE = GroPhase.Initial;
    private static final GroPhase UPDATED_GROWING_PHASE = GroPhase.Stage;

    private static final Instant DEFAULT_PLANTING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PLANTING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_PLANT_SPACING = 1;
    private static final Integer UPDATED_PLANT_SPACING = 2;

    private static final PlantingMat DEFAULT_PLANTING_MATERIAL = PlantingMat.Seeds;
    private static final PlantingMat UPDATED_PLANTING_MATERIAL = PlantingMat.Seedlings;

    private static final Instant DEFAULT_TRANSPLANTATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSPLANTATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_FERTIGATIONSCHEDULE_ID = 1L;
    private static final Long UPDATED_FERTIGATIONSCHEDULE_ID = 2L;

    private static final Integer DEFAULT_PLANNED_YIELD = 1;
    private static final Integer UPDATED_PLANNED_YIELD = 2;

    private static final Integer DEFAULT_ACTUAL_YIELD = 1;
    private static final Integer UPDATED_ACTUAL_YIELD = 2;

    private static final Integer DEFAULT_YIELD_UNIT = 1;
    private static final Integer UPDATED_YIELD_UNIT = 2;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/crops";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCropMockMvc;

    private Crop crop;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Crop createEntity(EntityManager em) {
        Crop crop = new Crop()
            .gUID(DEFAULT_G_UID)
            .cropCode(DEFAULT_CROP_CODE)
            .cropName(DEFAULT_CROP_NAME)
            .cropType(DEFAULT_CROP_TYPE)
            .horticultureType(DEFAULT_HORTICULTURE_TYPE)
            .isHybrid(DEFAULT_IS_HYBRID)
            .cultivar(DEFAULT_CULTIVAR)
            .sowingDate(DEFAULT_SOWING_DATE)
            .sowingDepth(DEFAULT_SOWING_DEPTH)
            .rowSpacingMax(DEFAULT_ROW_SPACING_MAX)
            .rowSpacingMin(DEFAULT_ROW_SPACING_MIN)
            .seedDepthMax(DEFAULT_SEED_DEPTH_MAX)
            .seedDepthMin(DEFAULT_SEED_DEPTH_MIN)
            .seedSpacingMax(DEFAULT_SEED_SPACING_MAX)
            .seedSpacingMin(DEFAULT_SEED_SPACING_MIN)
            .yearlyCrops(DEFAULT_YEARLY_CROPS)
            .growingSeason(DEFAULT_GROWING_SEASON)
            .growingPhase(DEFAULT_GROWING_PHASE)
            .plantingDate(DEFAULT_PLANTING_DATE)
            .plantSpacing(DEFAULT_PLANT_SPACING)
            .plantingMaterial(DEFAULT_PLANTING_MATERIAL)
            .transplantationDate(DEFAULT_TRANSPLANTATION_DATE)
            .fertigationscheduleID(DEFAULT_FERTIGATIONSCHEDULE_ID)
            .plannedYield(DEFAULT_PLANNED_YIELD)
            .actualYield(DEFAULT_ACTUAL_YIELD)
            .yieldUnit(DEFAULT_YIELD_UNIT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return crop;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Crop createUpdatedEntity(EntityManager em) {
        Crop crop = new Crop()
            .gUID(UPDATED_G_UID)
            .cropCode(UPDATED_CROP_CODE)
            .cropName(UPDATED_CROP_NAME)
            .cropType(UPDATED_CROP_TYPE)
            .horticultureType(UPDATED_HORTICULTURE_TYPE)
            .isHybrid(UPDATED_IS_HYBRID)
            .cultivar(UPDATED_CULTIVAR)
            .sowingDate(UPDATED_SOWING_DATE)
            .sowingDepth(UPDATED_SOWING_DEPTH)
            .rowSpacingMax(UPDATED_ROW_SPACING_MAX)
            .rowSpacingMin(UPDATED_ROW_SPACING_MIN)
            .seedDepthMax(UPDATED_SEED_DEPTH_MAX)
            .seedDepthMin(UPDATED_SEED_DEPTH_MIN)
            .seedSpacingMax(UPDATED_SEED_SPACING_MAX)
            .seedSpacingMin(UPDATED_SEED_SPACING_MIN)
            .yearlyCrops(UPDATED_YEARLY_CROPS)
            .growingSeason(UPDATED_GROWING_SEASON)
            .growingPhase(UPDATED_GROWING_PHASE)
            .plantingDate(UPDATED_PLANTING_DATE)
            .plantSpacing(UPDATED_PLANT_SPACING)
            .plantingMaterial(UPDATED_PLANTING_MATERIAL)
            .transplantationDate(UPDATED_TRANSPLANTATION_DATE)
            .fertigationscheduleID(UPDATED_FERTIGATIONSCHEDULE_ID)
            .plannedYield(UPDATED_PLANNED_YIELD)
            .actualYield(UPDATED_ACTUAL_YIELD)
            .yieldUnit(UPDATED_YIELD_UNIT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return crop;
    }

    @BeforeEach
    public void initTest() {
        crop = createEntity(em);
    }

    @Test
    @Transactional
    void createCrop() throws Exception {
        int databaseSizeBeforeCreate = cropRepository.findAll().size();
        // Create the Crop
        restCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(crop)))
            .andExpect(status().isCreated());

        // Validate the Crop in the database
        List<Crop> cropList = cropRepository.findAll();
        assertThat(cropList).hasSize(databaseSizeBeforeCreate + 1);
        Crop testCrop = cropList.get(cropList.size() - 1);
        assertThat(testCrop.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testCrop.getCropCode()).isEqualTo(DEFAULT_CROP_CODE);
        assertThat(testCrop.getCropName()).isEqualTo(DEFAULT_CROP_NAME);
        assertThat(testCrop.getCropType()).isEqualTo(DEFAULT_CROP_TYPE);
        assertThat(testCrop.getHorticultureType()).isEqualTo(DEFAULT_HORTICULTURE_TYPE);
        assertThat(testCrop.getIsHybrid()).isEqualTo(DEFAULT_IS_HYBRID);
        assertThat(testCrop.getCultivar()).isEqualTo(DEFAULT_CULTIVAR);
        assertThat(testCrop.getSowingDate()).isEqualTo(DEFAULT_SOWING_DATE);
        assertThat(testCrop.getSowingDepth()).isEqualTo(DEFAULT_SOWING_DEPTH);
        assertThat(testCrop.getRowSpacingMax()).isEqualTo(DEFAULT_ROW_SPACING_MAX);
        assertThat(testCrop.getRowSpacingMin()).isEqualTo(DEFAULT_ROW_SPACING_MIN);
        assertThat(testCrop.getSeedDepthMax()).isEqualTo(DEFAULT_SEED_DEPTH_MAX);
        assertThat(testCrop.getSeedDepthMin()).isEqualTo(DEFAULT_SEED_DEPTH_MIN);
        assertThat(testCrop.getSeedSpacingMax()).isEqualTo(DEFAULT_SEED_SPACING_MAX);
        assertThat(testCrop.getSeedSpacingMin()).isEqualTo(DEFAULT_SEED_SPACING_MIN);
        assertThat(testCrop.getYearlyCrops()).isEqualTo(DEFAULT_YEARLY_CROPS);
        assertThat(testCrop.getGrowingSeason()).isEqualTo(DEFAULT_GROWING_SEASON);
        assertThat(testCrop.getGrowingPhase()).isEqualTo(DEFAULT_GROWING_PHASE);
        assertThat(testCrop.getPlantingDate()).isEqualTo(DEFAULT_PLANTING_DATE);
        assertThat(testCrop.getPlantSpacing()).isEqualTo(DEFAULT_PLANT_SPACING);
        assertThat(testCrop.getPlantingMaterial()).isEqualTo(DEFAULT_PLANTING_MATERIAL);
        assertThat(testCrop.getTransplantationDate()).isEqualTo(DEFAULT_TRANSPLANTATION_DATE);
        assertThat(testCrop.getFertigationscheduleID()).isEqualTo(DEFAULT_FERTIGATIONSCHEDULE_ID);
        assertThat(testCrop.getPlannedYield()).isEqualTo(DEFAULT_PLANNED_YIELD);
        assertThat(testCrop.getActualYield()).isEqualTo(DEFAULT_ACTUAL_YIELD);
        assertThat(testCrop.getYieldUnit()).isEqualTo(DEFAULT_YIELD_UNIT);
        assertThat(testCrop.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCrop.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCrop.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCrop.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createCropWithExistingId() throws Exception {
        // Create the Crop with an existing ID
        crop.setId(1L);

        int databaseSizeBeforeCreate = cropRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(crop)))
            .andExpect(status().isBadRequest());

        // Validate the Crop in the database
        List<Crop> cropList = cropRepository.findAll();
        assertThat(cropList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCrops() throws Exception {
        // Initialize the database
        cropRepository.saveAndFlush(crop);

        // Get all the cropList
        restCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crop.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].cropCode").value(hasItem(DEFAULT_CROP_CODE)))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].cropType").value(hasItem(DEFAULT_CROP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].horticultureType").value(hasItem(DEFAULT_HORTICULTURE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isHybrid").value(hasItem(DEFAULT_IS_HYBRID.booleanValue())))
            .andExpect(jsonPath("$.[*].cultivar").value(hasItem(DEFAULT_CULTIVAR)))
            .andExpect(jsonPath("$.[*].sowingDate").value(hasItem(DEFAULT_SOWING_DATE.toString())))
            .andExpect(jsonPath("$.[*].sowingDepth").value(hasItem(DEFAULT_SOWING_DEPTH)))
            .andExpect(jsonPath("$.[*].rowSpacingMax").value(hasItem(DEFAULT_ROW_SPACING_MAX)))
            .andExpect(jsonPath("$.[*].rowSpacingMin").value(hasItem(DEFAULT_ROW_SPACING_MIN)))
            .andExpect(jsonPath("$.[*].seedDepthMax").value(hasItem(DEFAULT_SEED_DEPTH_MAX)))
            .andExpect(jsonPath("$.[*].seedDepthMin").value(hasItem(DEFAULT_SEED_DEPTH_MIN)))
            .andExpect(jsonPath("$.[*].seedSpacingMax").value(hasItem(DEFAULT_SEED_SPACING_MAX)))
            .andExpect(jsonPath("$.[*].seedSpacingMin").value(hasItem(DEFAULT_SEED_SPACING_MIN)))
            .andExpect(jsonPath("$.[*].yearlyCrops").value(hasItem(DEFAULT_YEARLY_CROPS)))
            .andExpect(jsonPath("$.[*].growingSeason").value(hasItem(DEFAULT_GROWING_SEASON)))
            .andExpect(jsonPath("$.[*].growingPhase").value(hasItem(DEFAULT_GROWING_PHASE.toString())))
            .andExpect(jsonPath("$.[*].plantingDate").value(hasItem(DEFAULT_PLANTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].plantSpacing").value(hasItem(DEFAULT_PLANT_SPACING)))
            .andExpect(jsonPath("$.[*].plantingMaterial").value(hasItem(DEFAULT_PLANTING_MATERIAL.toString())))
            .andExpect(jsonPath("$.[*].transplantationDate").value(hasItem(DEFAULT_TRANSPLANTATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].fertigationscheduleID").value(hasItem(DEFAULT_FERTIGATIONSCHEDULE_ID.intValue())))
            .andExpect(jsonPath("$.[*].plannedYield").value(hasItem(DEFAULT_PLANNED_YIELD)))
            .andExpect(jsonPath("$.[*].actualYield").value(hasItem(DEFAULT_ACTUAL_YIELD)))
            .andExpect(jsonPath("$.[*].yieldUnit").value(hasItem(DEFAULT_YIELD_UNIT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getCrop() throws Exception {
        // Initialize the database
        cropRepository.saveAndFlush(crop);

        // Get the crop
        restCropMockMvc
            .perform(get(ENTITY_API_URL_ID, crop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(crop.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.cropCode").value(DEFAULT_CROP_CODE))
            .andExpect(jsonPath("$.cropName").value(DEFAULT_CROP_NAME))
            .andExpect(jsonPath("$.cropType").value(DEFAULT_CROP_TYPE.toString()))
            .andExpect(jsonPath("$.horticultureType").value(DEFAULT_HORTICULTURE_TYPE.toString()))
            .andExpect(jsonPath("$.isHybrid").value(DEFAULT_IS_HYBRID.booleanValue()))
            .andExpect(jsonPath("$.cultivar").value(DEFAULT_CULTIVAR))
            .andExpect(jsonPath("$.sowingDate").value(DEFAULT_SOWING_DATE.toString()))
            .andExpect(jsonPath("$.sowingDepth").value(DEFAULT_SOWING_DEPTH))
            .andExpect(jsonPath("$.rowSpacingMax").value(DEFAULT_ROW_SPACING_MAX))
            .andExpect(jsonPath("$.rowSpacingMin").value(DEFAULT_ROW_SPACING_MIN))
            .andExpect(jsonPath("$.seedDepthMax").value(DEFAULT_SEED_DEPTH_MAX))
            .andExpect(jsonPath("$.seedDepthMin").value(DEFAULT_SEED_DEPTH_MIN))
            .andExpect(jsonPath("$.seedSpacingMax").value(DEFAULT_SEED_SPACING_MAX))
            .andExpect(jsonPath("$.seedSpacingMin").value(DEFAULT_SEED_SPACING_MIN))
            .andExpect(jsonPath("$.yearlyCrops").value(DEFAULT_YEARLY_CROPS))
            .andExpect(jsonPath("$.growingSeason").value(DEFAULT_GROWING_SEASON))
            .andExpect(jsonPath("$.growingPhase").value(DEFAULT_GROWING_PHASE.toString()))
            .andExpect(jsonPath("$.plantingDate").value(DEFAULT_PLANTING_DATE.toString()))
            .andExpect(jsonPath("$.plantSpacing").value(DEFAULT_PLANT_SPACING))
            .andExpect(jsonPath("$.plantingMaterial").value(DEFAULT_PLANTING_MATERIAL.toString()))
            .andExpect(jsonPath("$.transplantationDate").value(DEFAULT_TRANSPLANTATION_DATE.toString()))
            .andExpect(jsonPath("$.fertigationscheduleID").value(DEFAULT_FERTIGATIONSCHEDULE_ID.intValue()))
            .andExpect(jsonPath("$.plannedYield").value(DEFAULT_PLANNED_YIELD))
            .andExpect(jsonPath("$.actualYield").value(DEFAULT_ACTUAL_YIELD))
            .andExpect(jsonPath("$.yieldUnit").value(DEFAULT_YIELD_UNIT))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingCrop() throws Exception {
        // Get the crop
        restCropMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCrop() throws Exception {
        // Initialize the database
        cropRepository.saveAndFlush(crop);

        int databaseSizeBeforeUpdate = cropRepository.findAll().size();

        // Update the crop
        Crop updatedCrop = cropRepository.findById(crop.getId()).get();
        // Disconnect from session so that the updates on updatedCrop are not directly saved in db
        em.detach(updatedCrop);
        updatedCrop
            .gUID(UPDATED_G_UID)
            .cropCode(UPDATED_CROP_CODE)
            .cropName(UPDATED_CROP_NAME)
            .cropType(UPDATED_CROP_TYPE)
            .horticultureType(UPDATED_HORTICULTURE_TYPE)
            .isHybrid(UPDATED_IS_HYBRID)
            .cultivar(UPDATED_CULTIVAR)
            .sowingDate(UPDATED_SOWING_DATE)
            .sowingDepth(UPDATED_SOWING_DEPTH)
            .rowSpacingMax(UPDATED_ROW_SPACING_MAX)
            .rowSpacingMin(UPDATED_ROW_SPACING_MIN)
            .seedDepthMax(UPDATED_SEED_DEPTH_MAX)
            .seedDepthMin(UPDATED_SEED_DEPTH_MIN)
            .seedSpacingMax(UPDATED_SEED_SPACING_MAX)
            .seedSpacingMin(UPDATED_SEED_SPACING_MIN)
            .yearlyCrops(UPDATED_YEARLY_CROPS)
            .growingSeason(UPDATED_GROWING_SEASON)
            .growingPhase(UPDATED_GROWING_PHASE)
            .plantingDate(UPDATED_PLANTING_DATE)
            .plantSpacing(UPDATED_PLANT_SPACING)
            .plantingMaterial(UPDATED_PLANTING_MATERIAL)
            .transplantationDate(UPDATED_TRANSPLANTATION_DATE)
            .fertigationscheduleID(UPDATED_FERTIGATIONSCHEDULE_ID)
            .plannedYield(UPDATED_PLANNED_YIELD)
            .actualYield(UPDATED_ACTUAL_YIELD)
            .yieldUnit(UPDATED_YIELD_UNIT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCrop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCrop))
            )
            .andExpect(status().isOk());

        // Validate the Crop in the database
        List<Crop> cropList = cropRepository.findAll();
        assertThat(cropList).hasSize(databaseSizeBeforeUpdate);
        Crop testCrop = cropList.get(cropList.size() - 1);
        assertThat(testCrop.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testCrop.getCropCode()).isEqualTo(UPDATED_CROP_CODE);
        assertThat(testCrop.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testCrop.getCropType()).isEqualTo(UPDATED_CROP_TYPE);
        assertThat(testCrop.getHorticultureType()).isEqualTo(UPDATED_HORTICULTURE_TYPE);
        assertThat(testCrop.getIsHybrid()).isEqualTo(UPDATED_IS_HYBRID);
        assertThat(testCrop.getCultivar()).isEqualTo(UPDATED_CULTIVAR);
        assertThat(testCrop.getSowingDate()).isEqualTo(UPDATED_SOWING_DATE);
        assertThat(testCrop.getSowingDepth()).isEqualTo(UPDATED_SOWING_DEPTH);
        assertThat(testCrop.getRowSpacingMax()).isEqualTo(UPDATED_ROW_SPACING_MAX);
        assertThat(testCrop.getRowSpacingMin()).isEqualTo(UPDATED_ROW_SPACING_MIN);
        assertThat(testCrop.getSeedDepthMax()).isEqualTo(UPDATED_SEED_DEPTH_MAX);
        assertThat(testCrop.getSeedDepthMin()).isEqualTo(UPDATED_SEED_DEPTH_MIN);
        assertThat(testCrop.getSeedSpacingMax()).isEqualTo(UPDATED_SEED_SPACING_MAX);
        assertThat(testCrop.getSeedSpacingMin()).isEqualTo(UPDATED_SEED_SPACING_MIN);
        assertThat(testCrop.getYearlyCrops()).isEqualTo(UPDATED_YEARLY_CROPS);
        assertThat(testCrop.getGrowingSeason()).isEqualTo(UPDATED_GROWING_SEASON);
        assertThat(testCrop.getGrowingPhase()).isEqualTo(UPDATED_GROWING_PHASE);
        assertThat(testCrop.getPlantingDate()).isEqualTo(UPDATED_PLANTING_DATE);
        assertThat(testCrop.getPlantSpacing()).isEqualTo(UPDATED_PLANT_SPACING);
        assertThat(testCrop.getPlantingMaterial()).isEqualTo(UPDATED_PLANTING_MATERIAL);
        assertThat(testCrop.getTransplantationDate()).isEqualTo(UPDATED_TRANSPLANTATION_DATE);
        assertThat(testCrop.getFertigationscheduleID()).isEqualTo(UPDATED_FERTIGATIONSCHEDULE_ID);
        assertThat(testCrop.getPlannedYield()).isEqualTo(UPDATED_PLANNED_YIELD);
        assertThat(testCrop.getActualYield()).isEqualTo(UPDATED_ACTUAL_YIELD);
        assertThat(testCrop.getYieldUnit()).isEqualTo(UPDATED_YIELD_UNIT);
        assertThat(testCrop.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCrop.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCrop.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCrop.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingCrop() throws Exception {
        int databaseSizeBeforeUpdate = cropRepository.findAll().size();
        crop.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, crop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crop in the database
        List<Crop> cropList = cropRepository.findAll();
        assertThat(cropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCrop() throws Exception {
        int databaseSizeBeforeUpdate = cropRepository.findAll().size();
        crop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crop in the database
        List<Crop> cropList = cropRepository.findAll();
        assertThat(cropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCrop() throws Exception {
        int databaseSizeBeforeUpdate = cropRepository.findAll().size();
        crop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(crop)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Crop in the database
        List<Crop> cropList = cropRepository.findAll();
        assertThat(cropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCropWithPatch() throws Exception {
        // Initialize the database
        cropRepository.saveAndFlush(crop);

        int databaseSizeBeforeUpdate = cropRepository.findAll().size();

        // Update the crop using partial update
        Crop partialUpdatedCrop = new Crop();
        partialUpdatedCrop.setId(crop.getId());

        partialUpdatedCrop
            .cropCode(UPDATED_CROP_CODE)
            .cropName(UPDATED_CROP_NAME)
            .cropType(UPDATED_CROP_TYPE)
            .horticultureType(UPDATED_HORTICULTURE_TYPE)
            .isHybrid(UPDATED_IS_HYBRID)
            .cultivar(UPDATED_CULTIVAR)
            .sowingDate(UPDATED_SOWING_DATE)
            .yearlyCrops(UPDATED_YEARLY_CROPS)
            .growingSeason(UPDATED_GROWING_SEASON)
            .plantingDate(UPDATED_PLANTING_DATE)
            .plantSpacing(UPDATED_PLANT_SPACING)
            .plantingMaterial(UPDATED_PLANTING_MATERIAL)
            .transplantationDate(UPDATED_TRANSPLANTATION_DATE)
            .fertigationscheduleID(UPDATED_FERTIGATIONSCHEDULE_ID)
            .actualYield(UPDATED_ACTUAL_YIELD)
            .yieldUnit(UPDATED_YIELD_UNIT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCrop))
            )
            .andExpect(status().isOk());

        // Validate the Crop in the database
        List<Crop> cropList = cropRepository.findAll();
        assertThat(cropList).hasSize(databaseSizeBeforeUpdate);
        Crop testCrop = cropList.get(cropList.size() - 1);
        assertThat(testCrop.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testCrop.getCropCode()).isEqualTo(UPDATED_CROP_CODE);
        assertThat(testCrop.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testCrop.getCropType()).isEqualTo(UPDATED_CROP_TYPE);
        assertThat(testCrop.getHorticultureType()).isEqualTo(UPDATED_HORTICULTURE_TYPE);
        assertThat(testCrop.getIsHybrid()).isEqualTo(UPDATED_IS_HYBRID);
        assertThat(testCrop.getCultivar()).isEqualTo(UPDATED_CULTIVAR);
        assertThat(testCrop.getSowingDate()).isEqualTo(UPDATED_SOWING_DATE);
        assertThat(testCrop.getSowingDepth()).isEqualTo(DEFAULT_SOWING_DEPTH);
        assertThat(testCrop.getRowSpacingMax()).isEqualTo(DEFAULT_ROW_SPACING_MAX);
        assertThat(testCrop.getRowSpacingMin()).isEqualTo(DEFAULT_ROW_SPACING_MIN);
        assertThat(testCrop.getSeedDepthMax()).isEqualTo(DEFAULT_SEED_DEPTH_MAX);
        assertThat(testCrop.getSeedDepthMin()).isEqualTo(DEFAULT_SEED_DEPTH_MIN);
        assertThat(testCrop.getSeedSpacingMax()).isEqualTo(DEFAULT_SEED_SPACING_MAX);
        assertThat(testCrop.getSeedSpacingMin()).isEqualTo(DEFAULT_SEED_SPACING_MIN);
        assertThat(testCrop.getYearlyCrops()).isEqualTo(UPDATED_YEARLY_CROPS);
        assertThat(testCrop.getGrowingSeason()).isEqualTo(UPDATED_GROWING_SEASON);
        assertThat(testCrop.getGrowingPhase()).isEqualTo(DEFAULT_GROWING_PHASE);
        assertThat(testCrop.getPlantingDate()).isEqualTo(UPDATED_PLANTING_DATE);
        assertThat(testCrop.getPlantSpacing()).isEqualTo(UPDATED_PLANT_SPACING);
        assertThat(testCrop.getPlantingMaterial()).isEqualTo(UPDATED_PLANTING_MATERIAL);
        assertThat(testCrop.getTransplantationDate()).isEqualTo(UPDATED_TRANSPLANTATION_DATE);
        assertThat(testCrop.getFertigationscheduleID()).isEqualTo(UPDATED_FERTIGATIONSCHEDULE_ID);
        assertThat(testCrop.getPlannedYield()).isEqualTo(DEFAULT_PLANNED_YIELD);
        assertThat(testCrop.getActualYield()).isEqualTo(UPDATED_ACTUAL_YIELD);
        assertThat(testCrop.getYieldUnit()).isEqualTo(UPDATED_YIELD_UNIT);
        assertThat(testCrop.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCrop.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCrop.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCrop.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateCropWithPatch() throws Exception {
        // Initialize the database
        cropRepository.saveAndFlush(crop);

        int databaseSizeBeforeUpdate = cropRepository.findAll().size();

        // Update the crop using partial update
        Crop partialUpdatedCrop = new Crop();
        partialUpdatedCrop.setId(crop.getId());

        partialUpdatedCrop
            .gUID(UPDATED_G_UID)
            .cropCode(UPDATED_CROP_CODE)
            .cropName(UPDATED_CROP_NAME)
            .cropType(UPDATED_CROP_TYPE)
            .horticultureType(UPDATED_HORTICULTURE_TYPE)
            .isHybrid(UPDATED_IS_HYBRID)
            .cultivar(UPDATED_CULTIVAR)
            .sowingDate(UPDATED_SOWING_DATE)
            .sowingDepth(UPDATED_SOWING_DEPTH)
            .rowSpacingMax(UPDATED_ROW_SPACING_MAX)
            .rowSpacingMin(UPDATED_ROW_SPACING_MIN)
            .seedDepthMax(UPDATED_SEED_DEPTH_MAX)
            .seedDepthMin(UPDATED_SEED_DEPTH_MIN)
            .seedSpacingMax(UPDATED_SEED_SPACING_MAX)
            .seedSpacingMin(UPDATED_SEED_SPACING_MIN)
            .yearlyCrops(UPDATED_YEARLY_CROPS)
            .growingSeason(UPDATED_GROWING_SEASON)
            .growingPhase(UPDATED_GROWING_PHASE)
            .plantingDate(UPDATED_PLANTING_DATE)
            .plantSpacing(UPDATED_PLANT_SPACING)
            .plantingMaterial(UPDATED_PLANTING_MATERIAL)
            .transplantationDate(UPDATED_TRANSPLANTATION_DATE)
            .fertigationscheduleID(UPDATED_FERTIGATIONSCHEDULE_ID)
            .plannedYield(UPDATED_PLANNED_YIELD)
            .actualYield(UPDATED_ACTUAL_YIELD)
            .yieldUnit(UPDATED_YIELD_UNIT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCrop))
            )
            .andExpect(status().isOk());

        // Validate the Crop in the database
        List<Crop> cropList = cropRepository.findAll();
        assertThat(cropList).hasSize(databaseSizeBeforeUpdate);
        Crop testCrop = cropList.get(cropList.size() - 1);
        assertThat(testCrop.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testCrop.getCropCode()).isEqualTo(UPDATED_CROP_CODE);
        assertThat(testCrop.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testCrop.getCropType()).isEqualTo(UPDATED_CROP_TYPE);
        assertThat(testCrop.getHorticultureType()).isEqualTo(UPDATED_HORTICULTURE_TYPE);
        assertThat(testCrop.getIsHybrid()).isEqualTo(UPDATED_IS_HYBRID);
        assertThat(testCrop.getCultivar()).isEqualTo(UPDATED_CULTIVAR);
        assertThat(testCrop.getSowingDate()).isEqualTo(UPDATED_SOWING_DATE);
        assertThat(testCrop.getSowingDepth()).isEqualTo(UPDATED_SOWING_DEPTH);
        assertThat(testCrop.getRowSpacingMax()).isEqualTo(UPDATED_ROW_SPACING_MAX);
        assertThat(testCrop.getRowSpacingMin()).isEqualTo(UPDATED_ROW_SPACING_MIN);
        assertThat(testCrop.getSeedDepthMax()).isEqualTo(UPDATED_SEED_DEPTH_MAX);
        assertThat(testCrop.getSeedDepthMin()).isEqualTo(UPDATED_SEED_DEPTH_MIN);
        assertThat(testCrop.getSeedSpacingMax()).isEqualTo(UPDATED_SEED_SPACING_MAX);
        assertThat(testCrop.getSeedSpacingMin()).isEqualTo(UPDATED_SEED_SPACING_MIN);
        assertThat(testCrop.getYearlyCrops()).isEqualTo(UPDATED_YEARLY_CROPS);
        assertThat(testCrop.getGrowingSeason()).isEqualTo(UPDATED_GROWING_SEASON);
        assertThat(testCrop.getGrowingPhase()).isEqualTo(UPDATED_GROWING_PHASE);
        assertThat(testCrop.getPlantingDate()).isEqualTo(UPDATED_PLANTING_DATE);
        assertThat(testCrop.getPlantSpacing()).isEqualTo(UPDATED_PLANT_SPACING);
        assertThat(testCrop.getPlantingMaterial()).isEqualTo(UPDATED_PLANTING_MATERIAL);
        assertThat(testCrop.getTransplantationDate()).isEqualTo(UPDATED_TRANSPLANTATION_DATE);
        assertThat(testCrop.getFertigationscheduleID()).isEqualTo(UPDATED_FERTIGATIONSCHEDULE_ID);
        assertThat(testCrop.getPlannedYield()).isEqualTo(UPDATED_PLANNED_YIELD);
        assertThat(testCrop.getActualYield()).isEqualTo(UPDATED_ACTUAL_YIELD);
        assertThat(testCrop.getYieldUnit()).isEqualTo(UPDATED_YIELD_UNIT);
        assertThat(testCrop.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCrop.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCrop.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCrop.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingCrop() throws Exception {
        int databaseSizeBeforeUpdate = cropRepository.findAll().size();
        crop.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, crop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(crop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crop in the database
        List<Crop> cropList = cropRepository.findAll();
        assertThat(cropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCrop() throws Exception {
        int databaseSizeBeforeUpdate = cropRepository.findAll().size();
        crop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(crop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crop in the database
        List<Crop> cropList = cropRepository.findAll();
        assertThat(cropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCrop() throws Exception {
        int databaseSizeBeforeUpdate = cropRepository.findAll().size();
        crop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(crop)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Crop in the database
        List<Crop> cropList = cropRepository.findAll();
        assertThat(cropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCrop() throws Exception {
        // Initialize the database
        cropRepository.saveAndFlush(crop);

        int databaseSizeBeforeDelete = cropRepository.findAll().size();

        // Delete the crop
        restCropMockMvc
            .perform(delete(ENTITY_API_URL_ID, crop.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Crop> cropList = cropRepository.findAll();
        assertThat(cropList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
