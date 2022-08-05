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
import org.jhipster.blog.domain.SecurityPermission;
import org.jhipster.blog.repository.SecurityPermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SecurityPermissionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SecurityPermissionResourceIT {

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

    private static final String ENTITY_API_URL = "/api/security-permissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SecurityPermissionRepository securityPermissionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSecurityPermissionMockMvc;

    private SecurityPermission securityPermission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecurityPermission createEntity(EntityManager em) {
        SecurityPermission securityPermission = new SecurityPermission()
            .gUID(DEFAULT_G_UID)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return securityPermission;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecurityPermission createUpdatedEntity(EntityManager em) {
        SecurityPermission securityPermission = new SecurityPermission()
            .gUID(UPDATED_G_UID)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return securityPermission;
    }

    @BeforeEach
    public void initTest() {
        securityPermission = createEntity(em);
    }

    @Test
    @Transactional
    void createSecurityPermission() throws Exception {
        int databaseSizeBeforeCreate = securityPermissionRepository.findAll().size();
        // Create the SecurityPermission
        restSecurityPermissionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(securityPermission))
            )
            .andExpect(status().isCreated());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeCreate + 1);
        SecurityPermission testSecurityPermission = securityPermissionList.get(securityPermissionList.size() - 1);
        assertThat(testSecurityPermission.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testSecurityPermission.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSecurityPermission.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSecurityPermission.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSecurityPermission.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSecurityPermission.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createSecurityPermissionWithExistingId() throws Exception {
        // Create the SecurityPermission with an existing ID
        securityPermission.setId(1L);

        int databaseSizeBeforeCreate = securityPermissionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecurityPermissionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(securityPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSecurityPermissions() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList
        restSecurityPermissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(securityPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getSecurityPermission() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get the securityPermission
        restSecurityPermissionMockMvc
            .perform(get(ENTITY_API_URL_ID, securityPermission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(securityPermission.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingSecurityPermission() throws Exception {
        // Get the securityPermission
        restSecurityPermissionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSecurityPermission() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        int databaseSizeBeforeUpdate = securityPermissionRepository.findAll().size();

        // Update the securityPermission
        SecurityPermission updatedSecurityPermission = securityPermissionRepository.findById(securityPermission.getId()).get();
        // Disconnect from session so that the updates on updatedSecurityPermission are not directly saved in db
        em.detach(updatedSecurityPermission);
        updatedSecurityPermission
            .gUID(UPDATED_G_UID)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSecurityPermissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSecurityPermission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSecurityPermission))
            )
            .andExpect(status().isOk());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeUpdate);
        SecurityPermission testSecurityPermission = securityPermissionList.get(securityPermissionList.size() - 1);
        assertThat(testSecurityPermission.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSecurityPermission.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSecurityPermission.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSecurityPermission.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSecurityPermission.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSecurityPermission.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingSecurityPermission() throws Exception {
        int databaseSizeBeforeUpdate = securityPermissionRepository.findAll().size();
        securityPermission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecurityPermissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, securityPermission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(securityPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSecurityPermission() throws Exception {
        int databaseSizeBeforeUpdate = securityPermissionRepository.findAll().size();
        securityPermission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecurityPermissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(securityPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSecurityPermission() throws Exception {
        int databaseSizeBeforeUpdate = securityPermissionRepository.findAll().size();
        securityPermission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecurityPermissionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(securityPermission))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSecurityPermissionWithPatch() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        int databaseSizeBeforeUpdate = securityPermissionRepository.findAll().size();

        // Update the securityPermission using partial update
        SecurityPermission partialUpdatedSecurityPermission = new SecurityPermission();
        partialUpdatedSecurityPermission.setId(securityPermission.getId());

        partialUpdatedSecurityPermission.createdBy(UPDATED_CREATED_BY).updatedBy(UPDATED_UPDATED_BY).updatedOn(UPDATED_UPDATED_ON);

        restSecurityPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecurityPermission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSecurityPermission))
            )
            .andExpect(status().isOk());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeUpdate);
        SecurityPermission testSecurityPermission = securityPermissionList.get(securityPermissionList.size() - 1);
        assertThat(testSecurityPermission.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testSecurityPermission.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSecurityPermission.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSecurityPermission.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSecurityPermission.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSecurityPermission.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateSecurityPermissionWithPatch() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        int databaseSizeBeforeUpdate = securityPermissionRepository.findAll().size();

        // Update the securityPermission using partial update
        SecurityPermission partialUpdatedSecurityPermission = new SecurityPermission();
        partialUpdatedSecurityPermission.setId(securityPermission.getId());

        partialUpdatedSecurityPermission
            .gUID(UPDATED_G_UID)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSecurityPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecurityPermission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSecurityPermission))
            )
            .andExpect(status().isOk());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeUpdate);
        SecurityPermission testSecurityPermission = securityPermissionList.get(securityPermissionList.size() - 1);
        assertThat(testSecurityPermission.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testSecurityPermission.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSecurityPermission.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSecurityPermission.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSecurityPermission.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSecurityPermission.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingSecurityPermission() throws Exception {
        int databaseSizeBeforeUpdate = securityPermissionRepository.findAll().size();
        securityPermission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecurityPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, securityPermission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(securityPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSecurityPermission() throws Exception {
        int databaseSizeBeforeUpdate = securityPermissionRepository.findAll().size();
        securityPermission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecurityPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(securityPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSecurityPermission() throws Exception {
        int databaseSizeBeforeUpdate = securityPermissionRepository.findAll().size();
        securityPermission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecurityPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(securityPermission))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSecurityPermission() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        int databaseSizeBeforeDelete = securityPermissionRepository.findAll().size();

        // Delete the securityPermission
        restSecurityPermissionMockMvc
            .perform(delete(ENTITY_API_URL_ID, securityPermission.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
