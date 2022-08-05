package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.RoleType;
import org.jhipster.blog.repository.RoleTypeRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.RoleType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RoleTypeResource {

    private final Logger log = LoggerFactory.getLogger(RoleTypeResource.class);

    private static final String ENTITY_NAME = "roleType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleTypeRepository roleTypeRepository;

    public RoleTypeResource(RoleTypeRepository roleTypeRepository) {
        this.roleTypeRepository = roleTypeRepository;
    }

    /**
     * {@code POST  /role-types} : Create a new roleType.
     *
     * @param roleType the roleType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleType, or with status {@code 400 (Bad Request)} if the roleType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-types")
    public ResponseEntity<RoleType> createRoleType(@RequestBody RoleType roleType) throws URISyntaxException {
        log.debug("REST request to save RoleType : {}", roleType);
        if (roleType.getId() != null) {
            throw new BadRequestAlertException("A new roleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleType result = roleTypeRepository.save(roleType);
        return ResponseEntity
            .created(new URI("/api/role-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-types/:id} : Updates an existing roleType.
     *
     * @param id the id of the roleType to save.
     * @param roleType the roleType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleType,
     * or with status {@code 400 (Bad Request)} if the roleType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-types/{id}")
    public ResponseEntity<RoleType> updateRoleType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleType roleType
    ) throws URISyntaxException {
        log.debug("REST request to update RoleType : {}, {}", id, roleType);
        if (roleType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoleType result = roleTypeRepository.save(roleType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /role-types/:id} : Partial updates given fields of an existing roleType, field will ignore if it is null
     *
     * @param id the id of the roleType to save.
     * @param roleType the roleType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleType,
     * or with status {@code 400 (Bad Request)} if the roleType is not valid,
     * or with status {@code 404 (Not Found)} if the roleType is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/role-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoleType> partialUpdateRoleType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleType roleType
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoleType partially : {}, {}", id, roleType);
        if (roleType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoleType> result = roleTypeRepository
            .findById(roleType.getId())
            .map(existingRoleType -> {
                if (roleType.getgUID() != null) {
                    existingRoleType.setgUID(roleType.getgUID());
                }
                if (roleType.getHasTable() != null) {
                    existingRoleType.setHasTable(roleType.getHasTable());
                }
                if (roleType.getDescription() != null) {
                    existingRoleType.setDescription(roleType.getDescription());
                }
                if (roleType.getChildRoleType() != null) {
                    existingRoleType.setChildRoleType(roleType.getChildRoleType());
                }
                if (roleType.getValidPartyRelationshipType() != null) {
                    existingRoleType.setValidPartyRelationshipType(roleType.getValidPartyRelationshipType());
                }
                if (roleType.getValidFromPartyRelationshipType() != null) {
                    existingRoleType.setValidFromPartyRelationshipType(roleType.getValidFromPartyRelationshipType());
                }
                if (roleType.getPartyInvitationRoleAssociation() != null) {
                    existingRoleType.setPartyInvitationRoleAssociation(roleType.getPartyInvitationRoleAssociation());
                }
                if (roleType.getCreatedBy() != null) {
                    existingRoleType.setCreatedBy(roleType.getCreatedBy());
                }
                if (roleType.getCreatedOn() != null) {
                    existingRoleType.setCreatedOn(roleType.getCreatedOn());
                }
                if (roleType.getUpdatedBy() != null) {
                    existingRoleType.setUpdatedBy(roleType.getUpdatedBy());
                }
                if (roleType.getUpdatedOn() != null) {
                    existingRoleType.setUpdatedOn(roleType.getUpdatedOn());
                }

                return existingRoleType;
            })
            .map(roleTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleType.getId().toString())
        );
    }

    /**
     * {@code GET  /role-types} : get all the roleTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleTypes in body.
     */
    @GetMapping("/role-types")
    public List<RoleType> getAllRoleTypes() {
        log.debug("REST request to get all RoleTypes");
        return roleTypeRepository.findAll();
    }

    /**
     * {@code GET  /role-types/:id} : get the "id" roleType.
     *
     * @param id the id of the roleType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-types/{id}")
    public ResponseEntity<RoleType> getRoleType(@PathVariable Long id) {
        log.debug("REST request to get RoleType : {}", id);
        Optional<RoleType> roleType = roleTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(roleType);
    }

    /**
     * {@code DELETE  /role-types/:id} : delete the "id" roleType.
     *
     * @param id the id of the roleType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-types/{id}")
    public ResponseEntity<Void> deleteRoleType(@PathVariable Long id) {
        log.debug("REST request to delete RoleType : {}", id);
        roleTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
