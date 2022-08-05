package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.RawMaterial;
import org.jhipster.blog.repository.RawMaterialRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.RawMaterial}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RawMaterialResource {

    private final Logger log = LoggerFactory.getLogger(RawMaterialResource.class);

    private static final String ENTITY_NAME = "rawMaterial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialResource(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    /**
     * {@code POST  /raw-materials} : Create a new rawMaterial.
     *
     * @param rawMaterial the rawMaterial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rawMaterial, or with status {@code 400 (Bad Request)} if the rawMaterial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/raw-materials")
    public ResponseEntity<RawMaterial> createRawMaterial(@RequestBody RawMaterial rawMaterial) throws URISyntaxException {
        log.debug("REST request to save RawMaterial : {}", rawMaterial);
        if (rawMaterial.getId() != null) {
            throw new BadRequestAlertException("A new rawMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RawMaterial result = rawMaterialRepository.save(rawMaterial);
        return ResponseEntity
            .created(new URI("/api/raw-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /raw-materials/:id} : Updates an existing rawMaterial.
     *
     * @param id the id of the rawMaterial to save.
     * @param rawMaterial the rawMaterial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rawMaterial,
     * or with status {@code 400 (Bad Request)} if the rawMaterial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rawMaterial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/raw-materials/{id}")
    public ResponseEntity<RawMaterial> updateRawMaterial(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RawMaterial rawMaterial
    ) throws URISyntaxException {
        log.debug("REST request to update RawMaterial : {}, {}", id, rawMaterial);
        if (rawMaterial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rawMaterial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rawMaterialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RawMaterial result = rawMaterialRepository.save(rawMaterial);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rawMaterial.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /raw-materials/:id} : Partial updates given fields of an existing rawMaterial, field will ignore if it is null
     *
     * @param id the id of the rawMaterial to save.
     * @param rawMaterial the rawMaterial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rawMaterial,
     * or with status {@code 400 (Bad Request)} if the rawMaterial is not valid,
     * or with status {@code 404 (Not Found)} if the rawMaterial is not found,
     * or with status {@code 500 (Internal Server Error)} if the rawMaterial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/raw-materials/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RawMaterial> partialUpdateRawMaterial(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RawMaterial rawMaterial
    ) throws URISyntaxException {
        log.debug("REST request to partial update RawMaterial partially : {}, {}", id, rawMaterial);
        if (rawMaterial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rawMaterial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rawMaterialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RawMaterial> result = rawMaterialRepository
            .findById(rawMaterial.getId())
            .map(existingRawMaterial -> {
                if (rawMaterial.getgUID() != null) {
                    existingRawMaterial.setgUID(rawMaterial.getgUID());
                }
                if (rawMaterial.getQuantity() != null) {
                    existingRawMaterial.setQuantity(rawMaterial.getQuantity());
                }
                if (rawMaterial.getuOM() != null) {
                    existingRawMaterial.setuOM(rawMaterial.getuOM());
                }
                if (rawMaterial.getMaterialType() != null) {
                    existingRawMaterial.setMaterialType(rawMaterial.getMaterialType());
                }
                if (rawMaterial.getPrice() != null) {
                    existingRawMaterial.setPrice(rawMaterial.getPrice());
                }
                if (rawMaterial.getDescription() != null) {
                    existingRawMaterial.setDescription(rawMaterial.getDescription());
                }
                if (rawMaterial.getCreatedBy() != null) {
                    existingRawMaterial.setCreatedBy(rawMaterial.getCreatedBy());
                }
                if (rawMaterial.getCreatedOn() != null) {
                    existingRawMaterial.setCreatedOn(rawMaterial.getCreatedOn());
                }
                if (rawMaterial.getUpdatedBy() != null) {
                    existingRawMaterial.setUpdatedBy(rawMaterial.getUpdatedBy());
                }
                if (rawMaterial.getUpdatedOn() != null) {
                    existingRawMaterial.setUpdatedOn(rawMaterial.getUpdatedOn());
                }

                return existingRawMaterial;
            })
            .map(rawMaterialRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rawMaterial.getId().toString())
        );
    }

    /**
     * {@code GET  /raw-materials} : get all the rawMaterials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rawMaterials in body.
     */
    @GetMapping("/raw-materials")
    public List<RawMaterial> getAllRawMaterials() {
        log.debug("REST request to get all RawMaterials");
        return rawMaterialRepository.findAll();
    }

    /**
     * {@code GET  /raw-materials/:id} : get the "id" rawMaterial.
     *
     * @param id the id of the rawMaterial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rawMaterial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/raw-materials/{id}")
    public ResponseEntity<RawMaterial> getRawMaterial(@PathVariable Long id) {
        log.debug("REST request to get RawMaterial : {}", id);
        Optional<RawMaterial> rawMaterial = rawMaterialRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rawMaterial);
    }

    /**
     * {@code DELETE  /raw-materials/:id} : delete the "id" rawMaterial.
     *
     * @param id the id of the rawMaterial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/raw-materials/{id}")
    public ResponseEntity<Void> deleteRawMaterial(@PathVariable Long id) {
        log.debug("REST request to delete RawMaterial : {}", id);
        rawMaterialRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
