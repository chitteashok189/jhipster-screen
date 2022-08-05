package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeviceLevelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceLevel.class);
        DeviceLevel deviceLevel1 = new DeviceLevel();
        deviceLevel1.setId(1L);
        DeviceLevel deviceLevel2 = new DeviceLevel();
        deviceLevel2.setId(deviceLevel1.getId());
        assertThat(deviceLevel1).isEqualTo(deviceLevel2);
        deviceLevel2.setId(2L);
        assertThat(deviceLevel1).isNotEqualTo(deviceLevel2);
        deviceLevel1.setId(null);
        assertThat(deviceLevel1).isNotEqualTo(deviceLevel2);
    }
}
