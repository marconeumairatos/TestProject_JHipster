package com.marconation.jhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marconation.jhp.web.rest.TestUtil;

public class AntwortTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Antwort.class);
        Antwort antwort1 = new Antwort();
        antwort1.setId(1L);
        Antwort antwort2 = new Antwort();
        antwort2.setId(antwort1.getId());
        assertThat(antwort1).isEqualTo(antwort2);
        antwort2.setId(2L);
        assertThat(antwort1).isNotEqualTo(antwort2);
        antwort1.setId(null);
        assertThat(antwort1).isNotEqualTo(antwort2);
    }
}
