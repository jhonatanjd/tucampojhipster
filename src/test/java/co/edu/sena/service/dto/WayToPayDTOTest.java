package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WayToPayDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WayToPayDTO.class);
        WayToPayDTO wayToPayDTO1 = new WayToPayDTO();
        wayToPayDTO1.setId(1L);
        WayToPayDTO wayToPayDTO2 = new WayToPayDTO();
        assertThat(wayToPayDTO1).isNotEqualTo(wayToPayDTO2);
        wayToPayDTO2.setId(wayToPayDTO1.getId());
        assertThat(wayToPayDTO1).isEqualTo(wayToPayDTO2);
        wayToPayDTO2.setId(2L);
        assertThat(wayToPayDTO1).isNotEqualTo(wayToPayDTO2);
        wayToPayDTO1.setId(null);
        assertThat(wayToPayDTO1).isNotEqualTo(wayToPayDTO2);
    }
}
