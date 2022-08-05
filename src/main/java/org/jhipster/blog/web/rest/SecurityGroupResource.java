package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.SecurityGroup;
import org.jhipster.blog.repository.SecurityGroupRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.SecurityGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SecurityGroupResource {

    private final Logger log = LoggerFactory.getLogger(SecurityGroupResource.class);

    private static final String ENTITY_NAME = "securityGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecurityGroupRepository securityGroupRepository;

    public SecurityGroupResource(SecurityGroupRepository securityGroupRepository) {
        this.securityGroupRepository = securityGroupRepository;
    }

    /**
     * {@code POST  /security-groups} : Create a new securityGroup.
     *
     * @param securityGroup the securityGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new securityGroup, or with status {@code 400 (Bad Request)} if the securityGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/security-groups")
    public ResponseEntity<SecurityGroup> createSecurityGroup(@RequestBody SecurityGroup securityGroup) throws URISyntaxException {
        log.debug("REST request to save SecurityGroup : {}", securityGroup);
        if (securityGroup.getId() != null) {
            throw new BadRequestAlertException("A new securityGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecurityGroup result = securityGroupRepository.save(securityGroup);
        return ResponseEntity
            .created(new URI("/api/security-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /security-groups/:id} : Updates an existing securityGroup.
     *
     * @param id the id of the securityGroup to save.
     * @param securityGroup the securityGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityGroup,
     * or with status {@code 400 (Bad Request)} if the securityGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the securityGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/security-groups/{id}")
    public ResponseEntity<SecurityGroup> updateSecurityGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SecurityGroup securityGroup
    ) throws URISyntaxException {
        log.debug("REST request to update SecurityGroup : {}, {}", id, securityGroup);
        if (securityGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securityGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SecurityGroup result = securityGroupRepository.save(securityGroup);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, securityGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /security-groups/:id} : Partial updates given fields of an existing securityGroup, field will ignore if it is null
     *
     * @param id the id of the securityGroup to save.
     * @param securityGroup the securityGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityGroup,
     * or with status {@code 400 (Bad Request)} if the securityGroup is not valid,
     * or with status {@code 404 (Not Found)} if the securityGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the securityGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/security-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SecurityGroup> partialUpdateSecurityGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SecurityGroup securityGroup
    ) throws URISyntaxException {
        log.debug("REST request to partial update SecurityGroup partially : {}, {}", id, securityGroup);
        if (securityGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securityGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SecurityGroup> result = securityGroupRepository
            .findById(securityGroup.getId())
            .map(existingSecurityGroup -> {
                if (securityGroup.getgUID() != null) {
                    existingSecurityGroup.setgUID(securityGroup.getgUID());
                }
                if (securityGroup.getDescription() != null) {
                    existingSecurityGroup.setDescription(securityGroup.getDescription());
                }
                if (securityGroup.getCreatedBy() != null) {
                    existingSecurityGroup.setCreatedBy(securityGroup.getCreatedBy());
                }
                if (securityGroup.getCreatedOn() != null) {
                    existingSecurityGroup.setCreatedOn(securityGroup.getCreatedOn());
                }
                if (securityGroup.getUpdatedBy() != null) {
                    existingSecurityGroup.setUpdatedBy(securityGroup.getUpdatedBy());
                }
                if (securityGroup.getUpdatedOn() != null) {
                    existingSecurityGroup.setUpdatedOn(securityGroup.getUpdatedOn());
                }

                return existingSecurityGroup;
            })
            .map(securityGroupRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, securityGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /security-groups} : get all the securityGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of securityGroups in body.
     */
    @GetMapping("/security-groups")
    public List<SecurityGroup> getAllSecurityGroups() {
        log.debug("REST request to get all SecurityGroups");
        return securityGroupRepository.findAll();
    }

    /**
     * {@code GET  /security-groups/:id} : get the "id" securityGroup.
     *
     * @param id the id of the securityGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the securityGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/security-groups/{id}")
    public ResponseEntity<SecurityGroup> getSecurityGroup(@PathVariable Long id) {
        log.debug("REST request to get SecurityGroup : {}", id);
        Optional<SecurityGroup> securityGroup = securityGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(securityGroup);
    }

    /**
     * {@code DELETE  /security-groups/:id} : delete the "id" securityGroup.
     *
     * @param id the id of the securityGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/security-groups/{id}")
    public ResponseEntity<Void> deleteSecurityGroup(@PathVariable Long id) {
        log.debug("REST request to delete SecurityGroup : {}", id);
        securityGroupRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
