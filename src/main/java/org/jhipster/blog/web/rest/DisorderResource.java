package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Disorder;
import org.jhipster.blog.repository.DisorderRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Disorder}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DisorderResource {

    private final Logger log = LoggerFactory.getLogger(DisorderResource.class);

    private static final String ENTITY_NAME = "disorder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisorderRepository disorderRepository;

    public DisorderResource(DisorderRepository disorderRepository) {
        this.disorderRepository = disorderRepository;
    }

    /**
     * {@code POST  /disorders} : Create a new disorder.
     *
     * @param disorder the disorder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disorder, or with status {@code 400 (Bad Request)} if the disorder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/disorders")
    public ResponseEntity<Disorder> createDisorder(@RequestBody Disorder disorder) throws URISyntaxException {
        log.debug("REST request to save Disorder : {}", disorder);
        if (disorder.getId() != null) {
            throw new BadRequestAlertException("A new disorder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Disorder result = disorderRepository.save(disorder);
        return ResponseEntity
            .created(new URI("/api/disorders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /disorders/:id} : Updates an existing disorder.
     *
     * @param id the id of the disorder to save.
     * @param disorder the disorder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disorder,
     * or with status {@code 400 (Bad Request)} if the disorder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disorder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/disorders/{id}")
    public ResponseEntity<Disorder> updateDisorder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Disorder disorder
    ) throws URISyntaxException {
        log.debug("REST request to update Disorder : {}, {}", id, disorder);
        if (disorder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disorder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disorderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Disorder result = disorderRepository.save(disorder);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disorder.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /disorders/:id} : Partial updates given fields of an existing disorder, field will ignore if it is null
     *
     * @param id the id of the disorder to save.
     * @param disorder the disorder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disorder,
     * or with status {@code 400 (Bad Request)} if the disorder is not valid,
     * or with status {@code 404 (Not Found)} if the disorder is not found,
     * or with status {@code 500 (Internal Server Error)} if the disorder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/disorders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Disorder> partialUpdateDisorder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Disorder disorder
    ) throws URISyntaxException {
        log.debug("REST request to partial update Disorder partially : {}, {}", id, disorder);
        if (disorder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disorder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disorderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Disorder> result = disorderRepository
            .findById(disorder.getId())
            .map(existingDisorder -> {
                if (disorder.getgUID() != null) {
                    existingDisorder.setgUID(disorder.getgUID());
                }
                if (disorder.getPhysiologicalDisorder() != null) {
                    existingDisorder.setPhysiologicalDisorder(disorder.getPhysiologicalDisorder());
                }
                if (disorder.getCause() != null) {
                    existingDisorder.setCause(disorder.getCause());
                }
                if (disorder.getControlMeasure() != null) {
                    existingDisorder.setControlMeasure(disorder.getControlMeasure());
                }
                if (disorder.getCreatedBy() != null) {
                    existingDisorder.setCreatedBy(disorder.getCreatedBy());
                }
                if (disorder.getCreatedOn() != null) {
                    existingDisorder.setCreatedOn(disorder.getCreatedOn());
                }
                if (disorder.getUpdatedBy() != null) {
                    existingDisorder.setUpdatedBy(disorder.getUpdatedBy());
                }
                if (disorder.getUpdatedOn() != null) {
                    existingDisorder.setUpdatedOn(disorder.getUpdatedOn());
                }

                return existingDisorder;
            })
            .map(disorderRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disorder.getId().toString())
        );
    }

    /**
     * {@code GET  /disorders} : get all the disorders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disorders in body.
     */
    @GetMapping("/disorders")
    public List<Disorder> getAllDisorders() {
        log.debug("REST request to get all Disorders");
        return disorderRepository.findAll();
    }

    /**
     * {@code GET  /disorders/:id} : get the "id" disorder.
     *
     * @param id the id of the disorder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disorder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/disorders/{id}")
    public ResponseEntity<Disorder> getDisorder(@PathVariable Long id) {
        log.debug("REST request to get Disorder : {}", id);
        Optional<Disorder> disorder = disorderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(disorder);
    }

    /**
     * {@code DELETE  /disorders/:id} : delete the "id" disorder.
     *
     * @param id the id of the disorder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/disorders/{id}")
    public ResponseEntity<Void> deleteDisorder(@PathVariable Long id) {
        log.debug("REST request to delete Disorder : {}", id);
        disorderRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
