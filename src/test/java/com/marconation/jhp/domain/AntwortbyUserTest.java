package com.marconation.jhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marconation.jhp.web.rest.TestUtil;

public class AntwortbyUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AntwortbyUser.class);
        AntwortbyUser antwortbyUser1 = new AntwortbyUser();
        antwortbyUser1.setId(1L);
        AntwortbyUser antwortbyUser2 = new AntwortbyUser();
        antwortbyUser2.setId(antwortbyUser1.getId());
        assertThat(antwortbyUser1).isEqualTo(antwortbyUser2);
        antwortbyUser2.setId(2L);
        assertThat(antwortbyUser1).isNotEqualTo(antwortbyUser2);
        antwortbyUser1.setId(null);
        assertThat(antwortbyUser1).isNotEqualTo(antwortbyUser2);
    }
}
