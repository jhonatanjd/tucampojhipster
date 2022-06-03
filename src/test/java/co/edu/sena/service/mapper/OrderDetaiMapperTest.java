package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderDetaiMapperTest {

    private OrderDetaiMapper orderDetaiMapper;

    @BeforeEach
    public void setUp() {
        orderDetaiMapper = new OrderDetaiMapperImpl();
    }
}
