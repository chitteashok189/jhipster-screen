package org.jhipster.blog.repository;

import org.jhipster.blog.domain.SecurityGroupPermission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SecurityGroupPermission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecurityGroupPermissionRepository extends JpaRepository<SecurityGroupPermission, Long> {}
