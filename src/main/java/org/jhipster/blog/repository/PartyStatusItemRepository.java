package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PartyStatusItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PartyStatusItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyStatusItemRepository extends JpaRepository<PartyStatusItem, Long> {}
