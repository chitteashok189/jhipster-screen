package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GrowBedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrowBed.class);
        GrowBed growBed1 = new GrowBed();
        growBed1.setId(1L);
        GrowBed growBed2 = new GrowBed();
        growBed2.setId(growBed1.getId());
        assertThat(growBed1).isEqualTo(growBed2);
        growBed2.setId(2L);
        assertThat(growBed1).isNotEqualTo(growBed2);
        growBed1.setId(null);
        assertThat(growBed1).isNotEqualTo(growBed2);
    }
}
