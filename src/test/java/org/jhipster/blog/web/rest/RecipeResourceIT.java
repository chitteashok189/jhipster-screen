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
import org.jhipster.blog.domain.Recipe;
import org.jhipster.blog.domain.enumeration.Ec;
import org.jhipster.blog.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RecipeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecipeResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_PLANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RECIPE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RECIPE_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_P_H_MIN = 1L;
    private static final Long UPDATED_P_H_MIN = 2L;

    private static final Long DEFAULT_P_H_MAX = 1L;
    private static final Long UPDATED_P_H_MAX = 2L;

    private static final Ec DEFAULT_EC_MIN = Ec.Olericulture;
    private static final Ec UPDATED_EC_MIN = Ec.Floriculture;

    private static final Integer DEFAULT_E_C_MAX = 1;
    private static final Integer UPDATED_E_C_MAX = 2;

    private static final Integer DEFAULT_AIR_TEMP_MAX = 1;
    private static final Integer UPDATED_AIR_TEMP_MAX = 2;

    private static final Integer DEFAULT_AIR_TEMP_MIN = 1;
    private static final Integer UPDATED_AIR_TEMP_MIN = 2;

    private static final Integer DEFAULT_HUMIDITY_MAX = 1;
    private static final Integer UPDATED_HUMIDITY_MAX = 2;

    private static final Integer DEFAULT_HUMIDITY_MIN = 1;
    private static final Integer UPDATED_HUMIDITY_MIN = 2;

    private static final Integer DEFAULT_NUTRIENT_TEMP_MAX = 1;
    private static final Integer UPDATED_NUTRIENT_TEMP_MAX = 2;

    private static final Integer DEFAULT_NUTRIENT_TEMP_MIN = 1;
    private static final Integer UPDATED_NUTRIENT_TEMP_MIN = 2;

    private static final Integer DEFAULT_LUX_GERM_MAX = 1;
    private static final Integer UPDATED_LUX_GERM_MAX = 2;

    private static final Integer DEFAULT_LUX_GERM_MIN = 1;
    private static final Integer UPDATED_LUX_GERM_MIN = 2;

    private static final Integer DEFAULT_LIGHT_GERM_DOR = 1;
    private static final Integer UPDATED_LIGHT_GERM_DOR = 2;

    private static final Integer DEFAULT_LIGHT_GERM_CYCLE = 1;
    private static final Integer UPDATED_LIGHT_GERM_CYCLE = 2;

    private static final Integer DEFAULT_LUX_GROW_MAX = 1;
    private static final Integer UPDATED_LUX_GROW_MAX = 2;

    private static final Integer DEFAULT_LUX_GROW_MIN = 1;
    private static final Integer UPDATED_LUX_GROW_MIN = 2;

    private static final Integer DEFAULT_LIGHT_GROW_DOR = 1;
    private static final Integer UPDATED_LIGHT_GROW_DOR = 2;

    private static final Integer DEFAULT_LIGHT_GROW_CYCLE = 1;
    private static final Integer UPDATED_LIGHT_GROW_CYCLE = 2;

    private static final Integer DEFAULT_CO_2_LIGHT_MAX = 1;
    private static final Integer UPDATED_CO_2_LIGHT_MAX = 2;

    private static final Integer DEFAULT_CO_2_LIGHT_MIN = 1;
    private static final Integer UPDATED_CO_2_LIGHT_MIN = 2;

    private static final Integer DEFAULT_CO_2_DARK_MAX = 1;
    private static final Integer UPDATED_CO_2_DARK_MAX = 2;

    private static final Integer DEFAULT_CO_2_DARK_MIN = 1;
    private static final Integer UPDATED_CO_2_DARK_MIN = 2;

    private static final Integer DEFAULT_D_O_MAX = 1;
    private static final Integer UPDATED_D_O_MAX = 2;

    private static final Integer DEFAULT_D_O_MIN = 1;
    private static final Integer UPDATED_D_O_MIN = 2;

    private static final Integer DEFAULT_MEDIA_MOISTURE_MAX = 1;
    private static final Integer UPDATED_MEDIA_MOISTURE_MAX = 2;

    private static final Integer DEFAULT_MEDIA_MOISTURE_MIN = 1;
    private static final Integer UPDATED_MEDIA_MOISTURE_MIN = 2;

    private static final Integer DEFAULT_NITROGEN = 1;
    private static final Integer UPDATED_NITROGEN = 2;

    private static final Integer DEFAULT_PHOSPHORUS = 1;
    private static final Integer UPDATED_PHOSPHORUS = 2;

    private static final Integer DEFAULT_POTASSIUM = 1;
    private static final Integer UPDATED_POTASSIUM = 2;

    private static final Integer DEFAULT_SULPHUR = 1;
    private static final Integer UPDATED_SULPHUR = 2;

    private static final Integer DEFAULT_CALCIUM = 1;
    private static final Integer UPDATED_CALCIUM = 2;

    private static final Integer DEFAULT_MAGNESIUM = 1;
    private static final Integer UPDATED_MAGNESIUM = 2;

    private static final Integer DEFAULT_MANGANESE = 1;
    private static final Integer UPDATED_MANGANESE = 2;

    private static final Integer DEFAULT_IRON = 1;
    private static final Integer UPDATED_IRON = 2;

    private static final Integer DEFAULT_BORON = 1;
    private static final Integer UPDATED_BORON = 2;

    private static final Integer DEFAULT_COPPER = 1;
    private static final Integer UPDATED_COPPER = 2;

    private static final Integer DEFAULT_ZINC = 1;
    private static final Integer UPDATED_ZINC = 2;

    private static final Integer DEFAULT_MOLYBDENUM = 1;
    private static final Integer UPDATED_MOLYBDENUM = 2;

    private static final Integer DEFAULT_GERMINATION_TAT = 1;
    private static final Integer UPDATED_GERMINATION_TAT = 2;

    private static final String DEFAULT_IDENTIFICATION_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATION_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_GROWING_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_GROWING_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_USAGE_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_USAGE_COMMENT = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/recipes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipeMockMvc;

    private Recipe recipe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipe createEntity(EntityManager em) {
        Recipe recipe = new Recipe()
            .gUID(DEFAULT_G_UID)
            .plantName(DEFAULT_PLANT_NAME)
            .recipeType(DEFAULT_RECIPE_TYPE)
            .pHMin(DEFAULT_P_H_MIN)
            .pHMax(DEFAULT_P_H_MAX)
            .ecMin(DEFAULT_EC_MIN)
            .eCMax(DEFAULT_E_C_MAX)
            .airTempMax(DEFAULT_AIR_TEMP_MAX)
            .airTempMin(DEFAULT_AIR_TEMP_MIN)
            .humidityMax(DEFAULT_HUMIDITY_MAX)
            .humidityMin(DEFAULT_HUMIDITY_MIN)
            .nutrientTempMax(DEFAULT_NUTRIENT_TEMP_MAX)
            .nutrientTempMin(DEFAULT_NUTRIENT_TEMP_MIN)
            .luxGermMax(DEFAULT_LUX_GERM_MAX)
            .luxGermMin(DEFAULT_LUX_GERM_MIN)
            .lightGermDor(DEFAULT_LIGHT_GERM_DOR)
            .lightGermCycle(DEFAULT_LIGHT_GERM_CYCLE)
            .luxGrowMax(DEFAULT_LUX_GROW_MAX)
            .luxGrowMin(DEFAULT_LUX_GROW_MIN)
            .lightGrowDor(DEFAULT_LIGHT_GROW_DOR)
            .lightGrowCycle(DEFAULT_LIGHT_GROW_CYCLE)
            .co2LightMax(DEFAULT_CO_2_LIGHT_MAX)
            .co2LightMin(DEFAULT_CO_2_LIGHT_MIN)
            .co2DarkMax(DEFAULT_CO_2_DARK_MAX)
            .co2DarkMin(DEFAULT_CO_2_DARK_MIN)
            .dOMax(DEFAULT_D_O_MAX)
            .dOMin(DEFAULT_D_O_MIN)
            .mediaMoistureMax(DEFAULT_MEDIA_MOISTURE_MAX)
            .mediaMoistureMin(DEFAULT_MEDIA_MOISTURE_MIN)
            .nitrogen(DEFAULT_NITROGEN)
            .phosphorus(DEFAULT_PHOSPHORUS)
            .potassium(DEFAULT_POTASSIUM)
            .sulphur(DEFAULT_SULPHUR)
            .calcium(DEFAULT_CALCIUM)
            .magnesium(DEFAULT_MAGNESIUM)
            .manganese(DEFAULT_MANGANESE)
            .iron(DEFAULT_IRON)
            .boron(DEFAULT_BORON)
            .copper(DEFAULT_COPPER)
            .zinc(DEFAULT_ZINC)
            .molybdenum(DEFAULT_MOLYBDENUM)
            .germinationTAT(DEFAULT_GERMINATION_TAT)
            .identificationComment(DEFAULT_IDENTIFICATION_COMMENT)
            .growingComment(DEFAULT_GROWING_COMMENT)
            .usageComment(DEFAULT_USAGE_COMMENT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return recipe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipe createUpdatedEntity(EntityManager em) {
        Recipe recipe = new Recipe()
            .gUID(UPDATED_G_UID)
            .plantName(UPDATED_PLANT_NAME)
            .recipeType(UPDATED_RECIPE_TYPE)
            .pHMin(UPDATED_P_H_MIN)
            .pHMax(UPDATED_P_H_MAX)
            .ecMin(UPDATED_EC_MIN)
            .eCMax(UPDATED_E_C_MAX)
            .airTempMax(UPDATED_AIR_TEMP_MAX)
            .airTempMin(UPDATED_AIR_TEMP_MIN)
            .humidityMax(UPDATED_HUMIDITY_MAX)
            .humidityMin(UPDATED_HUMIDITY_MIN)
            .nutrientTempMax(UPDATED_NUTRIENT_TEMP_MAX)
            .nutrientTempMin(UPDATED_NUTRIENT_TEMP_MIN)
            .luxGermMax(UPDATED_LUX_GERM_MAX)
            .luxGermMin(UPDATED_LUX_GERM_MIN)
            .lightGermDor(UPDATED_LIGHT_GERM_DOR)
            .lightGermCycle(UPDATED_LIGHT_GERM_CYCLE)
            .luxGrowMax(UPDATED_LUX_GROW_MAX)
            .luxGrowMin(UPDATED_LUX_GROW_MIN)
            .lightGrowDor(UPDATED_LIGHT_GROW_DOR)
            .lightGrowCycle(UPDATED_LIGHT_GROW_CYCLE)
            .co2LightMax(UPDATED_CO_2_LIGHT_MAX)
            .co2LightMin(UPDATED_CO_2_LIGHT_MIN)
            .co2DarkMax(UPDATED_CO_2_DARK_MAX)
            .co2DarkMin(UPDATED_CO_2_DARK_MIN)
            .dOMax(UPDATED_D_O_MAX)
            .dOMin(UPDATED_D_O_MIN)
            .mediaMoistureMax(UPDATED_MEDIA_MOISTURE_MAX)
            .mediaMoistureMin(UPDATED_MEDIA_MOISTURE_MIN)
            .nitrogen(UPDATED_NITROGEN)
            .phosphorus(UPDATED_PHOSPHORUS)
            .potassium(UPDATED_POTASSIUM)
            .sulphur(UPDATED_SULPHUR)
            .calcium(UPDATED_CALCIUM)
            .magnesium(UPDATED_MAGNESIUM)
            .manganese(UPDATED_MANGANESE)
            .iron(UPDATED_IRON)
            .boron(UPDATED_BORON)
            .copper(UPDATED_COPPER)
            .zinc(UPDATED_ZINC)
            .molybdenum(UPDATED_MOLYBDENUM)
            .germinationTAT(UPDATED_GERMINATION_TAT)
            .identificationComment(UPDATED_IDENTIFICATION_COMMENT)
            .growingComment(UPDATED_GROWING_COMMENT)
            .usageComment(UPDATED_USAGE_COMMENT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return recipe;
    }

    @BeforeEach
    public void initTest() {
        recipe = createEntity(em);
    }

    @Test
    @Transactional
    void createRecipe() throws Exception {
        int databaseSizeBeforeCreate = recipeRepository.findAll().size();
        // Create the Recipe
        restRecipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recipe)))
            .andExpect(status().isCreated());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeCreate + 1);
        Recipe testRecipe = recipeList.get(recipeList.size() - 1);
        assertThat(testRecipe.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testRecipe.getPlantName()).isEqualTo(DEFAULT_PLANT_NAME);
        assertThat(testRecipe.getRecipeType()).isEqualTo(DEFAULT_RECIPE_TYPE);
        assertThat(testRecipe.getpHMin()).isEqualTo(DEFAULT_P_H_MIN);
        assertThat(testRecipe.getpHMax()).isEqualTo(DEFAULT_P_H_MAX);
        assertThat(testRecipe.getEcMin()).isEqualTo(DEFAULT_EC_MIN);
        assertThat(testRecipe.geteCMax()).isEqualTo(DEFAULT_E_C_MAX);
        assertThat(testRecipe.getAirTempMax()).isEqualTo(DEFAULT_AIR_TEMP_MAX);
        assertThat(testRecipe.getAirTempMin()).isEqualTo(DEFAULT_AIR_TEMP_MIN);
        assertThat(testRecipe.getHumidityMax()).isEqualTo(DEFAULT_HUMIDITY_MAX);
        assertThat(testRecipe.getHumidityMin()).isEqualTo(DEFAULT_HUMIDITY_MIN);
        assertThat(testRecipe.getNutrientTempMax()).isEqualTo(DEFAULT_NUTRIENT_TEMP_MAX);
        assertThat(testRecipe.getNutrientTempMin()).isEqualTo(DEFAULT_NUTRIENT_TEMP_MIN);
        assertThat(testRecipe.getLuxGermMax()).isEqualTo(DEFAULT_LUX_GERM_MAX);
        assertThat(testRecipe.getLuxGermMin()).isEqualTo(DEFAULT_LUX_GERM_MIN);
        assertThat(testRecipe.getLightGermDor()).isEqualTo(DEFAULT_LIGHT_GERM_DOR);
        assertThat(testRecipe.getLightGermCycle()).isEqualTo(DEFAULT_LIGHT_GERM_CYCLE);
        assertThat(testRecipe.getLuxGrowMax()).isEqualTo(DEFAULT_LUX_GROW_MAX);
        assertThat(testRecipe.getLuxGrowMin()).isEqualTo(DEFAULT_LUX_GROW_MIN);
        assertThat(testRecipe.getLightGrowDor()).isEqualTo(DEFAULT_LIGHT_GROW_DOR);
        assertThat(testRecipe.getLightGrowCycle()).isEqualTo(DEFAULT_LIGHT_GROW_CYCLE);
        assertThat(testRecipe.getCo2LightMax()).isEqualTo(DEFAULT_CO_2_LIGHT_MAX);
        assertThat(testRecipe.getCo2LightMin()).isEqualTo(DEFAULT_CO_2_LIGHT_MIN);
        assertThat(testRecipe.getCo2DarkMax()).isEqualTo(DEFAULT_CO_2_DARK_MAX);
        assertThat(testRecipe.getCo2DarkMin()).isEqualTo(DEFAULT_CO_2_DARK_MIN);
        assertThat(testRecipe.getdOMax()).isEqualTo(DEFAULT_D_O_MAX);
        assertThat(testRecipe.getdOMin()).isEqualTo(DEFAULT_D_O_MIN);
        assertThat(testRecipe.getMediaMoistureMax()).isEqualTo(DEFAULT_MEDIA_MOISTURE_MAX);
        assertThat(testRecipe.getMediaMoistureMin()).isEqualTo(DEFAULT_MEDIA_MOISTURE_MIN);
        assertThat(testRecipe.getNitrogen()).isEqualTo(DEFAULT_NITROGEN);
        assertThat(testRecipe.getPhosphorus()).isEqualTo(DEFAULT_PHOSPHORUS);
        assertThat(testRecipe.getPotassium()).isEqualTo(DEFAULT_POTASSIUM);
        assertThat(testRecipe.getSulphur()).isEqualTo(DEFAULT_SULPHUR);
        assertThat(testRecipe.getCalcium()).isEqualTo(DEFAULT_CALCIUM);
        assertThat(testRecipe.getMagnesium()).isEqualTo(DEFAULT_MAGNESIUM);
        assertThat(testRecipe.getManganese()).isEqualTo(DEFAULT_MANGANESE);
        assertThat(testRecipe.getIron()).isEqualTo(DEFAULT_IRON);
        assertThat(testRecipe.getBoron()).isEqualTo(DEFAULT_BORON);
        assertThat(testRecipe.getCopper()).isEqualTo(DEFAULT_COPPER);
        assertThat(testRecipe.getZinc()).isEqualTo(DEFAULT_ZINC);
        assertThat(testRecipe.getMolybdenum()).isEqualTo(DEFAULT_MOLYBDENUM);
        assertThat(testRecipe.getGerminationTAT()).isEqualTo(DEFAULT_GERMINATION_TAT);
        assertThat(testRecipe.getIdentificationComment()).isEqualTo(DEFAULT_IDENTIFICATION_COMMENT);
        assertThat(testRecipe.getGrowingComment()).isEqualTo(DEFAULT_GROWING_COMMENT);
        assertThat(testRecipe.getUsageComment()).isEqualTo(DEFAULT_USAGE_COMMENT);
        assertThat(testRecipe.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRecipe.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRecipe.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRecipe.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createRecipeWithExistingId() throws Exception {
        // Create the Recipe with an existing ID
        recipe.setId(1L);

        int databaseSizeBeforeCreate = recipeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recipe)))
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRecipes() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList
        restRecipeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].plantName").value(hasItem(DEFAULT_PLANT_NAME)))
            .andExpect(jsonPath("$.[*].recipeType").value(hasItem(DEFAULT_RECIPE_TYPE)))
            .andExpect(jsonPath("$.[*].pHMin").value(hasItem(DEFAULT_P_H_MIN.intValue())))
            .andExpect(jsonPath("$.[*].pHMax").value(hasItem(DEFAULT_P_H_MAX.intValue())))
            .andExpect(jsonPath("$.[*].ecMin").value(hasItem(DEFAULT_EC_MIN.toString())))
            .andExpect(jsonPath("$.[*].eCMax").value(hasItem(DEFAULT_E_C_MAX)))
            .andExpect(jsonPath("$.[*].airTempMax").value(hasItem(DEFAULT_AIR_TEMP_MAX)))
            .andExpect(jsonPath("$.[*].airTempMin").value(hasItem(DEFAULT_AIR_TEMP_MIN)))
            .andExpect(jsonPath("$.[*].humidityMax").value(hasItem(DEFAULT_HUMIDITY_MAX)))
            .andExpect(jsonPath("$.[*].humidityMin").value(hasItem(DEFAULT_HUMIDITY_MIN)))
            .andExpect(jsonPath("$.[*].nutrientTempMax").value(hasItem(DEFAULT_NUTRIENT_TEMP_MAX)))
            .andExpect(jsonPath("$.[*].nutrientTempMin").value(hasItem(DEFAULT_NUTRIENT_TEMP_MIN)))
            .andExpect(jsonPath("$.[*].luxGermMax").value(hasItem(DEFAULT_LUX_GERM_MAX)))
            .andExpect(jsonPath("$.[*].luxGermMin").value(hasItem(DEFAULT_LUX_GERM_MIN)))
            .andExpect(jsonPath("$.[*].lightGermDor").value(hasItem(DEFAULT_LIGHT_GERM_DOR)))
            .andExpect(jsonPath("$.[*].lightGermCycle").value(hasItem(DEFAULT_LIGHT_GERM_CYCLE)))
            .andExpect(jsonPath("$.[*].luxGrowMax").value(hasItem(DEFAULT_LUX_GROW_MAX)))
            .andExpect(jsonPath("$.[*].luxGrowMin").value(hasItem(DEFAULT_LUX_GROW_MIN)))
            .andExpect(jsonPath("$.[*].lightGrowDor").value(hasItem(DEFAULT_LIGHT_GROW_DOR)))
            .andExpect(jsonPath("$.[*].lightGrowCycle").value(hasItem(DEFAULT_LIGHT_GROW_CYCLE)))
            .andExpect(jsonPath("$.[*].co2LightMax").value(hasItem(DEFAULT_CO_2_LIGHT_MAX)))
            .andExpect(jsonPath("$.[*].co2LightMin").value(hasItem(DEFAULT_CO_2_LIGHT_MIN)))
            .andExpect(jsonPath("$.[*].co2DarkMax").value(hasItem(DEFAULT_CO_2_DARK_MAX)))
            .andExpect(jsonPath("$.[*].co2DarkMin").value(hasItem(DEFAULT_CO_2_DARK_MIN)))
            .andExpect(jsonPath("$.[*].dOMax").value(hasItem(DEFAULT_D_O_MAX)))
            .andExpect(jsonPath("$.[*].dOMin").value(hasItem(DEFAULT_D_O_MIN)))
            .andExpect(jsonPath("$.[*].mediaMoistureMax").value(hasItem(DEFAULT_MEDIA_MOISTURE_MAX)))
            .andExpect(jsonPath("$.[*].mediaMoistureMin").value(hasItem(DEFAULT_MEDIA_MOISTURE_MIN)))
            .andExpect(jsonPath("$.[*].nitrogen").value(hasItem(DEFAULT_NITROGEN)))
            .andExpect(jsonPath("$.[*].phosphorus").value(hasItem(DEFAULT_PHOSPHORUS)))
            .andExpect(jsonPath("$.[*].potassium").value(hasItem(DEFAULT_POTASSIUM)))
            .andExpect(jsonPath("$.[*].sulphur").value(hasItem(DEFAULT_SULPHUR)))
            .andExpect(jsonPath("$.[*].calcium").value(hasItem(DEFAULT_CALCIUM)))
            .andExpect(jsonPath("$.[*].magnesium").value(hasItem(DEFAULT_MAGNESIUM)))
            .andExpect(jsonPath("$.[*].manganese").value(hasItem(DEFAULT_MANGANESE)))
            .andExpect(jsonPath("$.[*].iron").value(hasItem(DEFAULT_IRON)))
            .andExpect(jsonPath("$.[*].boron").value(hasItem(DEFAULT_BORON)))
            .andExpect(jsonPath("$.[*].copper").value(hasItem(DEFAULT_COPPER)))
            .andExpect(jsonPath("$.[*].zinc").value(hasItem(DEFAULT_ZINC)))
            .andExpect(jsonPath("$.[*].molybdenum").value(hasItem(DEFAULT_MOLYBDENUM)))
            .andExpect(jsonPath("$.[*].germinationTAT").value(hasItem(DEFAULT_GERMINATION_TAT)))
            .andExpect(jsonPath("$.[*].identificationComment").value(hasItem(DEFAULT_IDENTIFICATION_COMMENT)))
            .andExpect(jsonPath("$.[*].growingComment").value(hasItem(DEFAULT_GROWING_COMMENT)))
            .andExpect(jsonPath("$.[*].usageComment").value(hasItem(DEFAULT_USAGE_COMMENT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getRecipe() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get the recipe
        restRecipeMockMvc
            .perform(get(ENTITY_API_URL_ID, recipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipe.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.plantName").value(DEFAULT_PLANT_NAME))
            .andExpect(jsonPath("$.recipeType").value(DEFAULT_RECIPE_TYPE))
            .andExpect(jsonPath("$.pHMin").value(DEFAULT_P_H_MIN.intValue()))
            .andExpect(jsonPath("$.pHMax").value(DEFAULT_P_H_MAX.intValue()))
            .andExpect(jsonPath("$.ecMin").value(DEFAULT_EC_MIN.toString()))
            .andExpect(jsonPath("$.eCMax").value(DEFAULT_E_C_MAX))
            .andExpect(jsonPath("$.airTempMax").value(DEFAULT_AIR_TEMP_MAX))
            .andExpect(jsonPath("$.airTempMin").value(DEFAULT_AIR_TEMP_MIN))
            .andExpect(jsonPath("$.humidityMax").value(DEFAULT_HUMIDITY_MAX))
            .andExpect(jsonPath("$.humidityMin").value(DEFAULT_HUMIDITY_MIN))
            .andExpect(jsonPath("$.nutrientTempMax").value(DEFAULT_NUTRIENT_TEMP_MAX))
            .andExpect(jsonPath("$.nutrientTempMin").value(DEFAULT_NUTRIENT_TEMP_MIN))
            .andExpect(jsonPath("$.luxGermMax").value(DEFAULT_LUX_GERM_MAX))
            .andExpect(jsonPath("$.luxGermMin").value(DEFAULT_LUX_GERM_MIN))
            .andExpect(jsonPath("$.lightGermDor").value(DEFAULT_LIGHT_GERM_DOR))
            .andExpect(jsonPath("$.lightGermCycle").value(DEFAULT_LIGHT_GERM_CYCLE))
            .andExpect(jsonPath("$.luxGrowMax").value(DEFAULT_LUX_GROW_MAX))
            .andExpect(jsonPath("$.luxGrowMin").value(DEFAULT_LUX_GROW_MIN))
            .andExpect(jsonPath("$.lightGrowDor").value(DEFAULT_LIGHT_GROW_DOR))
            .andExpect(jsonPath("$.lightGrowCycle").value(DEFAULT_LIGHT_GROW_CYCLE))
            .andExpect(jsonPath("$.co2LightMax").value(DEFAULT_CO_2_LIGHT_MAX))
            .andExpect(jsonPath("$.co2LightMin").value(DEFAULT_CO_2_LIGHT_MIN))
            .andExpect(jsonPath("$.co2DarkMax").value(DEFAULT_CO_2_DARK_MAX))
            .andExpect(jsonPath("$.co2DarkMin").value(DEFAULT_CO_2_DARK_MIN))
            .andExpect(jsonPath("$.dOMax").value(DEFAULT_D_O_MAX))
            .andExpect(jsonPath("$.dOMin").value(DEFAULT_D_O_MIN))
            .andExpect(jsonPath("$.mediaMoistureMax").value(DEFAULT_MEDIA_MOISTURE_MAX))
            .andExpect(jsonPath("$.mediaMoistureMin").value(DEFAULT_MEDIA_MOISTURE_MIN))
            .andExpect(jsonPath("$.nitrogen").value(DEFAULT_NITROGEN))
            .andExpect(jsonPath("$.phosphorus").value(DEFAULT_PHOSPHORUS))
            .andExpect(jsonPath("$.potassium").value(DEFAULT_POTASSIUM))
            .andExpect(jsonPath("$.sulphur").value(DEFAULT_SULPHUR))
            .andExpect(jsonPath("$.calcium").value(DEFAULT_CALCIUM))
            .andExpect(jsonPath("$.magnesium").value(DEFAULT_MAGNESIUM))
            .andExpect(jsonPath("$.manganese").value(DEFAULT_MANGANESE))
            .andExpect(jsonPath("$.iron").value(DEFAULT_IRON))
            .andExpect(jsonPath("$.boron").value(DEFAULT_BORON))
            .andExpect(jsonPath("$.copper").value(DEFAULT_COPPER))
            .andExpect(jsonPath("$.zinc").value(DEFAULT_ZINC))
            .andExpect(jsonPath("$.molybdenum").value(DEFAULT_MOLYBDENUM))
            .andExpect(jsonPath("$.germinationTAT").value(DEFAULT_GERMINATION_TAT))
            .andExpect(jsonPath("$.identificationComment").value(DEFAULT_IDENTIFICATION_COMMENT))
            .andExpect(jsonPath("$.growingComment").value(DEFAULT_GROWING_COMMENT))
            .andExpect(jsonPath("$.usageComment").value(DEFAULT_USAGE_COMMENT))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingRecipe() throws Exception {
        // Get the recipe
        restRecipeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRecipe() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();

        // Update the recipe
        Recipe updatedRecipe = recipeRepository.findById(recipe.getId()).get();
        // Disconnect from session so that the updates on updatedRecipe are not directly saved in db
        em.detach(updatedRecipe);
        updatedRecipe
            .gUID(UPDATED_G_UID)
            .plantName(UPDATED_PLANT_NAME)
            .recipeType(UPDATED_RECIPE_TYPE)
            .pHMin(UPDATED_P_H_MIN)
            .pHMax(UPDATED_P_H_MAX)
            .ecMin(UPDATED_EC_MIN)
            .eCMax(UPDATED_E_C_MAX)
            .airTempMax(UPDATED_AIR_TEMP_MAX)
            .airTempMin(UPDATED_AIR_TEMP_MIN)
            .humidityMax(UPDATED_HUMIDITY_MAX)
            .humidityMin(UPDATED_HUMIDITY_MIN)
            .nutrientTempMax(UPDATED_NUTRIENT_TEMP_MAX)
            .nutrientTempMin(UPDATED_NUTRIENT_TEMP_MIN)
            .luxGermMax(UPDATED_LUX_GERM_MAX)
            .luxGermMin(UPDATED_LUX_GERM_MIN)
            .lightGermDor(UPDATED_LIGHT_GERM_DOR)
            .lightGermCycle(UPDATED_LIGHT_GERM_CYCLE)
            .luxGrowMax(UPDATED_LUX_GROW_MAX)
            .luxGrowMin(UPDATED_LUX_GROW_MIN)
            .lightGrowDor(UPDATED_LIGHT_GROW_DOR)
            .lightGrowCycle(UPDATED_LIGHT_GROW_CYCLE)
            .co2LightMax(UPDATED_CO_2_LIGHT_MAX)
            .co2LightMin(UPDATED_CO_2_LIGHT_MIN)
            .co2DarkMax(UPDATED_CO_2_DARK_MAX)
            .co2DarkMin(UPDATED_CO_2_DARK_MIN)
            .dOMax(UPDATED_D_O_MAX)
            .dOMin(UPDATED_D_O_MIN)
            .mediaMoistureMax(UPDATED_MEDIA_MOISTURE_MAX)
            .mediaMoistureMin(UPDATED_MEDIA_MOISTURE_MIN)
            .nitrogen(UPDATED_NITROGEN)
            .phosphorus(UPDATED_PHOSPHORUS)
            .potassium(UPDATED_POTASSIUM)
            .sulphur(UPDATED_SULPHUR)
            .calcium(UPDATED_CALCIUM)
            .magnesium(UPDATED_MAGNESIUM)
            .manganese(UPDATED_MANGANESE)
            .iron(UPDATED_IRON)
            .boron(UPDATED_BORON)
            .copper(UPDATED_COPPER)
            .zinc(UPDATED_ZINC)
            .molybdenum(UPDATED_MOLYBDENUM)
            .germinationTAT(UPDATED_GERMINATION_TAT)
            .identificationComment(UPDATED_IDENTIFICATION_COMMENT)
            .growingComment(UPDATED_GROWING_COMMENT)
            .usageComment(UPDATED_USAGE_COMMENT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restRecipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRecipe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRecipe))
            )
            .andExpect(status().isOk());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
        Recipe testRecipe = recipeList.get(recipeList.size() - 1);
        assertThat(testRecipe.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testRecipe.getPlantName()).isEqualTo(UPDATED_PLANT_NAME);
        assertThat(testRecipe.getRecipeType()).isEqualTo(UPDATED_RECIPE_TYPE);
        assertThat(testRecipe.getpHMin()).isEqualTo(UPDATED_P_H_MIN);
        assertThat(testRecipe.getpHMax()).isEqualTo(UPDATED_P_H_MAX);
        assertThat(testRecipe.getEcMin()).isEqualTo(UPDATED_EC_MIN);
        assertThat(testRecipe.geteCMax()).isEqualTo(UPDATED_E_C_MAX);
        assertThat(testRecipe.getAirTempMax()).isEqualTo(UPDATED_AIR_TEMP_MAX);
        assertThat(testRecipe.getAirTempMin()).isEqualTo(UPDATED_AIR_TEMP_MIN);
        assertThat(testRecipe.getHumidityMax()).isEqualTo(UPDATED_HUMIDITY_MAX);
        assertThat(testRecipe.getHumidityMin()).isEqualTo(UPDATED_HUMIDITY_MIN);
        assertThat(testRecipe.getNutrientTempMax()).isEqualTo(UPDATED_NUTRIENT_TEMP_MAX);
        assertThat(testRecipe.getNutrientTempMin()).isEqualTo(UPDATED_NUTRIENT_TEMP_MIN);
        assertThat(testRecipe.getLuxGermMax()).isEqualTo(UPDATED_LUX_GERM_MAX);
        assertThat(testRecipe.getLuxGermMin()).isEqualTo(UPDATED_LUX_GERM_MIN);
        assertThat(testRecipe.getLightGermDor()).isEqualTo(UPDATED_LIGHT_GERM_DOR);
        assertThat(testRecipe.getLightGermCycle()).isEqualTo(UPDATED_LIGHT_GERM_CYCLE);
        assertThat(testRecipe.getLuxGrowMax()).isEqualTo(UPDATED_LUX_GROW_MAX);
        assertThat(testRecipe.getLuxGrowMin()).isEqualTo(UPDATED_LUX_GROW_MIN);
        assertThat(testRecipe.getLightGrowDor()).isEqualTo(UPDATED_LIGHT_GROW_DOR);
        assertThat(testRecipe.getLightGrowCycle()).isEqualTo(UPDATED_LIGHT_GROW_CYCLE);
        assertThat(testRecipe.getCo2LightMax()).isEqualTo(UPDATED_CO_2_LIGHT_MAX);
        assertThat(testRecipe.getCo2LightMin()).isEqualTo(UPDATED_CO_2_LIGHT_MIN);
        assertThat(testRecipe.getCo2DarkMax()).isEqualTo(UPDATED_CO_2_DARK_MAX);
        assertThat(testRecipe.getCo2DarkMin()).isEqualTo(UPDATED_CO_2_DARK_MIN);
        assertThat(testRecipe.getdOMax()).isEqualTo(UPDATED_D_O_MAX);
        assertThat(testRecipe.getdOMin()).isEqualTo(UPDATED_D_O_MIN);
        assertThat(testRecipe.getMediaMoistureMax()).isEqualTo(UPDATED_MEDIA_MOISTURE_MAX);
        assertThat(testRecipe.getMediaMoistureMin()).isEqualTo(UPDATED_MEDIA_MOISTURE_MIN);
        assertThat(testRecipe.getNitrogen()).isEqualTo(UPDATED_NITROGEN);
        assertThat(testRecipe.getPhosphorus()).isEqualTo(UPDATED_PHOSPHORUS);
        assertThat(testRecipe.getPotassium()).isEqualTo(UPDATED_POTASSIUM);
        assertThat(testRecipe.getSulphur()).isEqualTo(UPDATED_SULPHUR);
        assertThat(testRecipe.getCalcium()).isEqualTo(UPDATED_CALCIUM);
        assertThat(testRecipe.getMagnesium()).isEqualTo(UPDATED_MAGNESIUM);
        assertThat(testRecipe.getManganese()).isEqualTo(UPDATED_MANGANESE);
        assertThat(testRecipe.getIron()).isEqualTo(UPDATED_IRON);
        assertThat(testRecipe.getBoron()).isEqualTo(UPDATED_BORON);
        assertThat(testRecipe.getCopper()).isEqualTo(UPDATED_COPPER);
        assertThat(testRecipe.getZinc()).isEqualTo(UPDATED_ZINC);
        assertThat(testRecipe.getMolybdenum()).isEqualTo(UPDATED_MOLYBDENUM);
        assertThat(testRecipe.getGerminationTAT()).isEqualTo(UPDATED_GERMINATION_TAT);
        assertThat(testRecipe.getIdentificationComment()).isEqualTo(UPDATED_IDENTIFICATION_COMMENT);
        assertThat(testRecipe.getGrowingComment()).isEqualTo(UPDATED_GROWING_COMMENT);
        assertThat(testRecipe.getUsageComment()).isEqualTo(UPDATED_USAGE_COMMENT);
        assertThat(testRecipe.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRecipe.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRecipe.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRecipe.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingRecipe() throws Exception {
        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();
        recipe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recipe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecipe() throws Exception {
        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();
        recipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecipe() throws Exception {
        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();
        recipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recipe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecipeWithPatch() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();

        // Update the recipe using partial update
        Recipe partialUpdatedRecipe = new Recipe();
        partialUpdatedRecipe.setId(recipe.getId());

        partialUpdatedRecipe
            .gUID(UPDATED_G_UID)
            .plantName(UPDATED_PLANT_NAME)
            .humidityMax(UPDATED_HUMIDITY_MAX)
            .humidityMin(UPDATED_HUMIDITY_MIN)
            .luxGermMax(UPDATED_LUX_GERM_MAX)
            .lightGermCycle(UPDATED_LIGHT_GERM_CYCLE)
            .luxGrowMax(UPDATED_LUX_GROW_MAX)
            .lightGrowCycle(UPDATED_LIGHT_GROW_CYCLE)
            .co2LightMax(UPDATED_CO_2_LIGHT_MAX)
            .co2DarkMax(UPDATED_CO_2_DARK_MAX)
            .co2DarkMin(UPDATED_CO_2_DARK_MIN)
            .dOMax(UPDATED_D_O_MAX)
            .dOMin(UPDATED_D_O_MIN)
            .mediaMoistureMax(UPDATED_MEDIA_MOISTURE_MAX)
            .nitrogen(UPDATED_NITROGEN)
            .potassium(UPDATED_POTASSIUM)
            .sulphur(UPDATED_SULPHUR)
            .calcium(UPDATED_CALCIUM)
            .magnesium(UPDATED_MAGNESIUM)
            .iron(UPDATED_IRON)
            .germinationTAT(UPDATED_GERMINATION_TAT)
            .identificationComment(UPDATED_IDENTIFICATION_COMMENT)
            .growingComment(UPDATED_GROWING_COMMENT)
            .updatedBy(UPDATED_UPDATED_BY);

        restRecipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecipe))
            )
            .andExpect(status().isOk());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
        Recipe testRecipe = recipeList.get(recipeList.size() - 1);
        assertThat(testRecipe.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testRecipe.getPlantName()).isEqualTo(UPDATED_PLANT_NAME);
        assertThat(testRecipe.getRecipeType()).isEqualTo(DEFAULT_RECIPE_TYPE);
        assertThat(testRecipe.getpHMin()).isEqualTo(DEFAULT_P_H_MIN);
        assertThat(testRecipe.getpHMax()).isEqualTo(DEFAULT_P_H_MAX);
        assertThat(testRecipe.getEcMin()).isEqualTo(DEFAULT_EC_MIN);
        assertThat(testRecipe.geteCMax()).isEqualTo(DEFAULT_E_C_MAX);
        assertThat(testRecipe.getAirTempMax()).isEqualTo(DEFAULT_AIR_TEMP_MAX);
        assertThat(testRecipe.getAirTempMin()).isEqualTo(DEFAULT_AIR_TEMP_MIN);
        assertThat(testRecipe.getHumidityMax()).isEqualTo(UPDATED_HUMIDITY_MAX);
        assertThat(testRecipe.getHumidityMin()).isEqualTo(UPDATED_HUMIDITY_MIN);
        assertThat(testRecipe.getNutrientTempMax()).isEqualTo(DEFAULT_NUTRIENT_TEMP_MAX);
        assertThat(testRecipe.getNutrientTempMin()).isEqualTo(DEFAULT_NUTRIENT_TEMP_MIN);
        assertThat(testRecipe.getLuxGermMax()).isEqualTo(UPDATED_LUX_GERM_MAX);
        assertThat(testRecipe.getLuxGermMin()).isEqualTo(DEFAULT_LUX_GERM_MIN);
        assertThat(testRecipe.getLightGermDor()).isEqualTo(DEFAULT_LIGHT_GERM_DOR);
        assertThat(testRecipe.getLightGermCycle()).isEqualTo(UPDATED_LIGHT_GERM_CYCLE);
        assertThat(testRecipe.getLuxGrowMax()).isEqualTo(UPDATED_LUX_GROW_MAX);
        assertThat(testRecipe.getLuxGrowMin()).isEqualTo(DEFAULT_LUX_GROW_MIN);
        assertThat(testRecipe.getLightGrowDor()).isEqualTo(DEFAULT_LIGHT_GROW_DOR);
        assertThat(testRecipe.getLightGrowCycle()).isEqualTo(UPDATED_LIGHT_GROW_CYCLE);
        assertThat(testRecipe.getCo2LightMax()).isEqualTo(UPDATED_CO_2_LIGHT_MAX);
        assertThat(testRecipe.getCo2LightMin()).isEqualTo(DEFAULT_CO_2_LIGHT_MIN);
        assertThat(testRecipe.getCo2DarkMax()).isEqualTo(UPDATED_CO_2_DARK_MAX);
        assertThat(testRecipe.getCo2DarkMin()).isEqualTo(UPDATED_CO_2_DARK_MIN);
        assertThat(testRecipe.getdOMax()).isEqualTo(UPDATED_D_O_MAX);
        assertThat(testRecipe.getdOMin()).isEqualTo(UPDATED_D_O_MIN);
        assertThat(testRecipe.getMediaMoistureMax()).isEqualTo(UPDATED_MEDIA_MOISTURE_MAX);
        assertThat(testRecipe.getMediaMoistureMin()).isEqualTo(DEFAULT_MEDIA_MOISTURE_MIN);
        assertThat(testRecipe.getNitrogen()).isEqualTo(UPDATED_NITROGEN);
        assertThat(testRecipe.getPhosphorus()).isEqualTo(DEFAULT_PHOSPHORUS);
        assertThat(testRecipe.getPotassium()).isEqualTo(UPDATED_POTASSIUM);
        assertThat(testRecipe.getSulphur()).isEqualTo(UPDATED_SULPHUR);
        assertThat(testRecipe.getCalcium()).isEqualTo(UPDATED_CALCIUM);
        assertThat(testRecipe.getMagnesium()).isEqualTo(UPDATED_MAGNESIUM);
        assertThat(testRecipe.getManganese()).isEqualTo(DEFAULT_MANGANESE);
        assertThat(testRecipe.getIron()).isEqualTo(UPDATED_IRON);
        assertThat(testRecipe.getBoron()).isEqualTo(DEFAULT_BORON);
        assertThat(testRecipe.getCopper()).isEqualTo(DEFAULT_COPPER);
        assertThat(testRecipe.getZinc()).isEqualTo(DEFAULT_ZINC);
        assertThat(testRecipe.getMolybdenum()).isEqualTo(DEFAULT_MOLYBDENUM);
        assertThat(testRecipe.getGerminationTAT()).isEqualTo(UPDATED_GERMINATION_TAT);
        assertThat(testRecipe.getIdentificationComment()).isEqualTo(UPDATED_IDENTIFICATION_COMMENT);
        assertThat(testRecipe.getGrowingComment()).isEqualTo(UPDATED_GROWING_COMMENT);
        assertThat(testRecipe.getUsageComment()).isEqualTo(DEFAULT_USAGE_COMMENT);
        assertThat(testRecipe.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRecipe.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRecipe.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRecipe.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateRecipeWithPatch() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();

        // Update the recipe using partial update
        Recipe partialUpdatedRecipe = new Recipe();
        partialUpdatedRecipe.setId(recipe.getId());

        partialUpdatedRecipe
            .gUID(UPDATED_G_UID)
            .plantName(UPDATED_PLANT_NAME)
            .recipeType(UPDATED_RECIPE_TYPE)
            .pHMin(UPDATED_P_H_MIN)
            .pHMax(UPDATED_P_H_MAX)
            .ecMin(UPDATED_EC_MIN)
            .eCMax(UPDATED_E_C_MAX)
            .airTempMax(UPDATED_AIR_TEMP_MAX)
            .airTempMin(UPDATED_AIR_TEMP_MIN)
            .humidityMax(UPDATED_HUMIDITY_MAX)
            .humidityMin(UPDATED_HUMIDITY_MIN)
            .nutrientTempMax(UPDATED_NUTRIENT_TEMP_MAX)
            .nutrientTempMin(UPDATED_NUTRIENT_TEMP_MIN)
            .luxGermMax(UPDATED_LUX_GERM_MAX)
            .luxGermMin(UPDATED_LUX_GERM_MIN)
            .lightGermDor(UPDATED_LIGHT_GERM_DOR)
            .lightGermCycle(UPDATED_LIGHT_GERM_CYCLE)
            .luxGrowMax(UPDATED_LUX_GROW_MAX)
            .luxGrowMin(UPDATED_LUX_GROW_MIN)
            .lightGrowDor(UPDATED_LIGHT_GROW_DOR)
            .lightGrowCycle(UPDATED_LIGHT_GROW_CYCLE)
            .co2LightMax(UPDATED_CO_2_LIGHT_MAX)
            .co2LightMin(UPDATED_CO_2_LIGHT_MIN)
            .co2DarkMax(UPDATED_CO_2_DARK_MAX)
            .co2DarkMin(UPDATED_CO_2_DARK_MIN)
            .dOMax(UPDATED_D_O_MAX)
            .dOMin(UPDATED_D_O_MIN)
            .mediaMoistureMax(UPDATED_MEDIA_MOISTURE_MAX)
            .mediaMoistureMin(UPDATED_MEDIA_MOISTURE_MIN)
            .nitrogen(UPDATED_NITROGEN)
            .phosphorus(UPDATED_PHOSPHORUS)
            .potassium(UPDATED_POTASSIUM)
            .sulphur(UPDATED_SULPHUR)
            .calcium(UPDATED_CALCIUM)
            .magnesium(UPDATED_MAGNESIUM)
            .manganese(UPDATED_MANGANESE)
            .iron(UPDATED_IRON)
            .boron(UPDATED_BORON)
            .copper(UPDATED_COPPER)
            .zinc(UPDATED_ZINC)
            .molybdenum(UPDATED_MOLYBDENUM)
            .germinationTAT(UPDATED_GERMINATION_TAT)
            .identificationComment(UPDATED_IDENTIFICATION_COMMENT)
            .growingComment(UPDATED_GROWING_COMMENT)
            .usageComment(UPDATED_USAGE_COMMENT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restRecipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecipe))
            )
            .andExpect(status().isOk());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
        Recipe testRecipe = recipeList.get(recipeList.size() - 1);
        assertThat(testRecipe.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testRecipe.getPlantName()).isEqualTo(UPDATED_PLANT_NAME);
        assertThat(testRecipe.getRecipeType()).isEqualTo(UPDATED_RECIPE_TYPE);
        assertThat(testRecipe.getpHMin()).isEqualTo(UPDATED_P_H_MIN);
        assertThat(testRecipe.getpHMax()).isEqualTo(UPDATED_P_H_MAX);
        assertThat(testRecipe.getEcMin()).isEqualTo(UPDATED_EC_MIN);
        assertThat(testRecipe.geteCMax()).isEqualTo(UPDATED_E_C_MAX);
        assertThat(testRecipe.getAirTempMax()).isEqualTo(UPDATED_AIR_TEMP_MAX);
        assertThat(testRecipe.getAirTempMin()).isEqualTo(UPDATED_AIR_TEMP_MIN);
        assertThat(testRecipe.getHumidityMax()).isEqualTo(UPDATED_HUMIDITY_MAX);
        assertThat(testRecipe.getHumidityMin()).isEqualTo(UPDATED_HUMIDITY_MIN);
        assertThat(testRecipe.getNutrientTempMax()).isEqualTo(UPDATED_NUTRIENT_TEMP_MAX);
        assertThat(testRecipe.getNutrientTempMin()).isEqualTo(UPDATED_NUTRIENT_TEMP_MIN);
        assertThat(testRecipe.getLuxGermMax()).isEqualTo(UPDATED_LUX_GERM_MAX);
        assertThat(testRecipe.getLuxGermMin()).isEqualTo(UPDATED_LUX_GERM_MIN);
        assertThat(testRecipe.getLightGermDor()).isEqualTo(UPDATED_LIGHT_GERM_DOR);
        assertThat(testRecipe.getLightGermCycle()).isEqualTo(UPDATED_LIGHT_GERM_CYCLE);
        assertThat(testRecipe.getLuxGrowMax()).isEqualTo(UPDATED_LUX_GROW_MAX);
        assertThat(testRecipe.getLuxGrowMin()).isEqualTo(UPDATED_LUX_GROW_MIN);
        assertThat(testRecipe.getLightGrowDor()).isEqualTo(UPDATED_LIGHT_GROW_DOR);
        assertThat(testRecipe.getLightGrowCycle()).isEqualTo(UPDATED_LIGHT_GROW_CYCLE);
        assertThat(testRecipe.getCo2LightMax()).isEqualTo(UPDATED_CO_2_LIGHT_MAX);
        assertThat(testRecipe.getCo2LightMin()).isEqualTo(UPDATED_CO_2_LIGHT_MIN);
        assertThat(testRecipe.getCo2DarkMax()).isEqualTo(UPDATED_CO_2_DARK_MAX);
        assertThat(testRecipe.getCo2DarkMin()).isEqualTo(UPDATED_CO_2_DARK_MIN);
        assertThat(testRecipe.getdOMax()).isEqualTo(UPDATED_D_O_MAX);
        assertThat(testRecipe.getdOMin()).isEqualTo(UPDATED_D_O_MIN);
        assertThat(testRecipe.getMediaMoistureMax()).isEqualTo(UPDATED_MEDIA_MOISTURE_MAX);
        assertThat(testRecipe.getMediaMoistureMin()).isEqualTo(UPDATED_MEDIA_MOISTURE_MIN);
        assertThat(testRecipe.getNitrogen()).isEqualTo(UPDATED_NITROGEN);
        assertThat(testRecipe.getPhosphorus()).isEqualTo(UPDATED_PHOSPHORUS);
        assertThat(testRecipe.getPotassium()).isEqualTo(UPDATED_POTASSIUM);
        assertThat(testRecipe.getSulphur()).isEqualTo(UPDATED_SULPHUR);
        assertThat(testRecipe.getCalcium()).isEqualTo(UPDATED_CALCIUM);
        assertThat(testRecipe.getMagnesium()).isEqualTo(UPDATED_MAGNESIUM);
        assertThat(testRecipe.getManganese()).isEqualTo(UPDATED_MANGANESE);
        assertThat(testRecipe.getIron()).isEqualTo(UPDATED_IRON);
        assertThat(testRecipe.getBoron()).isEqualTo(UPDATED_BORON);
        assertThat(testRecipe.getCopper()).isEqualTo(UPDATED_COPPER);
        assertThat(testRecipe.getZinc()).isEqualTo(UPDATED_ZINC);
        assertThat(testRecipe.getMolybdenum()).isEqualTo(UPDATED_MOLYBDENUM);
        assertThat(testRecipe.getGerminationTAT()).isEqualTo(UPDATED_GERMINATION_TAT);
        assertThat(testRecipe.getIdentificationComment()).isEqualTo(UPDATED_IDENTIFICATION_COMMENT);
        assertThat(testRecipe.getGrowingComment()).isEqualTo(UPDATED_GROWING_COMMENT);
        assertThat(testRecipe.getUsageComment()).isEqualTo(UPDATED_USAGE_COMMENT);
        assertThat(testRecipe.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRecipe.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRecipe.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRecipe.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingRecipe() throws Exception {
        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();
        recipe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecipe() throws Exception {
        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();
        recipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecipe() throws Exception {
        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();
        recipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(recipe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecipe() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        int databaseSizeBeforeDelete = recipeRepository.findAll().size();

        // Delete the recipe
        restRecipeMockMvc
            .perform(delete(ENTITY_API_URL_ID, recipe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
