package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PartyStatusType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PartyStatusType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyStatusTypeRepository extends JpaRepository<PartyStatusType, Long> {}
