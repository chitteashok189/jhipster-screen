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
import org.jhipster.blog.domain.InspectionRecord;
import org.jhipster.blog.repository.InspectionRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link InspectionRecordResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InspectionRecordResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Integer DEFAULT_RAW_MATERIAL_BATCH_NO = 1;
    private static final Integer UPDATED_RAW_MATERIAL_BATCH_NO = 2;

    private static final Integer DEFAULT_PRODUCT_BATCH_NO = 1;
    private static final Integer UPDATED_PRODUCT_BATCH_NO = 2;

    private static final Integer DEFAULT_RAW_MATERIAL_BATCH_CODE = 1;
    private static final Integer UPDATED_RAW_MATERIAL_BATCH_CODE = 2;

    private static final Integer DEFAULT_INPUT_BATCH_NO = 1;
    private static final Integer UPDATED_INPUT_BATCH_NO = 2;

    private static final Integer DEFAULT_INPUT_BATCH_CODE = 1;
    private static final Integer UPDATED_INPUT_BATCH_CODE = 2;

    private static final byte[] DEFAULT_ATTACHMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENTS_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/inspection-records";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InspectionRecordRepository inspectionRecordRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInspectionRecordMockMvc;

    private InspectionRecord inspectionRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRecord createEntity(EntityManager em) {
        InspectionRecord inspectionRecord = new InspectionRecord()
            .gUID(DEFAULT_G_UID)
            .rawMaterialBatchNo(DEFAULT_RAW_MATERIAL_BATCH_NO)
            .productBatchNo(DEFAULT_PRODUCT_BATCH_NO)
            .rawMaterialBatchCode(DEFAULT_RAW_MATERIAL_BATCH_CODE)
            .inputBatchNo(DEFAULT_INPUT_BATCH_NO)
            .inputBatchCode(DEFAULT_INPUT_BATCH_CODE)
            .attachments(DEFAULT_ATTACHMENTS)
            .attachmentsContentType(DEFAULT_ATTACHMENTS_CONTENT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return inspectionRecord;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRecord createUpdatedEntity(EntityManager em) {
        InspectionRecord inspectionRecord = new InspectionRecord()
            .gUID(UPDATED_G_UID)
            .rawMaterialBatchNo(UPDATED_RAW_MATERIAL_BATCH_NO)
            .productBatchNo(UPDATED_PRODUCT_BATCH_NO)
            .rawMaterialBatchCode(UPDATED_RAW_MATERIAL_BATCH_CODE)
            .inputBatchNo(UPDATED_INPUT_BATCH_NO)
            .inputBatchCode(UPDATED_INPUT_BATCH_CODE)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return inspectionRecord;
    }

    @BeforeEach
    public void initTest() {
        inspectionRecord = createEntity(em);
    }

    @Test
    @Transactional
    void createInspectionRecord() throws Exception {
        int databaseSizeBeforeCreate = inspectionRecordRepository.findAll().size();
        // Create the InspectionRecord
        restInspectionRecordMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inspectionRecord))
            )
            .andExpect(status().isCreated());

        // Validate the InspectionRecord in the database
        List<InspectionRecord> inspectionRecordList = inspectionRecordRepository.findAll();
        assertThat(inspectionRecordList).hasSize(databaseSizeBeforeCreate + 1);
        InspectionRecord testInspectionRecord = inspectionRecordList.get(inspectionRecordList.size() - 1);
        assertThat(testInspectionRecord.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testInspectionRecord.getRawMaterialBatchNo()).isEqualTo(DEFAULT_RAW_MATERIAL_BATCH_NO);
        assertThat(testInspectionRecord.getProductBatchNo()).isEqualTo(DEFAULT_PRODUCT_BATCH_NO);
        assertThat(testInspectionRecord.getRawMaterialBatchCode()).isEqualTo(DEFAULT_RAW_MATERIAL_BATCH_CODE);
        assertThat(testInspectionRecord.getInputBatchNo()).isEqualTo(DEFAULT_INPUT_BATCH_NO);
        assertThat(testInspectionRecord.getInputBatchCode()).isEqualTo(DEFAULT_INPUT_BATCH_CODE);
        assertThat(testInspectionRecord.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testInspectionRecord.getAttachmentsContentType()).isEqualTo(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testInspectionRecord.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInspectionRecord.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testInspectionRecord.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testInspectionRecord.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createInspectionRecordWithExistingId() throws Exception {
        // Create the InspectionRecord with an existing ID
        inspectionRecord.setId(1L);

        int databaseSizeBeforeCreate = inspectionRecordRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionRecordMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecord in the database
        List<InspectionRecord> inspectionRecordList = inspectionRecordRepository.findAll();
        assertThat(inspectionRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInspectionRecords() throws Exception {
        // Initialize the database
        inspectionRecordRepository.saveAndFlush(inspectionRecord);

        // Get all the inspectionRecordList
        restInspectionRecordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspectionRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].rawMaterialBatchNo").value(hasItem(DEFAULT_RAW_MATERIAL_BATCH_NO)))
            .andExpect(jsonPath("$.[*].productBatchNo").value(hasItem(DEFAULT_PRODUCT_BATCH_NO)))
            .andExpect(jsonPath("$.[*].rawMaterialBatchCode").value(hasItem(DEFAULT_RAW_MATERIAL_BATCH_CODE)))
            .andExpect(jsonPath("$.[*].inputBatchNo").value(hasItem(DEFAULT_INPUT_BATCH_NO)))
            .andExpect(jsonPath("$.[*].inputBatchCode").value(hasItem(DEFAULT_INPUT_BATCH_CODE)))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getInspectionRecord() throws Exception {
        // Initialize the database
        inspectionRecordRepository.saveAndFlush(inspectionRecord);

        // Get the inspectionRecord
        restInspectionRecordMockMvc
            .perform(get(ENTITY_API_URL_ID, inspectionRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inspectionRecord.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.rawMaterialBatchNo").value(DEFAULT_RAW_MATERIAL_BATCH_NO))
            .andExpect(jsonPath("$.productBatchNo").value(DEFAULT_PRODUCT_BATCH_NO))
            .andExpect(jsonPath("$.rawMaterialBatchCode").value(DEFAULT_RAW_MATERIAL_BATCH_CODE))
            .andExpect(jsonPath("$.inputBatchNo").value(DEFAULT_INPUT_BATCH_NO))
            .andExpect(jsonPath("$.inputBatchCode").value(DEFAULT_INPUT_BATCH_CODE))
            .andExpect(jsonPath("$.attachmentsContentType").value(DEFAULT_ATTACHMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachments").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingInspectionRecord() throws Exception {
        // Get the inspectionRecord
        restInspectionRecordMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInspectionRecord() throws Exception {
        // Initialize the database
        inspectionRecordRepository.saveAndFlush(inspectionRecord);

        int databaseSizeBeforeUpdate = inspectionRecordRepository.findAll().size();

        // Update the inspectionRecord
        InspectionRecord updatedInspectionRecord = inspectionRecordRepository.findById(inspectionRecord.getId()).get();
        // Disconnect from session so that the updates on updatedInspectionRecord are not directly saved in db
        em.detach(updatedInspectionRecord);
        updatedInspectionRecord
            .gUID(UPDATED_G_UID)
            .rawMaterialBatchNo(UPDATED_RAW_MATERIAL_BATCH_NO)
            .productBatchNo(UPDATED_PRODUCT_BATCH_NO)
            .rawMaterialBatchCode(UPDATED_RAW_MATERIAL_BATCH_CODE)
            .inputBatchNo(UPDATED_INPUT_BATCH_NO)
            .inputBatchCode(UPDATED_INPUT_BATCH_CODE)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restInspectionRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInspectionRecord.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInspectionRecord))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRecord in the database
        List<InspectionRecord> inspectionRecordList = inspectionRecordRepository.findAll();
        assertThat(inspectionRecordList).hasSize(databaseSizeBeforeUpdate);
        InspectionRecord testInspectionRecord = inspectionRecordList.get(inspectionRecordList.size() - 1);
        assertThat(testInspectionRecord.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testInspectionRecord.getRawMaterialBatchNo()).isEqualTo(UPDATED_RAW_MATERIAL_BATCH_NO);
        assertThat(testInspectionRecord.getProductBatchNo()).isEqualTo(UPDATED_PRODUCT_BATCH_NO);
        assertThat(testInspectionRecord.getRawMaterialBatchCode()).isEqualTo(UPDATED_RAW_MATERIAL_BATCH_CODE);
        assertThat(testInspectionRecord.getInputBatchNo()).isEqualTo(UPDATED_INPUT_BATCH_NO);
        assertThat(testInspectionRecord.getInputBatchCode()).isEqualTo(UPDATED_INPUT_BATCH_CODE);
        assertThat(testInspectionRecord.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testInspectionRecord.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testInspectionRecord.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInspectionRecord.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testInspectionRecord.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testInspectionRecord.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingInspectionRecord() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRecordRepository.findAll().size();
        inspectionRecord.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inspectionRecord.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecord in the database
        List<InspectionRecord> inspectionRecordList = inspectionRecordRepository.findAll();
        assertThat(inspectionRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInspectionRecord() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRecordRepository.findAll().size();
        inspectionRecord.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecord in the database
        List<InspectionRecord> inspectionRecordList = inspectionRecordRepository.findAll();
        assertThat(inspectionRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInspectionRecord() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRecordRepository.findAll().size();
        inspectionRecord.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inspectionRecord))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRecord in the database
        List<InspectionRecord> inspectionRecordList = inspectionRecordRepository.findAll();
        assertThat(inspectionRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInspectionRecordWithPatch() throws Exception {
        // Initialize the database
        inspectionRecordRepository.saveAndFlush(inspectionRecord);

        int databaseSizeBeforeUpdate = inspectionRecordRepository.findAll().size();

        // Update the inspectionRecord using partial update
        InspectionRecord partialUpdatedInspectionRecord = new InspectionRecord();
        partialUpdatedInspectionRecord.setId(inspectionRecord.getId());

        partialUpdatedInspectionRecord
            .gUID(UPDATED_G_UID)
            .rawMaterialBatchNo(UPDATED_RAW_MATERIAL_BATCH_NO)
            .rawMaterialBatchCode(UPDATED_RAW_MATERIAL_BATCH_CODE)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInspectionRecord))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRecord in the database
        List<InspectionRecord> inspectionRecordList = inspectionRecordRepository.findAll();
        assertThat(inspectionRecordList).hasSize(databaseSizeBeforeUpdate);
        InspectionRecord testInspectionRecord = inspectionRecordList.get(inspectionRecordList.size() - 1);
        assertThat(testInspectionRecord.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testInspectionRecord.getRawMaterialBatchNo()).isEqualTo(UPDATED_RAW_MATERIAL_BATCH_NO);
        assertThat(testInspectionRecord.getProductBatchNo()).isEqualTo(DEFAULT_PRODUCT_BATCH_NO);
        assertThat(testInspectionRecord.getRawMaterialBatchCode()).isEqualTo(UPDATED_RAW_MATERIAL_BATCH_CODE);
        assertThat(testInspectionRecord.getInputBatchNo()).isEqualTo(DEFAULT_INPUT_BATCH_NO);
        assertThat(testInspectionRecord.getInputBatchCode()).isEqualTo(DEFAULT_INPUT_BATCH_CODE);
        assertThat(testInspectionRecord.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testInspectionRecord.getAttachmentsContentType()).isEqualTo(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testInspectionRecord.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInspectionRecord.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testInspectionRecord.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testInspectionRecord.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateInspectionRecordWithPatch() throws Exception {
        // Initialize the database
        inspectionRecordRepository.saveAndFlush(inspectionRecord);

        int databaseSizeBeforeUpdate = inspectionRecordRepository.findAll().size();

        // Update the inspectionRecord using partial update
        InspectionRecord partialUpdatedInspectionRecord = new InspectionRecord();
        partialUpdatedInspectionRecord.setId(inspectionRecord.getId());

        partialUpdatedInspectionRecord
            .gUID(UPDATED_G_UID)
            .rawMaterialBatchNo(UPDATED_RAW_MATERIAL_BATCH_NO)
            .productBatchNo(UPDATED_PRODUCT_BATCH_NO)
            .rawMaterialBatchCode(UPDATED_RAW_MATERIAL_BATCH_CODE)
            .inputBatchNo(UPDATED_INPUT_BATCH_NO)
            .inputBatchCode(UPDATED_INPUT_BATCH_CODE)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInspectionRecord))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRecord in the database
        List<InspectionRecord> inspectionRecordList = inspectionRecordRepository.findAll();
        assertThat(inspectionRecordList).hasSize(databaseSizeBeforeUpdate);
        InspectionRecord testInspectionRecord = inspectionRecordList.get(inspectionRecordList.size() - 1);
        assertThat(testInspectionRecord.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testInspectionRecord.getRawMaterialBatchNo()).isEqualTo(UPDATED_RAW_MATERIAL_BATCH_NO);
        assertThat(testInspectionRecord.getProductBatchNo()).isEqualTo(UPDATED_PRODUCT_BATCH_NO);
        assertThat(testInspectionRecord.getRawMaterialBatchCode()).isEqualTo(UPDATED_RAW_MATERIAL_BATCH_CODE);
        assertThat(testInspectionRecord.getInputBatchNo()).isEqualTo(UPDATED_INPUT_BATCH_NO);
        assertThat(testInspectionRecord.getInputBatchCode()).isEqualTo(UPDATED_INPUT_BATCH_CODE);
        assertThat(testInspectionRecord.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testInspectionRecord.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testInspectionRecord.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInspectionRecord.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testInspectionRecord.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testInspectionRecord.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingInspectionRecord() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRecordRepository.findAll().size();
        inspectionRecord.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inspectionRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecord in the database
        List<InspectionRecord> inspectionRecordList = inspectionRecordRepository.findAll();
        assertThat(inspectionRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInspectionRecord() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRecordRepository.findAll().size();
        inspectionRecord.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecord in the database
        List<InspectionRecord> inspectionRecordList = inspectionRecordRepository.findAll();
        assertThat(inspectionRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInspectionRecord() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRecordRepository.findAll().size();
        inspectionRecord.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inspectionRecord))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRecord in the database
        List<InspectionRecord> inspectionRecordList = inspectionRecordRepository.findAll();
        assertThat(inspectionRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInspectionRecord() throws Exception {
        // Initialize the database
        inspectionRecordRepository.saveAndFlush(inspectionRecord);

        int databaseSizeBeforeDelete = inspectionRecordRepository.findAll().size();

        // Delete the inspectionRecord
        restInspectionRecordMockMvc
            .perform(delete(ENTITY_API_URL_ID, inspectionRecord.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InspectionRecord> inspectionRecordList = inspectionRecordRepository.findAll();
        assertThat(inspectionRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
