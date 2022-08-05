package org.jhipster.blog.repository;

import org.jhipster.blog.domain.SecurityGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SecurityGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecurityGroupRepository extends JpaRepository<SecurityGroup, Long> {}
