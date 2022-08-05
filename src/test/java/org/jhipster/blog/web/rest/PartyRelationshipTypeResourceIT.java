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
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.blog.IntegrationTest;
import org.jhipster.blog.domain.PartyRelationshipType;
import org.jhipster.blog.repository.PartyRelationshipTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartyRelationshipTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartyRelationshipTypeResourceIT {

    private static final Long DEFAULT_G_UID = 1L;
    private static final Long UPDATED_G_UID = 2L;

    private static final Boolean DEFAULT_HAS_TABLE = false;
    private static final Boolean UPDATED_HAS_TABLE = true;

    private static final String DEFAULT_PARTY_RELATIONSHIP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARTY_RELATIONSHIP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_ROLE_TYPE_ID_VALID_FROM = 1L;
    private static final Long UPDATED_ROLE_TYPE_ID_VALID_FROM = 2L;

    private static final Long DEFAULT_ROLE_TYPE_ID_VALID_TO = 1L;
    private static final Long UPDATED_ROLE_TYPE_ID_VALID_TO = 2L;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/party-relationship-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartyRelationshipTypeRepository partyRelationshipTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyRelationshipTypeMockMvc;

    private PartyRelationshipType partyRelationshipType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRelationshipType createEntity(EntityManager em) {
        PartyRelationshipType partyRelationshipType = new PartyRelationshipType()
            .gUID(DEFAULT_G_UID)
            .hasTable(DEFAULT_HAS_TABLE)
            .partyRelationshipName(DEFAULT_PARTY_RELATIONSHIP_NAME)
            .description(DEFAULT_DESCRIPTION)
            .roleTypeIdValidFrom(DEFAULT_ROLE_TYPE_ID_VALID_FROM)
            .roleTypeIdValidTo(DEFAULT_ROLE_TYPE_ID_VALID_TO)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return partyRelationshipType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRelationshipType createUpdatedEntity(EntityManager em) {
        PartyRelationshipType partyRelationshipType = new PartyRelationshipType()
            .gUID(UPDATED_G_UID)
            .hasTable(UPDATED_HAS_TABLE)
            .partyRelationshipName(UPDATED_PARTY_RELATIONSHIP_NAME)
            .description(UPDATED_DESCRIPTION)
            .roleTypeIdValidFrom(UPDATED_ROLE_TYPE_ID_VALID_FROM)
            .roleTypeIdValidTo(UPDATED_ROLE_TYPE_ID_VALID_TO)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return partyRelationshipType;
    }

    @BeforeEach
    public void initTest() {
        partyRelationshipType = createEntity(em);
    }

    @Test
    @Transactional
    void createPartyRelationshipType() throws Exception {
        int databaseSizeBeforeCreate = partyRelationshipTypeRepository.findAll().size();
        // Create the PartyRelationshipType
        restPartyRelationshipTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyRelationshipType))
            )
            .andExpect(status().isCreated());

        // Validate the PartyRelationshipType in the database
        List<PartyRelationshipType> partyRelationshipTypeList = partyRelationshipTypeRepository.findAll();
        assertThat(partyRelationshipTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PartyRelationshipType testPartyRelationshipType = partyRelationshipTypeList.get(partyRelationshipTypeList.size() - 1);
        assertThat(testPartyRelationshipType.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyRelationshipType.getHasTable()).isEqualTo(DEFAULT_HAS_TABLE);
        assertThat(testPartyRelationshipType.getPartyRelationshipName()).isEqualTo(DEFAULT_PARTY_RELATIONSHIP_NAME);
        assertThat(testPartyRelationshipType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPartyRelationshipType.getRoleTypeIdValidFrom()).isEqualTo(DEFAULT_ROLE_TYPE_ID_VALID_FROM);
        assertThat(testPartyRelationshipType.getRoleTypeIdValidTo()).isEqualTo(DEFAULT_ROLE_TYPE_ID_VALID_TO);
        assertThat(testPartyRelationshipType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyRelationshipType.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyRelationshipType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyRelationshipType.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPartyRelationshipTypeWithExistingId() throws Exception {
        // Create the PartyRelationshipType with an existing ID
        partyRelationshipType.setId(1L);

        int databaseSizeBeforeCreate = partyRelationshipTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyRelationshipTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyRelationshipType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationshipType in the database
        List<PartyRelationshipType> partyRelationshipTypeList = partyRelationshipTypeRepository.findAll();
        assertThat(partyRelationshipTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartyRelationshipTypes() throws Exception {
        // Initialize the database
        partyRelationshipTypeRepository.saveAndFlush(partyRelationshipType);

        // Get all the partyRelationshipTypeList
        restPartyRelationshipTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyRelationshipType.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.intValue())))
            .andExpect(jsonPath("$.[*].hasTable").value(hasItem(DEFAULT_HAS_TABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].partyRelationshipName").value(hasItem(DEFAULT_PARTY_RELATIONSHIP_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].roleTypeIdValidFrom").value(hasItem(DEFAULT_ROLE_TYPE_ID_VALID_FROM.intValue())))
            .andExpect(jsonPath("$.[*].roleTypeIdValidTo").value(hasItem(DEFAULT_ROLE_TYPE_ID_VALID_TO.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPartyRelationshipType() throws Exception {
        // Initialize the database
        partyRelationshipTypeRepository.saveAndFlush(partyRelationshipType);

        // Get the partyRelationshipType
        restPartyRelationshipTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, partyRelationshipType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyRelationshipType.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.intValue()))
            .andExpect(jsonPath("$.hasTable").value(DEFAULT_HAS_TABLE.booleanValue()))
            .andExpect(jsonPath("$.partyRelationshipName").value(DEFAULT_PARTY_RELATIONSHIP_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.roleTypeIdValidFrom").value(DEFAULT_ROLE_TYPE_ID_VALID_FROM.intValue()))
            .andExpect(jsonPath("$.roleTypeIdValidTo").value(DEFAULT_ROLE_TYPE_ID_VALID_TO.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPartyRelationshipType() throws Exception {
        // Get the partyRelationshipType
        restPartyRelationshipTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartyRelationshipType() throws Exception {
        // Initialize the database
        partyRelationshipTypeRepository.saveAndFlush(partyRelationshipType);

        int databaseSizeBeforeUpdate = partyRelationshipTypeRepository.findAll().size();

        // Update the partyRelationshipType
        PartyRelationshipType updatedPartyRelationshipType = partyRelationshipTypeRepository.findById(partyRelationshipType.getId()).get();
        // Disconnect from session so that the updates on updatedPartyRelationshipType are not directly saved in db
        em.detach(updatedPartyRelationshipType);
        updatedPartyRelationshipType
            .gUID(UPDATED_G_UID)
            .hasTable(UPDATED_HAS_TABLE)
            .partyRelationshipName(UPDATED_PARTY_RELATIONSHIP_NAME)
            .description(UPDATED_DESCRIPTION)
            .roleTypeIdValidFrom(UPDATED_ROLE_TYPE_ID_VALID_FROM)
            .roleTypeIdValidTo(UPDATED_ROLE_TYPE_ID_VALID_TO)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyRelationshipTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartyRelationshipType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartyRelationshipType))
            )
            .andExpect(status().isOk());

        // Validate the PartyRelationshipType in the database
        List<PartyRelationshipType> partyRelationshipTypeList = partyRelationshipTypeRepository.findAll();
        assertThat(partyRelationshipTypeList).hasSize(databaseSizeBeforeUpdate);
        PartyRelationshipType testPartyRelationshipType = partyRelationshipTypeList.get(partyRelationshipTypeList.size() - 1);
        assertThat(testPartyRelationshipType.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyRelationshipType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testPartyRelationshipType.getPartyRelationshipName()).isEqualTo(UPDATED_PARTY_RELATIONSHIP_NAME);
        assertThat(testPartyRelationshipType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartyRelationshipType.getRoleTypeIdValidFrom()).isEqualTo(UPDATED_ROLE_TYPE_ID_VALID_FROM);
        assertThat(testPartyRelationshipType.getRoleTypeIdValidTo()).isEqualTo(UPDATED_ROLE_TYPE_ID_VALID_TO);
        assertThat(testPartyRelationshipType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyRelationshipType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyRelationshipType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyRelationshipType.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPartyRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipTypeRepository.findAll().size();
        partyRelationshipType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyRelationshipTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partyRelationshipType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyRelationshipType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationshipType in the database
        List<PartyRelationshipType> partyRelationshipTypeList = partyRelationshipTypeRepository.findAll();
        assertThat(partyRelationshipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartyRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipTypeRepository.findAll().size();
        partyRelationshipType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyRelationshipTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyRelationshipType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationshipType in the database
        List<PartyRelationshipType> partyRelationshipTypeList = partyRelationshipTypeRepository.findAll();
        assertThat(partyRelationshipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartyRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipTypeRepository.findAll().size();
        partyRelationshipType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyRelationshipTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyRelationshipType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyRelationshipType in the database
        List<PartyRelationshipType> partyRelationshipTypeList = partyRelationshipTypeRepository.findAll();
        assertThat(partyRelationshipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartyRelationshipTypeWithPatch() throws Exception {
        // Initialize the database
        partyRelationshipTypeRepository.saveAndFlush(partyRelationshipType);

        int databaseSizeBeforeUpdate = partyRelationshipTypeRepository.findAll().size();

        // Update the partyRelationshipType using partial update
        PartyRelationshipType partialUpdatedPartyRelationshipType = new PartyRelationshipType();
        partialUpdatedPartyRelationshipType.setId(partyRelationshipType.getId());

        partialUpdatedPartyRelationshipType
            .gUID(UPDATED_G_UID)
            .hasTable(UPDATED_HAS_TABLE)
            .description(UPDATED_DESCRIPTION)
            .roleTypeIdValidFrom(UPDATED_ROLE_TYPE_ID_VALID_FROM)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);

        restPartyRelationshipTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyRelationshipType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyRelationshipType))
            )
            .andExpect(status().isOk());

        // Validate the PartyRelationshipType in the database
        List<PartyRelationshipType> partyRelationshipTypeList = partyRelationshipTypeRepository.findAll();
        assertThat(partyRelationshipTypeList).hasSize(databaseSizeBeforeUpdate);
        PartyRelationshipType testPartyRelationshipType = partyRelationshipTypeList.get(partyRelationshipTypeList.size() - 1);
        assertThat(testPartyRelationshipType.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyRelationshipType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testPartyRelationshipType.getPartyRelationshipName()).isEqualTo(DEFAULT_PARTY_RELATIONSHIP_NAME);
        assertThat(testPartyRelationshipType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartyRelationshipType.getRoleTypeIdValidFrom()).isEqualTo(UPDATED_ROLE_TYPE_ID_VALID_FROM);
        assertThat(testPartyRelationshipType.getRoleTypeIdValidTo()).isEqualTo(DEFAULT_ROLE_TYPE_ID_VALID_TO);
        assertThat(testPartyRelationshipType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyRelationshipType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyRelationshipType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyRelationshipType.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePartyRelationshipTypeWithPatch() throws Exception {
        // Initialize the database
        partyRelationshipTypeRepository.saveAndFlush(partyRelationshipType);

        int databaseSizeBeforeUpdate = partyRelationshipTypeRepository.findAll().size();

        // Update the partyRelationshipType using partial update
        PartyRelationshipType partialUpdatedPartyRelationshipType = new PartyRelationshipType();
        partialUpdatedPartyRelationshipType.setId(partyRelationshipType.getId());

        partialUpdatedPartyRelationshipType
            .gUID(UPDATED_G_UID)
            .hasTable(UPDATED_HAS_TABLE)
            .partyRelationshipName(UPDATED_PARTY_RELATIONSHIP_NAME)
            .description(UPDATED_DESCRIPTION)
            .roleTypeIdValidFrom(UPDATED_ROLE_TYPE_ID_VALID_FROM)
            .roleTypeIdValidTo(UPDATED_ROLE_TYPE_ID_VALID_TO)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyRelationshipTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyRelationshipType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyRelationshipType))
            )
            .andExpect(status().isOk());

        // Validate the PartyRelationshipType in the database
        List<PartyRelationshipType> partyRelationshipTypeList = partyRelationshipTypeRepository.findAll();
        assertThat(partyRelationshipTypeList).hasSize(databaseSizeBeforeUpdate);
        PartyRelationshipType testPartyRelationshipType = partyRelationshipTypeList.get(partyRelationshipTypeList.size() - 1);
        assertThat(testPartyRelationshipType.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyRelationshipType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testPartyRelationshipType.getPartyRelationshipName()).isEqualTo(UPDATED_PARTY_RELATIONSHIP_NAME);
        assertThat(testPartyRelationshipType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartyRelationshipType.getRoleTypeIdValidFrom()).isEqualTo(UPDATED_ROLE_TYPE_ID_VALID_FROM);
        assertThat(testPartyRelationshipType.getRoleTypeIdValidTo()).isEqualTo(UPDATED_ROLE_TYPE_ID_VALID_TO);
        assertThat(testPartyRelationshipType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyRelationshipType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyRelationshipType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyRelationshipType.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPartyRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipTypeRepository.findAll().size();
        partyRelationshipType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyRelationshipTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partyRelationshipType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyRelationshipType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationshipType in the database
        List<PartyRelationshipType> partyRelationshipTypeList = partyRelationshipTypeRepository.findAll();
        assertThat(partyRelationshipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartyRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipTypeRepository.findAll().size();
        partyRelationshipType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyRelationshipTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyRelationshipType))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationshipType in the database
        List<PartyRelationshipType> partyRelationshipTypeList = partyRelationshipTypeRepository.findAll();
        assertThat(partyRelationshipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartyRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipTypeRepository.findAll().size();
        partyRelationshipType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyRelationshipTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyRelationshipType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyRelationshipType in the database
        List<PartyRelationshipType> partyRelationshipTypeList = partyRelationshipTypeRepository.findAll();
        assertThat(partyRelationshipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartyRelationshipType() throws Exception {
        // Initialize the database
        partyRelationshipTypeRepository.saveAndFlush(partyRelationshipType);

        int databaseSizeBeforeDelete = partyRelationshipTypeRepository.findAll().size();

        // Delete the partyRelationshipType
        restPartyRelationshipTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, partyRelationshipType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyRelationshipType> partyRelationshipTypeList = partyRelationshipTypeRepository.findAll();
        assertThat(partyRelationshipTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
