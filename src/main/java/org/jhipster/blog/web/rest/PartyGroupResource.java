package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PartyGroup;
import org.jhipster.blog.repository.PartyGroupRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PartyGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyGroupResource {

    private final Logger log = LoggerFactory.getLogger(PartyGroupResource.class);

    private static final String ENTITY_NAME = "partyGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyGroupRepository partyGroupRepository;

    public PartyGroupResource(PartyGroupRepository partyGroupRepository) {
        this.partyGroupRepository = partyGroupRepository;
    }

    /**
     * {@code POST  /party-groups} : Create a new partyGroup.
     *
     * @param partyGroup the partyGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyGroup, or with status {@code 400 (Bad Request)} if the partyGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-groups")
    public ResponseEntity<PartyGroup> createPartyGroup(@RequestBody PartyGroup partyGroup) throws URISyntaxException {
        log.debug("REST request to save PartyGroup : {}", partyGroup);
        if (partyGroup.getId() != null) {
            throw new BadRequestAlertException("A new partyGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyGroup result = partyGroupRepository.save(partyGroup);
        return ResponseEntity
            .created(new URI("/api/party-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-groups/:id} : Updates an existing partyGroup.
     *
     * @param id the id of the partyGroup to save.
     * @param partyGroup the partyGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyGroup,
     * or with status {@code 400 (Bad Request)} if the partyGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-groups/{id}")
    public ResponseEntity<PartyGroup> updatePartyGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyGroup partyGroup
    ) throws URISyntaxException {
        log.debug("REST request to update PartyGroup : {}, {}", id, partyGroup);
        if (partyGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PartyGroup result = partyGroupRepository.save(partyGroup);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /party-groups/:id} : Partial updates given fields of an existing partyGroup, field will ignore if it is null
     *
     * @param id the id of the partyGroup to save.
     * @param partyGroup the partyGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyGroup,
     * or with status {@code 400 (Bad Request)} if the partyGroup is not valid,
     * or with status {@code 404 (Not Found)} if the partyGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the partyGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/party-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartyGroup> partialUpdatePartyGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyGroup partyGroup
    ) throws URISyntaxException {
        log.debug("REST request to partial update PartyGroup partially : {}, {}", id, partyGroup);
        if (partyGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartyGroup> result = partyGroupRepository
            .findById(partyGroup.getId())
            .map(existingPartyGroup -> {
                if (partyGroup.getgUID() != null) {
                    existingPartyGroup.setgUID(partyGroup.getgUID());
                }
                if (partyGroup.getGroupName() != null) {
                    existingPartyGroup.setGroupName(partyGroup.getGroupName());
                }
                if (partyGroup.getLocalGroupName() != null) {
                    existingPartyGroup.setLocalGroupName(partyGroup.getLocalGroupName());
                }
                if (partyGroup.getOfficeSiteName() != null) {
                    existingPartyGroup.setOfficeSiteName(partyGroup.getOfficeSiteName());
                }
                if (partyGroup.getComments() != null) {
                    existingPartyGroup.setComments(partyGroup.getComments());
                }
                if (partyGroup.getLogoImageUrl() != null) {
                    existingPartyGroup.setLogoImageUrl(partyGroup.getLogoImageUrl());
                }
                if (partyGroup.getCreatedBy() != null) {
                    existingPartyGroup.setCreatedBy(partyGroup.getCreatedBy());
                }
                if (partyGroup.getCreatedOn() != null) {
                    existingPartyGroup.setCreatedOn(partyGroup.getCreatedOn());
                }
                if (partyGroup.getUpdatedBy() != null) {
                    existingPartyGroup.setUpdatedBy(partyGroup.getUpdatedBy());
                }
                if (partyGroup.getUpdatedOn() != null) {
                    existingPartyGroup.setUpdatedOn(partyGroup.getUpdatedOn());
                }

                return existingPartyGroup;
            })
            .map(partyGroupRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /party-groups} : get all the partyGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyGroups in body.
     */
    @GetMapping("/party-groups")
    public List<PartyGroup> getAllPartyGroups() {
        log.debug("REST request to get all PartyGroups");
        return partyGroupRepository.findAll();
    }

    /**
     * {@code GET  /party-groups/:id} : get the "id" partyGroup.
     *
     * @param id the id of the partyGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-groups/{id}")
    public ResponseEntity<PartyGroup> getPartyGroup(@PathVariable Long id) {
        log.debug("REST request to get PartyGroup : {}", id);
        Optional<PartyGroup> partyGroup = partyGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyGroup);
    }

    /**
     * {@code DELETE  /party-groups/:id} : delete the "id" partyGroup.
     *
     * @param id the id of the partyGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-groups/{id}")
    public ResponseEntity<Void> deletePartyGroup(@PathVariable Long id) {
        log.debug("REST request to delete PartyGroup : {}", id);
        partyGroupRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
