package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BreederTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Breeder.class);
        Breeder breeder1 = new Breeder();
        breeder1.setId(1L);
        Breeder breeder2 = new Breeder();
        breeder2.setId(breeder1.getId());
        assertThat(breeder1).isEqualTo(breeder2);
        breeder2.setId(2L);
        assertThat(breeder1).isNotEqualTo(breeder2);
        breeder1.setId(null);
        assertThat(breeder1).isNotEqualTo(breeder2);
    }
}
