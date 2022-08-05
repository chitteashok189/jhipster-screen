package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Season;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Season entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {}
