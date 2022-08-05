package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DisorderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Disorder.class);
        Disorder disorder1 = new Disorder();
        disorder1.setId(1L);
        Disorder disorder2 = new Disorder();
        disorder2.setId(disorder1.getId());
        assertThat(disorder1).isEqualTo(disorder2);
        disorder2.setId(2L);
        assertThat(disorder1).isNotEqualTo(disorder2);
        disorder1.setId(null);
        assertThat(disorder1).isNotEqualTo(disorder2);
    }
}
