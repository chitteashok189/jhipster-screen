package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.SecurityGroupPermission;
import org.jhipster.blog.repository.SecurityGroupPermissionRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.SecurityGroupPermission}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SecurityGroupPermissionResource {

    private final Logger log = LoggerFactory.getLogger(SecurityGroupPermissionResource.class);

    private static final String ENTITY_NAME = "securityGroupPermission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecurityGroupPermissionRepository securityGroupPermissionRepository;

    public SecurityGroupPermissionResource(SecurityGroupPermissionRepository securityGroupPermissionRepository) {
        this.securityGroupPermissionRepository = securityGroupPermissionRepository;
    }

    /**
     * {@code POST  /security-group-permissions} : Create a new securityGroupPermission.
     *
     * @param securityGroupPermission the securityGroupPermission to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new securityGroupPermission, or with status {@code 400 (Bad Request)} if the securityGroupPermission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/security-group-permissions")
    public ResponseEntity<SecurityGroupPermission> createSecurityGroupPermission(
        @RequestBody SecurityGroupPermission securityGroupPermission
    ) throws URISyntaxException {
        log.debug("REST request to save SecurityGroupPermission : {}", securityGroupPermission);
        if (securityGroupPermission.getId() != null) {
            throw new BadRequestAlertException("A new securityGroupPermission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecurityGroupPermission result = securityGroupPermissionRepository.save(securityGroupPermission);
        return ResponseEntity
            .created(new URI("/api/security-group-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /security-group-permissions/:id} : Updates an existing securityGroupPermission.
     *
     * @param id the id of the securityGroupPermission to save.
     * @param securityGroupPermission the securityGroupPermission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityGroupPermission,
     * or with status {@code 400 (Bad Request)} if the securityGroupPermission is not valid,
     * or with status {@code 500 (Internal Server Error)} if the securityGroupPermission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/security-group-permissions/{id}")
    public ResponseEntity<SecurityGroupPermission> updateSecurityGroupPermission(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SecurityGroupPermission securityGroupPermission
    ) throws URISyntaxException {
        log.debug("REST request to update SecurityGroupPermission : {}, {}", id, securityGroupPermission);
        if (securityGroupPermission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityGroupPermission.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securityGroupPermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SecurityGroupPermission result = securityGroupPermissionRepository.save(securityGroupPermission);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, securityGroupPermission.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /security-group-permissions/:id} : Partial updates given fields of an existing securityGroupPermission, field will ignore if it is null
     *
     * @param id the id of the securityGroupPermission to save.
     * @param securityGroupPermission the securityGroupPermission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityGroupPermission,
     * or with status {@code 400 (Bad Request)} if the securityGroupPermission is not valid,
     * or with status {@code 404 (Not Found)} if the securityGroupPermission is not found,
     * or with status {@code 500 (Internal Server Error)} if the securityGroupPermission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/security-group-permissions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SecurityGroupPermission> partialUpdateSecurityGroupPermission(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SecurityGroupPermission securityGroupPermission
    ) throws URISyntaxException {
        log.debug("REST request to partial update SecurityGroupPermission partially : {}, {}", id, securityGroupPermission);
        if (securityGroupPermission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityGroupPermission.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securityGroupPermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SecurityGroupPermission> result = securityGroupPermissionRepository
            .findById(securityGroupPermission.getId())
            .map(existingSecurityGroupPermission -> {
                if (securityGroupPermission.getgUID() != null) {
                    existingSecurityGroupPermission.setgUID(securityGroupPermission.getgUID());
                }
                if (securityGroupPermission.getCreatedBy() != null) {
                    existingSecurityGroupPermission.setCreatedBy(securityGroupPermission.getCreatedBy());
                }
                if (securityGroupPermission.getCreatedOn() != null) {
                    existingSecurityGroupPermission.setCreatedOn(securityGroupPermission.getCreatedOn());
                }
                if (securityGroupPermission.getUpdatedBy() != null) {
                    existingSecurityGroupPermission.setUpdatedBy(securityGroupPermission.getUpdatedBy());
                }
                if (securityGroupPermission.getUpdatedOn() != null) {
                    existingSecurityGroupPermission.setUpdatedOn(securityGroupPermission.getUpdatedOn());
                }

                return existingSecurityGroupPermission;
            })
            .map(securityGroupPermissionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, securityGroupPermission.getId().toString())
        );
    }

    /**
     * {@code GET  /security-group-permissions} : get all the securityGroupPermissions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of securityGroupPermissions in body.
     */
    @GetMapping("/security-group-permissions")
    public List<SecurityGroupPermission> getAllSecurityGroupPermissions() {
        log.debug("REST request to get all SecurityGroupPermissions");
        return securityGroupPermissionRepository.findAll();
    }

    /**
     * {@code GET  /security-group-permissions/:id} : get the "id" securityGroupPermission.
     *
     * @param id the id of the securityGroupPermission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the securityGroupPermission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/security-group-permissions/{id}")
    public ResponseEntity<SecurityGroupPermission> getSecurityGroupPermission(@PathVariable Long id) {
        log.debug("REST request to get SecurityGroupPermission : {}", id);
        Optional<SecurityGroupPermission> securityGroupPermission = securityGroupPermissionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(securityGroupPermission);
    }

    /**
     * {@code DELETE  /security-group-permissions/:id} : delete the "id" securityGroupPermission.
     *
     * @param id the id of the securityGroupPermission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/security-group-permissions/{id}")
    public ResponseEntity<Void> deleteSecurityGroupPermission(@PathVariable Long id) {
        log.debug("REST request to delete SecurityGroupPermission : {}", id);
        securityGroupPermissionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
