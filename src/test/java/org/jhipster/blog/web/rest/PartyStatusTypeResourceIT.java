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
import org.jhipster.blog.domain.PartyStatusType;
import org.jhipster.blog.repository.PartyStatusTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartyStatusTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartyStatusTypeResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Long DEFAULT_PARENT_TYPE_ID = 1L;
    private static final Long UPDATED_PARENT_TYPE_ID = 2L;

    private static final Boolean DEFAULT_HAS_TABLE = false;
    private static final Boolean UPDATED_HAS_TABLE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CHILD_STATUS_TYPE = 1L;
    private static final Long UPDATED_CHILD_STATUS_TYPE = 2L;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/party-status-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartyStatusTypeRepository partyStatusTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyStatusTypeMockMvc;

    private PartyStatusType partyStatusType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyStatusType createEntity(EntityManager em) {
        PartyStatusType partyStatusType = new PartyStatusType()
            .gUID(DEFAULT_G_UID)
            .parentTypeID(DEFAULT_PARENT_TYPE_ID)
            .hasTable(DEFAULT_HAS_TABLE)
            .description(DEFAULT_DESCRIPTION)
            .childStatusType(DEFAULT_CHILD_STATUS_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return partyStatusType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyStatusType createUpdatedEntity(EntityManager em) {
        PartyStatusType partyStatusType = new PartyStatusType()
            .gUID(UPDATED_G_UID)
            .parentTypeID(UPDATED_PARENT_TYPE_ID)
            .hasTable(UPDATED_HAS_TABLE)
            .description(UPDATED_DESCRIPTION)
            .childStatusType(UPDATED_CHILD_STATUS_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return partyStatusType;
    }

    @BeforeEach
    public void initTest() {
        partyStatusType = createEntity(em);
    }

    @Test
    @Transactional
    void createPartyStatusType() throws Exception {
        int databaseSizeBeforeCreate = partyStatusTypeRepository.findAll().size();
        // Create the PartyStatusType
        restPartyStatusTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyStatusType))
            )
            .andExpect(status().isCreated());

        // Validate the PartyStatusType in the database
        List<PartyStatusType> partyStatusTypeList = partyStatusTypeRepository.findAll();
        assertThat(partyStatusTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PartyStatusType testPartyStatusType = partyStatusTypeList.get(partyStatusTypeList.size() - 1);
        assertThat(testPartyStatusType.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyStatusType.getParentTypeID()).isEqualTo(DEFAULT_PARENT_TYPE_ID);
        assertThat(testPartyStatusType.getHasTable()).isEqualTo(DEFAULT_HAS_TABLE);
        assertThat(testPartyStatusType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPartyStatusType.getChildStatusType()).isEqualTo(DEFAULT_CHILD_STATUS_TYPE);
        assertThat(testPartyStatusType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyStatusType.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyStatusType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyStatusType.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPartyStatusTypeWithExistingId() throws Exception {
        // Create the PartyStatusType with an existing ID
        partyStatusType.setId(1L);

        int databaseSizeBeforeCreate = partyStatusTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyStatusTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyStatusType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatusType in the database
        List<PartyStatusType> partyStatusTypeList = partyStatusTypeRepository.findAll();
        assertThat(partyStatusTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartyStatusTypes() throws Exception {
        // Initialize the database
        partyStatusTypeRepository.saveAndFlush(partyStatusType);

        // Get all the partyStatusTypeList
        restPartyStatusTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyStatusType.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].parentTypeID").value(hasItem(DEFAULT_PARENT_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasTable").value(hasItem(DEFAULT_HAS_TABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].childStatusType").value(hasItem(DEFAULT_CHILD_STATUS_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPartyStatusType() throws Exception {
        // Initialize the database
        partyStatusTypeRepository.saveAndFlush(partyStatusType);

        // Get the partyStatusType
        restPartyStatusTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, partyStatusType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyStatusType.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.parentTypeID").value(DEFAULT_PARENT_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.hasTable").value(DEFAULT_HAS_TABLE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.childStatusType").value(DEFAULT_CHILD_STATUS_TYPE.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPartyStatusType() throws Exception {
        // Get the partyStatusType
        restPartyStatusTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartyStatusType() throws Exception {
        // Initialize the database
        partyStatusTypeRepository.saveAndFlush(partyStatusType);

        int databaseSizeBeforeUpdate = partyStatusTypeRepository.findAll().size();

        // Update the partyStatusType
        PartyStatusType updatedPartyStatusType = partyStatusTypeRepository.findById(partyStatusType.getId()).get();
        // Disconnect from session so that the updates on updatedPartyStatusType are not directly saved in db
        em.detach(updatedPartyStatusType);
        updatedPartyStatusType
            .gUID(UPDATED_G_UID)
            .parentTypeID(UPDATED_PARENT_TYPE_ID)
            .hasTable(UPDATED_HAS_TABLE)
            .description(UPDATED_DESCRIPTION)
            .childStatusType(UPDATED_CHILD_STATUS_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyStatusTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartyStatusType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartyStatusType))
            )
            .andExpect(status().isOk());

        // Validate the PartyStatusType in the database
        List<PartyStatusType> partyStatusTypeList = partyStatusTypeRepository.findAll();
        assertThat(partyStatusTypeList).hasSize(databaseSizeBeforeUpdate);
        PartyStatusType testPartyStatusType = partyStatusTypeList.get(partyStatusTypeList.size() - 1);
        assertThat(testPartyStatusType.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyStatusType.getParentTypeID()).isEqualTo(UPDATED_PARENT_TYPE_ID);
        assertThat(testPartyStatusType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testPartyStatusType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartyStatusType.getChildStatusType()).isEqualTo(UPDATED_CHILD_STATUS_TYPE);
        assertThat(testPartyStatusType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyStatusType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyStatusType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyStatusType.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPartyStatusType() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusTypeRepository.findAll().size();
        partyStatusType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyStatusTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partyStatusType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyStatusType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatusType in the database
        List<PartyStatusType> partyStatusTypeList = partyStatusTypeRepository.findAll();
        assertThat(partyStatusTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartyStatusType() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusTypeRepository.findAll().size();
        partyStatusType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyStatusTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyStatusType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatusType in the database
        List<PartyStatusType> partyStatusTypeList = partyStatusTypeRepository.findAll();
        assertThat(partyStatusTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartyStatusType() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusTypeRepository.findAll().size();
        partyStatusType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyStatusTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyStatusType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyStatusType in the database
        List<PartyStatusType> partyStatusTypeList = partyStatusTypeRepository.findAll();
        assertThat(partyStatusTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartyStatusTypeWithPatch() throws Exception {
        // Initialize the database
        partyStatusTypeRepository.saveAndFlush(partyStatusType);

        int databaseSizeBeforeUpdate = partyStatusTypeRepository.findAll().size();

        // Update the partyStatusType using partial update
        PartyStatusType partialUpdatedPartyStatusType = new PartyStatusType();
        partialUpdatedPartyStatusType.setId(partyStatusType.getId());

        partialUpdatedPartyStatusType.gUID(UPDATED_G_UID).hasTable(UPDATED_HAS_TABLE).updatedOn(UPDATED_UPDATED_ON);

        restPartyStatusTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyStatusType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyStatusType))
            )
            .andExpect(status().isOk());

        // Validate the PartyStatusType in the database
        List<PartyStatusType> partyStatusTypeList = partyStatusTypeRepository.findAll();
        assertThat(partyStatusTypeList).hasSize(databaseSizeBeforeUpdate);
        PartyStatusType testPartyStatusType = partyStatusTypeList.get(partyStatusTypeList.size() - 1);
        assertThat(testPartyStatusType.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyStatusType.getParentTypeID()).isEqualTo(DEFAULT_PARENT_TYPE_ID);
        assertThat(testPartyStatusType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testPartyStatusType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPartyStatusType.getChildStatusType()).isEqualTo(DEFAULT_CHILD_STATUS_TYPE);
        assertThat(testPartyStatusType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyStatusType.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyStatusType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyStatusType.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePartyStatusTypeWithPatch() throws Exception {
        // Initialize the database
        partyStatusTypeRepository.saveAndFlush(partyStatusType);

        int databaseSizeBeforeUpdate = partyStatusTypeRepository.findAll().size();

        // Update the partyStatusType using partial update
        PartyStatusType partialUpdatedPartyStatusType = new PartyStatusType();
        partialUpdatedPartyStatusType.setId(partyStatusType.getId());

        partialUpdatedPartyStatusType
            .gUID(UPDATED_G_UID)
            .parentTypeID(UPDATED_PARENT_TYPE_ID)
            .hasTable(UPDATED_HAS_TABLE)
            .description(UPDATED_DESCRIPTION)
            .childStatusType(UPDATED_CHILD_STATUS_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyStatusTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyStatusType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyStatusType))
            )
            .andExpect(status().isOk());

        // Validate the PartyStatusType in the database
        List<PartyStatusType> partyStatusTypeList = partyStatusTypeRepository.findAll();
        assertThat(partyStatusTypeList).hasSize(databaseSizeBeforeUpdate);
        PartyStatusType testPartyStatusType = partyStatusTypeList.get(partyStatusTypeList.size() - 1);
        assertThat(testPartyStatusType.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyStatusType.getParentTypeID()).isEqualTo(UPDATED_PARENT_TYPE_ID);
        assertThat(testPartyStatusType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testPartyStatusType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartyStatusType.getChildStatusType()).isEqualTo(UPDATED_CHILD_STATUS_TYPE);
        assertThat(testPartyStatusType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyStatusType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyStatusType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyStatusType.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPartyStatusType() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusTypeRepository.findAll().size();
        partyStatusType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyStatusTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partyStatusType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyStatusType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatusType in the database
        List<PartyStatusType> partyStatusTypeList = partyStatusTypeRepository.findAll();
        assertThat(partyStatusTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartyStatusType() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusTypeRepository.findAll().size();
        partyStatusType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyStatusTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyStatusType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyStatusType in the database
        List<PartyStatusType> partyStatusTypeList = partyStatusTypeRepository.findAll();
        assertThat(partyStatusTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartyStatusType() throws Exception {
        int databaseSizeBeforeUpdate = partyStatusTypeRepository.findAll().size();
        partyStatusType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyStatusTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyStatusType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyStatusType in the database
        List<PartyStatusType> partyStatusTypeList = partyStatusTypeRepository.findAll();
        assertThat(partyStatusTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartyStatusType() throws Exception {
        // Initialize the database
        partyStatusTypeRepository.saveAndFlush(partyStatusType);

        int databaseSizeBeforeDelete = partyStatusTypeRepository.findAll().size();

        // Delete the partyStatusType
        restPartyStatusTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, partyStatusType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyStatusType> partyStatusTypeList = partyStatusTypeRepository.findAll();
        assertThat(partyStatusTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
