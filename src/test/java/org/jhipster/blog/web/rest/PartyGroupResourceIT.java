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
import org.jhipster.blog.domain.PartyGroup;
import org.jhipster.blog.repository.PartyGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartyGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartyGroupResourceIT {

    private static final Long DEFAULT_G_UID = 1L;
    private static final Long UPDATED_G_UID = 2L;

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_GROUP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_SITE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_SITE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_IMAGE_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/party-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartyGroupRepository partyGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyGroupMockMvc;

    private PartyGroup partyGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyGroup createEntity(EntityManager em) {
        PartyGroup partyGroup = new PartyGroup()
            .gUID(DEFAULT_G_UID)
            .groupName(DEFAULT_GROUP_NAME)
            .localGroupName(DEFAULT_LOCAL_GROUP_NAME)
            .officeSiteName(DEFAULT_OFFICE_SITE_NAME)
            .comments(DEFAULT_COMMENTS)
            .logoImageUrl(DEFAULT_LOGO_IMAGE_URL)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return partyGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyGroup createUpdatedEntity(EntityManager em) {
        PartyGroup partyGroup = new PartyGroup()
            .gUID(UPDATED_G_UID)
            .groupName(UPDATED_GROUP_NAME)
            .localGroupName(UPDATED_LOCAL_GROUP_NAME)
            .officeSiteName(UPDATED_OFFICE_SITE_NAME)
            .comments(UPDATED_COMMENTS)
            .logoImageUrl(UPDATED_LOGO_IMAGE_URL)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return partyGroup;
    }

    @BeforeEach
    public void initTest() {
        partyGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createPartyGroup() throws Exception {
        int databaseSizeBeforeCreate = partyGroupRepository.findAll().size();
        // Create the PartyGroup
        restPartyGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyGroup)))
            .andExpect(status().isCreated());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeCreate + 1);
        PartyGroup testPartyGroup = partyGroupList.get(partyGroupList.size() - 1);
        assertThat(testPartyGroup.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyGroup.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
        assertThat(testPartyGroup.getLocalGroupName()).isEqualTo(DEFAULT_LOCAL_GROUP_NAME);
        assertThat(testPartyGroup.getOfficeSiteName()).isEqualTo(DEFAULT_OFFICE_SITE_NAME);
        assertThat(testPartyGroup.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPartyGroup.getLogoImageUrl()).isEqualTo(DEFAULT_LOGO_IMAGE_URL);
        assertThat(testPartyGroup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyGroup.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyGroup.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyGroup.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPartyGroupWithExistingId() throws Exception {
        // Create the PartyGroup with an existing ID
        partyGroup.setId(1L);

        int databaseSizeBeforeCreate = partyGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyGroup)))
            .andExpect(status().isBadRequest());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartyGroups() throws Exception {
        // Initialize the database
        partyGroupRepository.saveAndFlush(partyGroup);

        // Get all the partyGroupList
        restPartyGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].localGroupName").value(hasItem(DEFAULT_LOCAL_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].officeSiteName").value(hasItem(DEFAULT_OFFICE_SITE_NAME)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].logoImageUrl").value(hasItem(DEFAULT_LOGO_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPartyGroup() throws Exception {
        // Initialize the database
        partyGroupRepository.saveAndFlush(partyGroup);

        // Get the partyGroup
        restPartyGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, partyGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyGroup.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.intValue()))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME))
            .andExpect(jsonPath("$.localGroupName").value(DEFAULT_LOCAL_GROUP_NAME))
            .andExpect(jsonPath("$.officeSiteName").value(DEFAULT_OFFICE_SITE_NAME))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.logoImageUrl").value(DEFAULT_LOGO_IMAGE_URL))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPartyGroup() throws Exception {
        // Get the partyGroup
        restPartyGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartyGroup() throws Exception {
        // Initialize the database
        partyGroupRepository.saveAndFlush(partyGroup);

        int databaseSizeBeforeUpdate = partyGroupRepository.findAll().size();

        // Update the partyGroup
        PartyGroup updatedPartyGroup = partyGroupRepository.findById(partyGroup.getId()).get();
        // Disconnect from session so that the updates on updatedPartyGroup are not directly saved in db
        em.detach(updatedPartyGroup);
        updatedPartyGroup
            .gUID(UPDATED_G_UID)
            .groupName(UPDATED_GROUP_NAME)
            .localGroupName(UPDATED_LOCAL_GROUP_NAME)
            .officeSiteName(UPDATED_OFFICE_SITE_NAME)
            .comments(UPDATED_COMMENTS)
            .logoImageUrl(UPDATED_LOGO_IMAGE_URL)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartyGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartyGroup))
            )
            .andExpect(status().isOk());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeUpdate);
        PartyGroup testPartyGroup = partyGroupList.get(partyGroupList.size() - 1);
        assertThat(testPartyGroup.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testPartyGroup.getLocalGroupName()).isEqualTo(UPDATED_LOCAL_GROUP_NAME);
        assertThat(testPartyGroup.getOfficeSiteName()).isEqualTo(UPDATED_OFFICE_SITE_NAME);
        assertThat(testPartyGroup.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPartyGroup.getLogoImageUrl()).isEqualTo(UPDATED_LOGO_IMAGE_URL);
        assertThat(testPartyGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyGroup.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyGroup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyGroup.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPartyGroup() throws Exception {
        int databaseSizeBeforeUpdate = partyGroupRepository.findAll().size();
        partyGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partyGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartyGroup() throws Exception {
        int databaseSizeBeforeUpdate = partyGroupRepository.findAll().size();
        partyGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartyGroup() throws Exception {
        int databaseSizeBeforeUpdate = partyGroupRepository.findAll().size();
        partyGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartyGroupWithPatch() throws Exception {
        // Initialize the database
        partyGroupRepository.saveAndFlush(partyGroup);

        int databaseSizeBeforeUpdate = partyGroupRepository.findAll().size();

        // Update the partyGroup using partial update
        PartyGroup partialUpdatedPartyGroup = new PartyGroup();
        partialUpdatedPartyGroup.setId(partyGroup.getId());

        partialUpdatedPartyGroup
            .gUID(UPDATED_G_UID)
            .localGroupName(UPDATED_LOCAL_GROUP_NAME)
            .officeSiteName(UPDATED_OFFICE_SITE_NAME)
            .comments(UPDATED_COMMENTS)
            .logoImageUrl(UPDATED_LOGO_IMAGE_URL)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restPartyGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyGroup))
            )
            .andExpect(status().isOk());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeUpdate);
        PartyGroup testPartyGroup = partyGroupList.get(partyGroupList.size() - 1);
        assertThat(testPartyGroup.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyGroup.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
        assertThat(testPartyGroup.getLocalGroupName()).isEqualTo(UPDATED_LOCAL_GROUP_NAME);
        assertThat(testPartyGroup.getOfficeSiteName()).isEqualTo(UPDATED_OFFICE_SITE_NAME);
        assertThat(testPartyGroup.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPartyGroup.getLogoImageUrl()).isEqualTo(UPDATED_LOGO_IMAGE_URL);
        assertThat(testPartyGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyGroup.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyGroup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyGroup.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePartyGroupWithPatch() throws Exception {
        // Initialize the database
        partyGroupRepository.saveAndFlush(partyGroup);

        int databaseSizeBeforeUpdate = partyGroupRepository.findAll().size();

        // Update the partyGroup using partial update
        PartyGroup partialUpdatedPartyGroup = new PartyGroup();
        partialUpdatedPartyGroup.setId(partyGroup.getId());

        partialUpdatedPartyGroup
            .gUID(UPDATED_G_UID)
            .groupName(UPDATED_GROUP_NAME)
            .localGroupName(UPDATED_LOCAL_GROUP_NAME)
            .officeSiteName(UPDATED_OFFICE_SITE_NAME)
            .comments(UPDATED_COMMENTS)
            .logoImageUrl(UPDATED_LOGO_IMAGE_URL)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyGroup))
            )
            .andExpect(status().isOk());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeUpdate);
        PartyGroup testPartyGroup = partyGroupList.get(partyGroupList.size() - 1);
        assertThat(testPartyGroup.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testPartyGroup.getLocalGroupName()).isEqualTo(UPDATED_LOCAL_GROUP_NAME);
        assertThat(testPartyGroup.getOfficeSiteName()).isEqualTo(UPDATED_OFFICE_SITE_NAME);
        assertThat(testPartyGroup.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPartyGroup.getLogoImageUrl()).isEqualTo(UPDATED_LOGO_IMAGE_URL);
        assertThat(testPartyGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyGroup.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyGroup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyGroup.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPartyGroup() throws Exception {
        int databaseSizeBeforeUpdate = partyGroupRepository.findAll().size();
        partyGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partyGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartyGroup() throws Exception {
        int databaseSizeBeforeUpdate = partyGroupRepository.findAll().size();
        partyGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartyGroup() throws Exception {
        int databaseSizeBeforeUpdate = partyGroupRepository.findAll().size();
        partyGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyGroupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partyGroup))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartyGroup() throws Exception {
        // Initialize the database
        partyGroupRepository.saveAndFlush(partyGroup);

        int databaseSizeBeforeDelete = partyGroupRepository.findAll().size();

        // Delete the partyGroup
        restPartyGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, partyGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
