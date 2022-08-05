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
import org.jhipster.blog.domain.PartyNote;
import org.jhipster.blog.repository.PartyNoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartyNoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartyNoteResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Long DEFAULT_NOTE_ID = 1L;
    private static final Long UPDATED_NOTE_ID = 2L;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/party-notes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartyNoteRepository partyNoteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyNoteMockMvc;

    private PartyNote partyNote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyNote createEntity(EntityManager em) {
        PartyNote partyNote = new PartyNote()
            .gUID(DEFAULT_G_UID)
            .noteID(DEFAULT_NOTE_ID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return partyNote;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyNote createUpdatedEntity(EntityManager em) {
        PartyNote partyNote = new PartyNote()
            .gUID(UPDATED_G_UID)
            .noteID(UPDATED_NOTE_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return partyNote;
    }

    @BeforeEach
    public void initTest() {
        partyNote = createEntity(em);
    }

    @Test
    @Transactional
    void createPartyNote() throws Exception {
        int databaseSizeBeforeCreate = partyNoteRepository.findAll().size();
        // Create the PartyNote
        restPartyNoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyNote)))
            .andExpect(status().isCreated());

        // Validate the PartyNote in the database
        List<PartyNote> partyNoteList = partyNoteRepository.findAll();
        assertThat(partyNoteList).hasSize(databaseSizeBeforeCreate + 1);
        PartyNote testPartyNote = partyNoteList.get(partyNoteList.size() - 1);
        assertThat(testPartyNote.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyNote.getNoteID()).isEqualTo(DEFAULT_NOTE_ID);
        assertThat(testPartyNote.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyNote.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPartyNote.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyNote.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPartyNoteWithExistingId() throws Exception {
        // Create the PartyNote with an existing ID
        partyNote.setId(1L);

        int databaseSizeBeforeCreate = partyNoteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyNoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyNote)))
            .andExpect(status().isBadRequest());

        // Validate the PartyNote in the database
        List<PartyNote> partyNoteList = partyNoteRepository.findAll();
        assertThat(partyNoteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartyNotes() throws Exception {
        // Initialize the database
        partyNoteRepository.saveAndFlush(partyNote);

        // Get all the partyNoteList
        restPartyNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].noteID").value(hasItem(DEFAULT_NOTE_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPartyNote() throws Exception {
        // Initialize the database
        partyNoteRepository.saveAndFlush(partyNote);

        // Get the partyNote
        restPartyNoteMockMvc
            .perform(get(ENTITY_API_URL_ID, partyNote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyNote.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.noteID").value(DEFAULT_NOTE_ID.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPartyNote() throws Exception {
        // Get the partyNote
        restPartyNoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartyNote() throws Exception {
        // Initialize the database
        partyNoteRepository.saveAndFlush(partyNote);

        int databaseSizeBeforeUpdate = partyNoteRepository.findAll().size();

        // Update the partyNote
        PartyNote updatedPartyNote = partyNoteRepository.findById(partyNote.getId()).get();
        // Disconnect from session so that the updates on updatedPartyNote are not directly saved in db
        em.detach(updatedPartyNote);
        updatedPartyNote
            .gUID(UPDATED_G_UID)
            .noteID(UPDATED_NOTE_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartyNote.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartyNote))
            )
            .andExpect(status().isOk());

        // Validate the PartyNote in the database
        List<PartyNote> partyNoteList = partyNoteRepository.findAll();
        assertThat(partyNoteList).hasSize(databaseSizeBeforeUpdate);
        PartyNote testPartyNote = partyNoteList.get(partyNoteList.size() - 1);
        assertThat(testPartyNote.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyNote.getNoteID()).isEqualTo(UPDATED_NOTE_ID);
        assertThat(testPartyNote.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyNote.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyNote.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyNote.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPartyNote() throws Exception {
        int databaseSizeBeforeUpdate = partyNoteRepository.findAll().size();
        partyNote.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partyNote.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyNote))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyNote in the database
        List<PartyNote> partyNoteList = partyNoteRepository.findAll();
        assertThat(partyNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartyNote() throws Exception {
        int databaseSizeBeforeUpdate = partyNoteRepository.findAll().size();
        partyNote.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partyNote))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyNote in the database
        List<PartyNote> partyNoteList = partyNoteRepository.findAll();
        assertThat(partyNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartyNote() throws Exception {
        int databaseSizeBeforeUpdate = partyNoteRepository.findAll().size();
        partyNote.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyNoteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partyNote)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyNote in the database
        List<PartyNote> partyNoteList = partyNoteRepository.findAll();
        assertThat(partyNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartyNoteWithPatch() throws Exception {
        // Initialize the database
        partyNoteRepository.saveAndFlush(partyNote);

        int databaseSizeBeforeUpdate = partyNoteRepository.findAll().size();

        // Update the partyNote using partial update
        PartyNote partialUpdatedPartyNote = new PartyNote();
        partialUpdatedPartyNote.setId(partyNote.getId());

        partialUpdatedPartyNote.createdOn(UPDATED_CREATED_ON);

        restPartyNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyNote))
            )
            .andExpect(status().isOk());

        // Validate the PartyNote in the database
        List<PartyNote> partyNoteList = partyNoteRepository.findAll();
        assertThat(partyNoteList).hasSize(databaseSizeBeforeUpdate);
        PartyNote testPartyNote = partyNoteList.get(partyNoteList.size() - 1);
        assertThat(testPartyNote.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPartyNote.getNoteID()).isEqualTo(DEFAULT_NOTE_ID);
        assertThat(testPartyNote.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartyNote.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyNote.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPartyNote.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePartyNoteWithPatch() throws Exception {
        // Initialize the database
        partyNoteRepository.saveAndFlush(partyNote);

        int databaseSizeBeforeUpdate = partyNoteRepository.findAll().size();

        // Update the partyNote using partial update
        PartyNote partialUpdatedPartyNote = new PartyNote();
        partialUpdatedPartyNote.setId(partyNote.getId());

        partialUpdatedPartyNote
            .gUID(UPDATED_G_UID)
            .noteID(UPDATED_NOTE_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPartyNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartyNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartyNote))
            )
            .andExpect(status().isOk());

        // Validate the PartyNote in the database
        List<PartyNote> partyNoteList = partyNoteRepository.findAll();
        assertThat(partyNoteList).hasSize(databaseSizeBeforeUpdate);
        PartyNote testPartyNote = partyNoteList.get(partyNoteList.size() - 1);
        assertThat(testPartyNote.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPartyNote.getNoteID()).isEqualTo(UPDATED_NOTE_ID);
        assertThat(testPartyNote.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartyNote.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPartyNote.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPartyNote.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPartyNote() throws Exception {
        int databaseSizeBeforeUpdate = partyNoteRepository.findAll().size();
        partyNote.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partyNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyNote))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyNote in the database
        List<PartyNote> partyNoteList = partyNoteRepository.findAll();
        assertThat(partyNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartyNote() throws Exception {
        int databaseSizeBeforeUpdate = partyNoteRepository.findAll().size();
        partyNote.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partyNote))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartyNote in the database
        List<PartyNote> partyNoteList = partyNoteRepository.findAll();
        assertThat(partyNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartyNote() throws Exception {
        int databaseSizeBeforeUpdate = partyNoteRepository.findAll().size();
        partyNote.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartyNoteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partyNote))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartyNote in the database
        List<PartyNote> partyNoteList = partyNoteRepository.findAll();
        assertThat(partyNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartyNote() throws Exception {
        // Initialize the database
        partyNoteRepository.saveAndFlush(partyNote);

        int databaseSizeBeforeDelete = partyNoteRepository.findAll().size();

        // Delete the partyNote
        restPartyNoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, partyNote.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyNote> partyNoteList = partyNoteRepository.findAll();
        assertThat(partyNoteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
