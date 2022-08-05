package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Nutrient;
import org.jhipster.blog.repository.NutrientRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Nutrient}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NutrientResource {

    private final Logger log = LoggerFactory.getLogger(NutrientResource.class);

    private static final String ENTITY_NAME = "nutrient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NutrientRepository nutrientRepository;

    public NutrientResource(NutrientRepository nutrientRepository) {
        this.nutrientRepository = nutrientRepository;
    }

    /**
     * {@code POST  /nutrients} : Create a new nutrient.
     *
     * @param nutrient the nutrient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nutrient, or with status {@code 400 (Bad Request)} if the nutrient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nutrients")
    public ResponseEntity<Nutrient> createNutrient(@RequestBody Nutrient nutrient) throws URISyntaxException {
        log.debug("REST request to save Nutrient : {}", nutrient);
        if (nutrient.getId() != null) {
            throw new BadRequestAlertException("A new nutrient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nutrient result = nutrientRepository.save(nutrient);
        return ResponseEntity
            .created(new URI("/api/nutrients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nutrients/:id} : Updates an existing nutrient.
     *
     * @param id the id of the nutrient to save.
     * @param nutrient the nutrient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nutrient,
     * or with status {@code 400 (Bad Request)} if the nutrient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nutrient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nutrients/{id}")
    public ResponseEntity<Nutrient> updateNutrient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nutrient nutrient
    ) throws URISyntaxException {
        log.debug("REST request to update Nutrient : {}, {}", id, nutrient);
        if (nutrient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nutrient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nutrientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Nutrient result = nutrientRepository.save(nutrient);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nutrient.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nutrients/:id} : Partial updates given fields of an existing nutrient, field will ignore if it is null
     *
     * @param id the id of the nutrient to save.
     * @param nutrient the nutrient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nutrient,
     * or with status {@code 400 (Bad Request)} if the nutrient is not valid,
     * or with status {@code 404 (Not Found)} if the nutrient is not found,
     * or with status {@code 500 (Internal Server Error)} if the nutrient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nutrients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Nutrient> partialUpdateNutrient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nutrient nutrient
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nutrient partially : {}, {}", id, nutrient);
        if (nutrient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nutrient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nutrientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Nutrient> result = nutrientRepository
            .findById(nutrient.getId())
            .map(existingNutrient -> {
                if (nutrient.getgUID() != null) {
                    existingNutrient.setgUID(nutrient.getgUID());
                }
                if (nutrient.getNutrientName() != null) {
                    existingNutrient.setNutrientName(nutrient.getNutrientName());
                }
                if (nutrient.getType() != null) {
                    existingNutrient.setType(nutrient.getType());
                }
                if (nutrient.getBrandName() != null) {
                    existingNutrient.setBrandName(nutrient.getBrandName());
                }
                if (nutrient.getNutrientLabel() != null) {
                    existingNutrient.setNutrientLabel(nutrient.getNutrientLabel());
                }
                if (nutrient.getNutrientForms() != null) {
                    existingNutrient.setNutrientForms(nutrient.getNutrientForms());
                }
                if (nutrient.getNutrientTypeID() != null) {
                    existingNutrient.setNutrientTypeID(nutrient.getNutrientTypeID());
                }
                if (nutrient.getPrice() != null) {
                    existingNutrient.setPrice(nutrient.getPrice());
                }
                if (nutrient.getCreatedBy() != null) {
                    existingNutrient.setCreatedBy(nutrient.getCreatedBy());
                }
                if (nutrient.getCreatedOn() != null) {
                    existingNutrient.setCreatedOn(nutrient.getCreatedOn());
                }
                if (nutrient.getUpdatedBy() != null) {
                    existingNutrient.setUpdatedBy(nutrient.getUpdatedBy());
                }
                if (nutrient.getUpdatedOn() != null) {
                    existingNutrient.setUpdatedOn(nutrient.getUpdatedOn());
                }

                return existingNutrient;
            })
            .map(nutrientRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nutrient.getId().toString())
        );
    }

    /**
     * {@code GET  /nutrients} : get all the nutrients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nutrients in body.
     */
    @GetMapping("/nutrients")
    public List<Nutrient> getAllNutrients() {
        log.debug("REST request to get all Nutrients");
        return nutrientRepository.findAll();
    }

    /**
     * {@code GET  /nutrients/:id} : get the "id" nutrient.
     *
     * @param id the id of the nutrient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nutrient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nutrients/{id}")
    public ResponseEntity<Nutrient> getNutrient(@PathVariable Long id) {
        log.debug("REST request to get Nutrient : {}", id);
        Optional<Nutrient> nutrient = nutrientRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nutrient);
    }

    /**
     * {@code DELETE  /nutrients/:id} : delete the "id" nutrient.
     *
     * @param id the id of the nutrient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nutrients/{id}")
    public ResponseEntity<Void> deleteNutrient(@PathVariable Long id) {
        log.debug("REST request to delete Nutrient : {}", id);
        nutrientRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
