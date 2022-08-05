package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.RoleTypeAttribute;
import org.jhipster.blog.repository.RoleTypeAttributeRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.RoleTypeAttribute}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RoleTypeAttributeResource {

    private final Logger log = LoggerFactory.getLogger(RoleTypeAttributeResource.class);

    private static final String ENTITY_NAME = "roleTypeAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleTypeAttributeRepository roleTypeAttributeRepository;

    public RoleTypeAttributeResource(RoleTypeAttributeRepository roleTypeAttributeRepository) {
        this.roleTypeAttributeRepository = roleTypeAttributeRepository;
    }

    /**
     * {@code POST  /role-type-attributes} : Create a new roleTypeAttribute.
     *
     * @param roleTypeAttribute the roleTypeAttribute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleTypeAttribute, or with status {@code 400 (Bad Request)} if the roleTypeAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-type-attributes")
    public ResponseEntity<RoleTypeAttribute> createRoleTypeAttribute(@RequestBody RoleTypeAttribute roleTypeAttribute)
        throws URISyntaxException {
        log.debug("REST request to save RoleTypeAttribute : {}", roleTypeAttribute);
        if (roleTypeAttribute.getId() != null) {
            throw new BadRequestAlertException("A new roleTypeAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleTypeAttribute result = roleTypeAttributeRepository.save(roleTypeAttribute);
        return ResponseEntity
            .created(new URI("/api/role-type-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-type-attributes/:id} : Updates an existing roleTypeAttribute.
     *
     * @param id the id of the roleTypeAttribute to save.
     * @param roleTypeAttribute the roleTypeAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleTypeAttribute,
     * or with status {@code 400 (Bad Request)} if the roleTypeAttribute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleTypeAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-type-attributes/{id}")
    public ResponseEntity<RoleTypeAttribute> updateRoleTypeAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleTypeAttribute roleTypeAttribute
    ) throws URISyntaxException {
        log.debug("REST request to update RoleTypeAttribute : {}, {}", id, roleTypeAttribute);
        if (roleTypeAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleTypeAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleTypeAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoleTypeAttribute result = roleTypeAttributeRepository.save(roleTypeAttribute);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleTypeAttribute.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /role-type-attributes/:id} : Partial updates given fields of an existing roleTypeAttribute, field will ignore if it is null
     *
     * @param id the id of the roleTypeAttribute to save.
     * @param roleTypeAttribute the roleTypeAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleTypeAttribute,
     * or with status {@code 400 (Bad Request)} if the roleTypeAttribute is not valid,
     * or with status {@code 404 (Not Found)} if the roleTypeAttribute is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleTypeAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/role-type-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoleTypeAttribute> partialUpdateRoleTypeAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleTypeAttribute roleTypeAttribute
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoleTypeAttribute partially : {}, {}", id, roleTypeAttribute);
        if (roleTypeAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleTypeAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleTypeAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoleTypeAttribute> result = roleTypeAttributeRepository
            .findById(roleTypeAttribute.getId())
            .map(existingRoleTypeAttribute -> {
                if (roleTypeAttribute.getgUID() != null) {
                    existingRoleTypeAttribute.setgUID(roleTypeAttribute.getgUID());
                }
                if (roleTypeAttribute.getAttributeName() != null) {
                    existingRoleTypeAttribute.setAttributeName(roleTypeAttribute.getAttributeName());
                }
                if (roleTypeAttribute.getDescription() != null) {
                    existingRoleTypeAttribute.setDescription(roleTypeAttribute.getDescription());
                }
                if (roleTypeAttribute.getCreatedBy() != null) {
                    existingRoleTypeAttribute.setCreatedBy(roleTypeAttribute.getCreatedBy());
                }
                if (roleTypeAttribute.getCreatedOn() != null) {
                    existingRoleTypeAttribute.setCreatedOn(roleTypeAttribute.getCreatedOn());
                }
                if (roleTypeAttribute.getUpdatedBy() != null) {
                    existingRoleTypeAttribute.setUpdatedBy(roleTypeAttribute.getUpdatedBy());
                }
                if (roleTypeAttribute.getUpdatedOn() != null) {
                    existingRoleTypeAttribute.setUpdatedOn(roleTypeAttribute.getUpdatedOn());
                }

                return existingRoleTypeAttribute;
            })
            .map(roleTypeAttributeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleTypeAttribute.getId().toString())
        );
    }

    /**
     * {@code GET  /role-type-attributes} : get all the roleTypeAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleTypeAttributes in body.
     */
    @GetMapping("/role-type-attributes")
    public List<RoleTypeAttribute> getAllRoleTypeAttributes() {
        log.debug("REST request to get all RoleTypeAttributes");
        return roleTypeAttributeRepository.findAll();
    }

    /**
     * {@code GET  /role-type-attributes/:id} : get the "id" roleTypeAttribute.
     *
     * @param id the id of the roleTypeAttribute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleTypeAttribute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-type-attributes/{id}")
    public ResponseEntity<RoleTypeAttribute> getRoleTypeAttribute(@PathVariable Long id) {
        log.debug("REST request to get RoleTypeAttribute : {}", id);
        Optional<RoleTypeAttribute> roleTypeAttribute = roleTypeAttributeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(roleTypeAttribute);
    }

    /**
     * {@code DELETE  /role-type-attributes/:id} : delete the "id" roleTypeAttribute.
     *
     * @param id the id of the roleTypeAttribute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-type-attributes/{id}")
    public ResponseEntity<Void> deleteRoleTypeAttribute(@PathVariable Long id) {
        log.debug("REST request to delete RoleTypeAttribute : {}", id);
        roleTypeAttributeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
