package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Plant;
import org.jhipster.blog.repository.PlantRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Plant}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PlantResource {

    private final Logger log = LoggerFactory.getLogger(PlantResource.class);

    private static final String ENTITY_NAME = "plant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlantRepository plantRepository;

    public PlantResource(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    /**
     * {@code POST  /plants} : Create a new plant.
     *
     * @param plant the plant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plant, or with status {@code 400 (Bad Request)} if the plant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plants")
    public ResponseEntity<Plant> createPlant(@RequestBody Plant plant) throws URISyntaxException {
        log.debug("REST request to save Plant : {}", plant);
        if (plant.getId() != null) {
            throw new BadRequestAlertException("A new plant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plant result = plantRepository.save(plant);
        return ResponseEntity
            .created(new URI("/api/plants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plants/:id} : Updates an existing plant.
     *
     * @param id the id of the plant to save.
     * @param plant the plant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plant,
     * or with status {@code 400 (Bad Request)} if the plant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plants/{id}")
    public ResponseEntity<Plant> updatePlant(@PathVariable(value = "id", required = false) final Long id, @RequestBody Plant plant)
        throws URISyntaxException {
        log.debug("REST request to update Plant : {}, {}", id, plant);
        if (plant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Plant result = plantRepository.save(plant);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plant.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plants/:id} : Partial updates given fields of an existing plant, field will ignore if it is null
     *
     * @param id the id of the plant to save.
     * @param plant the plant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plant,
     * or with status {@code 400 (Bad Request)} if the plant is not valid,
     * or with status {@code 404 (Not Found)} if the plant is not found,
     * or with status {@code 500 (Internal Server Error)} if the plant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Plant> partialUpdatePlant(@PathVariable(value = "id", required = false) final Long id, @RequestBody Plant plant)
        throws URISyntaxException {
        log.debug("REST request to partial update Plant partially : {}, {}", id, plant);
        if (plant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Plant> result = plantRepository
            .findById(plant.getId())
            .map(existingPlant -> {
                if (plant.getgUID() != null) {
                    existingPlant.setgUID(plant.getgUID());
                }
                if (plant.getCommonName() != null) {
                    existingPlant.setCommonName(plant.getCommonName());
                }
                if (plant.getScientificName() != null) {
                    existingPlant.setScientificName(plant.getScientificName());
                }
                if (plant.getFamilyName() != null) {
                    existingPlant.setFamilyName(plant.getFamilyName());
                }
                if (plant.getPlantSpacing() != null) {
                    existingPlant.setPlantSpacing(plant.getPlantSpacing());
                }
                if (plant.getSeedingMonth() != null) {
                    existingPlant.setSeedingMonth(plant.getSeedingMonth());
                }
                if (plant.getTransplantMonth() != null) {
                    existingPlant.setTransplantMonth(plant.getTransplantMonth());
                }
                if (plant.getHarvestMonth() != null) {
                    existingPlant.setHarvestMonth(plant.getHarvestMonth());
                }
                if (plant.getOriginCountry() != null) {
                    existingPlant.setOriginCountry(plant.getOriginCountry());
                }
                if (plant.getYearlyCrops() != null) {
                    existingPlant.setYearlyCrops(plant.getYearlyCrops());
                }
                if (plant.getNativeTemperature() != null) {
                    existingPlant.setNativeTemperature(plant.getNativeTemperature());
                }
                if (plant.getNativeHumidity() != null) {
                    existingPlant.setNativeHumidity(plant.getNativeHumidity());
                }
                if (plant.getNativeDayDuration() != null) {
                    existingPlant.setNativeDayDuration(plant.getNativeDayDuration());
                }
                if (plant.getNativeNightDuration() != null) {
                    existingPlant.setNativeNightDuration(plant.getNativeNightDuration());
                }
                if (plant.getNativeSoilMoisture() != null) {
                    existingPlant.setNativeSoilMoisture(plant.getNativeSoilMoisture());
                }
                if (plant.getPlantingPeriod() != null) {
                    existingPlant.setPlantingPeriod(plant.getPlantingPeriod());
                }
                if (plant.getYieldUnit() != null) {
                    existingPlant.setYieldUnit(plant.getYieldUnit());
                }
                if (plant.getGrowthHeightMin() != null) {
                    existingPlant.setGrowthHeightMin(plant.getGrowthHeightMin());
                }
                if (plant.getGrowthHeightMax() != null) {
                    existingPlant.setGrowthHeightMax(plant.getGrowthHeightMax());
                }
                if (plant.getGrownSpreadMin() != null) {
                    existingPlant.setGrownSpreadMin(plant.getGrownSpreadMin());
                }
                if (plant.getGrownSpreadMax() != null) {
                    existingPlant.setGrownSpreadMax(plant.getGrownSpreadMax());
                }
                if (plant.getGrownWeightMax() != null) {
                    existingPlant.setGrownWeightMax(plant.getGrownWeightMax());
                }
                if (plant.getGrownWeightMin() != null) {
                    existingPlant.setGrownWeightMin(plant.getGrownWeightMin());
                }
                if (plant.getGrowingMedia() != null) {
                    existingPlant.setGrowingMedia(plant.getGrowingMedia());
                }
                if (plant.getDocuments() != null) {
                    existingPlant.setDocuments(plant.getDocuments());
                }
                if (plant.getNotes() != null) {
                    existingPlant.setNotes(plant.getNotes());
                }
                if (plant.getAttachments() != null) {
                    existingPlant.setAttachments(plant.getAttachments());
                }
                if (plant.getAttachmentsContentType() != null) {
                    existingPlant.setAttachmentsContentType(plant.getAttachmentsContentType());
                }
                if (plant.getCreatedBy() != null) {
                    existingPlant.setCreatedBy(plant.getCreatedBy());
                }
                if (plant.getCreatedOn() != null) {
                    existingPlant.setCreatedOn(plant.getCreatedOn());
                }
                if (plant.getUpdatedBy() != null) {
                    existingPlant.setUpdatedBy(plant.getUpdatedBy());
                }
                if (plant.getUpdatedOn() != null) {
                    existingPlant.setUpdatedOn(plant.getUpdatedOn());
                }

                return existingPlant;
            })
            .map(plantRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plant.getId().toString())
        );
    }

    /**
     * {@code GET  /plants} : get all the plants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plants in body.
     */
    @GetMapping("/plants")
    public List<Plant> getAllPlants() {
        log.debug("REST request to get all Plants");
        return plantRepository.findAll();
    }

    /**
     * {@code GET  /plants/:id} : get the "id" plant.
     *
     * @param id the id of the plant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plants/{id}")
    public ResponseEntity<Plant> getPlant(@PathVariable Long id) {
        log.debug("REST request to get Plant : {}", id);
        Optional<Plant> plant = plantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(plant);
    }

    /**
     * {@code DELETE  /plants/:id} : delete the "id" plant.
     *
     * @param id the id of the plant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plants/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
        log.debug("REST request to delete Plant : {}", id);
        plantRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
