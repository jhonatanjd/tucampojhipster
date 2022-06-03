package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShoppingMapperTest {

    private ShoppingMapper shoppingMapper;

    @BeforeEach
    public void setUp() {
        shoppingMapper = new ShoppingMapperImpl();
    }
}
