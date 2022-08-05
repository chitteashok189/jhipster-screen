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
import org.jhipster.blog.domain.PartyClassification;
import org.jhipster.blog.repository.PartyClassificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartyClassificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartyClassificationResourceIT {

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

    private static final String ENTITY_API_URL = "/api/party-classifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartyClassificationRepository partyClassificationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyClassificationMockMvc;

    private PartyClassification partyClassification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyClassification createEntity(EntityManager em) {
        PartyClassification partyClassification = new PartyClassification()
            .gUID(DEFAULT_G_UID)
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return partyClassification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyClassification createUpdatedEntity(EntityManager em) {
        PartyClassification partyClassification = new PartyClassification()
            .gUID(UPDATED_G_UID)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return partyClassification;
    }

    @BeforeEach
    public void initTest() {
        partyClassification = createEntity(em);
    }

    @Test
    @Transactional
    void createPartyClassification() throws Exception {
        int databaseSizeBeforeCreate = partyClassificationRepository.findAll().size();
        // Create the PartyClassification
        restPartyClassificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyClassification))
            )
            .andExpect(status().isCreated());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeCreate + 1);
        PartyClassification testPartyClassification = partyClassificationList.get(partyClassificationList.size() - 1);
        assertThat(testPartyClassification.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyClassification.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPartyClassification.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testPartyClassification.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyClassification.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyClassification.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyClassification.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPartyClassificationWithExistingId() throws Exception {
        // Create the PartyClassification with an existing ID
        partyClassification.setId(1L);

        int databaseSizeBeforeCreate = partyClassificationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyClassificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyClassification))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartyClassifications() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList
        restPartyClassificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyClassification.getId().intValue())))
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
    void getPartyClassification() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get the partyClassification
        restPartyClassificationMockMvc
            .perform(get(ENTITY_API_URL_ID, partyClassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyClassification.getId().intValue()))
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
    void getNonExistingPartyClassification() throws Exception {
        // Get the partyClassification
        restPartyClassificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartyClassification() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        int databaseSizeBeforeUpdate = partyClassificationRepository.findAll().size();

        // Update the partyClassification
        PartyClassification updatedPartyClassification = partyClassificationRepository.findById(partyClassification.getId()).get();
        // Disconnect from session so that the updates on updatedPartyClassification are not directly saved in db
        em.detach(updatedPartyClassification);
        updatedPartyClassification
            .gUID(UPDATED_G_UID)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyClassificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartyClassification.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartyClassification))
            )
            .andExpect(status().isOk());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeUpdate);
        PartyClassification testPartyClassification = partyClassificationList.get(partyClassificationList.size() - 1);
        assertThat(testPartyClassification.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyClassification.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPartyClassification.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testPartyClassification.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyClassification.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyClassification.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyClassification.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPartyClassification() throws Exception {
        int databaseSizeBeforeUpdate = partyClassificationRepository.findAll().size();
        partyClassification.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyClassificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partyClassification.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyClassification))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartyClassification() throws Exception {
        int databaseSizeBeforeUpdate = partyClassificationRepository.findAll().size();
        partyClassification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyClassificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyClassification))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartyClassification() throws Exception {
        int databaseSizeBeforeUpdate = partyClassificationRepository.findAll().size();
        partyClassification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyClassificationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyClassification))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartyClassificationWithPatch() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        int databaseSizeBeforeUpdate = partyClassificationRepository.findAll().size();

        // Update the partyClassification using partial update
        PartyClassification partialUpdatedPartyClassification = new PartyClassification();
        partialUpdatedPartyClassification.setId(partyClassification.getId());

        partialUpdatedPartyClassification.createdBy(UPDATED_CREATED_BY).createdOn(UPDATED_CREATED_ON).updatedOn(UPDATED_UPDATED_ON);

        restPartyClassificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyClassification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyClassification))
            )
            .andExpect(status().isOk());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeUpdate);
        PartyClassification testPartyClassification = partyClassificationList.get(partyClassificationList.size() - 1);
        assertThat(testPartyClassification.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyClassification.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPartyClassification.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testPartyClassification.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyClassification.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyClassification.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyClassification.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePartyClassificationWithPatch() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        int databaseSizeBeforeUpdate = partyClassificationRepository.findAll().size();

        // Update the partyClassification using partial update
        PartyClassification partialUpdatedPartyClassification = new PartyClassification();
        partialUpdatedPartyClassification.setId(partyClassification.getId());

        partialUpdatedPartyClassification
            .gUID(UPDATED_G_UID)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyClassificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyClassification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyClassification))
            )
            .andExpect(status().isOk());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeUpdate);
        PartyClassification testPartyClassification = partyClassificationList.get(partyClassificationList.size() - 1);
        assertThat(testPartyClassification.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyClassification.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPartyClassification.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testPartyClassification.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyClassification.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyClassification.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyClassification.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPartyClassification() throws Exception {
        int databaseSizeBeforeUpdate = partyClassificationRepository.findAll().size();
        partyClassification.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyClassificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partyClassification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyClassification))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartyClassification() throws Exception {
        int databaseSizeBeforeUpdate = partyClassificationRepository.findAll().size();
        partyClassification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyClassificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyClassification))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartyClassification() throws Exception {
        int databaseSizeBeforeUpdate = partyClassificationRepository.findAll().size();
        partyClassification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyClassificationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyClassification))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartyClassification() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        int databaseSizeBeforeDelete = partyClassificationRepository.findAll().size();

        // Delete the partyClassification
        restPartyClassificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, partyClassification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
