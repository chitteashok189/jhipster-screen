package org.jhipster.blog.repository;

import org.jhipster.blog.domain.Crop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Crop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {}
