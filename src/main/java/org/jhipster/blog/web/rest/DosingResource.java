package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Dosing;
import org.jhipster.blog.repository.DosingRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Dosing}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DosingResource {

    private final Logger log = LoggerFactory.getLogger(DosingResource.class);

    private static final String ENTITY_NAME = "dosing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DosingRepository dosingRepository;

    public DosingResource(DosingRepository dosingRepository) {
        this.dosingRepository = dosingRepository;
    }

    /**
     * {@code POST  /dosings} : Create a new dosing.
     *
     * @param dosing the dosing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dosing, or with status {@code 400 (Bad Request)} if the dosing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dosings")
    public ResponseEntity<Dosing> createDosing(@RequestBody Dosing dosing) throws URISyntaxException {
        log.debug("REST request to save Dosing : {}", dosing);
        if (dosing.getId() != null) {
            throw new BadRequestAlertException("A new dosing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dosing result = dosingRepository.save(dosing);
        return ResponseEntity
            .created(new URI("/api/dosings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dosings/:id} : Updates an existing dosing.
     *
     * @param id the id of the dosing to save.
     * @param dosing the dosing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dosing,
     * or with status {@code 400 (Bad Request)} if the dosing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dosing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dosings/{id}")
    public ResponseEntity<Dosing> updateDosing(@PathVariable(value = "id", required = false) final Long id, @RequestBody Dosing dosing)
        throws URISyntaxException {
        log.debug("REST request to update Dosing : {}, {}", id, dosing);
        if (dosing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dosing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dosingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Dosing result = dosingRepository.save(dosing);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dosing.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dosings/:id} : Partial updates given fields of an existing dosing, field will ignore if it is null
     *
     * @param id the id of the dosing to save.
     * @param dosing the dosing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dosing,
     * or with status {@code 400 (Bad Request)} if the dosing is not valid,
     * or with status {@code 404 (Not Found)} if the dosing is not found,
     * or with status {@code 500 (Internal Server Error)} if the dosing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dosings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Dosing> partialUpdateDosing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Dosing dosing
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dosing partially : {}, {}", id, dosing);
        if (dosing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dosing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dosingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Dosing> result = dosingRepository
            .findById(dosing.getId())
            .map(existingDosing -> {
                if (dosing.getgUID() != null) {
                    existingDosing.setgUID(dosing.getgUID());
                }
                if (dosing.getSource() != null) {
                    existingDosing.setSource(dosing.getSource());
                }
                if (dosing.getpH() != null) {
                    existingDosing.setpH(dosing.getpH());
                }
                if (dosing.geteC() != null) {
                    existingDosing.seteC(dosing.geteC());
                }
                if (dosing.getdO() != null) {
                    existingDosing.setdO(dosing.getdO());
                }
                if (dosing.getNutrientTemperature() != null) {
                    existingDosing.setNutrientTemperature(dosing.getNutrientTemperature());
                }
                if (dosing.getSolarRadiation() != null) {
                    existingDosing.setSolarRadiation(dosing.getSolarRadiation());
                }
                if (dosing.getCreatedBy() != null) {
                    existingDosing.setCreatedBy(dosing.getCreatedBy());
                }
                if (dosing.getCreatedOn() != null) {
                    existingDosing.setCreatedOn(dosing.getCreatedOn());
                }
                if (dosing.getUpdatedBy() != null) {
                    existingDosing.setUpdatedBy(dosing.getUpdatedBy());
                }
                if (dosing.getUpdatedOn() != null) {
                    existingDosing.setUpdatedOn(dosing.getUpdatedOn());
                }

                return existingDosing;
            })
            .map(dosingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dosing.getId().toString())
        );
    }

    /**
     * {@code GET  /dosings} : get all the dosings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dosings in body.
     */
    @GetMapping("/dosings")
    public List<Dosing> getAllDosings() {
        log.debug("REST request to get all Dosings");
        return dosingRepository.findAll();
    }

    /**
     * {@code GET  /dosings/:id} : get the "id" dosing.
     *
     * @param id the id of the dosing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dosing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dosings/{id}")
    public ResponseEntity<Dosing> getDosing(@PathVariable Long id) {
        log.debug("REST request to get Dosing : {}", id);
        Optional<Dosing> dosing = dosingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dosing);
    }

    /**
     * {@code DELETE  /dosings/:id} : delete the "id" dosing.
     *
     * @param id the id of the dosing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dosings/{id}")
    public ResponseEntity<Void> deleteDosing(@PathVariable Long id) {
        log.debug("REST request to delete Dosing : {}", id);
        dosingRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
