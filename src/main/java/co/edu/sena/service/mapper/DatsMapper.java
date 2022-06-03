package co.edu.sena.service.mapper;

import co.edu.sena.domain.Administrator;
import co.edu.sena.domain.Client;
import co.edu.sena.domain.Dats;
import co.edu.sena.domain.Driver;
import co.edu.sena.domain.Producer;
import co.edu.sena.domain.User;
import co.edu.sena.service.dto.AdministratorDTO;
import co.edu.sena.service.dto.ClientDTO;
import co.edu.sena.service.dto.DatsDTO;
import co.edu.sena.service.dto.DriverDTO;
import co.edu.sena.service.dto.ProducerDTO;
import co.edu.sena.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dats} and its DTO {@link DatsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DatsMapper extends EntityMapper<DatsDTO, Dats> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    @Mapping(target = "producer", source = "producer", qualifiedByName = "producerId")
    @Mapping(target = "administrator", source = "administrator", qualifiedByName = "administratorId")
    @Mapping(target = "driver", source = "driver", qualifiedByName = "driverId")
    DatsDTO toDto(Dats s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);

    @Named("producerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProducerDTO toDtoProducerId(Producer producer);

    @Named("administratorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdministratorDTO toDtoAdministratorId(Administrator administrator);

    @Named("driverId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DriverDTO toDtoDriverId(Driver driver);
}
