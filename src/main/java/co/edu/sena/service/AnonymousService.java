package co.edu.sena.service;

import co.edu.sena.service.dto.AnonymousDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Anonymous}.
 */
public interface AnonymousService {
    /**
     * Save a anonymous.
     *
     * @param anonymousDTO the entity to save.
     * @return the persisted entity.
     */
    AnonymousDTO save(AnonymousDTO anonymousDTO);

    /**
     * Updates a anonymous.
     *
     * @param anonymousDTO the entity to update.
     * @return the persisted entity.
     */
    AnonymousDTO update(AnonymousDTO anonymousDTO);

    /**
     * Partially updates a anonymous.
     *
     * @param anonymousDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnonymousDTO> partialUpdate(AnonymousDTO anonymousDTO);

    /**
     * Get all the anonymous.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnonymousDTO> findAll(Pageable pageable);

    /**
     * Get the "id" anonymous.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnonymousDTO> findOne(Long id);

    /**
     * Delete the "id" anonymous.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
