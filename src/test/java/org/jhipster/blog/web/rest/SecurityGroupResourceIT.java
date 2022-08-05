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
import org.jhipster.blog.domain.SecurityGroup;
import org.jhipster.blog.repository.SecurityGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SecurityGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SecurityGroupResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

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

    private static final String ENTITY_API_URL = "/api/security-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SecurityGroupRepository securityGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSecurityGroupMockMvc;

    private SecurityGroup securityGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecurityGroup createEntity(EntityManager em) {
        SecurityGroup securityGroup = new SecurityGroup()
            .gUID(DEFAULT_G_UID)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return securityGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecurityGroup createUpdatedEntity(EntityManager em) {
        SecurityGroup securityGroup = new SecurityGroup()
            .gUID(UPDATED_G_UID)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return securityGroup;
    }

    @BeforeEach
    public void initTest() {
        securityGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createSecurityGroup() throws Exception {
        int databaseSizeBeforeCreate = securityGroupRepository.findAll().size();
        // Create the SecurityGroup
        restSecurityGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(securityGroup)))
            .andExpect(status().isCreated());

        // Validate the SecurityGroup in the database
        List<SecurityGroup> securityGroupList = securityGroupRepository.findAll();
        assertThat(securityGroupList).hasSize(databaseSizeBeforeCreate + 1);
        SecurityGroup testSecurityGroup = securityGroupList.get(securityGroupList.size() - 1);
        assertThat(testSecurityGroup.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testSecurityGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSecurityGroup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSecurityGroup.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSecurityGroup.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSecurityGroup.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createSecurityGroupWithExistingId() throws Exception {
        // Create the SecurityGroup with an existing ID
        securityGroup.setId(1L);

        int databaseSizeBeforeCreate = securityGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecurityGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(securityGroup)))
            .andExpect(status().isBadRequest());

        // Validate the SecurityGroup in the database
        List<SecurityGroup> securityGroupList = securityGroupRepository.findAll();
        assertThat(securityGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSecurityGroups() throws Exception {
        // Initialize the database
        securityGroupRepository.saveAndFlush(securityGroup);

        // Get all the securityGroupList
        restSecurityGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(securityGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getSecurityGroup() throws Exception {
        // Initialize the database
        securityGroupRepository.saveAndFlush(securityGroup);

        // Get the securityGroup
        restSecurityGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, securityGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(securityGroup.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingSecurityGroup() throws Exception {
        // Get the securityGroup
        restSecurityGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSecurityGroup() throws Exception {
        // Initialize the database
        securityGroupRepository.saveAndFlush(securityGroup);

        int databaseSizeBeforeUpdate = securityGroupRepository.findAll().size();

        // Update the securityGroup
        SecurityGroup updatedSecurityGroup = securityGroupRepository.findById(securityGroup.getId()).get();
        // Disconnect from session so that the updates on updatedSecurityGroup are not directly saved in db
        em.detach(updatedSecurityGroup);
        updatedSecurityGroup
            .gUID(UPDATED_G_UID)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSecurityGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSecurityGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSecurityGroup))
            )
            .andExpect(status().isOk());

        // Validate the SecurityGroup in the database
        List<SecurityGroup> securityGroupList = securityGroupRepository.findAll();
        assertThat(securityGroupList).hasSize(databaseSizeBeforeUpdate);
        SecurityGroup testSecurityGroup = securityGroupList.get(securityGroupList.size() - 1);
        assertThat(testSecurityGroup.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSecurityGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSecurityGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSecurityGroup.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSecurityGroup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSecurityGroup.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingSecurityGroup() throws Exception {
        int databaseSizeBeforeUpdate = securityGroupRepository.findAll().size();
        securityGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecurityGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, securityGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(securityGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityGroup in the database
        List<SecurityGroup> securityGroupList = securityGroupRepository.findAll();
        assertThat(securityGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSecurityGroup() throws Exception {
        int databaseSizeBeforeUpdate = securityGroupRepository.findAll().size();
        securityGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecurityGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(securityGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityGroup in the database
        List<SecurityGroup> securityGroupList = securityGroupRepository.findAll();
        assertThat(securityGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSecurityGroup() throws Exception {
        int databaseSizeBeforeUpdate = securityGroupRepository.findAll().size();
        securityGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecurityGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(securityGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SecurityGroup in the database
        List<SecurityGroup> securityGroupList = securityGroupRepository.findAll();
        assertThat(securityGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSecurityGroupWithPatch() throws Exception {
        // Initialize the database
        securityGroupRepository.saveAndFlush(securityGroup);

        int databaseSizeBeforeUpdate = securityGroupRepository.findAll().size();

        // Update the securityGroup using partial update
        SecurityGroup partialUpdatedSecurityGroup = new SecurityGroup();
        partialUpdatedSecurityGroup.setId(securityGroup.getId());

        partialUpdatedSecurityGroup.description(UPDATED_DESCRIPTION).createdOn(UPDATED_CREATED_ON).updatedOn(UPDATED_UPDATED_ON);

        restSecurityGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecurityGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSecurityGroup))
            )
            .andExpect(status().isOk());

        // Validate the SecurityGroup in the database
        List<SecurityGroup> securityGroupList = securityGroupRepository.findAll();
        assertThat(securityGroupList).hasSize(databaseSizeBeforeUpdate);
        SecurityGroup testSecurityGroup = securityGroupList.get(securityGroupList.size() - 1);
        assertThat(testSecurityGroup.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testSecurityGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSecurityGroup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSecurityGroup.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSecurityGroup.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSecurityGroup.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateSecurityGroupWithPatch() throws Exception {
        // Initialize the database
        securityGroupRepository.saveAndFlush(securityGroup);

        int databaseSizeBeforeUpdate = securityGroupRepository.findAll().size();

        // Update the securityGroup using partial update
        SecurityGroup partialUpdatedSecurityGroup = new SecurityGroup();
        partialUpdatedSecurityGroup.setId(securityGroup.getId());

        partialUpdatedSecurityGroup
            .gUID(UPDATED_G_UID)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSecurityGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecurityGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSecurityGroup))
            )
            .andExpect(status().isOk());

        // Validate the SecurityGroup in the database
        List<SecurityGroup> securityGroupList = securityGroupRepository.findAll();
        assertThat(securityGroupList).hasSize(databaseSizeBeforeUpdate);
        SecurityGroup testSecurityGroup = securityGroupList.get(securityGroupList.size() - 1);
        assertThat(testSecurityGroup.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSecurityGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSecurityGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSecurityGroup.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSecurityGroup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSecurityGroup.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingSecurityGroup() throws Exception {
        int databaseSizeBeforeUpdate = securityGroupRepository.findAll().size();
        securityGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecurityGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, securityGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(securityGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityGroup in the database
        List<SecurityGroup> securityGroupList = securityGroupRepository.findAll();
        assertThat(securityGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSecurityGroup() throws Exception {
        int databaseSizeBeforeUpdate = securityGroupRepository.findAll().size();
        securityGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecurityGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(securityGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityGroup in the database
        List<SecurityGroup> securityGroupList = securityGroupRepository.findAll();
        assertThat(securityGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSecurityGroup() throws Exception {
        int databaseSizeBeforeUpdate = securityGroupRepository.findAll().size();
        securityGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecurityGroupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(securityGroup))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SecurityGroup in the database
        List<SecurityGroup> securityGroupList = securityGroupRepository.findAll();
        assertThat(securityGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSecurityGroup() throws Exception {
        // Initialize the database
        securityGroupRepository.saveAndFlush(securityGroup);

        int databaseSizeBeforeDelete = securityGroupRepository.findAll().size();

        // Delete the securityGroup
        restSecurityGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, securityGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SecurityGroup> securityGroupList = securityGroupRepository.findAll();
        assertThat(securityGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
