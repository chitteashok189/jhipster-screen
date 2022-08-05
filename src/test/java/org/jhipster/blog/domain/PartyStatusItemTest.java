package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartyStatusItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyStatusItem.class);
        PartyStatusItem partyStatusItem1 = new PartyStatusItem();
        partyStatusItem1.setId(1L);
        PartyStatusItem partyStatusItem2 = new PartyStatusItem();
        partyStatusItem2.setId(partyStatusItem1.getId());
        assertThat(partyStatusItem1).isEqualTo(partyStatusItem2);
        partyStatusItem2.setId(2L);
        assertThat(partyStatusItem1).isNotEqualTo(partyStatusItem2);
        partyStatusItem1.setId(null);
        assertThat(partyStatusItem1).isNotEqualTo(partyStatusItem2);
    }
}
