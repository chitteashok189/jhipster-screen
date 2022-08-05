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
import org.jhipster.blog.domain.PartyTypeAttribute;
import org.jhipster.blog.repository.PartyTypeAttributeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartyTypeAttributeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartyTypeAttributeResourceIT {

    private static final Long DEFAULT_G_UID = 1L;
    private static final Long UPDATED_G_UID = 2L;

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/party-type-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartyTypeAttributeRepository partyTypeAttributeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyTypeAttributeMockMvc;

    private PartyTypeAttribute partyTypeAttribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyTypeAttribute createEntity(EntityManager em) {
        PartyTypeAttribute partyTypeAttribute = new PartyTypeAttribute()
            .gUID(DEFAULT_G_UID)
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return partyTypeAttribute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyTypeAttribute createUpdatedEntity(EntityManager em) {
        PartyTypeAttribute partyTypeAttribute = new PartyTypeAttribute()
            .gUID(UPDATED_G_UID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return partyTypeAttribute;
    }

    @BeforeEach
    public void initTest() {
        partyTypeAttribute = createEntity(em);
    }

    @Test
    @Transactional
    void createPartyTypeAttribute() throws Exception {
        int databaseSizeBeforeCreate = partyTypeAttributeRepository.findAll().size();
        // Create the PartyTypeAttribute
        restPartyTypeAttributeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyTypeAttribute))
            )
            .andExpect(status().isCreated());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        PartyTypeAttribute testPartyTypeAttribute = partyTypeAttributeList.get(partyTypeAttributeList.size() - 1);
        assertThat(testPartyTypeAttribute.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyTypeAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testPartyTypeAttribute.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyTypeAttribute.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyTypeAttribute.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyTypeAttribute.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPartyTypeAttributeWithExistingId() throws Exception {
        // Create the PartyTypeAttribute with an existing ID
        partyTypeAttribute.setId(1L);

        int databaseSizeBeforeCreate = partyTypeAttributeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyTypeAttributeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyTypeAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartyTypeAttributes() throws Exception {
        // Initialize the database
        partyTypeAttributeRepository.saveAndFlush(partyTypeAttribute);

        // Get all the partyTypeAttributeList
        restPartyTypeAttributeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyTypeAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPartyTypeAttribute() throws Exception {
        // Initialize the database
        partyTypeAttributeRepository.saveAndFlush(partyTypeAttribute);

        // Get the partyTypeAttribute
        restPartyTypeAttributeMockMvc
            .perform(get(ENTITY_API_URL_ID, partyTypeAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyTypeAttribute.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPartyTypeAttribute() throws Exception {
        // Get the partyTypeAttribute
        restPartyTypeAttributeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartyTypeAttribute() throws Exception {
        // Initialize the database
        partyTypeAttributeRepository.saveAndFlush(partyTypeAttribute);

        int databaseSizeBeforeUpdate = partyTypeAttributeRepository.findAll().size();

        // Update the partyTypeAttribute
        PartyTypeAttribute updatedPartyTypeAttribute = partyTypeAttributeRepository.findById(partyTypeAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedPartyTypeAttribute are not directly saved in db
        em.detach(updatedPartyTypeAttribute);
        updatedPartyTypeAttribute
            .gUID(UPDATED_G_UID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyTypeAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartyTypeAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartyTypeAttribute))
            )
            .andExpect(status().isOk());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
        PartyTypeAttribute testPartyTypeAttribute = partyTypeAttributeList.get(partyTypeAttributeList.size() - 1);
        assertThat(testPartyTypeAttribute.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyTypeAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testPartyTypeAttribute.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyTypeAttribute.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyTypeAttribute.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyTypeAttribute.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPartyTypeAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeAttributeRepository.findAll().size();
        partyTypeAttribute.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyTypeAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partyTypeAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyTypeAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartyTypeAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeAttributeRepository.findAll().size();
        partyTypeAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyTypeAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyTypeAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartyTypeAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeAttributeRepository.findAll().size();
        partyTypeAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyTypeAttributeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyTypeAttribute))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartyTypeAttributeWithPatch() throws Exception {
        // Initialize the database
        partyTypeAttributeRepository.saveAndFlush(partyTypeAttribute);

        int databaseSizeBeforeUpdate = partyTypeAttributeRepository.findAll().size();

        // Update the partyTypeAttribute using partial update
        PartyTypeAttribute partialUpdatedPartyTypeAttribute = new PartyTypeAttribute();
        partialUpdatedPartyTypeAttribute.setId(partyTypeAttribute.getId());

        partialUpdatedPartyTypeAttribute.createdBy(UPDATED_CREATED_BY).createdOn(UPDATED_CREATED_ON).updatedOn(UPDATED_UPDATED_ON);

        restPartyTypeAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyTypeAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyTypeAttribute))
            )
            .andExpect(status().isOk());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
        PartyTypeAttribute testPartyTypeAttribute = partyTypeAttributeList.get(partyTypeAttributeList.size() - 1);
        assertThat(testPartyTypeAttribute.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyTypeAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testPartyTypeAttribute.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyTypeAttribute.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyTypeAttribute.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyTypeAttribute.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePartyTypeAttributeWithPatch() throws Exception {
        // Initialize the database
        partyTypeAttributeRepository.saveAndFlush(partyTypeAttribute);

        int databaseSizeBeforeUpdate = partyTypeAttributeRepository.findAll().size();

        // Update the partyTypeAttribute using partial update
        PartyTypeAttribute partialUpdatedPartyTypeAttribute = new PartyTypeAttribute();
        partialUpdatedPartyTypeAttribute.setId(partyTypeAttribute.getId());

        partialUpdatedPartyTypeAttribute
            .gUID(UPDATED_G_UID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyTypeAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyTypeAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyTypeAttribute))
            )
            .andExpect(status().isOk());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
        PartyTypeAttribute testPartyTypeAttribute = partyTypeAttributeList.get(partyTypeAttributeList.size() - 1);
        assertThat(testPartyTypeAttribute.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyTypeAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testPartyTypeAttribute.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyTypeAttribute.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyTypeAttribute.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyTypeAttribute.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPartyTypeAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeAttributeRepository.findAll().size();
        partyTypeAttribute.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyTypeAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partyTypeAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyTypeAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartyTypeAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeAttributeRepository.findAll().size();
        partyTypeAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyTypeAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyTypeAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartyTypeAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeAttributeRepository.findAll().size();
        partyTypeAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyTypeAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyTypeAttribute))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartyTypeAttribute() throws Exception {
        // Initialize the database
        partyTypeAttributeRepository.saveAndFlush(partyTypeAttribute);

        int databaseSizeBeforeDelete = partyTypeAttributeRepository.findAll().size();

        // Delete the partyTypeAttribute
        restPartyTypeAttributeMockMvc
            .perform(delete(ENTITY_API_URL_ID, partyTypeAttribute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
