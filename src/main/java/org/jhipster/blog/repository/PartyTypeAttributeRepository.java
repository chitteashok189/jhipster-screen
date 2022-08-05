package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PartyTypeAttribute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PartyTypeAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyTypeAttributeRepository extends JpaRepository<PartyTypeAttribute, Long> {}
