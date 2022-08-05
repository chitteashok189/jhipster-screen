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
import org.jhipster.blog.domain.RoleType;
import org.jhipster.blog.repository.RoleTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RoleTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoleTypeResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_HAS_TABLE = false;
    private static final Boolean UPDATED_HAS_TABLE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CHILD_ROLE_TYPE = 1L;
    private static final Long UPDATED_CHILD_ROLE_TYPE = 2L;

    private static final Long DEFAULT_VALID_PARTY_RELATIONSHIP_TYPE = 1L;
    private static final Long UPDATED_VALID_PARTY_RELATIONSHIP_TYPE = 2L;

    private static final Long DEFAULT_VALID_FROM_PARTY_RELATIONSHIP_TYPE = 1L;
    private static final Long UPDATED_VALID_FROM_PARTY_RELATIONSHIP_TYPE = 2L;

    private static final String DEFAULT_PARTY_INVITATION_ROLE_ASSOCIATION = "AAAAAAAAAA";
    private static final String UPDATED_PARTY_INVITATION_ROLE_ASSOCIATION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/role-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoleTypeRepository roleTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoleTypeMockMvc;

    private RoleType roleType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleType createEntity(EntityManager em) {
        RoleType roleType = new RoleType()
            .gUID(DEFAULT_G_UID)
            .hasTable(DEFAULT_HAS_TABLE)
            .description(DEFAULT_DESCRIPTION)
            .childRoleType(DEFAULT_CHILD_ROLE_TYPE)
            .validPartyRelationshipType(DEFAULT_VALID_PARTY_RELATIONSHIP_TYPE)
            .validFromPartyRelationshipType(DEFAULT_VALID_FROM_PARTY_RELATIONSHIP_TYPE)
            .partyInvitationRoleAssociation(DEFAULT_PARTY_INVITATION_ROLE_ASSOCIATION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return roleType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleType createUpdatedEntity(EntityManager em) {
        RoleType roleType = new RoleType()
            .gUID(UPDATED_G_UID)
            .hasTable(UPDATED_HAS_TABLE)
            .description(UPDATED_DESCRIPTION)
            .childRoleType(UPDATED_CHILD_ROLE_TYPE)
            .validPartyRelationshipType(UPDATED_VALID_PARTY_RELATIONSHIP_TYPE)
            .validFromPartyRelationshipType(UPDATED_VALID_FROM_PARTY_RELATIONSHIP_TYPE)
            .partyInvitationRoleAssociation(UPDATED_PARTY_INVITATION_ROLE_ASSOCIATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return roleType;
    }

    @BeforeEach
    public void initTest() {
        roleType = createEntity(em);
    }

    @Test
    @Transactional
    void createRoleType() throws Exception {
        int databaseSizeBeforeCreate = roleTypeRepository.findAll().size();
        // Create the RoleType
        restRoleTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleType)))
            .andExpect(status().isCreated());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RoleType testRoleType = roleTypeList.get(roleTypeList.size() - 1);
        assertThat(testRoleType.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testRoleType.getHasTable()).isEqualTo(DEFAULT_HAS_TABLE);
        assertThat(testRoleType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRoleType.getChildRoleType()).isEqualTo(DEFAULT_CHILD_ROLE_TYPE);
        assertThat(testRoleType.getValidPartyRelationshipType()).isEqualTo(DEFAULT_VALID_PARTY_RELATIONSHIP_TYPE);
        assertThat(testRoleType.getValidFromPartyRelationshipType()).isEqualTo(DEFAULT_VALID_FROM_PARTY_RELATIONSHIP_TYPE);
        assertThat(testRoleType.getPartyInvitationRoleAssociation()).isEqualTo(DEFAULT_PARTY_INVITATION_ROLE_ASSOCIATION);
        assertThat(testRoleType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRoleType.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRoleType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRoleType.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createRoleTypeWithExistingId() throws Exception {
        // Create the RoleType with an existing ID
        roleType.setId(1L);

        int databaseSizeBeforeCreate = roleTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleType)))
            .andExpect(status().isBadRequest());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoleTypes() throws Exception {
        // Initialize the database
        roleTypeRepository.saveAndFlush(roleType);

        // Get all the roleTypeList
        restRoleTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].hasTable").value(hasItem(DEFAULT_HAS_TABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].childRoleType").value(hasItem(DEFAULT_CHILD_ROLE_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].validPartyRelationshipType").value(hasItem(DEFAULT_VALID_PARTY_RELATIONSHIP_TYPE.intValue())))
            .andExpect(
                jsonPath("$.[*].validFromPartyRelationshipType").value(hasItem(DEFAULT_VALID_FROM_PARTY_RELATIONSHIP_TYPE.intValue()))
            )
            .andExpect(jsonPath("$.[*].partyInvitationRoleAssociation").value(hasItem(DEFAULT_PARTY_INVITATION_ROLE_ASSOCIATION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getRoleType() throws Exception {
        // Initialize the database
        roleTypeRepository.saveAndFlush(roleType);

        // Get the roleType
        restRoleTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, roleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roleType.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.hasTable").value(DEFAULT_HAS_TABLE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.childRoleType").value(DEFAULT_CHILD_ROLE_TYPE.intValue()))
            .andExpect(jsonPath("$.validPartyRelationshipType").value(DEFAULT_VALID_PARTY_RELATIONSHIP_TYPE.intValue()))
            .andExpect(jsonPath("$.validFromPartyRelationshipType").value(DEFAULT_VALID_FROM_PARTY_RELATIONSHIP_TYPE.intValue()))
            .andExpect(jsonPath("$.partyInvitationRoleAssociation").value(DEFAULT_PARTY_INVITATION_ROLE_ASSOCIATION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingRoleType() throws Exception {
        // Get the roleType
        restRoleTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoleType() throws Exception {
        // Initialize the database
        roleTypeRepository.saveAndFlush(roleType);

        int databaseSizeBeforeUpdate = roleTypeRepository.findAll().size();

        // Update the roleType
        RoleType updatedRoleType = roleTypeRepository.findById(roleType.getId()).get();
        // Disconnect from session so that the updates on updatedRoleType are not directly saved in db
        em.detach(updatedRoleType);
        updatedRoleType
            .gUID(UPDATED_G_UID)
            .hasTable(UPDATED_HAS_TABLE)
            .description(UPDATED_DESCRIPTION)
            .childRoleType(UPDATED_CHILD_ROLE_TYPE)
            .validPartyRelationshipType(UPDATED_VALID_PARTY_RELATIONSHIP_TYPE)
            .validFromPartyRelationshipType(UPDATED_VALID_FROM_PARTY_RELATIONSHIP_TYPE)
            .partyInvitationRoleAssociation(UPDATED_PARTY_INVITATION_ROLE_ASSOCIATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restRoleTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoleType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoleType))
            )
            .andExpect(status().isOk());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeUpdate);
        RoleType testRoleType = roleTypeList.get(roleTypeList.size() - 1);
        assertThat(testRoleType.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testRoleType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testRoleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoleType.getChildRoleType()).isEqualTo(UPDATED_CHILD_ROLE_TYPE);
        assertThat(testRoleType.getValidPartyRelationshipType()).isEqualTo(UPDATED_VALID_PARTY_RELATIONSHIP_TYPE);
        assertThat(testRoleType.getValidFromPartyRelationshipType()).isEqualTo(UPDATED_VALID_FROM_PARTY_RELATIONSHIP_TYPE);
        assertThat(testRoleType.getPartyInvitationRoleAssociation()).isEqualTo(UPDATED_PARTY_INVITATION_ROLE_ASSOCIATION);
        assertThat(testRoleType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoleType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRoleType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRoleType.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingRoleType() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRepository.findAll().size();
        roleType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roleType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoleType() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRepository.findAll().size();
        roleType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoleType() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRepository.findAll().size();
        roleType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoleTypeWithPatch() throws Exception {
        // Initialize the database
        roleTypeRepository.saveAndFlush(roleType);

        int databaseSizeBeforeUpdate = roleTypeRepository.findAll().size();

        // Update the roleType using partial update
        RoleType partialUpdatedRoleType = new RoleType();
        partialUpdatedRoleType.setId(roleType.getId());

        partialUpdatedRoleType.gUID(UPDATED_G_UID).hasTable(UPDATED_HAS_TABLE).updatedOn(UPDATED_UPDATED_ON);

        restRoleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleType))
            )
            .andExpect(status().isOk());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeUpdate);
        RoleType testRoleType = roleTypeList.get(roleTypeList.size() - 1);
        assertThat(testRoleType.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testRoleType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testRoleType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRoleType.getChildRoleType()).isEqualTo(DEFAULT_CHILD_ROLE_TYPE);
        assertThat(testRoleType.getValidPartyRelationshipType()).isEqualTo(DEFAULT_VALID_PARTY_RELATIONSHIP_TYPE);
        assertThat(testRoleType.getValidFromPartyRelationshipType()).isEqualTo(DEFAULT_VALID_FROM_PARTY_RELATIONSHIP_TYPE);
        assertThat(testRoleType.getPartyInvitationRoleAssociation()).isEqualTo(DEFAULT_PARTY_INVITATION_ROLE_ASSOCIATION);
        assertThat(testRoleType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRoleType.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRoleType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRoleType.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateRoleTypeWithPatch() throws Exception {
        // Initialize the database
        roleTypeRepository.saveAndFlush(roleType);

        int databaseSizeBeforeUpdate = roleTypeRepository.findAll().size();

        // Update the roleType using partial update
        RoleType partialUpdatedRoleType = new RoleType();
        partialUpdatedRoleType.setId(roleType.getId());

        partialUpdatedRoleType
            .gUID(UPDATED_G_UID)
            .hasTable(UPDATED_HAS_TABLE)
            .description(UPDATED_DESCRIPTION)
            .childRoleType(UPDATED_CHILD_ROLE_TYPE)
            .validPartyRelationshipType(UPDATED_VALID_PARTY_RELATIONSHIP_TYPE)
            .validFromPartyRelationshipType(UPDATED_VALID_FROM_PARTY_RELATIONSHIP_TYPE)
            .partyInvitationRoleAssociation(UPDATED_PARTY_INVITATION_ROLE_ASSOCIATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restRoleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleType))
            )
            .andExpect(status().isOk());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeUpdate);
        RoleType testRoleType = roleTypeList.get(roleTypeList.size() - 1);
        assertThat(testRoleType.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testRoleType.getHasTable()).isEqualTo(UPDATED_HAS_TABLE);
        assertThat(testRoleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoleType.getChildRoleType()).isEqualTo(UPDATED_CHILD_ROLE_TYPE);
        assertThat(testRoleType.getValidPartyRelationshipType()).isEqualTo(UPDATED_VALID_PARTY_RELATIONSHIP_TYPE);
        assertThat(testRoleType.getValidFromPartyRelationshipType()).isEqualTo(UPDATED_VALID_FROM_PARTY_RELATIONSHIP_TYPE);
        assertThat(testRoleType.getPartyInvitationRoleAssociation()).isEqualTo(UPDATED_PARTY_INVITATION_ROLE_ASSOCIATION);
        assertThat(testRoleType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoleType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRoleType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRoleType.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingRoleType() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRepository.findAll().size();
        roleType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roleType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoleType() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRepository.findAll().size();
        roleType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoleType() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRepository.findAll().size();
        roleType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roleType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoleType() throws Exception {
        // Initialize the database
        roleTypeRepository.saveAndFlush(roleType);

        int databaseSizeBeforeDelete = roleTypeRepository.findAll().size();

        // Delete the roleType
        restRoleTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, roleType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
