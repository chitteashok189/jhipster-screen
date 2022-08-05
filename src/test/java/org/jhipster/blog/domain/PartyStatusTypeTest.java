package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartyStatusTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyStatusType.class);
        PartyStatusType partyStatusType1 = new PartyStatusType();
        partyStatusType1.setId(1L);
        PartyStatusType partyStatusType2 = new PartyStatusType();
        partyStatusType2.setId(partyStatusType1.getId());
        assertThat(partyStatusType1).isEqualTo(partyStatusType2);
        partyStatusType2.setId(2L);
        assertThat(partyStatusType1).isNotEqualTo(partyStatusType2);
        partyStatusType1.setId(null);
        assertThat(partyStatusType1).isNotEqualTo(partyStatusType2);
    }
}
