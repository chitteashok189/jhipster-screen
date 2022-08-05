package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PartyAttribute;
import org.jhipster.blog.repository.PartyAttributeRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PartyAttribute}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyAttributeResource {

    private final Logger log = LoggerFactory.getLogger(PartyAttributeResource.class);

    private static final String ENTITY_NAME = "partyAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyAttributeRepository partyAttributeRepository;

    public PartyAttributeResource(PartyAttributeRepository partyAttributeRepository) {
        this.partyAttributeRepository = partyAttributeRepository;
    }

    /**
     * {@code POST  /party-attributes} : Create a new partyAttribute.
     *
     * @param partyAttribute the partyAttribute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyAttribute, or with status {@code 400 (Bad Request)} if the partyAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-attributes")
    public ResponseEntity<PartyAttribute> createPartyAttribute(@RequestBody PartyAttribute partyAttribute) throws URISyntaxException {
        log.debug("REST request to save PartyAttribute : {}", partyAttribute);
        if (partyAttribute.getId() != null) {
            throw new BadRequestAlertException("A new partyAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyAttribute result = partyAttributeRepository.save(partyAttribute);
        return ResponseEntity
            .created(new URI("/api/party-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-attributes/:id} : Updates an existing partyAttribute.
     *
     * @param id the id of the partyAttribute to save.
     * @param partyAttribute the partyAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyAttribute,
     * or with status {@code 400 (Bad Request)} if the partyAttribute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-attributes/{id}")
    public ResponseEntity<PartyAttribute> updatePartyAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyAttribute partyAttribute
    ) throws URISyntaxException {
        log.debug("REST request to update PartyAttribute : {}, {}", id, partyAttribute);
        if (partyAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PartyAttribute result = partyAttributeRepository.save(partyAttribute);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyAttribute.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /party-attributes/:id} : Partial updates given fields of an existing partyAttribute, field will ignore if it is null
     *
     * @param id the id of the partyAttribute to save.
     * @param partyAttribute the partyAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyAttribute,
     * or with status {@code 400 (Bad Request)} if the partyAttribute is not valid,
     * or with status {@code 404 (Not Found)} if the partyAttribute is not found,
     * or with status {@code 500 (Internal Server Error)} if the partyAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/party-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartyAttribute> partialUpdatePartyAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyAttribute partyAttribute
    ) throws URISyntaxException {
        log.debug("REST request to partial update PartyAttribute partially : {}, {}", id, partyAttribute);
        if (partyAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartyAttribute> result = partyAttributeRepository
            .findById(partyAttribute.getId())
            .map(existingPartyAttribute -> {
                if (partyAttribute.getgUID() != null) {
                    existingPartyAttribute.setgUID(partyAttribute.getgUID());
                }
                if (partyAttribute.getAttributeName() != null) {
                    existingPartyAttribute.setAttributeName(partyAttribute.getAttributeName());
                }
                if (partyAttribute.getAttributeValue() != null) {
                    existingPartyAttribute.setAttributeValue(partyAttribute.getAttributeValue());
                }
                if (partyAttribute.getCreatedBy() != null) {
                    existingPartyAttribute.setCreatedBy(partyAttribute.getCreatedBy());
                }
                if (partyAttribute.getCreatedOn() != null) {
                    existingPartyAttribute.setCreatedOn(partyAttribute.getCreatedOn());
                }
                if (partyAttribute.getUpdatedBy() != null) {
                    existingPartyAttribute.setUpdatedBy(partyAttribute.getUpdatedBy());
                }
                if (partyAttribute.getUpdatedOn() != null) {
                    existingPartyAttribute.setUpdatedOn(partyAttribute.getUpdatedOn());
                }

                return existingPartyAttribute;
            })
            .map(partyAttributeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyAttribute.getId().toString())
        );
    }

    /**
     * {@code GET  /party-attributes} : get all the partyAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyAttributes in body.
     */
    @GetMapping("/party-attributes")
    public List<PartyAttribute> getAllPartyAttributes() {
        log.debug("REST request to get all PartyAttributes");
        return partyAttributeRepository.findAll();
    }

    /**
     * {@code GET  /party-attributes/:id} : get the "id" partyAttribute.
     *
     * @param id the id of the partyAttribute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyAttribute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-attributes/{id}")
    public ResponseEntity<PartyAttribute> getPartyAttribute(@PathVariable Long id) {
        log.debug("REST request to get PartyAttribute : {}", id);
        Optional<PartyAttribute> partyAttribute = partyAttributeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyAttribute);
    }

    /**
     * {@code DELETE  /party-attributes/:id} : delete the "id" partyAttribute.
     *
     * @param id the id of the partyAttribute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-attributes/{id}")
    public ResponseEntity<Void> deletePartyAttribute(@PathVariable Long id) {
        log.debug("REST request to delete PartyAttribute : {}", id);
        partyAttributeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
