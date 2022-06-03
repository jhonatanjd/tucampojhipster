package co.edu.sena.service.mapper;

import co.edu.sena.domain.Administrator;
import co.edu.sena.service.dto.AdministratorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Administrator} and its DTO {@link AdministratorDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdministratorMapper extends EntityMapper<AdministratorDTO, Administrator> {}
