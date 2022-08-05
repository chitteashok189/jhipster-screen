package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartyNoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyNote.class);
        PartyNote partyNote1 = new PartyNote();
        partyNote1.setId(1L);
        PartyNote partyNote2 = new PartyNote();
        partyNote2.setId(partyNote1.getId());
        assertThat(partyNote1).isEqualTo(partyNote2);
        partyNote2.setId(2L);
        assertThat(partyNote1).isNotEqualTo(partyNote2);
        partyNote1.setId(null);
        assertThat(partyNote1).isNotEqualTo(partyNote2);
    }
}
