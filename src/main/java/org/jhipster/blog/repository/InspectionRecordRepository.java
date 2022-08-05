package org.jhipster.blog.repository;

import org.jhipster.blog.domain.InspectionRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the InspectionRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectionRecordRepository extends JpaRepository<InspectionRecord, Long> {}
