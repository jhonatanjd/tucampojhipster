package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WayToPayTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WayToPay.class);
        WayToPay wayToPay1 = new WayToPay();
        wayToPay1.setId(1L);
        WayToPay wayToPay2 = new WayToPay();
        wayToPay2.setId(wayToPay1.getId());
        assertThat(wayToPay1).isEqualTo(wayToPay2);
        wayToPay2.setId(2L);
        assertThat(wayToPay1).isNotEqualTo(wayToPay2);
        wayToPay1.setId(null);
        assertThat(wayToPay1).isNotEqualTo(wayToPay2);
    }
}
