package co.edu.sena.service.mapper;

import co.edu.sena.domain.OrderDetai;
import co.edu.sena.domain.Producer;
import co.edu.sena.domain.Sale;
import co.edu.sena.service.dto.OrderDetaiDTO;
import co.edu.sena.service.dto.ProducerDTO;
import co.edu.sena.service.dto.SaleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sale} and its DTO {@link SaleDTO}.
 */
@Mapper(componentModel = "spring")
public interface SaleMapper extends EntityMapper<SaleDTO, Sale> {
    @Mapping(target = "producer", source = "producer", qualifiedByName = "producerId")
    @Mapping(target = "orderDetai", source = "orderDetai", qualifiedByName = "orderDetaiId")
    SaleDTO toDto(Sale s);

    @Named("producerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProducerDTO toDtoProducerId(Producer producer);

    @Named("orderDetaiId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDetaiDTO toDtoOrderDetaiId(OrderDetai orderDetai);
}
