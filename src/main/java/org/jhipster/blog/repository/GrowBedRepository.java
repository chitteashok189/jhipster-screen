package org.jhipster.blog.repository;

import org.jhipster.blog.domain.GrowBed;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GrowBed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrowBedRepository extends JpaRepository<GrowBed, Long> {}
