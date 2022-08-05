package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PartyAttribute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PartyAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyAttributeRepository extends JpaRepository<PartyAttribute, Long> {}
