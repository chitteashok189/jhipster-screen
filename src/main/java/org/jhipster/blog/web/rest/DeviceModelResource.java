package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.DeviceModel;
import org.jhipster.blog.repository.DeviceModelRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.DeviceModel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DeviceModelResource {

    private final Logger log = LoggerFactory.getLogger(DeviceModelResource.class);

    private static final String ENTITY_NAME = "deviceModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceModelRepository deviceModelRepository;

    public DeviceModelResource(DeviceModelRepository deviceModelRepository) {
        this.deviceModelRepository = deviceModelRepository;
    }

    /**
     * {@code POST  /device-models} : Create a new deviceModel.
     *
     * @param deviceModel the deviceModel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceModel, or with status {@code 400 (Bad Request)} if the deviceModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-models")
    public ResponseEntity<DeviceModel> createDeviceModel(@RequestBody DeviceModel deviceModel) throws URISyntaxException {
        log.debug("REST request to save DeviceModel : {}", deviceModel);
        if (deviceModel.getId() != null) {
            throw new BadRequestAlertException("A new deviceModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceModel result = deviceModelRepository.save(deviceModel);
        return ResponseEntity
            .created(new URI("/api/device-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-models/:id} : Updates an existing deviceModel.
     *
     * @param id the id of the deviceModel to save.
     * @param deviceModel the deviceModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceModel,
     * or with status {@code 400 (Bad Request)} if the deviceModel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-models/{id}")
    public ResponseEntity<DeviceModel> updateDeviceModel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceModel deviceModel
    ) throws URISyntaxException {
        log.debug("REST request to update DeviceModel : {}, {}", id, deviceModel);
        if (deviceModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceModel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeviceModel result = deviceModelRepository.save(deviceModel);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceModel.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /device-models/:id} : Partial updates given fields of an existing deviceModel, field will ignore if it is null
     *
     * @param id the id of the deviceModel to save.
     * @param deviceModel the deviceModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceModel,
     * or with status {@code 400 (Bad Request)} if the deviceModel is not valid,
     * or with status {@code 404 (Not Found)} if the deviceModel is not found,
     * or with status {@code 500 (Internal Server Error)} if the deviceModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/device-models/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DeviceModel> partialUpdateDeviceModel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceModel deviceModel
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeviceModel partially : {}, {}", id, deviceModel);
        if (deviceModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceModel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeviceModel> result = deviceModelRepository
            .findById(deviceModel.getId())
            .map(existingDeviceModel -> {
                if (deviceModel.getgUID() != null) {
                    existingDeviceModel.setgUID(deviceModel.getgUID());
                }
                if (deviceModel.getModelName() != null) {
                    existingDeviceModel.setModelName(deviceModel.getModelName());
                }
                if (deviceModel.getType() != null) {
                    existingDeviceModel.setType(deviceModel.getType());
                }
                if (deviceModel.getManufacturer() != null) {
                    existingDeviceModel.setManufacturer(deviceModel.getManufacturer());
                }
                if (deviceModel.getProductCode() != null) {
                    existingDeviceModel.setProductCode(deviceModel.getProductCode());
                }
                if (deviceModel.getProperties() != null) {
                    existingDeviceModel.setProperties(deviceModel.getProperties());
                }
                if (deviceModel.getDescription() != null) {
                    existingDeviceModel.setDescription(deviceModel.getDescription());
                }
                if (deviceModel.getCreatedBy() != null) {
                    existingDeviceModel.setCreatedBy(deviceModel.getCreatedBy());
                }
                if (deviceModel.getCreatedOn() != null) {
                    existingDeviceModel.setCreatedOn(deviceModel.getCreatedOn());
                }
                if (deviceModel.getUpdatedBy() != null) {
                    existingDeviceModel.setUpdatedBy(deviceModel.getUpdatedBy());
                }
                if (deviceModel.getUpdatedOn() != null) {
                    existingDeviceModel.setUpdatedOn(deviceModel.getUpdatedOn());
                }

                return existingDeviceModel;
            })
            .map(deviceModelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceModel.getId().toString())
        );
    }

    /**
     * {@code GET  /device-models} : get all the deviceModels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceModels in body.
     */
    @GetMapping("/device-models")
    public List<DeviceModel> getAllDeviceModels() {
        log.debug("REST request to get all DeviceModels");
        return deviceModelRepository.findAll();
    }

    /**
     * {@code GET  /device-models/:id} : get the "id" deviceModel.
     *
     * @param id the id of the deviceModel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceModel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-models/{id}")
    public ResponseEntity<DeviceModel> getDeviceModel(@PathVariable Long id) {
        log.debug("REST request to get DeviceModel : {}", id);
        Optional<DeviceModel> deviceModel = deviceModelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deviceModel);
    }

    /**
     * {@code DELETE  /device-models/:id} : delete the "id" deviceModel.
     *
     * @param id the id of the deviceModel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-models/{id}")
    public ResponseEntity<Void> deleteDeviceModel(@PathVariable Long id) {
        log.debug("REST request to delete DeviceModel : {}", id);
        deviceModelRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
