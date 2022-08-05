package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PlantFactoryController;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PlantFactoryController entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlantFactoryControllerRepository extends JpaRepository<PlantFactoryController, Long> {}
