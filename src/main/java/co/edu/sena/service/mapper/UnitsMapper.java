package co.edu.sena.service.mapper;

import co.edu.sena.domain.Products;
import co.edu.sena.domain.Units;
import co.edu.sena.service.dto.ProductsDTO;
import co.edu.sena.service.dto.UnitsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Units} and its DTO {@link UnitsDTO}.
 */
@Mapper(componentModel = "spring")
public interface UnitsMapper extends EntityMapper<UnitsDTO, Units> {
    @Mapping(target = "products", source = "products", qualifiedByName = "productsId")
    UnitsDTO toDto(Units s);

    @Named("productsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductsDTO toDtoProductsId(Products products);
}
