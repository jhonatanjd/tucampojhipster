package co.edu.sena.service;

import co.edu.sena.service.dto.WayToPayDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.WayToPay}.
 */
public interface WayToPayService {
    /**
     * Save a wayToPay.
     *
     * @param wayToPayDTO the entity to save.
     * @return the persisted entity.
     */
    WayToPayDTO save(WayToPayDTO wayToPayDTO);

    /**
     * Updates a wayToPay.
     *
     * @param wayToPayDTO the entity to update.
     * @return the persisted entity.
     */
    WayToPayDTO update(WayToPayDTO wayToPayDTO);

    /**
     * Partially updates a wayToPay.
     *
     * @param wayToPayDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WayToPayDTO> partialUpdate(WayToPayDTO wayToPayDTO);

    /**
     * Get all the wayToPays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WayToPayDTO> findAll(Pageable pageable);

    /**
     * Get the "id" wayToPay.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WayToPayDTO> findOne(Long id);

    /**
     * Delete the "id" wayToPay.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
