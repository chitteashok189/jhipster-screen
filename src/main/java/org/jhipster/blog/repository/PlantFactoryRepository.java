package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PlantFactory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PlantFactory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlantFactoryRepository extends JpaRepository<PlantFactory, Long> {}
