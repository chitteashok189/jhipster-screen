package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Party;
import org.jhipster.blog.repository.PartyRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Party}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyResource {

    private final Logger log = LoggerFactory.getLogger(PartyResource.class);

    private static final String ENTITY_NAME = "party";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyRepository partyRepository;

    public PartyResource(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    /**
     * {@code POST  /parties} : Create a new party.
     *
     * @param party the party to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new party, or with status {@code 400 (Bad Request)} if the party has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parties")
    public ResponseEntity<Party> createParty(@RequestBody Party party) throws URISyntaxException {
        log.debug("REST request to save Party : {}", party);
        if (party.getId() != null) {
            throw new BadRequestAlertException("A new party cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Party result = partyRepository.save(party);
        return ResponseEntity
            .created(new URI("/api/parties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parties/:id} : Updates an existing party.
     *
     * @param id the id of the party to save.
     * @param party the party to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated party,
     * or with status {@code 400 (Bad Request)} if the party is not valid,
     * or with status {@code 500 (Internal Server Error)} if the party couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parties/{id}")
    public ResponseEntity<Party> updateParty(@PathVariable(value = "id", required = false) final Long id, @RequestBody Party party)
        throws URISyntaxException {
        log.debug("REST request to update Party : {}, {}", id, party);
        if (party.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, party.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Party result = partyRepository.save(party);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, party.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parties/:id} : Partial updates given fields of an existing party, field will ignore if it is null
     *
     * @param id the id of the party to save.
     * @param party the party to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated party,
     * or with status {@code 400 (Bad Request)} if the party is not valid,
     * or with status {@code 404 (Not Found)} if the party is not found,
     * or with status {@code 500 (Internal Server Error)} if the party couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parties/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Party> partialUpdateParty(@PathVariable(value = "id", required = false) final Long id, @RequestBody Party party)
        throws URISyntaxException {
        log.debug("REST request to partial update Party partially : {}, {}", id, party);
        if (party.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, party.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Party> result = partyRepository
            .findById(party.getId())
            .map(existingParty -> {
                if (party.getgUID() != null) {
                    existingParty.setgUID(party.getgUID());
                }
                if (party.getPartyName() != null) {
                    existingParty.setPartyName(party.getPartyName());
                }
                if (party.getStatusID() != null) {
                    existingParty.setStatusID(party.getStatusID());
                }
                if (party.getDescription() != null) {
                    existingParty.setDescription(party.getDescription());
                }
                if (party.getExternalID() != null) {
                    existingParty.setExternalID(party.getExternalID());
                }
                if (party.getCreatedBy() != null) {
                    existingParty.setCreatedBy(party.getCreatedBy());
                }
                if (party.getCreatedOn() != null) {
                    existingParty.setCreatedOn(party.getCreatedOn());
                }
                if (party.getUpdatedBy() != null) {
                    existingParty.setUpdatedBy(party.getUpdatedBy());
                }
                if (party.getUpdatedOn() != null) {
                    existingParty.setUpdatedOn(party.getUpdatedOn());
                }

                return existingParty;
            })
            .map(partyRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, party.getId().toString())
        );
    }

    /**
     * {@code GET  /parties} : get all the parties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parties in body.
     */
    @GetMapping("/parties")
    public List<Party> getAllParties() {
        log.debug("REST request to get all Parties");
        return partyRepository.findAll();
    }

    /**
     * {@code GET  /parties/:id} : get the "id" party.
     *
     * @param id the id of the party to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the party, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parties/{id}")
    public ResponseEntity<Party> getParty(@PathVariable Long id) {
        log.debug("REST request to get Party : {}", id);
        Optional<Party> party = partyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(party);
    }

    /**
     * {@code DELETE  /parties/:id} : delete the "id" party.
     *
     * @param id the id of the party to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parties/{id}")
    public ResponseEntity<Void> deleteParty(@PathVariable Long id) {
        log.debug("REST request to delete Party : {}", id);
        partyRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
