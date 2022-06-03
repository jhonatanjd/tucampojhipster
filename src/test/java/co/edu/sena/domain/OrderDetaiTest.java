package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderDetaiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderDetai.class);
        OrderDetai orderDetai1 = new OrderDetai();
        orderDetai1.setId(1L);
        OrderDetai orderDetai2 = new OrderDetai();
        orderDetai2.setId(orderDetai1.getId());
        assertThat(orderDetai1).isEqualTo(orderDetai2);
        orderDetai2.setId(2L);
        assertThat(orderDetai1).isNotEqualTo(orderDetai2);
        orderDetai1.setId(null);
        assertThat(orderDetai1).isNotEqualTo(orderDetai2);
    }
}
