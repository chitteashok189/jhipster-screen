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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.blog.IntegrationTest;
import org.jhipster.blog.domain.PartyRelationship;
import org.jhipster.blog.repository.PartyRelationshipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartyRelationshipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartyRelationshipResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Long DEFAULT_PARTY_ID_TO = 1L;
    private static final Long UPDATED_PARTY_ID_TO = 2L;

    private static final Long DEFAULT_PARTY_ID_FROM = 1L;
    private static final Long UPDATED_PARTY_ID_FROM = 2L;

    private static final Long DEFAULT_ROLE_TYPE_ID_FROM = 1L;
    private static final Long UPDATED_ROLE_TYPE_ID_FROM = 2L;

    private static final Long DEFAULT_ROLE_TYPE_ID_TO = 1L;
    private static final Long UPDATED_ROLE_TYPE_ID_TO = 2L;

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_THRU_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THRU_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RELATIONSHIP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RELATIONSHIP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_POSITION_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/party-relationships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartyRelationshipRepository partyRelationshipRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyRelationshipMockMvc;

    private PartyRelationship partyRelationship;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRelationship createEntity(EntityManager em) {
        PartyRelationship partyRelationship = new PartyRelationship()
            .gUID(DEFAULT_G_UID)
            .partyIdTo(DEFAULT_PARTY_ID_TO)
            .partyIdFrom(DEFAULT_PARTY_ID_FROM)
            .roleTypeIdFrom(DEFAULT_ROLE_TYPE_ID_FROM)
            .roleTypeIdTo(DEFAULT_ROLE_TYPE_ID_TO)
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .relationshipName(DEFAULT_RELATIONSHIP_NAME)
            .positionTitle(DEFAULT_POSITION_TITLE)
            .comments(DEFAULT_COMMENTS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return partyRelationship;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRelationship createUpdatedEntity(EntityManager em) {
        PartyRelationship partyRelationship = new PartyRelationship()
            .gUID(UPDATED_G_UID)
            .partyIdTo(UPDATED_PARTY_ID_TO)
            .partyIdFrom(UPDATED_PARTY_ID_FROM)
            .roleTypeIdFrom(UPDATED_ROLE_TYPE_ID_FROM)
            .roleTypeIdTo(UPDATED_ROLE_TYPE_ID_TO)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .relationshipName(UPDATED_RELATIONSHIP_NAME)
            .positionTitle(UPDATED_POSITION_TITLE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return partyRelationship;
    }

    @BeforeEach
    public void initTest() {
        partyRelationship = createEntity(em);
    }

    @Test
    @Transactional
    void createPartyRelationship() throws Exception {
        int databaseSizeBeforeCreate = partyRelationshipRepository.findAll().size();
        // Create the PartyRelationship
        restPartyRelationshipMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyRelationship))
            )
            .andExpect(status().isCreated());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        PartyRelationship testPartyRelationship = partyRelationshipList.get(partyRelationshipList.size() - 1);
        assertThat(testPartyRelationship.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyRelationship.getPartyIdTo()).isEqualTo(DEFAULT_PARTY_ID_TO);
        assertThat(testPartyRelationship.getPartyIdFrom()).isEqualTo(DEFAULT_PARTY_ID_FROM);
        assertThat(testPartyRelationship.getRoleTypeIdFrom()).isEqualTo(DEFAULT_ROLE_TYPE_ID_FROM);
        assertThat(testPartyRelationship.getRoleTypeIdTo()).isEqualTo(DEFAULT_ROLE_TYPE_ID_TO);
        assertThat(testPartyRelationship.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPartyRelationship.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testPartyRelationship.getRelationshipName()).isEqualTo(DEFAULT_RELATIONSHIP_NAME);
        assertThat(testPartyRelationship.getPositionTitle()).isEqualTo(DEFAULT_POSITION_TITLE);
        assertThat(testPartyRelationship.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPartyRelationship.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyRelationship.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyRelationship.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyRelationship.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPartyRelationshipWithExistingId() throws Exception {
        // Create the PartyRelationship with an existing ID
        partyRelationship.setId(1L);

        int databaseSizeBeforeCreate = partyRelationshipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyRelationshipMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyRelationship))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartyRelationships() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        // Get all the partyRelationshipList
        restPartyRelationshipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].partyIdTo").value(hasItem(DEFAULT_PARTY_ID_TO.intValue())))
            .andExpect(jsonPath("$.[*].partyIdFrom").value(hasItem(DEFAULT_PARTY_ID_FROM.intValue())))
            .andExpect(jsonPath("$.[*].roleTypeIdFrom").value(hasItem(DEFAULT_ROLE_TYPE_ID_FROM.intValue())))
            .andExpect(jsonPath("$.[*].roleTypeIdTo").value(hasItem(DEFAULT_ROLE_TYPE_ID_TO.intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].relationshipName").value(hasItem(DEFAULT_RELATIONSHIP_NAME)))
            .andExpect(jsonPath("$.[*].positionTitle").value(hasItem(DEFAULT_POSITION_TITLE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPartyRelationship() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        // Get the partyRelationship
        restPartyRelationshipMockMvc
            .perform(get(ENTITY_API_URL_ID, partyRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyRelationship.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.partyIdTo").value(DEFAULT_PARTY_ID_TO.intValue()))
            .andExpect(jsonPath("$.partyIdFrom").value(DEFAULT_PARTY_ID_FROM.intValue()))
            .andExpect(jsonPath("$.roleTypeIdFrom").value(DEFAULT_ROLE_TYPE_ID_FROM.intValue()))
            .andExpect(jsonPath("$.roleTypeIdTo").value(DEFAULT_ROLE_TYPE_ID_TO.intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()))
            .andExpect(jsonPath("$.relationshipName").value(DEFAULT_RELATIONSHIP_NAME))
            .andExpect(jsonPath("$.positionTitle").value(DEFAULT_POSITION_TITLE))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPartyRelationship() throws Exception {
        // Get the partyRelationship
        restPartyRelationshipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartyRelationship() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        int databaseSizeBeforeUpdate = partyRelationshipRepository.findAll().size();

        // Update the partyRelationship
        PartyRelationship updatedPartyRelationship = partyRelationshipRepository.findById(partyRelationship.getId()).get();
        // Disconnect from session so that the updates on updatedPartyRelationship are not directly saved in db
        em.detach(updatedPartyRelationship);
        updatedPartyRelationship
            .gUID(UPDATED_G_UID)
            .partyIdTo(UPDATED_PARTY_ID_TO)
            .partyIdFrom(UPDATED_PARTY_ID_FROM)
            .roleTypeIdFrom(UPDATED_ROLE_TYPE_ID_FROM)
            .roleTypeIdTo(UPDATED_ROLE_TYPE_ID_TO)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .relationshipName(UPDATED_RELATIONSHIP_NAME)
            .positionTitle(UPDATED_POSITION_TITLE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartyRelationship.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartyRelationship))
            )
            .andExpect(status().isOk());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeUpdate);
        PartyRelationship testPartyRelationship = partyRelationshipList.get(partyRelationshipList.size() - 1);
        assertThat(testPartyRelationship.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyRelationship.getPartyIdTo()).isEqualTo(UPDATED_PARTY_ID_TO);
        assertThat(testPartyRelationship.getPartyIdFrom()).isEqualTo(UPDATED_PARTY_ID_FROM);
        assertThat(testPartyRelationship.getRoleTypeIdFrom()).isEqualTo(UPDATED_ROLE_TYPE_ID_FROM);
        assertThat(testPartyRelationship.getRoleTypeIdTo()).isEqualTo(UPDATED_ROLE_TYPE_ID_TO);
        assertThat(testPartyRelationship.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPartyRelationship.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testPartyRelationship.getRelationshipName()).isEqualTo(UPDATED_RELATIONSHIP_NAME);
        assertThat(testPartyRelationship.getPositionTitle()).isEqualTo(UPDATED_POSITION_TITLE);
        assertThat(testPartyRelationship.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPartyRelationship.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyRelationship.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyRelationship.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyRelationship.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPartyRelationship() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipRepository.findAll().size();
        partyRelationship.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partyRelationship.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyRelationship))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartyRelationship() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipRepository.findAll().size();
        partyRelationship.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyRelationship))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartyRelationship() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipRepository.findAll().size();
        partyRelationship.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyRelationship))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartyRelationshipWithPatch() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        int databaseSizeBeforeUpdate = partyRelationshipRepository.findAll().size();

        // Update the partyRelationship using partial update
        PartyRelationship partialUpdatedPartyRelationship = new PartyRelationship();
        partialUpdatedPartyRelationship.setId(partyRelationship.getId());

        partialUpdatedPartyRelationship
            .partyIdTo(UPDATED_PARTY_ID_TO)
            .roleTypeIdFrom(UPDATED_ROLE_TYPE_ID_FROM)
            .positionTitle(UPDATED_POSITION_TITLE)
            .comments(UPDATED_COMMENTS)
            .createdOn(UPDATED_CREATED_ON);

        restPartyRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyRelationship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyRelationship))
            )
            .andExpect(status().isOk());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeUpdate);
        PartyRelationship testPartyRelationship = partyRelationshipList.get(partyRelationshipList.size() - 1);
        assertThat(testPartyRelationship.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyRelationship.getPartyIdTo()).isEqualTo(UPDATED_PARTY_ID_TO);
        assertThat(testPartyRelationship.getPartyIdFrom()).isEqualTo(DEFAULT_PARTY_ID_FROM);
        assertThat(testPartyRelationship.getRoleTypeIdFrom()).isEqualTo(UPDATED_ROLE_TYPE_ID_FROM);
        assertThat(testPartyRelationship.getRoleTypeIdTo()).isEqualTo(DEFAULT_ROLE_TYPE_ID_TO);
        assertThat(testPartyRelationship.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPartyRelationship.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testPartyRelationship.getRelationshipName()).isEqualTo(DEFAULT_RELATIONSHIP_NAME);
        assertThat(testPartyRelationship.getPositionTitle()).isEqualTo(UPDATED_POSITION_TITLE);
        assertThat(testPartyRelationship.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPartyRelationship.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyRelationship.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyRelationship.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyRelationship.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePartyRelationshipWithPatch() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        int databaseSizeBeforeUpdate = partyRelationshipRepository.findAll().size();

        // Update the partyRelationship using partial update
        PartyRelationship partialUpdatedPartyRelationship = new PartyRelationship();
        partialUpdatedPartyRelationship.setId(partyRelationship.getId());

        partialUpdatedPartyRelationship
            .gUID(UPDATED_G_UID)
            .partyIdTo(UPDATED_PARTY_ID_TO)
            .partyIdFrom(UPDATED_PARTY_ID_FROM)
            .roleTypeIdFrom(UPDATED_ROLE_TYPE_ID_FROM)
            .roleTypeIdTo(UPDATED_ROLE_TYPE_ID_TO)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .relationshipName(UPDATED_RELATIONSHIP_NAME)
            .positionTitle(UPDATED_POSITION_TITLE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyRelationship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyRelationship))
            )
            .andExpect(status().isOk());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeUpdate);
        PartyRelationship testPartyRelationship = partyRelationshipList.get(partyRelationshipList.size() - 1);
        assertThat(testPartyRelationship.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyRelationship.getPartyIdTo()).isEqualTo(UPDATED_PARTY_ID_TO);
        assertThat(testPartyRelationship.getPartyIdFrom()).isEqualTo(UPDATED_PARTY_ID_FROM);
        assertThat(testPartyRelationship.getRoleTypeIdFrom()).isEqualTo(UPDATED_ROLE_TYPE_ID_FROM);
        assertThat(testPartyRelationship.getRoleTypeIdTo()).isEqualTo(UPDATED_ROLE_TYPE_ID_TO);
        assertThat(testPartyRelationship.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPartyRelationship.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testPartyRelationship.getRelationshipName()).isEqualTo(UPDATED_RELATIONSHIP_NAME);
        assertThat(testPartyRelationship.getPositionTitle()).isEqualTo(UPDATED_POSITION_TITLE);
        assertThat(testPartyRelationship.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPartyRelationship.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyRelationship.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyRelationship.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyRelationship.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPartyRelationship() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipRepository.findAll().size();
        partyRelationship.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partyRelationship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyRelationship))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartyRelationship() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipRepository.findAll().size();
        partyRelationship.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyRelationship))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartyRelationship() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipRepository.findAll().size();
        partyRelationship.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyRelationship))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartyRelationship() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        int databaseSizeBeforeDelete = partyRelationshipRepository.findAll().size();

        // Delete the partyRelationship
        restPartyRelationshipMockMvc
            .perform(delete(ENTITY_API_URL_ID, partyRelationship.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
