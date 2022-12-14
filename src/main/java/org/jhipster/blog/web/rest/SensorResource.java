package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Sensor;
import org.jhipster.blog.repository.SensorRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Sensor}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SensorResource {

    private final Logger log = LoggerFactory.getLogger(SensorResource.class);

    private static final String ENTITY_NAME = "sensor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SensorRepository sensorRepository;

    public SensorResource(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    /**
     * {@code POST  /sensors} : Create a new sensor.
     *
     * @param sensor the sensor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sensor, or with status {@code 400 (Bad Request)} if the sensor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sensors")
    public ResponseEntity<Sensor> createSensor(@RequestBody Sensor sensor) throws URISyntaxException {
        log.debug("REST request to save Sensor : {}", sensor);
        if (sensor.getId() != null) {
            throw new BadRequestAlertException("A new sensor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sensor result = sensorRepository.save(sensor);
        return ResponseEntity
            .created(new URI("/api/sensors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sensors/:id} : Updates an existing sensor.
     *
     * @param id the id of the sensor to save.
     * @param sensor the sensor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sensor,
     * or with status {@code 400 (Bad Request)} if the sensor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sensor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sensors/{id}")
    public ResponseEntity<Sensor> updateSensor(@PathVariable(value = "id", required = false) final Long id, @RequestBody Sensor sensor)
        throws URISyntaxException {
        log.debug("REST request to update Sensor : {}, {}", id, sensor);
        if (sensor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sensor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sensorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sensor result = sensorRepository.save(sensor);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sensor.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sensors/:id} : Partial updates given fields of an existing sensor, field will ignore if it is null
     *
     * @param id the id of the sensor to save.
     * @param sensor the sensor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sensor,
     * or with status {@code 400 (Bad Request)} if the sensor is not valid,
     * or with status {@code 404 (Not Found)} if the sensor is not found,
     * or with status {@code 500 (Internal Server Error)} if the sensor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sensors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sensor> partialUpdateSensor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sensor sensor
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sensor partially : {}, {}", id, sensor);
        if (sensor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sensor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sensorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sensor> result = sensorRepository
            .findById(sensor.getId())
            .map(existingSensor -> {
                if (sensor.getgUID() != null) {
                    existingSensor.setgUID(sensor.getgUID());
                }
                if (sensor.getSensorName() != null) {
                    existingSensor.setSensorName(sensor.getSensorName());
                }
                if (sensor.getSensorModelName() != null) {
                    existingSensor.setSensorModelName(sensor.getSensorModelName());
                }
                if (sensor.getHardwareID() != null) {
                    existingSensor.setHardwareID(sensor.getHardwareID());
                }
                if (sensor.getPort() != null) {
                    existingSensor.setPort(sensor.getPort());
                }
                if (sensor.getProperties() != null) {
                    existingSensor.setProperties(sensor.getProperties());
                }
                if (sensor.getDescription() != null) {
                    existingSensor.setDescription(sensor.getDescription());
                }
                if (sensor.getCreatedBy() != null) {
                    existingSensor.setCreatedBy(sensor.getCreatedBy());
                }
                if (sensor.getCreatedOn() != null) {
                    existingSensor.setCreatedOn(sensor.getCreatedOn());
                }
                if (sensor.getUpdatedBy() != null) {
                    existingSensor.setUpdatedBy(sensor.getUpdatedBy());
                }
                if (sensor.getUpdatedOn() != null) {
                    existingSensor.setUpdatedOn(sensor.getUpdatedOn());
                }

                return existingSensor;
            })
            .map(sensorRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sensor.getId().toString())
        );
    }

    /**
     * {@code GET  /sensors} : get all the sensors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sensors in body.
     */
    @GetMapping("/sensors")
    public List<Sensor> getAllSensors() {
        log.debug("REST request to get all Sensors");
        return sensorRepository.findAll();
    }

    /**
     * {@code GET  /sensors/:id} : get the "id" sensor.
     *
     * @param id the id of the sensor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sensor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sensors/{id}")
    public ResponseEntity<Sensor> getSensor(@PathVariable Long id) {
        log.debug("REST request to get Sensor : {}", id);
        Optional<Sensor> sensor = sensorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sensor);
    }

    /**
     * {@code DELETE  /sensors/:id} : delete the "id" sensor.
     *
     * @param id the id of the sensor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sensors/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        log.debug("REST request to delete Sensor : {}", id);
        sensorRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
