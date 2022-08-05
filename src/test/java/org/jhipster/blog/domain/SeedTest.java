package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seed.class);
        Seed seed1 = new Seed();
        seed1.setId(1L);
        Seed seed2 = new Seed();
        seed2.setId(seed1.getId());
        assertThat(seed1).isEqualTo(seed2);
        seed2.setId(2L);
        assertThat(seed1).isNotEqualTo(seed2);
        seed1.setId(null);
        assertThat(seed1).isNotEqualTo(seed2);
    }
}
