package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PartyGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PartyGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyGroupRepository extends JpaRepository<PartyGroup, Long> {}
