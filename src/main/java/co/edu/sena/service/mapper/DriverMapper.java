package co.edu.sena.service.mapper;

import co.edu.sena.domain.Driver;
import co.edu.sena.service.dto.DriverDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Driver} and its DTO {@link DriverDTO}.
 */
@Mapper(componentModel = "spring")
public interface DriverMapper extends EntityMapper<DriverDTO, Driver> {}
