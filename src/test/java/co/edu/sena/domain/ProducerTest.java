package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProducerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Producer.class);
        Producer producer1 = new Producer();
        producer1.setId(1L);
        Producer producer2 = new Producer();
        producer2.setId(producer1.getId());
        assertThat(producer1).isEqualTo(producer2);
        producer2.setId(2L);
        assertThat(producer1).isNotEqualTo(producer2);
        producer1.setId(null);
        assertThat(producer1).isNotEqualTo(producer2);
    }
}
