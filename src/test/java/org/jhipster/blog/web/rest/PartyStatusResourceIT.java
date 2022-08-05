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
import org.jhipster.blog.domain.PartyStatus;
import org.jhipster.blog.repository.PartyStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartyStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartyStatusResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Instant DEFAULT_STATUS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STATUS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/party-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartyStatusRepository partyStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyStatusMockMvc;

    private PartyStatus partyStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyStatus createEntity(EntityManager em) {
        PartyStatus partyStatus = new PartyStatus()
            .gUID(DEFAULT_G_UID)
            .statusDate(DEFAULT_STATUS_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return partyStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyStatus createUpdatedEntity(EntityManager em) {
        PartyStatus partyStatus = new PartyStatus()
            .gUID(UPDATED_G_UID)
            .statusDate(UPDATED_STATUS_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return partyStatus;
    }

    @BeforeEach
    public void initTest() {
        partyStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createPartyStatus() throws Exception {
        int databaseSizeBeforeCreate = partyStatusRepository.findAll().size();
        // Create the PartyStatus
        restPartyStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyStatus)))
            .andExpect(status().isCreated());

        // Validate the PartyStatus in the database
        List<PartyStatus> partyStatusList = partyStatusRepository.findAll();
        assertThat(partyStatusList).hasSize(databaseSizeBeforeCreate + 1);
        PartyStatus testPartyStatus = partyStatusList.get(partyStatusList.size() - 1);
        assertThat(testPartyStatus.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyStatus.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testPartyStatus.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyStatus.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyStatus.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyStatus.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPartyStatusWithExistingId() throws Exception {
        // Create the PartyStatus with an existing ID
        partyStatus.setId(1L);

        int databaseSizeBeforeCreate = partyStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyStatus)))
            .andExpect(status().isBadRequest());

        // Validate the PartyStatus in the database
        List<PartyStatus> partyStatusList = partyStatusRepository.findAll();
        assertThat(partyStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartyStatuses() throws Exception {
        // Initialize the database
        partyStatusRepository.saveAndFlush(partyStatus);

        // Get all the partyStatusList
        restPartyStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPartyStatus() throws Exception {
        // Initialize the database
        partyStatusRepository.saveAndFlush(partyStatus);

        // Get the partyStatus
        restPartyStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, partyStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyStatus.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.statusDate").value(DEFAULT_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPartyStatus() throws Exception {
        // Get the partyStatus
        restPartyStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartyStatus() throws Exception {
        // Initialize the database
        partyStatusRepository.saveAndFlush(partyStatus);

        int databaseSizeBeforeUpdate = partyStatusRepository.findAll().size();

        // Update the partyStatus
        PartyStatus updatedPartyStatus = partyStatusRepository.findById(partyStatus.getId()).get();
        // Disconnect from session so that the updates on updatedPartyStatus are not directly saved in db
        em.detach(updatedPartyStatus);
        updatedPartyStatus
            .gUID(UPDATED_G_UID)
            .statusDate(UPDATED_STATUS_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartyStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartyStatus))
            )
            .andExpect(status().isOk());

        // Validate the PartyStatus in the database
        List<PartyStatus> partyStatusList = partyStatusRepository.findAll();
        assertThat(partyStatusList).hasSize(databaseSizeBeforeUpdate);
        PartyStatus testPartyStatus = partyStatusList.get(partyStatusList.size() - 1);
        assertThat(testPartyStatus.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyStatus.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testPartyStatus.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyStatus.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyStatus.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyStatus.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPartyStatus() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusRepository.findAll().size();
        partyStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partyStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatus in the database
        List<PartyStatus> partyStatusList = partyStatusRepository.findAll();
        assertThat(partyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartyStatus() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusRepository.findAll().size();
        partyStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatus in the database
        List<PartyStatus> partyStatusList = partyStatusRepository.findAll();
        assertThat(partyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartyStatus() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusRepository.findAll().size();
        partyStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyStatus in the database
        List<PartyStatus> partyStatusList = partyStatusRepository.findAll();
        assertThat(partyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartyStatusWithPatch() throws Exception {
        // Initialize the database
        partyStatusRepository.saveAndFlush(partyStatus);

        int databaseSizeBeforeUpdate = partyStatusRepository.findAll().size();

        // Update the partyStatus using partial update
        PartyStatus partialUpdatedPartyStatus = new PartyStatus();
        partialUpdatedPartyStatus.setId(partyStatus.getId());

        partialUpdatedPartyStatus.gUID(UPDATED_G_UID).createdOn(UPDATED_CREATED_ON).updatedOn(UPDATED_UPDATED_ON);

        restPartyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyStatus))
            )
            .andExpect(status().isOk());

        // Validate the PartyStatus in the database
        List<PartyStatus> partyStatusList = partyStatusRepository.findAll();
        assertThat(partyStatusList).hasSize(databaseSizeBeforeUpdate);
        PartyStatus testPartyStatus = partyStatusList.get(partyStatusList.size() - 1);
        assertThat(testPartyStatus.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyStatus.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testPartyStatus.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyStatus.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyStatus.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyStatus.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePartyStatusWithPatch() throws Exception {
        // Initialize the database
        partyStatusRepository.saveAndFlush(partyStatus);

        int databaseSizeBeforeUpdate = partyStatusRepository.findAll().size();

        // Update the partyStatus using partial update
        PartyStatus partialUpdatedPartyStatus = new PartyStatus();
        partialUpdatedPartyStatus.setId(partyStatus.getId());

        partialUpdatedPartyStatus
            .gUID(UPDATED_G_UID)
            .statusDate(UPDATED_STATUS_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyStatus))
            )
            .andExpect(status().isOk());

        // Validate the PartyStatus in the database
        List<PartyStatus> partyStatusList = partyStatusRepository.findAll();
        assertThat(partyStatusList).hasSize(databaseSizeBeforeUpdate);
        PartyStatus testPartyStatus = partyStatusList.get(partyStatusList.size() - 1);
        assertThat(testPartyStatus.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyStatus.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testPartyStatus.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyStatus.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyStatus.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyStatus.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPartyStatus() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusRepository.findAll().size();
        partyStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partyStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatus in the database
        List<PartyStatus> partyStatusList = partyStatusRepository.findAll();
        assertThat(partyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartyStatus() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusRepository.findAll().size();
        partyStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatus in the database
        List<PartyStatus> partyStatusList = partyStatusRepository.findAll();
        assertThat(partyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartyStatus() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusRepository.findAll().size();
        partyStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partyStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyStatus in the database
        List<PartyStatus> partyStatusList = partyStatusRepository.findAll();
        assertThat(partyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartyStatus() throws Exception {
        // Initialize the database
        partyStatusRepository.saveAndFlush(partyStatus);

        int databaseSizeBeforeDelete = partyStatusRepository.findAll().size();

        // Delete the partyStatus
        restPartyStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, partyStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyStatus> partyStatusList = partyStatusRepository.findAll();
        assertThat(partyStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
