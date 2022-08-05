package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PlantFactory;
import org.jhipster.blog.repository.PlantFactoryRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PlantFactory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PlantFactoryResource {

    private final Logger log = LoggerFactory.getLogger(PlantFactoryResource.class);

    private static final String ENTITY_NAME = "plantFactory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlantFactoryRepository plantFactoryRepository;

    public PlantFactoryResource(PlantFactoryRepository plantFactoryRepository) {
        this.plantFactoryRepository = plantFactoryRepository;
    }

    /**
     * {@code POST  /plant-factories} : Create a new plantFactory.
     *
     * @param plantFactory the plantFactory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plantFactory, or with status {@code 400 (Bad Request)} if the plantFactory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plant-factories")
    public ResponseEntity<PlantFactory> createPlantFactory(@RequestBody PlantFactory plantFactory) throws URISyntaxException {
        log.debug("REST request to save PlantFactory : {}", plantFactory);
        if (plantFactory.getId() != null) {
            throw new BadRequestAlertException("A new plantFactory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlantFactory result = plantFactoryRepository.save(plantFactory);
        return ResponseEntity
            .created(new URI("/api/plant-factories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plant-factories/:id} : Updates an existing plantFactory.
     *
     * @param id the id of the plantFactory to save.
     * @param plantFactory the plantFactory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plantFactory,
     * or with status {@code 400 (Bad Request)} if the plantFactory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plantFactory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plant-factories/{id}")
    public ResponseEntity<PlantFactory> updatePlantFactory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlantFactory plantFactory
    ) throws URISyntaxException {
        log.debug("REST request to update PlantFactory : {}, {}", id, plantFactory);
        if (plantFactory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plantFactory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plantFactoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlantFactory result = plantFactoryRepository.save(plantFactory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plantFactory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plant-factories/:id} : Partial updates given fields of an existing plantFactory, field will ignore if it is null
     *
     * @param id the id of the plantFactory to save.
     * @param plantFactory the plantFactory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plantFactory,
     * or with status {@code 400 (Bad Request)} if the plantFactory is not valid,
     * or with status {@code 404 (Not Found)} if the plantFactory is not found,
     * or with status {@code 500 (Internal Server Error)} if the plantFactory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plant-factories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlantFactory> partialUpdatePlantFactory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlantFactory plantFactory
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlantFactory partially : {}, {}", id, plantFactory);
        if (plantFactory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plantFactory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plantFactoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlantFactory> result = plantFactoryRepository
            .findById(plantFactory.getId())
            .map(existingPlantFactory -> {
                if (plantFactory.getgUID() != null) {
                    existingPlantFactory.setgUID(plantFactory.getgUID());
                }
                if (plantFactory.getPlantFactoryName() != null) {
                    existingPlantFactory.setPlantFactoryName(plantFactory.getPlantFactoryName());
                }
                if (plantFactory.getPlantFactoryTypeID() != null) {
                    existingPlantFactory.setPlantFactoryTypeID(plantFactory.getPlantFactoryTypeID());
                }
                if (plantFactory.getPlantFactorySubType() != null) {
                    existingPlantFactory.setPlantFactorySubType(plantFactory.getPlantFactorySubType());
                }
                if (plantFactory.getPlantFactoryDescription() != null) {
                    existingPlantFactory.setPlantFactoryDescription(plantFactory.getPlantFactoryDescription());
                }
                if (plantFactory.getCreatedBy() != null) {
                    existingPlantFactory.setCreatedBy(plantFactory.getCreatedBy());
                }
                if (plantFactory.getCreatedOn() != null) {
                    existingPlantFactory.setCreatedOn(plantFactory.getCreatedOn());
                }
                if (plantFactory.getUpdatedBy() != null) {
                    existingPlantFactory.setUpdatedBy(plantFactory.getUpdatedBy());
                }
                if (plantFactory.getUpdatedOn() != null) {
                    existingPlantFactory.setUpdatedOn(plantFactory.getUpdatedOn());
                }

                return existingPlantFactory;
            })
            .map(plantFactoryRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plantFactory.getId().toString())
        );
    }

    /**
     * {@code GET  /plant-factories} : get all the plantFactories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plantFactories in body.
     */
    @GetMapping("/plant-factories")
    public List<PlantFactory> getAllPlantFactories() {
        log.debug("REST request to get all PlantFactories");
        return plantFactoryRepository.findAll();
    }

    /**
     * {@code GET  /plant-factories/:id} : get the "id" plantFactory.
     *
     * @param id the id of the plantFactory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plantFactory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plant-factories/{id}")
    public ResponseEntity<PlantFactory> getPlantFactory(@PathVariable Long id) {
        log.debug("REST request to get PlantFactory : {}", id);
        Optional<PlantFactory> plantFactory = plantFactoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(plantFactory);
    }

    /**
     * {@code DELETE  /plant-factories/:id} : delete the "id" plantFactory.
     *
     * @param id the id of the plantFactory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plant-factories/{id}")
    public ResponseEntity<Void> deletePlantFactory(@PathVariable Long id) {
        log.debug("REST request to delete PlantFactory : {}", id);
        plantFactoryRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
