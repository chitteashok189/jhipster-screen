package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Dosing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Dosing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DosingRepository extends JpaRepository<Dosing, Long> {}
