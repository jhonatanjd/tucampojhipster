package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WayToPayMapperTest {

    private WayToPayMapper wayToPayMapper;

    @BeforeEach
    public void setUp() {
        wayToPayMapper = new WayToPayMapperImpl();
    }
}
