package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PestControl;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PestControl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PestControlRepository extends JpaRepository<PestControl, Long> {}
