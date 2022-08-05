package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.EnnumerationType;
import org.jhipster.blog.repository.EnnumerationTypeRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.EnnumerationType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EnnumerationTypeResource {

    private final Logger log = LoggerFactory.getLogger(EnnumerationTypeResource.class);

    private static final String ENTITY_NAME = "ennumerationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnnumerationTypeRepository ennumerationTypeRepository;

    public EnnumerationTypeResource(EnnumerationTypeRepository ennumerationTypeRepository) {
        this.ennumerationTypeRepository = ennumerationTypeRepository;
    }

    /**
     * {@code POST  /ennumeration-types} : Create a new ennumerationType.
     *
     * @param ennumerationType the ennumerationType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ennumerationType, or with status {@code 400 (Bad Request)} if the ennumerationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ennumeration-types")
    public ResponseEntity<EnnumerationType> createEnnumerationType(@RequestBody EnnumerationType ennumerationType)
        throws URISyntaxException {
        log.debug("REST request to save EnnumerationType : {}", ennumerationType);
        if (ennumerationType.getId() != null) {
            throw new BadRequestAlertException("A new ennumerationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnnumerationType result = ennumerationTypeRepository.save(ennumerationType);
        return ResponseEntity
            .created(new URI("/api/ennumeration-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ennumeration-types/:id} : Updates an existing ennumerationType.
     *
     * @param id the id of the ennumerationType to save.
     * @param ennumerationType the ennumerationType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ennumerationType,
     * or with status {@code 400 (Bad Request)} if the ennumerationType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ennumerationType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ennumeration-types/{id}")
    public ResponseEntity<EnnumerationType> updateEnnumerationType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnnumerationType ennumerationType
    ) throws URISyntaxException {
        log.debug("REST request to update EnnumerationType : {}, {}", id, ennumerationType);
        if (ennumerationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ennumerationType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ennumerationTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EnnumerationType result = ennumerationTypeRepository.save(ennumerationType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ennumerationType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ennumeration-types/:id} : Partial updates given fields of an existing ennumerationType, field will ignore if it is null
     *
     * @param id the id of the ennumerationType to save.
     * @param ennumerationType the ennumerationType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ennumerationType,
     * or with status {@code 400 (Bad Request)} if the ennumerationType is not valid,
     * or with status {@code 404 (Not Found)} if the ennumerationType is not found,
     * or with status {@code 500 (Internal Server Error)} if the ennumerationType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ennumeration-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EnnumerationType> partialUpdateEnnumerationType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnnumerationType ennumerationType
    ) throws URISyntaxException {
        log.debug("REST request to partial update EnnumerationType partially : {}, {}", id, ennumerationType);
        if (ennumerationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ennumerationType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ennumerationTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnnumerationType> result = ennumerationTypeRepository
            .findById(ennumerationType.getId())
            .map(existingEnnumerationType -> {
                if (ennumerationType.getEnnumerationType() != null) {
                    existingEnnumerationType.setEnnumerationType(ennumerationType.getEnnumerationType());
                }
                if (ennumerationType.getHasTable() != null) {
                    existingEnnumerationType.setHasTable(ennumerationType.getHasTable());
                }
                if (ennumerationType.getDescription() != null) {
                    existingEnnumerationType.setDescription(ennumerationType.getDescription());
                }
                if (ennumerationType.getEnnumeration() != null) {
                    existingEnnumerationType.setEnnumeration(ennumerationType.getEnnumeration());
                }

                return existingEnnumerationType;
            })
            .map(ennumerationTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ennumerationType.getId().toString())
        );
    }

    /**
     * {@code GET  /ennumeration-types} : get all the ennumerationTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ennumerationTypes in body.
     */
    @GetMapping("/ennumeration-types")
    public List<EnnumerationType> getAllEnnumerationTypes() {
        log.debug("REST request to get all EnnumerationTypes");
        return ennumerationTypeRepository.findAll();
    }

    /**
     * {@code GET  /ennumeration-types/:id} : get the "id" ennumerationType.
     *
     * @param id the id of the ennumerationType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ennumerationType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ennumeration-types/{id}")
    public ResponseEntity<EnnumerationType> getEnnumerationType(@PathVariable Long id) {
        log.debug("REST request to get EnnumerationType : {}", id);
        Optional<EnnumerationType> ennumerationType = ennumerationTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ennumerationType);
    }

    /**
     * {@code DELETE  /ennumeration-types/:id} : delete the "id" ennumerationType.
     *
     * @param id the id of the ennumerationType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ennumeration-types/{id}")
    public ResponseEntity<Void> deleteEnnumerationType(@PathVariable Long id) {
        log.debug("REST request to delete EnnumerationType : {}", id);
        ennumerationTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
