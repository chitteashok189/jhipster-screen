package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DosingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dosing.class);
        Dosing dosing1 = new Dosing();
        dosing1.setId(1L);
        Dosing dosing2 = new Dosing();
        dosing2.setId(dosing1.getId());
        assertThat(dosing1).isEqualTo(dosing2);
        dosing2.setId(2L);
        assertThat(dosing1).isNotEqualTo(dosing2);
        dosing1.setId(null);
        assertThat(dosing1).isNotEqualTo(dosing2);
    }
}
