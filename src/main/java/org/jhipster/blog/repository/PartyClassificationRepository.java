package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PartyClassification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PartyClassification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyClassificationRepository extends JpaRepository<PartyClassification, Long> {}
