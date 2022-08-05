package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PestControl;
import org.jhipster.blog.repository.PestControlRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PestControl}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PestControlResource {

    private final Logger log = LoggerFactory.getLogger(PestControlResource.class);

    private static final String ENTITY_NAME = "pestControl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PestControlRepository pestControlRepository;

    public PestControlResource(PestControlRepository pestControlRepository) {
        this.pestControlRepository = pestControlRepository;
    }

    /**
     * {@code POST  /pest-controls} : Create a new pestControl.
     *
     * @param pestControl the pestControl to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pestControl, or with status {@code 400 (Bad Request)} if the pestControl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pest-controls")
    public ResponseEntity<PestControl> createPestControl(@RequestBody PestControl pestControl) throws URISyntaxException {
        log.debug("REST request to save PestControl : {}", pestControl);
        if (pestControl.getId() != null) {
            throw new BadRequestAlertException("A new pestControl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PestControl result = pestControlRepository.save(pestControl);
        return ResponseEntity
            .created(new URI("/api/pest-controls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pest-controls/:id} : Updates an existing pestControl.
     *
     * @param id the id of the pestControl to save.
     * @param pestControl the pestControl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pestControl,
     * or with status {@code 400 (Bad Request)} if the pestControl is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pestControl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pest-controls/{id}")
    public ResponseEntity<PestControl> updatePestControl(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PestControl pestControl
    ) throws URISyntaxException {
        log.debug("REST request to update PestControl : {}, {}", id, pestControl);
        if (pestControl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pestControl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pestControlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PestControl result = pestControlRepository.save(pestControl);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pestControl.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pest-controls/:id} : Partial updates given fields of an existing pestControl, field will ignore if it is null
     *
     * @param id the id of the pestControl to save.
     * @param pestControl the pestControl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pestControl,
     * or with status {@code 400 (Bad Request)} if the pestControl is not valid,
     * or with status {@code 404 (Not Found)} if the pestControl is not found,
     * or with status {@code 500 (Internal Server Error)} if the pestControl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pest-controls/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PestControl> partialUpdatePestControl(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PestControl pestControl
    ) throws URISyntaxException {
        log.debug("REST request to partial update PestControl partially : {}, {}", id, pestControl);
        if (pestControl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pestControl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pestControlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PestControl> result = pestControlRepository
            .findById(pestControl.getId())
            .map(existingPestControl -> {
                if (pestControl.getgUID() != null) {
                    existingPestControl.setgUID(pestControl.getgUID());
                }
                if (pestControl.getNatureOfDamage() != null) {
                    existingPestControl.setNatureOfDamage(pestControl.getNatureOfDamage());
                }
                if (pestControl.getControlType() != null) {
                    existingPestControl.setControlType(pestControl.getControlType());
                }
                if (pestControl.getControlMeasures() != null) {
                    existingPestControl.setControlMeasures(pestControl.getControlMeasures());
                }
                if (pestControl.getCreatedBy() != null) {
                    existingPestControl.setCreatedBy(pestControl.getCreatedBy());
                }
                if (pestControl.getCreatedOn() != null) {
                    existingPestControl.setCreatedOn(pestControl.getCreatedOn());
                }
                if (pestControl.getUpdatedBy() != null) {
                    existingPestControl.setUpdatedBy(pestControl.getUpdatedBy());
                }
                if (pestControl.getUpdatedOn() != null) {
                    existingPestControl.setUpdatedOn(pestControl.getUpdatedOn());
                }

                return existingPestControl;
            })
            .map(pestControlRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pestControl.getId().toString())
        );
    }

    /**
     * {@code GET  /pest-controls} : get all the pestControls.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pestControls in body.
     */
    @GetMapping("/pest-controls")
    public List<PestControl> getAllPestControls() {
        log.debug("REST request to get all PestControls");
        return pestControlRepository.findAll();
    }

    /**
     * {@code GET  /pest-controls/:id} : get the "id" pestControl.
     *
     * @param id the id of the pestControl to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pestControl, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pest-controls/{id}")
    public ResponseEntity<PestControl> getPestControl(@PathVariable Long id) {
        log.debug("REST request to get PestControl : {}", id);
        Optional<PestControl> pestControl = pestControlRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pestControl);
    }

    /**
     * {@code DELETE  /pest-controls/:id} : delete the "id" pestControl.
     *
     * @param id the id of the pestControl to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pest-controls/{id}")
    public ResponseEntity<Void> deletePestControl(@PathVariable Long id) {
        log.debug("REST request to delete PestControl : {}", id);
        pestControlRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
