package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoleTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleType.class);
        RoleType roleType1 = new RoleType();
        roleType1.setId(1L);
        RoleType roleType2 = new RoleType();
        roleType2.setId(roleType1.getId());
        assertThat(roleType1).isEqualTo(roleType2);
        roleType2.setId(2L);
        assertThat(roleType1).isNotEqualTo(roleType2);
        roleType1.setId(null);
        assertThat(roleType1).isNotEqualTo(roleType2);
    }
}
