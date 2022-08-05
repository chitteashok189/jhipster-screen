package org.jhipster.blog.repository;

import org.jhipster.blog.domain.ApplicationUserSecurityGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApplicationUserSecurityGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationUserSecurityGroupRepository extends JpaRepository<ApplicationUserSecurityGroup, Long> {}
