package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.SensorModel;
import org.jhipster.blog.repository.SensorModelRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.SensorModel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SensorModelResource {

    private final Logger log = LoggerFactory.getLogger(SensorModelResource.class);

    private static final String ENTITY_NAME = "sensorModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SensorModelRepository sensorModelRepository;

    public SensorModelResource(SensorModelRepository sensorModelRepository) {
        this.sensorModelRepository = sensorModelRepository;
    }

    /**
     * {@code POST  /sensor-models} : Create a new sensorModel.
     *
     * @param sensorModel the sensorModel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sensorModel, or with status {@code 400 (Bad Request)} if the sensorModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sensor-models")
    public ResponseEntity<SensorModel> createSensorModel(@RequestBody SensorModel sensorModel) throws URISyntaxException {
        log.debug("REST request to save SensorModel : {}", sensorModel);
        if (sensorModel.getId() != null) {
            throw new BadRequestAlertException("A new sensorModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SensorModel result = sensorModelRepository.save(sensorModel);
        return ResponseEntity
            .created(new URI("/api/sensor-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sensor-models/:id} : Updates an existing sensorModel.
     *
     * @param id the id of the sensorModel to save.
     * @param sensorModel the sensorModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sensorModel,
     * or with status {@code 400 (Bad Request)} if the sensorModel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sensorModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sensor-models/{id}")
    public ResponseEntity<SensorModel> updateSensorModel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SensorModel sensorModel
    ) throws URISyntaxException {
        log.debug("REST request to update SensorModel : {}, {}", id, sensorModel);
        if (sensorModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sensorModel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sensorModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SensorModel result = sensorModelRepository.save(sensorModel);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sensorModel.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sensor-models/:id} : Partial updates given fields of an existing sensorModel, field will ignore if it is null
     *
     * @param id the id of the sensorModel to save.
     * @param sensorModel the sensorModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sensorModel,
     * or with status {@code 400 (Bad Request)} if the sensorModel is not valid,
     * or with status {@code 404 (Not Found)} if the sensorModel is not found,
     * or with status {@code 500 (Internal Server Error)} if the sensorModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sensor-models/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SensorModel> partialUpdateSensorModel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SensorModel sensorModel
    ) throws URISyntaxException {
        log.debug("REST request to partial update SensorModel partially : {}, {}", id, sensorModel);
        if (sensorModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sensorModel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sensorModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SensorModel> result = sensorModelRepository
            .findById(sensorModel.getId())
            .map(existingSensorModel -> {
                if (sensorModel.getgUID() != null) {
                    existingSensorModel.setgUID(sensorModel.getgUID());
                }
                if (sensorModel.getSensorType() != null) {
                    existingSensorModel.setSensorType(sensorModel.getSensorType());
                }
                if (sensorModel.getManufacturer() != null) {
                    existingSensorModel.setManufacturer(sensorModel.getManufacturer());
                }
                if (sensorModel.getProductCode() != null) {
                    existingSensorModel.setProductCode(sensorModel.getProductCode());
                }
                if (sensorModel.getSensorMeasure() != null) {
                    existingSensorModel.setSensorMeasure(sensorModel.getSensorMeasure());
                }
                if (sensorModel.getModelName() != null) {
                    existingSensorModel.setModelName(sensorModel.getModelName());
                }
                if (sensorModel.getProperties() != null) {
                    existingSensorModel.setProperties(sensorModel.getProperties());
                }
                if (sensorModel.getDescription() != null) {
                    existingSensorModel.setDescription(sensorModel.getDescription());
                }
                if (sensorModel.getCreatedBy() != null) {
                    existingSensorModel.setCreatedBy(sensorModel.getCreatedBy());
                }
                if (sensorModel.getCreatedOn() != null) {
                    existingSensorModel.setCreatedOn(sensorModel.getCreatedOn());
                }
                if (sensorModel.getUpdatedBy() != null) {
                    existingSensorModel.setUpdatedBy(sensorModel.getUpdatedBy());
                }
                if (sensorModel.getUpdatedOn() != null) {
                    existingSensorModel.setUpdatedOn(sensorModel.getUpdatedOn());
                }

                return existingSensorModel;
            })
            .map(sensorModelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sensorModel.getId().toString())
        );
    }

    /**
     * {@code GET  /sensor-models} : get all the sensorModels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sensorModels in body.
     */
    @GetMapping("/sensor-models")
    public List<SensorModel> getAllSensorModels() {
        log.debug("REST request to get all SensorModels");
        return sensorModelRepository.findAll();
    }

    /**
     * {@code GET  /sensor-models/:id} : get the "id" sensorModel.
     *
     * @param id the id of the sensorModel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sensorModel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sensor-models/{id}")
    public ResponseEntity<SensorModel> getSensorModel(@PathVariable Long id) {
        log.debug("REST request to get SensorModel : {}", id);
        Optional<SensorModel> sensorModel = sensorModelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sensorModel);
    }

    /**
     * {@code DELETE  /sensor-models/:id} : delete the "id" sensorModel.
     *
     * @param id the id of the sensorModel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sensor-models/{id}")
    public ResponseEntity<Void> deleteSensorModel(@PathVariable Long id) {
        log.debug("REST request to delete SensorModel : {}", id);
        sensorModelRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
