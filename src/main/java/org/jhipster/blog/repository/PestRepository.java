package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Pest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Pest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PestRepository extends JpaRepository<Pest, Long> {}
