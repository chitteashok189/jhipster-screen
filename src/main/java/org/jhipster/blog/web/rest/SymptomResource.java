package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Symptom;
import org.jhipster.blog.repository.SymptomRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Symptom}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SymptomResource {

    private final Logger log = LoggerFactory.getLogger(SymptomResource.class);

    private static final String ENTITY_NAME = "symptom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SymptomRepository symptomRepository;

    public SymptomResource(SymptomRepository symptomRepository) {
        this.symptomRepository = symptomRepository;
    }

    /**
     * {@code POST  /symptoms} : Create a new symptom.
     *
     * @param symptom the symptom to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new symptom, or with status {@code 400 (Bad Request)} if the symptom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/symptoms")
    public ResponseEntity<Symptom> createSymptom(@RequestBody Symptom symptom) throws URISyntaxException {
        log.debug("REST request to save Symptom : {}", symptom);
        if (symptom.getId() != null) {
            throw new BadRequestAlertException("A new symptom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Symptom result = symptomRepository.save(symptom);
        return ResponseEntity
            .created(new URI("/api/symptoms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /symptoms/:id} : Updates an existing symptom.
     *
     * @param id the id of the symptom to save.
     * @param symptom the symptom to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated symptom,
     * or with status {@code 400 (Bad Request)} if the symptom is not valid,
     * or with status {@code 500 (Internal Server Error)} if the symptom couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/symptoms/{id}")
    public ResponseEntity<Symptom> updateSymptom(@PathVariable(value = "id", required = false) final Long id, @RequestBody Symptom symptom)
        throws URISyntaxException {
        log.debug("REST request to update Symptom : {}, {}", id, symptom);
        if (symptom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, symptom.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!symptomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Symptom result = symptomRepository.save(symptom);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, symptom.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /symptoms/:id} : Partial updates given fields of an existing symptom, field will ignore if it is null
     *
     * @param id the id of the symptom to save.
     * @param symptom the symptom to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated symptom,
     * or with status {@code 400 (Bad Request)} if the symptom is not valid,
     * or with status {@code 404 (Not Found)} if the symptom is not found,
     * or with status {@code 500 (Internal Server Error)} if the symptom couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/symptoms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Symptom> partialUpdateSymptom(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Symptom symptom
    ) throws URISyntaxException {
        log.debug("REST request to partial update Symptom partially : {}, {}", id, symptom);
        if (symptom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, symptom.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!symptomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Symptom> result = symptomRepository
            .findById(symptom.getId())
            .map(existingSymptom -> {
                if (symptom.getgUID() != null) {
                    existingSymptom.setgUID(symptom.getgUID());
                }
                if (symptom.getObservation() != null) {
                    existingSymptom.setObservation(symptom.getObservation());
                }
                if (symptom.getSymptomImage() != null) {
                    existingSymptom.setSymptomImage(symptom.getSymptomImage());
                }
                if (symptom.getSymptomImageContentType() != null) {
                    existingSymptom.setSymptomImageContentType(symptom.getSymptomImageContentType());
                }
                if (symptom.getCreatedBy() != null) {
                    existingSymptom.setCreatedBy(symptom.getCreatedBy());
                }
                if (symptom.getCreatedOn() != null) {
                    existingSymptom.setCreatedOn(symptom.getCreatedOn());
                }
                if (symptom.getUpdatedBy() != null) {
                    existingSymptom.setUpdatedBy(symptom.getUpdatedBy());
                }
                if (symptom.getUpdatedOn() != null) {
                    existingSymptom.setUpdatedOn(symptom.getUpdatedOn());
                }

                return existingSymptom;
            })
            .map(symptomRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, symptom.getId().toString())
        );
    }

    /**
     * {@code GET  /symptoms} : get all the symptoms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of symptoms in body.
     */
    @GetMapping("/symptoms")
    public List<Symptom> getAllSymptoms() {
        log.debug("REST request to get all Symptoms");
        return symptomRepository.findAll();
    }

    /**
     * {@code GET  /symptoms/:id} : get the "id" symptom.
     *
     * @param id the id of the symptom to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the symptom, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/symptoms/{id}")
    public ResponseEntity<Symptom> getSymptom(@PathVariable Long id) {
        log.debug("REST request to get Symptom : {}", id);
        Optional<Symptom> symptom = symptomRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(symptom);
    }

    /**
     * {@code DELETE  /symptoms/:id} : delete the "id" symptom.
     *
     * @param id the id of the symptom to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/symptoms/{id}")
    public ResponseEntity<Void> deleteSymptom(@PathVariable Long id) {
        log.debug("REST request to delete Symptom : {}", id);
        symptomRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
