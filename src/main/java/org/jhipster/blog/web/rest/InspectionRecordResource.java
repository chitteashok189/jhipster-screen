package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.InspectionRecord;
import org.jhipster.blog.repository.InspectionRecordRepository;
import org.jhipster.blog.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.jhipster.blog.domain.InspectionRecord}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InspectionRecordResource {

    private final Logger log = LoggerFactory.getLogger(InspectionRecordResource.class);

    private static final String ENTITY_NAME = "inspectionRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspectionRecordRepository inspectionRecordRepository;

    public InspectionRecordResource(InspectionRecordRepository inspectionRecordRepository) {
        this.inspectionRecordRepository = inspectionRecordRepository;
    }

    /**
     * {@code POST  /inspection-records} : Create a new inspectionRecord.
     *
     * @param inspectionRecord the inspectionRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inspectionRecord, or with status {@code 400 (Bad Request)} if the inspectionRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inspection-records")
    public ResponseEntity<InspectionRecord> createInspectionRecord(@RequestBody InspectionRecord inspectionRecord)
        throws URISyntaxException {
        log.debug("REST request to save InspectionRecord : {}", inspectionRecord);
        if (inspectionRecord.getId() != null) {
            throw new BadRequestAlertException("A new inspectionRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InspectionRecord result = inspectionRecordRepository.save(inspectionRecord);
        return ResponseEntity
            .created(new URI("/api/inspection-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inspection-records/:id} : Updates an existing inspectionRecord.
     *
     * @param id the id of the inspectionRecord to save.
     * @param inspectionRecord the inspectionRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionRecord,
     * or with status {@code 400 (Bad Request)} if the inspectionRecord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inspectionRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inspection-records/{id}")
    public ResponseEntity<InspectionRecord> updateInspectionRecord(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InspectionRecord inspectionRecord
    ) throws URISyntaxException {
        log.debug("REST request to update InspectionRecord : {}, {}", id, inspectionRecord);
        if (inspectionRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRecord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InspectionRecord result = inspectionRecordRepository.save(inspectionRecord);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRecord.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /inspection-records/:id} : Partial updates given fields of an existing inspectionRecord, field will ignore if it is null
     *
     * @param id the id of the inspectionRecord to save.
     * @param inspectionRecord the inspectionRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionRecord,
     * or with status {@code 400 (Bad Request)} if the inspectionRecord is not valid,
     * or with status {@code 404 (Not Found)} if the inspectionRecord is not found,
     * or with status {@code 500 (Internal Server Error)} if the inspectionRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/inspection-records/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InspectionRecord> partialUpdateInspectionRecord(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InspectionRecord inspectionRecord
    ) throws URISyntaxException {
        log.debug("REST request to partial update InspectionRecord partially : {}, {}", id, inspectionRecord);
        if (inspectionRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRecord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InspectionRecord> result = inspectionRecordRepository
            .findById(inspectionRecord.getId())
            .map(existingInspectionRecord -> {
                if (inspectionRecord.getgUID() != null) {
                    existingInspectionRecord.setgUID(inspectionRecord.getgUID());
                }
                if (inspectionRecord.getRawMaterialBatchNo() != null) {
                    existingInspectionRecord.setRawMaterialBatchNo(inspectionRecord.getRawMaterialBatchNo());
                }
                if (inspectionRecord.getProductBatchNo() != null) {
                    existingInspectionRecord.setProductBatchNo(inspectionRecord.getProductBatchNo());
                }
                if (inspectionRecord.getRawMaterialBatchCode() != null) {
                    existingInspectionRecord.setRawMaterialBatchCode(inspectionRecord.getRawMaterialBatchCode());
                }
                if (inspectionRecord.getInputBatchNo() != null) {
                    existingInspectionRecord.setInputBatchNo(inspectionRecord.getInputBatchNo());
                }
                if (inspectionRecord.getInputBatchCode() != null) {
                    existingInspectionRecord.setInputBatchCode(inspectionRecord.getInputBatchCode());
                }
                if (inspectionRecord.getAttachments() != null) {
                    existingInspectionRecord.setAttachments(inspectionRecord.getAttachments());
                }
                if (inspectionRecord.getAttachmentsContentType() != null) {
                    existingInspectionRecord.setAttachmentsContentType(inspectionRecord.getAttachmentsContentType());
                }
                if (inspectionRecord.getCreatedBy() != null) {
                    existingInspectionRecord.setCreatedBy(inspectionRecord.getCreatedBy());
                }
                if (inspectionRecord.getCreatedOn() != null) {
                    existingInspectionRecord.setCreatedOn(inspectionRecord.getCreatedOn());
                }
                if (inspectionRecord.getUpdatedBy() != null) {
                    existingInspectionRecord.setUpdatedBy(inspectionRecord.getUpdatedBy());
                }
                if (inspectionRecord.getUpdatedOn() != null) {
                    existingInspectionRecord.setUpdatedOn(inspectionRecord.getUpdatedOn());
                }

                return existingInspectionRecord;
            })
            .map(inspectionRecordRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRecord.getId().toString())
        );
    }

    /**
     * {@code GET  /inspection-records} : get all the inspectionRecords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inspectionRecords in body.
     */
    @GetMapping("/inspection-records")
    public List<InspectionRecord> getAllInspectionRecords() {
        log.debug("REST request to get all InspectionRecords");
        return inspectionRecordRepository.findAll();
    }

    /**
     * {@code GET  /inspection-records/:id} : get the "id" inspectionRecord.
     *
     * @param id the id of the inspectionRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inspectionRecord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inspection-records/{id}")
    public ResponseEntity<InspectionRecord> getInspectionRecord(@PathVariable Long id) {
        log.debug("REST request to get InspectionRecord : {}", id);
        Optional<InspectionRecord> inspectionRecord = inspectionRecordRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inspectionRecord);
    }

    /**
     * {@code DELETE  /inspection-records/:id} : delete the "id" inspectionRecord.
     *
     * @param id the id of the inspectionRecord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inspection-records/{id}")
    public ResponseEntity<Void> deleteInspectionRecord(@PathVariable Long id) {
        log.debug("REST request to delete InspectionRecord : {}", id);
        inspectionRecordRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
