package co.edu.sena.service.mapper;

import co.edu.sena.domain.Driver;
import co.edu.sena.domain.Vehicle;
import co.edu.sena.service.dto.DriverDTO;
import co.edu.sena.service.dto.VehicleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vehicle} and its DTO {@link VehicleDTO}.
 */
@Mapper(componentModel = "spring")
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {
    @Mapping(target = "driver", source = "driver", qualifiedByName = "driverId")
    VehicleDTO toDto(Vehicle s);

    @Named("driverId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DriverDTO toDtoDriverId(Driver driver);
}
