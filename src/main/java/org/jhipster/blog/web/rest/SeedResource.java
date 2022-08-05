package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Seed;
import org.jhipster.blog.repository.SeedRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Seed}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SeedResource {

    private final Logger log = LoggerFactory.getLogger(SeedResource.class);

    private static final String ENTITY_NAME = "seed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeedRepository seedRepository;

    public SeedResource(SeedRepository seedRepository) {
        this.seedRepository = seedRepository;
    }

    /**
     * {@code POST  /seeds} : Create a new seed.
     *
     * @param seed the seed to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seed, or with status {@code 400 (Bad Request)} if the seed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seeds")
    public ResponseEntity<Seed> createSeed(@RequestBody Seed seed) throws URISyntaxException {
        log.debug("REST request to save Seed : {}", seed);
        if (seed.getId() != null) {
            throw new BadRequestAlertException("A new seed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Seed result = seedRepository.save(seed);
        return ResponseEntity
            .created(new URI("/api/seeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seeds/:id} : Updates an existing seed.
     *
     * @param id the id of the seed to save.
     * @param seed the seed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seed,
     * or with status {@code 400 (Bad Request)} if the seed is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seeds/{id}")
    public ResponseEntity<Seed> updateSeed(@PathVariable(value = "id", required = false) final Long id, @RequestBody Seed seed)
        throws URISyntaxException {
        log.debug("REST request to update Seed : {}, {}", id, seed);
        if (seed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seed.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Seed result = seedRepository.save(seed);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seed.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /seeds/:id} : Partial updates given fields of an existing seed, field will ignore if it is null
     *
     * @param id the id of the seed to save.
     * @param seed the seed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seed,
     * or with status {@code 400 (Bad Request)} if the seed is not valid,
     * or with status {@code 404 (Not Found)} if the seed is not found,
     * or with status {@code 500 (Internal Server Error)} if the seed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/seeds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Seed> partialUpdateSeed(@PathVariable(value = "id", required = false) final Long id, @RequestBody Seed seed)
        throws URISyntaxException {
        log.debug("REST request to partial update Seed partially : {}, {}", id, seed);
        if (seed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seed.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Seed> result = seedRepository
            .findById(seed.getId())
            .map(existingSeed -> {
                if (seed.getgUID() != null) {
                    existingSeed.setgUID(seed.getgUID());
                }
                if (seed.getBreederID() != null) {
                    existingSeed.setBreederID(seed.getBreederID());
                }
                if (seed.getSeedClass() != null) {
                    existingSeed.setSeedClass(seed.getSeedClass());
                }
                if (seed.getVariety() != null) {
                    existingSeed.setVariety(seed.getVariety());
                }
                if (seed.getSeedRate() != null) {
                    existingSeed.setSeedRate(seed.getSeedRate());
                }
                if (seed.getGerminationPercentage() != null) {
                    existingSeed.setGerminationPercentage(seed.getGerminationPercentage());
                }
                if (seed.getTreatment() != null) {
                    existingSeed.setTreatment(seed.getTreatment());
                }
                if (seed.getOrigin() != null) {
                    existingSeed.setOrigin(seed.getOrigin());
                }
                if (seed.getCreatedBy() != null) {
                    existingSeed.setCreatedBy(seed.getCreatedBy());
                }
                if (seed.getCreatedOn() != null) {
                    existingSeed.setCreatedOn(seed.getCreatedOn());
                }
                if (seed.getUpdatedBy() != null) {
                    existingSeed.setUpdatedBy(seed.getUpdatedBy());
                }
                if (seed.getUpdatedOn() != null) {
                    existingSeed.setUpdatedOn(seed.getUpdatedOn());
                }

                return existingSeed;
            })
            .map(seedRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seed.getId().toString())
        );
    }

    /**
     * {@code GET  /seeds} : get all the seeds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seeds in body.
     */
    @GetMapping("/seeds")
    public List<Seed> getAllSeeds() {
        log.debug("REST request to get all Seeds");
        return seedRepository.findAll();
    }

    /**
     * {@code GET  /seeds/:id} : get the "id" seed.
     *
     * @param id the id of the seed to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seed, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seeds/{id}")
    public ResponseEntity<Seed> getSeed(@PathVariable Long id) {
        log.debug("REST request to get Seed : {}", id);
        Optional<Seed> seed = seedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(seed);
    }

    /**
     * {@code DELETE  /seeds/:id} : delete the "id" seed.
     *
     * @param id the id of the seed to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seeds/{id}")
    public ResponseEntity<Void> deleteSeed(@PathVariable Long id) {
        log.debug("REST request to delete Seed : {}", id);
        seedRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
