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
import org.jhipster.blog.domain.Activity;
import org.jhipster.blog.domain.enumeration.ActType;
import org.jhipster.blog.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ActivityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActivityResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final ActType DEFAULT_ACTIVITY_TYPE = ActType.Sowing;
    private static final ActType UPDATED_ACTIVITY_TYPE = ActType.Pruning;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/activities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityMockMvc;

    private Activity activity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createEntity(EntityManager em) {
        Activity activity = new Activity()
            .gUID(DEFAULT_G_UID)
            .activityType(DEFAULT_ACTIVITY_TYPE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return activity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createUpdatedEntity(EntityManager em) {
        Activity activity = new Activity()
            .gUID(UPDATED_G_UID)
            .activityType(UPDATED_ACTIVITY_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return activity;
    }

    @BeforeEach
    public void initTest() {
        activity = createEntity(em);
    }

    @Test
    @Transactional
    void createActivity() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();
        // Create the Activity
        restActivityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate + 1);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testActivity.getActivityType()).isEqualTo(DEFAULT_ACTIVITY_TYPE);
        assertThat(testActivity.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testActivity.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testActivity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testActivity.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testActivity.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testActivity.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testActivity.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createActivityWithExistingId() throws Exception {
        // Create the Activity with an existing ID
        activity.setId(1L);

        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActivities() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList
        restActivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].activityType").value(hasItem(DEFAULT_ACTIVITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get the activity
        restActivityMockMvc
            .perform(get(ENTITY_API_URL_ID, activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activity.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.activityType").value(DEFAULT_ACTIVITY_TYPE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity
        Activity updatedActivity = activityRepository.findById(activity.getId()).get();
        // Disconnect from session so that the updates on updatedActivity are not directly saved in db
        em.detach(updatedActivity);
        updatedActivity
            .gUID(UPDATED_G_UID)
            .activityType(UPDATED_ACTIVITY_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActivity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testActivity.getActivityType()).isEqualTo(UPDATED_ACTIVITY_TYPE);
        assertThat(testActivity.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testActivity.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testActivity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testActivity.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testActivity.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testActivity.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testActivity.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivityWithPatch() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity using partial update
        Activity partialUpdatedActivity = new Activity();
        partialUpdatedActivity.setId(activity.getId());

        partialUpdatedActivity
            .gUID(UPDATED_G_UID)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON);

        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testActivity.getActivityType()).isEqualTo(DEFAULT_ACTIVITY_TYPE);
        assertThat(testActivity.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testActivity.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testActivity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testActivity.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testActivity.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testActivity.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testActivity.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateActivityWithPatch() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity using partial update
        Activity partialUpdatedActivity = new Activity();
        partialUpdatedActivity.setId(activity.getId());

        partialUpdatedActivity
            .gUID(UPDATED_G_UID)
            .activityType(UPDATED_ACTIVITY_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testActivity.getActivityType()).isEqualTo(UPDATED_ACTIVITY_TYPE);
        assertThat(testActivity.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testActivity.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testActivity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testActivity.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testActivity.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testActivity.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testActivity.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Delete the activity
        restActivityMockMvc
            .perform(delete(ENTITY_API_URL_ID, activity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
