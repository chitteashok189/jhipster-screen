package org.jhipster.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnnumerationTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnnumerationType.class);
        EnnumerationType ennumerationType1 = new EnnumerationType();
        ennumerationType1.setId(1L);
        EnnumerationType ennumerationType2 = new EnnumerationType();
        ennumerationType2.setId(ennumerationType1.getId());
        assertThat(ennumerationType1).isEqualTo(ennumerationType2);
        ennumerationType2.setId(2L);
        assertThat(ennumerationType1).isNotEqualTo(ennumerationType2);
        ennumerationType1.setId(null);
        assertThat(ennumerationType1).isNotEqualTo(ennumerationType2);
    }
}
