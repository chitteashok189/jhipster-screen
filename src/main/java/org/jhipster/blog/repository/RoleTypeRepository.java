package org.jhipster.blog.repository;

import org.jhipster.blog.domain.RoleType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RoleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleTypeRepository extends JpaRepository<RoleType, Long> {}
