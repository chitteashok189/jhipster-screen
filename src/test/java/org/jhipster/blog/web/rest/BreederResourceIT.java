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
import org.jhipster.blog.domain.Breeder;
import org.jhipster.blog.repository.BreederRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BreederResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BreederResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_BREEDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BREEDER_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_BREEDER_TYPE = 1L;
    private static final Long UPDATED_BREEDER_TYPE = 2L;

    private static final Long DEFAULT_BREEDER_STATUS = 1L;
    private static final Long UPDATED_BREEDER_STATUS = 2L;

    private static final String DEFAULT_BREEDER_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_BREEDER_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/breeders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BreederRepository breederRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBreederMockMvc;

    private Breeder breeder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Breeder createEntity(EntityManager em) {
        Breeder breeder = new Breeder()
            .gUID(DEFAULT_G_UID)
            .breederName(DEFAULT_BREEDER_NAME)
            .breederType(DEFAULT_BREEDER_TYPE)
            .breederStatus(DEFAULT_BREEDER_STATUS)
            .breederDescription(DEFAULT_BREEDER_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return breeder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Breeder createUpdatedEntity(EntityManager em) {
        Breeder breeder = new Breeder()
            .gUID(UPDATED_G_UID)
            .breederName(UPDATED_BREEDER_NAME)
            .breederType(UPDATED_BREEDER_TYPE)
            .breederStatus(UPDATED_BREEDER_STATUS)
            .breederDescription(UPDATED_BREEDER_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return breeder;
    }

    @BeforeEach
    public void initTest() {
        breeder = createEntity(em);
    }

    @Test
    @Transactional
    void createBreeder() throws Exception {
        int databaseSizeBeforeCreate = breederRepository.findAll().size();
        // Create the Breeder
        restBreederMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(breeder)))
            .andExpect(status().isCreated());

        // Validate the Breeder in the database
        List<Breeder> breederList = breederRepository.findAll();
        assertThat(breederList).hasSize(databaseSizeBeforeCreate + 1);
        Breeder testBreeder = breederList.get(breederList.size() - 1);
        assertThat(testBreeder.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testBreeder.getBreederName()).isEqualTo(DEFAULT_BREEDER_NAME);
        assertThat(testBreeder.getBreederType()).isEqualTo(DEFAULT_BREEDER_TYPE);
        assertThat(testBreeder.getBreederStatus()).isEqualTo(DEFAULT_BREEDER_STATUS);
        assertThat(testBreeder.getBreederDescription()).isEqualTo(DEFAULT_BREEDER_DESCRIPTION);
        assertThat(testBreeder.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBreeder.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testBreeder.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testBreeder.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createBreederWithExistingId() throws Exception {
        // Create the Breeder with an existing ID
        breeder.setId(1L);

        int databaseSizeBeforeCreate = breederRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBreederMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(breeder)))
            .andExpect(status().isBadRequest());

        // Validate the Breeder in the database
        List<Breeder> breederList = breederRepository.findAll();
        assertThat(breederList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBreeders() throws Exception {
        // Initialize the database
        breederRepository.saveAndFlush(breeder);

        // Get all the breederList
        restBreederMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(breeder.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].breederName").value(hasItem(DEFAULT_BREEDER_NAME)))
            .andExpect(jsonPath("$.[*].breederType").value(hasItem(DEFAULT_BREEDER_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].breederStatus").value(hasItem(DEFAULT_BREEDER_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].breederDescription").value(hasItem(DEFAULT_BREEDER_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getBreeder() throws Exception {
        // Initialize the database
        breederRepository.saveAndFlush(breeder);

        // Get the breeder
        restBreederMockMvc
            .perform(get(ENTITY_API_URL_ID, breeder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(breeder.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.breederName").value(DEFAULT_BREEDER_NAME))
            .andExpect(jsonPath("$.breederType").value(DEFAULT_BREEDER_TYPE.intValue()))
            .andExpect(jsonPath("$.breederStatus").value(DEFAULT_BREEDER_STATUS.intValue()))
            .andExpect(jsonPath("$.breederDescription").value(DEFAULT_BREEDER_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingBreeder() throws Exception {
        // Get the breeder
        restBreederMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBreeder() throws Exception {
        // Initialize the database
        breederRepository.saveAndFlush(breeder);

        int databaseSizeBeforeUpdate = breederRepository.findAll().size();

        // Update the breeder
        Breeder updatedBreeder = breederRepository.findById(breeder.getId()).get();
        // Disconnect from session so that the updates on updatedBreeder are not directly saved in db
        em.detach(updatedBreeder);
        updatedBreeder
            .gUID(UPDATED_G_UID)
            .breederName(UPDATED_BREEDER_NAME)
            .breederType(UPDATED_BREEDER_TYPE)
            .breederStatus(UPDATED_BREEDER_STATUS)
            .breederDescription(UPDATED_BREEDER_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restBreederMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBreeder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBreeder))
            )
            .andExpect(status().isOk());

        // Validate the Breeder in the database
        List<Breeder> breederList = breederRepository.findAll();
        assertThat(breederList).hasSize(databaseSizeBeforeUpdate);
        Breeder testBreeder = breederList.get(breederList.size() - 1);
        assertThat(testBreeder.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testBreeder.getBreederName()).isEqualTo(UPDATED_BREEDER_NAME);
        assertThat(testBreeder.getBreederType()).isEqualTo(UPDATED_BREEDER_TYPE);
        assertThat(testBreeder.getBreederStatus()).isEqualTo(UPDATED_BREEDER_STATUS);
        assertThat(testBreeder.getBreederDescription()).isEqualTo(UPDATED_BREEDER_DESCRIPTION);
        assertThat(testBreeder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBreeder.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBreeder.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testBreeder.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingBreeder() throws Exception {
        int databaseSizeBeforeUpdate = breederRepository.findAll().size();
        breeder.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBreederMockMvc
            .perform(
                put(ENTITY_API_URL_ID, breeder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(breeder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Breeder in the database
        List<Breeder> breederList = breederRepository.findAll();
        assertThat(breederList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBreeder() throws Exception {
        int databaseSizeBeforeUpdate = breederRepository.findAll().size();
        breeder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBreederMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(breeder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Breeder in the database
        List<Breeder> breederList = breederRepository.findAll();
        assertThat(breederList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBreeder() throws Exception {
        int databaseSizeBeforeUpdate = breederRepository.findAll().size();
        breeder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBreederMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(breeder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Breeder in the database
        List<Breeder> breederList = breederRepository.findAll();
        assertThat(breederList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBreederWithPatch() throws Exception {
        // Initialize the database
        breederRepository.saveAndFlush(breeder);

        int databaseSizeBeforeUpdate = breederRepository.findAll().size();

        // Update the breeder using partial update
        Breeder partialUpdatedBreeder = new Breeder();
        partialUpdatedBreeder.setId(breeder.getId());

        partialUpdatedBreeder
            .gUID(UPDATED_G_UID)
            .breederType(UPDATED_BREEDER_TYPE)
            .breederDescription(UPDATED_BREEDER_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restBreederMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBreeder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBreeder))
            )
            .andExpect(status().isOk());

        // Validate the Breeder in the database
        List<Breeder> breederList = breederRepository.findAll();
        assertThat(breederList).hasSize(databaseSizeBeforeUpdate);
        Breeder testBreeder = breederList.get(breederList.size() - 1);
        assertThat(testBreeder.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testBreeder.getBreederName()).isEqualTo(DEFAULT_BREEDER_NAME);
        assertThat(testBreeder.getBreederType()).isEqualTo(UPDATED_BREEDER_TYPE);
        assertThat(testBreeder.getBreederStatus()).isEqualTo(DEFAULT_BREEDER_STATUS);
        assertThat(testBreeder.getBreederDescription()).isEqualTo(UPDATED_BREEDER_DESCRIPTION);
        assertThat(testBreeder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBreeder.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBreeder.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testBreeder.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateBreederWithPatch() throws Exception {
        // Initialize the database
        breederRepository.saveAndFlush(breeder);

        int databaseSizeBeforeUpdate = breederRepository.findAll().size();

        // Update the breeder using partial update
        Breeder partialUpdatedBreeder = new Breeder();
        partialUpdatedBreeder.setId(breeder.getId());

        partialUpdatedBreeder
            .gUID(UPDATED_G_UID)
            .breederName(UPDATED_BREEDER_NAME)
            .breederType(UPDATED_BREEDER_TYPE)
            .breederStatus(UPDATED_BREEDER_STATUS)
            .breederDescription(UPDATED_BREEDER_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restBreederMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBreeder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBreeder))
            )
            .andExpect(status().isOk());

        // Validate the Breeder in the database
        List<Breeder> breederList = breederRepository.findAll();
        assertThat(breederList).hasSize(databaseSizeBeforeUpdate);
        Breeder testBreeder = breederList.get(breederList.size() - 1);
        assertThat(testBreeder.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testBreeder.getBreederName()).isEqualTo(UPDATED_BREEDER_NAME);
        assertThat(testBreeder.getBreederType()).isEqualTo(UPDATED_BREEDER_TYPE);
        assertThat(testBreeder.getBreederStatus()).isEqualTo(UPDATED_BREEDER_STATUS);
        assertThat(testBreeder.getBreederDescription()).isEqualTo(UPDATED_BREEDER_DESCRIPTION);
        assertThat(testBreeder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBreeder.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBreeder.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testBreeder.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingBreeder() throws Exception {
        int databaseSizeBeforeUpdate = breederRepository.findAll().size();
        breeder.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBreederMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, breeder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(breeder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Breeder in the database
        List<Breeder> breederList = breederRepository.findAll();
        assertThat(breederList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBreeder() throws Exception {
        int databaseSizeBeforeUpdate = breederRepository.findAll().size();
        breeder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBreederMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(breeder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Breeder in the database
        List<Breeder> breederList = breederRepository.findAll();
        assertThat(breederList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBreeder() throws Exception {
        int databaseSizeBeforeUpdate = breederRepository.findAll().size();
        breeder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBreederMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(breeder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Breeder in the database
        List<Breeder> breederList = breederRepository.findAll();
        assertThat(breederList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBreeder() throws Exception {
        // Initialize the database
        breederRepository.saveAndFlush(breeder);

        int databaseSizeBeforeDelete = breederRepository.findAll().size();

        // Delete the breeder
        restBreederMockMvc
            .perform(delete(ENTITY_API_URL_ID, breeder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Breeder> breederList = breederRepository.findAll();
        assertThat(breederList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
