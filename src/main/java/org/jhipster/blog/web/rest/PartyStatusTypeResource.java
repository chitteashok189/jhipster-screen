package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PartyStatusType;
import org.jhipster.blog.repository.PartyStatusTypeRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PartyStatusType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyStatusTypeResource {

    private final Logger log = LoggerFactory.getLogger(PartyStatusTypeResource.class);

    private static final String ENTITY_NAME = "partyStatusType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyStatusTypeRepository partyStatusTypeRepository;

    public PartyStatusTypeResource(PartyStatusTypeRepository partyStatusTypeRepository) {
        this.partyStatusTypeRepository = partyStatusTypeRepository;
    }

    /**
     * {@code POST  /party-status-types} : Create a new partyStatusType.
     *
     * @param partyStatusType the partyStatusType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyStatusType, or with status {@code 400 (Bad Request)} if the partyStatusType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-status-types")
    public ResponseEntity<PartyStatusType> createPartyStatusType(@RequestBody PartyStatusType partyStatusType) throws URISyntaxException {
        log.debug("REST request to save PartyStatusType : {}", partyStatusType);
        if (partyStatusType.getId() != null) {
            throw new BadRequestAlertException("A new partyStatusType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyStatusType result = partyStatusTypeRepository.save(partyStatusType);
        return ResponseEntity
            .created(new URI("/api/party-status-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-status-types/:id} : Updates an existing partyStatusType.
     *
     * @param id the id of the partyStatusType to save.
     * @param partyStatusType the partyStatusType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyStatusType,
     * or with status {@code 400 (Bad Request)} if the partyStatusType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyStatusType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-status-types/{id}")
    public ResponseEntity<PartyStatusType> updatePartyStatusType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyStatusType partyStatusType
    ) throws URISyntaxException {
        log.debug("REST request to update PartyStatusType : {}, {}", id, partyStatusType);
        if (partyStatusType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyStatusType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyStatusTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PartyStatusType result = partyStatusTypeRepository.save(partyStatusType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyStatusType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /party-status-types/:id} : Partial updates given fields of an existing partyStatusType, field will ignore if it is null
     *
     * @param id the id of the partyStatusType to save.
     * @param partyStatusType the partyStatusType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyStatusType,
     * or with status {@code 400 (Bad Request)} if the partyStatusType is not valid,
     * or with status {@code 404 (Not Found)} if the partyStatusType is not found,
     * or with status {@code 500 (Internal Server Error)} if the partyStatusType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/party-status-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartyStatusType> partialUpdatePartyStatusType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyStatusType partyStatusType
    ) throws URISyntaxException {
        log.debug("REST request to partial update PartyStatusType partially : {}, {}", id, partyStatusType);
        if (partyStatusType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyStatusType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyStatusTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartyStatusType> result = partyStatusTypeRepository
            .findById(partyStatusType.getId())
            .map(existingPartyStatusType -> {
                if (partyStatusType.getgUID() != null) {
                    existingPartyStatusType.setgUID(partyStatusType.getgUID());
                }
                if (partyStatusType.getParentTypeID() != null) {
                    existingPartyStatusType.setParentTypeID(partyStatusType.getParentTypeID());
                }
                if (partyStatusType.getHasTable() != null) {
                    existingPartyStatusType.setHasTable(partyStatusType.getHasTable());
                }
                if (partyStatusType.getDescription() != null) {
                    existingPartyStatusType.setDescription(partyStatusType.getDescription());
                }
                if (partyStatusType.getChildStatusType() != null) {
                    existingPartyStatusType.setChildStatusType(partyStatusType.getChildStatusType());
                }
                if (partyStatusType.getCreatedBy() != null) {
                    existingPartyStatusType.setCreatedBy(partyStatusType.getCreatedBy());
                }
                if (partyStatusType.getCreatedOn() != null) {
                    existingPartyStatusType.setCreatedOn(partyStatusType.getCreatedOn());
                }
                if (partyStatusType.getUpdatedBy() != null) {
                    existingPartyStatusType.setUpdatedBy(partyStatusType.getUpdatedBy());
                }
                if (partyStatusType.getUpdatedOn() != null) {
                    existingPartyStatusType.setUpdatedOn(partyStatusType.getUpdatedOn());
                }

                return existingPartyStatusType;
            })
            .map(partyStatusTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyStatusType.getId().toString())
        );
    }

    /**
     * {@code GET  /party-status-types} : get all the partyStatusTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyStatusTypes in body.
     */
    @GetMapping("/party-status-types")
    public List<PartyStatusType> getAllPartyStatusTypes() {
        log.debug("REST request to get all PartyStatusTypes");
        return partyStatusTypeRepository.findAll();
    }

    /**
     * {@code GET  /party-status-types/:id} : get the "id" partyStatusType.
     *
     * @param id the id of the partyStatusType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyStatusType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-status-types/{id}")
    public ResponseEntity<PartyStatusType> getPartyStatusType(@PathVariable Long id) {
        log.debug("REST request to get PartyStatusType : {}", id);
        Optional<PartyStatusType> partyStatusType = partyStatusTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyStatusType);
    }

    /**
     * {@code DELETE  /party-status-types/:id} : delete the "id" partyStatusType.
     *
     * @param id the id of the partyStatusType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-status-types/{id}")
    public ResponseEntity<Void> deletePartyStatusType(@PathVariable Long id) {
        log.debug("REST request to delete PartyStatusType : {}", id);
        partyStatusTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
