package org.jhipster.blog.repository;

import org.jhipster.blog.domain.DiseaseControl;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DiseaseControl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiseaseControlRepository extends JpaRepository<DiseaseControl, Long> {}
