package co.edu.sena.service.mapper;

import co.edu.sena.domain.Dats;
import co.edu.sena.domain.DocumenType;
import co.edu.sena.service.dto.DatsDTO;
import co.edu.sena.service.dto.DocumenTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumenType} and its DTO {@link DocumenTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumenTypeMapper extends EntityMapper<DocumenTypeDTO, DocumenType> {
    @Mapping(target = "dats", source = "dats", qualifiedByName = "datsId")
    DocumenTypeDTO toDto(DocumenType s);

    @Named("datsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DatsDTO toDtoDatsId(Dats dats);
}
