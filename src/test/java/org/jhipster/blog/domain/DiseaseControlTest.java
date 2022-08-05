package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiseaseControlTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiseaseControl.class);
        DiseaseControl diseaseControl1 = new DiseaseControl();
        diseaseControl1.setId(1L);
        DiseaseControl diseaseControl2 = new DiseaseControl();
        diseaseControl2.setId(diseaseControl1.getId());
        assertThat(diseaseControl1).isEqualTo(diseaseControl2);
        diseaseControl2.setId(2L);
        assertThat(diseaseControl1).isNotEqualTo(diseaseControl2);
        diseaseControl1.setId(null);
        assertThat(diseaseControl1).isNotEqualTo(diseaseControl2);
    }
}
