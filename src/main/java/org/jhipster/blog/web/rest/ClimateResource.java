package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Climate;
import org.jhipster.blog.repository.ClimateRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Climate}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClimateResource {

    private final Logger log = LoggerFactory.getLogger(ClimateResource.class);

    private static final String ENTITY_NAME = "climate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClimateRepository climateRepository;

    public ClimateResource(ClimateRepository climateRepository) {
        this.climateRepository = climateRepository;
    }

    /**
     * {@code POST  /climates} : Create a new climate.
     *
     * @param climate the climate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new climate, or with status {@code 400 (Bad Request)} if the climate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/climates")
    public ResponseEntity<Climate> createClimate(@RequestBody Climate climate) throws URISyntaxException {
        log.debug("REST request to save Climate : {}", climate);
        if (climate.getId() != null) {
            throw new BadRequestAlertException("A new climate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Climate result = climateRepository.save(climate);
        return ResponseEntity
            .created(new URI("/api/climates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /climates/:id} : Updates an existing climate.
     *
     * @param id the id of the climate to save.
     * @param climate the climate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated climate,
     * or with status {@code 400 (Bad Request)} if the climate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the climate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/climates/{id}")
    public ResponseEntity<Climate> updateClimate(@PathVariable(value = "id", required = false) final Long id, @RequestBody Climate climate)
        throws URISyntaxException {
        log.debug("REST request to update Climate : {}, {}", id, climate);
        if (climate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, climate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!climateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Climate result = climateRepository.save(climate);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, climate.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /climates/:id} : Partial updates given fields of an existing climate, field will ignore if it is null
     *
     * @param id the id of the climate to save.
     * @param climate the climate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated climate,
     * or with status {@code 400 (Bad Request)} if the climate is not valid,
     * or with status {@code 404 (Not Found)} if the climate is not found,
     * or with status {@code 500 (Internal Server Error)} if the climate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/climates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Climate> partialUpdateClimate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Climate climate
    ) throws URISyntaxException {
        log.debug("REST request to partial update Climate partially : {}, {}", id, climate);
        if (climate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, climate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!climateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Climate> result = climateRepository
            .findById(climate.getId())
            .map(existingClimate -> {
                if (climate.getgUID() != null) {
                    existingClimate.setgUID(climate.getgUID());
                }
                if (climate.getSource() != null) {
                    existingClimate.setSource(climate.getSource());
                }
                if (climate.getAirTemperature() != null) {
                    existingClimate.setAirTemperature(climate.getAirTemperature());
                }
                if (climate.getRelativeHumidity() != null) {
                    existingClimate.setRelativeHumidity(climate.getRelativeHumidity());
                }
                if (climate.getvPD() != null) {
                    existingClimate.setvPD(climate.getvPD());
                }
                if (climate.getEvapotranspiration() != null) {
                    existingClimate.setEvapotranspiration(climate.getEvapotranspiration());
                }
                if (climate.getBarometricPressure() != null) {
                    existingClimate.setBarometricPressure(climate.getBarometricPressure());
                }
                if (climate.getSeaLevelPressure() != null) {
                    existingClimate.setSeaLevelPressure(climate.getSeaLevelPressure());
                }
                if (climate.getCo2Level() != null) {
                    existingClimate.setCo2Level(climate.getCo2Level());
                }
                if (climate.getDewPoint() != null) {
                    existingClimate.setDewPoint(climate.getDewPoint());
                }
                if (climate.getSolarRadiation() != null) {
                    existingClimate.setSolarRadiation(climate.getSolarRadiation());
                }
                if (climate.getHeatIndex() != null) {
                    existingClimate.setHeatIndex(climate.getHeatIndex());
                }
                if (climate.getCreatedBy() != null) {
                    existingClimate.setCreatedBy(climate.getCreatedBy());
                }
                if (climate.getCreatedOn() != null) {
                    existingClimate.setCreatedOn(climate.getCreatedOn());
                }
                if (climate.getUpdatedBy() != null) {
                    existingClimate.setUpdatedBy(climate.getUpdatedBy());
                }
                if (climate.getUpdatedOn() != null) {
                    existingClimate.setUpdatedOn(climate.getUpdatedOn());
                }

                return existingClimate;
            })
            .map(climateRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, climate.getId().toString())
        );
    }

    /**
     * {@code GET  /climates} : get all the climates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of climates in body.
     */
    @GetMapping("/climates")
    public List<Climate> getAllClimates() {
        log.debug("REST request to get all Climates");
        return climateRepository.findAll();
    }

    /**
     * {@code GET  /climates/:id} : get the "id" climate.
     *
     * @param id the id of the climate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the climate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/climates/{id}")
    public ResponseEntity<Climate> getClimate(@PathVariable Long id) {
        log.debug("REST request to get Climate : {}", id);
        Optional<Climate> climate = climateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(climate);
    }

    /**
     * {@code DELETE  /climates/:id} : delete the "id" climate.
     *
     * @param id the id of the climate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/climates/{id}")
    public ResponseEntity<Void> deleteClimate(@PathVariable Long id) {
        log.debug("REST request to delete Climate : {}", id);
        climateRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
