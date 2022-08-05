package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnnumerationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ennumeration.class);
        Ennumeration ennumeration1 = new Ennumeration();
        ennumeration1.setId(1L);
        Ennumeration ennumeration2 = new Ennumeration();
        ennumeration2.setId(ennumeration1.getId());
        assertThat(ennumeration1).isEqualTo(ennumeration2);
        ennumeration2.setId(2L);
        assertThat(ennumeration1).isNotEqualTo(ennumeration2);
        ennumeration1.setId(null);
        assertThat(ennumeration1).isNotEqualTo(ennumeration2);
    }
}
