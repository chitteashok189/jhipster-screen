package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Disorder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Disorder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisorderRepository extends JpaRepository<Disorder, Long> {}
