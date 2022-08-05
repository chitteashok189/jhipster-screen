package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartyAttributeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyAttribute.class);
        PartyAttribute partyAttribute1 = new PartyAttribute();
        partyAttribute1.setId(1L);
        PartyAttribute partyAttribute2 = new PartyAttribute();
        partyAttribute2.setId(partyAttribute1.getId());
        assertThat(partyAttribute1).isEqualTo(partyAttribute2);
        partyAttribute2.setId(2L);
        assertThat(partyAttribute1).isNotEqualTo(partyAttribute2);
        partyAttribute1.setId(null);
        assertThat(partyAttribute1).isNotEqualTo(partyAttribute2);
    }
}
