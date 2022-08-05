package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Crop;
import org.jhipster.blog.repository.CropRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Crop}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CropResource {

    private final Logger log = LoggerFactory.getLogger(CropResource.class);

    private static final String ENTITY_NAME = "crop";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CropRepository cropRepository;

    public CropResource(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    /**
     * {@code POST  /crops} : Create a new crop.
     *
     * @param crop the crop to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new crop, or with status {@code 400 (Bad Request)} if the crop has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crops")
    public ResponseEntity<Crop> createCrop(@RequestBody Crop crop) throws URISyntaxException {
        log.debug("REST request to save Crop : {}", crop);
        if (crop.getId() != null) {
            throw new BadRequestAlertException("A new crop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Crop result = cropRepository.save(crop);
        return ResponseEntity
            .created(new URI("/api/crops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /crops/:id} : Updates an existing crop.
     *
     * @param id the id of the crop to save.
     * @param crop the crop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crop,
     * or with status {@code 400 (Bad Request)} if the crop is not valid,
     * or with status {@code 500 (Internal Server Error)} if the crop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crops/{id}")
    public ResponseEntity<Crop> updateCrop(@PathVariable(value = "id", required = false) final Long id, @RequestBody Crop crop)
        throws URISyntaxException {
        log.debug("REST request to update Crop : {}, {}", id, crop);
        if (crop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cropRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Crop result = cropRepository.save(crop);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, crop.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /crops/:id} : Partial updates given fields of an existing crop, field will ignore if it is null
     *
     * @param id the id of the crop to save.
     * @param crop the crop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crop,
     * or with status {@code 400 (Bad Request)} if the crop is not valid,
     * or with status {@code 404 (Not Found)} if the crop is not found,
     * or with status {@code 500 (Internal Server Error)} if the crop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/crops/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Crop> partialUpdateCrop(@PathVariable(value = "id", required = false) final Long id, @RequestBody Crop crop)
        throws URISyntaxException {
        log.debug("REST request to partial update Crop partially : {}, {}", id, crop);
        if (crop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cropRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Crop> result = cropRepository
            .findById(crop.getId())
            .map(existingCrop -> {
                if (crop.getgUID() != null) {
                    existingCrop.setgUID(crop.getgUID());
                }
                if (crop.getCropCode() != null) {
                    existingCrop.setCropCode(crop.getCropCode());
                }
                if (crop.getCropName() != null) {
                    existingCrop.setCropName(crop.getCropName());
                }
                if (crop.getCropType() != null) {
                    existingCrop.setCropType(crop.getCropType());
                }
                if (crop.getHorticultureType() != null) {
                    existingCrop.setHorticultureType(crop.getHorticultureType());
                }
                if (crop.getIsHybrid() != null) {
                    existingCrop.setIsHybrid(crop.getIsHybrid());
                }
                if (crop.getCultivar() != null) {
                    existingCrop.setCultivar(crop.getCultivar());
                }
                if (crop.getSowingDate() != null) {
                    existingCrop.setSowingDate(crop.getSowingDate());
                }
                if (crop.getSowingDepth() != null) {
                    existingCrop.setSowingDepth(crop.getSowingDepth());
                }
                if (crop.getRowSpacingMax() != null) {
                    existingCrop.setRowSpacingMax(crop.getRowSpacingMax());
                }
                if (crop.getRowSpacingMin() != null) {
                    existingCrop.setRowSpacingMin(crop.getRowSpacingMin());
                }
                if (crop.getSeedDepthMax() != null) {
                    existingCrop.setSeedDepthMax(crop.getSeedDepthMax());
                }
                if (crop.getSeedDepthMin() != null) {
                    existingCrop.setSeedDepthMin(crop.getSeedDepthMin());
                }
                if (crop.getSeedSpacingMax() != null) {
                    existingCrop.setSeedSpacingMax(crop.getSeedSpacingMax());
                }
                if (crop.getSeedSpacingMin() != null) {
                    existingCrop.setSeedSpacingMin(crop.getSeedSpacingMin());
                }
                if (crop.getYearlyCrops() != null) {
                    existingCrop.setYearlyCrops(crop.getYearlyCrops());
                }
                if (crop.getGrowingSeason() != null) {
                    existingCrop.setGrowingSeason(crop.getGrowingSeason());
                }
                if (crop.getGrowingPhase() != null) {
                    existingCrop.setGrowingPhase(crop.getGrowingPhase());
                }
                if (crop.getPlantingDate() != null) {
                    existingCrop.setPlantingDate(crop.getPlantingDate());
                }
                if (crop.getPlantSpacing() != null) {
                    existingCrop.setPlantSpacing(crop.getPlantSpacing());
                }
                if (crop.getPlantingMaterial() != null) {
                    existingCrop.setPlantingMaterial(crop.getPlantingMaterial());
                }
                if (crop.getTransplantationDate() != null) {
                    existingCrop.setTransplantationDate(crop.getTransplantationDate());
                }
                if (crop.getFertigationscheduleID() != null) {
                    existingCrop.setFertigationscheduleID(crop.getFertigationscheduleID());
                }
                if (crop.getPlannedYield() != null) {
                    existingCrop.setPlannedYield(crop.getPlannedYield());
                }
                if (crop.getActualYield() != null) {
                    existingCrop.setActualYield(crop.getActualYield());
                }
                if (crop.getYieldUnit() != null) {
                    existingCrop.setYieldUnit(crop.getYieldUnit());
                }
                if (crop.getCreatedBy() != null) {
                    existingCrop.setCreatedBy(crop.getCreatedBy());
                }
                if (crop.getCreatedOn() != null) {
                    existingCrop.setCreatedOn(crop.getCreatedOn());
                }
                if (crop.getUpdatedBy() != null) {
                    existingCrop.setUpdatedBy(crop.getUpdatedBy());
                }
                if (crop.getUpdatedOn() != null) {
                    existingCrop.setUpdatedOn(crop.getUpdatedOn());
                }

                return existingCrop;
            })
            .map(cropRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, crop.getId().toString())
        );
    }

    /**
     * {@code GET  /crops} : get all the crops.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crops in body.
     */
    @GetMapping("/crops")
    public List<Crop> getAllCrops() {
        log.debug("REST request to get all Crops");
        return cropRepository.findAll();
    }

    /**
     * {@code GET  /crops/:id} : get the "id" crop.
     *
     * @param id the id of the crop to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the crop, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crops/{id}")
    public ResponseEntity<Crop> getCrop(@PathVariable Long id) {
        log.debug("REST request to get Crop : {}", id);
        Optional<Crop> crop = cropRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(crop);
    }

    /**
     * {@code DELETE  /crops/:id} : delete the "id" crop.
     *
     * @param id the id of the crop to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crops/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        log.debug("REST request to delete Crop : {}", id);
        cropRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
