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
import org.jhipster.blog.domain.PartyType;
import org.jhipster.blog.domain.enumeration.ParentType;
import org.jhipster.blog.repository.PartyTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartyTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartyTypeResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final ParentType DEFAULT_PARENT_TYPE_ID = ParentType.Automated;
    private static final ParentType UPDATED_PARENT_TYPE_ID = ParentType.Agent;

    private static final String DEFAULT_HAS_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_HAS_TABLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PARTY_TYPE_ATTR = "AAAAAAAAAA";
    private static final String UPDATED_PARTY_TYPE_ATTR = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/party-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartyTypeRepository partyTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyTypeMockMvc;

    private PartyType partyType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyType createEntity(EntityManager em) {
        PartyType partyType = new PartyType()
            .gUID(DEFAULT_G_UID)
            .parentTypeID(DEFAULT_PARENT_TYPE_ID)
            .hasTable(DEFAULT_HAS_TABLE)
            .description(DEFAULT_DESCRIPTION)
            .partyTypeAttr(DEFAULT_PARTY_TYPE_ATTR)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return partyType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyType createUpdatedEntity(EntityManager em) {
        PartyType partyType = new PartyType()
            .gUID(UPDATED_G_UID)
            .parentTypeID(UPDATED_PARENT_TYPE_ID)
            .hasTable(UPDATED_HAS_TABLE)
            .description(UPDATED_DESCRIPTION)
            .partyTypeAttr(UPDATED_PARTY_TYPE_ATTR)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return partyType;
    }

    @BeforeEach
    public void initTest() {
        partyType = createEntity(em);
    }

    @Test
    @Transactional
    void createPartyType() throws Exception {
        int databaseSizeBeforeCreate = partyTypeRepository.findAll().size();
        // Create the PartyType
        restPartyTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyType)))
            .andExpect(status().isCreated());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PartyType testPartyType = partyTypeList.get(partyTypeList.size() - 1);
        assertThat(testPartyType.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyType.getParentTypeID()).isEqualTo(DEFAULT_PARENT_TYPE_ID);
        assertThat(testPartyType.getHasTable()).isEqualTo(DEFAULT_HAS_TABLE);
        assertThat(testPartyType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPartyType.getPartyTypeAttr()).isEqualTo(DEFAULT_PARTY_TYPE_ATTR);
        assertThat(testPartyType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyType.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyType.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPartyTypeWithExistingId() throws Exception {
        // Create the PartyType with an existing ID
        partyType.setId(1L);

        int databaseSizeBeforeCreate = partyTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyType)))
            .andExpect(status().isBadRequest());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartyTypes() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        // Get all the partyTypeList
        restPartyTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].parentTypeID").value(hasItem(DEFAULT_PARENT_TYPE_ID.toString())))
            .andExpect(jsonPath("$.[*].hasTable").value(hasItem(DEFAULT_HAS_TABLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].partyTypeAttr").value(hasItem(DEFAULT_PARTY_TYPE_ATTR)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPartyType() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        // Get the partyType
        restPartyTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, partyType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyType.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.parentTypeID").value(DEFAULT_PARENT_TYPE_ID.toString()))
            .andExpect(jsonPath("$.hasTable").value(DEFAULT_HAS_TABLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.partyTypeAttr").value(DEFAULT_PARTY_TYPE_ATTR))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPartyType() throws Exception {
        // Get the partyType
        restPartyTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartyType() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();

        // Update the partyType
        PartyType updatedPartyType = partyTypeRepository.findById(partyType.getId()).get();
        // Disconnect from session so that the updates on updatedPartyType are not directly saved in db
        em.detach(updatedPartyType);
        updatedPartyType
            .gUID(UPDATED_G_UID)
            .parentTypeID(UPDATED_PARENT_TYPE_ID)
            .hasTable(UPDATED_HAS_TABLE)
            .description(UPDATED_DESCRIPTION)
            .partyTypeAttr(UPDATED_PARTY_TYPE_ATTR)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartyType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartyType))
            )
            .andExpect(status().isOk());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
        PartyType testPartyType = partyTypeList.get(partyTypeList.size() - 1);
        assertThat(testPartyType.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyType.getParentTypeID()).isEqualTo(UPDATED_PARENT_TYPE_ID);
        assertThat(testPartyType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testPartyType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartyType.getPartyTypeAttr()).isEqualTo(UPDATED_PARTY_TYPE_ATTR);
        assertThat(testPartyType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyType.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPartyType() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();
        partyType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partyType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartyType() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();
        partyType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartyType() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();
        partyType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartyTypeWithPatch() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();

        // Update the partyType using partial update
        PartyType partialUpdatedPartyType = new PartyType();
        partialUpdatedPartyType.setId(partyType.getId());

        partialUpdatedPartyType
            .gUID(UPDATED_G_UID)
            .parentTypeID(UPDATED_PARENT_TYPE_ID)
            .hasTable(UPDATED_HAS_TABLE)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restPartyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyType))
            )
            .andExpect(status().isOk());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
        PartyType testPartyType = partyTypeList.get(partyTypeList.size() - 1);
        assertThat(testPartyType.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyType.getParentTypeID()).isEqualTo(UPDATED_PARENT_TYPE_ID);
        assertThat(testPartyType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testPartyType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPartyType.getPartyTypeAttr()).isEqualTo(DEFAULT_PARTY_TYPE_ATTR);
        assertThat(testPartyType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyType.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePartyTypeWithPatch() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();

        // Update the partyType using partial update
        PartyType partialUpdatedPartyType = new PartyType();
        partialUpdatedPartyType.setId(partyType.getId());

        partialUpdatedPartyType
            .gUID(UPDATED_G_UID)
            .parentTypeID(UPDATED_PARENT_TYPE_ID)
            .hasTable(UPDATED_HAS_TABLE)
            .description(UPDATED_DESCRIPTION)
            .partyTypeAttr(UPDATED_PARTY_TYPE_ATTR)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyType))
            )
            .andExpect(status().isOk());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
        PartyType testPartyType = partyTypeList.get(partyTypeList.size() - 1);
        assertThat(testPartyType.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyType.getParentTypeID()).isEqualTo(UPDATED_PARENT_TYPE_ID);
        assertThat(testPartyType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testPartyType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartyType.getPartyTypeAttr()).isEqualTo(UPDATED_PARTY_TYPE_ATTR);
        assertThat(testPartyType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyType.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPartyType() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();
        partyType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partyType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartyType() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();
        partyType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartyType() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();
        partyType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partyType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartyType() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        int databaseSizeBeforeDelete = partyTypeRepository.findAll().size();

        // Delete the partyType
        restPartyTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, partyType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
