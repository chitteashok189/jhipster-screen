package org.jhipster.blog.repository;

import org.jhipster.blog.domain.DeviceLevel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DeviceLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceLevelRepository extends JpaRepository<DeviceLevel, Long> {}
