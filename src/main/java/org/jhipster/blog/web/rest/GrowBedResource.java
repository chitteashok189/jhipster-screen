package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.GrowBed;
import org.jhipster.blog.repository.GrowBedRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.GrowBed}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GrowBedResource {

    private final Logger log = LoggerFactory.getLogger(GrowBedResource.class);

    private static final String ENTITY_NAME = "growBed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrowBedRepository growBedRepository;

    public GrowBedResource(GrowBedRepository growBedRepository) {
        this.growBedRepository = growBedRepository;
    }

    /**
     * {@code POST  /grow-beds} : Create a new growBed.
     *
     * @param growBed the growBed to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new growBed, or with status {@code 400 (Bad Request)} if the growBed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grow-beds")
    public ResponseEntity<GrowBed> createGrowBed(@RequestBody GrowBed growBed) throws URISyntaxException {
        log.debug("REST request to save GrowBed : {}", growBed);
        if (growBed.getId() != null) {
            throw new BadRequestAlertException("A new growBed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrowBed result = growBedRepository.save(growBed);
        return ResponseEntity
            .created(new URI("/api/grow-beds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grow-beds/:id} : Updates an existing growBed.
     *
     * @param id the id of the growBed to save.
     * @param growBed the growBed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated growBed,
     * or with status {@code 400 (Bad Request)} if the growBed is not valid,
     * or with status {@code 500 (Internal Server Error)} if the growBed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grow-beds/{id}")
    public ResponseEntity<GrowBed> updateGrowBed(@PathVariable(value = "id", required = false) final Long id, @RequestBody GrowBed growBed)
        throws URISyntaxException {
        log.debug("REST request to update GrowBed : {}, {}", id, growBed);
        if (growBed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, growBed.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!growBedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GrowBed result = growBedRepository.save(growBed);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, growBed.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /grow-beds/:id} : Partial updates given fields of an existing growBed, field will ignore if it is null
     *
     * @param id the id of the growBed to save.
     * @param growBed the growBed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated growBed,
     * or with status {@code 400 (Bad Request)} if the growBed is not valid,
     * or with status {@code 404 (Not Found)} if the growBed is not found,
     * or with status {@code 500 (Internal Server Error)} if the growBed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/grow-beds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GrowBed> partialUpdateGrowBed(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GrowBed growBed
    ) throws URISyntaxException {
        log.debug("REST request to partial update GrowBed partially : {}, {}", id, growBed);
        if (growBed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, growBed.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!growBedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GrowBed> result = growBedRepository
            .findById(growBed.getId())
            .map(existingGrowBed -> {
                if (growBed.getgUID() != null) {
                    existingGrowBed.setgUID(growBed.getgUID());
                }
                if (growBed.getGrowBedType() != null) {
                    existingGrowBed.setGrowBedType(growBed.getGrowBedType());
                }
                if (growBed.getGrowBedName() != null) {
                    existingGrowBed.setGrowBedName(growBed.getGrowBedName());
                }
                if (growBed.getManufacturer() != null) {
                    existingGrowBed.setManufacturer(growBed.getManufacturer());
                }
                if (growBed.getCreatedBy() != null) {
                    existingGrowBed.setCreatedBy(growBed.getCreatedBy());
                }
                if (growBed.getCreatedOn() != null) {
                    existingGrowBed.setCreatedOn(growBed.getCreatedOn());
                }
                if (growBed.getUpdatedBy() != null) {
                    existingGrowBed.setUpdatedBy(growBed.getUpdatedBy());
                }
                if (growBed.getUpdatedOn() != null) {
                    existingGrowBed.setUpdatedOn(growBed.getUpdatedOn());
                }

                return existingGrowBed;
            })
            .map(growBedRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, growBed.getId().toString())
        );
    }

    /**
     * {@code GET  /grow-beds} : get all the growBeds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of growBeds in body.
     */
    @GetMapping("/grow-beds")
    public List<GrowBed> getAllGrowBeds() {
        log.debug("REST request to get all GrowBeds");
        return growBedRepository.findAll();
    }

    /**
     * {@code GET  /grow-beds/:id} : get the "id" growBed.
     *
     * @param id the id of the growBed to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the growBed, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grow-beds/{id}")
    public ResponseEntity<GrowBed> getGrowBed(@PathVariable Long id) {
        log.debug("REST request to get GrowBed : {}", id);
        Optional<GrowBed> growBed = growBedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(growBed);
    }

    /**
     * {@code DELETE  /grow-beds/:id} : delete the "id" growBed.
     *
     * @param id the id of the growBed to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grow-beds/{id}")
    public ResponseEntity<Void> deleteGrowBed(@PathVariable Long id) {
        log.debug("REST request to delete GrowBed : {}", id);
        growBedRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
