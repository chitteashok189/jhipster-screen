package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Nutrient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Nutrient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NutrientRepository extends JpaRepository<Nutrient, Long> {}
