package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PartyRelationship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PartyRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyRelationshipRepository extends JpaRepository<PartyRelationship, Long> {}
