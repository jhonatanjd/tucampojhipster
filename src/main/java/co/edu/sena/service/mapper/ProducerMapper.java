package co.edu.sena.service.mapper;

import co.edu.sena.domain.Producer;
import co.edu.sena.service.dto.ProducerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producer} and its DTO {@link ProducerDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProducerMapper extends EntityMapper<ProducerDTO, Producer> {}
