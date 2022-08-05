package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PartyNote;
import org.jhipster.blog.repository.PartyNoteRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PartyNote}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyNoteResource {

    private final Logger log = LoggerFactory.getLogger(PartyNoteResource.class);

    private static final String ENTITY_NAME = "partyNote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyNoteRepository partyNoteRepository;

    public PartyNoteResource(PartyNoteRepository partyNoteRepository) {
        this.partyNoteRepository = partyNoteRepository;
    }

    /**
     * {@code POST  /party-notes} : Create a new partyNote.
     *
     * @param partyNote the partyNote to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyNote, or with status {@code 400 (Bad Request)} if the partyNote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-notes")
    public ResponseEntity<PartyNote> createPartyNote(@RequestBody PartyNote partyNote) throws URISyntaxException {
        log.debug("REST request to save PartyNote : {}", partyNote);
        if (partyNote.getId() != null) {
            throw new BadRequestAlertException("A new partyNote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyNote result = partyNoteRepository.save(partyNote);
        return ResponseEntity
            .created(new URI("/api/party-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-notes/:id} : Updates an existing partyNote.
     *
     * @param id the id of the partyNote to save.
     * @param partyNote the partyNote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyNote,
     * or with status {@code 400 (Bad Request)} if the partyNote is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyNote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-notes/{id}")
    public ResponseEntity<PartyNote> updatePartyNote(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyNote partyNote
    ) throws URISyntaxException {
        log.debug("REST request to update PartyNote : {}, {}", id, partyNote);
        if (partyNote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyNote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyNoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PartyNote result = partyNoteRepository.save(partyNote);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyNote.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /party-notes/:id} : Partial updates given fields of an existing partyNote, field will ignore if it is null
     *
     * @param id the id of the partyNote to save.
     * @param partyNote the partyNote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyNote,
     * or with status {@code 400 (Bad Request)} if the partyNote is not valid,
     * or with status {@code 404 (Not Found)} if the partyNote is not found,
     * or with status {@code 500 (Internal Server Error)} if the partyNote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/party-notes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartyNote> partialUpdatePartyNote(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartyNote partyNote
    ) throws URISyntaxException {
        log.debug("REST request to partial update PartyNote partially : {}, {}", id, partyNote);
        if (partyNote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partyNote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partyNoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartyNote> result = partyNoteRepository
            .findById(partyNote.getId())
            .map(existingPartyNote -> {
                if (partyNote.getgUID() != null) {
                    existingPartyNote.setgUID(partyNote.getgUID());
                }
                if (partyNote.getNoteID() != null) {
                    existingPartyNote.setNoteID(partyNote.getNoteID());
                }
                if (partyNote.getCreatedBy() != null) {
                    existingPartyNote.setCreatedBy(partyNote.getCreatedBy());
                }
                if (partyNote.getCreatedOn() != null) {
                    existingPartyNote.setCreatedOn(partyNote.getCreatedOn());
                }
                if (partyNote.getUpdatedBy() != null) {
                    existingPartyNote.setUpdatedBy(partyNote.getUpdatedBy());
                }
                if (partyNote.getUpdatedOn() != null) {
                    existingPartyNote.setUpdatedOn(partyNote.getUpdatedOn());
                }

                return existingPartyNote;
            })
            .map(partyNoteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyNote.getId().toString())
        );
    }

    /**
     * {@code GET  /party-notes} : get all the partyNotes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyNotes in body.
     */
    @GetMapping("/party-notes")
    public List<PartyNote> getAllPartyNotes() {
        log.debug("REST request to get all PartyNotes");
        return partyNoteRepository.findAll();
    }

    /**
     * {@code GET  /party-notes/:id} : get the "id" partyNote.
     *
     * @param id the id of the partyNote to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyNote, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-notes/{id}")
    public ResponseEntity<PartyNote> getPartyNote(@PathVariable Long id) {
        log.debug("REST request to get PartyNote : {}", id);
        Optional<PartyNote> partyNote = partyNoteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyNote);
    }

    /**
     * {@code DELETE  /party-notes/:id} : delete the "id" partyNote.
     *
     * @param id the id of the partyNote to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-notes/{id}")
    public ResponseEntity<Void> deletePartyNote(@PathVariable Long id) {
        log.debug("REST request to delete PartyNote : {}", id);
        partyNoteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
