package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Harvest;
import org.jhipster.blog.repository.HarvestRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Harvest}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HarvestResource {

    private final Logger log = LoggerFactory.getLogger(HarvestResource.class);

    private static final String ENTITY_NAME = "harvest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HarvestRepository harvestRepository;

    public HarvestResource(HarvestRepository harvestRepository) {
        this.harvestRepository = harvestRepository;
    }

    /**
     * {@code POST  /harvests} : Create a new harvest.
     *
     * @param harvest the harvest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new harvest, or with status {@code 400 (Bad Request)} if the harvest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/harvests")
    public ResponseEntity<Harvest> createHarvest(@RequestBody Harvest harvest) throws URISyntaxException {
        log.debug("REST request to save Harvest : {}", harvest);
        if (harvest.getId() != null) {
            throw new BadRequestAlertException("A new harvest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Harvest result = harvestRepository.save(harvest);
        return ResponseEntity
            .created(new URI("/api/harvests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /harvests/:id} : Updates an existing harvest.
     *
     * @param id the id of the harvest to save.
     * @param harvest the harvest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated harvest,
     * or with status {@code 400 (Bad Request)} if the harvest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the harvest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/harvests/{id}")
    public ResponseEntity<Harvest> updateHarvest(@PathVariable(value = "id", required = false) final Long id, @RequestBody Harvest harvest)
        throws URISyntaxException {
        log.debug("REST request to update Harvest : {}, {}", id, harvest);
        if (harvest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, harvest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!harvestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Harvest result = harvestRepository.save(harvest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, harvest.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /harvests/:id} : Partial updates given fields of an existing harvest, field will ignore if it is null
     *
     * @param id the id of the harvest to save.
     * @param harvest the harvest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated harvest,
     * or with status {@code 400 (Bad Request)} if the harvest is not valid,
     * or with status {@code 404 (Not Found)} if the harvest is not found,
     * or with status {@code 500 (Internal Server Error)} if the harvest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/harvests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Harvest> partialUpdateHarvest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Harvest harvest
    ) throws URISyntaxException {
        log.debug("REST request to partial update Harvest partially : {}, {}", id, harvest);
        if (harvest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, harvest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!harvestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Harvest> result = harvestRepository
            .findById(harvest.getId())
            .map(existingHarvest -> {
                if (harvest.getgUID() != null) {
                    existingHarvest.setgUID(harvest.getgUID());
                }
                if (harvest.getHarvestingDate() != null) {
                    existingHarvest.setHarvestingDate(harvest.getHarvestingDate());
                }
                if (harvest.getCreatedBy() != null) {
                    existingHarvest.setCreatedBy(harvest.getCreatedBy());
                }
                if (harvest.getCreatedOn() != null) {
                    existingHarvest.setCreatedOn(harvest.getCreatedOn());
                }
                if (harvest.getUpdatedBy() != null) {
                    existingHarvest.setUpdatedBy(harvest.getUpdatedBy());
                }
                if (harvest.getUpdatedOn() != null) {
                    existingHarvest.setUpdatedOn(harvest.getUpdatedOn());
                }

                return existingHarvest;
            })
            .map(harvestRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, harvest.getId().toString())
        );
    }

    /**
     * {@code GET  /harvests} : get all the harvests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of harvests in body.
     */
    @GetMapping("/harvests")
    public List<Harvest> getAllHarvests() {
        log.debug("REST request to get all Harvests");
        return harvestRepository.findAll();
    }

    /**
     * {@code GET  /harvests/:id} : get the "id" harvest.
     *
     * @param id the id of the harvest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the harvest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/harvests/{id}")
    public ResponseEntity<Harvest> getHarvest(@PathVariable Long id) {
        log.debug("REST request to get Harvest : {}", id);
        Optional<Harvest> harvest = harvestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(harvest);
    }

    /**
     * {@code DELETE  /harvests/:id} : delete the "id" harvest.
     *
     * @param id the id of the harvest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/harvests/{id}")
    public ResponseEntity<Void> deleteHarvest(@PathVariable Long id) {
        log.debug("REST request to delete Harvest : {}", id);
        harvestRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
