package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlantFactoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlantFactory.class);
        PlantFactory plantFactory1 = new PlantFactory();
        plantFactory1.setId(1L);
        PlantFactory plantFactory2 = new PlantFactory();
        plantFactory2.setId(plantFactory1.getId());
        assertThat(plantFactory1).isEqualTo(plantFactory2);
        plantFactory2.setId(2L);
        assertThat(plantFactory1).isNotEqualTo(plantFactory2);
        plantFactory1.setId(null);
        assertThat(plantFactory1).isNotEqualTo(plantFactory2);
    }
}
