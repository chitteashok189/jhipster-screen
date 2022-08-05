package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PartyTypeAttribute;
import org.jhipster.blog.repository.PartyTypeAttributeRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PartyTypeAttribute}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyTypeAttributeResource {

    private final Logger log = LoggerFactory.getLogger(PartyTypeAttributeResource.class);

    private static final String ENTITY_NAME = "partyTypeAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyTypeAttributeRepository partyTypeAttributeRepository;

    public PartyTypeAttributeResource(PartyTypeAttributeRepository partyTypeAttributeRepository) {
        this.partyTypeAttributeRepository = partyTypeAttributeRepository;
    }

    /**
     * {@code POST  /party-type-attributes} : Create a new partyTypeAttribute.
     *
     * @param partyTypeAttribute the partyTypeAttribute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyTypeAttribute, or with status {@code 400 (Bad Request)} if the partyTypeAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-type-attributes")
    public ResponseEntity<PartyTypeAttribute> createPartyTypeAttribute(@RequestBody PartyTypeAttribute partyTypeAttribute)
        throws URISyntaxException {
        log.debug("REST request to save PartyTypeAttribute : {}", partyTypeAttribute);
        if (partyTypeAttribute.getId() != null) {
            throw new BadRequestAlertException("A new partyTypeAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyTypeAttribute result = partyTypeAttributeRepository.save(partyTypeAttribute);
        return ResponseEntity
            .created(new URI("/api/party-type-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-type-attributes/:id} : Updates an existing partyTypeAttribute.
     *
     * @param id the id of the partyTypeAttribute to save.
     * @param partyTypeAttribute the partyTypeAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyTypeAttribute,
     * or with status {@code 400 (Bad Request)} if the partyTypeAttribute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyTypeAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-type-attributes/{id}")
    public ResponseEntity<PartyTypeAttribute> updatePartyTypeAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyTypeAttribute partyTypeAttribute
    ) throws URISyntaxException {
        log.debug("REST request to update PartyTypeAttribute : {}, {}", id, partyTypeAttribute);
        if (partyTypeAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyTypeAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyTypeAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PartyTypeAttribute result = partyTypeAttributeRepository.save(partyTypeAttribute);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyTypeAttribute.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /party-type-attributes/:id} : Partial updates given fields of an existing partyTypeAttribute, field will ignore if it is null
     *
     * @param id the id of the partyTypeAttribute to save.
     * @param partyTypeAttribute the partyTypeAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyTypeAttribute,
     * or with status {@code 400 (Bad Request)} if the partyTypeAttribute is not valid,
     * or with status {@code 404 (Not Found)} if the partyTypeAttribute is not found,
     * or with status {@code 500 (Internal Server Error)} if the partyTypeAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/party-type-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartyTypeAttribute> partialUpdatePartyTypeAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyTypeAttribute partyTypeAttribute
    ) throws URISyntaxException {
        log.debug("REST request to partial update PartyTypeAttribute partially : {}, {}", id, partyTypeAttribute);
        if (partyTypeAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyTypeAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyTypeAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartyTypeAttribute> result = partyTypeAttributeRepository
            .findById(partyTypeAttribute.getId())
            .map(existingPartyTypeAttribute -> {
                if (partyTypeAttribute.getgUID() != null) {
                    existingPartyTypeAttribute.setgUID(partyTypeAttribute.getgUID());
                }
                if (partyTypeAttribute.getAttributeName() != null) {
                    existingPartyTypeAttribute.setAttributeName(partyTypeAttribute.getAttributeName());
                }
                if (partyTypeAttribute.getCreatedBy() != null) {
                    existingPartyTypeAttribute.setCreatedBy(partyTypeAttribute.getCreatedBy());
                }
                if (partyTypeAttribute.getCreatedOn() != null) {
                    existingPartyTypeAttribute.setCreatedOn(partyTypeAttribute.getCreatedOn());
                }
                if (partyTypeAttribute.getUpdatedBy() != null) {
                    existingPartyTypeAttribute.setUpdatedBy(partyTypeAttribute.getUpdatedBy());
                }
                if (partyTypeAttribute.getUpdatedOn() != null) {
                    existingPartyTypeAttribute.setUpdatedOn(partyTypeAttribute.getUpdatedOn());
                }

                return existingPartyTypeAttribute;
            })
            .map(partyTypeAttributeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyTypeAttribute.getId().toString())
        );
    }

    /**
     * {@code GET  /party-type-attributes} : get all the partyTypeAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyTypeAttributes in body.
     */
    @GetMapping("/party-type-attributes")
    public List<PartyTypeAttribute> getAllPartyTypeAttributes() {
        log.debug("REST request to get all PartyTypeAttributes");
        return partyTypeAttributeRepository.findAll();
    }

    /**
     * {@code GET  /party-type-attributes/:id} : get the "id" partyTypeAttribute.
     *
     * @param id the id of the partyTypeAttribute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyTypeAttribute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-type-attributes/{id}")
    public ResponseEntity<PartyTypeAttribute> getPartyTypeAttribute(@PathVariable Long id) {
        log.debug("REST request to get PartyTypeAttribute : {}", id);
        Optional<PartyTypeAttribute> partyTypeAttribute = partyTypeAttributeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyTypeAttribute);
    }

    /**
     * {@code DELETE  /party-type-attributes/:id} : delete the "id" partyTypeAttribute.
     *
     * @param id the id of the partyTypeAttribute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-type-attributes/{id}")
    public ResponseEntity<Void> deletePartyTypeAttribute(@PathVariable Long id) {
        log.debug("REST request to delete PartyTypeAttribute : {}", id);
        partyTypeAttributeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
