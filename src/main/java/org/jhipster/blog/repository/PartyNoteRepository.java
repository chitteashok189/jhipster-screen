package org.jhipster.blog.repository;

import org.jhipster.blog.domain.PartyNote;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PartyNote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyNoteRepository extends JpaRepository<PartyNote, Long> {}
