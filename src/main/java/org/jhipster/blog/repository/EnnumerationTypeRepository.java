package org.jhipster.blog.repository;

import org.jhipster.blog.domain.EnnumerationType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EnnumerationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnnumerationTypeRepository extends JpaRepository<EnnumerationType, Long> {}
