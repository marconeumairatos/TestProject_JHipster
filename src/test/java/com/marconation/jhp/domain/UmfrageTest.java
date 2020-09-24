package com.marconation.jhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marconation.jhp.web.rest.TestUtil;

public class UmfrageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Umfrage.class);
        Umfrage umfrage1 = new Umfrage();
        umfrage1.setId(1L);
        Umfrage umfrage2 = new Umfrage();
        umfrage2.setId(umfrage1.getId());
        assertThat(umfrage1).isEqualTo(umfrage2);
        umfrage2.setId(2L);
        assertThat(umfrage1).isNotEqualTo(umfrage2);
        umfrage1.setId(null);
        assertThat(umfrage1).isNotEqualTo(umfrage2);
    }
}
