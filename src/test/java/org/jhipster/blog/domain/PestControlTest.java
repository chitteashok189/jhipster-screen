package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PestControlTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PestControl.class);
        PestControl pestControl1 = new PestControl();
        pestControl1.setId(1L);
        PestControl pestControl2 = new PestControl();
        pestControl2.setId(pestControl1.getId());
        assertThat(pestControl1).isEqualTo(pestControl2);
        pestControl2.setId(2L);
        assertThat(pestControl1).isNotEqualTo(pestControl2);
        pestControl1.setId(null);
        assertThat(pestControl1).isNotEqualTo(pestControl2);
    }
}
