package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.DiseaseControl;
import org.jhipster.blog.repository.DiseaseControlRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.DiseaseControl}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DiseaseControlResource {

    private final Logger log = LoggerFactory.getLogger(DiseaseControlResource.class);

    private static final String ENTITY_NAME = "diseaseControl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiseaseControlRepository diseaseControlRepository;

    public DiseaseControlResource(DiseaseControlRepository diseaseControlRepository) {
        this.diseaseControlRepository = diseaseControlRepository;
    }

    /**
     * {@code POST  /disease-controls} : Create a new diseaseControl.
     *
     * @param diseaseControl the diseaseControl to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diseaseControl, or with status {@code 400 (Bad Request)} if the diseaseControl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/disease-controls")
    public ResponseEntity<DiseaseControl> createDiseaseControl(@RequestBody DiseaseControl diseaseControl) throws URISyntaxException {
        log.debug("REST request to save DiseaseControl : {}", diseaseControl);
        if (diseaseControl.getId() != null) {
            throw new BadRequestAlertException("A new diseaseControl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiseaseControl result = diseaseControlRepository.save(diseaseControl);
        return ResponseEntity
            .created(new URI("/api/disease-controls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /disease-controls/:id} : Updates an existing diseaseControl.
     *
     * @param id the id of the diseaseControl to save.
     * @param diseaseControl the diseaseControl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diseaseControl,
     * or with status {@code 400 (Bad Request)} if the diseaseControl is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diseaseControl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/disease-controls/{id}")
    public ResponseEntity<DiseaseControl> updateDiseaseControl(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiseaseControl diseaseControl
    ) throws URISyntaxException {
        log.debug("REST request to update DiseaseControl : {}, {}", id, diseaseControl);
        if (diseaseControl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diseaseControl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diseaseControlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DiseaseControl result = diseaseControlRepository.save(diseaseControl);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diseaseControl.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /disease-controls/:id} : Partial updates given fields of an existing diseaseControl, field will ignore if it is null
     *
     * @param id the id of the diseaseControl to save.
     * @param diseaseControl the diseaseControl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diseaseControl,
     * or with status {@code 400 (Bad Request)} if the diseaseControl is not valid,
     * or with status {@code 404 (Not Found)} if the diseaseControl is not found,
     * or with status {@code 500 (Internal Server Error)} if the diseaseControl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/disease-controls/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DiseaseControl> partialUpdateDiseaseControl(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiseaseControl diseaseControl
    ) throws URISyntaxException {
        log.debug("REST request to partial update DiseaseControl partially : {}, {}", id, diseaseControl);
        if (diseaseControl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diseaseControl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diseaseControlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DiseaseControl> result = diseaseControlRepository
            .findById(diseaseControl.getId())
            .map(existingDiseaseControl -> {
                if (diseaseControl.getgUID() != null) {
                    existingDiseaseControl.setgUID(diseaseControl.getgUID());
                }
                if (diseaseControl.getControlType() != null) {
                    existingDiseaseControl.setControlType(diseaseControl.getControlType());
                }
                if (diseaseControl.getTreatment() != null) {
                    existingDiseaseControl.setTreatment(diseaseControl.getTreatment());
                }
                if (diseaseControl.getCreatedBy() != null) {
                    existingDiseaseControl.setCreatedBy(diseaseControl.getCreatedBy());
                }
                if (diseaseControl.getCreatedOn() != null) {
                    existingDiseaseControl.setCreatedOn(diseaseControl.getCreatedOn());
                }
                if (diseaseControl.getUpdatedBy() != null) {
                    existingDiseaseControl.setUpdatedBy(diseaseControl.getUpdatedBy());
                }
                if (diseaseControl.getUpdatedOn() != null) {
                    existingDiseaseControl.setUpdatedOn(diseaseControl.getUpdatedOn());
                }

                return existingDiseaseControl;
            })
            .map(diseaseControlRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diseaseControl.getId().toString())
        );
    }

    /**
     * {@code GET  /disease-controls} : get all the diseaseControls.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diseaseControls in body.
     */
    @GetMapping("/disease-controls")
    public List<DiseaseControl> getAllDiseaseControls() {
        log.debug("REST request to get all DiseaseControls");
        return diseaseControlRepository.findAll();
    }

    /**
     * {@code GET  /disease-controls/:id} : get the "id" diseaseControl.
     *
     * @param id the id of the diseaseControl to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diseaseControl, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/disease-controls/{id}")
    public ResponseEntity<DiseaseControl> getDiseaseControl(@PathVariable Long id) {
        log.debug("REST request to get DiseaseControl : {}", id);
        Optional<DiseaseControl> diseaseControl = diseaseControlRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(diseaseControl);
    }

    /**
     * {@code DELETE  /disease-controls/:id} : delete the "id" diseaseControl.
     *
     * @param id the id of the diseaseControl to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/disease-controls/{id}")
    public ResponseEntity<Void> deleteDiseaseControl(@PathVariable Long id) {
        log.debug("REST request to delete DiseaseControl : {}", id);
        diseaseControlRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
