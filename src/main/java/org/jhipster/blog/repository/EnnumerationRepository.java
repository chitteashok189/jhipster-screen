package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Ennumeration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Ennumeration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnnumerationRepository extends JpaRepository<Ennumeration, Long> {}
