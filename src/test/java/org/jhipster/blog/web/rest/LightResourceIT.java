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
import org.jhipster.blog.domain.Light;
import org.jhipster.blog.domain.enumeration.LightSource;
import org.jhipster.blog.repository.LightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LightResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LightResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final LightSource DEFAULT_SOURCE = LightSource.Automatic;
    private static final LightSource UPDATED_SOURCE = LightSource.Manual;

    private static final Integer DEFAULT_LIGHT_INTENSITY = 1;
    private static final Integer UPDATED_LIGHT_INTENSITY = 2;

    private static final Integer DEFAULT_DAILY_LIGHT_INTEGRAL = 1;
    private static final Integer UPDATED_DAILY_LIGHT_INTEGRAL = 2;

    private static final Integer DEFAULT_P_AR = 1;
    private static final Integer UPDATED_P_AR = 2;

    private static final Integer DEFAULT_P_PFD = 1;
    private static final Integer UPDATED_P_PFD = 2;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/lights";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LightRepository lightRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLightMockMvc;

    private Light light;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Light createEntity(EntityManager em) {
        Light light = new Light()
            .gUID(DEFAULT_G_UID)
            .source(DEFAULT_SOURCE)
            .lightIntensity(DEFAULT_LIGHT_INTENSITY)
            .dailyLightIntegral(DEFAULT_DAILY_LIGHT_INTEGRAL)
            .pAR(DEFAULT_P_AR)
            .pPFD(DEFAULT_P_PFD)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return light;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Light createUpdatedEntity(EntityManager em) {
        Light light = new Light()
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .lightIntensity(UPDATED_LIGHT_INTENSITY)
            .dailyLightIntegral(UPDATED_DAILY_LIGHT_INTEGRAL)
            .pAR(UPDATED_P_AR)
            .pPFD(UPDATED_P_PFD)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return light;
    }

    @BeforeEach
    public void initTest() {
        light = createEntity(em);
    }

    @Test
    @Transactional
    void createLight() throws Exception {
        int databaseSizeBeforeCreate = lightRepository.findAll().size();
        // Create the Light
        restLightMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(light)))
            .andExpect(status().isCreated());

        // Validate the Light in the database
        List<Light> lightList = lightRepository.findAll();
        assertThat(lightList).hasSize(databaseSizeBeforeCreate + 1);
        Light testLight = lightList.get(lightList.size() - 1);
        assertThat(testLight.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testLight.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testLight.getLightIntensity()).isEqualTo(DEFAULT_LIGHT_INTENSITY);
        assertThat(testLight.getDailyLightIntegral()).isEqualTo(DEFAULT_DAILY_LIGHT_INTEGRAL);
        assertThat(testLight.getpAR()).isEqualTo(DEFAULT_P_AR);
        assertThat(testLight.getpPFD()).isEqualTo(DEFAULT_P_PFD);
        assertThat(testLight.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLight.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testLight.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testLight.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createLightWithExistingId() throws Exception {
        // Create the Light with an existing ID
        light.setId(1L);

        int databaseSizeBeforeCreate = lightRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLightMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(light)))
            .andExpect(status().isBadRequest());

        // Validate the Light in the database
        List<Light> lightList = lightRepository.findAll();
        assertThat(lightList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLights() throws Exception {
        // Initialize the database
        lightRepository.saveAndFlush(light);

        // Get all the lightList
        restLightMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(light.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].lightIntensity").value(hasItem(DEFAULT_LIGHT_INTENSITY)))
            .andExpect(jsonPath("$.[*].dailyLightIntegral").value(hasItem(DEFAULT_DAILY_LIGHT_INTEGRAL)))
            .andExpect(jsonPath("$.[*].pAR").value(hasItem(DEFAULT_P_AR)))
            .andExpect(jsonPath("$.[*].pPFD").value(hasItem(DEFAULT_P_PFD)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getLight() throws Exception {
        // Initialize the database
        lightRepository.saveAndFlush(light);

        // Get the light
        restLightMockMvc
            .perform(get(ENTITY_API_URL_ID, light.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(light.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.lightIntensity").value(DEFAULT_LIGHT_INTENSITY))
            .andExpect(jsonPath("$.dailyLightIntegral").value(DEFAULT_DAILY_LIGHT_INTEGRAL))
            .andExpect(jsonPath("$.pAR").value(DEFAULT_P_AR))
            .andExpect(jsonPath("$.pPFD").value(DEFAULT_P_PFD))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingLight() throws Exception {
        // Get the light
        restLightMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLight() throws Exception {
        // Initialize the database
        lightRepository.saveAndFlush(light);

        int databaseSizeBeforeUpdate = lightRepository.findAll().size();

        // Update the light
        Light updatedLight = lightRepository.findById(light.getId()).get();
        // Disconnect from session so that the updates on updatedLight are not directly saved in db
        em.detach(updatedLight);
        updatedLight
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .lightIntensity(UPDATED_LIGHT_INTENSITY)
            .dailyLightIntegral(UPDATED_DAILY_LIGHT_INTEGRAL)
            .pAR(UPDATED_P_AR)
            .pPFD(UPDATED_P_PFD)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restLightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLight.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLight))
            )
            .andExpect(status().isOk());

        // Validate the Light in the database
        List<Light> lightList = lightRepository.findAll();
        assertThat(lightList).hasSize(databaseSizeBeforeUpdate);
        Light testLight = lightList.get(lightList.size() - 1);
        assertThat(testLight.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testLight.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testLight.getLightIntensity()).isEqualTo(UPDATED_LIGHT_INTENSITY);
        assertThat(testLight.getDailyLightIntegral()).isEqualTo(UPDATED_DAILY_LIGHT_INTEGRAL);
        assertThat(testLight.getpAR()).isEqualTo(UPDATED_P_AR);
        assertThat(testLight.getpPFD()).isEqualTo(UPDATED_P_PFD);
        assertThat(testLight.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLight.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLight.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLight.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingLight() throws Exception {
        int databaseSizeBeforeUpdate = lightRepository.findAll().size();
        light.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, light.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(light))
            )
            .andExpect(status().isBadRequest());

        // Validate the Light in the database
        List<Light> lightList = lightRepository.findAll();
        assertThat(lightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLight() throws Exception {
        int databaseSizeBeforeUpdate = lightRepository.findAll().size();
        light.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(light))
            )
            .andExpect(status().isBadRequest());

        // Validate the Light in the database
        List<Light> lightList = lightRepository.findAll();
        assertThat(lightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLight() throws Exception {
        int databaseSizeBeforeUpdate = lightRepository.findAll().size();
        light.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLightMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(light)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Light in the database
        List<Light> lightList = lightRepository.findAll();
        assertThat(lightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLightWithPatch() throws Exception {
        // Initialize the database
        lightRepository.saveAndFlush(light);

        int databaseSizeBeforeUpdate = lightRepository.findAll().size();

        // Update the light using partial update
        Light partialUpdatedLight = new Light();
        partialUpdatedLight.setId(light.getId());

        partialUpdatedLight
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .lightIntensity(UPDATED_LIGHT_INTENSITY)
            .pPFD(UPDATED_P_PFD)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restLightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLight.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLight))
            )
            .andExpect(status().isOk());

        // Validate the Light in the database
        List<Light> lightList = lightRepository.findAll();
        assertThat(lightList).hasSize(databaseSizeBeforeUpdate);
        Light testLight = lightList.get(lightList.size() - 1);
        assertThat(testLight.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testLight.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testLight.getLightIntensity()).isEqualTo(UPDATED_LIGHT_INTENSITY);
        assertThat(testLight.getDailyLightIntegral()).isEqualTo(DEFAULT_DAILY_LIGHT_INTEGRAL);
        assertThat(testLight.getpAR()).isEqualTo(DEFAULT_P_AR);
        assertThat(testLight.getpPFD()).isEqualTo(UPDATED_P_PFD);
        assertThat(testLight.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLight.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLight.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLight.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateLightWithPatch() throws Exception {
        // Initialize the database
        lightRepository.saveAndFlush(light);

        int databaseSizeBeforeUpdate = lightRepository.findAll().size();

        // Update the light using partial update
        Light partialUpdatedLight = new Light();
        partialUpdatedLight.setId(light.getId());

        partialUpdatedLight
            .gUID(UPDATED_G_UID)
            .source(UPDATED_SOURCE)
            .lightIntensity(UPDATED_LIGHT_INTENSITY)
            .dailyLightIntegral(UPDATED_DAILY_LIGHT_INTEGRAL)
            .pAR(UPDATED_P_AR)
            .pPFD(UPDATED_P_PFD)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restLightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLight.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLight))
            )
            .andExpect(status().isOk());

        // Validate the Light in the database
        List<Light> lightList = lightRepository.findAll();
        assertThat(lightList).hasSize(databaseSizeBeforeUpdate);
        Light testLight = lightList.get(lightList.size() - 1);
        assertThat(testLight.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testLight.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testLight.getLightIntensity()).isEqualTo(UPDATED_LIGHT_INTENSITY);
        assertThat(testLight.getDailyLightIntegral()).isEqualTo(UPDATED_DAILY_LIGHT_INTEGRAL);
        assertThat(testLight.getpAR()).isEqualTo(UPDATED_P_AR);
        assertThat(testLight.getpPFD()).isEqualTo(UPDATED_P_PFD);
        assertThat(testLight.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLight.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLight.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLight.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingLight() throws Exception {
        int databaseSizeBeforeUpdate = lightRepository.findAll().size();
        light.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, light.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(light))
            )
            .andExpect(status().isBadRequest());

        // Validate the Light in the database
        List<Light> lightList = lightRepository.findAll();
        assertThat(lightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLight() throws Exception {
        int databaseSizeBeforeUpdate = lightRepository.findAll().size();
        light.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(light))
            )
            .andExpect(status().isBadRequest());

        // Validate the Light in the database
        List<Light> lightList = lightRepository.findAll();
        assertThat(lightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLight() throws Exception {
        int databaseSizeBeforeUpdate = lightRepository.findAll().size();
        light.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLightMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(light)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Light in the database
        List<Light> lightList = lightRepository.findAll();
        assertThat(lightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLight() throws Exception {
        // Initialize the database
        lightRepository.saveAndFlush(light);

        int databaseSizeBeforeDelete = lightRepository.findAll().size();

        // Delete the light
        restLightMockMvc
            .perform(delete(ENTITY_API_URL_ID, light.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Light> lightList = lightRepository.findAll();
        assertThat(lightList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
