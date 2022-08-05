package org.jhipster.blog.repository;

import org.jhipster.blog.domain.RawMaterial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RawMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {}
