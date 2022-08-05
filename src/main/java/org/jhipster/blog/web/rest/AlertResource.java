package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Alert;
import org.jhipster.blog.repository.AlertRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Alert}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AlertResource {

    private final Logger log = LoggerFactory.getLogger(AlertResource.class);

    private static final String ENTITY_NAME = "alert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlertRepository alertRepository;

    public AlertResource(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    /**
     * {@code POST  /alerts} : Create a new alert.
     *
     * @param alert the alert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alert, or with status {@code 400 (Bad Request)} if the alert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alerts")
    public ResponseEntity<Alert> createAlert(@RequestBody Alert alert) throws URISyntaxException {
        log.debug("REST request to save Alert : {}", alert);
        if (alert.getId() != null) {
            throw new BadRequestAlertException("A new alert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Alert result = alertRepository.save(alert);
        return ResponseEntity
            .created(new URI("/api/alerts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alerts/:id} : Updates an existing alert.
     *
     * @param id the id of the alert to save.
     * @param alert the alert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alert,
     * or with status {@code 400 (Bad Request)} if the alert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alerts/{id}")
    public ResponseEntity<Alert> updateAlert(@PathVariable(value = "id", required = false) final Long id, @RequestBody Alert alert)
        throws URISyntaxException {
        log.debug("REST request to update Alert : {}, {}", id, alert);
        if (alert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alert.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Alert result = alertRepository.save(alert);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alert.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /alerts/:id} : Partial updates given fields of an existing alert, field will ignore if it is null
     *
     * @param id the id of the alert to save.
     * @param alert the alert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alert,
     * or with status {@code 400 (Bad Request)} if the alert is not valid,
     * or with status {@code 404 (Not Found)} if the alert is not found,
     * or with status {@code 500 (Internal Server Error)} if the alert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/alerts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Alert> partialUpdateAlert(@PathVariable(value = "id", required = false) final Long id, @RequestBody Alert alert)
        throws URISyntaxException {
        log.debug("REST request to partial update Alert partially : {}, {}", id, alert);
        if (alert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alert.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Alert> result = alertRepository
            .findById(alert.getId())
            .map(existingAlert -> {
                if (alert.getgUID() != null) {
                    existingAlert.setgUID(alert.getgUID());
                }
                if (alert.getName() != null) {
                    existingAlert.setName(alert.getName());
                }
                if (alert.getAlertType() != null) {
                    existingAlert.setAlertType(alert.getAlertType());
                }
                if (alert.getDescription() != null) {
                    existingAlert.setDescription(alert.getDescription());
                }
                if (alert.getDatePeriod() != null) {
                    existingAlert.setDatePeriod(alert.getDatePeriod());
                }
                if (alert.getDurationDays() != null) {
                    existingAlert.setDurationDays(alert.getDurationDays());
                }
                if (alert.getMinimumTemperature() != null) {
                    existingAlert.setMinimumTemperature(alert.getMinimumTemperature());
                }
                if (alert.getMaximumTemperature() != null) {
                    existingAlert.setMaximumTemperature(alert.getMaximumTemperature());
                }
                if (alert.getMinHumidity() != null) {
                    existingAlert.setMinHumidity(alert.getMinHumidity());
                }
                if (alert.getMaxHumidity() != null) {
                    existingAlert.setMaxHumidity(alert.getMaxHumidity());
                }
                if (alert.getPrecipitationType() != null) {
                    existingAlert.setPrecipitationType(alert.getPrecipitationType());
                }
                if (alert.getMinPrecipitation() != null) {
                    existingAlert.setMinPrecipitation(alert.getMinPrecipitation());
                }
                if (alert.getMaxPrecipitation() != null) {
                    existingAlert.setMaxPrecipitation(alert.getMaxPrecipitation());
                }
                if (alert.getStatus() != null) {
                    existingAlert.setStatus(alert.getStatus());
                }
                if (alert.getRemediation() != null) {
                    existingAlert.setRemediation(alert.getRemediation());
                }
                if (alert.getFarmAssigned() != null) {
                    existingAlert.setFarmAssigned(alert.getFarmAssigned());
                }
                if (alert.getCreatedBy() != null) {
                    existingAlert.setCreatedBy(alert.getCreatedBy());
                }
                if (alert.getCreatedOn() != null) {
                    existingAlert.setCreatedOn(alert.getCreatedOn());
                }
                if (alert.getUpdatedBy() != null) {
                    existingAlert.setUpdatedBy(alert.getUpdatedBy());
                }
                if (alert.getUpdatedOn() != null) {
                    existingAlert.setUpdatedOn(alert.getUpdatedOn());
                }

                return existingAlert;
            })
            .map(alertRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alert.getId().toString())
        );
    }

    /**
     * {@code GET  /alerts} : get all the alerts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alerts in body.
     */
    @GetMapping("/alerts")
    public List<Alert> getAllAlerts() {
        log.debug("REST request to get all Alerts");
        return alertRepository.findAll();
    }

    /**
     * {@code GET  /alerts/:id} : get the "id" alert.
     *
     * @param id the id of the alert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alerts/{id}")
    public ResponseEntity<Alert> getAlert(@PathVariable Long id) {
        log.debug("REST request to get Alert : {}", id);
        Optional<Alert> alert = alertRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(alert);
    }

    /**
     * {@code DELETE  /alerts/:id} : delete the "id" alert.
     *
     * @param id the id of the alert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alerts/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        log.debug("REST request to delete Alert : {}", id);
        alertRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
