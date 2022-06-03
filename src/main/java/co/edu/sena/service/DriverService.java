package co.edu.sena.service;

import co.edu.sena.service.dto.DriverDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Driver}.
 */
public interface DriverService {
    /**
     * Save a driver.
     *
     * @param driverDTO the entity to save.
     * @return the persisted entity.
     */
    DriverDTO save(DriverDTO driverDTO);

    /**
     * Updates a driver.
     *
     * @param driverDTO the entity to update.
     * @return the persisted entity.
     */
    DriverDTO update(DriverDTO driverDTO);

    /**
     * Partially updates a driver.
     *
     * @param driverDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DriverDTO> partialUpdate(DriverDTO driverDTO);

    /**
     * Get all the drivers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DriverDTO> findAll(Pageable pageable);
    /**
     * Get all the DriverDTO where Vehicle is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DriverDTO> findAllWhereVehicleIsNull();

    /**
     * Get the "id" driver.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DriverDTO> findOne(Long id);

    /**
     * Delete the "id" driver.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
