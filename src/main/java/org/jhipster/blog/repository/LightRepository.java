package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Light;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Light entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LightRepository extends JpaRepository<Light, Long> {}
