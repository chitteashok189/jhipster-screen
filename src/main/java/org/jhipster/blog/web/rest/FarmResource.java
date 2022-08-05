package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Farm;
import org.jhipster.blog.repository.FarmRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Farm}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FarmResource {

    private final Logger log = LoggerFactory.getLogger(FarmResource.class);

    private static final String ENTITY_NAME = "farm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FarmRepository farmRepository;

    public FarmResource(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    /**
     * {@code POST  /farms} : Create a new farm.
     *
     * @param farm the farm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new farm, or with status {@code 400 (Bad Request)} if the farm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/farms")
    public ResponseEntity<Farm> createFarm(@RequestBody Farm farm) throws URISyntaxException {
        log.debug("REST request to save Farm : {}", farm);
        if (farm.getId() != null) {
            throw new BadRequestAlertException("A new farm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Farm result = farmRepository.save(farm);
        return ResponseEntity
            .created(new URI("/api/farms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /farms/:id} : Updates an existing farm.
     *
     * @param id the id of the farm to save.
     * @param farm the farm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated farm,
     * or with status {@code 400 (Bad Request)} if the farm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the farm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/farms/{id}")
    public ResponseEntity<Farm> updateFarm(@PathVariable(value = "id", required = false) final Long id, @RequestBody Farm farm)
        throws URISyntaxException {
        log.debug("REST request to update Farm : {}, {}", id, farm);
        if (farm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, farm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!farmRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Farm result = farmRepository.save(farm);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, farm.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /farms/:id} : Partial updates given fields of an existing farm, field will ignore if it is null
     *
     * @param id the id of the farm to save.
     * @param farm the farm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated farm,
     * or with status {@code 400 (Bad Request)} if the farm is not valid,
     * or with status {@code 404 (Not Found)} if the farm is not found,
     * or with status {@code 500 (Internal Server Error)} if the farm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/farms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Farm> partialUpdateFarm(@PathVariable(value = "id", required = false) final Long id, @RequestBody Farm farm)
        throws URISyntaxException {
        log.debug("REST request to partial update Farm partially : {}, {}", id, farm);
        if (farm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, farm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!farmRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Farm> result = farmRepository
            .findById(farm.getId())
            .map(existingFarm -> {
                if (farm.getgUID() != null) {
                    existingFarm.setgUID(farm.getgUID());
                }
                if (farm.getFarmName() != null) {
                    existingFarm.setFarmName(farm.getFarmName());
                }
                if (farm.getFarmType() != null) {
                    existingFarm.setFarmType(farm.getFarmType());
                }
                if (farm.getFarmDescription() != null) {
                    existingFarm.setFarmDescription(farm.getFarmDescription());
                }
                if (farm.getCreatedBy() != null) {
                    existingFarm.setCreatedBy(farm.getCreatedBy());
                }
                if (farm.getCreatedOn() != null) {
                    existingFarm.setCreatedOn(farm.getCreatedOn());
                }
                if (farm.getUpdatedBy() != null) {
                    existingFarm.setUpdatedBy(farm.getUpdatedBy());
                }
                if (farm.getUpdatedOn() != null) {
                    existingFarm.setUpdatedOn(farm.getUpdatedOn());
                }

                return existingFarm;
            })
            .map(farmRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, farm.getId().toString())
        );
    }

    /**
     * {@code GET  /farms} : get all the farms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of farms in body.
     */
    @GetMapping("/farms")
    public List<Farm> getAllFarms() {
        log.debug("REST request to get all Farms");
        return farmRepository.findAll();
    }

    /**
     * {@code GET  /farms/:id} : get the "id" farm.
     *
     * @param id the id of the farm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the farm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/farms/{id}")
    public ResponseEntity<Farm> getFarm(@PathVariable Long id) {
        log.debug("REST request to get Farm : {}", id);
        Optional<Farm> farm = farmRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(farm);
    }

    /**
     * {@code DELETE  /farms/:id} : delete the "id" farm.
     *
     * @param id the id of the farm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/farms/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable Long id) {
        log.debug("REST request to delete Farm : {}", id);
        farmRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
