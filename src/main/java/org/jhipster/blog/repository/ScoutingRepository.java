package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Scouting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Scouting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScoutingRepository extends JpaRepository<Scouting, Long> {}
