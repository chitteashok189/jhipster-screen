package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClimateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Climate.class);
        Climate climate1 = new Climate();
        climate1.setId(1L);
        Climate climate2 = new Climate();
        climate2.setId(climate1.getId());
        assertThat(climate1).isEqualTo(climate2);
        climate2.setId(2L);
        assertThat(climate1).isNotEqualTo(climate2);
        climate1.setId(null);
        assertThat(climate1).isNotEqualTo(climate2);
    }
}
