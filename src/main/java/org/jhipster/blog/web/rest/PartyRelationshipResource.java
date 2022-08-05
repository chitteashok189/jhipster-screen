package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PartyRelationship;
import org.jhipster.blog.repository.PartyRelationshipRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PartyRelationship}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyRelationshipResource {

    private final Logger log = LoggerFactory.getLogger(PartyRelationshipResource.class);

    private static final String ENTITY_NAME = "partyRelationship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyRelationshipRepository partyRelationshipRepository;

    public PartyRelationshipResource(PartyRelationshipRepository partyRelationshipRepository) {
        this.partyRelationshipRepository = partyRelationshipRepository;
    }

    /**
     * {@code POST  /party-relationships} : Create a new partyRelationship.
     *
     * @param partyRelationship the partyRelationship to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyRelationship, or with status {@code 400 (Bad Request)} if the partyRelationship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-relationships")
    public ResponseEntity<PartyRelationship> createPartyRelationship(@RequestBody PartyRelationship partyRelationship)
        throws URISyntaxException {
        log.debug("REST request to save PartyRelationship : {}", partyRelationship);
        if (partyRelationship.getId() != null) {
            throw new BadRequestAlertException("A new partyRelationship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyRelationship result = partyRelationshipRepository.save(partyRelationship);
        return ResponseEntity
            .created(new URI("/api/party-relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-relationships/:id} : Updates an existing partyRelationship.
     *
     * @param id the id of the partyRelationship to save.
     * @param partyRelationship the partyRelationship to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyRelationship,
     * or with status {@code 400 (Bad Request)} if the partyRelationship is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyRelationship couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-relationships/{id}")
    public ResponseEntity<PartyRelationship> updatePartyRelationship(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyRelationship partyRelationship
    ) throws URISyntaxException {
        log.debug("REST request to update PartyRelationship : {}, {}", id, partyRelationship);
        if (partyRelationship.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyRelationship.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyRelationshipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PartyRelationship result = partyRelationshipRepository.save(partyRelationship);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyRelationship.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /party-relationships/:id} : Partial updates given fields of an existing partyRelationship, field will ignore if it is null
     *
     * @param id the id of the partyRelationship to save.
     * @param partyRelationship the partyRelationship to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyRelationship,
     * or with status {@code 400 (Bad Request)} if the partyRelationship is not valid,
     * or with status {@code 404 (Not Found)} if the partyRelationship is not found,
     * or with status {@code 500 (Internal Server Error)} if the partyRelationship couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/party-relationships/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartyRelationship> partialUpdatePartyRelationship(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyRelationship partyRelationship
    ) throws URISyntaxException {
        log.debug("REST request to partial update PartyRelationship partially : {}, {}", id, partyRelationship);
        if (partyRelationship.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyRelationship.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyRelationshipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartyRelationship> result = partyRelationshipRepository
            .findById(partyRelationship.getId())
            .map(existingPartyRelationship -> {
                if (partyRelationship.getgUID() != null) {
                    existingPartyRelationship.setgUID(partyRelationship.getgUID());
                }
                if (partyRelationship.getPartyIdTo() != null) {
                    existingPartyRelationship.setPartyIdTo(partyRelationship.getPartyIdTo());
                }
                if (partyRelationship.getPartyIdFrom() != null) {
                    existingPartyRelationship.setPartyIdFrom(partyRelationship.getPartyIdFrom());
                }
                if (partyRelationship.getRoleTypeIdFrom() != null) {
                    existingPartyRelationship.setRoleTypeIdFrom(partyRelationship.getRoleTypeIdFrom());
                }
                if (partyRelationship.getRoleTypeIdTo() != null) {
                    existingPartyRelationship.setRoleTypeIdTo(partyRelationship.getRoleTypeIdTo());
                }
                if (partyRelationship.getFromDate() != null) {
                    existingPartyRelationship.setFromDate(partyRelationship.getFromDate());
                }
                if (partyRelationship.getThruDate() != null) {
                    existingPartyRelationship.setThruDate(partyRelationship.getThruDate());
                }
                if (partyRelationship.getRelationshipName() != null) {
                    existingPartyRelationship.setRelationshipName(partyRelationship.getRelationshipName());
                }
                if (partyRelationship.getPositionTitle() != null) {
                    existingPartyRelationship.setPositionTitle(partyRelationship.getPositionTitle());
                }
                if (partyRelationship.getComments() != null) {
                    existingPartyRelationship.setComments(partyRelationship.getComments());
                }
                if (partyRelationship.getCreatedBy() != null) {
                    existingPartyRelationship.setCreatedBy(partyRelationship.getCreatedBy());
                }
                if (partyRelationship.getCreatedOn() != null) {
                    existingPartyRelationship.setCreatedOn(partyRelationship.getCreatedOn());
                }
                if (partyRelationship.getUpdatedBy() != null) {
                    existingPartyRelationship.setUpdatedBy(partyRelationship.getUpdatedBy());
                }
                if (partyRelationship.getUpdatedOn() != null) {
                    existingPartyRelationship.setUpdatedOn(partyRelationship.getUpdatedOn());
                }

                return existingPartyRelationship;
            })
            .map(partyRelationshipRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyRelationship.getId().toString())
        );
    }

    /**
     * {@code GET  /party-relationships} : get all the partyRelationships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyRelationships in body.
     */
    @GetMapping("/party-relationships")
    public List<PartyRelationship> getAllPartyRelationships() {
        log.debug("REST request to get all PartyRelationships");
        return partyRelationshipRepository.findAll();
    }

    /**
     * {@code GET  /party-relationships/:id} : get the "id" partyRelationship.
     *
     * @param id the id of the partyRelationship to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyRelationship, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-relationships/{id}")
    public ResponseEntity<PartyRelationship> getPartyRelationship(@PathVariable Long id) {
        log.debug("REST request to get PartyRelationship : {}", id);
        Optional<PartyRelationship> partyRelationship = partyRelationshipRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyRelationship);
    }

    /**
     * {@code DELETE  /party-relationships/:id} : delete the "id" partyRelationship.
     *
     * @param id the id of the partyRelationship to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-relationships/{id}")
    public ResponseEntity<Void> deletePartyRelationship(@PathVariable Long id) {
        log.debug("REST request to delete PartyRelationship : {}", id);
        partyRelationshipRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
