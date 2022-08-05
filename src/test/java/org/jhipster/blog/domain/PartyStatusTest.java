package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartyStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyStatus.class);
        PartyStatus partyStatus1 = new PartyStatus();
        partyStatus1.setId(1L);
        PartyStatus partyStatus2 = new PartyStatus();
        partyStatus2.setId(partyStatus1.getId());
        assertThat(partyStatus1).isEqualTo(partyStatus2);
        partyStatus2.setId(2L);
        assertThat(partyStatus1).isNotEqualTo(partyStatus2);
        partyStatus1.setId(null);
        assertThat(partyStatus1).isNotEqualTo(partyStatus2);
    }
}
