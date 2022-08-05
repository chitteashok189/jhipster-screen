package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Breeder;
import org.jhipster.blog.repository.BreederRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Breeder}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BreederResource {

    private final Logger log = LoggerFactory.getLogger(BreederResource.class);

    private static final String ENTITY_NAME = "breeder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BreederRepository breederRepository;

    public BreederResource(BreederRepository breederRepository) {
        this.breederRepository = breederRepository;
    }

    /**
     * {@code POST  /breeders} : Create a new breeder.
     *
     * @param breeder the breeder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new breeder, or with status {@code 400 (Bad Request)} if the breeder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/breeders")
    public ResponseEntity<Breeder> createBreeder(@RequestBody Breeder breeder) throws URISyntaxException {
        log.debug("REST request to save Breeder : {}", breeder);
        if (breeder.getId() != null) {
            throw new BadRequestAlertException("A new breeder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Breeder result = breederRepository.save(breeder);
        return ResponseEntity
            .created(new URI("/api/breeders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /breeders/:id} : Updates an existing breeder.
     *
     * @param id the id of the breeder to save.
     * @param breeder the breeder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated breeder,
     * or with status {@code 400 (Bad Request)} if the breeder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the breeder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/breeders/{id}")
    public ResponseEntity<Breeder> updateBreeder(@PathVariable(value = "id", required = false) final Long id, @RequestBody Breeder breeder)
        throws URISyntaxException {
        log.debug("REST request to update Breeder : {}, {}", id, breeder);
        if (breeder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, breeder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!breederRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Breeder result = breederRepository.save(breeder);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, breeder.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /breeders/:id} : Partial updates given fields of an existing breeder, field will ignore if it is null
     *
     * @param id the id of the breeder to save.
     * @param breeder the breeder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated breeder,
     * or with status {@code 400 (Bad Request)} if the breeder is not valid,
     * or with status {@code 404 (Not Found)} if the breeder is not found,
     * or with status {@code 500 (Internal Server Error)} if the breeder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/breeders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Breeder> partialUpdateBreeder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Breeder breeder
    ) throws URISyntaxException {
        log.debug("REST request to partial update Breeder partially : {}, {}", id, breeder);
        if (breeder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, breeder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!breederRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Breeder> result = breederRepository
            .findById(breeder.getId())
            .map(existingBreeder -> {
                if (breeder.getgUID() != null) {
                    existingBreeder.setgUID(breeder.getgUID());
                }
                if (breeder.getBreederName() != null) {
                    existingBreeder.setBreederName(breeder.getBreederName());
                }
                if (breeder.getBreederType() != null) {
                    existingBreeder.setBreederType(breeder.getBreederType());
                }
                if (breeder.getBreederStatus() != null) {
                    existingBreeder.setBreederStatus(breeder.getBreederStatus());
                }
                if (breeder.getBreederDescription() != null) {
                    existingBreeder.setBreederDescription(breeder.getBreederDescription());
                }
                if (breeder.getCreatedBy() != null) {
                    existingBreeder.setCreatedBy(breeder.getCreatedBy());
                }
                if (breeder.getCreatedOn() != null) {
                    existingBreeder.setCreatedOn(breeder.getCreatedOn());
                }
                if (breeder.getUpdatedBy() != null) {
                    existingBreeder.setUpdatedBy(breeder.getUpdatedBy());
                }
                if (breeder.getUpdatedOn() != null) {
                    existingBreeder.setUpdatedOn(breeder.getUpdatedOn());
                }

                return existingBreeder;
            })
            .map(breederRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, breeder.getId().toString())
        );
    }

    /**
     * {@code GET  /breeders} : get all the breeders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of breeders in body.
     */
    @GetMapping("/breeders")
    public List<Breeder> getAllBreeders() {
        log.debug("REST request to get all Breeders");
        return breederRepository.findAll();
    }

    /**
     * {@code GET  /breeders/:id} : get the "id" breeder.
     *
     * @param id the id of the breeder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the breeder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/breeders/{id}")
    public ResponseEntity<Breeder> getBreeder(@PathVariable Long id) {
        log.debug("REST request to get Breeder : {}", id);
        Optional<Breeder> breeder = breederRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(breeder);
    }

    /**
     * {@code DELETE  /breeders/:id} : delete the "id" breeder.
     *
     * @param id the id of the breeder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/breeders/{id}")
    public ResponseEntity<Void> deleteBreeder(@PathVariable Long id) {
        log.debug("REST request to delete Breeder : {}", id);
        breederRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
