package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FarmTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Farm.class);
        Farm farm1 = new Farm();
        farm1.setId(1L);
        Farm farm2 = new Farm();
        farm2.setId(farm1.getId());
        assertThat(farm1).isEqualTo(farm2);
        farm2.setId(2L);
        assertThat(farm1).isNotEqualTo(farm2);
        farm1.setId(null);
        assertThat(farm1).isNotEqualTo(farm2);
    }
}
