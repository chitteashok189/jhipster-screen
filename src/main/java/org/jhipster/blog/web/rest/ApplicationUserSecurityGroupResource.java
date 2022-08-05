package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.ApplicationUserSecurityGroup;
import org.jhipster.blog.repository.ApplicationUserSecurityGroupRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.ApplicationUserSecurityGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ApplicationUserSecurityGroupResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationUserSecurityGroupResource.class);

    private static final String ENTITY_NAME = "applicationUserSecurityGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationUserSecurityGroupRepository applicationUserSecurityGroupRepository;

    public ApplicationUserSecurityGroupResource(ApplicationUserSecurityGroupRepository applicationUserSecurityGroupRepository) {
        this.applicationUserSecurityGroupRepository = applicationUserSecurityGroupRepository;
    }

    /**
     * {@code POST  /application-user-security-groups} : Create a new applicationUserSecurityGroup.
     *
     * @param applicationUserSecurityGroup the applicationUserSecurityGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationUserSecurityGroup, or with status {@code 400 (Bad Request)} if the applicationUserSecurityGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-user-security-groups")
    public ResponseEntity<ApplicationUserSecurityGroup> createApplicationUserSecurityGroup(
        @RequestBody ApplicationUserSecurityGroup applicationUserSecurityGroup
    ) throws URISyntaxException {
        log.debug("REST request to save ApplicationUserSecurityGroup : {}", applicationUserSecurityGroup);
        if (applicationUserSecurityGroup.getId() != null) {
            throw new BadRequestAlertException("A new applicationUserSecurityGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationUserSecurityGroup result = applicationUserSecurityGroupRepository.save(applicationUserSecurityGroup);
        return ResponseEntity
            .created(new URI("/api/application-user-security-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-user-security-groups/:id} : Updates an existing applicationUserSecurityGroup.
     *
     * @param id the id of the applicationUserSecurityGroup to save.
     * @param applicationUserSecurityGroup the applicationUserSecurityGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationUserSecurityGroup,
     * or with status {@code 400 (Bad Request)} if the applicationUserSecurityGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationUserSecurityGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-user-security-groups/{id}")
    public ResponseEntity<ApplicationUserSecurityGroup> updateApplicationUserSecurityGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicationUserSecurityGroup applicationUserSecurityGroup
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicationUserSecurityGroup : {}, {}", id, applicationUserSecurityGroup);
        if (applicationUserSecurityGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationUserSecurityGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationUserSecurityGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicationUserSecurityGroup result = applicationUserSecurityGroupRepository.save(applicationUserSecurityGroup);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationUserSecurityGroup.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /application-user-security-groups/:id} : Partial updates given fields of an existing applicationUserSecurityGroup, field will ignore if it is null
     *
     * @param id the id of the applicationUserSecurityGroup to save.
     * @param applicationUserSecurityGroup the applicationUserSecurityGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationUserSecurityGroup,
     * or with status {@code 400 (Bad Request)} if the applicationUserSecurityGroup is not valid,
     * or with status {@code 404 (Not Found)} if the applicationUserSecurityGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicationUserSecurityGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/application-user-security-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicationUserSecurityGroup> partialUpdateApplicationUserSecurityGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicationUserSecurityGroup applicationUserSecurityGroup
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicationUserSecurityGroup partially : {}, {}", id, applicationUserSecurityGroup);
        if (applicationUserSecurityGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationUserSecurityGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationUserSecurityGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicationUserSecurityGroup> result = applicationUserSecurityGroupRepository
            .findById(applicationUserSecurityGroup.getId())
            .map(existingApplicationUserSecurityGroup -> {
                if (applicationUserSecurityGroup.getgUID() != null) {
                    existingApplicationUserSecurityGroup.setgUID(applicationUserSecurityGroup.getgUID());
                }
                if (applicationUserSecurityGroup.getFromDate() != null) {
                    existingApplicationUserSecurityGroup.setFromDate(applicationUserSecurityGroup.getFromDate());
                }
                if (applicationUserSecurityGroup.getThruDate() != null) {
                    existingApplicationUserSecurityGroup.setThruDate(applicationUserSecurityGroup.getThruDate());
                }
                if (applicationUserSecurityGroup.getCreatedBy() != null) {
                    existingApplicationUserSecurityGroup.setCreatedBy(applicationUserSecurityGroup.getCreatedBy());
                }
                if (applicationUserSecurityGroup.getCreatedOn() != null) {
                    existingApplicationUserSecurityGroup.setCreatedOn(applicationUserSecurityGroup.getCreatedOn());
                }
                if (applicationUserSecurityGroup.getUpdatedBy() != null) {
                    existingApplicationUserSecurityGroup.setUpdatedBy(applicationUserSecurityGroup.getUpdatedBy());
                }
                if (applicationUserSecurityGroup.getUpdatedOn() != null) {
                    existingApplicationUserSecurityGroup.setUpdatedOn(applicationUserSecurityGroup.getUpdatedOn());
                }

                return existingApplicationUserSecurityGroup;
            })
            .map(applicationUserSecurityGroupRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationUserSecurityGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /application-user-security-groups} : get all the applicationUserSecurityGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationUserSecurityGroups in body.
     */
    @GetMapping("/application-user-security-groups")
    public List<ApplicationUserSecurityGroup> getAllApplicationUserSecurityGroups() {
        log.debug("REST request to get all ApplicationUserSecurityGroups");
        return applicationUserSecurityGroupRepository.findAll();
    }

    /**
     * {@code GET  /application-user-security-groups/:id} : get the "id" applicationUserSecurityGroup.
     *
     * @param id the id of the applicationUserSecurityGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationUserSecurityGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-user-security-groups/{id}")
    public ResponseEntity<ApplicationUserSecurityGroup> getApplicationUserSecurityGroup(@PathVariable Long id) {
        log.debug("REST request to get ApplicationUserSecurityGroup : {}", id);
        Optional<ApplicationUserSecurityGroup> applicationUserSecurityGroup = applicationUserSecurityGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(applicationUserSecurityGroup);
    }

    /**
     * {@code DELETE  /application-user-security-groups/:id} : delete the "id" applicationUserSecurityGroup.
     *
     * @param id the id of the applicationUserSecurityGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-user-security-groups/{id}")
    public ResponseEntity<Void> deleteApplicationUserSecurityGroup(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationUserSecurityGroup : {}", id);
        applicationUserSecurityGroupRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
