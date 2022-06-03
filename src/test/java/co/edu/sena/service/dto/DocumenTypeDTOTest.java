package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumenTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumenTypeDTO.class);
        DocumenTypeDTO documenTypeDTO1 = new DocumenTypeDTO();
        documenTypeDTO1.setId(1L);
        DocumenTypeDTO documenTypeDTO2 = new DocumenTypeDTO();
        assertThat(documenTypeDTO1).isNotEqualTo(documenTypeDTO2);
        documenTypeDTO2.setId(documenTypeDTO1.getId());
        assertThat(documenTypeDTO1).isEqualTo(documenTypeDTO2);
        documenTypeDTO2.setId(2L);
        assertThat(documenTypeDTO1).isNotEqualTo(documenTypeDTO2);
        documenTypeDTO1.setId(null);
        assertThat(documenTypeDTO1).isNotEqualTo(documenTypeDTO2);
    }
}
