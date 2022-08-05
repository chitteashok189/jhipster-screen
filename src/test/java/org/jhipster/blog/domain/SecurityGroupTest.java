package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SecurityGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecurityGroup.class);
        SecurityGroup securityGroup1 = new SecurityGroup();
        securityGroup1.setId(1L);
        SecurityGroup securityGroup2 = new SecurityGroup();
        securityGroup2.setId(securityGroup1.getId());
        assertThat(securityGroup1).isEqualTo(securityGroup2);
        securityGroup2.setId(2L);
        assertThat(securityGroup1).isNotEqualTo(securityGroup2);
        securityGroup1.setId(null);
        assertThat(securityGroup1).isNotEqualTo(securityGroup2);
    }
}
