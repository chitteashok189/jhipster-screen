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
import org.jhipster.blog.domain.RoleTypeAttribute;
import org.jhipster.blog.repository.RoleTypeAttributeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RoleTypeAttributeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoleTypeAttributeResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/role-type-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoleTypeAttributeRepository roleTypeAttributeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoleTypeAttributeMockMvc;

    private RoleTypeAttribute roleTypeAttribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleTypeAttribute createEntity(EntityManager em) {
        RoleTypeAttribute roleTypeAttribute = new RoleTypeAttribute()
            .gUID(DEFAULT_G_UID)
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return roleTypeAttribute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleTypeAttribute createUpdatedEntity(EntityManager em) {
        RoleTypeAttribute roleTypeAttribute = new RoleTypeAttribute()
            .gUID(UPDATED_G_UID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return roleTypeAttribute;
    }

    @BeforeEach
    public void initTest() {
        roleTypeAttribute = createEntity(em);
    }

    @Test
    @Transactional
    void createRoleTypeAttribute() throws Exception {
        int databaseSizeBeforeCreate = roleTypeAttributeRepository.findAll().size();
        // Create the RoleTypeAttribute
        restRoleTypeAttributeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleTypeAttribute))
            )
            .andExpect(status().isCreated());

        // Validate the RoleTypeAttribute in the database
        List<RoleTypeAttribute> roleTypeAttributeList = roleTypeAttributeRepository.findAll();
        assertThat(roleTypeAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        RoleTypeAttribute testRoleTypeAttribute = roleTypeAttributeList.get(roleTypeAttributeList.size() - 1);
        assertThat(testRoleTypeAttribute.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testRoleTypeAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testRoleTypeAttribute.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRoleTypeAttribute.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRoleTypeAttribute.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRoleTypeAttribute.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRoleTypeAttribute.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createRoleTypeAttributeWithExistingId() throws Exception {
        // Create the RoleTypeAttribute with an existing ID
        roleTypeAttribute.setId(1L);

        int databaseSizeBeforeCreate = roleTypeAttributeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleTypeAttributeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleTypeAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeAttribute in the database
        List<RoleTypeAttribute> roleTypeAttributeList = roleTypeAttributeRepository.findAll();
        assertThat(roleTypeAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoleTypeAttributes() throws Exception {
        // Initialize the database
        roleTypeAttributeRepository.saveAndFlush(roleTypeAttribute);

        // Get all the roleTypeAttributeList
        restRoleTypeAttributeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleTypeAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getRoleTypeAttribute() throws Exception {
        // Initialize the database
        roleTypeAttributeRepository.saveAndFlush(roleTypeAttribute);

        // Get the roleTypeAttribute
        restRoleTypeAttributeMockMvc
            .perform(get(ENTITY_API_URL_ID, roleTypeAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roleTypeAttribute.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingRoleTypeAttribute() throws Exception {
        // Get the roleTypeAttribute
        restRoleTypeAttributeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoleTypeAttribute() throws Exception {
        // Initialize the database
        roleTypeAttributeRepository.saveAndFlush(roleTypeAttribute);

        int databaseSizeBeforeUpdate = roleTypeAttributeRepository.findAll().size();

        // Update the roleTypeAttribute
        RoleTypeAttribute updatedRoleTypeAttribute = roleTypeAttributeRepository.findById(roleTypeAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedRoleTypeAttribute are not directly saved in db
        em.detach(updatedRoleTypeAttribute);
        updatedRoleTypeAttribute
            .gUID(UPDATED_G_UID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restRoleTypeAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoleTypeAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoleTypeAttribute))
            )
            .andExpect(status().isOk());

        // Validate the RoleTypeAttribute in the database
        List<RoleTypeAttribute> roleTypeAttributeList = roleTypeAttributeRepository.findAll();
        assertThat(roleTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
        RoleTypeAttribute testRoleTypeAttribute = roleTypeAttributeList.get(roleTypeAttributeList.size() - 1);
        assertThat(testRoleTypeAttribute.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testRoleTypeAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testRoleTypeAttribute.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoleTypeAttribute.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoleTypeAttribute.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRoleTypeAttribute.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRoleTypeAttribute.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingRoleTypeAttribute() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeAttributeRepository.findAll().size();
        roleTypeAttribute.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleTypeAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roleTypeAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeAttribute in the database
        List<RoleTypeAttribute> roleTypeAttributeList = roleTypeAttributeRepository.findAll();
        assertThat(roleTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoleTypeAttribute() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeAttributeRepository.findAll().size();
        roleTypeAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeAttribute in the database
        List<RoleTypeAttribute> roleTypeAttributeList = roleTypeAttributeRepository.findAll();
        assertThat(roleTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoleTypeAttribute() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeAttributeRepository.findAll().size();
        roleTypeAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeAttributeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleTypeAttribute))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleTypeAttribute in the database
        List<RoleTypeAttribute> roleTypeAttributeList = roleTypeAttributeRepository.findAll();
        assertThat(roleTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoleTypeAttributeWithPatch() throws Exception {
        // Initialize the database
        roleTypeAttributeRepository.saveAndFlush(roleTypeAttribute);

        int databaseSizeBeforeUpdate = roleTypeAttributeRepository.findAll().size();

        // Update the roleTypeAttribute using partial update
        RoleTypeAttribute partialUpdatedRoleTypeAttribute = new RoleTypeAttribute();
        partialUpdatedRoleTypeAttribute.setId(roleTypeAttribute.getId());

        partialUpdatedRoleTypeAttribute.attributeName(UPDATED_ATTRIBUTE_NAME).createdOn(UPDATED_CREATED_ON).updatedBy(UPDATED_UPDATED_BY);

        restRoleTypeAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleTypeAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleTypeAttribute))
            )
            .andExpect(status().isOk());

        // Validate the RoleTypeAttribute in the database
        List<RoleTypeAttribute> roleTypeAttributeList = roleTypeAttributeRepository.findAll();
        assertThat(roleTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
        RoleTypeAttribute testRoleTypeAttribute = roleTypeAttributeList.get(roleTypeAttributeList.size() - 1);
        assertThat(testRoleTypeAttribute.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testRoleTypeAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testRoleTypeAttribute.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRoleTypeAttribute.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRoleTypeAttribute.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRoleTypeAttribute.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRoleTypeAttribute.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateRoleTypeAttributeWithPatch() throws Exception {
        // Initialize the database
        roleTypeAttributeRepository.saveAndFlush(roleTypeAttribute);

        int databaseSizeBeforeUpdate = roleTypeAttributeRepository.findAll().size();

        // Update the roleTypeAttribute using partial update
        RoleTypeAttribute partialUpdatedRoleTypeAttribute = new RoleTypeAttribute();
        partialUpdatedRoleTypeAttribute.setId(roleTypeAttribute.getId());

        partialUpdatedRoleTypeAttribute
            .gUID(UPDATED_G_UID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restRoleTypeAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleTypeAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleTypeAttribute))
            )
            .andExpect(status().isOk());

        // Validate the RoleTypeAttribute in the database
        List<RoleTypeAttribute> roleTypeAttributeList = roleTypeAttributeRepository.findAll();
        assertThat(roleTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
        RoleTypeAttribute testRoleTypeAttribute = roleTypeAttributeList.get(roleTypeAttributeList.size() - 1);
        assertThat(testRoleTypeAttribute.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testRoleTypeAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testRoleTypeAttribute.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoleTypeAttribute.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoleTypeAttribute.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRoleTypeAttribute.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRoleTypeAttribute.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingRoleTypeAttribute() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeAttributeRepository.findAll().size();
        roleTypeAttribute.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleTypeAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roleTypeAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeAttribute in the database
        List<RoleTypeAttribute> roleTypeAttributeList = roleTypeAttributeRepository.findAll();
        assertThat(roleTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoleTypeAttribute() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeAttributeRepository.findAll().size();
        roleTypeAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeAttribute in the database
        List<RoleTypeAttribute> roleTypeAttributeList = roleTypeAttributeRepository.findAll();
        assertThat(roleTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoleTypeAttribute() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeAttributeRepository.findAll().size();
        roleTypeAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeAttribute))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleTypeAttribute in the database
        List<RoleTypeAttribute> roleTypeAttributeList = roleTypeAttributeRepository.findAll();
        assertThat(roleTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoleTypeAttribute() throws Exception {
        // Initialize the database
        roleTypeAttributeRepository.saveAndFlush(roleTypeAttribute);

        int databaseSizeBeforeDelete = roleTypeAttributeRepository.findAll().size();

        // Delete the roleTypeAttribute
        restRoleTypeAttributeMockMvc
            .perform(delete(ENTITY_API_URL_ID, roleTypeAttribute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoleTypeAttribute> roleTypeAttributeList = roleTypeAttributeRepository.findAll();
        assertThat(roleTypeAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
