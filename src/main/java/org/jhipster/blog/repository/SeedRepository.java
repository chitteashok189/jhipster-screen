package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Seed;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Seed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeedRepository extends JpaRepository<Seed, Long> {}
