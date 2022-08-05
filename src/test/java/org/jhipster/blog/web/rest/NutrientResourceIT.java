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
import org.jhipster.blog.domain.Nutrient;
import org.jhipster.blog.domain.enumeration.NutForms;
import org.jhipster.blog.domain.enumeration.NutTypeID;
import org.jhipster.blog.domain.enumeration.NutrientType;
import org.jhipster.blog.repository.NutrientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NutrientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NutrientResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_NUTRIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NUTRIENT_NAME = "BBBBBBBBBB";

    private static final NutrientType DEFAULT_TYPE = NutrientType.Organic;
    private static final NutrientType UPDATED_TYPE = NutrientType.Synthetic;

    private static final String DEFAULT_BRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUTRIENT_LABEL = 1;
    private static final Integer UPDATED_NUTRIENT_LABEL = 2;

    private static final NutForms DEFAULT_NUTRIENT_FORMS = NutForms.Solid;
    private static final NutForms UPDATED_NUTRIENT_FORMS = NutForms.Or;

    private static final NutTypeID DEFAULT_NUTRIENT_TYPE_ID = NutTypeID.Inorganic;
    private static final NutTypeID UPDATED_NUTRIENT_TYPE_ID = NutTypeID.Mineral;

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/nutrients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NutrientRepository nutrientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNutrientMockMvc;

    private Nutrient nutrient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nutrient createEntity(EntityManager em) {
        Nutrient nutrient = new Nutrient()
            .gUID(DEFAULT_G_UID)
            .nutrientName(DEFAULT_NUTRIENT_NAME)
            .type(DEFAULT_TYPE)
            .brandName(DEFAULT_BRAND_NAME)
            .nutrientLabel(DEFAULT_NUTRIENT_LABEL)
            .nutrientForms(DEFAULT_NUTRIENT_FORMS)
            .nutrientTypeID(DEFAULT_NUTRIENT_TYPE_ID)
            .price(DEFAULT_PRICE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return nutrient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nutrient createUpdatedEntity(EntityManager em) {
        Nutrient nutrient = new Nutrient()
            .gUID(UPDATED_G_UID)
            .nutrientName(UPDATED_NUTRIENT_NAME)
            .type(UPDATED_TYPE)
            .brandName(UPDATED_BRAND_NAME)
            .nutrientLabel(UPDATED_NUTRIENT_LABEL)
            .nutrientForms(UPDATED_NUTRIENT_FORMS)
            .nutrientTypeID(UPDATED_NUTRIENT_TYPE_ID)
            .price(UPDATED_PRICE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return nutrient;
    }

    @BeforeEach
    public void initTest() {
        nutrient = createEntity(em);
    }

    @Test
    @Transactional
    void createNutrient() throws Exception {
        int databaseSizeBeforeCreate = nutrientRepository.findAll().size();
        // Create the Nutrient
        restNutrientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutrient)))
            .andExpect(status().isCreated());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeCreate + 1);
        Nutrient testNutrient = nutrientList.get(nutrientList.size() - 1);
        assertThat(testNutrient.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testNutrient.getNutrientName()).isEqualTo(DEFAULT_NUTRIENT_NAME);
        assertThat(testNutrient.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNutrient.getBrandName()).isEqualTo(DEFAULT_BRAND_NAME);
        assertThat(testNutrient.getNutrientLabel()).isEqualTo(DEFAULT_NUTRIENT_LABEL);
        assertThat(testNutrient.getNutrientForms()).isEqualTo(DEFAULT_NUTRIENT_FORMS);
        assertThat(testNutrient.getNutrientTypeID()).isEqualTo(DEFAULT_NUTRIENT_TYPE_ID);
        assertThat(testNutrient.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testNutrient.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNutrient.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testNutrient.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testNutrient.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createNutrientWithExistingId() throws Exception {
        // Create the Nutrient with an existing ID
        nutrient.setId(1L);

        int databaseSizeBeforeCreate = nutrientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNutrientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutrient)))
            .andExpect(status().isBadRequest());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNutrients() throws Exception {
        // Initialize the database
        nutrientRepository.saveAndFlush(nutrient);

        // Get all the nutrientList
        restNutrientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nutrient.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].nutrientName").value(hasItem(DEFAULT_NUTRIENT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].nutrientLabel").value(hasItem(DEFAULT_NUTRIENT_LABEL)))
            .andExpect(jsonPath("$.[*].nutrientForms").value(hasItem(DEFAULT_NUTRIENT_FORMS.toString())))
            .andExpect(jsonPath("$.[*].nutrientTypeID").value(hasItem(DEFAULT_NUTRIENT_TYPE_ID.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getNutrient() throws Exception {
        // Initialize the database
        nutrientRepository.saveAndFlush(nutrient);

        // Get the nutrient
        restNutrientMockMvc
            .perform(get(ENTITY_API_URL_ID, nutrient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nutrient.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.nutrientName").value(DEFAULT_NUTRIENT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.brandName").value(DEFAULT_BRAND_NAME))
            .andExpect(jsonPath("$.nutrientLabel").value(DEFAULT_NUTRIENT_LABEL))
            .andExpect(jsonPath("$.nutrientForms").value(DEFAULT_NUTRIENT_FORMS.toString()))
            .andExpect(jsonPath("$.nutrientTypeID").value(DEFAULT_NUTRIENT_TYPE_ID.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingNutrient() throws Exception {
        // Get the nutrient
        restNutrientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNutrient() throws Exception {
        // Initialize the database
        nutrientRepository.saveAndFlush(nutrient);

        int databaseSizeBeforeUpdate = nutrientRepository.findAll().size();

        // Update the nutrient
        Nutrient updatedNutrient = nutrientRepository.findById(nutrient.getId()).get();
        // Disconnect from session so that the updates on updatedNutrient are not directly saved in db
        em.detach(updatedNutrient);
        updatedNutrient
            .gUID(UPDATED_G_UID)
            .nutrientName(UPDATED_NUTRIENT_NAME)
            .type(UPDATED_TYPE)
            .brandName(UPDATED_BRAND_NAME)
            .nutrientLabel(UPDATED_NUTRIENT_LABEL)
            .nutrientForms(UPDATED_NUTRIENT_FORMS)
            .nutrientTypeID(UPDATED_NUTRIENT_TYPE_ID)
            .price(UPDATED_PRICE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restNutrientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNutrient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNutrient))
            )
            .andExpect(status().isOk());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeUpdate);
        Nutrient testNutrient = nutrientList.get(nutrientList.size() - 1);
        assertThat(testNutrient.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testNutrient.getNutrientName()).isEqualTo(UPDATED_NUTRIENT_NAME);
        assertThat(testNutrient.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNutrient.getBrandName()).isEqualTo(UPDATED_BRAND_NAME);
        assertThat(testNutrient.getNutrientLabel()).isEqualTo(UPDATED_NUTRIENT_LABEL);
        assertThat(testNutrient.getNutrientForms()).isEqualTo(UPDATED_NUTRIENT_FORMS);
        assertThat(testNutrient.getNutrientTypeID()).isEqualTo(UPDATED_NUTRIENT_TYPE_ID);
        assertThat(testNutrient.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testNutrient.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNutrient.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testNutrient.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testNutrient.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingNutrient() throws Exception {
        int databaseSizeBeforeUpdate = nutrientRepository.findAll().size();
        nutrient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutrientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nutrient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nutrient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNutrient() throws Exception {
        int databaseSizeBeforeUpdate = nutrientRepository.findAll().size();
        nutrient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutrientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nutrient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNutrient() throws Exception {
        int databaseSizeBeforeUpdate = nutrientRepository.findAll().size();
        nutrient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutrientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutrient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNutrientWithPatch() throws Exception {
        // Initialize the database
        nutrientRepository.saveAndFlush(nutrient);

        int databaseSizeBeforeUpdate = nutrientRepository.findAll().size();

        // Update the nutrient using partial update
        Nutrient partialUpdatedNutrient = new Nutrient();
        partialUpdatedNutrient.setId(nutrient.getId());

        partialUpdatedNutrient
            .brandName(UPDATED_BRAND_NAME)
            .nutrientForms(UPDATED_NUTRIENT_FORMS)
            .price(UPDATED_PRICE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restNutrientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNutrient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNutrient))
            )
            .andExpect(status().isOk());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeUpdate);
        Nutrient testNutrient = nutrientList.get(nutrientList.size() - 1);
        assertThat(testNutrient.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testNutrient.getNutrientName()).isEqualTo(DEFAULT_NUTRIENT_NAME);
        assertThat(testNutrient.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNutrient.getBrandName()).isEqualTo(UPDATED_BRAND_NAME);
        assertThat(testNutrient.getNutrientLabel()).isEqualTo(DEFAULT_NUTRIENT_LABEL);
        assertThat(testNutrient.getNutrientForms()).isEqualTo(UPDATED_NUTRIENT_FORMS);
        assertThat(testNutrient.getNutrientTypeID()).isEqualTo(DEFAULT_NUTRIENT_TYPE_ID);
        assertThat(testNutrient.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testNutrient.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNutrient.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testNutrient.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testNutrient.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateNutrientWithPatch() throws Exception {
        // Initialize the database
        nutrientRepository.saveAndFlush(nutrient);

        int databaseSizeBeforeUpdate = nutrientRepository.findAll().size();

        // Update the nutrient using partial update
        Nutrient partialUpdatedNutrient = new Nutrient();
        partialUpdatedNutrient.setId(nutrient.getId());

        partialUpdatedNutrient
            .gUID(UPDATED_G_UID)
            .nutrientName(UPDATED_NUTRIENT_NAME)
            .type(UPDATED_TYPE)
            .brandName(UPDATED_BRAND_NAME)
            .nutrientLabel(UPDATED_NUTRIENT_LABEL)
            .nutrientForms(UPDATED_NUTRIENT_FORMS)
            .nutrientTypeID(UPDATED_NUTRIENT_TYPE_ID)
            .price(UPDATED_PRICE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restNutrientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNutrient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNutrient))
            )
            .andExpect(status().isOk());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeUpdate);
        Nutrient testNutrient = nutrientList.get(nutrientList.size() - 1);
        assertThat(testNutrient.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testNutrient.getNutrientName()).isEqualTo(UPDATED_NUTRIENT_NAME);
        assertThat(testNutrient.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNutrient.getBrandName()).isEqualTo(UPDATED_BRAND_NAME);
        assertThat(testNutrient.getNutrientLabel()).isEqualTo(UPDATED_NUTRIENT_LABEL);
        assertThat(testNutrient.getNutrientForms()).isEqualTo(UPDATED_NUTRIENT_FORMS);
        assertThat(testNutrient.getNutrientTypeID()).isEqualTo(UPDATED_NUTRIENT_TYPE_ID);
        assertThat(testNutrient.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testNutrient.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNutrient.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testNutrient.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testNutrient.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingNutrient() throws Exception {
        int databaseSizeBeforeUpdate = nutrientRepository.findAll().size();
        nutrient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutrientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nutrient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutrient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNutrient() throws Exception {
        int databaseSizeBeforeUpdate = nutrientRepository.findAll().size();
        nutrient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutrientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutrient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNutrient() throws Exception {
        int databaseSizeBeforeUpdate = nutrientRepository.findAll().size();
        nutrient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutrientMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nutrient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNutrient() throws Exception {
        // Initialize the database
        nutrientRepository.saveAndFlush(nutrient);

        int databaseSizeBeforeDelete = nutrientRepository.findAll().size();

        // Delete the nutrient
        restNutrientMockMvc
            .perform(delete(ENTITY_API_URL_ID, nutrient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
