package co.edu.sena.service.mapper;

import co.edu.sena.domain.Client;
import co.edu.sena.domain.Invoice;
import co.edu.sena.domain.Shopping;
import co.edu.sena.service.dto.ClientDTO;
import co.edu.sena.service.dto.InvoiceDTO;
import co.edu.sena.service.dto.ShoppingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Shopping} and its DTO {@link ShoppingDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShoppingMapper extends EntityMapper<ShoppingDTO, Shopping> {
    @Mapping(target = "invoice", source = "invoice", qualifiedByName = "invoiceId")
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    ShoppingDTO toDto(Shopping s);

    @Named("invoiceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InvoiceDTO toDtoInvoiceId(Invoice invoice);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
