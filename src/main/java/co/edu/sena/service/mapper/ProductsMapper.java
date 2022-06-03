package co.edu.sena.service.mapper;

import co.edu.sena.domain.Category;
import co.edu.sena.domain.OrderDetai;
import co.edu.sena.domain.Products;
import co.edu.sena.domain.Sale;
import co.edu.sena.service.dto.CategoryDTO;
import co.edu.sena.service.dto.OrderDetaiDTO;
import co.edu.sena.service.dto.ProductsDTO;
import co.edu.sena.service.dto.SaleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Products} and its DTO {@link ProductsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductsMapper extends EntityMapper<ProductsDTO, Products> {
    @Mapping(target = "sale", source = "sale", qualifiedByName = "saleId")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    @Mapping(target = "orderDetai", source = "orderDetai", qualifiedByName = "orderDetaiId")
    ProductsDTO toDto(Products s);

    @Named("saleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SaleDTO toDtoSaleId(Sale sale);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);

    @Named("orderDetaiId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDetaiDTO toDtoOrderDetaiId(OrderDetai orderDetai);
}
