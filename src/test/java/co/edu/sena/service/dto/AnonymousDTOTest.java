package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnonymousDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnonymousDTO.class);
        AnonymousDTO anonymousDTO1 = new AnonymousDTO();
        anonymousDTO1.setId(1L);
        AnonymousDTO anonymousDTO2 = new AnonymousDTO();
        assertThat(anonymousDTO1).isNotEqualTo(anonymousDTO2);
        anonymousDTO2.setId(anonymousDTO1.getId());
        assertThat(anonymousDTO1).isEqualTo(anonymousDTO2);
        anonymousDTO2.setId(2L);
        assertThat(anonymousDTO1).isNotEqualTo(anonymousDTO2);
        anonymousDTO1.setId(null);
        assertThat(anonymousDTO1).isNotEqualTo(anonymousDTO2);
    }
}
