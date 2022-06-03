package co.edu.sena.service;

import co.edu.sena.service.dto.OrderDetaiDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.OrderDetai}.
 */
public interface OrderDetaiService {
    /**
     * Save a orderDetai.
     *
     * @param orderDetaiDTO the entity to save.
     * @return the persisted entity.
     */
    OrderDetaiDTO save(OrderDetaiDTO orderDetaiDTO);

    /**
     * Updates a orderDetai.
     *
     * @param orderDetaiDTO the entity to update.
     * @return the persisted entity.
     */
    OrderDetaiDTO update(OrderDetaiDTO orderDetaiDTO);

    /**
     * Partially updates a orderDetai.
     *
     * @param orderDetaiDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrderDetaiDTO> partialUpdate(OrderDetaiDTO orderDetaiDTO);

    /**
     * Get all the orderDetais.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderDetaiDTO> findAll(Pageable pageable);

    /**
     * Get the "id" orderDetai.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderDetaiDTO> findOne(Long id);

    /**
     * Delete the "id" orderDetai.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
