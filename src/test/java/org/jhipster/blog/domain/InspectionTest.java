package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InspectionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inspection.class);
        Inspection inspection1 = new Inspection();
        inspection1.setId(1L);
        Inspection inspection2 = new Inspection();
        inspection2.setId(inspection1.getId());
        assertThat(inspection1).isEqualTo(inspection2);
        inspection2.setId(2L);
        assertThat(inspection1).isNotEqualTo(inspection2);
        inspection1.setId(null);
        assertThat(inspection1).isNotEqualTo(inspection2);
    }
}
