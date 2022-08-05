package org.jhipster.blog.repository;

import org.jhipster.blog.domain.DeviceModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DeviceModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long> {}
