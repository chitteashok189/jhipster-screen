package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Light;
import org.jhipster.blog.repository.LightRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Light}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LightResource {

    private final Logger log = LoggerFactory.getLogger(LightResource.class);

    private static final String ENTITY_NAME = "light";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LightRepository lightRepository;

    public LightResource(LightRepository lightRepository) {
        this.lightRepository = lightRepository;
    }

    /**
     * {@code POST  /lights} : Create a new light.
     *
     * @param light the light to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new light, or with status {@code 400 (Bad Request)} if the light has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lights")
    public ResponseEntity<Light> createLight(@RequestBody Light light) throws URISyntaxException {
        log.debug("REST request to save Light : {}", light);
        if (light.getId() != null) {
            throw new BadRequestAlertException("A new light cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Light result = lightRepository.save(light);
        return ResponseEntity
            .created(new URI("/api/lights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lights/:id} : Updates an existing light.
     *
     * @param id the id of the light to save.
     * @param light the light to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated light,
     * or with status {@code 400 (Bad Request)} if the light is not valid,
     * or with status {@code 500 (Internal Server Error)} if the light couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lights/{id}")
    public ResponseEntity<Light> updateLight(@PathVariable(value = "id", required = false) final Long id, @RequestBody Light light)
        throws URISyntaxException {
        log.debug("REST request to update Light : {}, {}", id, light);
        if (light.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, light.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lightRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Light result = lightRepository.save(light);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, light.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lights/:id} : Partial updates given fields of an existing light, field will ignore if it is null
     *
     * @param id the id of the light to save.
     * @param light the light to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated light,
     * or with status {@code 400 (Bad Request)} if the light is not valid,
     * or with status {@code 404 (Not Found)} if the light is not found,
     * or with status {@code 500 (Internal Server Error)} if the light couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lights/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Light> partialUpdateLight(@PathVariable(value = "id", required = false) final Long id, @RequestBody Light light)
        throws URISyntaxException {
        log.debug("REST request to partial update Light partially : {}, {}", id, light);
        if (light.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, light.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lightRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Light> result = lightRepository
            .findById(light.getId())
            .map(existingLight -> {
                if (light.getgUID() != null) {
                    existingLight.setgUID(light.getgUID());
                }
                if (light.getSource() != null) {
                    existingLight.setSource(light.getSource());
                }
                if (light.getLightIntensity() != null) {
                    existingLight.setLightIntensity(light.getLightIntensity());
                }
                if (light.getDailyLightIntegral() != null) {
                    existingLight.setDailyLightIntegral(light.getDailyLightIntegral());
                }
                if (light.getpAR() != null) {
                    existingLight.setpAR(light.getpAR());
                }
                if (light.getpPFD() != null) {
                    existingLight.setpPFD(light.getpPFD());
                }
                if (light.getCreatedBy() != null) {
                    existingLight.setCreatedBy(light.getCreatedBy());
                }
                if (light.getCreatedOn() != null) {
                    existingLight.setCreatedOn(light.getCreatedOn());
                }
                if (light.getUpdatedBy() != null) {
                    existingLight.setUpdatedBy(light.getUpdatedBy());
                }
                if (light.getUpdatedOn() != null) {
                    existingLight.setUpdatedOn(light.getUpdatedOn());
                }

                return existingLight;
            })
            .map(lightRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, light.getId().toString())
        );
    }

    /**
     * {@code GET  /lights} : get all the lights.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lights in body.
     */
    @GetMapping("/lights")
    public List<Light> getAllLights() {
        log.debug("REST request to get all Lights");
        return lightRepository.findAll();
    }

    /**
     * {@code GET  /lights/:id} : get the "id" light.
     *
     * @param id the id of the light to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the light, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lights/{id}")
    public ResponseEntity<Light> getLight(@PathVariable Long id) {
        log.debug("REST request to get Light : {}", id);
        Optional<Light> light = lightRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(light);
    }

    /**
     * {@code DELETE  /lights/:id} : delete the "id" light.
     *
     * @param id the id of the light to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lights/{id}")
    public ResponseEntity<Void> deleteLight(@PathVariable Long id) {
        log.debug("REST request to delete Light : {}", id);
        lightRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
