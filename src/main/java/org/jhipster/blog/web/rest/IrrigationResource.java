package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Irrigation;
import org.jhipster.blog.repository.IrrigationRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Irrigation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IrrigationResource {

    private final Logger log = LoggerFactory.getLogger(IrrigationResource.class);

    private static final String ENTITY_NAME = "irrigation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IrrigationRepository irrigationRepository;

    public IrrigationResource(IrrigationRepository irrigationRepository) {
        this.irrigationRepository = irrigationRepository;
    }

    /**
     * {@code POST  /irrigations} : Create a new irrigation.
     *
     * @param irrigation the irrigation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new irrigation, or with status {@code 400 (Bad Request)} if the irrigation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/irrigations")
    public ResponseEntity<Irrigation> createIrrigation(@RequestBody Irrigation irrigation) throws URISyntaxException {
        log.debug("REST request to save Irrigation : {}", irrigation);
        if (irrigation.getId() != null) {
            throw new BadRequestAlertException("A new irrigation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Irrigation result = irrigationRepository.save(irrigation);
        return ResponseEntity
            .created(new URI("/api/irrigations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /irrigations/:id} : Updates an existing irrigation.
     *
     * @param id the id of the irrigation to save.
     * @param irrigation the irrigation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated irrigation,
     * or with status {@code 400 (Bad Request)} if the irrigation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the irrigation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/irrigations/{id}")
    public ResponseEntity<Irrigation> updateIrrigation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Irrigation irrigation
    ) throws URISyntaxException {
        log.debug("REST request to update Irrigation : {}, {}", id, irrigation);
        if (irrigation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, irrigation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!irrigationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Irrigation result = irrigationRepository.save(irrigation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, irrigation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /irrigations/:id} : Partial updates given fields of an existing irrigation, field will ignore if it is null
     *
     * @param id the id of the irrigation to save.
     * @param irrigation the irrigation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated irrigation,
     * or with status {@code 400 (Bad Request)} if the irrigation is not valid,
     * or with status {@code 404 (Not Found)} if the irrigation is not found,
     * or with status {@code 500 (Internal Server Error)} if the irrigation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/irrigations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Irrigation> partialUpdateIrrigation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Irrigation irrigation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Irrigation partially : {}, {}", id, irrigation);
        if (irrigation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, irrigation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!irrigationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Irrigation> result = irrigationRepository
            .findById(irrigation.getId())
            .map(existingIrrigation -> {
                if (irrigation.getgUID() != null) {
                    existingIrrigation.setgUID(irrigation.getgUID());
                }
                if (irrigation.getSource() != null) {
                    existingIrrigation.setSource(irrigation.getSource());
                }
                if (irrigation.getNutrientLevel() != null) {
                    existingIrrigation.setNutrientLevel(irrigation.getNutrientLevel());
                }
                if (irrigation.getSolarRadiation() != null) {
                    existingIrrigation.setSolarRadiation(irrigation.getSolarRadiation());
                }
                if (irrigation.getInletFlow() != null) {
                    existingIrrigation.setInletFlow(irrigation.getInletFlow());
                }
                if (irrigation.getOutletFlow() != null) {
                    existingIrrigation.setOutletFlow(irrigation.getOutletFlow());
                }
                if (irrigation.getCreatedBy() != null) {
                    existingIrrigation.setCreatedBy(irrigation.getCreatedBy());
                }
                if (irrigation.getCreatedOn() != null) {
                    existingIrrigation.setCreatedOn(irrigation.getCreatedOn());
                }
                if (irrigation.getUpdatedBy() != null) {
                    existingIrrigation.setUpdatedBy(irrigation.getUpdatedBy());
                }
                if (irrigation.getUpdatedOn() != null) {
                    existingIrrigation.setUpdatedOn(irrigation.getUpdatedOn());
                }

                return existingIrrigation;
            })
            .map(irrigationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, irrigation.getId().toString())
        );
    }

    /**
     * {@code GET  /irrigations} : get all the irrigations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of irrigations in body.
     */
    @GetMapping("/irrigations")
    public List<Irrigation> getAllIrrigations() {
        log.debug("REST request to get all Irrigations");
        return irrigationRepository.findAll();
    }

    /**
     * {@code GET  /irrigations/:id} : get the "id" irrigation.
     *
     * @param id the id of the irrigation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the irrigation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/irrigations/{id}")
    public ResponseEntity<Irrigation> getIrrigation(@PathVariable Long id) {
        log.debug("REST request to get Irrigation : {}", id);
        Optional<Irrigation> irrigation = irrigationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(irrigation);
    }

    /**
     * {@code DELETE  /irrigations/:id} : delete the "id" irrigation.
     *
     * @param id the id of the irrigation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/irrigations/{id}")
    public ResponseEntity<Void> deleteIrrigation(@PathVariable Long id) {
        log.debug("REST request to delete Irrigation : {}", id);
        irrigationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
