package org.jhipster.blog.repository;

import org.jhipster.blog.domain.SecurityPermission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SecurityPermission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecurityPermissionRepository extends JpaRepository<SecurityPermission, Long> {}
