package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Scouting;
import org.jhipster.blog.repository.ScoutingRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Scouting}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ScoutingResource {

    private final Logger log = LoggerFactory.getLogger(ScoutingResource.class);

    private static final String ENTITY_NAME = "scouting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScoutingRepository scoutingRepository;

    public ScoutingResource(ScoutingRepository scoutingRepository) {
        this.scoutingRepository = scoutingRepository;
    }

    /**
     * {@code POST  /scoutings} : Create a new scouting.
     *
     * @param scouting the scouting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scouting, or with status {@code 400 (Bad Request)} if the scouting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scoutings")
    public ResponseEntity<Scouting> createScouting(@RequestBody Scouting scouting) throws URISyntaxException {
        log.debug("REST request to save Scouting : {}", scouting);
        if (scouting.getId() != null) {
            throw new BadRequestAlertException("A new scouting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Scouting result = scoutingRepository.save(scouting);
        return ResponseEntity
            .created(new URI("/api/scoutings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scoutings/:id} : Updates an existing scouting.
     *
     * @param id the id of the scouting to save.
     * @param scouting the scouting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scouting,
     * or with status {@code 400 (Bad Request)} if the scouting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scouting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scoutings/{id}")
    public ResponseEntity<Scouting> updateScouting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Scouting scouting
    ) throws URISyntaxException {
        log.debug("REST request to update Scouting : {}, {}", id, scouting);
        if (scouting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scouting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scoutingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Scouting result = scoutingRepository.save(scouting);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scouting.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scoutings/:id} : Partial updates given fields of an existing scouting, field will ignore if it is null
     *
     * @param id the id of the scouting to save.
     * @param scouting the scouting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scouting,
     * or with status {@code 400 (Bad Request)} if the scouting is not valid,
     * or with status {@code 404 (Not Found)} if the scouting is not found,
     * or with status {@code 500 (Internal Server Error)} if the scouting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/scoutings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Scouting> partialUpdateScouting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Scouting scouting
    ) throws URISyntaxException {
        log.debug("REST request to partial update Scouting partially : {}, {}", id, scouting);
        if (scouting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scouting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scoutingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Scouting> result = scoutingRepository
            .findById(scouting.getId())
            .map(existingScouting -> {
                if (scouting.getgUID() != null) {
                    existingScouting.setgUID(scouting.getgUID());
                }
                if (scouting.getScoutingDate() != null) {
                    existingScouting.setScoutingDate(scouting.getScoutingDate());
                }
                if (scouting.getScout() != null) {
                    existingScouting.setScout(scouting.getScout());
                }
                if (scouting.getScoutingType() != null) {
                    existingScouting.setScoutingType(scouting.getScoutingType());
                }
                if (scouting.getScoutingCoordinates() != null) {
                    existingScouting.setScoutingCoordinates(scouting.getScoutingCoordinates());
                }
                if (scouting.getScoutingArea() != null) {
                    existingScouting.setScoutingArea(scouting.getScoutingArea());
                }
                if (scouting.getCropState() != null) {
                    existingScouting.setCropState(scouting.getCropState());
                }
                if (scouting.getComments() != null) {
                    existingScouting.setComments(scouting.getComments());
                }
                if (scouting.getCreatedBy() != null) {
                    existingScouting.setCreatedBy(scouting.getCreatedBy());
                }
                if (scouting.getCreatedOn() != null) {
                    existingScouting.setCreatedOn(scouting.getCreatedOn());
                }
                if (scouting.getUpdatedBy() != null) {
                    existingScouting.setUpdatedBy(scouting.getUpdatedBy());
                }
                if (scouting.getUpdatedOn() != null) {
                    existingScouting.setUpdatedOn(scouting.getUpdatedOn());
                }

                return existingScouting;
            })
            .map(scoutingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scouting.getId().toString())
        );
    }

    /**
     * {@code GET  /scoutings} : get all the scoutings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scoutings in body.
     */
    @GetMapping("/scoutings")
    public List<Scouting> getAllScoutings() {
        log.debug("REST request to get all Scoutings");
        return scoutingRepository.findAll();
    }

    /**
     * {@code GET  /scoutings/:id} : get the "id" scouting.
     *
     * @param id the id of the scouting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scouting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scoutings/{id}")
    public ResponseEntity<Scouting> getScouting(@PathVariable Long id) {
        log.debug("REST request to get Scouting : {}", id);
        Optional<Scouting> scouting = scoutingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(scouting);
    }

    /**
     * {@code DELETE  /scoutings/:id} : delete the "id" scouting.
     *
     * @param id the id of the scouting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scoutings/{id}")
    public ResponseEntity<Void> deleteScouting(@PathVariable Long id) {
        log.debug("REST request to delete Scouting : {}", id);
        scoutingRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
