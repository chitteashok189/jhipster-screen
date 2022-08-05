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
import org.jhipster.blog.domain.SecurityGroupPermission;
import org.jhipster.blog.repository.SecurityGroupPermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SecurityGroupPermissionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SecurityGroupPermissionResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/security-group-permissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SecurityGroupPermissionRepository securityGroupPermissionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSecurityGroupPermissionMockMvc;

    private SecurityGroupPermission securityGroupPermission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecurityGroupPermission createEntity(EntityManager em) {
        SecurityGroupPermission securityGroupPermission = new SecurityGroupPermission()
            .gUID(DEFAULT_G_UID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return securityGroupPermission;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecurityGroupPermission createUpdatedEntity(EntityManager em) {
        SecurityGroupPermission securityGroupPermission = new SecurityGroupPermission()
            .gUID(UPDATED_G_UID)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return securityGroupPermission;
    }

    @BeforeEach
    public void initTest() {
        securityGroupPermission = createEntity(em);
    }

    @Test
    @Transactional
    void createSecurityGroupPermission() throws Exception {
        int databaseSizeBeforeCreate = securityGroupPermissionRepository.findAll().size();
        // Create the SecurityGroupPermission
        restSecurityGroupPermissionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(securityGroupPermission))
            )
            .andExpect(status().isCreated());

        // Validate the SecurityGroupPermission in the database
        List<SecurityGroupPermission> securityGroupPermissionList = securityGroupPermissionRepository.findAll();
        assertThat(securityGroupPermissionList).hasSize(databaseSizeBeforeCreate + 1);
        SecurityGroupPermission testSecurityGroupPermission = securityGroupPermissionList.get(securityGroupPermissionList.size() - 1);
        assertThat(testSecurityGroupPermission.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testSecurityGroupPermission.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSecurityGroupPermission.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSecurityGroupPermission.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSecurityGroupPermission.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createSecurityGroupPermissionWithExistingId() throws Exception {
        // Create the SecurityGroupPermission with an existing ID
        securityGroupPermission.setId(1L);

        int databaseSizeBeforeCreate = securityGroupPermissionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecurityGroupPermissionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(securityGroupPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityGroupPermission in the database
        List<SecurityGroupPermission> securityGroupPermissionList = securityGroupPermissionRepository.findAll();
        assertThat(securityGroupPermissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSecurityGroupPermissions() throws Exception {
        // Initialize the database
        securityGroupPermissionRepository.saveAndFlush(securityGroupPermission);

        // Get all the securityGroupPermissionList
        restSecurityGroupPermissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(securityGroupPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getSecurityGroupPermission() throws Exception {
        // Initialize the database
        securityGroupPermissionRepository.saveAndFlush(securityGroupPermission);

        // Get the securityGroupPermission
        restSecurityGroupPermissionMockMvc
            .perform(get(ENTITY_API_URL_ID, securityGroupPermission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(securityGroupPermission.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingSecurityGroupPermission() throws Exception {
        // Get the securityGroupPermission
        restSecurityGroupPermissionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSecurityGroupPermission() throws Exception {
        // Initialize the database
        securityGroupPermissionRepository.saveAndFlush(securityGroupPermission);

        int databaseSizeBeforeUpdate = securityGroupPermissionRepository.findAll().size();

        // Update the securityGroupPermission
        SecurityGroupPermission updatedSecurityGroupPermission = securityGroupPermissionRepository
            .findById(securityGroupPermission.getId())
            .get();
        // Disconnect from session so that the updates on updatedSecurityGroupPermission are not directly saved in db
        em.detach(updatedSecurityGroupPermission);
        updatedSecurityGroupPermission
            .gUID(UPDATED_G_UID)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSecurityGroupPermissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSecurityGroupPermission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSecurityGroupPermission))
            )
            .andExpect(status().isOk());

        // Validate the SecurityGroupPermission in the database
        List<SecurityGroupPermission> securityGroupPermissionList = securityGroupPermissionRepository.findAll();
        assertThat(securityGroupPermissionList).hasSize(databaseSizeBeforeUpdate);
        SecurityGroupPermission testSecurityGroupPermission = securityGroupPermissionList.get(securityGroupPermissionList.size() - 1);
        assertThat(testSecurityGroupPermission.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSecurityGroupPermission.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSecurityGroupPermission.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSecurityGroupPermission.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSecurityGroupPermission.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingSecurityGroupPermission() throws Exception {
        int databaseSizeBeforeUpdate = securityGroupPermissionRepository.findAll().size();
        securityGroupPermission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecurityGroupPermissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, securityGroupPermission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(securityGroupPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityGroupPermission in the database
        List<SecurityGroupPermission> securityGroupPermissionList = securityGroupPermissionRepository.findAll();
        assertThat(securityGroupPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSecurityGroupPermission() throws Exception {
        int databaseSizeBeforeUpdate = securityGroupPermissionRepository.findAll().size();
        securityGroupPermission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecurityGroupPermissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(securityGroupPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityGroupPermission in the database
        List<SecurityGroupPermission> securityGroupPermissionList = securityGroupPermissionRepository.findAll();
        assertThat(securityGroupPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSecurityGroupPermission() throws Exception {
        int databaseSizeBeforeUpdate = securityGroupPermissionRepository.findAll().size();
        securityGroupPermission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecurityGroupPermissionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(securityGroupPermission))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SecurityGroupPermission in the database
        List<SecurityGroupPermission> securityGroupPermissionList = securityGroupPermissionRepository.findAll();
        assertThat(securityGroupPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSecurityGroupPermissionWithPatch() throws Exception {
        // Initialize the database
        securityGroupPermissionRepository.saveAndFlush(securityGroupPermission);

        int databaseSizeBeforeUpdate = securityGroupPermissionRepository.findAll().size();

        // Update the securityGroupPermission using partial update
        SecurityGroupPermission partialUpdatedSecurityGroupPermission = new SecurityGroupPermission();
        partialUpdatedSecurityGroupPermission.setId(securityGroupPermission.getId());

        partialUpdatedSecurityGroupPermission.gUID(UPDATED_G_UID).createdBy(UPDATED_CREATED_BY).updatedOn(UPDATED_UPDATED_ON);

        restSecurityGroupPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecurityGroupPermission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSecurityGroupPermission))
            )
            .andExpect(status().isOk());

        // Validate the SecurityGroupPermission in the database
        List<SecurityGroupPermission> securityGroupPermissionList = securityGroupPermissionRepository.findAll();
        assertThat(securityGroupPermissionList).hasSize(databaseSizeBeforeUpdate);
        SecurityGroupPermission testSecurityGroupPermission = securityGroupPermissionList.get(securityGroupPermissionList.size() - 1);
        assertThat(testSecurityGroupPermission.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSecurityGroupPermission.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSecurityGroupPermission.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSecurityGroupPermission.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSecurityGroupPermission.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateSecurityGroupPermissionWithPatch() throws Exception {
        // Initialize the database
        securityGroupPermissionRepository.saveAndFlush(securityGroupPermission);

        int databaseSizeBeforeUpdate = securityGroupPermissionRepository.findAll().size();

        // Update the securityGroupPermission using partial update
        SecurityGroupPermission partialUpdatedSecurityGroupPermission = new SecurityGroupPermission();
        partialUpdatedSecurityGroupPermission.setId(securityGroupPermission.getId());

        partialUpdatedSecurityGroupPermission
            .gUID(UPDATED_G_UID)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSecurityGroupPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecurityGroupPermission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSecurityGroupPermission))
            )
            .andExpect(status().isOk());

        // Validate the SecurityGroupPermission in the database
        List<SecurityGroupPermission> securityGroupPermissionList = securityGroupPermissionRepository.findAll();
        assertThat(securityGroupPermissionList).hasSize(databaseSizeBeforeUpdate);
        SecurityGroupPermission testSecurityGroupPermission = securityGroupPermissionList.get(securityGroupPermissionList.size() - 1);
        assertThat(testSecurityGroupPermission.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSecurityGroupPermission.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSecurityGroupPermission.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSecurityGroupPermission.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSecurityGroupPermission.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingSecurityGroupPermission() throws Exception {
        int databaseSizeBeforeUpdate = securityGroupPermissionRepository.findAll().size();
        securityGroupPermission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecurityGroupPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, securityGroupPermission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(securityGroupPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityGroupPermission in the database
        List<SecurityGroupPermission> securityGroupPermissionList = securityGroupPermissionRepository.findAll();
        assertThat(securityGroupPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSecurityGroupPermission() throws Exception {
        int databaseSizeBeforeUpdate = securityGroupPermissionRepository.findAll().size();
        securityGroupPermission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecurityGroupPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(securityGroupPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityGroupPermission in the database
        List<SecurityGroupPermission> securityGroupPermissionList = securityGroupPermissionRepository.findAll();
        assertThat(securityGroupPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSecurityGroupPermission() throws Exception {
        int databaseSizeBeforeUpdate = securityGroupPermissionRepository.findAll().size();
        securityGroupPermission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecurityGroupPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(securityGroupPermission))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SecurityGroupPermission in the database
        List<SecurityGroupPermission> securityGroupPermissionList = securityGroupPermissionRepository.findAll();
        assertThat(securityGroupPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSecurityGroupPermission() throws Exception {
        // Initialize the database
        securityGroupPermissionRepository.saveAndFlush(securityGroupPermission);

        int databaseSizeBeforeDelete = securityGroupPermissionRepository.findAll().size();

        // Delete the securityGroupPermission
        restSecurityGroupPermissionMockMvc
            .perform(delete(ENTITY_API_URL_ID, securityGroupPermission.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SecurityGroupPermission> securityGroupPermissionList = securityGroupPermissionRepository.findAll();
        assertThat(securityGroupPermissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
