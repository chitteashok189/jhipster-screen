package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PartyStatusItem;
import org.jhipster.blog.repository.PartyStatusItemRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PartyStatusItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyStatusItemResource {

    private final Logger log = LoggerFactory.getLogger(PartyStatusItemResource.class);

    private static final String ENTITY_NAME = "partyStatusItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyStatusItemRepository partyStatusItemRepository;

    public PartyStatusItemResource(PartyStatusItemRepository partyStatusItemRepository) {
        this.partyStatusItemRepository = partyStatusItemRepository;
    }

    /**
     * {@code POST  /party-status-items} : Create a new partyStatusItem.
     *
     * @param partyStatusItem the partyStatusItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyStatusItem, or with status {@code 400 (Bad Request)} if the partyStatusItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-status-items")
    public ResponseEntity<PartyStatusItem> createPartyStatusItem(@RequestBody PartyStatusItem partyStatusItem) throws URISyntaxException {
        log.debug("REST request to save PartyStatusItem : {}", partyStatusItem);
        if (partyStatusItem.getId() != null) {
            throw new BadRequestAlertException("A new partyStatusItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyStatusItem result = partyStatusItemRepository.save(partyStatusItem);
        return ResponseEntity
            .created(new URI("/api/party-status-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-status-items/:id} : Updates an existing partyStatusItem.
     *
     * @param id the id of the partyStatusItem to save.
     * @param partyStatusItem the partyStatusItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyStatusItem,
     * or with status {@code 400 (Bad Request)} if the partyStatusItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyStatusItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-status-items/{id}")
    public ResponseEntity<PartyStatusItem> updatePartyStatusItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyStatusItem partyStatusItem
    ) throws URISyntaxException {
        log.debug("REST request to update PartyStatusItem : {}, {}", id, partyStatusItem);
        if (partyStatusItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyStatusItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyStatusItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PartyStatusItem result = partyStatusItemRepository.save(partyStatusItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyStatusItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /party-status-items/:id} : Partial updates given fields of an existing partyStatusItem, field will ignore if it is null
     *
     * @param id the id of the partyStatusItem to save.
     * @param partyStatusItem the partyStatusItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyStatusItem,
     * or with status {@code 400 (Bad Request)} if the partyStatusItem is not valid,
     * or with status {@code 404 (Not Found)} if the partyStatusItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the partyStatusItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/party-status-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartyStatusItem> partialUpdatePartyStatusItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyStatusItem partyStatusItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update PartyStatusItem partially : {}, {}", id, partyStatusItem);
        if (partyStatusItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyStatusItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyStatusItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartyStatusItem> result = partyStatusItemRepository
            .findById(partyStatusItem.getId())
            .map(existingPartyStatusItem -> {
                if (partyStatusItem.getgUID() != null) {
                    existingPartyStatusItem.setgUID(partyStatusItem.getgUID());
                }
                if (partyStatusItem.getStatusCode() != null) {
                    existingPartyStatusItem.setStatusCode(partyStatusItem.getStatusCode());
                }
                if (partyStatusItem.getSequenceID() != null) {
                    existingPartyStatusItem.setSequenceID(partyStatusItem.getSequenceID());
                }
                if (partyStatusItem.getDescription() != null) {
                    existingPartyStatusItem.setDescription(partyStatusItem.getDescription());
                }
                if (partyStatusItem.getCreatedBy() != null) {
                    existingPartyStatusItem.setCreatedBy(partyStatusItem.getCreatedBy());
                }
                if (partyStatusItem.getCreatedOn() != null) {
                    existingPartyStatusItem.setCreatedOn(partyStatusItem.getCreatedOn());
                }
                if (partyStatusItem.getUpdatedBy() != null) {
                    existingPartyStatusItem.setUpdatedBy(partyStatusItem.getUpdatedBy());
                }
                if (partyStatusItem.getUpdatedOn() != null) {
                    existingPartyStatusItem.setUpdatedOn(partyStatusItem.getUpdatedOn());
                }

                return existingPartyStatusItem;
            })
            .map(partyStatusItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyStatusItem.getId().toString())
        );
    }

    /**
     * {@code GET  /party-status-items} : get all the partyStatusItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyStatusItems in body.
     */
    @GetMapping("/party-status-items")
    public List<PartyStatusItem> getAllPartyStatusItems() {
        log.debug("REST request to get all PartyStatusItems");
        return partyStatusItemRepository.findAll();
    }

    /**
     * {@code GET  /party-status-items/:id} : get the "id" partyStatusItem.
     *
     * @param id the id of the partyStatusItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyStatusItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-status-items/{id}")
    public ResponseEntity<PartyStatusItem> getPartyStatusItem(@PathVariable Long id) {
        log.debug("REST request to get PartyStatusItem : {}", id);
        Optional<PartyStatusItem> partyStatusItem = partyStatusItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyStatusItem);
    }

    /**
     * {@code DELETE  /party-status-items/:id} : delete the "id" partyStatusItem.
     *
     * @param id the id of the partyStatusItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-status-items/{id}")
    public ResponseEntity<Void> deletePartyStatusItem(@PathVariable Long id) {
        log.debug("REST request to delete PartyStatusItem : {}", id);
        partyStatusItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
