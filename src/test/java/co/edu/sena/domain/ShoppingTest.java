package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShoppingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shopping.class);
        Shopping shopping1 = new Shopping();
        shopping1.setId(1L);
        Shopping shopping2 = new Shopping();
        shopping2.setId(shopping1.getId());
        assertThat(shopping1).isEqualTo(shopping2);
        shopping2.setId(2L);
        assertThat(shopping1).isNotEqualTo(shopping2);
        shopping1.setId(null);
        assertThat(shopping1).isNotEqualTo(shopping2);
    }
}
