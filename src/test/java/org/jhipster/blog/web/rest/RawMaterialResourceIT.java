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
import org.jhipster.blog.domain.RawMaterial;
import org.jhipster.blog.domain.enumeration.MaterialType;
import org.jhipster.blog.repository.RawMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RawMaterialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RawMaterialResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Integer DEFAULT_U_OM = 1;
    private static final Integer UPDATED_U_OM = 2;

    private static final MaterialType DEFAULT_MATERIAL_TYPE = MaterialType.Vegetables;
    private static final MaterialType UPDATED_MATERIAL_TYPE = MaterialType.Flowers;

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/raw-materials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRawMaterialMockMvc;

    private RawMaterial rawMaterial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawMaterial createEntity(EntityManager em) {
        RawMaterial rawMaterial = new RawMaterial()
            .gUID(DEFAULT_G_UID)
            .quantity(DEFAULT_QUANTITY)
            .uOM(DEFAULT_U_OM)
            .materialType(DEFAULT_MATERIAL_TYPE)
            .price(DEFAULT_PRICE)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return rawMaterial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawMaterial createUpdatedEntity(EntityManager em) {
        RawMaterial rawMaterial = new RawMaterial()
            .gUID(UPDATED_G_UID)
            .quantity(UPDATED_QUANTITY)
            .uOM(UPDATED_U_OM)
            .materialType(UPDATED_MATERIAL_TYPE)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return rawMaterial;
    }

    @BeforeEach
    public void initTest() {
        rawMaterial = createEntity(em);
    }

    @Test
    @Transactional
    void createRawMaterial() throws Exception {
        int databaseSizeBeforeCreate = rawMaterialRepository.findAll().size();
        // Create the RawMaterial
        restRawMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rawMaterial)))
            .andExpect(status().isCreated());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        RawMaterial testRawMaterial = rawMaterialList.get(rawMaterialList.size() - 1);
        assertThat(testRawMaterial.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testRawMaterial.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testRawMaterial.getuOM()).isEqualTo(DEFAULT_U_OM);
        assertThat(testRawMaterial.getMaterialType()).isEqualTo(DEFAULT_MATERIAL_TYPE);
        assertThat(testRawMaterial.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testRawMaterial.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRawMaterial.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRawMaterial.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRawMaterial.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRawMaterial.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createRawMaterialWithExistingId() throws Exception {
        // Create the RawMaterial with an existing ID
        rawMaterial.setId(1L);

        int databaseSizeBeforeCreate = rawMaterialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRawMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rawMaterial)))
            .andExpect(status().isBadRequest());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRawMaterials() throws Exception {
        // Initialize the database
        rawMaterialRepository.saveAndFlush(rawMaterial);

        // Get all the rawMaterialList
        restRawMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rawMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].uOM").value(hasItem(DEFAULT_U_OM)))
            .andExpect(jsonPath("$.[*].materialType").value(hasItem(DEFAULT_MATERIAL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getRawMaterial() throws Exception {
        // Initialize the database
        rawMaterialRepository.saveAndFlush(rawMaterial);

        // Get the rawMaterial
        restRawMaterialMockMvc
            .perform(get(ENTITY_API_URL_ID, rawMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rawMaterial.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.uOM").value(DEFAULT_U_OM))
            .andExpect(jsonPath("$.materialType").value(DEFAULT_MATERIAL_TYPE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingRawMaterial() throws Exception {
        // Get the rawMaterial
        restRawMaterialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRawMaterial() throws Exception {
        // Initialize the database
        rawMaterialRepository.saveAndFlush(rawMaterial);

        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();

        // Update the rawMaterial
        RawMaterial updatedRawMaterial = rawMaterialRepository.findById(rawMaterial.getId()).get();
        // Disconnect from session so that the updates on updatedRawMaterial are not directly saved in db
        em.detach(updatedRawMaterial);
        updatedRawMaterial
            .gUID(UPDATED_G_UID)
            .quantity(UPDATED_QUANTITY)
            .uOM(UPDATED_U_OM)
            .materialType(UPDATED_MATERIAL_TYPE)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restRawMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRawMaterial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRawMaterial))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
        RawMaterial testRawMaterial = rawMaterialList.get(rawMaterialList.size() - 1);
        assertThat(testRawMaterial.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testRawMaterial.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testRawMaterial.getuOM()).isEqualTo(UPDATED_U_OM);
        assertThat(testRawMaterial.getMaterialType()).isEqualTo(UPDATED_MATERIAL_TYPE);
        assertThat(testRawMaterial.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testRawMaterial.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRawMaterial.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRawMaterial.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRawMaterial.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRawMaterial.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingRawMaterial() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();
        rawMaterial.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rawMaterial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRawMaterial() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();
        rawMaterial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRawMaterial() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();
        rawMaterial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rawMaterial)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRawMaterialWithPatch() throws Exception {
        // Initialize the database
        rawMaterialRepository.saveAndFlush(rawMaterial);

        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();

        // Update the rawMaterial using partial update
        RawMaterial partialUpdatedRawMaterial = new RawMaterial();
        partialUpdatedRawMaterial.setId(rawMaterial.getId());

        partialUpdatedRawMaterial
            .uOM(UPDATED_U_OM)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restRawMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRawMaterial))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
        RawMaterial testRawMaterial = rawMaterialList.get(rawMaterialList.size() - 1);
        assertThat(testRawMaterial.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testRawMaterial.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testRawMaterial.getuOM()).isEqualTo(UPDATED_U_OM);
        assertThat(testRawMaterial.getMaterialType()).isEqualTo(DEFAULT_MATERIAL_TYPE);
        assertThat(testRawMaterial.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testRawMaterial.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRawMaterial.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRawMaterial.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRawMaterial.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRawMaterial.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateRawMaterialWithPatch() throws Exception {
        // Initialize the database
        rawMaterialRepository.saveAndFlush(rawMaterial);

        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();

        // Update the rawMaterial using partial update
        RawMaterial partialUpdatedRawMaterial = new RawMaterial();
        partialUpdatedRawMaterial.setId(rawMaterial.getId());

        partialUpdatedRawMaterial
            .gUID(UPDATED_G_UID)
            .quantity(UPDATED_QUANTITY)
            .uOM(UPDATED_U_OM)
            .materialType(UPDATED_MATERIAL_TYPE)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restRawMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRawMaterial))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
        RawMaterial testRawMaterial = rawMaterialList.get(rawMaterialList.size() - 1);
        assertThat(testRawMaterial.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testRawMaterial.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testRawMaterial.getuOM()).isEqualTo(UPDATED_U_OM);
        assertThat(testRawMaterial.getMaterialType()).isEqualTo(UPDATED_MATERIAL_TYPE);
        assertThat(testRawMaterial.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testRawMaterial.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRawMaterial.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRawMaterial.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRawMaterial.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRawMaterial.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingRawMaterial() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();
        rawMaterial.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rawMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRawMaterial() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();
        rawMaterial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRawMaterial() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();
        rawMaterial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rawMaterial))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRawMaterial() throws Exception {
        // Initialize the database
        rawMaterialRepository.saveAndFlush(rawMaterial);

        int databaseSizeBeforeDelete = rawMaterialRepository.findAll().size();

        // Delete the rawMaterial
        restRawMaterialMockMvc
            .perform(delete(ENTITY_API_URL_ID, rawMaterial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
