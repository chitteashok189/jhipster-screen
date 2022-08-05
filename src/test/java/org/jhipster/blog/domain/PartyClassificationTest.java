package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartyClassificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyClassification.class);
        PartyClassification partyClassification1 = new PartyClassification();
        partyClassification1.setId(1L);
        PartyClassification partyClassification2 = new PartyClassification();
        partyClassification2.setId(partyClassification1.getId());
        assertThat(partyClassification1).isEqualTo(partyClassification2);
        partyClassification2.setId(2L);
        assertThat(partyClassification1).isNotEqualTo(partyClassification2);
        partyClassification1.setId(null);
        assertThat(partyClassification1).isNotEqualTo(partyClassification2);
    }
}
