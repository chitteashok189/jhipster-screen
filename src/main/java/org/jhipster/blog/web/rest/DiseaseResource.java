package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Disease;
import org.jhipster.blog.repository.DiseaseRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Disease}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DiseaseResource {

    private final Logger log = LoggerFactory.getLogger(DiseaseResource.class);

    private static final String ENTITY_NAME = "disease";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiseaseRepository diseaseRepository;

    public DiseaseResource(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    /**
     * {@code POST  /diseases} : Create a new disease.
     *
     * @param disease the disease to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disease, or with status {@code 400 (Bad Request)} if the disease has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/diseases")
    public ResponseEntity<Disease> createDisease(@RequestBody Disease disease) throws URISyntaxException {
        log.debug("REST request to save Disease : {}", disease);
        if (disease.getId() != null) {
            throw new BadRequestAlertException("A new disease cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Disease result = diseaseRepository.save(disease);
        return ResponseEntity
            .created(new URI("/api/diseases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /diseases/:id} : Updates an existing disease.
     *
     * @param id the id of the disease to save.
     * @param disease the disease to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disease,
     * or with status {@code 400 (Bad Request)} if the disease is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disease couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/diseases/{id}")
    public ResponseEntity<Disease> updateDisease(@PathVariable(value = "id", required = false) final Long id, @RequestBody Disease disease)
        throws URISyntaxException {
        log.debug("REST request to update Disease : {}, {}", id, disease);
        if (disease.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disease.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diseaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Disease result = diseaseRepository.save(disease);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disease.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /diseases/:id} : Partial updates given fields of an existing disease, field will ignore if it is null
     *
     * @param id the id of the disease to save.
     * @param disease the disease to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disease,
     * or with status {@code 400 (Bad Request)} if the disease is not valid,
     * or with status {@code 404 (Not Found)} if the disease is not found,
     * or with status {@code 500 (Internal Server Error)} if the disease couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/diseases/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Disease> partialUpdateDisease(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Disease disease
    ) throws URISyntaxException {
        log.debug("REST request to partial update Disease partially : {}, {}", id, disease);
        if (disease.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disease.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diseaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Disease> result = diseaseRepository
            .findById(disease.getId())
            .map(existingDisease -> {
                if (disease.getgUID() != null) {
                    existingDisease.setgUID(disease.getgUID());
                }
                if (disease.getThreatLevel() != null) {
                    existingDisease.setThreatLevel(disease.getThreatLevel());
                }
                if (disease.getName() != null) {
                    existingDisease.setName(disease.getName());
                }
                if (disease.getCausalOrganism() != null) {
                    existingDisease.setCausalOrganism(disease.getCausalOrganism());
                }
                if (disease.getAttachments() != null) {
                    existingDisease.setAttachments(disease.getAttachments());
                }
                if (disease.getCreatedBy() != null) {
                    existingDisease.setCreatedBy(disease.getCreatedBy());
                }
                if (disease.getCreatedOn() != null) {
                    existingDisease.setCreatedOn(disease.getCreatedOn());
                }
                if (disease.getUpdatedBy() != null) {
                    existingDisease.setUpdatedBy(disease.getUpdatedBy());
                }
                if (disease.getUpdatedOn() != null) {
                    existingDisease.setUpdatedOn(disease.getUpdatedOn());
                }

                return existingDisease;
            })
            .map(diseaseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disease.getId().toString())
        );
    }

    /**
     * {@code GET  /diseases} : get all the diseases.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diseases in body.
     */
    @GetMapping("/diseases")
    public List<Disease> getAllDiseases() {
        log.debug("REST request to get all Diseases");
        return diseaseRepository.findAll();
    }

    /**
     * {@code GET  /diseases/:id} : get the "id" disease.
     *
     * @param id the id of the disease to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disease, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/diseases/{id}")
    public ResponseEntity<Disease> getDisease(@PathVariable Long id) {
        log.debug("REST request to get Disease : {}", id);
        Optional<Disease> disease = diseaseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(disease);
    }

    /**
     * {@code DELETE  /diseases/:id} : delete the "id" disease.
     *
     * @param id the id of the disease to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/diseases/{id}")
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        log.debug("REST request to delete Disease : {}", id);
        diseaseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
