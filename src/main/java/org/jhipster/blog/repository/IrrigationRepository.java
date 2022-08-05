package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Irrigation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Irrigation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IrrigationRepository extends JpaRepository<Irrigation, Long> {}
