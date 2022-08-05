package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoleTypeAttributeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleTypeAttribute.class);
        RoleTypeAttribute roleTypeAttribute1 = new RoleTypeAttribute();
        roleTypeAttribute1.setId(1L);
        RoleTypeAttribute roleTypeAttribute2 = new RoleTypeAttribute();
        roleTypeAttribute2.setId(roleTypeAttribute1.getId());
        assertThat(roleTypeAttribute1).isEqualTo(roleTypeAttribute2);
        roleTypeAttribute2.setId(2L);
        assertThat(roleTypeAttribute1).isNotEqualTo(roleTypeAttribute2);
        roleTypeAttribute1.setId(null);
        assertThat(roleTypeAttribute1).isNotEqualTo(roleTypeAttribute2);
    }
}
