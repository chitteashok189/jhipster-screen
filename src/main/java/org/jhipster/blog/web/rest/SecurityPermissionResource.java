package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.SecurityPermission;
import org.jhipster.blog.repository.SecurityPermissionRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.SecurityPermission}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SecurityPermissionResource {

    private final Logger log = LoggerFactory.getLogger(SecurityPermissionResource.class);

    private static final String ENTITY_NAME = "securityPermission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecurityPermissionRepository securityPermissionRepository;

    public SecurityPermissionResource(SecurityPermissionRepository securityPermissionRepository) {
        this.securityPermissionRepository = securityPermissionRepository;
    }

    /**
     * {@code POST  /security-permissions} : Create a new securityPermission.
     *
     * @param securityPermission the securityPermission to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new securityPermission, or with status {@code 400 (Bad Request)} if the securityPermission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/security-permissions")
    public ResponseEntity<SecurityPermission> createSecurityPermission(@RequestBody SecurityPermission securityPermission)
        throws URISyntaxException {
        log.debug("REST request to save SecurityPermission : {}", securityPermission);
        if (securityPermission.getId() != null) {
            throw new BadRequestAlertException("A new securityPermission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecurityPermission result = securityPermissionRepository.save(securityPermission);
        return ResponseEntity
            .created(new URI("/api/security-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /security-permissions/:id} : Updates an existing securityPermission.
     *
     * @param id the id of the securityPermission to save.
     * @param securityPermission the securityPermission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityPermission,
     * or with status {@code 400 (Bad Request)} if the securityPermission is not valid,
     * or with status {@code 500 (Internal Server Error)} if the securityPermission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/security-permissions/{id}")
    public ResponseEntity<SecurityPermission> updateSecurityPermission(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SecurityPermission securityPermission
    ) throws URISyntaxException {
        log.debug("REST request to update SecurityPermission : {}, {}", id, securityPermission);
        if (securityPermission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityPermission.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securityPermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SecurityPermission result = securityPermissionRepository.save(securityPermission);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, securityPermission.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /security-permissions/:id} : Partial updates given fields of an existing securityPermission, field will ignore if it is null
     *
     * @param id the id of the securityPermission to save.
     * @param securityPermission the securityPermission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityPermission,
     * or with status {@code 400 (Bad Request)} if the securityPermission is not valid,
     * or with status {@code 404 (Not Found)} if the securityPermission is not found,
     * or with status {@code 500 (Internal Server Error)} if the securityPermission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/security-permissions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SecurityPermission> partialUpdateSecurityPermission(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SecurityPermission securityPermission
    ) throws URISyntaxException {
        log.debug("REST request to partial update SecurityPermission partially : {}, {}", id, securityPermission);
        if (securityPermission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityPermission.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securityPermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SecurityPermission> result = securityPermissionRepository
            .findById(securityPermission.getId())
            .map(existingSecurityPermission -> {
                if (securityPermission.getgUID() != null) {
                    existingSecurityPermission.setgUID(securityPermission.getgUID());
                }
                if (securityPermission.getDescription() != null) {
                    existingSecurityPermission.setDescription(securityPermission.getDescription());
                }
                if (securityPermission.getCreatedBy() != null) {
                    existingSecurityPermission.setCreatedBy(securityPermission.getCreatedBy());
                }
                if (securityPermission.getCreatedOn() != null) {
                    existingSecurityPermission.setCreatedOn(securityPermission.getCreatedOn());
                }
                if (securityPermission.getUpdatedBy() != null) {
                    existingSecurityPermission.setUpdatedBy(securityPermission.getUpdatedBy());
                }
                if (securityPermission.getUpdatedOn() != null) {
                    existingSecurityPermission.setUpdatedOn(securityPermission.getUpdatedOn());
                }

                return existingSecurityPermission;
            })
            .map(securityPermissionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, securityPermission.getId().toString())
        );
    }

    /**
     * {@code GET  /security-permissions} : get all the securityPermissions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of securityPermissions in body.
     */
    @GetMapping("/security-permissions")
    public List<SecurityPermission> getAllSecurityPermissions() {
        log.debug("REST request to get all SecurityPermissions");
        return securityPermissionRepository.findAll();
    }

    /**
     * {@code GET  /security-permissions/:id} : get the "id" securityPermission.
     *
     * @param id the id of the securityPermission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the securityPermission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/security-permissions/{id}")
    public ResponseEntity<SecurityPermission> getSecurityPermission(@PathVariable Long id) {
        log.debug("REST request to get SecurityPermission : {}", id);
        Optional<SecurityPermission> securityPermission = securityPermissionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(securityPermission);
    }

    /**
     * {@code DELETE  /security-permissions/:id} : delete the "id" securityPermission.
     *
     * @param id the id of the securityPermission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/security-permissions/{id}")
    public ResponseEntity<Void> deleteSecurityPermission(@PathVariable Long id) {
        log.debug("REST request to delete SecurityPermission : {}", id);
        securityPermissionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
