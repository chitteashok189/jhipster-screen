package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SensorModelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensorModel.class);
        SensorModel sensorModel1 = new SensorModel();
        sensorModel1.setId(1L);
        SensorModel sensorModel2 = new SensorModel();
        sensorModel2.setId(sensorModel1.getId());
        assertThat(sensorModel1).isEqualTo(sensorModel2);
        sensorModel2.setId(2L);
        assertThat(sensorModel1).isNotEqualTo(sensorModel2);
        sensorModel1.setId(null);
        assertThat(sensorModel1).isNotEqualTo(sensorModel2);
    }
}
