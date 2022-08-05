package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartyTypeAttributeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyTypeAttribute.class);
        PartyTypeAttribute partyTypeAttribute1 = new PartyTypeAttribute();
        partyTypeAttribute1.setId(1L);
        PartyTypeAttribute partyTypeAttribute2 = new PartyTypeAttribute();
        partyTypeAttribute2.setId(partyTypeAttribute1.getId());
        assertThat(partyTypeAttribute1).isEqualTo(partyTypeAttribute2);
        partyTypeAttribute2.setId(2L);
        assertThat(partyTypeAttribute1).isNotEqualTo(partyTypeAttribute2);
        partyTypeAttribute1.setId(null);
        assertThat(partyTypeAttribute1).isNotEqualTo(partyTypeAttribute2);
    }
}
