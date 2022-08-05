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
import org.jhipster.blog.domain.Alert;
import org.jhipster.blog.domain.enumeration.AlertStatus;
import org.jhipster.blog.domain.enumeration.AlertType;
import org.jhipster.blog.domain.enumeration.PreType;
import org.jhipster.blog.domain.enumeration.Remediation;
import org.jhipster.blog.repository.AlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AlertResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlertResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final AlertType DEFAULT_ALERT_TYPE = AlertType.Disease;
    private static final AlertType UPDATED_ALERT_TYPE = AlertType.Alert;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DATE_PERIOD = 1;
    private static final Integer UPDATED_DATE_PERIOD = 2;

    private static final Integer DEFAULT_DURATION_DAYS = 1;
    private static final Integer UPDATED_DURATION_DAYS = 2;

    private static final Integer DEFAULT_MINIMUM_TEMPERATURE = 1;
    private static final Integer UPDATED_MINIMUM_TEMPERATURE = 2;

    private static final Integer DEFAULT_MAXIMUM_TEMPERATURE = 1;
    private static final Integer UPDATED_MAXIMUM_TEMPERATURE = 2;

    private static final Integer DEFAULT_MIN_HUMIDITY = 1;
    private static final Integer UPDATED_MIN_HUMIDITY = 2;

    private static final Integer DEFAULT_MAX_HUMIDITY = 1;
    private static final Integer UPDATED_MAX_HUMIDITY = 2;

    private static final PreType DEFAULT_PRECIPITATION_TYPE = PreType.Rain;
    private static final PreType UPDATED_PRECIPITATION_TYPE = PreType.Snow;

    private static final Integer DEFAULT_MIN_PRECIPITATION = 1;
    private static final Integer UPDATED_MIN_PRECIPITATION = 2;

    private static final Integer DEFAULT_MAX_PRECIPITATION = 1;
    private static final Integer UPDATED_MAX_PRECIPITATION = 2;

    private static final AlertStatus DEFAULT_STATUS = AlertStatus.Active;
    private static final AlertStatus UPDATED_STATUS = AlertStatus.Inactive;

    private static final Remediation DEFAULT_REMEDIATION = Remediation.Manual;
    private static final Remediation UPDATED_REMEDIATION = Remediation.Automatic;

    private static final String DEFAULT_FARM_ASSIGNED = "AAAAAAAAAA";
    private static final String UPDATED_FARM_ASSIGNED = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/alerts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertMockMvc;

    private Alert alert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alert createEntity(EntityManager em) {
        Alert alert = new Alert()
            .gUID(DEFAULT_G_UID)
            .name(DEFAULT_NAME)
            .alertType(DEFAULT_ALERT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .datePeriod(DEFAULT_DATE_PERIOD)
            .durationDays(DEFAULT_DURATION_DAYS)
            .minimumTemperature(DEFAULT_MINIMUM_TEMPERATURE)
            .maximumTemperature(DEFAULT_MAXIMUM_TEMPERATURE)
            .minHumidity(DEFAULT_MIN_HUMIDITY)
            .maxHumidity(DEFAULT_MAX_HUMIDITY)
            .precipitationType(DEFAULT_PRECIPITATION_TYPE)
            .minPrecipitation(DEFAULT_MIN_PRECIPITATION)
            .maxPrecipitation(DEFAULT_MAX_PRECIPITATION)
            .status(DEFAULT_STATUS)
            .remediation(DEFAULT_REMEDIATION)
            .farmAssigned(DEFAULT_FARM_ASSIGNED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return alert;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alert createUpdatedEntity(EntityManager em) {
        Alert alert = new Alert()
            .gUID(UPDATED_G_UID)
            .name(UPDATED_NAME)
            .alertType(UPDATED_ALERT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .datePeriod(UPDATED_DATE_PERIOD)
            .durationDays(UPDATED_DURATION_DAYS)
            .minimumTemperature(UPDATED_MINIMUM_TEMPERATURE)
            .maximumTemperature(UPDATED_MAXIMUM_TEMPERATURE)
            .minHumidity(UPDATED_MIN_HUMIDITY)
            .maxHumidity(UPDATED_MAX_HUMIDITY)
            .precipitationType(UPDATED_PRECIPITATION_TYPE)
            .minPrecipitation(UPDATED_MIN_PRECIPITATION)
            .maxPrecipitation(UPDATED_MAX_PRECIPITATION)
            .status(UPDATED_STATUS)
            .remediation(UPDATED_REMEDIATION)
            .farmAssigned(UPDATED_FARM_ASSIGNED)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return alert;
    }

    @BeforeEach
    public void initTest() {
        alert = createEntity(em);
    }

    @Test
    @Transactional
    void createAlert() throws Exception {
        int databaseSizeBeforeCreate = alertRepository.findAll().size();
        // Create the Alert
        restAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alert)))
            .andExpect(status().isCreated());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeCreate + 1);
        Alert testAlert = alertList.get(alertList.size() - 1);
        assertThat(testAlert.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testAlert.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlert.getAlertType()).isEqualTo(DEFAULT_ALERT_TYPE);
        assertThat(testAlert.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAlert.getDatePeriod()).isEqualTo(DEFAULT_DATE_PERIOD);
        assertThat(testAlert.getDurationDays()).isEqualTo(DEFAULT_DURATION_DAYS);
        assertThat(testAlert.getMinimumTemperature()).isEqualTo(DEFAULT_MINIMUM_TEMPERATURE);
        assertThat(testAlert.getMaximumTemperature()).isEqualTo(DEFAULT_MAXIMUM_TEMPERATURE);
        assertThat(testAlert.getMinHumidity()).isEqualTo(DEFAULT_MIN_HUMIDITY);
        assertThat(testAlert.getMaxHumidity()).isEqualTo(DEFAULT_MAX_HUMIDITY);
        assertThat(testAlert.getPrecipitationType()).isEqualTo(DEFAULT_PRECIPITATION_TYPE);
        assertThat(testAlert.getMinPrecipitation()).isEqualTo(DEFAULT_MIN_PRECIPITATION);
        assertThat(testAlert.getMaxPrecipitation()).isEqualTo(DEFAULT_MAX_PRECIPITATION);
        assertThat(testAlert.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAlert.getRemediation()).isEqualTo(DEFAULT_REMEDIATION);
        assertThat(testAlert.getFarmAssigned()).isEqualTo(DEFAULT_FARM_ASSIGNED);
        assertThat(testAlert.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAlert.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAlert.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAlert.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createAlertWithExistingId() throws Exception {
        // Create the Alert with an existing ID
        alert.setId(1L);

        int databaseSizeBeforeCreate = alertRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alert)))
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAlerts() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        // Get all the alertList
        restAlertMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alert.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].alertType").value(hasItem(DEFAULT_ALERT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].datePeriod").value(hasItem(DEFAULT_DATE_PERIOD)))
            .andExpect(jsonPath("$.[*].durationDays").value(hasItem(DEFAULT_DURATION_DAYS)))
            .andExpect(jsonPath("$.[*].minimumTemperature").value(hasItem(DEFAULT_MINIMUM_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].maximumTemperature").value(hasItem(DEFAULT_MAXIMUM_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].minHumidity").value(hasItem(DEFAULT_MIN_HUMIDITY)))
            .andExpect(jsonPath("$.[*].maxHumidity").value(hasItem(DEFAULT_MAX_HUMIDITY)))
            .andExpect(jsonPath("$.[*].precipitationType").value(hasItem(DEFAULT_PRECIPITATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].minPrecipitation").value(hasItem(DEFAULT_MIN_PRECIPITATION)))
            .andExpect(jsonPath("$.[*].maxPrecipitation").value(hasItem(DEFAULT_MAX_PRECIPITATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remediation").value(hasItem(DEFAULT_REMEDIATION.toString())))
            .andExpect(jsonPath("$.[*].farmAssigned").value(hasItem(DEFAULT_FARM_ASSIGNED)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getAlert() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        // Get the alert
        restAlertMockMvc
            .perform(get(ENTITY_API_URL_ID, alert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alert.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.alertType").value(DEFAULT_ALERT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.datePeriod").value(DEFAULT_DATE_PERIOD))
            .andExpect(jsonPath("$.durationDays").value(DEFAULT_DURATION_DAYS))
            .andExpect(jsonPath("$.minimumTemperature").value(DEFAULT_MINIMUM_TEMPERATURE))
            .andExpect(jsonPath("$.maximumTemperature").value(DEFAULT_MAXIMUM_TEMPERATURE))
            .andExpect(jsonPath("$.minHumidity").value(DEFAULT_MIN_HUMIDITY))
            .andExpect(jsonPath("$.maxHumidity").value(DEFAULT_MAX_HUMIDITY))
            .andExpect(jsonPath("$.precipitationType").value(DEFAULT_PRECIPITATION_TYPE.toString()))
            .andExpect(jsonPath("$.minPrecipitation").value(DEFAULT_MIN_PRECIPITATION))
            .andExpect(jsonPath("$.maxPrecipitation").value(DEFAULT_MAX_PRECIPITATION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.remediation").value(DEFAULT_REMEDIATION.toString()))
            .andExpect(jsonPath("$.farmAssigned").value(DEFAULT_FARM_ASSIGNED))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingAlert() throws Exception {
        // Get the alert
        restAlertMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAlert() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        int databaseSizeBeforeUpdate = alertRepository.findAll().size();

        // Update the alert
        Alert updatedAlert = alertRepository.findById(alert.getId()).get();
        // Disconnect from session so that the updates on updatedAlert are not directly saved in db
        em.detach(updatedAlert);
        updatedAlert
            .gUID(UPDATED_G_UID)
            .name(UPDATED_NAME)
            .alertType(UPDATED_ALERT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .datePeriod(UPDATED_DATE_PERIOD)
            .durationDays(UPDATED_DURATION_DAYS)
            .minimumTemperature(UPDATED_MINIMUM_TEMPERATURE)
            .maximumTemperature(UPDATED_MAXIMUM_TEMPERATURE)
            .minHumidity(UPDATED_MIN_HUMIDITY)
            .maxHumidity(UPDATED_MAX_HUMIDITY)
            .precipitationType(UPDATED_PRECIPITATION_TYPE)
            .minPrecipitation(UPDATED_MIN_PRECIPITATION)
            .maxPrecipitation(UPDATED_MAX_PRECIPITATION)
            .status(UPDATED_STATUS)
            .remediation(UPDATED_REMEDIATION)
            .farmAssigned(UPDATED_FARM_ASSIGNED)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restAlertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAlert.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAlert))
            )
            .andExpect(status().isOk());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
        Alert testAlert = alertList.get(alertList.size() - 1);
        assertThat(testAlert.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testAlert.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlert.getAlertType()).isEqualTo(UPDATED_ALERT_TYPE);
        assertThat(testAlert.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlert.getDatePeriod()).isEqualTo(UPDATED_DATE_PERIOD);
        assertThat(testAlert.getDurationDays()).isEqualTo(UPDATED_DURATION_DAYS);
        assertThat(testAlert.getMinimumTemperature()).isEqualTo(UPDATED_MINIMUM_TEMPERATURE);
        assertThat(testAlert.getMaximumTemperature()).isEqualTo(UPDATED_MAXIMUM_TEMPERATURE);
        assertThat(testAlert.getMinHumidity()).isEqualTo(UPDATED_MIN_HUMIDITY);
        assertThat(testAlert.getMaxHumidity()).isEqualTo(UPDATED_MAX_HUMIDITY);
        assertThat(testAlert.getPrecipitationType()).isEqualTo(UPDATED_PRECIPITATION_TYPE);
        assertThat(testAlert.getMinPrecipitation()).isEqualTo(UPDATED_MIN_PRECIPITATION);
        assertThat(testAlert.getMaxPrecipitation()).isEqualTo(UPDATED_MAX_PRECIPITATION);
        assertThat(testAlert.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAlert.getRemediation()).isEqualTo(UPDATED_REMEDIATION);
        assertThat(testAlert.getFarmAssigned()).isEqualTo(UPDATED_FARM_ASSIGNED);
        assertThat(testAlert.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAlert.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAlert.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAlert.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();
        alert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alert.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();
        alert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();
        alert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alert)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlertWithPatch() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        int databaseSizeBeforeUpdate = alertRepository.findAll().size();

        // Update the alert using partial update
        Alert partialUpdatedAlert = new Alert();
        partialUpdatedAlert.setId(alert.getId());

        partialUpdatedAlert
            .gUID(UPDATED_G_UID)
            .name(UPDATED_NAME)
            .datePeriod(UPDATED_DATE_PERIOD)
            .durationDays(UPDATED_DURATION_DAYS)
            .minimumTemperature(UPDATED_MINIMUM_TEMPERATURE)
            .maximumTemperature(UPDATED_MAXIMUM_TEMPERATURE)
            .minHumidity(UPDATED_MIN_HUMIDITY)
            .precipitationType(UPDATED_PRECIPITATION_TYPE)
            .maxPrecipitation(UPDATED_MAX_PRECIPITATION)
            .status(UPDATED_STATUS)
            .remediation(UPDATED_REMEDIATION)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlert))
            )
            .andExpect(status().isOk());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
        Alert testAlert = alertList.get(alertList.size() - 1);
        assertThat(testAlert.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testAlert.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlert.getAlertType()).isEqualTo(DEFAULT_ALERT_TYPE);
        assertThat(testAlert.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAlert.getDatePeriod()).isEqualTo(UPDATED_DATE_PERIOD);
        assertThat(testAlert.getDurationDays()).isEqualTo(UPDATED_DURATION_DAYS);
        assertThat(testAlert.getMinimumTemperature()).isEqualTo(UPDATED_MINIMUM_TEMPERATURE);
        assertThat(testAlert.getMaximumTemperature()).isEqualTo(UPDATED_MAXIMUM_TEMPERATURE);
        assertThat(testAlert.getMinHumidity()).isEqualTo(UPDATED_MIN_HUMIDITY);
        assertThat(testAlert.getMaxHumidity()).isEqualTo(DEFAULT_MAX_HUMIDITY);
        assertThat(testAlert.getPrecipitationType()).isEqualTo(UPDATED_PRECIPITATION_TYPE);
        assertThat(testAlert.getMinPrecipitation()).isEqualTo(DEFAULT_MIN_PRECIPITATION);
        assertThat(testAlert.getMaxPrecipitation()).isEqualTo(UPDATED_MAX_PRECIPITATION);
        assertThat(testAlert.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAlert.getRemediation()).isEqualTo(UPDATED_REMEDIATION);
        assertThat(testAlert.getFarmAssigned()).isEqualTo(DEFAULT_FARM_ASSIGNED);
        assertThat(testAlert.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAlert.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAlert.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAlert.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateAlertWithPatch() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        int databaseSizeBeforeUpdate = alertRepository.findAll().size();

        // Update the alert using partial update
        Alert partialUpdatedAlert = new Alert();
        partialUpdatedAlert.setId(alert.getId());

        partialUpdatedAlert
            .gUID(UPDATED_G_UID)
            .name(UPDATED_NAME)
            .alertType(UPDATED_ALERT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .datePeriod(UPDATED_DATE_PERIOD)
            .durationDays(UPDATED_DURATION_DAYS)
            .minimumTemperature(UPDATED_MINIMUM_TEMPERATURE)
            .maximumTemperature(UPDATED_MAXIMUM_TEMPERATURE)
            .minHumidity(UPDATED_MIN_HUMIDITY)
            .maxHumidity(UPDATED_MAX_HUMIDITY)
            .precipitationType(UPDATED_PRECIPITATION_TYPE)
            .minPrecipitation(UPDATED_MIN_PRECIPITATION)
            .maxPrecipitation(UPDATED_MAX_PRECIPITATION)
            .status(UPDATED_STATUS)
            .remediation(UPDATED_REMEDIATION)
            .farmAssigned(UPDATED_FARM_ASSIGNED)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlert))
            )
            .andExpect(status().isOk());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
        Alert testAlert = alertList.get(alertList.size() - 1);
        assertThat(testAlert.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testAlert.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlert.getAlertType()).isEqualTo(UPDATED_ALERT_TYPE);
        assertThat(testAlert.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlert.getDatePeriod()).isEqualTo(UPDATED_DATE_PERIOD);
        assertThat(testAlert.getDurationDays()).isEqualTo(UPDATED_DURATION_DAYS);
        assertThat(testAlert.getMinimumTemperature()).isEqualTo(UPDATED_MINIMUM_TEMPERATURE);
        assertThat(testAlert.getMaximumTemperature()).isEqualTo(UPDATED_MAXIMUM_TEMPERATURE);
        assertThat(testAlert.getMinHumidity()).isEqualTo(UPDATED_MIN_HUMIDITY);
        assertThat(testAlert.getMaxHumidity()).isEqualTo(UPDATED_MAX_HUMIDITY);
        assertThat(testAlert.getPrecipitationType()).isEqualTo(UPDATED_PRECIPITATION_TYPE);
        assertThat(testAlert.getMinPrecipitation()).isEqualTo(UPDATED_MIN_PRECIPITATION);
        assertThat(testAlert.getMaxPrecipitation()).isEqualTo(UPDATED_MAX_PRECIPITATION);
        assertThat(testAlert.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAlert.getRemediation()).isEqualTo(UPDATED_REMEDIATION);
        assertThat(testAlert.getFarmAssigned()).isEqualTo(UPDATED_FARM_ASSIGNED);
        assertThat(testAlert.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAlert.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAlert.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAlert.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();
        alert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();
        alert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();
        alert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(alert)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlert() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        int databaseSizeBeforeDelete = alertRepository.findAll().size();

        // Delete the alert
        restAlertMockMvc
            .perform(delete(ENTITY_API_URL_ID, alert.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
