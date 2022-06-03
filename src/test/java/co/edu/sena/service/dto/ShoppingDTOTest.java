package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShoppingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingDTO.class);
        ShoppingDTO shoppingDTO1 = new ShoppingDTO();
        shoppingDTO1.setId(1L);
        ShoppingDTO shoppingDTO2 = new ShoppingDTO();
        assertThat(shoppingDTO1).isNotEqualTo(shoppingDTO2);
        shoppingDTO2.setId(shoppingDTO1.getId());
        assertThat(shoppingDTO1).isEqualTo(shoppingDTO2);
        shoppingDTO2.setId(2L);
        assertThat(shoppingDTO1).isNotEqualTo(shoppingDTO2);
        shoppingDTO1.setId(null);
        assertThat(shoppingDTO1).isNotEqualTo(shoppingDTO2);
    }
}
