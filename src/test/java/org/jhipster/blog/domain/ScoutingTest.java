package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScoutingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scouting.class);
        Scouting scouting1 = new Scouting();
        scouting1.setId(1L);
        Scouting scouting2 = new Scouting();
        scouting2.setId(scouting1.getId());
        assertThat(scouting1).isEqualTo(scouting2);
        scouting2.setId(2L);
        assertThat(scouting1).isNotEqualTo(scouting2);
        scouting1.setId(null);
        assertThat(scouting1).isNotEqualTo(scouting2);
    }
}
