package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlantFactoryControllerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlantFactoryController.class);
        PlantFactoryController plantFactoryController1 = new PlantFactoryController();
        plantFactoryController1.setId(1L);
        PlantFactoryController plantFactoryController2 = new PlantFactoryController();
        plantFactoryController2.setId(plantFactoryController1.getId());
        assertThat(plantFactoryController1).isEqualTo(plantFactoryController2);
        plantFactoryController2.setId(2L);
        assertThat(plantFactoryController1).isNotEqualTo(plantFactoryController2);
        plantFactoryController1.setId(null);
        assertThat(plantFactoryController1).isNotEqualTo(plantFactoryController2);
    }
}
