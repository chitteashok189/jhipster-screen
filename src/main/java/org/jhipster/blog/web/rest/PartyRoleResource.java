package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PartyRole;
import org.jhipster.blog.repository.PartyRoleRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PartyRole}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyRoleResource {

    private final Logger log = LoggerFactory.getLogger(PartyRoleResource.class);

    private static final String ENTITY_NAME = "partyRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyRoleRepository partyRoleRepository;

    public PartyRoleResource(PartyRoleRepository partyRoleRepository) {
        this.partyRoleRepository = partyRoleRepository;
    }

    /**
     * {@code POST  /party-roles} : Create a new partyRole.
     *
     * @param partyRole the partyRole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyRole, or with status {@code 400 (Bad Request)} if the partyRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-roles")
    public ResponseEntity<PartyRole> createPartyRole(@RequestBody PartyRole partyRole) throws URISyntaxException {
        log.debug("REST request to save PartyRole : {}", partyRole);
        if (partyRole.getId() != null) {
            throw new BadRequestAlertException("A new partyRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyRole result = partyRoleRepository.save(partyRole);
        return ResponseEntity
            .created(new URI("/api/party-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-roles/:id} : Updates an existing partyRole.
     *
     * @param id the id of the partyRole to save.
     * @param partyRole the partyRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyRole,
     * or with status {@code 400 (Bad Request)} if the partyRole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-roles/{id}")
    public ResponseEntity<PartyRole> updatePartyRole(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyRole partyRole
    ) throws URISyntaxException {
        log.debug("REST request to update PartyRole : {}, {}", id, partyRole);
        if (partyRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyRole.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PartyRole result = partyRoleRepository.save(partyRole);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyRole.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /party-roles/:id} : Partial updates given fields of an existing partyRole, field will ignore if it is null
     *
     * @param id the id of the partyRole to save.
     * @param partyRole the partyRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyRole,
     * or with status {@code 400 (Bad Request)} if the partyRole is not valid,
     * or with status {@code 404 (Not Found)} if the partyRole is not found,
     * or with status {@code 500 (Internal Server Error)} if the partyRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/party-roles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartyRole> partialUpdatePartyRole(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyRole partyRole
    ) throws URISyntaxException {
        log.debug("REST request to partial update PartyRole partially : {}, {}", id, partyRole);
        if (partyRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyRole.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartyRole> result = partyRoleRepository
            .findById(partyRole.getId())
            .map(existingPartyRole -> {
                if (partyRole.getgUID() != null) {
                    existingPartyRole.setgUID(partyRole.getgUID());
                }
                if (partyRole.getFromAgreement() != null) {
                    existingPartyRole.setFromAgreement(partyRole.getFromAgreement());
                }
                if (partyRole.getToAgreement() != null) {
                    existingPartyRole.setToAgreement(partyRole.getToAgreement());
                }
                if (partyRole.getFromCommunicationEvent() != null) {
                    existingPartyRole.setFromCommunicationEvent(partyRole.getFromCommunicationEvent());
                }
                if (partyRole.getToCommunicationEvent() != null) {
                    existingPartyRole.setToCommunicationEvent(partyRole.getToCommunicationEvent());
                }
                if (partyRole.getPartyIdFrom() != null) {
                    existingPartyRole.setPartyIdFrom(partyRole.getPartyIdFrom());
                }
                if (partyRole.getPartyIdTO() != null) {
                    existingPartyRole.setPartyIdTO(partyRole.getPartyIdTO());
                }
                if (partyRole.getCreatedBy() != null) {
                    existingPartyRole.setCreatedBy(partyRole.getCreatedBy());
                }
                if (partyRole.getCreatedOn() != null) {
                    existingPartyRole.setCreatedOn(partyRole.getCreatedOn());
                }
                if (partyRole.getUpdatedBy() != null) {
                    existingPartyRole.setUpdatedBy(partyRole.getUpdatedBy());
                }
                if (partyRole.getUpdatedOn() != null) {
                    existingPartyRole.setUpdatedOn(partyRole.getUpdatedOn());
                }

                return existingPartyRole;
            })
            .map(partyRoleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyRole.getId().toString())
        );
    }

    /**
     * {@code GET  /party-roles} : get all the partyRoles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyRoles in body.
     */
    @GetMapping("/party-roles")
    public List<PartyRole> getAllPartyRoles() {
        log.debug("REST request to get all PartyRoles");
        return partyRoleRepository.findAll();
    }

    /**
     * {@code GET  /party-roles/:id} : get the "id" partyRole.
     *
     * @param id the id of the partyRole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyRole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-roles/{id}")
    public ResponseEntity<PartyRole> getPartyRole(@PathVariable Long id) {
        log.debug("REST request to get PartyRole : {}", id);
        Optional<PartyRole> partyRole = partyRoleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyRole);
    }

    /**
     * {@code DELETE  /party-roles/:id} : delete the "id" partyRole.
     *
     * @param id the id of the partyRole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-roles/{id}")
    public ResponseEntity<Void> deletePartyRole(@PathVariable Long id) {
        log.debug("REST request to delete PartyRole : {}", id);
        partyRoleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
