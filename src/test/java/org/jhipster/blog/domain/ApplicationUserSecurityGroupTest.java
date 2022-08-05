package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationUserSecurityGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationUserSecurityGroup.class);
        ApplicationUserSecurityGroup applicationUserSecurityGroup1 = new ApplicationUserSecurityGroup();
        applicationUserSecurityGroup1.setId(1L);
        ApplicationUserSecurityGroup applicationUserSecurityGroup2 = new ApplicationUserSecurityGroup();
        applicationUserSecurityGroup2.setId(applicationUserSecurityGroup1.getId());
        assertThat(applicationUserSecurityGroup1).isEqualTo(applicationUserSecurityGroup2);
        applicationUserSecurityGroup2.setId(2L);
        assertThat(applicationUserSecurityGroup1).isNotEqualTo(applicationUserSecurityGroup2);
        applicationUserSecurityGroup1.setId(null);
        assertThat(applicationUserSecurityGroup1).isNotEqualTo(applicationUserSecurityGroup2);
    }
}
