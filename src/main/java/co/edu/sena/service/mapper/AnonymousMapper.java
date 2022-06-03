package co.edu.sena.service.mapper;

import co.edu.sena.domain.Anonymous;
import co.edu.sena.domain.Sale;
import co.edu.sena.service.dto.AnonymousDTO;
import co.edu.sena.service.dto.SaleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Anonymous} and its DTO {@link AnonymousDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnonymousMapper extends EntityMapper<AnonymousDTO, Anonymous> {
    @Mapping(target = "sale", source = "sale", qualifiedByName = "saleId")
    AnonymousDTO toDto(Anonymous s);

    @Named("saleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SaleDTO toDtoSaleId(Sale sale);
}
