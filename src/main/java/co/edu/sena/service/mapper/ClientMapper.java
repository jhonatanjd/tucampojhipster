package co.edu.sena.service.mapper;

import co.edu.sena.domain.Client;
import co.edu.sena.domain.Invoice;
import co.edu.sena.service.dto.ClientDTO;
import co.edu.sena.service.dto.InvoiceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "invoice", source = "invoice", qualifiedByName = "invoiceId")
    ClientDTO toDto(Client s);

    @Named("invoiceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InvoiceDTO toDtoInvoiceId(Invoice invoice);
}
