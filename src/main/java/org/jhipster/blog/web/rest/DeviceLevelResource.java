package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.DeviceLevel;
import org.jhipster.blog.repository.DeviceLevelRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.DeviceLevel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DeviceLevelResource {

    private final Logger log = LoggerFactory.getLogger(DeviceLevelResource.class);

    private static final String ENTITY_NAME = "deviceLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceLevelRepository deviceLevelRepository;

    public DeviceLevelResource(DeviceLevelRepository deviceLevelRepository) {
        this.deviceLevelRepository = deviceLevelRepository;
    }

    /**
     * {@code POST  /device-levels} : Create a new deviceLevel.
     *
     * @param deviceLevel the deviceLevel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceLevel, or with status {@code 400 (Bad Request)} if the deviceLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-levels")
    public ResponseEntity<DeviceLevel> createDeviceLevel(@RequestBody DeviceLevel deviceLevel) throws URISyntaxException {
        log.debug("REST request to save DeviceLevel : {}", deviceLevel);
        if (deviceLevel.getId() != null) {
            throw new BadRequestAlertException("A new deviceLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceLevel result = deviceLevelRepository.save(deviceLevel);
        return ResponseEntity
            .created(new URI("/api/device-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-levels/:id} : Updates an existing deviceLevel.
     *
     * @param id the id of the deviceLevel to save.
     * @param deviceLevel the deviceLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceLevel,
     * or with status {@code 400 (Bad Request)} if the deviceLevel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-levels/{id}")
    public ResponseEntity<DeviceLevel> updateDeviceLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceLevel deviceLevel
    ) throws URISyntaxException {
        log.debug("REST request to update DeviceLevel : {}, {}", id, deviceLevel);
        if (deviceLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceLevel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeviceLevel result = deviceLevelRepository.save(deviceLevel);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceLevel.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /device-levels/:id} : Partial updates given fields of an existing deviceLevel, field will ignore if it is null
     *
     * @param id the id of the deviceLevel to save.
     * @param deviceLevel the deviceLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceLevel,
     * or with status {@code 400 (Bad Request)} if the deviceLevel is not valid,
     * or with status {@code 404 (Not Found)} if the deviceLevel is not found,
     * or with status {@code 500 (Internal Server Error)} if the deviceLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/device-levels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DeviceLevel> partialUpdateDeviceLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceLevel deviceLevel
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeviceLevel partially : {}, {}", id, deviceLevel);
        if (deviceLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceLevel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeviceLevel> result = deviceLevelRepository
            .findById(deviceLevel.getId())
            .map(existingDeviceLevel -> {
                if (deviceLevel.getgUID() != null) {
                    existingDeviceLevel.setgUID(deviceLevel.getgUID());
                }
                if (deviceLevel.getLevelName() != null) {
                    existingDeviceLevel.setLevelName(deviceLevel.getLevelName());
                }
                if (deviceLevel.getDeviceLevelType() != null) {
                    existingDeviceLevel.setDeviceLevelType(deviceLevel.getDeviceLevelType());
                }
                if (deviceLevel.getManufacturer() != null) {
                    existingDeviceLevel.setManufacturer(deviceLevel.getManufacturer());
                }
                if (deviceLevel.getProductCode() != null) {
                    existingDeviceLevel.setProductCode(deviceLevel.getProductCode());
                }
                if (deviceLevel.getPorts() != null) {
                    existingDeviceLevel.setPorts(deviceLevel.getPorts());
                }
                if (deviceLevel.getProperties() != null) {
                    existingDeviceLevel.setProperties(deviceLevel.getProperties());
                }
                if (deviceLevel.getDescription() != null) {
                    existingDeviceLevel.setDescription(deviceLevel.getDescription());
                }
                if (deviceLevel.getCreatedBy() != null) {
                    existingDeviceLevel.setCreatedBy(deviceLevel.getCreatedBy());
                }
                if (deviceLevel.getCreatedOn() != null) {
                    existingDeviceLevel.setCreatedOn(deviceLevel.getCreatedOn());
                }
                if (deviceLevel.getUpdatedBy() != null) {
                    existingDeviceLevel.setUpdatedBy(deviceLevel.getUpdatedBy());
                }
                if (deviceLevel.getUpdatedOn() != null) {
                    existingDeviceLevel.setUpdatedOn(deviceLevel.getUpdatedOn());
                }

                return existingDeviceLevel;
            })
            .map(deviceLevelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceLevel.getId().toString())
        );
    }

    /**
     * {@code GET  /device-levels} : get all the deviceLevels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceLevels in body.
     */
    @GetMapping("/device-levels")
    public List<DeviceLevel> getAllDeviceLevels() {
        log.debug("REST request to get all DeviceLevels");
        return deviceLevelRepository.findAll();
    }

    /**
     * {@code GET  /device-levels/:id} : get the "id" deviceLevel.
     *
     * @param id the id of the deviceLevel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceLevel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-levels/{id}")
    public ResponseEntity<DeviceLevel> getDeviceLevel(@PathVariable Long id) {
        log.debug("REST request to get DeviceLevel : {}", id);
        Optional<DeviceLevel> deviceLevel = deviceLevelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deviceLevel);
    }

    /**
     * {@code DELETE  /device-levels/:id} : delete the "id" deviceLevel.
     *
     * @param id the id of the deviceLevel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-levels/{id}")
    public ResponseEntity<Void> deleteDeviceLevel(@PathVariable Long id) {
        log.debug("REST request to delete DeviceLevel : {}", id);
        deviceLevelRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
