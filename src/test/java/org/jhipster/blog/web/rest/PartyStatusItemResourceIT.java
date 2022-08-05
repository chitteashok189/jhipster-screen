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
import org.jhipster.blog.domain.PartyStatusItem;
import org.jhipster.blog.repository.PartyStatusItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartyStatusItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartyStatusItemResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Integer DEFAULT_STATUS_CODE = 1;
    private static final Integer UPDATED_STATUS_CODE = 2;

    private static final Long DEFAULT_SEQUENCE_ID = 1L;
    private static final Long UPDATED_SEQUENCE_ID = 2L;

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

    private static final String ENTITY_API_URL = "/api/party-status-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartyStatusItemRepository partyStatusItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyStatusItemMockMvc;

    private PartyStatusItem partyStatusItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyStatusItem createEntity(EntityManager em) {
        PartyStatusItem partyStatusItem = new PartyStatusItem()
            .gUID(DEFAULT_G_UID)
            .statusCode(DEFAULT_STATUS_CODE)
            .sequenceID(DEFAULT_SEQUENCE_ID)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return partyStatusItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyStatusItem createUpdatedEntity(EntityManager em) {
        PartyStatusItem partyStatusItem = new PartyStatusItem()
            .gUID(UPDATED_G_UID)
            .statusCode(UPDATED_STATUS_CODE)
            .sequenceID(UPDATED_SEQUENCE_ID)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return partyStatusItem;
    }

    @BeforeEach
    public void initTest() {
        partyStatusItem = createEntity(em);
    }

    @Test
    @Transactional
    void createPartyStatusItem() throws Exception {
        int databaseSizeBeforeCreate = partyStatusItemRepository.findAll().size();
        // Create the PartyStatusItem
        restPartyStatusItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyStatusItem))
            )
            .andExpect(status().isCreated());

        // Validate the PartyStatusItem in the database
        List<PartyStatusItem> partyStatusItemList = partyStatusItemRepository.findAll();
        assertThat(partyStatusItemList).hasSize(databaseSizeBeforeCreate + 1);
        PartyStatusItem testPartyStatusItem = partyStatusItemList.get(partyStatusItemList.size() - 1);
        assertThat(testPartyStatusItem.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyStatusItem.getStatusCode()).isEqualTo(DEFAULT_STATUS_CODE);
        assertThat(testPartyStatusItem.getSequenceID()).isEqualTo(DEFAULT_SEQUENCE_ID);
        assertThat(testPartyStatusItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPartyStatusItem.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyStatusItem.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyStatusItem.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyStatusItem.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPartyStatusItemWithExistingId() throws Exception {
        // Create the PartyStatusItem with an existing ID
        partyStatusItem.setId(1L);

        int databaseSizeBeforeCreate = partyStatusItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyStatusItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyStatusItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatusItem in the database
        List<PartyStatusItem> partyStatusItemList = partyStatusItemRepository.findAll();
        assertThat(partyStatusItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartyStatusItems() throws Exception {
        // Initialize the database
        partyStatusItemRepository.saveAndFlush(partyStatusItem);

        // Get all the partyStatusItemList
        restPartyStatusItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyStatusItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].statusCode").value(hasItem(DEFAULT_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].sequenceID").value(hasItem(DEFAULT_SEQUENCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPartyStatusItem() throws Exception {
        // Initialize the database
        partyStatusItemRepository.saveAndFlush(partyStatusItem);

        // Get the partyStatusItem
        restPartyStatusItemMockMvc
            .perform(get(ENTITY_API_URL_ID, partyStatusItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyStatusItem.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.statusCode").value(DEFAULT_STATUS_CODE))
            .andExpect(jsonPath("$.sequenceID").value(DEFAULT_SEQUENCE_ID.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPartyStatusItem() throws Exception {
        // Get the partyStatusItem
        restPartyStatusItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartyStatusItem() throws Exception {
        // Initialize the database
        partyStatusItemRepository.saveAndFlush(partyStatusItem);

        int databaseSizeBeforeUpdate = partyStatusItemRepository.findAll().size();

        // Update the partyStatusItem
        PartyStatusItem updatedPartyStatusItem = partyStatusItemRepository.findById(partyStatusItem.getId()).get();
        // Disconnect from session so that the updates on updatedPartyStatusItem are not directly saved in db
        em.detach(updatedPartyStatusItem);
        updatedPartyStatusItem
            .gUID(UPDATED_G_UID)
            .statusCode(UPDATED_STATUS_CODE)
            .sequenceID(UPDATED_SEQUENCE_ID)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyStatusItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartyStatusItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartyStatusItem))
            )
            .andExpect(status().isOk());

        // Validate the PartyStatusItem in the database
        List<PartyStatusItem> partyStatusItemList = partyStatusItemRepository.findAll();
        assertThat(partyStatusItemList).hasSize(databaseSizeBeforeUpdate);
        PartyStatusItem testPartyStatusItem = partyStatusItemList.get(partyStatusItemList.size() - 1);
        assertThat(testPartyStatusItem.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyStatusItem.getStatusCode()).isEqualTo(UPDATED_STATUS_CODE);
        assertThat(testPartyStatusItem.getSequenceID()).isEqualTo(UPDATED_SEQUENCE_ID);
        assertThat(testPartyStatusItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartyStatusItem.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyStatusItem.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyStatusItem.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyStatusItem.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPartyStatusItem() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusItemRepository.findAll().size();
        partyStatusItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyStatusItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partyStatusItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyStatusItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatusItem in the database
        List<PartyStatusItem> partyStatusItemList = partyStatusItemRepository.findAll();
        assertThat(partyStatusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartyStatusItem() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusItemRepository.findAll().size();
        partyStatusItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyStatusItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyStatusItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatusItem in the database
        List<PartyStatusItem> partyStatusItemList = partyStatusItemRepository.findAll();
        assertThat(partyStatusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartyStatusItem() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusItemRepository.findAll().size();
        partyStatusItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyStatusItemMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyStatusItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyStatusItem in the database
        List<PartyStatusItem> partyStatusItemList = partyStatusItemRepository.findAll();
        assertThat(partyStatusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartyStatusItemWithPatch() throws Exception {
        // Initialize the database
        partyStatusItemRepository.saveAndFlush(partyStatusItem);

        int databaseSizeBeforeUpdate = partyStatusItemRepository.findAll().size();

        // Update the partyStatusItem using partial update
        PartyStatusItem partialUpdatedPartyStatusItem = new PartyStatusItem();
        partialUpdatedPartyStatusItem.setId(partyStatusItem.getId());

        partialUpdatedPartyStatusItem
            .gUID(UPDATED_G_UID)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyStatusItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyStatusItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyStatusItem))
            )
            .andExpect(status().isOk());

        // Validate the PartyStatusItem in the database
        List<PartyStatusItem> partyStatusItemList = partyStatusItemRepository.findAll();
        assertThat(partyStatusItemList).hasSize(databaseSizeBeforeUpdate);
        PartyStatusItem testPartyStatusItem = partyStatusItemList.get(partyStatusItemList.size() - 1);
        assertThat(testPartyStatusItem.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyStatusItem.getStatusCode()).isEqualTo(DEFAULT_STATUS_CODE);
        assertThat(testPartyStatusItem.getSequenceID()).isEqualTo(DEFAULT_SEQUENCE_ID);
        assertThat(testPartyStatusItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartyStatusItem.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyStatusItem.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyStatusItem.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyStatusItem.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePartyStatusItemWithPatch() throws Exception {
        // Initialize the database
        partyStatusItemRepository.saveAndFlush(partyStatusItem);

        int databaseSizeBeforeUpdate = partyStatusItemRepository.findAll().size();

        // Update the partyStatusItem using partial update
        PartyStatusItem partialUpdatedPartyStatusItem = new PartyStatusItem();
        partialUpdatedPartyStatusItem.setId(partyStatusItem.getId());

        partialUpdatedPartyStatusItem
            .gUID(UPDATED_G_UID)
            .statusCode(UPDATED_STATUS_CODE)
            .sequenceID(UPDATED_SEQUENCE_ID)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyStatusItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyStatusItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyStatusItem))
            )
            .andExpect(status().isOk());

        // Validate the PartyStatusItem in the database
        List<PartyStatusItem> partyStatusItemList = partyStatusItemRepository.findAll();
        assertThat(partyStatusItemList).hasSize(databaseSizeBeforeUpdate);
        PartyStatusItem testPartyStatusItem = partyStatusItemList.get(partyStatusItemList.size() - 1);
        assertThat(testPartyStatusItem.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyStatusItem.getStatusCode()).isEqualTo(UPDATED_STATUS_CODE);
        assertThat(testPartyStatusItem.getSequenceID()).isEqualTo(UPDATED_SEQUENCE_ID);
        assertThat(testPartyStatusItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartyStatusItem.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyStatusItem.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyStatusItem.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyStatusItem.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPartyStatusItem() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusItemRepository.findAll().size();
        partyStatusItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyStatusItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partyStatusItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyStatusItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatusItem in the database
        List<PartyStatusItem> partyStatusItemList = partyStatusItemRepository.findAll();
        assertThat(partyStatusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartyStatusItem() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusItemRepository.findAll().size();
        partyStatusItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyStatusItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyStatusItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatusItem in the database
        List<PartyStatusItem> partyStatusItemList = partyStatusItemRepository.findAll();
        assertThat(partyStatusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartyStatusItem() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusItemRepository.findAll().size();
        partyStatusItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyStatusItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyStatusItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyStatusItem in the database
        List<PartyStatusItem> partyStatusItemList = partyStatusItemRepository.findAll();
        assertThat(partyStatusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartyStatusItem() throws Exception {
        // Initialize the database
        partyStatusItemRepository.saveAndFlush(partyStatusItem);

        int databaseSizeBeforeDelete = partyStatusItemRepository.findAll().size();

        // Delete the partyStatusItem
        restPartyStatusItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, partyStatusItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyStatusItem> partyStatusItemList = partyStatusItemRepository.findAll();
        assertThat(partyStatusItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
