package co.edu.sena.service.mapper;

import co.edu.sena.domain.Invoice;
import co.edu.sena.domain.WayToPay;
import co.edu.sena.service.dto.InvoiceDTO;
import co.edu.sena.service.dto.WayToPayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invoice} and its DTO {@link InvoiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, Invoice> {
    @Mapping(target = "wayToPay", source = "wayToPay", qualifiedByName = "wayToPayId")
    InvoiceDTO toDto(Invoice s);

    @Named("wayToPayId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WayToPayDTO toDtoWayToPayId(WayToPay wayToPay);
}
