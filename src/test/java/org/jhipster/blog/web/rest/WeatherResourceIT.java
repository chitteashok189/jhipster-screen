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
import org.jhipster.blog.domain.Weather;
import org.jhipster.blog.repository.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WeatherResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WeatherResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Long DEFAULT_CITY_ID = 1L;
    private static final Long UPDATED_CITY_ID = 2L;

    private static final Integer DEFAULT_START_TIMESTAMP = 1;
    private static final Integer UPDATED_START_TIMESTAMP = 2;

    private static final Integer DEFAULT_END_TIMESTAMP = 1;
    private static final Integer UPDATED_END_TIMESTAMP = 2;

    private static final Long DEFAULT_WEATHER_STATUS_ID = 1L;
    private static final Long UPDATED_WEATHER_STATUS_ID = 2L;

    private static final Integer DEFAULT_TEMPERATURE = 1;
    private static final Integer UPDATED_TEMPERATURE = 2;

    private static final Integer DEFAULT_FEELS_LIKE_TEMPERATURE = 1;
    private static final Integer UPDATED_FEELS_LIKE_TEMPERATURE = 2;

    private static final Integer DEFAULT_HUMIDITY = 1;
    private static final Integer UPDATED_HUMIDITY = 2;

    private static final Integer DEFAULT_WIND_SPEED = 1;
    private static final Integer UPDATED_WIND_SPEED = 2;

    private static final Integer DEFAULT_WIND_DIRECTION = 1;
    private static final Integer UPDATED_WIND_DIRECTION = 2;

    private static final Integer DEFAULT_PRESSUREINMMHG = 1;
    private static final Integer UPDATED_PRESSUREINMMHG = 2;

    private static final Integer DEFAULT_VISIBILITYINMPH = 1;
    private static final Integer UPDATED_VISIBILITYINMPH = 2;

    private static final Integer DEFAULT_CLOUD_COVER = 1;
    private static final Integer UPDATED_CLOUD_COVER = 2;

    private static final Integer DEFAULT_PRECIPITATION = 1;
    private static final Integer UPDATED_PRECIPITATION = 2;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/weathers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWeatherMockMvc;

    private Weather weather;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Weather createEntity(EntityManager em) {
        Weather weather = new Weather()
            .gUID(DEFAULT_G_UID)
            .cityID(DEFAULT_CITY_ID)
            .startTimestamp(DEFAULT_START_TIMESTAMP)
            .endTimestamp(DEFAULT_END_TIMESTAMP)
            .weatherStatusID(DEFAULT_WEATHER_STATUS_ID)
            .temperature(DEFAULT_TEMPERATURE)
            .feelsLikeTemperature(DEFAULT_FEELS_LIKE_TEMPERATURE)
            .humidity(DEFAULT_HUMIDITY)
            .windSpeed(DEFAULT_WIND_SPEED)
            .windDirection(DEFAULT_WIND_DIRECTION)
            .pressureinmmhg(DEFAULT_PRESSUREINMMHG)
            .visibilityinmph(DEFAULT_VISIBILITYINMPH)
            .cloudCover(DEFAULT_CLOUD_COVER)
            .precipitation(DEFAULT_PRECIPITATION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return weather;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Weather createUpdatedEntity(EntityManager em) {
        Weather weather = new Weather()
            .gUID(UPDATED_G_UID)
            .cityID(UPDATED_CITY_ID)
            .startTimestamp(UPDATED_START_TIMESTAMP)
            .endTimestamp(UPDATED_END_TIMESTAMP)
            .weatherStatusID(UPDATED_WEATHER_STATUS_ID)
            .temperature(UPDATED_TEMPERATURE)
            .feelsLikeTemperature(UPDATED_FEELS_LIKE_TEMPERATURE)
            .humidity(UPDATED_HUMIDITY)
            .windSpeed(UPDATED_WIND_SPEED)
            .windDirection(UPDATED_WIND_DIRECTION)
            .pressureinmmhg(UPDATED_PRESSUREINMMHG)
            .visibilityinmph(UPDATED_VISIBILITYINMPH)
            .cloudCover(UPDATED_CLOUD_COVER)
            .precipitation(UPDATED_PRECIPITATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return weather;
    }

    @BeforeEach
    public void initTest() {
        weather = createEntity(em);
    }

    @Test
    @Transactional
    void createWeather() throws Exception {
        int databaseSizeBeforeCreate = weatherRepository.findAll().size();
        // Create the Weather
        restWeatherMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isCreated());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeCreate + 1);
        Weather testWeather = weatherList.get(weatherList.size() - 1);
        assertThat(testWeather.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testWeather.getCityID()).isEqualTo(DEFAULT_CITY_ID);
        assertThat(testWeather.getStartTimestamp()).isEqualTo(DEFAULT_START_TIMESTAMP);
        assertThat(testWeather.getEndTimestamp()).isEqualTo(DEFAULT_END_TIMESTAMP);
        assertThat(testWeather.getWeatherStatusID()).isEqualTo(DEFAULT_WEATHER_STATUS_ID);
        assertThat(testWeather.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testWeather.getFeelsLikeTemperature()).isEqualTo(DEFAULT_FEELS_LIKE_TEMPERATURE);
        assertThat(testWeather.getHumidity()).isEqualTo(DEFAULT_HUMIDITY);
        assertThat(testWeather.getWindSpeed()).isEqualTo(DEFAULT_WIND_SPEED);
        assertThat(testWeather.getWindDirection()).isEqualTo(DEFAULT_WIND_DIRECTION);
        assertThat(testWeather.getPressureinmmhg()).isEqualTo(DEFAULT_PRESSUREINMMHG);
        assertThat(testWeather.getVisibilityinmph()).isEqualTo(DEFAULT_VISIBILITYINMPH);
        assertThat(testWeather.getCloudCover()).isEqualTo(DEFAULT_CLOUD_COVER);
        assertThat(testWeather.getPrecipitation()).isEqualTo(DEFAULT_PRECIPITATION);
        assertThat(testWeather.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWeather.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testWeather.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testWeather.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createWeatherWithExistingId() throws Exception {
        // Create the Weather with an existing ID
        weather.setId(1L);

        int databaseSizeBeforeCreate = weatherRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeatherMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWeathers() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);

        // Get all the weatherList
        restWeatherMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weather.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].cityID").value(hasItem(DEFAULT_CITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].startTimestamp").value(hasItem(DEFAULT_START_TIMESTAMP)))
            .andExpect(jsonPath("$.[*].endTimestamp").value(hasItem(DEFAULT_END_TIMESTAMP)))
            .andExpect(jsonPath("$.[*].weatherStatusID").value(hasItem(DEFAULT_WEATHER_STATUS_ID.intValue())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].feelsLikeTemperature").value(hasItem(DEFAULT_FEELS_LIKE_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].humidity").value(hasItem(DEFAULT_HUMIDITY)))
            .andExpect(jsonPath("$.[*].windSpeed").value(hasItem(DEFAULT_WIND_SPEED)))
            .andExpect(jsonPath("$.[*].windDirection").value(hasItem(DEFAULT_WIND_DIRECTION)))
            .andExpect(jsonPath("$.[*].pressureinmmhg").value(hasItem(DEFAULT_PRESSUREINMMHG)))
            .andExpect(jsonPath("$.[*].visibilityinmph").value(hasItem(DEFAULT_VISIBILITYINMPH)))
            .andExpect(jsonPath("$.[*].cloudCover").value(hasItem(DEFAULT_CLOUD_COVER)))
            .andExpect(jsonPath("$.[*].precipitation").value(hasItem(DEFAULT_PRECIPITATION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getWeather() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);

        // Get the weather
        restWeatherMockMvc
            .perform(get(ENTITY_API_URL_ID, weather.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(weather.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.cityID").value(DEFAULT_CITY_ID.intValue()))
            .andExpect(jsonPath("$.startTimestamp").value(DEFAULT_START_TIMESTAMP))
            .andExpect(jsonPath("$.endTimestamp").value(DEFAULT_END_TIMESTAMP))
            .andExpect(jsonPath("$.weatherStatusID").value(DEFAULT_WEATHER_STATUS_ID.intValue()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE))
            .andExpect(jsonPath("$.feelsLikeTemperature").value(DEFAULT_FEELS_LIKE_TEMPERATURE))
            .andExpect(jsonPath("$.humidity").value(DEFAULT_HUMIDITY))
            .andExpect(jsonPath("$.windSpeed").value(DEFAULT_WIND_SPEED))
            .andExpect(jsonPath("$.windDirection").value(DEFAULT_WIND_DIRECTION))
            .andExpect(jsonPath("$.pressureinmmhg").value(DEFAULT_PRESSUREINMMHG))
            .andExpect(jsonPath("$.visibilityinmph").value(DEFAULT_VISIBILITYINMPH))
            .andExpect(jsonPath("$.cloudCover").value(DEFAULT_CLOUD_COVER))
            .andExpect(jsonPath("$.precipitation").value(DEFAULT_PRECIPITATION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingWeather() throws Exception {
        // Get the weather
        restWeatherMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWeather() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);

        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();

        // Update the weather
        Weather updatedWeather = weatherRepository.findById(weather.getId()).get();
        // Disconnect from session so that the updates on updatedWeather are not directly saved in db
        em.detach(updatedWeather);
        updatedWeather
            .gUID(UPDATED_G_UID)
            .cityID(UPDATED_CITY_ID)
            .startTimestamp(UPDATED_START_TIMESTAMP)
            .endTimestamp(UPDATED_END_TIMESTAMP)
            .weatherStatusID(UPDATED_WEATHER_STATUS_ID)
            .temperature(UPDATED_TEMPERATURE)
            .feelsLikeTemperature(UPDATED_FEELS_LIKE_TEMPERATURE)
            .humidity(UPDATED_HUMIDITY)
            .windSpeed(UPDATED_WIND_SPEED)
            .windDirection(UPDATED_WIND_DIRECTION)
            .pressureinmmhg(UPDATED_PRESSUREINMMHG)
            .visibilityinmph(UPDATED_VISIBILITYINMPH)
            .cloudCover(UPDATED_CLOUD_COVER)
            .precipitation(UPDATED_PRECIPITATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restWeatherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWeather.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWeather))
            )
            .andExpect(status().isOk());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeUpdate);
        Weather testWeather = weatherList.get(weatherList.size() - 1);
        assertThat(testWeather.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testWeather.getCityID()).isEqualTo(UPDATED_CITY_ID);
        assertThat(testWeather.getStartTimestamp()).isEqualTo(UPDATED_START_TIMESTAMP);
        assertThat(testWeather.getEndTimestamp()).isEqualTo(UPDATED_END_TIMESTAMP);
        assertThat(testWeather.getWeatherStatusID()).isEqualTo(UPDATED_WEATHER_STATUS_ID);
        assertThat(testWeather.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testWeather.getFeelsLikeTemperature()).isEqualTo(UPDATED_FEELS_LIKE_TEMPERATURE);
        assertThat(testWeather.getHumidity()).isEqualTo(UPDATED_HUMIDITY);
        assertThat(testWeather.getWindSpeed()).isEqualTo(UPDATED_WIND_SPEED);
        assertThat(testWeather.getWindDirection()).isEqualTo(UPDATED_WIND_DIRECTION);
        assertThat(testWeather.getPressureinmmhg()).isEqualTo(UPDATED_PRESSUREINMMHG);
        assertThat(testWeather.getVisibilityinmph()).isEqualTo(UPDATED_VISIBILITYINMPH);
        assertThat(testWeather.getCloudCover()).isEqualTo(UPDATED_CLOUD_COVER);
        assertThat(testWeather.getPrecipitation()).isEqualTo(UPDATED_PRECIPITATION);
        assertThat(testWeather.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWeather.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testWeather.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testWeather.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingWeather() throws Exception {
        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();
        weather.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeatherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, weather.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weather))
            )
            .andExpect(status().isBadRequest());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWeather() throws Exception {
        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();
        weather.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeatherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weather))
            )
            .andExpect(status().isBadRequest());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWeather() throws Exception {
        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();
        weather.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeatherMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWeatherWithPatch() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);

        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();

        // Update the weather using partial update
        Weather partialUpdatedWeather = new Weather();
        partialUpdatedWeather.setId(weather.getId());

        partialUpdatedWeather
            .cityID(UPDATED_CITY_ID)
            .startTimestamp(UPDATED_START_TIMESTAMP)
            .endTimestamp(UPDATED_END_TIMESTAMP)
            .weatherStatusID(UPDATED_WEATHER_STATUS_ID)
            .temperature(UPDATED_TEMPERATURE)
            .windSpeed(UPDATED_WIND_SPEED)
            .precipitation(UPDATED_PRECIPITATION);

        restWeatherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeather.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWeather))
            )
            .andExpect(status().isOk());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeUpdate);
        Weather testWeather = weatherList.get(weatherList.size() - 1);
        assertThat(testWeather.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testWeather.getCityID()).isEqualTo(UPDATED_CITY_ID);
        assertThat(testWeather.getStartTimestamp()).isEqualTo(UPDATED_START_TIMESTAMP);
        assertThat(testWeather.getEndTimestamp()).isEqualTo(UPDATED_END_TIMESTAMP);
        assertThat(testWeather.getWeatherStatusID()).isEqualTo(UPDATED_WEATHER_STATUS_ID);
        assertThat(testWeather.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testWeather.getFeelsLikeTemperature()).isEqualTo(DEFAULT_FEELS_LIKE_TEMPERATURE);
        assertThat(testWeather.getHumidity()).isEqualTo(DEFAULT_HUMIDITY);
        assertThat(testWeather.getWindSpeed()).isEqualTo(UPDATED_WIND_SPEED);
        assertThat(testWeather.getWindDirection()).isEqualTo(DEFAULT_WIND_DIRECTION);
        assertThat(testWeather.getPressureinmmhg()).isEqualTo(DEFAULT_PRESSUREINMMHG);
        assertThat(testWeather.getVisibilityinmph()).isEqualTo(DEFAULT_VISIBILITYINMPH);
        assertThat(testWeather.getCloudCover()).isEqualTo(DEFAULT_CLOUD_COVER);
        assertThat(testWeather.getPrecipitation()).isEqualTo(UPDATED_PRECIPITATION);
        assertThat(testWeather.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWeather.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testWeather.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testWeather.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateWeatherWithPatch() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);

        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();

        // Update the weather using partial update
        Weather partialUpdatedWeather = new Weather();
        partialUpdatedWeather.setId(weather.getId());

        partialUpdatedWeather
            .gUID(UPDATED_G_UID)
            .cityID(UPDATED_CITY_ID)
            .startTimestamp(UPDATED_START_TIMESTAMP)
            .endTimestamp(UPDATED_END_TIMESTAMP)
            .weatherStatusID(UPDATED_WEATHER_STATUS_ID)
            .temperature(UPDATED_TEMPERATURE)
            .feelsLikeTemperature(UPDATED_FEELS_LIKE_TEMPERATURE)
            .humidity(UPDATED_HUMIDITY)
            .windSpeed(UPDATED_WIND_SPEED)
            .windDirection(UPDATED_WIND_DIRECTION)
            .pressureinmmhg(UPDATED_PRESSUREINMMHG)
            .visibilityinmph(UPDATED_VISIBILITYINMPH)
            .cloudCover(UPDATED_CLOUD_COVER)
            .precipitation(UPDATED_PRECIPITATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restWeatherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeather.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWeather))
            )
            .andExpect(status().isOk());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeUpdate);
        Weather testWeather = weatherList.get(weatherList.size() - 1);
        assertThat(testWeather.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testWeather.getCityID()).isEqualTo(UPDATED_CITY_ID);
        assertThat(testWeather.getStartTimestamp()).isEqualTo(UPDATED_START_TIMESTAMP);
        assertThat(testWeather.getEndTimestamp()).isEqualTo(UPDATED_END_TIMESTAMP);
        assertThat(testWeather.getWeatherStatusID()).isEqualTo(UPDATED_WEATHER_STATUS_ID);
        assertThat(testWeather.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testWeather.getFeelsLikeTemperature()).isEqualTo(UPDATED_FEELS_LIKE_TEMPERATURE);
        assertThat(testWeather.getHumidity()).isEqualTo(UPDATED_HUMIDITY);
        assertThat(testWeather.getWindSpeed()).isEqualTo(UPDATED_WIND_SPEED);
        assertThat(testWeather.getWindDirection()).isEqualTo(UPDATED_WIND_DIRECTION);
        assertThat(testWeather.getPressureinmmhg()).isEqualTo(UPDATED_PRESSUREINMMHG);
        assertThat(testWeather.getVisibilityinmph()).isEqualTo(UPDATED_VISIBILITYINMPH);
        assertThat(testWeather.getCloudCover()).isEqualTo(UPDATED_CLOUD_COVER);
        assertThat(testWeather.getPrecipitation()).isEqualTo(UPDATED_PRECIPITATION);
        assertThat(testWeather.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWeather.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testWeather.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testWeather.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingWeather() throws Exception {
        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();
        weather.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeatherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, weather.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(weather))
            )
            .andExpect(status().isBadRequest());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWeather() throws Exception {
        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();
        weather.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeatherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(weather))
            )
            .andExpect(status().isBadRequest());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWeather() throws Exception {
        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();
        weather.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeatherMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWeather() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);

        int databaseSizeBeforeDelete = weatherRepository.findAll().size();

        // Delete the weather
        restWeatherMockMvc
            .perform(delete(ENTITY_API_URL_ID, weather.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
