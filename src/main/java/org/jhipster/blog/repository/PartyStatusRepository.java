package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PartyStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PartyStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyStatusRepository extends JpaRepository<PartyStatus, Long> {}
