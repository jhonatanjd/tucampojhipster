package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderDetaiDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderDetaiDTO.class);
        OrderDetaiDTO orderDetaiDTO1 = new OrderDetaiDTO();
        orderDetaiDTO1.setId(1L);
        OrderDetaiDTO orderDetaiDTO2 = new OrderDetaiDTO();
        assertThat(orderDetaiDTO1).isNotEqualTo(orderDetaiDTO2);
        orderDetaiDTO2.setId(orderDetaiDTO1.getId());
        assertThat(orderDetaiDTO1).isEqualTo(orderDetaiDTO2);
        orderDetaiDTO2.setId(2L);
        assertThat(orderDetaiDTO1).isNotEqualTo(orderDetaiDTO2);
        orderDetaiDTO1.setId(null);
        assertThat(orderDetaiDTO1).isNotEqualTo(orderDetaiDTO2);
    }
}
