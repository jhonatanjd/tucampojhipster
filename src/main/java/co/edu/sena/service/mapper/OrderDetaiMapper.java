package co.edu.sena.service.mapper;

import co.edu.sena.domain.OrderDetai;
import co.edu.sena.service.dto.OrderDetaiDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderDetai} and its DTO {@link OrderDetaiDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderDetaiMapper extends EntityMapper<OrderDetaiDTO, OrderDetai> {}
