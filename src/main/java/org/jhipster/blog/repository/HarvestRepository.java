package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Harvest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Harvest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {}
