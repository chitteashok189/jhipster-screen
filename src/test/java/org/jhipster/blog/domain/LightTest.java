package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LightTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Light.class);
        Light light1 = new Light();
        light1.setId(1L);
        Light light2 = new Light();
        light2.setId(light1.getId());
        assertThat(light1).isEqualTo(light2);
        light2.setId(2L);
        assertThat(light1).isNotEqualTo(light2);
        light1.setId(null);
        assertThat(light1).isNotEqualTo(light2);
    }
}
