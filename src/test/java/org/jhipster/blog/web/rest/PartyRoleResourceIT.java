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
import org.jhipster.blog.domain.PartyRole;
import org.jhipster.blog.repository.PartyRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartyRoleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartyRoleResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_FROM_AGREEMENT = "AAAAAAAAAA";
    private static final String UPDATED_FROM_AGREEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_TO_AGREEMENT = "AAAAAAAAAA";
    private static final String UPDATED_TO_AGREEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_FROM_COMMUNICATION_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_FROM_COMMUNICATION_EVENT = "BBBBBBBBBB";

    private static final String DEFAULT_TO_COMMUNICATION_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_TO_COMMUNICATION_EVENT = "BBBBBBBBBB";

    private static final Long DEFAULT_PARTY_ID_FROM = 1L;
    private static final Long UPDATED_PARTY_ID_FROM = 2L;

    private static final Long DEFAULT_PARTY_ID_TO = 1L;
    private static final Long UPDATED_PARTY_ID_TO = 2L;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/party-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartyRoleRepository partyRoleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyRoleMockMvc;

    private PartyRole partyRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRole createEntity(EntityManager em) {
        PartyRole partyRole = new PartyRole()
            .gUID(DEFAULT_G_UID)
            .fromAgreement(DEFAULT_FROM_AGREEMENT)
            .toAgreement(DEFAULT_TO_AGREEMENT)
            .fromCommunicationEvent(DEFAULT_FROM_COMMUNICATION_EVENT)
            .toCommunicationEvent(DEFAULT_TO_COMMUNICATION_EVENT)
            .partyIdFrom(DEFAULT_PARTY_ID_FROM)
            .partyIdTO(DEFAULT_PARTY_ID_TO)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return partyRole;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRole createUpdatedEntity(EntityManager em) {
        PartyRole partyRole = new PartyRole()
            .gUID(UPDATED_G_UID)
            .fromAgreement(UPDATED_FROM_AGREEMENT)
            .toAgreement(UPDATED_TO_AGREEMENT)
            .fromCommunicationEvent(UPDATED_FROM_COMMUNICATION_EVENT)
            .toCommunicationEvent(UPDATED_TO_COMMUNICATION_EVENT)
            .partyIdFrom(UPDATED_PARTY_ID_FROM)
            .partyIdTO(UPDATED_PARTY_ID_TO)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return partyRole;
    }

    @BeforeEach
    public void initTest() {
        partyRole = createEntity(em);
    }

    @Test
    @Transactional
    void createPartyRole() throws Exception {
        int databaseSizeBeforeCreate = partyRoleRepository.findAll().size();
        // Create the PartyRole
        restPartyRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyRole)))
            .andExpect(status().isCreated());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeCreate + 1);
        PartyRole testPartyRole = partyRoleList.get(partyRoleList.size() - 1);
        assertThat(testPartyRole.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyRole.getFromAgreement()).isEqualTo(DEFAULT_FROM_AGREEMENT);
        assertThat(testPartyRole.getToAgreement()).isEqualTo(DEFAULT_TO_AGREEMENT);
        assertThat(testPartyRole.getFromCommunicationEvent()).isEqualTo(DEFAULT_FROM_COMMUNICATION_EVENT);
        assertThat(testPartyRole.getToCommunicationEvent()).isEqualTo(DEFAULT_TO_COMMUNICATION_EVENT);
        assertThat(testPartyRole.getPartyIdFrom()).isEqualTo(DEFAULT_PARTY_ID_FROM);
        assertThat(testPartyRole.getPartyIdTO()).isEqualTo(DEFAULT_PARTY_ID_TO);
        assertThat(testPartyRole.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyRole.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyRole.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyRole.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPartyRoleWithExistingId() throws Exception {
        // Create the PartyRole with an existing ID
        partyRole.setId(1L);

        int databaseSizeBeforeCreate = partyRoleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyRole)))
            .andExpect(status().isBadRequest());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartyRoles() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        // Get all the partyRoleList
        restPartyRoleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].fromAgreement").value(hasItem(DEFAULT_FROM_AGREEMENT)))
            .andExpect(jsonPath("$.[*].toAgreement").value(hasItem(DEFAULT_TO_AGREEMENT)))
            .andExpect(jsonPath("$.[*].fromCommunicationEvent").value(hasItem(DEFAULT_FROM_COMMUNICATION_EVENT)))
            .andExpect(jsonPath("$.[*].toCommunicationEvent").value(hasItem(DEFAULT_TO_COMMUNICATION_EVENT)))
            .andExpect(jsonPath("$.[*].partyIdFrom").value(hasItem(DEFAULT_PARTY_ID_FROM.intValue())))
            .andExpect(jsonPath("$.[*].partyIdTO").value(hasItem(DEFAULT_PARTY_ID_TO.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPartyRole() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        // Get the partyRole
        restPartyRoleMockMvc
            .perform(get(ENTITY_API_URL_ID, partyRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyRole.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.fromAgreement").value(DEFAULT_FROM_AGREEMENT))
            .andExpect(jsonPath("$.toAgreement").value(DEFAULT_TO_AGREEMENT))
            .andExpect(jsonPath("$.fromCommunicationEvent").value(DEFAULT_FROM_COMMUNICATION_EVENT))
            .andExpect(jsonPath("$.toCommunicationEvent").value(DEFAULT_TO_COMMUNICATION_EVENT))
            .andExpect(jsonPath("$.partyIdFrom").value(DEFAULT_PARTY_ID_FROM.intValue()))
            .andExpect(jsonPath("$.partyIdTO").value(DEFAULT_PARTY_ID_TO.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPartyRole() throws Exception {
        // Get the partyRole
        restPartyRoleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartyRole() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();

        // Update the partyRole
        PartyRole updatedPartyRole = partyRoleRepository.findById(partyRole.getId()).get();
        // Disconnect from session so that the updates on updatedPartyRole are not directly saved in db
        em.detach(updatedPartyRole);
        updatedPartyRole
            .gUID(UPDATED_G_UID)
            .fromAgreement(UPDATED_FROM_AGREEMENT)
            .toAgreement(UPDATED_TO_AGREEMENT)
            .fromCommunicationEvent(UPDATED_FROM_COMMUNICATION_EVENT)
            .toCommunicationEvent(UPDATED_TO_COMMUNICATION_EVENT)
            .partyIdFrom(UPDATED_PARTY_ID_FROM)
            .partyIdTO(UPDATED_PARTY_ID_TO)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartyRole.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartyRole))
            )
            .andExpect(status().isOk());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
        PartyRole testPartyRole = partyRoleList.get(partyRoleList.size() - 1);
        assertThat(testPartyRole.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyRole.getFromAgreement()).isEqualTo(UPDATED_FROM_AGREEMENT);
        assertThat(testPartyRole.getToAgreement()).isEqualTo(UPDATED_TO_AGREEMENT);
        assertThat(testPartyRole.getFromCommunicationEvent()).isEqualTo(UPDATED_FROM_COMMUNICATION_EVENT);
        assertThat(testPartyRole.getToCommunicationEvent()).isEqualTo(UPDATED_TO_COMMUNICATION_EVENT);
        assertThat(testPartyRole.getPartyIdFrom()).isEqualTo(UPDATED_PARTY_ID_FROM);
        assertThat(testPartyRole.getPartyIdTO()).isEqualTo(UPDATED_PARTY_ID_TO);
        assertThat(testPartyRole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyRole.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyRole.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyRole.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPartyRole() throws Exception {
        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();
        partyRole.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partyRole.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyRole))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartyRole() throws Exception {
        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();
        partyRole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyRole))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartyRole() throws Exception {
        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();
        partyRole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyRoleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyRole)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartyRoleWithPatch() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();

        // Update the partyRole using partial update
        PartyRole partialUpdatedPartyRole = new PartyRole();
        partialUpdatedPartyRole.setId(partyRole.getId());

        partialUpdatedPartyRole
            .gUID(UPDATED_G_UID)
            .fromAgreement(UPDATED_FROM_AGREEMENT)
            .toAgreement(UPDATED_TO_AGREEMENT)
            .fromCommunicationEvent(UPDATED_FROM_COMMUNICATION_EVENT)
            .partyIdFrom(UPDATED_PARTY_ID_FROM)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyRole))
            )
            .andExpect(status().isOk());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
        PartyRole testPartyRole = partyRoleList.get(partyRoleList.size() - 1);
        assertThat(testPartyRole.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyRole.getFromAgreement()).isEqualTo(UPDATED_FROM_AGREEMENT);
        assertThat(testPartyRole.getToAgreement()).isEqualTo(UPDATED_TO_AGREEMENT);
        assertThat(testPartyRole.getFromCommunicationEvent()).isEqualTo(UPDATED_FROM_COMMUNICATION_EVENT);
        assertThat(testPartyRole.getToCommunicationEvent()).isEqualTo(DEFAULT_TO_COMMUNICATION_EVENT);
        assertThat(testPartyRole.getPartyIdFrom()).isEqualTo(UPDATED_PARTY_ID_FROM);
        assertThat(testPartyRole.getPartyIdTO()).isEqualTo(DEFAULT_PARTY_ID_TO);
        assertThat(testPartyRole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyRole.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyRole.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyRole.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePartyRoleWithPatch() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();

        // Update the partyRole using partial update
        PartyRole partialUpdatedPartyRole = new PartyRole();
        partialUpdatedPartyRole.setId(partyRole.getId());

        partialUpdatedPartyRole
            .gUID(UPDATED_G_UID)
            .fromAgreement(UPDATED_FROM_AGREEMENT)
            .toAgreement(UPDATED_TO_AGREEMENT)
            .fromCommunicationEvent(UPDATED_FROM_COMMUNICATION_EVENT)
            .toCommunicationEvent(UPDATED_TO_COMMUNICATION_EVENT)
            .partyIdFrom(UPDATED_PARTY_ID_FROM)
            .partyIdTO(UPDATED_PARTY_ID_TO)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyRole))
            )
            .andExpect(status().isOk());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
        PartyRole testPartyRole = partyRoleList.get(partyRoleList.size() - 1);
        assertThat(testPartyRole.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyRole.getFromAgreement()).isEqualTo(UPDATED_FROM_AGREEMENT);
        assertThat(testPartyRole.getToAgreement()).isEqualTo(UPDATED_TO_AGREEMENT);
        assertThat(testPartyRole.getFromCommunicationEvent()).isEqualTo(UPDATED_FROM_COMMUNICATION_EVENT);
        assertThat(testPartyRole.getToCommunicationEvent()).isEqualTo(UPDATED_TO_COMMUNICATION_EVENT);
        assertThat(testPartyRole.getPartyIdFrom()).isEqualTo(UPDATED_PARTY_ID_FROM);
        assertThat(testPartyRole.getPartyIdTO()).isEqualTo(UPDATED_PARTY_ID_TO);
        assertThat(testPartyRole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyRole.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyRole.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyRole.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPartyRole() throws Exception {
        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();
        partyRole.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partyRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyRole))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartyRole() throws Exception {
        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();
        partyRole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyRole))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartyRole() throws Exception {
        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();
        partyRole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyRoleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partyRole))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartyRole() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        int databaseSizeBeforeDelete = partyRoleRepository.findAll().size();

        // Delete the partyRole
        restPartyRoleMockMvc
            .perform(delete(ENTITY_API_URL_ID, partyRole.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
