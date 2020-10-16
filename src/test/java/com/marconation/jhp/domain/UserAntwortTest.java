package com.marconation.jhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marconation.jhp.web.rest.TestUtil;

public class UserAntwortTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAntwort.class);
        UserAntwort userAntwort1 = new UserAntwort();
        userAntwort1.setId(1L);
        UserAntwort userAntwort2 = new UserAntwort();
        userAntwort2.setId(userAntwort1.getId());
        assertThat(userAntwort1).isEqualTo(userAntwort2);
        userAntwort2.setId(2L);
        assertThat(userAntwort1).isNotEqualTo(userAntwort2);
        userAntwort1.setId(null);
        assertThat(userAntwort1).isNotEqualTo(userAntwort2);
    }
}
