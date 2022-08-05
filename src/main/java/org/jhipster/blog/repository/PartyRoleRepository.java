package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PartyRole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PartyRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyRoleRepository extends JpaRepository<PartyRole, Long> {}
