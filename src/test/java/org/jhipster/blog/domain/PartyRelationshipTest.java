package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartyRelationshipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyRelationship.class);
        PartyRelationship partyRelationship1 = new PartyRelationship();
        partyRelationship1.setId(1L);
        PartyRelationship partyRelationship2 = new PartyRelationship();
        partyRelationship2.setId(partyRelationship1.getId());
        assertThat(partyRelationship1).isEqualTo(partyRelationship2);
        partyRelationship2.setId(2L);
        assertThat(partyRelationship1).isNotEqualTo(partyRelationship2);
        partyRelationship1.setId(null);
        assertThat(partyRelationship1).isNotEqualTo(partyRelationship2);
    }
}
