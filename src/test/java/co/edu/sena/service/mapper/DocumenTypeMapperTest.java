package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DocumenTypeMapperTest {

    private DocumenTypeMapper documenTypeMapper;

    @BeforeEach
    public void setUp() {
        documenTypeMapper = new DocumenTypeMapperImpl();
    }
}
