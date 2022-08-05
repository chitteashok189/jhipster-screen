package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Climate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Climate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClimateRepository extends JpaRepository<Climate, Long> {}
