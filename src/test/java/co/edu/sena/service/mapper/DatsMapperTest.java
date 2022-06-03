package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatsMapperTest {

    private DatsMapper datsMapper;

    @BeforeEach
    public void setUp() {
        datsMapper = new DatsMapperImpl();
    }
}
