package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PartyRelationshipType;
import org.jhipster.blog.repository.PartyRelationshipTypeRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PartyRelationshipType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyRelationshipTypeResource {

    private final Logger log = LoggerFactory.getLogger(PartyRelationshipTypeResource.class);

    private static final String ENTITY_NAME = "partyRelationshipType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyRelationshipTypeRepository partyRelationshipTypeRepository;

    public PartyRelationshipTypeResource(PartyRelationshipTypeRepository partyRelationshipTypeRepository) {
        this.partyRelationshipTypeRepository = partyRelationshipTypeRepository;
    }

    /**
     * {@code POST  /party-relationship-types} : Create a new partyRelationshipType.
     *
     * @param partyRelationshipType the partyRelationshipType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyRelationshipType, or with status {@code 400 (Bad Request)} if the partyRelationshipType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-relationship-types")
    public ResponseEntity<PartyRelationshipType> createPartyRelationshipType(@RequestBody PartyRelationshipType partyRelationshipType)
        throws URISyntaxException {
        log.debug("REST request to save PartyRelationshipType : {}", partyRelationshipType);
        if (partyRelationshipType.getId() != null) {
            throw new BadRequestAlertException("A new partyRelationshipType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyRelationshipType result = partyRelationshipTypeRepository.save(partyRelationshipType);
        return ResponseEntity
            .created(new URI("/api/party-relationship-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-relationship-types/:id} : Updates an existing partyRelationshipType.
     *
     * @param id the id of the partyRelationshipType to save.
     * @param partyRelationshipType the partyRelationshipType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyRelationshipType,
     * or with status {@code 400 (Bad Request)} if the partyRelationshipType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyRelationshipType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-relationship-types/{id}")
    public ResponseEntity<PartyRelationshipType> updatePartyRelationshipType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyRelationshipType partyRelationshipType
    ) throws URISyntaxException {
        log.debug("REST request to update PartyRelationshipType : {}, {}", id, partyRelationshipType);
        if (partyRelationshipType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyRelationshipType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyRelationshipTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PartyRelationshipType result = partyRelationshipTypeRepository.save(partyRelationshipType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyRelationshipType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /party-relationship-types/:id} : Partial updates given fields of an existing partyRelationshipType, field will ignore if it is null
     *
     * @param id the id of the partyRelationshipType to save.
     * @param partyRelationshipType the partyRelationshipType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyRelationshipType,
     * or with status {@code 400 (Bad Request)} if the partyRelationshipType is not valid,
     * or with status {@code 404 (Not Found)} if the partyRelationshipType is not found,
     * or with status {@code 500 (Internal Server Error)} if the partyRelationshipType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/party-relationship-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartyRelationshipType> partialUpdatePartyRelationshipType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyRelationshipType partyRelationshipType
    ) throws URISyntaxException {
        log.debug("REST request to partial update PartyRelationshipType partially : {}, {}", id, partyRelationshipType);
        if (partyRelationshipType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyRelationshipType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyRelationshipTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartyRelationshipType> result = partyRelationshipTypeRepository
            .findById(partyRelationshipType.getId())
            .map(existingPartyRelationshipType -> {
                if (partyRelationshipType.getgUID() != null) {
                    existingPartyRelationshipType.setgUID(partyRelationshipType.getgUID());
                }
                if (partyRelationshipType.getHasTable() != null) {
                    existingPartyRelationshipType.setHasTable(partyRelationshipType.getHasTable());
                }
                if (partyRelationshipType.getPartyRelationshipName() != null) {
                    existingPartyRelationshipType.setPartyRelationshipName(partyRelationshipType.getPartyRelationshipName());
                }
                if (partyRelationshipType.getDescription() != null) {
                    existingPartyRelationshipType.setDescription(partyRelationshipType.getDescription());
                }
                if (partyRelationshipType.getRoleTypeIdValidFrom() != null) {
                    existingPartyRelationshipType.setRoleTypeIdValidFrom(partyRelationshipType.getRoleTypeIdValidFrom());
                }
                if (partyRelationshipType.getRoleTypeIdValidTo() != null) {
                    existingPartyRelationshipType.setRoleTypeIdValidTo(partyRelationshipType.getRoleTypeIdValidTo());
                }
                if (partyRelationshipType.getCreatedBy() != null) {
                    existingPartyRelationshipType.setCreatedBy(partyRelationshipType.getCreatedBy());
                }
                if (partyRelationshipType.getCreatedOn() != null) {
                    existingPartyRelationshipType.setCreatedOn(partyRelationshipType.getCreatedOn());
                }
                if (partyRelationshipType.getUpdatedBy() != null) {
                    existingPartyRelationshipType.setUpdatedBy(partyRelationshipType.getUpdatedBy());
                }
                if (partyRelationshipType.getUpdatedOn() != null) {
                    existingPartyRelationshipType.setUpdatedOn(partyRelationshipType.getUpdatedOn());
                }

                return existingPartyRelationshipType;
            })
            .map(partyRelationshipTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyRelationshipType.getId().toString())
        );
    }

    /**
     * {@code GET  /party-relationship-types} : get all the partyRelationshipTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyRelationshipTypes in body.
     */
    @GetMapping("/party-relationship-types")
    public List<PartyRelationshipType> getAllPartyRelationshipTypes() {
        log.debug("REST request to get all PartyRelationshipTypes");
        return partyRelationshipTypeRepository.findAll();
    }

    /**
     * {@code GET  /party-relationship-types/:id} : get the "id" partyRelationshipType.
     *
     * @param id the id of the partyRelationshipType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyRelationshipType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-relationship-types/{id}")
    public ResponseEntity<PartyRelationshipType> getPartyRelationshipType(@PathVariable Long id) {
        log.debug("REST request to get PartyRelationshipType : {}", id);
        Optional<PartyRelationshipType> partyRelationshipType = partyRelationshipTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyRelationshipType);
    }

    /**
     * {@code DELETE  /party-relationship-types/:id} : delete the "id" partyRelationshipType.
     *
     * @param id the id of the partyRelationshipType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-relationship-types/{id}")
    public ResponseEntity<Void> deletePartyRelationshipType(@PathVariable Long id) {
        log.debug("REST request to delete PartyRelationshipType : {}", id);
        partyRelationshipTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
