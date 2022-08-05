package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Breeder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Breeder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BreederRepository extends JpaRepository<Breeder, Long> {}
