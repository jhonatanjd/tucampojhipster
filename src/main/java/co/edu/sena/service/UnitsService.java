package co.edu.sena.service;

import co.edu.sena.service.dto.UnitsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Units}.
 */
public interface UnitsService {
    /**
     * Save a units.
     *
     * @param unitsDTO the entity to save.
     * @return the persisted entity.
     */
    UnitsDTO save(UnitsDTO unitsDTO);

    /**
     * Updates a units.
     *
     * @param unitsDTO the entity to update.
     * @return the persisted entity.
     */
    UnitsDTO update(UnitsDTO unitsDTO);

    /**
     * Partially updates a units.
     *
     * @param unitsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UnitsDTO> partialUpdate(UnitsDTO unitsDTO);

    /**
     * Get all the units.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UnitsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" units.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UnitsDTO> findOne(Long id);

    /**
     * Delete the "id" units.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
