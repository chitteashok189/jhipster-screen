package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PartyRelationshipType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PartyRelationshipType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyRelationshipTypeRepository extends JpaRepository<PartyRelationshipType, Long> {}
