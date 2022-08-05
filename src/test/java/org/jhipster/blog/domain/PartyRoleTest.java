package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartyRoleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyRole.class);
        PartyRole partyRole1 = new PartyRole();
        partyRole1.setId(1L);
        PartyRole partyRole2 = new PartyRole();
        partyRole2.setId(partyRole1.getId());
        assertThat(partyRole1).isEqualTo(partyRole2);
        partyRole2.setId(2L);
        assertThat(partyRole1).isNotEqualTo(partyRole2);
        partyRole1.setId(null);
        assertThat(partyRole1).isNotEqualTo(partyRole2);
    }
}
