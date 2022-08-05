package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InspectionRecordTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectionRecord.class);
        InspectionRecord inspectionRecord1 = new InspectionRecord();
        inspectionRecord1.setId(1L);
        InspectionRecord inspectionRecord2 = new InspectionRecord();
        inspectionRecord2.setId(inspectionRecord1.getId());
        assertThat(inspectionRecord1).isEqualTo(inspectionRecord2);
        inspectionRecord2.setId(2L);
        assertThat(inspectionRecord1).isNotEqualTo(inspectionRecord2);
        inspectionRecord1.setId(null);
        assertThat(inspectionRecord1).isNotEqualTo(inspectionRecord2);
    }
}
