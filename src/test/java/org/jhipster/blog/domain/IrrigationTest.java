package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IrrigationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Irrigation.class);
        Irrigation irrigation1 = new Irrigation();
        irrigation1.setId(1L);
        Irrigation irrigation2 = new Irrigation();
        irrigation2.setId(irrigation1.getId());
        assertThat(irrigation1).isEqualTo(irrigation2);
        irrigation2.setId(2L);
        assertThat(irrigation1).isNotEqualTo(irrigation2);
        irrigation1.setId(null);
        assertThat(irrigation1).isNotEqualTo(irrigation2);
    }
}
