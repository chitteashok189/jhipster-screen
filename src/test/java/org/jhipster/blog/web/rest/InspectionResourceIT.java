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
import org.jhipster.blog.domain.Inspection;
import org.jhipster.blog.domain.enumeration.Aroma;
import org.jhipster.blog.domain.enumeration.Defect;
import org.jhipster.blog.domain.enumeration.Flavour;
import org.jhipster.blog.domain.enumeration.NutritionalValueType;
import org.jhipster.blog.domain.enumeration.Texture;
import org.jhipster.blog.repository.InspectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InspectionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InspectionResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Integer DEFAULT_INSPECTION_SIZE = 1;
    private static final Integer UPDATED_INSPECTION_SIZE = 2;

    private static final Integer DEFAULT_SHAPE = 1;
    private static final Integer UPDATED_SHAPE = 2;

    private static final Integer DEFAULT_WHOLENESS = 1;
    private static final Integer UPDATED_WHOLENESS = 2;

    private static final Integer DEFAULT_GLOSS = 1;
    private static final Integer UPDATED_GLOSS = 2;

    private static final Integer DEFAULT_CONSISTENCY = 1;
    private static final Integer UPDATED_CONSISTENCY = 2;

    private static final Defect DEFAULT_DEFECTS = Defect.Blemishes;
    private static final Defect UPDATED_DEFECTS = Defect.Bruises;

    private static final String DEFAULT_COLOUR = "AAAAAAAAAA";
    private static final String UPDATED_COLOUR = "BBBBBBBBBB";

    private static final Texture DEFAULT_TEXTURE = Texture.Mealiness;
    private static final Texture UPDATED_TEXTURE = Texture.Succulence;

    private static final Aroma DEFAULT_AROMA = Aroma.Ripeness;
    private static final Aroma UPDATED_AROMA = Aroma.Sweetness;

    private static final Flavour DEFAULT_FLAVOUR = Flavour.Sweet;
    private static final Flavour UPDATED_FLAVOUR = Flavour.Sour;

    private static final Integer DEFAULT_NUTRITIONAL_VALUE = 1;
    private static final Integer UPDATED_NUTRITIONAL_VALUE = 2;

    private static final NutritionalValueType DEFAULT_NUTRITIONAL_VALUE_TYPE = NutritionalValueType.Vitamin;
    private static final NutritionalValueType UPDATED_NUTRITIONAL_VALUE_TYPE = NutritionalValueType.A;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/inspections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InspectionRepository inspectionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInspectionMockMvc;

    private Inspection inspection;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inspection createEntity(EntityManager em) {
        Inspection inspection = new Inspection()
            .gUID(DEFAULT_G_UID)
            .inspectionSize(DEFAULT_INSPECTION_SIZE)
            .shape(DEFAULT_SHAPE)
            .wholeness(DEFAULT_WHOLENESS)
            .gloss(DEFAULT_GLOSS)
            .consistency(DEFAULT_CONSISTENCY)
            .defects(DEFAULT_DEFECTS)
            .colour(DEFAULT_COLOUR)
            .texture(DEFAULT_TEXTURE)
            .aroma(DEFAULT_AROMA)
            .flavour(DEFAULT_FLAVOUR)
            .nutritionalValue(DEFAULT_NUTRITIONAL_VALUE)
            .nutritionalValueType(DEFAULT_NUTRITIONAL_VALUE_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return inspection;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inspection createUpdatedEntity(EntityManager em) {
        Inspection inspection = new Inspection()
            .gUID(UPDATED_G_UID)
            .inspectionSize(UPDATED_INSPECTION_SIZE)
            .shape(UPDATED_SHAPE)
            .wholeness(UPDATED_WHOLENESS)
            .gloss(UPDATED_GLOSS)
            .consistency(UPDATED_CONSISTENCY)
            .defects(UPDATED_DEFECTS)
            .colour(UPDATED_COLOUR)
            .texture(UPDATED_TEXTURE)
            .aroma(UPDATED_AROMA)
            .flavour(UPDATED_FLAVOUR)
            .nutritionalValue(UPDATED_NUTRITIONAL_VALUE)
            .nutritionalValueType(UPDATED_NUTRITIONAL_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return inspection;
    }

    @BeforeEach
    public void initTest() {
        inspection = createEntity(em);
    }

    @Test
    @Transactional
    void createInspection() throws Exception {
        int databaseSizeBeforeCreate = inspectionRepository.findAll().size();
        // Create the Inspection
        restInspectionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isCreated());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeCreate + 1);
        Inspection testInspection = inspectionList.get(inspectionList.size() - 1);
        assertThat(testInspection.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testInspection.getInspectionSize()).isEqualTo(DEFAULT_INSPECTION_SIZE);
        assertThat(testInspection.getShape()).isEqualTo(DEFAULT_SHAPE);
        assertThat(testInspection.getWholeness()).isEqualTo(DEFAULT_WHOLENESS);
        assertThat(testInspection.getGloss()).isEqualTo(DEFAULT_GLOSS);
        assertThat(testInspection.getConsistency()).isEqualTo(DEFAULT_CONSISTENCY);
        assertThat(testInspection.getDefects()).isEqualTo(DEFAULT_DEFECTS);
        assertThat(testInspection.getColour()).isEqualTo(DEFAULT_COLOUR);
        assertThat(testInspection.getTexture()).isEqualTo(DEFAULT_TEXTURE);
        assertThat(testInspection.getAroma()).isEqualTo(DEFAULT_AROMA);
        assertThat(testInspection.getFlavour()).isEqualTo(DEFAULT_FLAVOUR);
        assertThat(testInspection.getNutritionalValue()).isEqualTo(DEFAULT_NUTRITIONAL_VALUE);
        assertThat(testInspection.getNutritionalValueType()).isEqualTo(DEFAULT_NUTRITIONAL_VALUE_TYPE);
        assertThat(testInspection.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInspection.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testInspection.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testInspection.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createInspectionWithExistingId() throws Exception {
        // Create the Inspection with an existing ID
        inspection.setId(1L);

        int databaseSizeBeforeCreate = inspectionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isBadRequest());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInspections() throws Exception {
        // Initialize the database
        inspectionRepository.saveAndFlush(inspection);

        // Get all the inspectionList
        restInspectionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspection.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].inspectionSize").value(hasItem(DEFAULT_INSPECTION_SIZE)))
            .andExpect(jsonPath("$.[*].shape").value(hasItem(DEFAULT_SHAPE)))
            .andExpect(jsonPath("$.[*].wholeness").value(hasItem(DEFAULT_WHOLENESS)))
            .andExpect(jsonPath("$.[*].gloss").value(hasItem(DEFAULT_GLOSS)))
            .andExpect(jsonPath("$.[*].consistency").value(hasItem(DEFAULT_CONSISTENCY)))
            .andExpect(jsonPath("$.[*].defects").value(hasItem(DEFAULT_DEFECTS.toString())))
            .andExpect(jsonPath("$.[*].colour").value(hasItem(DEFAULT_COLOUR)))
            .andExpect(jsonPath("$.[*].texture").value(hasItem(DEFAULT_TEXTURE.toString())))
            .andExpect(jsonPath("$.[*].aroma").value(hasItem(DEFAULT_AROMA.toString())))
            .andExpect(jsonPath("$.[*].flavour").value(hasItem(DEFAULT_FLAVOUR.toString())))
            .andExpect(jsonPath("$.[*].nutritionalValue").value(hasItem(DEFAULT_NUTRITIONAL_VALUE)))
            .andExpect(jsonPath("$.[*].nutritionalValueType").value(hasItem(DEFAULT_NUTRITIONAL_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getInspection() throws Exception {
        // Initialize the database
        inspectionRepository.saveAndFlush(inspection);

        // Get the inspection
        restInspectionMockMvc
            .perform(get(ENTITY_API_URL_ID, inspection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inspection.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.inspectionSize").value(DEFAULT_INSPECTION_SIZE))
            .andExpect(jsonPath("$.shape").value(DEFAULT_SHAPE))
            .andExpect(jsonPath("$.wholeness").value(DEFAULT_WHOLENESS))
            .andExpect(jsonPath("$.gloss").value(DEFAULT_GLOSS))
            .andExpect(jsonPath("$.consistency").value(DEFAULT_CONSISTENCY))
            .andExpect(jsonPath("$.defects").value(DEFAULT_DEFECTS.toString()))
            .andExpect(jsonPath("$.colour").value(DEFAULT_COLOUR))
            .andExpect(jsonPath("$.texture").value(DEFAULT_TEXTURE.toString()))
            .andExpect(jsonPath("$.aroma").value(DEFAULT_AROMA.toString()))
            .andExpect(jsonPath("$.flavour").value(DEFAULT_FLAVOUR.toString()))
            .andExpect(jsonPath("$.nutritionalValue").value(DEFAULT_NUTRITIONAL_VALUE))
            .andExpect(jsonPath("$.nutritionalValueType").value(DEFAULT_NUTRITIONAL_VALUE_TYPE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingInspection() throws Exception {
        // Get the inspection
        restInspectionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInspection() throws Exception {
        // Initialize the database
        inspectionRepository.saveAndFlush(inspection);

        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();

        // Update the inspection
        Inspection updatedInspection = inspectionRepository.findById(inspection.getId()).get();
        // Disconnect from session so that the updates on updatedInspection are not directly saved in db
        em.detach(updatedInspection);
        updatedInspection
            .gUID(UPDATED_G_UID)
            .inspectionSize(UPDATED_INSPECTION_SIZE)
            .shape(UPDATED_SHAPE)
            .wholeness(UPDATED_WHOLENESS)
            .gloss(UPDATED_GLOSS)
            .consistency(UPDATED_CONSISTENCY)
            .defects(UPDATED_DEFECTS)
            .colour(UPDATED_COLOUR)
            .texture(UPDATED_TEXTURE)
            .aroma(UPDATED_AROMA)
            .flavour(UPDATED_FLAVOUR)
            .nutritionalValue(UPDATED_NUTRITIONAL_VALUE)
            .nutritionalValueType(UPDATED_NUTRITIONAL_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restInspectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInspection.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInspection))
            )
            .andExpect(status().isOk());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate);
        Inspection testInspection = inspectionList.get(inspectionList.size() - 1);
        assertThat(testInspection.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testInspection.getInspectionSize()).isEqualTo(UPDATED_INSPECTION_SIZE);
        assertThat(testInspection.getShape()).isEqualTo(UPDATED_SHAPE);
        assertThat(testInspection.getWholeness()).isEqualTo(UPDATED_WHOLENESS);
        assertThat(testInspection.getGloss()).isEqualTo(UPDATED_GLOSS);
        assertThat(testInspection.getConsistency()).isEqualTo(UPDATED_CONSISTENCY);
        assertThat(testInspection.getDefects()).isEqualTo(UPDATED_DEFECTS);
        assertThat(testInspection.getColour()).isEqualTo(UPDATED_COLOUR);
        assertThat(testInspection.getTexture()).isEqualTo(UPDATED_TEXTURE);
        assertThat(testInspection.getAroma()).isEqualTo(UPDATED_AROMA);
        assertThat(testInspection.getFlavour()).isEqualTo(UPDATED_FLAVOUR);
        assertThat(testInspection.getNutritionalValue()).isEqualTo(UPDATED_NUTRITIONAL_VALUE);
        assertThat(testInspection.getNutritionalValueType()).isEqualTo(UPDATED_NUTRITIONAL_VALUE_TYPE);
        assertThat(testInspection.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInspection.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testInspection.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testInspection.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingInspection() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();
        inspection.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inspection.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inspection))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInspection() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();
        inspection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inspection))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInspection() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();
        inspection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInspectionWithPatch() throws Exception {
        // Initialize the database
        inspectionRepository.saveAndFlush(inspection);

        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();

        // Update the inspection using partial update
        Inspection partialUpdatedInspection = new Inspection();
        partialUpdatedInspection.setId(inspection.getId());

        partialUpdatedInspection
            .gUID(UPDATED_G_UID)
            .shape(UPDATED_SHAPE)
            .wholeness(UPDATED_WHOLENESS)
            .gloss(UPDATED_GLOSS)
            .consistency(UPDATED_CONSISTENCY)
            .colour(UPDATED_COLOUR)
            .aroma(UPDATED_AROMA)
            .flavour(UPDATED_FLAVOUR)
            .nutritionalValue(UPDATED_NUTRITIONAL_VALUE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restInspectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInspection))
            )
            .andExpect(status().isOk());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate);
        Inspection testInspection = inspectionList.get(inspectionList.size() - 1);
        assertThat(testInspection.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testInspection.getInspectionSize()).isEqualTo(DEFAULT_INSPECTION_SIZE);
        assertThat(testInspection.getShape()).isEqualTo(UPDATED_SHAPE);
        assertThat(testInspection.getWholeness()).isEqualTo(UPDATED_WHOLENESS);
        assertThat(testInspection.getGloss()).isEqualTo(UPDATED_GLOSS);
        assertThat(testInspection.getConsistency()).isEqualTo(UPDATED_CONSISTENCY);
        assertThat(testInspection.getDefects()).isEqualTo(DEFAULT_DEFECTS);
        assertThat(testInspection.getColour()).isEqualTo(UPDATED_COLOUR);
        assertThat(testInspection.getTexture()).isEqualTo(DEFAULT_TEXTURE);
        assertThat(testInspection.getAroma()).isEqualTo(UPDATED_AROMA);
        assertThat(testInspection.getFlavour()).isEqualTo(UPDATED_FLAVOUR);
        assertThat(testInspection.getNutritionalValue()).isEqualTo(UPDATED_NUTRITIONAL_VALUE);
        assertThat(testInspection.getNutritionalValueType()).isEqualTo(DEFAULT_NUTRITIONAL_VALUE_TYPE);
        assertThat(testInspection.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInspection.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testInspection.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testInspection.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateInspectionWithPatch() throws Exception {
        // Initialize the database
        inspectionRepository.saveAndFlush(inspection);

        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();

        // Update the inspection using partial update
        Inspection partialUpdatedInspection = new Inspection();
        partialUpdatedInspection.setId(inspection.getId());

        partialUpdatedInspection
            .gUID(UPDATED_G_UID)
            .inspectionSize(UPDATED_INSPECTION_SIZE)
            .shape(UPDATED_SHAPE)
            .wholeness(UPDATED_WHOLENESS)
            .gloss(UPDATED_GLOSS)
            .consistency(UPDATED_CONSISTENCY)
            .defects(UPDATED_DEFECTS)
            .colour(UPDATED_COLOUR)
            .texture(UPDATED_TEXTURE)
            .aroma(UPDATED_AROMA)
            .flavour(UPDATED_FLAVOUR)
            .nutritionalValue(UPDATED_NUTRITIONAL_VALUE)
            .nutritionalValueType(UPDATED_NUTRITIONAL_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restInspectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInspection))
            )
            .andExpect(status().isOk());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate);
        Inspection testInspection = inspectionList.get(inspectionList.size() - 1);
        assertThat(testInspection.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testInspection.getInspectionSize()).isEqualTo(UPDATED_INSPECTION_SIZE);
        assertThat(testInspection.getShape()).isEqualTo(UPDATED_SHAPE);
        assertThat(testInspection.getWholeness()).isEqualTo(UPDATED_WHOLENESS);
        assertThat(testInspection.getGloss()).isEqualTo(UPDATED_GLOSS);
        assertThat(testInspection.getConsistency()).isEqualTo(UPDATED_CONSISTENCY);
        assertThat(testInspection.getDefects()).isEqualTo(UPDATED_DEFECTS);
        assertThat(testInspection.getColour()).isEqualTo(UPDATED_COLOUR);
        assertThat(testInspection.getTexture()).isEqualTo(UPDATED_TEXTURE);
        assertThat(testInspection.getAroma()).isEqualTo(UPDATED_AROMA);
        assertThat(testInspection.getFlavour()).isEqualTo(UPDATED_FLAVOUR);
        assertThat(testInspection.getNutritionalValue()).isEqualTo(UPDATED_NUTRITIONAL_VALUE);
        assertThat(testInspection.getNutritionalValueType()).isEqualTo(UPDATED_NUTRITIONAL_VALUE_TYPE);
        assertThat(testInspection.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInspection.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testInspection.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testInspection.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingInspection() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();
        inspection.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inspection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inspection))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInspection() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();
        inspection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inspection))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInspection() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();
        inspection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(inspection))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInspection() throws Exception {
        // Initialize the database
        inspectionRepository.saveAndFlush(inspection);

        int databaseSizeBeforeDelete = inspectionRepository.findAll().size();

        // Delete the inspection
        restInspectionMockMvc
            .perform(delete(ENTITY_API_URL_ID, inspection.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
