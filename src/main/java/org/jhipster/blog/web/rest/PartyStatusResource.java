package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PartyStatus;
import org.jhipster.blog.repository.PartyStatusRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PartyStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyStatusResource {

    private final Logger log = LoggerFactory.getLogger(PartyStatusResource.class);

    private static final String ENTITY_NAME = "partyStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyStatusRepository partyStatusRepository;

    public PartyStatusResource(PartyStatusRepository partyStatusRepository) {
        this.partyStatusRepository = partyStatusRepository;
    }

    /**
     * {@code POST  /party-statuses} : Create a new partyStatus.
     *
     * @param partyStatus the partyStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyStatus, or with status {@code 400 (Bad Request)} if the partyStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-statuses")
    public ResponseEntity<PartyStatus> createPartyStatus(@RequestBody PartyStatus partyStatus) throws URISyntaxException {
        log.debug("REST request to save PartyStatus : {}", partyStatus);
        if (partyStatus.getId() != null) {
            throw new BadRequestAlertException("A new partyStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyStatus result = partyStatusRepository.save(partyStatus);
        return ResponseEntity
            .created(new URI("/api/party-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-statuses/:id} : Updates an existing partyStatus.
     *
     * @param id the id of the partyStatus to save.
     * @param partyStatus the partyStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyStatus,
     * or with status {@code 400 (Bad Request)} if the partyStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-statuses/{id}")
    public ResponseEntity<PartyStatus> updatePartyStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyStatus partyStatus
    ) throws URISyntaxException {
        log.debug("REST request to update PartyStatus : {}, {}", id, partyStatus);
        if (partyStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PartyStatus result = partyStatusRepository.save(partyStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /party-statuses/:id} : Partial updates given fields of an existing partyStatus, field will ignore if it is null
     *
     * @param id the id of the partyStatus to save.
     * @param partyStatus the partyStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyStatus,
     * or with status {@code 400 (Bad Request)} if the partyStatus is not valid,
     * or with status {@code 404 (Not Found)} if the partyStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the partyStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/party-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartyStatus> partialUpdatePartyStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyStatus partyStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update PartyStatus partially : {}, {}", id, partyStatus);
        if (partyStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartyStatus> result = partyStatusRepository
            .findById(partyStatus.getId())
            .map(existingPartyStatus -> {
                if (partyStatus.getgUID() != null) {
                    existingPartyStatus.setgUID(partyStatus.getgUID());
                }
                if (partyStatus.getStatusDate() != null) {
                    existingPartyStatus.setStatusDate(partyStatus.getStatusDate());
                }
                if (partyStatus.getCreatedBy() != null) {
                    existingPartyStatus.setCreatedBy(partyStatus.getCreatedBy());
                }
                if (partyStatus.getCreatedOn() != null) {
                    existingPartyStatus.setCreatedOn(partyStatus.getCreatedOn());
                }
                if (partyStatus.getUpdatedBy() != null) {
                    existingPartyStatus.setUpdatedBy(partyStatus.getUpdatedBy());
                }
                if (partyStatus.getUpdatedOn() != null) {
                    existingPartyStatus.setUpdatedOn(partyStatus.getUpdatedOn());
                }

                return existingPartyStatus;
            })
            .map(partyStatusRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /party-statuses} : get all the partyStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyStatuses in body.
     */
    @GetMapping("/party-statuses")
    public List<PartyStatus> getAllPartyStatuses() {
        log.debug("REST request to get all PartyStatuses");
        return partyStatusRepository.findAll();
    }

    /**
     * {@code GET  /party-statuses/:id} : get the "id" partyStatus.
     *
     * @param id the id of the partyStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-statuses/{id}")
    public ResponseEntity<PartyStatus> getPartyStatus(@PathVariable Long id) {
        log.debug("REST request to get PartyStatus : {}", id);
        Optional<PartyStatus> partyStatus = partyStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyStatus);
    }

    /**
     * {@code DELETE  /party-statuses/:id} : delete the "id" partyStatus.
     *
     * @param id the id of the partyStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-statuses/{id}")
    public ResponseEntity<Void> deletePartyStatus(@PathVariable Long id) {
        log.debug("REST request to delete PartyStatus : {}", id);
        partyStatusRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
