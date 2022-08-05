package org.jhipster.blog.repository;

import org.jhipster.blog.domain.SensorModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SensorModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensorModelRepository extends JpaRepository<SensorModel, Long> {}
