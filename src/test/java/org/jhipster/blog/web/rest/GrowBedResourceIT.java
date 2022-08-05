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
import org.jhipster.blog.domain.GrowBed;
import org.jhipster.blog.domain.enumeration.GrowBedType;
import org.jhipster.blog.repository.GrowBedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GrowBedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GrowBedResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final GrowBedType DEFAULT_GROW_BED_TYPE = GrowBedType.Vertical;
    private static final GrowBedType UPDATED_GROW_BED_TYPE = GrowBedType.Rack;

    private static final String DEFAULT_GROW_BED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROW_BED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/grow-beds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GrowBedRepository growBedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrowBedMockMvc;

    private GrowBed growBed;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrowBed createEntity(EntityManager em) {
        GrowBed growBed = new GrowBed()
            .gUID(DEFAULT_G_UID)
            .growBedType(DEFAULT_GROW_BED_TYPE)
            .growBedName(DEFAULT_GROW_BED_NAME)
            .manufacturer(DEFAULT_MANUFACTURER)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return growBed;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrowBed createUpdatedEntity(EntityManager em) {
        GrowBed growBed = new GrowBed()
            .gUID(UPDATED_G_UID)
            .growBedType(UPDATED_GROW_BED_TYPE)
            .growBedName(UPDATED_GROW_BED_NAME)
            .manufacturer(UPDATED_MANUFACTURER)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return growBed;
    }

    @BeforeEach
    public void initTest() {
        growBed = createEntity(em);
    }

    @Test
    @Transactional
    void createGrowBed() throws Exception {
        int databaseSizeBeforeCreate = growBedRepository.findAll().size();
        // Create the GrowBed
        restGrowBedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(growBed)))
            .andExpect(status().isCreated());

        // Validate the GrowBed in the database
        List<GrowBed> growBedList = growBedRepository.findAll();
        assertThat(growBedList).hasSize(databaseSizeBeforeCreate + 1);
        GrowBed testGrowBed = growBedList.get(growBedList.size() - 1);
        assertThat(testGrowBed.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testGrowBed.getGrowBedType()).isEqualTo(DEFAULT_GROW_BED_TYPE);
        assertThat(testGrowBed.getGrowBedName()).isEqualTo(DEFAULT_GROW_BED_NAME);
        assertThat(testGrowBed.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testGrowBed.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testGrowBed.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testGrowBed.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testGrowBed.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createGrowBedWithExistingId() throws Exception {
        // Create the GrowBed with an existing ID
        growBed.setId(1L);

        int databaseSizeBeforeCreate = growBedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrowBedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(growBed)))
            .andExpect(status().isBadRequest());

        // Validate the GrowBed in the database
        List<GrowBed> growBedList = growBedRepository.findAll();
        assertThat(growBedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGrowBeds() throws Exception {
        // Initialize the database
        growBedRepository.saveAndFlush(growBed);

        // Get all the growBedList
        restGrowBedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(growBed.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].growBedType").value(hasItem(DEFAULT_GROW_BED_TYPE.toString())))
            .andExpect(jsonPath("$.[*].growBedName").value(hasItem(DEFAULT_GROW_BED_NAME)))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getGrowBed() throws Exception {
        // Initialize the database
        growBedRepository.saveAndFlush(growBed);

        // Get the growBed
        restGrowBedMockMvc
            .perform(get(ENTITY_API_URL_ID, growBed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(growBed.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.growBedType").value(DEFAULT_GROW_BED_TYPE.toString()))
            .andExpect(jsonPath("$.growBedName").value(DEFAULT_GROW_BED_NAME))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingGrowBed() throws Exception {
        // Get the growBed
        restGrowBedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGrowBed() throws Exception {
        // Initialize the database
        growBedRepository.saveAndFlush(growBed);

        int databaseSizeBeforeUpdate = growBedRepository.findAll().size();

        // Update the growBed
        GrowBed updatedGrowBed = growBedRepository.findById(growBed.getId()).get();
        // Disconnect from session so that the updates on updatedGrowBed are not directly saved in db
        em.detach(updatedGrowBed);
        updatedGrowBed
            .gUID(UPDATED_G_UID)
            .growBedType(UPDATED_GROW_BED_TYPE)
            .growBedName(UPDATED_GROW_BED_NAME)
            .manufacturer(UPDATED_MANUFACTURER)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restGrowBedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrowBed.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGrowBed))
            )
            .andExpect(status().isOk());

        // Validate the GrowBed in the database
        List<GrowBed> growBedList = growBedRepository.findAll();
        assertThat(growBedList).hasSize(databaseSizeBeforeUpdate);
        GrowBed testGrowBed = growBedList.get(growBedList.size() - 1);
        assertThat(testGrowBed.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testGrowBed.getGrowBedType()).isEqualTo(UPDATED_GROW_BED_TYPE);
        assertThat(testGrowBed.getGrowBedName()).isEqualTo(UPDATED_GROW_BED_NAME);
        assertThat(testGrowBed.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testGrowBed.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testGrowBed.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testGrowBed.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testGrowBed.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingGrowBed() throws Exception {
        int databaseSizeBeforeUpdate = growBedRepository.findAll().size();
        growBed.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrowBedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, growBed.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(growBed))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrowBed in the database
        List<GrowBed> growBedList = growBedRepository.findAll();
        assertThat(growBedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrowBed() throws Exception {
        int databaseSizeBeforeUpdate = growBedRepository.findAll().size();
        growBed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrowBedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(growBed))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrowBed in the database
        List<GrowBed> growBedList = growBedRepository.findAll();
        assertThat(growBedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrowBed() throws Exception {
        int databaseSizeBeforeUpdate = growBedRepository.findAll().size();
        growBed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrowBedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(growBed)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrowBed in the database
        List<GrowBed> growBedList = growBedRepository.findAll();
        assertThat(growBedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrowBedWithPatch() throws Exception {
        // Initialize the database
        growBedRepository.saveAndFlush(growBed);

        int databaseSizeBeforeUpdate = growBedRepository.findAll().size();

        // Update the growBed using partial update
        GrowBed partialUpdatedGrowBed = new GrowBed();
        partialUpdatedGrowBed.setId(growBed.getId());

        partialUpdatedGrowBed
            .growBedType(UPDATED_GROW_BED_TYPE)
            .growBedName(UPDATED_GROW_BED_NAME)
            .manufacturer(UPDATED_MANUFACTURER)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restGrowBedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrowBed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrowBed))
            )
            .andExpect(status().isOk());

        // Validate the GrowBed in the database
        List<GrowBed> growBedList = growBedRepository.findAll();
        assertThat(growBedList).hasSize(databaseSizeBeforeUpdate);
        GrowBed testGrowBed = growBedList.get(growBedList.size() - 1);
        assertThat(testGrowBed.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testGrowBed.getGrowBedType()).isEqualTo(UPDATED_GROW_BED_TYPE);
        assertThat(testGrowBed.getGrowBedName()).isEqualTo(UPDATED_GROW_BED_NAME);
        assertThat(testGrowBed.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testGrowBed.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testGrowBed.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testGrowBed.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testGrowBed.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateGrowBedWithPatch() throws Exception {
        // Initialize the database
        growBedRepository.saveAndFlush(growBed);

        int databaseSizeBeforeUpdate = growBedRepository.findAll().size();

        // Update the growBed using partial update
        GrowBed partialUpdatedGrowBed = new GrowBed();
        partialUpdatedGrowBed.setId(growBed.getId());

        partialUpdatedGrowBed
            .gUID(UPDATED_G_UID)
            .growBedType(UPDATED_GROW_BED_TYPE)
            .growBedName(UPDATED_GROW_BED_NAME)
            .manufacturer(UPDATED_MANUFACTURER)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restGrowBedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrowBed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrowBed))
            )
            .andExpect(status().isOk());

        // Validate the GrowBed in the database
        List<GrowBed> growBedList = growBedRepository.findAll();
        assertThat(growBedList).hasSize(databaseSizeBeforeUpdate);
        GrowBed testGrowBed = growBedList.get(growBedList.size() - 1);
        assertThat(testGrowBed.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testGrowBed.getGrowBedType()).isEqualTo(UPDATED_GROW_BED_TYPE);
        assertThat(testGrowBed.getGrowBedName()).isEqualTo(UPDATED_GROW_BED_NAME);
        assertThat(testGrowBed.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testGrowBed.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testGrowBed.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testGrowBed.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testGrowBed.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingGrowBed() throws Exception {
        int databaseSizeBeforeUpdate = growBedRepository.findAll().size();
        growBed.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrowBedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, growBed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(growBed))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrowBed in the database
        List<GrowBed> growBedList = growBedRepository.findAll();
        assertThat(growBedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrowBed() throws Exception {
        int databaseSizeBeforeUpdate = growBedRepository.findAll().size();
        growBed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrowBedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(growBed))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrowBed in the database
        List<GrowBed> growBedList = growBedRepository.findAll();
        assertThat(growBedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrowBed() throws Exception {
        int databaseSizeBeforeUpdate = growBedRepository.findAll().size();
        growBed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrowBedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(growBed)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrowBed in the database
        List<GrowBed> growBedList = growBedRepository.findAll();
        assertThat(growBedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrowBed() throws Exception {
        // Initialize the database
        growBedRepository.saveAndFlush(growBed);

        int databaseSizeBeforeDelete = growBedRepository.findAll().size();

        // Delete the growBed
        restGrowBedMockMvc
            .perform(delete(ENTITY_API_URL_ID, growBed.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrowBed> growBedList = growBedRepository.findAll();
        assertThat(growBedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
