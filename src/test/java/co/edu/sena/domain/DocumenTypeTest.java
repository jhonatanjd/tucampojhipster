package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumenTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumenType.class);
        DocumenType documenType1 = new DocumenType();
        documenType1.setId(1L);
        DocumenType documenType2 = new DocumenType();
        documenType2.setId(documenType1.getId());
        assertThat(documenType1).isEqualTo(documenType2);
        documenType2.setId(2L);
        assertThat(documenType1).isNotEqualTo(documenType2);
        documenType1.setId(null);
        assertThat(documenType1).isNotEqualTo(documenType2);
    }
}
