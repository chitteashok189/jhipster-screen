package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartyRelationshipTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyRelationshipType.class);
        PartyRelationshipType partyRelationshipType1 = new PartyRelationshipType();
        partyRelationshipType1.setId(1L);
        PartyRelationshipType partyRelationshipType2 = new PartyRelationshipType();
        partyRelationshipType2.setId(partyRelationshipType1.getId());
        assertThat(partyRelationshipType1).isEqualTo(partyRelationshipType2);
        partyRelationshipType2.setId(2L);
        assertThat(partyRelationshipType1).isNotEqualTo(partyRelationshipType2);
        partyRelationshipType1.setId(null);
        assertThat(partyRelationshipType1).isNotEqualTo(partyRelationshipType2);
    }
}
