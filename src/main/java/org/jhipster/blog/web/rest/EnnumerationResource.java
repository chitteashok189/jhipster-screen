package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Ennumeration;
import org.jhipster.blog.repository.EnnumerationRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Ennumeration}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EnnumerationResource {

    private final Logger log = LoggerFactory.getLogger(EnnumerationResource.class);

    private static final String ENTITY_NAME = "ennumeration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnnumerationRepository ennumerationRepository;

    public EnnumerationResource(EnnumerationRepository ennumerationRepository) {
        this.ennumerationRepository = ennumerationRepository;
    }

    /**
     * {@code POST  /ennumerations} : Create a new ennumeration.
     *
     * @param ennumeration the ennumeration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ennumeration, or with status {@code 400 (Bad Request)} if the ennumeration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ennumerations")
    public ResponseEntity<Ennumeration> createEnnumeration(@RequestBody Ennumeration ennumeration) throws URISyntaxException {
        log.debug("REST request to save Ennumeration : {}", ennumeration);
        if (ennumeration.getId() != null) {
            throw new BadRequestAlertException("A new ennumeration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ennumeration result = ennumerationRepository.save(ennumeration);
        return ResponseEntity
            .created(new URI("/api/ennumerations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ennumerations/:id} : Updates an existing ennumeration.
     *
     * @param id the id of the ennumeration to save.
     * @param ennumeration the ennumeration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ennumeration,
     * or with status {@code 400 (Bad Request)} if the ennumeration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ennumeration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ennumerations/{id}")
    public ResponseEntity<Ennumeration> updateEnnumeration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ennumeration ennumeration
    ) throws URISyntaxException {
        log.debug("REST request to update Ennumeration : {}, {}", id, ennumeration);
        if (ennumeration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ennumeration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ennumerationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ennumeration result = ennumerationRepository.save(ennumeration);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ennumeration.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ennumerations/:id} : Partial updates given fields of an existing ennumeration, field will ignore if it is null
     *
     * @param id the id of the ennumeration to save.
     * @param ennumeration the ennumeration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ennumeration,
     * or with status {@code 400 (Bad Request)} if the ennumeration is not valid,
     * or with status {@code 404 (Not Found)} if the ennumeration is not found,
     * or with status {@code 500 (Internal Server Error)} if the ennumeration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ennumerations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ennumeration> partialUpdateEnnumeration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ennumeration ennumeration
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ennumeration partially : {}, {}", id, ennumeration);
        if (ennumeration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ennumeration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ennumerationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ennumeration> result = ennumerationRepository
            .findById(ennumeration.getId())
            .map(existingEnnumeration -> {
                if (ennumeration.getEnumCode() != null) {
                    existingEnnumeration.setEnumCode(ennumeration.getEnumCode());
                }
                if (ennumeration.getDescription() != null) {
                    existingEnnumeration.setDescription(ennumeration.getDescription());
                }

                return existingEnnumeration;
            })
            .map(ennumerationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ennumeration.getId().toString())
        );
    }

    /**
     * {@code GET  /ennumerations} : get all the ennumerations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ennumerations in body.
     */
    @GetMapping("/ennumerations")
    public List<Ennumeration> getAllEnnumerations() {
        log.debug("REST request to get all Ennumerations");
        return ennumerationRepository.findAll();
    }

    /**
     * {@code GET  /ennumerations/:id} : get the "id" ennumeration.
     *
     * @param id the id of the ennumeration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ennumeration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ennumerations/{id}")
    public ResponseEntity<Ennumeration> getEnnumeration(@PathVariable Long id) {
        log.debug("REST request to get Ennumeration : {}", id);
        Optional<Ennumeration> ennumeration = ennumerationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ennumeration);
    }

    /**
     * {@code DELETE  /ennumerations/:id} : delete the "id" ennumeration.
     *
     * @param id the id of the ennumeration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ennumerations/{id}")
    public ResponseEntity<Void> deleteEnnumeration(@PathVariable Long id) {
        log.debug("REST request to delete Ennumeration : {}", id);
        ennumerationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
