package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DatsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatsDTO.class);
        DatsDTO datsDTO1 = new DatsDTO();
        datsDTO1.setId(1L);
        DatsDTO datsDTO2 = new DatsDTO();
        assertThat(datsDTO1).isNotEqualTo(datsDTO2);
        datsDTO2.setId(datsDTO1.getId());
        assertThat(datsDTO1).isEqualTo(datsDTO2);
        datsDTO2.setId(2L);
        assertThat(datsDTO1).isNotEqualTo(datsDTO2);
        datsDTO1.setId(null);
        assertThat(datsDTO1).isNotEqualTo(datsDTO2);
    }
}
