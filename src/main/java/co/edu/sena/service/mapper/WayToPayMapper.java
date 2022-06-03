package co.edu.sena.service.mapper;

import co.edu.sena.domain.WayToPay;
import co.edu.sena.service.dto.WayToPayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WayToPay} and its DTO {@link WayToPayDTO}.
 */
@Mapper(componentModel = "spring")
public interface WayToPayMapper extends EntityMapper<WayToPayDTO, WayToPay> {}
