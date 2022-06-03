package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnonymousMapperTest {

    private AnonymousMapper anonymousMapper;

    @BeforeEach
    public void setUp() {
        anonymousMapper = new AnonymousMapperImpl();
    }
}
