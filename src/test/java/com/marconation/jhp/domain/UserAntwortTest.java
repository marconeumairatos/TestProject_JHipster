package com.marconation.jhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marconation.jhp.web.rest.TestUtil;

public class UserantwortTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Userantwort.class);
        Userantwort userantwort1 = new Userantwort();
        userantwort1.setId(1L);
        Userantwort userantwort2 = new Userantwort();
        userantwort2.setId(userantwort1.getId());
        assertThat(userantwort1).isEqualTo(userantwort2);
        userantwort2.setId(2L);
        assertThat(userantwort1).isNotEqualTo(userantwort2);
        userantwort1.setId(null);
        assertThat(userantwort1).isNotEqualTo(userantwort2);
    }
}
