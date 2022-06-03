package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dats.class);
        Dats dats1 = new Dats();
        dats1.setId(1L);
        Dats dats2 = new Dats();
        dats2.setId(dats1.getId());
        assertThat(dats1).isEqualTo(dats2);
        dats2.setId(2L);
        assertThat(dats1).isNotEqualTo(dats2);
        dats1.setId(null);
        assertThat(dats1).isNotEqualTo(dats2);
    }
}
