package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HarvestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Harvest.class);
        Harvest harvest1 = new Harvest();
        harvest1.setId(1L);
        Harvest harvest2 = new Harvest();
        harvest2.setId(harvest1.getId());
        assertThat(harvest1).isEqualTo(harvest2);
        harvest2.setId(2L);
        assertThat(harvest1).isNotEqualTo(harvest2);
        harvest1.setId(null);
        assertThat(harvest1).isNotEqualTo(harvest2);
    }
}
