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
import org.jhipster.blog.domain.ApplicationUserSecurityGroup;
import org.jhipster.blog.repository.ApplicationUserSecurityGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ApplicationUserSecurityGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApplicationUserSecurityGroupResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_THRU_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THRU_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/application-user-security-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicationUserSecurityGroupRepository applicationUserSecurityGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationUserSecurityGroupMockMvc;

    private ApplicationUserSecurityGroup applicationUserSecurityGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationUserSecurityGroup createEntity(EntityManager em) {
        ApplicationUserSecurityGroup applicationUserSecurityGroup = new ApplicationUserSecurityGroup()
            .gUID(DEFAULT_G_UID)
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return applicationUserSecurityGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationUserSecurityGroup createUpdatedEntity(EntityManager em) {
        ApplicationUserSecurityGroup applicationUserSecurityGroup = new ApplicationUserSecurityGroup()
            .gUID(UPDATED_G_UID)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return applicationUserSecurityGroup;
    }

    @BeforeEach
    public void initTest() {
        applicationUserSecurityGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicationUserSecurityGroup() throws Exception {
        int databaseSizeBeforeCreate = applicationUserSecurityGroupRepository.findAll().size();
        // Create the ApplicationUserSecurityGroup
        restApplicationUserSecurityGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserSecurityGroup))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicationUserSecurityGroup in the database
        List<ApplicationUserSecurityGroup> applicationUserSecurityGroupList = applicationUserSecurityGroupRepository.findAll();
        assertThat(applicationUserSecurityGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationUserSecurityGroup testApplicationUserSecurityGroup = applicationUserSecurityGroupList.get(
            applicationUserSecurityGroupList.size() - 1
        );
        assertThat(testApplicationUserSecurityGroup.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testApplicationUserSecurityGroup.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testApplicationUserSecurityGroup.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testApplicationUserSecurityGroup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicationUserSecurityGroup.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testApplicationUserSecurityGroup.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testApplicationUserSecurityGroup.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createApplicationUserSecurityGroupWithExistingId() throws Exception {
        // Create the ApplicationUserSecurityGroup with an existing ID
        applicationUserSecurityGroup.setId(1L);

        int databaseSizeBeforeCreate = applicationUserSecurityGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationUserSecurityGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserSecurityGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserSecurityGroup in the database
        List<ApplicationUserSecurityGroup> applicationUserSecurityGroupList = applicationUserSecurityGroupRepository.findAll();
        assertThat(applicationUserSecurityGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicationUserSecurityGroups() throws Exception {
        // Initialize the database
        applicationUserSecurityGroupRepository.saveAndFlush(applicationUserSecurityGroup);

        // Get all the applicationUserSecurityGroupList
        restApplicationUserSecurityGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationUserSecurityGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getApplicationUserSecurityGroup() throws Exception {
        // Initialize the database
        applicationUserSecurityGroupRepository.saveAndFlush(applicationUserSecurityGroup);

        // Get the applicationUserSecurityGroup
        restApplicationUserSecurityGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, applicationUserSecurityGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationUserSecurityGroup.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingApplicationUserSecurityGroup() throws Exception {
        // Get the applicationUserSecurityGroup
        restApplicationUserSecurityGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApplicationUserSecurityGroup() throws Exception {
        // Initialize the database
        applicationUserSecurityGroupRepository.saveAndFlush(applicationUserSecurityGroup);

        int databaseSizeBeforeUpdate = applicationUserSecurityGroupRepository.findAll().size();

        // Update the applicationUserSecurityGroup
        ApplicationUserSecurityGroup updatedApplicationUserSecurityGroup = applicationUserSecurityGroupRepository
            .findById(applicationUserSecurityGroup.getId())
            .get();
        // Disconnect from session so that the updates on updatedApplicationUserSecurityGroup are not directly saved in db
        em.detach(updatedApplicationUserSecurityGroup);
        updatedApplicationUserSecurityGroup
            .gUID(UPDATED_G_UID)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restApplicationUserSecurityGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApplicationUserSecurityGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApplicationUserSecurityGroup))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUserSecurityGroup in the database
        List<ApplicationUserSecurityGroup> applicationUserSecurityGroupList = applicationUserSecurityGroupRepository.findAll();
        assertThat(applicationUserSecurityGroupList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUserSecurityGroup testApplicationUserSecurityGroup = applicationUserSecurityGroupList.get(
            applicationUserSecurityGroupList.size() - 1
        );
        assertThat(testApplicationUserSecurityGroup.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testApplicationUserSecurityGroup.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testApplicationUserSecurityGroup.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testApplicationUserSecurityGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationUserSecurityGroup.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testApplicationUserSecurityGroup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testApplicationUserSecurityGroup.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingApplicationUserSecurityGroup() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserSecurityGroupRepository.findAll().size();
        applicationUserSecurityGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationUserSecurityGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationUserSecurityGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserSecurityGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserSecurityGroup in the database
        List<ApplicationUserSecurityGroup> applicationUserSecurityGroupList = applicationUserSecurityGroupRepository.findAll();
        assertThat(applicationUserSecurityGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicationUserSecurityGroup() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserSecurityGroupRepository.findAll().size();
        applicationUserSecurityGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserSecurityGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserSecurityGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserSecurityGroup in the database
        List<ApplicationUserSecurityGroup> applicationUserSecurityGroupList = applicationUserSecurityGroupRepository.findAll();
        assertThat(applicationUserSecurityGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicationUserSecurityGroup() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserSecurityGroupRepository.findAll().size();
        applicationUserSecurityGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserSecurityGroupMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserSecurityGroup))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationUserSecurityGroup in the database
        List<ApplicationUserSecurityGroup> applicationUserSecurityGroupList = applicationUserSecurityGroupRepository.findAll();
        assertThat(applicationUserSecurityGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationUserSecurityGroupWithPatch() throws Exception {
        // Initialize the database
        applicationUserSecurityGroupRepository.saveAndFlush(applicationUserSecurityGroup);

        int databaseSizeBeforeUpdate = applicationUserSecurityGroupRepository.findAll().size();

        // Update the applicationUserSecurityGroup using partial update
        ApplicationUserSecurityGroup partialUpdatedApplicationUserSecurityGroup = new ApplicationUserSecurityGroup();
        partialUpdatedApplicationUserSecurityGroup.setId(applicationUserSecurityGroup.getId());

        partialUpdatedApplicationUserSecurityGroup
            .gUID(UPDATED_G_UID)
            .thruDate(UPDATED_THRU_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restApplicationUserSecurityGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationUserSecurityGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationUserSecurityGroup))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUserSecurityGroup in the database
        List<ApplicationUserSecurityGroup> applicationUserSecurityGroupList = applicationUserSecurityGroupRepository.findAll();
        assertThat(applicationUserSecurityGroupList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUserSecurityGroup testApplicationUserSecurityGroup = applicationUserSecurityGroupList.get(
            applicationUserSecurityGroupList.size() - 1
        );
        assertThat(testApplicationUserSecurityGroup.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testApplicationUserSecurityGroup.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testApplicationUserSecurityGroup.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testApplicationUserSecurityGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationUserSecurityGroup.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testApplicationUserSecurityGroup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testApplicationUserSecurityGroup.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateApplicationUserSecurityGroupWithPatch() throws Exception {
        // Initialize the database
        applicationUserSecurityGroupRepository.saveAndFlush(applicationUserSecurityGroup);

        int databaseSizeBeforeUpdate = applicationUserSecurityGroupRepository.findAll().size();

        // Update the applicationUserSecurityGroup using partial update
        ApplicationUserSecurityGroup partialUpdatedApplicationUserSecurityGroup = new ApplicationUserSecurityGroup();
        partialUpdatedApplicationUserSecurityGroup.setId(applicationUserSecurityGroup.getId());

        partialUpdatedApplicationUserSecurityGroup
            .gUID(UPDATED_G_UID)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restApplicationUserSecurityGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationUserSecurityGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationUserSecurityGroup))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUserSecurityGroup in the database
        List<ApplicationUserSecurityGroup> applicationUserSecurityGroupList = applicationUserSecurityGroupRepository.findAll();
        assertThat(applicationUserSecurityGroupList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUserSecurityGroup testApplicationUserSecurityGroup = applicationUserSecurityGroupList.get(
            applicationUserSecurityGroupList.size() - 1
        );
        assertThat(testApplicationUserSecurityGroup.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testApplicationUserSecurityGroup.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testApplicationUserSecurityGroup.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testApplicationUserSecurityGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationUserSecurityGroup.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testApplicationUserSecurityGroup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testApplicationUserSecurityGroup.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingApplicationUserSecurityGroup() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserSecurityGroupRepository.findAll().size();
        applicationUserSecurityGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationUserSecurityGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicationUserSecurityGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserSecurityGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserSecurityGroup in the database
        List<ApplicationUserSecurityGroup> applicationUserSecurityGroupList = applicationUserSecurityGroupRepository.findAll();
        assertThat(applicationUserSecurityGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicationUserSecurityGroup() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserSecurityGroupRepository.findAll().size();
        applicationUserSecurityGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserSecurityGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserSecurityGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserSecurityGroup in the database
        List<ApplicationUserSecurityGroup> applicationUserSecurityGroupList = applicationUserSecurityGroupRepository.findAll();
        assertThat(applicationUserSecurityGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicationUserSecurityGroup() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserSecurityGroupRepository.findAll().size();
        applicationUserSecurityGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserSecurityGroupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserSecurityGroup))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationUserSecurityGroup in the database
        List<ApplicationUserSecurityGroup> applicationUserSecurityGroupList = applicationUserSecurityGroupRepository.findAll();
        assertThat(applicationUserSecurityGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicationUserSecurityGroup() throws Exception {
        // Initialize the database
        applicationUserSecurityGroupRepository.saveAndFlush(applicationUserSecurityGroup);

        int databaseSizeBeforeDelete = applicationUserSecurityGroupRepository.findAll().size();

        // Delete the applicationUserSecurityGroup
        restApplicationUserSecurityGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicationUserSecurityGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationUserSecurityGroup> applicationUserSecurityGroupList = applicationUserSecurityGroupRepository.findAll();
        assertThat(applicationUserSecurityGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
