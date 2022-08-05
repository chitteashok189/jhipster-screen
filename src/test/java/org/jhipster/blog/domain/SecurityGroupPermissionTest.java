package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SecurityGroupPermissionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecurityGroupPermission.class);
        SecurityGroupPermission securityGroupPermission1 = new SecurityGroupPermission();
        securityGroupPermission1.setId(1L);
        SecurityGroupPermission securityGroupPermission2 = new SecurityGroupPermission();
        securityGroupPermission2.setId(securityGroupPermission1.getId());
        assertThat(securityGroupPermission1).isEqualTo(securityGroupPermission2);
        securityGroupPermission2.setId(2L);
        assertThat(securityGroupPermission1).isNotEqualTo(securityGroupPermission2);
        securityGroupPermission1.setId(null);
        assertThat(securityGroupPermission1).isNotEqualTo(securityGroupPermission2);
    }
}
