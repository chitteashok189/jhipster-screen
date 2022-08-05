package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Pest;
import org.jhipster.blog.repository.PestRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Pest}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PestResource {

    private final Logger log = LoggerFactory.getLogger(PestResource.class);

    private static final String ENTITY_NAME = "pest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PestRepository pestRepository;

    public PestResource(PestRepository pestRepository) {
        this.pestRepository = pestRepository;
    }

    /**
     * {@code POST  /pests} : Create a new pest.
     *
     * @param pest the pest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pest, or with status {@code 400 (Bad Request)} if the pest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pests")
    public ResponseEntity<Pest> createPest(@RequestBody Pest pest) throws URISyntaxException {
        log.debug("REST request to save Pest : {}", pest);
        if (pest.getId() != null) {
            throw new BadRequestAlertException("A new pest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pest result = pestRepository.save(pest);
        return ResponseEntity
            .created(new URI("/api/pests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pests/:id} : Updates an existing pest.
     *
     * @param id the id of the pest to save.
     * @param pest the pest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pest,
     * or with status {@code 400 (Bad Request)} if the pest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pests/{id}")
    public ResponseEntity<Pest> updatePest(@PathVariable(value = "id", required = false) final Long id, @RequestBody Pest pest)
        throws URISyntaxException {
        log.debug("REST request to update Pest : {}, {}", id, pest);
        if (pest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Pest result = pestRepository.save(pest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pest.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pests/:id} : Partial updates given fields of an existing pest, field will ignore if it is null
     *
     * @param id the id of the pest to save.
     * @param pest the pest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pest,
     * or with status {@code 400 (Bad Request)} if the pest is not valid,
     * or with status {@code 404 (Not Found)} if the pest is not found,
     * or with status {@code 500 (Internal Server Error)} if the pest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pest> partialUpdatePest(@PathVariable(value = "id", required = false) final Long id, @RequestBody Pest pest)
        throws URISyntaxException {
        log.debug("REST request to partial update Pest partially : {}, {}", id, pest);
        if (pest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pest> result = pestRepository
            .findById(pest.getId())
            .map(existingPest -> {
                if (pest.getgUID() != null) {
                    existingPest.setgUID(pest.getgUID());
                }
                if (pest.getThreatLevel() != null) {
                    existingPest.setThreatLevel(pest.getThreatLevel());
                }
                if (pest.getName() != null) {
                    existingPest.setName(pest.getName());
                }
                if (pest.getScientificName() != null) {
                    existingPest.setScientificName(pest.getScientificName());
                }
                if (pest.getAttachements() != null) {
                    existingPest.setAttachements(pest.getAttachements());
                }
                if (pest.getCreatedBy() != null) {
                    existingPest.setCreatedBy(pest.getCreatedBy());
                }
                if (pest.getCreatedOn() != null) {
                    existingPest.setCreatedOn(pest.getCreatedOn());
                }
                if (pest.getUpdatedBy() != null) {
                    existingPest.setUpdatedBy(pest.getUpdatedBy());
                }
                if (pest.getUpdatedOn() != null) {
                    existingPest.setUpdatedOn(pest.getUpdatedOn());
                }

                return existingPest;
            })
            .map(pestRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pest.getId().toString())
        );
    }

    /**
     * {@code GET  /pests} : get all the pests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pests in body.
     */
    @GetMapping("/pests")
    public List<Pest> getAllPests() {
        log.debug("REST request to get all Pests");
        return pestRepository.findAll();
    }

    /**
     * {@code GET  /pests/:id} : get the "id" pest.
     *
     * @param id the id of the pest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pests/{id}")
    public ResponseEntity<Pest> getPest(@PathVariable Long id) {
        log.debug("REST request to get Pest : {}", id);
        Optional<Pest> pest = pestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pest);
    }

    /**
     * {@code DELETE  /pests/:id} : delete the "id" pest.
     *
     * @param id the id of the pest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pests/{id}")
    public ResponseEntity<Void> deletePest(@PathVariable Long id) {
        log.debug("REST request to delete Pest : {}", id);
        pestRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
