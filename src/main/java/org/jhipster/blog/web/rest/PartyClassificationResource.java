package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PartyClassification;
import org.jhipster.blog.repository.PartyClassificationRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PartyClassification}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyClassificationResource {

    private final Logger log = LoggerFactory.getLogger(PartyClassificationResource.class);

    private static final String ENTITY_NAME = "partyClassification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyClassificationRepository partyClassificationRepository;

    public PartyClassificationResource(PartyClassificationRepository partyClassificationRepository) {
        this.partyClassificationRepository = partyClassificationRepository;
    }

    /**
     * {@code POST  /party-classifications} : Create a new partyClassification.
     *
     * @param partyClassification the partyClassification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyClassification, or with status {@code 400 (Bad Request)} if the partyClassification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-classifications")
    public ResponseEntity<PartyClassification> createPartyClassification(@RequestBody PartyClassification partyClassification)
        throws URISyntaxException {
        log.debug("REST request to save PartyClassification : {}", partyClassification);
        if (partyClassification.getId() != null) {
            throw new BadRequestAlertException("A new partyClassification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyClassification result = partyClassificationRepository.save(partyClassification);
        return ResponseEntity
            .created(new URI("/api/party-classifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-classifications/:id} : Updates an existing partyClassification.
     *
     * @param id the id of the partyClassification to save.
     * @param partyClassification the partyClassification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyClassification,
     * or with status {@code 400 (Bad Request)} if the partyClassification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyClassification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-classifications/{id}")
    public ResponseEntity<PartyClassification> updatePartyClassification(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyClassification partyClassification
    ) throws URISyntaxException {
        log.debug("REST request to update PartyClassification : {}, {}", id, partyClassification);
        if (partyClassification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyClassification.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyClassificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PartyClassification result = partyClassificationRepository.save(partyClassification);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyClassification.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /party-classifications/:id} : Partial updates given fields of an existing partyClassification, field will ignore if it is null
     *
     * @param id the id of the partyClassification to save.
     * @param partyClassification the partyClassification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyClassification,
     * or with status {@code 400 (Bad Request)} if the partyClassification is not valid,
     * or with status {@code 404 (Not Found)} if the partyClassification is not found,
     * or with status {@code 500 (Internal Server Error)} if the partyClassification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/party-classifications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartyClassification> partialUpdatePartyClassification(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyClassification partyClassification
    ) throws URISyntaxException {
        log.debug("REST request to partial update PartyClassification partially : {}, {}", id, partyClassification);
        if (partyClassification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyClassification.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyClassificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartyClassification> result = partyClassificationRepository
            .findById(partyClassification.getId())
            .map(existingPartyClassification -> {
                if (partyClassification.getgUID() != null) {
                    existingPartyClassification.setgUID(partyClassification.getgUID());
                }
                if (partyClassification.getFromDate() != null) {
                    existingPartyClassification.setFromDate(partyClassification.getFromDate());
                }
                if (partyClassification.getThruDate() != null) {
                    existingPartyClassification.setThruDate(partyClassification.getThruDate());
                }
                if (partyClassification.getCreatedBy() != null) {
                    existingPartyClassification.setCreatedBy(partyClassification.getCreatedBy());
                }
                if (partyClassification.getCreatedOn() != null) {
                    existingPartyClassification.setCreatedOn(partyClassification.getCreatedOn());
                }
                if (partyClassification.getUpdatedBy() != null) {
                    existingPartyClassification.setUpdatedBy(partyClassification.getUpdatedBy());
                }
                if (partyClassification.getUpdatedOn() != null) {
                    existingPartyClassification.setUpdatedOn(partyClassification.getUpdatedOn());
                }

                return existingPartyClassification;
            })
            .map(partyClassificationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyClassification.getId().toString())
        );
    }

    /**
     * {@code GET  /party-classifications} : get all the partyClassifications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyClassifications in body.
     */
    @GetMapping("/party-classifications")
    public List<PartyClassification> getAllPartyClassifications() {
        log.debug("REST request to get all PartyClassifications");
        return partyClassificationRepository.findAll();
    }

    /**
     * {@code GET  /party-classifications/:id} : get the "id" partyClassification.
     *
     * @param id the id of the partyClassification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyClassification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-classifications/{id}")
    public ResponseEntity<PartyClassification> getPartyClassification(@PathVariable Long id) {
        log.debug("REST request to get PartyClassification : {}", id);
        Optional<PartyClassification> partyClassification = partyClassificationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyClassification);
    }

    /**
     * {@code DELETE  /party-classifications/:id} : delete the "id" partyClassification.
     *
     * @param id the id of the partyClassification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-classifications/{id}")
    public ResponseEntity<Void> deletePartyClassification(@PathVariable Long id) {
        log.debug("REST request to delete PartyClassification : {}", id);
        partyClassificationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
