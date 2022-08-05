package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NutrientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nutrient.class);
        Nutrient nutrient1 = new Nutrient();
        nutrient1.setId(1L);
        Nutrient nutrient2 = new Nutrient();
        nutrient2.setId(nutrient1.getId());
        assertThat(nutrient1).isEqualTo(nutrient2);
        nutrient2.setId(2L);
        assertThat(nutrient1).isNotEqualTo(nutrient2);
        nutrient1.setId(null);
        assertThat(nutrient1).isNotEqualTo(nutrient2);
    }
}
