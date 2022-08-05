package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.PlantFactoryController;
import org.jhipster.blog.repository.PlantFactoryControllerRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.PlantFactoryController}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PlantFactoryControllerResource {

    private final Logger log = LoggerFactory.getLogger(PlantFactoryControllerResource.class);

    private static final String ENTITY_NAME = "plantFactoryController";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlantFactoryControllerRepository plantFactoryControllerRepository;

    public PlantFactoryControllerResource(PlantFactoryControllerRepository plantFactoryControllerRepository) {
        this.plantFactoryControllerRepository = plantFactoryControllerRepository;
    }

    /**
     * {@code POST  /plant-factory-controllers} : Create a new plantFactoryController.
     *
     * @param plantFactoryController the plantFactoryController to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plantFactoryController, or with status {@code 400 (Bad Request)} if the plantFactoryController has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plant-factory-controllers")
    public ResponseEntity<PlantFactoryController> createPlantFactoryController(@RequestBody PlantFactoryController plantFactoryController)
        throws URISyntaxException {
        log.debug("REST request to save PlantFactoryController : {}", plantFactoryController);
        if (plantFactoryController.getId() != null) {
            throw new BadRequestAlertException("A new plantFactoryController cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlantFactoryController result = plantFactoryControllerRepository.save(plantFactoryController);
        return ResponseEntity
            .created(new URI("/api/plant-factory-controllers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plant-factory-controllers/:id} : Updates an existing plantFactoryController.
     *
     * @param id the id of the plantFactoryController to save.
     * @param plantFactoryController the plantFactoryController to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plantFactoryController,
     * or with status {@code 400 (Bad Request)} if the plantFactoryController is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plantFactoryController couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plant-factory-controllers/{id}")
    public ResponseEntity<PlantFactoryController> updatePlantFactoryController(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlantFactoryController plantFactoryController
    ) throws URISyntaxException {
        log.debug("REST request to update PlantFactoryController : {}, {}", id, plantFactoryController);
        if (plantFactoryController.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plantFactoryController.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plantFactoryControllerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlantFactoryController result = plantFactoryControllerRepository.save(plantFactoryController);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plantFactoryController.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plant-factory-controllers/:id} : Partial updates given fields of an existing plantFactoryController, field will ignore if it is null
     *
     * @param id the id of the plantFactoryController to save.
     * @param plantFactoryController the plantFactoryController to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plantFactoryController,
     * or with status {@code 400 (Bad Request)} if the plantFactoryController is not valid,
     * or with status {@code 404 (Not Found)} if the plantFactoryController is not found,
     * or with status {@code 500 (Internal Server Error)} if the plantFactoryController couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plant-factory-controllers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlantFactoryController> partialUpdatePlantFactoryController(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlantFactoryController plantFactoryController
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlantFactoryController partially : {}, {}", id, plantFactoryController);
        if (plantFactoryController.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plantFactoryController.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plantFactoryControllerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlantFactoryController> result = plantFactoryControllerRepository
            .findById(plantFactoryController.getId())
            .map(existingPlantFactoryController -> {
                if (plantFactoryController.getgUID() != null) {
                    existingPlantFactoryController.setgUID(plantFactoryController.getgUID());
                }
                if (plantFactoryController.getSource() != null) {
                    existingPlantFactoryController.setSource(plantFactoryController.getSource());
                }
                if (plantFactoryController.getAirTemperature() != null) {
                    existingPlantFactoryController.setAirTemperature(plantFactoryController.getAirTemperature());
                }
                if (plantFactoryController.getRelativeHumidity() != null) {
                    existingPlantFactoryController.setRelativeHumidity(plantFactoryController.getRelativeHumidity());
                }
                if (plantFactoryController.getvPD() != null) {
                    existingPlantFactoryController.setvPD(plantFactoryController.getvPD());
                }
                if (plantFactoryController.getEvapotranspiration() != null) {
                    existingPlantFactoryController.setEvapotranspiration(plantFactoryController.getEvapotranspiration());
                }
                if (plantFactoryController.getBarometricPressure() != null) {
                    existingPlantFactoryController.setBarometricPressure(plantFactoryController.getBarometricPressure());
                }
                if (plantFactoryController.getSeaLevelPressure() != null) {
                    existingPlantFactoryController.setSeaLevelPressure(plantFactoryController.getSeaLevelPressure());
                }
                if (plantFactoryController.getCo2Level() != null) {
                    existingPlantFactoryController.setCo2Level(plantFactoryController.getCo2Level());
                }
                if (plantFactoryController.getNutrientTankLevel() != null) {
                    existingPlantFactoryController.setNutrientTankLevel(plantFactoryController.getNutrientTankLevel());
                }
                if (plantFactoryController.getDewPoint() != null) {
                    existingPlantFactoryController.setDewPoint(plantFactoryController.getDewPoint());
                }
                if (plantFactoryController.getHeatIndex() != null) {
                    existingPlantFactoryController.setHeatIndex(plantFactoryController.getHeatIndex());
                }
                if (plantFactoryController.getTurbidity() != null) {
                    existingPlantFactoryController.setTurbidity(plantFactoryController.getTurbidity());
                }
                if (plantFactoryController.getCreatedBy() != null) {
                    existingPlantFactoryController.setCreatedBy(plantFactoryController.getCreatedBy());
                }
                if (plantFactoryController.getCreatedOn() != null) {
                    existingPlantFactoryController.setCreatedOn(plantFactoryController.getCreatedOn());
                }
                if (plantFactoryController.getUpdatedBy() != null) {
                    existingPlantFactoryController.setUpdatedBy(plantFactoryController.getUpdatedBy());
                }
                if (plantFactoryController.getUpdatedOn() != null) {
                    existingPlantFactoryController.setUpdatedOn(plantFactoryController.getUpdatedOn());
                }

                return existingPlantFactoryController;
            })
            .map(plantFactoryControllerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plantFactoryController.getId().toString())
        );
    }

    /**
     * {@code GET  /plant-factory-controllers} : get all the plantFactoryControllers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plantFactoryControllers in body.
     */
    @GetMapping("/plant-factory-controllers")
    public List<PlantFactoryController> getAllPlantFactoryControllers() {
        log.debug("REST request to get all PlantFactoryControllers");
        return plantFactoryControllerRepository.findAll();
    }

    /**
     * {@code GET  /plant-factory-controllers/:id} : get the "id" plantFactoryController.
     *
     * @param id the id of the plantFactoryController to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plantFactoryController, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plant-factory-controllers/{id}")
    public ResponseEntity<PlantFactoryController> getPlantFactoryController(@PathVariable Long id) {
        log.debug("REST request to get PlantFactoryController : {}", id);
        Optional<PlantFactoryController> plantFactoryController = plantFactoryControllerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(plantFactoryController);
    }

    /**
     * {@code DELETE  /plant-factory-controllers/:id} : delete the "id" plantFactoryController.
     *
     * @param id the id of the plantFactoryController to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plant-factory-controllers/{id}")
    public ResponseEntity<Void> deletePlantFactoryController(@PathVariable Long id) {
        log.debug("REST request to delete PlantFactoryController : {}", id);
        plantFactoryControllerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
