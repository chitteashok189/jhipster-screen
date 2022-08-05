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
import org.jhipster.blog.domain.PartyAttribute;
import org.jhipster.blog.repository.PartyAttributeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartyAttributeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartyAttributeResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ATTRIBUTE_VALUE = 1;
    private static final Integer UPDATED_ATTRIBUTE_VALUE = 2;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/party-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartyAttributeRepository partyAttributeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyAttributeMockMvc;

    private PartyAttribute partyAttribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyAttribute createEntity(EntityManager em) {
        PartyAttribute partyAttribute = new PartyAttribute()
            .gUID(DEFAULT_G_UID)
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return partyAttribute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyAttribute createUpdatedEntity(EntityManager em) {
        PartyAttribute partyAttribute = new PartyAttribute()
            .gUID(UPDATED_G_UID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return partyAttribute;
    }

    @BeforeEach
    public void initTest() {
        partyAttribute = createEntity(em);
    }

    @Test
    @Transactional
    void createPartyAttribute() throws Exception {
        int databaseSizeBeforeCreate = partyAttributeRepository.findAll().size();
        // Create the PartyAttribute
        restPartyAttributeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyAttribute))
            )
            .andExpect(status().isCreated());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        PartyAttribute testPartyAttribute = partyAttributeList.get(partyAttributeList.size() - 1);
        assertThat(testPartyAttribute.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testPartyAttribute.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
        assertThat(testPartyAttribute.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyAttribute.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyAttribute.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyAttribute.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPartyAttributeWithExistingId() throws Exception {
        // Create the PartyAttribute with an existing ID
        partyAttribute.setId(1L);

        int databaseSizeBeforeCreate = partyAttributeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyAttributeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartyAttributes() throws Exception {
        // Initialize the database
        partyAttributeRepository.saveAndFlush(partyAttribute);

        // Get all the partyAttributeList
        restPartyAttributeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPartyAttribute() throws Exception {
        // Initialize the database
        partyAttributeRepository.saveAndFlush(partyAttribute);

        // Get the partyAttribute
        restPartyAttributeMockMvc
            .perform(get(ENTITY_API_URL_ID, partyAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyAttribute.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPartyAttribute() throws Exception {
        // Get the partyAttribute
        restPartyAttributeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartyAttribute() throws Exception {
        // Initialize the database
        partyAttributeRepository.saveAndFlush(partyAttribute);

        int databaseSizeBeforeUpdate = partyAttributeRepository.findAll().size();

        // Update the partyAttribute
        PartyAttribute updatedPartyAttribute = partyAttributeRepository.findById(partyAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedPartyAttribute are not directly saved in db
        em.detach(updatedPartyAttribute);
        updatedPartyAttribute
            .gUID(UPDATED_G_UID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartyAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartyAttribute))
            )
            .andExpect(status().isOk());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeUpdate);
        PartyAttribute testPartyAttribute = partyAttributeList.get(partyAttributeList.size() - 1);
        assertThat(testPartyAttribute.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testPartyAttribute.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
        assertThat(testPartyAttribute.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyAttribute.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyAttribute.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyAttribute.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPartyAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyAttributeRepository.findAll().size();
        partyAttribute.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partyAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartyAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyAttributeRepository.findAll().size();
        partyAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartyAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyAttributeRepository.findAll().size();
        partyAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyAttributeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyAttribute)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartyAttributeWithPatch() throws Exception {
        // Initialize the database
        partyAttributeRepository.saveAndFlush(partyAttribute);

        int databaseSizeBeforeUpdate = partyAttributeRepository.findAll().size();

        // Update the partyAttribute using partial update
        PartyAttribute partialUpdatedPartyAttribute = new PartyAttribute();
        partialUpdatedPartyAttribute.setId(partyAttribute.getId());

        partialUpdatedPartyAttribute
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyAttribute))
            )
            .andExpect(status().isOk());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeUpdate);
        PartyAttribute testPartyAttribute = partyAttributeList.get(partyAttributeList.size() - 1);
        assertThat(testPartyAttribute.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testPartyAttribute.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
        assertThat(testPartyAttribute.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyAttribute.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyAttribute.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyAttribute.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePartyAttributeWithPatch() throws Exception {
        // Initialize the database
        partyAttributeRepository.saveAndFlush(partyAttribute);

        int databaseSizeBeforeUpdate = partyAttributeRepository.findAll().size();

        // Update the partyAttribute using partial update
        PartyAttribute partialUpdatedPartyAttribute = new PartyAttribute();
        partialUpdatedPartyAttribute.setId(partyAttribute.getId());

        partialUpdatedPartyAttribute
            .gUID(UPDATED_G_UID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyAttribute))
            )
            .andExpect(status().isOk());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeUpdate);
        PartyAttribute testPartyAttribute = partyAttributeList.get(partyAttributeList.size() - 1);
        assertThat(testPartyAttribute.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testPartyAttribute.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
        assertThat(testPartyAttribute.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyAttribute.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyAttribute.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyAttribute.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPartyAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyAttributeRepository.findAll().size();
        partyAttribute.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partyAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartyAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyAttributeRepository.findAll().size();
        partyAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartyAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyAttributeRepository.findAll().size();
        partyAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partyAttribute))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartyAttribute() throws Exception {
        // Initialize the database
        partyAttributeRepository.saveAndFlush(partyAttribute);

        int databaseSizeBeforeDelete = partyAttributeRepository.findAll().size();

        // Delete the partyAttribute
        restPartyAttributeMockMvc
            .perform(delete(ENTITY_API_URL_ID, partyAttribute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
