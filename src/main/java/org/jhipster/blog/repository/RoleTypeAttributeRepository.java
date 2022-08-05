package org.jhipster.blog.repository;

import org.jhipster.blog.domain.RoleTypeAttribute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RoleTypeAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleTypeAttributeRepository extends JpaRepository<RoleTypeAttribute, Long> {}
